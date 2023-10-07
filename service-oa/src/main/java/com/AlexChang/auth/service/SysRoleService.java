package com.AlexChang.auth.service;

import com.AlexChang.model.system.SysRole;
import com.AlexChang.vo.system.AssignRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * ClassName:SysRoleService
 * Description:
 *
 * @author Alex
 * @create 2023-09-14 上午 10:12
 * @Version:1.0
 */
public interface SysRoleService extends IService<SysRole> {

    //1.查詢所有腳色 和 當前用戶所屬腳色
    Map<String,Object> findRoleDateByUserId(Long userId);

    //2.位用戶分配角色
    void doAssign(AssignRoleVo assignRoleVo);
}
