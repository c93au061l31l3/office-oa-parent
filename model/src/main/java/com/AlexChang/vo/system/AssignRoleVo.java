package com.AlexChang.vo.system;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * ClassName:AssginRoleVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 01:18
 * @Version:1.0
 */

@Data
//@ApiModel(description = "分配菜單")
@Schema(description = "分配菜單")
public class AssignRoleVo {

    //@ApiModelProperty(value = "用戶id")
    @Schema(description = "用戶id")
    private Long userId;

    //@ApiModelProperty(value = "角色id列表")
    @Schema(description = "角色id列表")
    private List<Long> roleIdList;
}
