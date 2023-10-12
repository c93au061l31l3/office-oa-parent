package com.AlexChang.auth.utils;

import com.AlexChang.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:MenuHelper
 * Description:
 *
 * @author Alex
 * @create 2023-10-10 下午 08:08
 * @Version:1.0
 */
public class MenuHelper {

    //使用遞迴方式構建菜單
    public static List<SysMenu> buildTree(List<SysMenu> sysMenList){

        //創建List集合，用於最終數據
        List<SysMenu> trees = new ArrayList<>();

        //把所有菜單數據遍歷
        for(SysMenu sysMenu : sysMenList){
            //遞歸入口進入，parentId = 0 就是入口
            if(sysMenu.getParentId().longValue() == 0){
                trees.add(getChildren(sysMenu,sysMenList));
            }
        }
        return trees;
    }

    public static SysMenu getChildren(SysMenu sysMenu,List<SysMenu> sysMenuList){
        sysMenu.setChildren(new ArrayList<>());

        //遍歷所有菜單數據，判斷id與parentId的對應關係
        for(SysMenu it : sysMenuList){
            if(sysMenu.getId().longValue() == it.getParentId().longValue()){
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(getChildren(it,sysMenuList));
            }
        }
        return sysMenu;
    }
}
