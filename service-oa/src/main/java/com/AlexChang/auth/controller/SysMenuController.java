package com.AlexChang.auth.controller;

import com.AlexChang.auth.service.SysMenuService;
import com.AlexChang.common.result.Result;
import com.AlexChang.model.system.SysMenu;
import com.AlexChang.vo.system.AssignMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:SysMenuController
 * Description:
 *
 * @author Alex
 * @create 2023-10-10 下午 04:41
 * @Version:1.0
 */

@Tag(name = "菜單管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //菜單列表接口
    @Operation(summary =  "菜單列表")
    @GetMapping("/findNodes")
    public Result findNodes(){

        List<SysMenu> list = sysMenuService.findNodes();

        return Result.ok(list);
    }

    @Operation(summary =  "新增菜單")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @Operation(summary =  "修改菜單")
    @PutMapping("update")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @Operation(summary =  "刪除菜單")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysMenuService.removeMenuById(id);

        return Result.ok();
    }

    //查詢所有菜單和角色分配的菜單
    @Operation(summary =  "查詢所有菜單和角色分配的菜單")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){

        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);

        return Result.ok(list);
    }

    //角色分配菜單
    @Operation(summary =  "角色分配菜單")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignMenuVo assignMenuVo){

        sysMenuService.doAssign(assignMenuVo);

        return Result.ok();
    }
}
