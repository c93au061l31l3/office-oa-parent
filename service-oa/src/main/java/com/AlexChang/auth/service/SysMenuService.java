package com.AlexChang.auth.service;

import com.AlexChang.model.system.SysMenu;
import com.AlexChang.vo.system.AssignMenuVo;
import com.AlexChang.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * ClassName:SysMenuService
 * Description:
 *
 * @author Alex
 * @create 2023-10-10 下午 04:49
 * @Version:1.0
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    //刪除菜單
    void removeMenuById(Long id);

    List<SysMenu> findMenuByRoleId(Long roleId);

    void doAssign(AssignMenuVo assignMenuVo);

    List<RouterVo> findUserMenuListByUserId(Long userId);

    List<String> findUserPermsByUserId(Long userId);
}
