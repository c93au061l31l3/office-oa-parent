package com.AlexChang.vo.system;


import lombok.Data;

import java.util.List;

/**
 * ClassName:AssginMenuVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 01:14
 * @Version:1.0
 */

@Data
//@ApiModel(description = "分配菜單")
public class AssignMenuVo {

    //@ApiModelProperty(value = "角色id")
    private Long roleId;

    //@ApiModelProperty(value = "菜單id列表")
    private List<Long> menuIdList;
}
