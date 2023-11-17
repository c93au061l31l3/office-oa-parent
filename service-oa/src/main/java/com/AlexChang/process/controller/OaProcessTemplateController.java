package com.AlexChang.process.controller;

import com.AlexChang.common.result.Result;
import com.AlexChang.model.process.ProcessTemplate;
import com.AlexChang.model.process.ProcessType;
import com.AlexChang.process.service.OaProcessTemplateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:OaProcessTemplateController
 * Description:
 *
 * @author Alex
 * @create 2023-10-20 下午 05:01
 * @Version:1.0
 */

@RestController
@RequestMapping(value = "/admin/process/processTemplate")
public class OaProcessTemplateController {

    @Autowired
    private OaProcessTemplateService processTemplateService;

    //分頁查詢審批模板
    @Operation(summary =  "獲取分頁模板審批數據")
    @GetMapping("/{page}/{limit}")
    public Result index(@PathVariable Long page,@PathVariable Long limit){

        Page<ProcessTemplate> pageParam = new Page<>(page,limit);
        //分页查询审批模板，把审批类型对应名称查询
        IPage<ProcessTemplate> pageModel = processTemplateService.selectPageProcessTemplate(pageParam);

        return Result.ok(pageModel);
    }

    @Operation(summary = "獲取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        ProcessTemplate processTemplate = processTemplateService.getById(id);
        return Result.ok(processTemplate);
    }

    @Operation(summary = "新增")
    @PostMapping("save")
    public Result save(@RequestBody ProcessTemplate processTemplate) {
        processTemplateService.save(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @Operation(summary = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody ProcessTemplate processTemplate) {
        processTemplateService.updateById(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.remove')")
    @Operation(summary = "刪除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        processTemplateService.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "上傳流程定義")
    @PostMapping("/uploadProcessDefinition")
    public Result uploadProcessDefinition(MultipartFile file) throws FileNotFoundException {

        //獲取class路徑
        String path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
        //設置上傳的文件夾
        File tempFile = new File(path + "/processes/");
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }
        //創建空文件，實現文件寫入
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        File zipFile = new File(path + "/processes/" + filename);
        //保存文件
        try {
            file.transferTo(zipFile);
        } catch (IOException e) {
            return Result.fail();
        }
        //根據上傳地址後續部屬流程定義，文件名稱為定義的默認key
        Map<String,Object> map = new HashMap<>();
        map.put("processDefinitionPath","processes/" + filename);
        map.put("processDefinitionKey",filename.substring(0,filename.lastIndexOf(".")));

        return Result.ok(map);
    }

    //部屬流程定義
    @PreAuthorize("hasAuthority('bnt.processTemplate.publish')")
    @Operation(summary = "發布")
    @GetMapping("/publish/{id}")
    public Result publish(@PathVariable Long id) {

        //修改模板發布狀態，1:代表已經發布
        //流程定義部屬
        processTemplateService.publish(id);

        return Result.ok();
    }

}
