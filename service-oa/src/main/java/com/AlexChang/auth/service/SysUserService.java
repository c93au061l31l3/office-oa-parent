package com.AlexChang.auth.service;

import com.AlexChang.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * ClassName:SysUserService
 * Description:
 *
 * @author Alex
 * @create 2023-10-06 下午 08:29
 * @Version:1.0
 */
public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id, Integer status);

    SysUser getUserByName(String username);

    Map<String, Object> getCurrentUser();
}
