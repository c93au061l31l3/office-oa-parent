package com.AlexChang.process.service;

import com.AlexChang.model.process.Process;
import com.AlexChang.model.process.ProcessTemplate;
import com.AlexChang.vo.process.ApprovalVo;
import com.AlexChang.vo.process.ProcessFormVo;
import com.AlexChang.vo.process.ProcessQueryVo;
import com.AlexChang.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * ClassName:OaProcessService
 * Description:
 *
 * @author Alex
 * @create 2023-11-08 下午 12:50
 * @Version:1.0
 */
public interface OaProcessService extends IService<Process> {


    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);

    //部屬流程定義
    void deployByZip( ProcessTemplate processTemplate/*String deployPath*/);

    void startUp(ProcessFormVo formVo);

    IPage<ProcessVo> findPending(Page<Process> pageParam);

    Map<String, Object> show(Long id);

    void approve(ApprovalVo approvalVo);

    void endTask(String taskId);

    IPage<ProcessVo> findProcessed(Page<Process> pageParam);

    IPage<ProcessVo> findStarted(Page<ProcessVo> pageParam);
}
