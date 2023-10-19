package com.AlexChang.security.custom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * ClassName:UserDetailsService
 * Description:
 *
 * @author Alex
 * @create 2023-10-14 下午 07:20
 * @Version:1.0
 */
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService{

    /**
     * 根据用户名获取用户对象（获取不到直接抛异常）
     */
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
