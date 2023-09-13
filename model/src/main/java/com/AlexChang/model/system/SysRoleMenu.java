package com.AlexChang.model.system;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:SysRoleMenu
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 上午 11:23
 * @Version:1.0
 */

@Data
@ApiModel(description = "角色菜單")
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "菜單id")
    @TableField("menu_id")
    private Long menuId;
}
