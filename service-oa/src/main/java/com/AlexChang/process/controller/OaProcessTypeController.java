package com.AlexChang.process.controller;

import com.AlexChang.common.result.Result;
import com.AlexChang.model.process.ProcessType;
import com.AlexChang.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:OaProcessTypeController
 * Description:
 *
 * @author Alex
 * @create 2023-10-20 下午 05:01
 * @Version:1.0
 */

@RestController
@RequestMapping("/admin/process/processType")
public class OaProcessTypeController {

    @Autowired
    private OaProcessTypeService processTypeService;

    @Operation(summary =  "獲取分頁列表")
    @GetMapping("/{page}/{limit}")
    @PreAuthorize("hasAuthority('bnt.processType.list')")
    public Result index(@PathVariable Long page,@PathVariable Long limit){

        Page<ProcessType> pageParam = new Page<>(page,limit);
        IPage<ProcessType> pageModel = processTypeService.page(pageParam);

        return Result.ok(pageModel);
    }

    @Operation(summary = "獲取")
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('bnt.processType.list')")
    public Result get(@PathVariable Long id) {
        ProcessType processType = processTypeService.getById(id);
        return Result.ok(processType);
    }

    @Operation(summary = "新增")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('bnt.processType.add')")
    public Result save(@RequestBody ProcessType processType) {
        processTypeService.save(processType);
        return Result.ok();
    }

    @Operation(summary = "修改")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('bnt.processType.update')")
    public Result updateById(@RequestBody ProcessType processType) {
        processTypeService.updateById(processType);
        return Result.ok();
    }

    @Operation(summary = "刪除")
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAuthority('bnt.processType.remove')")
    public Result remove(@PathVariable Long id) {
        processTypeService.removeById(id);
        return Result.ok();
    }

    //查詢所有審批分類
    @GetMapping("/findAll")
    public Result findAll(){
        List<ProcessType> list = processTypeService.list();
        return Result.ok(list);
    }



}
