package com.AlexChang.model.system;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ClassName:SysUser
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 上午 11:26
 * @Version:1.0
 */

@Data
@ApiModel(description = "用戶")
@TableName("sys_user")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用戶名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密碼")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手機")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "頭像地址")
    @TableField("head_url")
    private String headUrl;

    @ApiModelProperty(value = "部門id")
    @TableField("dept_id")
    private Long deptId;

    @ApiModelProperty(value = "職位id")
    @TableField("post_id")
    private Long postId;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "openId")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "狀態(1:正常 0:停用)")
    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private List<SysRole> roleList;

    @TableField(exist = false)
    private String postName; //職位

    @TableField(exist = false)
    private String deptName; //部門

}
