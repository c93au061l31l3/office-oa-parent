package com.AlexChang.model.process;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ClassName:Process
 * Description:
 *
 * @author Alex
 * @create 2023-09-10 下午 02:20
 * @Version:1.0
 */

@Data
//@ApiModel(description = "Process")
@Schema(description = "Process")
@TableName("oa_process")
public class Process extends BaseEntity {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty(value = "審查code")
    @Schema(description = "審查code")
    @TableField("process_code")
    private String processCode;

//    @ApiModelProperty(value = "用戶id")
    @Schema(description = "用戶id")
    @TableField("user_id")
    private Long userId;

//    @ApiModelProperty(value = "審查模板id")
    @Schema(description = "審查模板id")
    @TableField("process_template_id")
    private Long processTemplateId;

//    @ApiModelProperty(value = "審查類型id")
    @Schema(description = "審查類型id")
    @TableField("process_type_id")
    private Long processTypeId;

//    @ApiModelProperty(value = "標題")
    @Schema(description = "標題")
    @TableField("title")
    private String title;

//    @ApiModelProperty(value = "描述")
    @Schema(description = "描述")
    @TableField("description")
    private String description;

//    @ApiModelProperty(value = "表單值")
    @Schema(description = "表單值")
    @TableField("form_values")
    private String formValues;

//    @ApiModelProperty(value = "流程實例id")
    @Schema(description = "流程實例id")
    @TableField("process_instance_id")
    private String processInstanceId;

//    @ApiModelProperty(value = "當前審查人")
    @Schema(description = "當前審查人")
    @TableField("current_auditor")
    private String currentAuditor;

//    @ApiModelProperty(value = "狀態 (0:默認 1:審查中 2:審查通過 -1:駁回)")
    @Schema(description = "狀態 (0:默認 1:審查中 2:審查通過 -1:駁回)")
    private Integer status;
}
