package com.AlexChang.process.controller;

import com.AlexChang.common.result.Result;
import com.AlexChang.process.service.OaProcessService;
import com.AlexChang.vo.process.ProcessQueryVo;
import com.AlexChang.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:OaProcessController
 * Description:
 *
 * @author Alex
 * @create 2023-11-08 下午 12:52
 * @Version:1.0
 */

@RestController
@RequestMapping(value = "/admin/process")
public class OaProcessController {

    @Autowired
    private OaProcessService oaProcessService;

    //審批管理列表
    @Operation(summary = "獲取分頁列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        ProcessQueryVo processQueryVo){

        Page<ProcessVo> pageParam = new Page<>(page,limit);
        IPage<ProcessVo> pageModel = oaProcessService.selectPage(pageParam,processQueryVo);

        return Result.ok(pageModel);
    }


}
