package com.AlexChang.process.service.impl;

import com.AlexChang.model.process.ProcessTemplate;
import com.AlexChang.model.process.ProcessType;
import com.AlexChang.process.mapper.OaProcessTypeMapper;
import com.AlexChang.process.service.OaProcessTemplateService;
import com.AlexChang.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:OaProcessTypeServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-10-20 下午 05:07
 * @Version:1.0
 */

@Service

//@SuppressWarnings({"unchecked", "rawtypes"})
public class OaProcessTypeServiceImpl extends ServiceImpl<OaProcessTypeMapper, ProcessType>
        implements OaProcessTypeService {

    @Autowired
    private OaProcessTemplateService processTemplateService;

    @Override
    public List<ProcessType> findProcessType() {

        //1.查詢所有審批分類，返回List集合
        List<ProcessType> processTypeList = baseMapper.selectList(null);

        //2.遍歷返回所有審批分類List集合
        for(ProcessType processType : processTypeList){
            //3.得到每個審批分類，根據審批分類id查詢對應審批模版
            Long typeId = processType.getId();
            LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessTemplate::getProcessTypeId,typeId);
            List<ProcessTemplate> processTemplateList = processTemplateService.list(wrapper);
            //4.根據審批分類id查詢對應審批模版數據封裝到配每個審批模版對象裡
            processType.setProcessTemplateList(processTemplateList);
        }

        return processTypeList;
    }
}
