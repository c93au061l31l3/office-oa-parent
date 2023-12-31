package com.AlexChang.model.system;

import com.AlexChang.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * ClassName:SysMenu
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 上午 10:52
 * @Version:1.0
 */
@Data
//@ApiModel(description = "菜單")
@Schema(description = "菜單")
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "所屬上級")
    @Schema(description = "所屬上級")
    @TableField("parent_id")
    private Long parentId;

    //@ApiModelProperty(value = "名稱")
    @Schema(description = "名稱")
    @TableField("name")
    private String name;

    //@ApiModelProperty(value = "類型(1:菜單，2:按鈕)")
    @Schema(description = "類型(1:菜單，2:按鈕)")
    @TableField("type")
    private Integer type;

    //@ApiModelProperty(value = "路由地址")
    @Schema(description = "路由地址")
    @TableField("path")
    private String path;

    //@ApiModelProperty(value = "組件路徑")
    @Schema(description = "組件路徑")
    @TableField("component")
    private String component;

    //@ApiModelProperty(value = "權限標示")
    @Schema(description = "權限標示")
    @TableField("perms")
    private String perms;

    //@ApiModelProperty(value = "圖標")
    @Schema(description = "圖標")
    @TableField("icon")
    private String icon;

    //@ApiModelProperty(value = "排序")
    @Schema(description = "排序")
    @TableField("sort_value")
    private Integer sortValue;

    //@ApiModelProperty(value = "狀態(0:禁止，1:正常)")
    @Schema(description = "狀態(0:禁止，1:正常)")
    @TableField("status")
    private Integer status;

    //下級列表
    @TableField(exist = false)
    private List<SysMenu> children;

    //是否選中
    @TableField(exist = false)
    private boolean isSelect;
}
