package com.AlexChang.vo.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:ProcessForm
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 12:54
 * @Version:1.0
 */

@Data
@ApiModel(description = "流程表單")
public class ProcessFormVo {

    @ApiModelProperty(value = "審查模板id")
    private Long processTemplateId;

    @ApiModelProperty(value = "審查類型id")
    private Long processTypeId;

    @ApiModelProperty(value = "表單值")
    private String formValues;

}
