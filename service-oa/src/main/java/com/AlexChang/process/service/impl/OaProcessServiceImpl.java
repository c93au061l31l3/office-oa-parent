package com.AlexChang.process.service.impl;

import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.model.process.Process;
import com.AlexChang.model.process.ProcessRecord;
import com.AlexChang.model.process.ProcessTemplate;
import com.AlexChang.model.system.SysUser;
import com.AlexChang.process.mapper.OaProcessMapper;
import com.AlexChang.process.service.OaProcessRecordService;
import com.AlexChang.process.service.OaProcessService;
import com.AlexChang.process.service.OaProcessTemplateService;
import com.AlexChang.security.custom.LoginUserInfoHelper;
import com.AlexChang.vo.process.ApprovalVo;
import com.AlexChang.vo.process.ProcessFormVo;
import com.AlexChang.vo.process.ProcessQueryVo;
import com.AlexChang.vo.process.ProcessVo;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.io.InputStream;
import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * ClassName:OaProcessServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-11-08 下午 12:51
 * @Version:1.0
 */

@Service
public class OaProcessServiceImpl extends ServiceImpl<OaProcessMapper, Process> implements OaProcessService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OaProcessTemplateService processTemplateService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private OaProcessRecordService processRecordService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private OaProcessMapper processMapper;
    @Override
    public IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo) {

        IPage<ProcessVo> pageModel = processMapper.selectPage(pageParam, processQueryVo);

        return pageModel;
    }

    @Override
    public void deployByZip(ProcessTemplate processTemplate/*String deployPath*/) {
        String deployPath = processTemplate.getProcessDefinitionPath();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(deployPath);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //部屬
        Deployment deployment = repositoryService
                .createDeployment()
                .addZipInputStream(zipInputStream)
                .name(processTemplate.getName())
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

    /**
     * 啟動流程
     * @param processFormVo
     */
    @Override
    public void startUp(ProcessFormVo processFormVo) {

        //1.根據當前用戶id獲取用戶信息
        SysUser sysUser = sysUserService.getById(LoginUserInfoHelper.getUserId());

        //2.根據審批模板id查詢模板信息
        ProcessTemplate processTemplate = processTemplateService.getById(processFormVo.getProcessTemplateId());

        //3.保存提交審批信息到業務表(oa_process)
        Process process = new Process();
        //processFormVo 的值複製到 process
        BeanUtils.copyProperties(processFormVo, process);
        //其他的值
        process.setStatus(1); //審批中
        String workNo = System.currentTimeMillis() + "";
        process.setProcessCode(workNo);
        process.setUserId(LoginUserInfoHelper.getUserId());
        process.setFormValues(processFormVo.getFormValues());
        process.setTitle(sysUser.getName() + "進行" + processTemplate.getName() + "申請");
        baseMapper.insert(process);

        //4.啟動流程實例 (通過RuntimeService啟動)
        //4.1 參數:流程定義key
        String processDefinitionKey = processTemplate.getProcessDefinitionKey();

        //4.2 參數:業務key
        String businessKey = String.valueOf(process.getId());

        //4.3 參數:流程參數form表單json數據，轉換map集合傳入
        String formValues = processFormVo.getFormValues();
        JSONObject jsonObject = JSON.parseObject(formValues);
        JSONObject formData = jsonObject.getJSONObject("formData");

        //遍例formData的內容，封裝到map集合中
        Map<String,Object> map = new HashMap();
        for(Map.Entry<String,Object> entry : formData.entrySet()){
            map.put(entry.getKey(),entry.getValue());
        }
        Map<String,Object> variables = new HashMap<>();
        variables.put("data",map);

        //流程 啟動!
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

        //5.查詢下一個審批人(可能有多個人)
        List<Task> taskList = this.getCurrentTaskList(processInstance.getId());
        List<String> nameList = new ArrayList<>();
        for(Task task : taskList){
            String assigneeName = task.getAssignee(); //獲取審批人
            SysUser user = sysUserService.getUserByName(assigneeName);
            String name = user.getName();
            nameList.add(name);

            //todo 6.推送消息
        }
        process.setProcessInstanceId(processInstance.getId());
        process.setDescription("等待" + StringUtils.join(nameList.toArray(),",") + "審批");

        //7.業務&流程進行關聯
        baseMapper.updateById(process);
        //紀錄操作
        processRecordService.record(process.getId(),1,"進行申請");

    }

    /**
     * 查詢待處理的任務列表
     *
     * @param pageParam
     * @return
     */
    @Override
    public IPage<ProcessVo> findPending(Page<Process> pageParam) {

        //1.封裝查詢條件，根據當前用戶名稱封裝
        TaskQuery query = taskService.createTaskQuery()
                .taskAssignee(LoginUserInfoHelper.getUsername())
                .orderByTaskCreateTime()
                .desc();

        //2.調用方法分頁條件查詢，返回list集合，待辦任物集合
        //參數1: 開始位置 ; 參數2: 每頁顯示數
        int begin = (int) ((pageParam.getCurrent() - 1) * pageParam.getSize());
        int size = (int) pageParam.getSize();
        List<Task> taskList = query.listPage(begin, size);
        long totalCount = query.count();

        //3.封裝返回list集合數據到List<ProcessVo>裡
        List<ProcessVo> processVoList = new ArrayList<>();
        for(Task task : taskList){
            //從task中獲取流程實例id
            String processDefinitionId = task.getProcessInstanceId();
            //根據流程實例id獲取實例對象
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processDefinitionId)
                    .singleResult();
            //從流程實例對象獲取業務key---processId
            String businessKey = processInstance.getBusinessKey();
            if(businessKey == null){
                continue;
            }
            //根據業務key獲取Process對象
            long processId = Long.parseLong(businessKey);
            Process process = baseMapper.selectById(processId);
            //Process對象複製到ProcessVo對象
            ProcessVo processVo = new ProcessVo();
            BeanUtils.copyProperties(process, processVo);
            processVo.setTaskId(task.getId());
            processVoList.add(processVo);
        }

        //4.返回IPage對象
        IPage<ProcessVo> page = new Page<>(pageParam.getCurrent(),pageParam.getSize(),totalCount);
        page.setRecords(processVoList);
        return page;
    }

    /**
     * 查看審批祥情
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> show(Long id) {

        //1.根據流程id獲取流程信息Process
        Process process = baseMapper.selectById(id);

        //2.根據流程id獲取流程紀錄信息
        LambdaQueryWrapper<ProcessRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProcessRecord::getProcessId,id);
        List<ProcessRecord> processRecordList = processRecordService.list(wrapper);

        //3.根據模板id查詢模板信息
        ProcessTemplate processTemplate = processTemplateService.getById(process.getProcessTemplateId());

        //4.判斷當前用戶是否有權限審批&不能重複審批
        boolean isApprove = false;
        List<Task> currentTaskList = this.getCurrentTaskList(process.getProcessInstanceId());
        for(Task task : currentTaskList){
            //判斷此流程的審批人是否是當前用戶
            String username = LoginUserInfoHelper.getUsername();
            if(task.getAssignee().equals(username)){
                isApprove = true;
            }
        }

        //5.將查詢數據封裝並返回
        Map<String,Object> map = new HashMap<>();
        map.put("process",process);
        map.put("processRecordList",processRecordList);
        map.put("processTemplate",processTemplate);
        map.put("isApprove",isApprove);

        return map;
    }

    /**
     * 審批
     * @param approvalVo
     */
    @Override
    public void approve(ApprovalVo approvalVo) {

        //1.從approvalVo獲取任務id,再根據任務id獲取流程變量
        String taskId = approvalVo.getTaskId();
        Map<String, Object> variables = taskService.getVariables(taskId);

        //2.判斷審批狀態。 狀態值:1(審批通過) ; 狀態值:-1(駁回，流程直接結束)
        if(approvalVo.getStatus() == 1){
            Map<String,Object> variable = new HashMap<>();
            taskService.complete(taskId,variable);
        }else {
            this.endTask(taskId);
        }

        //3.紀錄審批相關過程信息
        String description = approvalVo.getStatus().intValue() == 1 ? "審核通過" : "駁回";
        processRecordService.record(approvalVo.getProcessId(),approvalVo.getStatus(),description);

        //4.查詢下一個審批人，更新流程表紀錄
        Process process = baseMapper.selectById(approvalVo.getProcessId());
        //查詢待辦任務
        List<Task> taskList = this.getCurrentTaskList(process.getProcessInstanceId());
        if(!CollectionUtils.isEmpty(taskList)){
            List<String> assignList = new ArrayList<>();
            for(Task task : taskList){
                String assignee = task.getAssignee();
                SysUser sysUser = sysUserService.getUserByName(assignee);
                assignList.add(sysUser.getName());
                //todo 消息推送
            }
            //更新process流程信息
            process.setDescription("等待" + StringUtils.join(assignList.toArray(),",") + "審批");
            process.setStatus(1);

        }else {
            if(approvalVo.getStatus().intValue() == 1){
                process.setDescription("審批完成(通過)");
                process.setStatus(2);
            }else {
                process.setDescription("審批結束(駁回)");
                process.setStatus(-1);
            }
        }
        baseMapper.updateById(process);

    }

    /**
     * 終止流程
     * @param taskId
     */
    @Override
    public void endTask(String taskId) {

        //1.根據任務id獲取任務對象(Task)
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        //2.獲取流程定義模型(BpmnModel)
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());

        //3.獲取結束的流向節點
        List<EndEvent> endEventList = bpmnModel.getMainProcess().findFlowElementsOfType(EndEvent.class);
        if(CollectionUtils.isEmpty(endEventList)){
            return;
        }
        FlowNode endFlowNode = (FlowNode) endEventList.get(0);

        //4.獲取當前流向節點
        FlowNode currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(task.getTaskDefinitionKey());

        //臨時保存當前活動的原始方向
        List originalSequenceFlowList = new ArrayList();
        originalSequenceFlowList.addAll(currentFlowNode.getOutgoingFlows());

        //5.清理當前流向方向
        currentFlowNode.getOutgoingFlows().clear();

        //6.創建新流向(指向結束)
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlow");
        newSequenceFlow.setSourceFlowElement(currentFlowNode);
        newSequenceFlow.setTargetFlowElement(endFlowNode);

        //7.將當前節點指向新方向
        List newSequenceFlowList = new ArrayList();
        newSequenceFlowList.add(newSequenceFlow);
        currentFlowNode.setOutgoingFlows(newSequenceFlowList);

        //8.完成當前任務
        taskService.complete(taskId);
    }

    /**
     * 查詢已處理任務
     * @param pageParam
     * @return
     */
    @Override
    public IPage<ProcessVo> findProcessed(Page<Process> pageParam) {

        //封裝查詢條件 (根據當前用戶名查詢此人有哪些已完成任務)
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(LoginUserInfoHelper.getUsername())
                .finished().orderByTaskCreateTime().desc();

        //調用方法條件分頁查詢，返回List集合
        //開始位置 & 每頁紀錄數
        int begin = (int) ((pageParam.getCurrent() - 1) * pageParam.getSize());
        int size = (int) pageParam.getSize();
        List<HistoricTaskInstance> list = query.listPage(begin, size);
        long totalCount = query.count();

        //遍歷返回List集合，封裝List<ProcessVo>
        List<ProcessVo> processVoList = new ArrayList<>();
        for(HistoricTaskInstance item : list){
            //獲取流程實例id
            String processInstanceId = item.getProcessInstanceId();
            //根據流程實例id查詢process信息
            LambdaQueryWrapper<Process> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Process::getProcessInstanceId,processInstanceId);
            Process process = baseMapper.selectOne(wrapper);
            //將process轉換成processVo
            ProcessVo processVo = new ProcessVo();
            BeanUtils.copyProperties(process,processVo);
            //將processVo放進list
            processVoList.add(processVo);
        }

        //IPage封裝分頁查詢的所有數據並返回
        IPage<ProcessVo> pageModel = new Page<ProcessVo>(pageParam.getCurrent(),pageParam.getSize(),totalCount);
        pageModel.setRecords(processVoList);

        return pageModel;
    }

    /**
     * 查詢已發起任務
     * @param pageParam
     * @return
     */
    @Override
    public IPage<ProcessVo> findStarted(Page<ProcessVo> pageParam) {

        ProcessQueryVo processQueryVo = new ProcessQueryVo();
        processQueryVo.setUserId(LoginUserInfoHelper.getUserId());

        IPage<ProcessVo> pageModel = baseMapper.selectPage(pageParam, processQueryVo);

        return pageModel;
    }

    /**
     * 獲取當前審批任務列表
     * @param id
     * @return
     */
    private List<Task> getCurrentTaskList(String id) {

        List<org.activiti.engine.task.Task> taskList = taskService.createTaskQuery().processInstanceId(id).list();

        return taskList;
    }
}
