package com.AlexChang.auth;

import com.AlexChang.auth.mapper.SysRoleMapper;
import com.AlexChang.auth.service.SysRoleService;
import com.AlexChang.auth.service.impl.SysRoleServiceImpl;
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
public class TestDemo02 {

    @Autowired
    private SysRoleService service;

    @Test
    public void getAll(){
        List<SysRole> list = service.list();
        System.out.println(list);
    }

    @Test
    public void add(){


    }

    @Test
    public void update(){

    }

    //條件查詢QueryWrapper
    @Test
    public void testQuery1(){

    }

    //條件查詢LambdaQueryWrapper
    @Test
    public void testQuery2(){


    }
}
