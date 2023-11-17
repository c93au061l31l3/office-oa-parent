package com.AlexChang.process.service.impl;

import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.model.process.ProcessRecord;
import com.AlexChang.model.system.SysUser;
import com.AlexChang.process.mapper.OaProcessRecordMapper;
import com.AlexChang.process.service.OaProcessRecordService;
import com.AlexChang.security.custom.LoginUserInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:OaProcessRecordServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-11-11 下午 05:53
 * @Version:1.0
 */

@Service
public class OaProcessRecordServiceImpl
        extends ServiceImpl<OaProcessRecordMapper, ProcessRecord>
        implements OaProcessRecordService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 紀錄審批流程的操作
     * @param processId
     * @param status
     * @param description
     */
    @Override
    public void record(Long processId, Integer status, String description) {

        Long userId = LoginUserInfoHelper.getUserId();
        SysUser sysUser = sysUserService.getById(userId);

        ProcessRecord processRecord = new ProcessRecord();
        processRecord.setProcessId(processId);
        processRecord.setStatus(status);
        processRecord.setDescription(description);
        processRecord.setOperateUser(sysUser.getName());
        processRecord.setOperateUserId(userId);

        baseMapper.insert(processRecord);
    }
}
