package com.AlexChang.process.service;

import com.AlexChang.model.process.ProcessType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * ClassName:OaProcessTypeService
 * Description:
 *
 * @author Alex
 * @create 2023-10-20 下午 05:05
 * @Version:1.0
 */
public interface OaProcessTypeService extends IService<ProcessType> {
    List<ProcessType> findProcessType();
}
