package com.AlexChang.auth;

import com.AlexChang.auth.mapper.SysRoleMapper;
import com.AlexChang.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * ClassName:TestDemo01
 * Description:
 *
 * @author Alex
 * @create 2023-09-13 下午 07:19
 * @Version:1.0
 */

@SpringBootTest
public class TestDemo01 {

    @Autowired
    private SysRoleMapper mapper;

    @Test
    public void getAll(){
        List<SysRole> sysRoles = mapper.selectList(null);
        System.out.println(sysRoles);
    }

    @Test
    public void add(){

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("Gura");
        sysRole.setRoleCode("role");
        sysRole.setDescription("admin");

        int insert = mapper.insert(sysRole);
        System.out.println(sysRole.getId()); //這邊會回填自動賦予的id值

    }

    @Test
    public void update(){

        //根據id查詢
        SysRole sysRole = mapper.selectById(13);

        //設置修改值
        sysRole.setRoleName("Juliaaa");

        //調用方法實現修改
        int i = mapper.updateById(sysRole);
    }

    //條件查詢QueryWrapper
    @Test
    public void testQuery1(){

        //創建QueryWrapper對象，調用方法封裝條件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","admin");

        //調用mp方法實現操作
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }

    //條件查詢LambdaQueryWrapper
    @Test
    public void testQuery2(){

        //創建LambdaQueryWrapper對象，調用方法封裝條件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName,"Hitomi");

        //調用mp方法實現操作
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }


    @Test
    public void deleteId(){
        int i = mapper.deleteById(13);
    }

}
