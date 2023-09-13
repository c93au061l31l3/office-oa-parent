package com.AlexChang.vo.process;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * ClassName:ProcessVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 01:01
 * @Version:1.0
 */

@Data
@ApiModel(description = "Process")
public class ProcessVo {

    private Long id;

    private Date createTime;

    @ApiModelProperty(value = "審查code")
    private String processCode;

    @ApiModelProperty(value = "用戶id")
    private Long userId;
    private String name;

    @TableField("process_template_id")
    private Long processTemplateId;
    private String processTemplateName;

    @ApiModelProperty(value = "審查類型id")
    private Long processTypeId;
    private String processTypeName;

    @ApiModelProperty(value = "標題")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "表單屬性")
    private String formProps;

    @ApiModelProperty(value = "表單選項")
    private String formOptions;

    @ApiModelProperty(value = "表單屬性值")
    private String formValues;

    @ApiModelProperty(value = "流程案例id")
    private String processInstanceId;

    @ApiModelProperty(value = "當前審查人")
    private String currentAuditor;

    @ApiModelProperty(value = "狀態(0:默認 1:審查中 2:審查通過 -1:駁回)")
    private Integer status;


    private String taskId;


}
