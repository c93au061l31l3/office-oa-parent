package com.AlexChang.vo.system;

import lombok.Data;

/**
 * ClassName:MetaVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 01:28
 * @Version:1.0
 */

//路由顯示信息
@Data
public class MetaVo {


    private String title; //設置該路由在側邊攔和麵包屑中展示的名字


    private String icon; //設置該路由的圖標，對應路徑src/assets/icons/svg

    public MetaVo(){}

    public MetaVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }
}
