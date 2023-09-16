package com.AlexChang.auth.controller;

import com.AlexChang.auth.service.SysRoleService;

import com.AlexChang.common.config.exception.CustException;
import com.AlexChang.common.result.Result;
import com.AlexChang.model.system.SysRole;

import com.AlexChang.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:SysRoleController
 * Description:
 *
 * @author Alex
 * @create 2023-09-14 上午 10:29
 * @Version:1.0
 */

@Tag(name = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    //注入service
    @Autowired
    private SysRoleService sysRoleService;

    //查詢所有角色
//    @GetMapping("/findAll")
//    public List<SysRole> findAll(){
//
//        List<SysRole> list = sysRoleService.list();
//        return list;
//    }

    @Operation(summary = "查詢所有角色")
    @GetMapping("/findAll")
    public Result findAll(){

        List<SysRole> list = sysRoleService.list();

        try {
            int i = 1/0;
        }catch (Exception e){
            throw new CustException(20001,"自訂義異常處理...");
        }

        //統一返回數據結果
        return Result.ok(list);
    }

    //條件分頁查詢
    @Operation(summary = "條件分頁查詢")
    @GetMapping("/{page}/{limit}") //page:當前頁，limit:每頁顯示數，SysRoleQueryVo:條件對象
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo){
        //調用service的方法實現
        //1.創建page對象，傳遞分頁參數
        Page<SysRole> pageParam = new Page<>(page,limit);

        //2.封裝條件，判斷條件是否為空
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            //like:模糊查詢
            wrapper.like(SysRole::getRoleName,roleName);
        }

        //3.調用方法實現
        IPage<SysRole> pageModel1 = sysRoleService.page(pageParam, wrapper);

        return Result.ok(pageModel1);
    }

    //添加角色
    @Operation(summary = "添加角色")
    @PostMapping("/save")
    public Result save(@RequestBody SysRole role){

        //調用service方法
        boolean is_success = sysRoleService.save(role);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //根據id修改角色
    @Operation(summary = "根據id修改角色")
    @PostMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    //修改角色-完整版
    @Operation(summary = "修改角色")
    @PutMapping("/update")
    public Result update(@PathVariable SysRole role){
        boolean is_success = sysRoleService.updateById(role);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //根據id刪除
    @Operation(summary = "根據id刪除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean is_success = sysRoleService.removeById(id);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //批量刪除
    @Operation(summary = "批量刪除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean is_success = sysRoleService.removeByIds(idList);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

}
