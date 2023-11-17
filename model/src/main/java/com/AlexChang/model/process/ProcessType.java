package com.AlexChang.model.process;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * ClassName:ProcessType
 * Description:
 *
 * @author Alex
 * @create 2023-09-11 下午 07:49
 * @Version:1.0
 */
@Data
//@ApiModel(description = "ProcessType")
@Schema(description = "ProcessType")
@TableName("oa_process_type")
public class ProcessType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "类型名称")
    @Schema(description = "类型名称")
    @TableField("name")
    private String name;

    //@ApiModelProperty(value = "描述")
    @Schema(description = "描述")
    @TableField("description")
    private String description;

    @TableField(exist = false)
    private List<ProcessTemplate> processTemplateList;
}
