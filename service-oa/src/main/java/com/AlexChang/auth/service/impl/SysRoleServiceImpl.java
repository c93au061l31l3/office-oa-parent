package com.AlexChang.auth.service.impl;

import com.AlexChang.auth.mapper.SysRoleMapper;
import com.AlexChang.auth.service.SysRoleService;
import com.AlexChang.auth.service.SysUserRoleService;
import com.AlexChang.model.system.SysRole;
import com.AlexChang.model.system.SysUserRole;
import com.AlexChang.vo.system.AssignRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName:SysRoleServiceImpl
 * Description:
 *
 * @author Alex
 * @create 2023-09-14 上午 10:13
 * @Version:1.0
 */

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    //1.查詢所有腳色 和 當前用戶所屬腳色
    @Override
    public Map<String, Object> findRoleDateByUserId(Long userId) {

        //1.查詢所有腳色，返回list集合
        List<SysRole> allRoleList = baseMapper.selectList(null); //baseMapper就會自湊注入mapper

        //2.根據userid查詢，腳色用戶關係表，查詢userid對應的所有角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,userId);
        List<SysUserRole> existUserRoleList = sysUserRoleService.list(wrapper);
        //從查詢出來的用戶id對應的list集合中(existUserRoleList)，獲取所有角色id
        List<Long> existUserRoleIDList = existUserRoleList.stream().map(c -> c.getRoleId()).collect(Collectors.toList());
//        上述代碼等同
//        List<Long> list = new ArrayList<>();
//        for (SysUserRole sysUserRole:existUserRoleList) {
//            Long roleId = sysUserRole.getRoleId();
//            list.add(roleId);
//        }

        //3.根據查詢所有角色id，找到對應角色信息
        //根據角色id到所有角色的list集合進行比較
        List<SysRole> assignRoleList = new ArrayList<>();
        for(SysRole sysRole : allRoleList){
            //比較
            if(existUserRoleIDList.contains(sysRole.getId())){
                assignRoleList.add(sysRole);
            }
        }

        //4.把得到的兩部分數據分裝到map集合，並返回
        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList",assignRoleList);
        roleMap.put("allRolesList",allRoleList);

        return roleMap;
    }

    //2.為用戶分配角色
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        //把用户之前分配角色数据删除，用户角色关系表里面，根据userid删除
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,assignRoleVo.getUserId());
        sysUserRoleService.remove(wrapper);

        //重新进行分配
        List<Long> roleIdList = assignRoleVo.getRoleIdList();
        for(Long roleId:roleIdList) {
            if(StringUtils.isEmpty(roleId)) {
                continue;
            }
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assignRoleVo.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleService.save(sysUserRole);
        }
    }
}
