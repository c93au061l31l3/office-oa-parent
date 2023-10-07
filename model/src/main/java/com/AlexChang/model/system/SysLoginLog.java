package com.AlexChang.model.system;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

/**
 * ClassName:SysLoginLog
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 上午 09:52
 * @Version:1.0
 */

@Data
//@ApiModel(description = "SysLoginLog")
@TableName("sys_login_log")
public class SysLoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "用戶帳號")
    @TableField("username")
    private String username;

    //@ApiModelProperty(value = "登入ip地址")
    @TableField("ipaddr")
    private String ipaddr;

    //@ApiModelProperty(value = "登入狀態(0成功 1失敗)")
    @TableField("status")
    private Integer status;

    //@ApiModelProperty(value = "提示信息")
    @TableField("msg")
    private String msg;

    //@ApiModelProperty(value = "訪問時間")
    @TableField("access_time")
    private Date accessTime;

}
