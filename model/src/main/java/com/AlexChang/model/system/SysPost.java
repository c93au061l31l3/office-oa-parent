package com.AlexChang.model.system;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:SysPost
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 上午 11:14
 * @Version:1.0
 */

@Data
@ApiModel(description = "職位")
@TableName("sys_post")
public class SysPost extends BaseEntity {

    private static final long serialVersion = 1L;

    @ApiModelProperty(value = "職位編碼")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty(value = "職位名稱")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "顯示順序")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "狀態(1正常 0停用)")
    @TableField("status")
    private Integer status;

}
