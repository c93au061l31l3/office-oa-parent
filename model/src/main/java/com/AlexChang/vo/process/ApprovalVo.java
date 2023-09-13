package com.AlexChang.vo.process;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:ApprovalVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 12:52
 * @Version:1.0
 */

@Data
public class ApprovalVo {

    private Long processId;

    private String taskId;

    @ApiModelProperty(value = "狀態")
    private Integer status;

    @ApiModelProperty(value = "審查描述")
    private String description;
}
