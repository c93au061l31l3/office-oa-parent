package com.AlexChang.model.process;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * ClassName:ProcessRecord
 * Description:
 *
 * @author Alex
 * @create 2023-09-11 下午 07:19
 * @Version:1.0
 */

@Data
//@ApiModel(description = "ProcessRecord")
@TableName("oa_process_record")
public class ProcessRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "審查流程id")
    @TableField("process_id")
    private Long processId;

    //@ApiModelProperty(value = "審查描述")
    @TableField("description")
    private String description;

    //@ApiModelProperty(value = "狀態")
    @TableField("status")
    private Integer status;

    //@ApiModelProperty(value = "操作用戶id")
    @TableField("operate_user_id")
    private Long operateUserId;

    //@ApiModelProperty(value = "操作用戶")
    @TableField("operate_user")
    private String operateUser;
}
