package com.AlexChang.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:SysPostQueryVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 04:53
 * @Version:1.0
 */

@Data
public class SysPostQueryVo {

    @ApiModelProperty(value = "職位編碼")
    private String postCode;

    @ApiModelProperty(value = "職位名稱")
    private String name;

    @ApiModelProperty(value = "狀態(1正常 0停用)")
    private Boolean status;
}
