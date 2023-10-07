package com.AlexChang.auth.service.impl;

import com.AlexChang.auth.mapper.SysUserMapper;
import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * ClassName:SysUserServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-10-06 下午 08:29
 * @Version:1.0
 */

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
