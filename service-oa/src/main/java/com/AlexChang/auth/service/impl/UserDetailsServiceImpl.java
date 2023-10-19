package com.AlexChang.auth.service.impl;

import com.AlexChang.auth.service.SysMenuService;
import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.model.system.SysUser;
import com.AlexChang.security.custom.CustomUser;
import com.AlexChang.security.custom.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:UserDetailsServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-10-14 下午 07:36
 * @Version:1.0
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根據用戶名進行查詢
        SysUser sysUser = sysUserService.getUserByName(username);

        if(null == sysUser) {
            throw new UsernameNotFoundException("用戶名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("帳號已停用");
        }

        //根據用戶id查詢用戶操作權限
        List<String> userPermsList = sysMenuService.findUserPermsByUserId(sysUser.getId());
        //創建List集合用於封裝最終的權限數據
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        //遍歷
        for(String perms : userPermsList){
            authList.add(new SimpleGrantedAuthority(perms.trim()));
        }

        return new CustomUser(sysUser, authList);
    }
}
