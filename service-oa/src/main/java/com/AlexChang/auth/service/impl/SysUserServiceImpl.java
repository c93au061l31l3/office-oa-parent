package com.AlexChang.auth.service.impl;

import com.AlexChang.auth.mapper.SysUserMapper;
import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.model.system.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    @Override
    public void updateStatus(Long id, Integer status) {
        //根據userid查詢對象
        SysUser sysUser = baseMapper.selectById(id);

        //設置修改狀態值
        sysUser.setStatus(status);

        //調用方法進行修改
        baseMapper.updateById(sysUser);
    }

    @Override
    public SysUser getUserByName(String username) {

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = baseMapper.selectOne(wrapper);

        return sysUser;
    }
}
