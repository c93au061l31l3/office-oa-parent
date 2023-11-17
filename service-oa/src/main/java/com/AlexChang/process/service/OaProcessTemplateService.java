package com.AlexChang.process.service;

import com.AlexChang.model.process.ProcessTemplate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * ClassName:OaProcessTemplateService
 * Description:
 *
 * @author Alex
 * @create 2023-10-20 下午 05:05
 * @Version:1.0
 */
public interface OaProcessTemplateService extends IService<ProcessTemplate> {
    IPage<ProcessTemplate> selectPageProcessTemplate(Page<ProcessTemplate> pageParam);
    void publish(Long id);
}
