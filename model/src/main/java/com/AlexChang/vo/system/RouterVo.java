package com.AlexChang.vo.system;

import java.util.List;

/**
 * ClassName:RouterVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 01:53
 * @Version:1.0
 */

//路由配置信息
public class RouterVo {

    //路由名字
    //private String name;

    //路由地址
    private String path;

    //是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
    private Boolean hidden;

    //组件地址
    private String component;

    //当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
    private Boolean alwaysShow;

    //其他元素
    private MetaVo meta;

    //子路由
    private List<RouterVo> children;
}
