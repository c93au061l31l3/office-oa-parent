package com.AlexChang.model.process;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:ProcessTemplate
 * Description:
 *
 * @author Alex
 * @create 2023-09-11 下午 07:37
 * @Version:1.0
 */

@Data
@ApiModel(description = "ProcessTemplate")
@TableName("oa_process_template")
public class ProcessTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板名稱")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "圖標路徑")
    @TableField("icon_url")
    private String iconUrl;

    @ApiModelProperty(value = "processTypeId")
    @TableField("process_type_id")
    private Long processTypeId;

    @ApiModelProperty(value = "表單屬性")
    @TableField("form_props")
    private String formProps;

    @ApiModelProperty(value = "表單選項")
    @TableField("form_options")
    private String formOptions;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "流程定義key")
    @TableField("process_definition_key")
    private String processDefinitionKey;

    @ApiModelProperty(value = "流程定義上船路徑process_model_id")
    @TableField("process_definition_path")
    private String processDefinitionPath;

    @ApiModelProperty(value = "流程定義模型id")
    @TableField("process_model_id")
    private String processModelId;

    @ApiModelProperty(value = "狀態")
    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private String processTypeName;
}
