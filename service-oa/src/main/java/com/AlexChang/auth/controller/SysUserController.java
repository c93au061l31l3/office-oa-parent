package com.AlexChang.auth.controller;

import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.common.result.Result;
import com.AlexChang.common.utils.MD5;
import com.AlexChang.model.system.SysUser;
import com.AlexChang.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName:SysUserController
 * Description:
 *
 * @author Alex
 * @create 2023-10-06 下午 08:23
 * @Version:1.0
 */

@Tag(name = "用戶管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
@CrossOrigin
public class SysUserController {

    @Autowired
    private SysUserService service;

    //用戶的條件分頁查詢
    @Operation(summary = "用戶的條件分頁查詢")
    @GetMapping("/{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        @ParameterObject SysUserQueryVo sysUserQueryVo){

        //创建page对象
        Page<SysUser> pageParam = new Page<>(page,limit);

        //封装条件，判断条件值不为空
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        //获取条件值
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        //判断条件值不为空
        //like 模糊查询
        if(!StringUtils.isEmpty(username)) {
            wrapper.like(SysUser::getUsername,username);
        }
        //ge 大于等于
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge(SysUser::getCreateTime,createTimeBegin);
        }
        //le 小于等于
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le(SysUser::getCreateTime,createTimeEnd);
        }

        //调用mp的方法实现条件分页查询
        IPage<SysUser> pageModel = service.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @Operation(summary = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = service.getById(id);
        return Result.ok(user);
    }

    @Operation(summary = "保存用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user) {
        //密码进行加密，使用MD5
        String passwordMD5 = MD5.encrypt(user.getPassword());
        user.setPassword(passwordMD5);

//        String password = user.getPassword();
//        user.setPassword(password);

        boolean save = service.save(user);
        if(save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @Operation(summary = "更新用户")
    @PutMapping("update")
    public Result updateById(@RequestBody SysUser user) {
        service.updateById(user);
        return Result.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        service.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        service.updateStatus(id,status);
        return Result.ok();
    }

    /**
     * 獲取當前用戶基本信息
     * @return
     */
    @GetMapping("/getCurrentUser")
    public Result getCurrentUser(){

        Map<String,Object> map = service.getCurrentUser();

        return Result.ok(map);
    }

}
