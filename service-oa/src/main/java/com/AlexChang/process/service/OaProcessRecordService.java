package com.AlexChang.process.service;

import com.AlexChang.model.process.ProcessRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * ClassName:OaProcessRecordService
 * Description:
 *
 * @author Alex
 * @create 2023-11-11 下午 05:53
 * @Version:1.0
 */
public interface OaProcessRecordService extends IService<ProcessRecord> {

    void record(Long processId, Integer status, String description);

}
