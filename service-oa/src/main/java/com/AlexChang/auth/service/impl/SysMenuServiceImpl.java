package com.AlexChang.auth.service.impl;

import com.AlexChang.auth.mapper.SysMenuMapper;
import com.AlexChang.auth.service.SysMenuService;
import com.AlexChang.auth.service.SysRoleMenuService;
import com.AlexChang.auth.service.SysRoleService;
import com.AlexChang.auth.utils.MenuHelper;
import com.AlexChang.common.exception.CustException;
import com.AlexChang.model.system.SysMenu;
import com.AlexChang.model.system.SysRoleMenu;
import com.AlexChang.vo.system.AssignMenuVo;
import com.AlexChang.vo.system.MetaVo;
import com.AlexChang.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:SysMenuServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-10-10 下午 04:51
 * @Version:1.0
 */

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    //菜單列表接口
    @Override
    public List<SysMenu> findNodes() {

        //1查詢所有燦單數據
        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        //2構建成樹型結構
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);

        return sysMenus;
    }

    //刪除菜單
    @Override
    public void removeMenuById(Long id) {

        //判斷當前菜單是否有子菜單
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId,id);
        Long count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new CustException(201,"菜單不能刪除");
        }
        baseMapper.deleteById(id);
    }

    //查詢所有菜單和角色分配的單
    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {

        //1查詢所有菜單，條件為status=1
        LambdaQueryWrapper<SysMenu> wrapperSysMenu = new LambdaQueryWrapper<>();
        wrapperSysMenu.eq(SysMenu::getStatus,1);
        List<SysMenu> allSysMenuList = baseMapper.selectList(wrapperSysMenu);

        //2根據角色id(role id)查詢對應的菜單id
        LambdaQueryWrapper<SysRoleMenu> wrapperSysRoleMenu = new LambdaQueryWrapper<>();
        wrapperSysRoleMenu.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(wrapperSysRoleMenu);

        //3根據獲取的菜單id，獲取對應的菜單對象
        List<Long> MenuIdList = sysRoleMenuList.stream().map(c -> c.getMenuId()).collect(Collectors.toList());

        //3.1 拿着菜单id 和所有菜单集合里面id进行比较，如果相同封装
        allSysMenuList.stream().forEach(item -> {
            if(MenuIdList.contains(item.getId())){
                item.setSelect(true);
            }else{
                item.setSelect(false);
            }
        });

        //4返回樹形遞歸格式菜單列表
        List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);

        return sysMenuList;
    }

    //角色分配菜單
    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {

        //1根據角色id 刪除角色菜單表 分配數據
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId());
        sysRoleMenuService.remove(wrapper);

        //2从参数里面获取角色新分配菜单id列表，进行遍历，把每个id数据添加菜单角色表
        List<Long> menuIdList = assignMenuVo.getMenuIdList();
        for(Long menuId : menuIdList){
            if(StringUtils.isEmpty(menuId)) {
                continue;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenuService.save(sysRoleMenu);
        }

    }

    //根據用戶id獲取用戶可以操作的表單列表
    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {

        List<SysMenu> sysMenuList = null;

        //1.判斷當前用戶是否為管理員 userId=1
        //1.1如果是管理員，查詢所有列表
        if(userId.longValue()==1){
            //查詢所有菜單列表
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getStatus,1);
            wrapper.orderByAsc(SysMenu::getSortValue);
            sysMenuList = baseMapper.selectList(wrapper);
        }else{
            //1.2如果不是管理員，根據userId查詢可以操作的菜單
            //多表關聯查詢 : 用戶角色關係表，角色菜單關係表，菜單表
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }

        //2.把查詢出來的數據列表構建成框架要求的路由數據結構
        //使用菜单操作工具类构建树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        //构建成框架要求的路由结构
        List<RouterVo> routerList = this.buildRouter(sysMenuTreeList);

        return routerList;
    }

    //構建成框架要求的路由結構
    private List<RouterVo> buildRouter(List<SysMenu> menus) {

        //1.創建List集合，存儲最終數據
        List<RouterVo> routers = new ArrayList<>();

        //2.menu遍歷
        for(SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            //下一层数据部分
            List<SysMenu> children = menu.getChildren();
            if(menu.getType().intValue() == 1) { //2
                //加载出来下面隐藏路由
                List<SysMenu> hiddenMenuList = children.stream()
                        .filter(item -> !StringUtils.isEmpty(item.getComponent()))
                        .collect(Collectors.toList());
                for(SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    //true 隐藏路由
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));

                    routers.add(hiddenRouter);
                }

            } else {
                if(!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    //递归
                    router.setChildren(buildRouter(children));
                }
            }
            routers.add(router);
        }
        return routers;

    }

    //根據用戶id獲得用戶可以操作的按鈕列表
    @Override
    public List<String> findUserPermsByUserId(Long userId) {

        //1.判斷是否是管理員，如果是管理員，查詢所有按鈕列表
        List<SysMenu> sysMenuList = null;
        if(userId.longValue() == 1){
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getStatus,1);
            sysMenuList = baseMapper.selectList(wrapper);
        }else {
            //2.如果不是管理員，根據userId查詢可以操作的按鈕列表
            //多表關聯查詢: 用戶角色關係表，角色菜單關係表，菜單表
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }

        //3.從查詢出來的數據中獲取可以操作的按鈕值的List集合
        List<String> permsList = sysMenuList.stream()
                .filter(item -> item.getType() == 2)
                .map(item -> item.getPerms())
                .collect(Collectors.toList());

        return permsList;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }
}
