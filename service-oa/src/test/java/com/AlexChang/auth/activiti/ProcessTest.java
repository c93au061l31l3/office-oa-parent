package com.AlexChang.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * ClassName:ProcessTest
 * Description:
 *
 * @author Alex
 * @create 2023-10-18 下午 05:31
 * @Version:1.0
 */

@SpringBootTest
public class ProcessTest {

    //注入RepositoryService
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    //單個文件部屬
    @Test
    public void deployProcess(){
        //流程部屬
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .addClasspathResource("process/qingjia.png")
                .name("請假申請流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    //啟動一個流程實例
    @Test
    public void startProcess(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia");
        System.out.println("流程定義id" + processInstance.getProcessDefinitionId());
        System.out.println("流程實例id" + processInstance.getId());
        System.out.println("當前活動id" + processInstance.getActivityId());
    }

    /**
     * 查询当前个人待执行的任务
     */
    @Test
    public void findPendingTaskList() {
        //任务负责人
        String assignee = "張三";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assignee)//只查询该任务负责人的任务
                .list();
        for (Task task : list) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }

    //處理當前任務
    @Test
    public void completeTask(){

        //查詢負責人需要處裡的一條任務，返回一條
        Task task = taskService.createTaskQuery()
                .taskAssignee("張三")
                .singleResult();
        //完成任務
        taskService.complete(task.getId());
    }

    //查詢已經處理的任務
    @Test
    public void findCompleteTaskList(){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee("張三")
                .finished().list();

        for(HistoricTaskInstance historicTaskInstance : list){
            System.out.println("實例id :" + historicTaskInstance.getProcessInstanceId());
            System.out.println("任務id :" + historicTaskInstance.getId());
            System.out.println("任務負責人id :" + historicTaskInstance.getAssignee());
            System.out.println("任務名稱 :" + historicTaskInstance.getName());
        }
    }


    //創建流程實例，指定BusinessKey
    @Test
    public void startUpProcessAddBusinessKey(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia", "1001");
        System.out.println(processInstance.getBusinessKey());
    }

    //全部流程实例挂起or激活
    @Test
    public void suspendProcessInstanceAll(){

        //1.獲取流程定義對象
        ProcessDefinition qingjia = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("qingjia").singleResult();

        //2.調用流程定義對象的方法判斷當前狀態
        boolean suspended = qingjia.isSuspended();

        //3.判斷，如果掛起:實現激活
        if(suspended){
            //第一个参数 流程定义id
            //第二个参数 是否激活 true
            //第三个参数 时间点
            repositoryService.activateProcessDefinitionById(qingjia.getId(),true,null);
            System.out.println(qingjia.getId() + " 激活了");
        }else{ //判斷，如果激活，實現掛起
            repositoryService.suspendProcessDefinitionById(qingjia.getId(),true,null);
            System.out.println(qingjia.getId() + " 掛起了");
        }
    }


    //单个流程实例挂起
    @Test
    public void SingleSuspendProcessInstance() {
        String processInstanceId = "1d3b541f-6da6-11ee-81fd-049226087d15";
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        //获取到当前流程定义是否为暂停状态   suspended方法为true代表为暂停   false就是运行的
        boolean suspended = processInstance.isSuspended();
        if (suspended) {
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程实例:" + processInstanceId + "激活");
        } else {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程实例:" + processInstanceId + "挂起");
        }
    }

}
