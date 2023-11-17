package com.AlexChang.process.service.impl;

import com.AlexChang.model.process.ProcessTemplate;
import com.AlexChang.model.process.ProcessType;
import com.AlexChang.process.mapper.OaProcessTemplateMapper;
import com.AlexChang.process.service.OaProcessService;
import com.AlexChang.process.service.OaProcessTemplateService;
import com.AlexChang.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ClassName:OaProcessTemplateServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-10-20 下午 05:07
 * @Version:1.0
 */

@Service
public class OaProcessTemplateServiceImpl
        extends ServiceImpl<OaProcessTemplateMapper, ProcessTemplate>
        implements OaProcessTemplateService {

    @Autowired
    private OaProcessTypeService processTypeService;


    @Autowired
    private OaProcessService processService;

    @Override
    public IPage<ProcessTemplate> selectPageProcessTemplate(Page<ProcessTemplate> pageParam) {

        //1.調用mapper的方法實現分頁查詢
        Page<ProcessTemplate> processTemplatePage = baseMapper.selectPage(pageParam, null);

        //2.第一步分頁查詢返回的分頁數據，從分頁數據獲取列表的List集合
        List<ProcessTemplate> processTemplateList = processTemplatePage.getRecords();

        //3.遍歷List，以得到每個對象的審批類型id
        for(ProcessTemplate processTemplate : processTemplateList){
            Long processTypeId = processTemplate.getProcessTypeId();
            //4.根據審批類型id,查詢獲取對應名稱
            LambdaQueryWrapper<ProcessType> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessType::getId, processTypeId);
            ProcessType processType = processTypeService.getOne(wrapper);
            if(processType == null){
                continue;
            }
            //5.完成封裝processTypeName
            processTemplate.setProcessTypeName(processType.getName());
        }


        return processTemplatePage;
    }

    @Override
    public void publish(Long id) {
        //修改模板發布狀態，1:代表已經發布
        ProcessTemplate processTemplate = baseMapper.selectById(id);
        processTemplate.setStatus(1);
        baseMapper.updateById(processTemplate);

        String deployPath = processTemplate.getProcessDefinitionPath();

        // 流程定義部屬
        if(!StringUtils.isEmpty(deployPath)){
            processService.deployByZip(processTemplate/*.getProcessDefinitionPath()*/);
        }

    }
}
