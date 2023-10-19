package com.AlexChang.auth.controller;

import com.AlexChang.auth.service.SysRoleService;

import com.AlexChang.common.exception.CustException;
import com.AlexChang.common.result.Result;
import com.AlexChang.model.system.SysRole;

import com.AlexChang.vo.system.AssignRoleVo;
import com.AlexChang.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

//        try {
//            int i = 1/0;
//        }catch (Exception e){
//            throw new CustException(20001,"自訂義異常處理...");
//        }

        //統一返回數據結果
        return Result.ok(list);
    }

    //條件分頁查詢
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @Operation(summary = "條件分頁查詢")
    @GetMapping("/{page}/{limit}") //page:當前頁，limit:每頁顯示數，SysRoleQueryVo:條件對象
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                //@ParameterObject
                                @ParameterObject SysRoleQueryVo sysRoleQueryVo){
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

        //3.調用Service方法實現
        IPage<SysRole> pageModel1 = sysRoleService.page(pageParam, wrapper);

        return Result.ok(pageModel1);
    }

    //添加角色
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @Operation(summary = "添加角色")
    @PostMapping(value = "/save")
    public Result save(@RequestBody SysRole role){ //@RequestBody : 參數通過請求體並以JSON格式傳入

        //調用service方法
        boolean is_success = sysRoleService.save(role);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //根據id查詢
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @Operation(summary = "根據id查詢")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    //修改角色-完整版
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @Operation(summary = "修改角色")
    @PutMapping("/update")
    public Result update(@RequestBody SysRole role){
        boolean is_success = sysRoleService.updateById(role);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //根據id刪除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
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
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @Operation(summary = "批量刪除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){  //前端用數組[]傳遞
        boolean is_success = sysRoleService.removeByIds(idList);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //1.查詢所有腳色 和 當前用戶所屬腳色
    @Operation(summary = "獲取角色")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String, Object> map = sysRoleService.findRoleDateByUserId(userId);
        return Result.ok(map);
    }
    //2.位用戶分配角色
    @Operation(summary = "位用戶分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignRoleVo assignRoleVo){
        sysRoleService.doAssign(assignRoleVo);
        return Result.ok();
    }

}
