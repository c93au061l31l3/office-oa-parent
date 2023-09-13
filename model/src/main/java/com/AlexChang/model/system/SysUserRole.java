package com.AlexChang.model.system;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:SysUserRole
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 上午 11:53
 * @Version:1.0
 */

@Data
@ApiModel(description = "用戶角色")
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "用戶id")
    @TableField("user_id")
    private Long userId;
}
