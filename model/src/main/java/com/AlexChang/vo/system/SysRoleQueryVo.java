package com.AlexChang.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * ClassName:SysRoleQueryVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 04:59
 * @Version:1.0
 */

//角色查詢實體
public class SysRoleQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;


    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
