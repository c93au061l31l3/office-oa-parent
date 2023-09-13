package com.AlexChang.vo.process;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:ProcessQueryVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 12:56
 * @Version:1.0
 */

@Data
@ApiModel(description = "Process")
public class ProcessQueryVo {

    @ApiModelProperty(value = "關鍵值")
    private String keyword;

    @ApiModelProperty(value = "用戶id")
    private Long userId;

    @TableField("process_template_id")
    private Long processTemplateId;

    @ApiModelProperty(value = "審查類性id")
    private Long processTypeId;


    private String createTimeBegin;
    private String createTimeEnd;

    @ApiModelProperty(value = "狀態(0:默認 1:審查中 2:審查通過 -1:駁回)")
    private Integer status;
}
