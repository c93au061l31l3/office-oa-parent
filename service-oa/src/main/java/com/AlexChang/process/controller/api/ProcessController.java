package com.AlexChang.process.controller.api;

import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.common.result.Result;
import com.AlexChang.model.process.Process;
import com.AlexChang.model.process.ProcessTemplate;
import com.AlexChang.model.process.ProcessType;
import com.AlexChang.process.service.OaProcessService;
import com.AlexChang.process.service.OaProcessTemplateService;
import com.AlexChang.process.service.OaProcessTypeService;
import com.AlexChang.vo.process.ApprovalVo;
import com.AlexChang.vo.process.ProcessFormVo;
import com.AlexChang.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ProcessController
 * Description:
 *
 * @author Alex
 * @create 2023-11-08 下午 07:27
 * @Version:1.0
 */

@RestController
@RequestMapping(value = "/admin/process")
@Tag(name = "審批流程管理")
@CrossOrigin //跨域
public class ProcessController {

    @Autowired
    private OaProcessTypeService processTypeService;

    @Autowired
    private OaProcessTemplateService processTemplateService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private OaProcessService processService;

    /**
     * 查詢所有審批分類&每個分類所有的審批模板
     * @return
     */
    @GetMapping("/findProcessType")
    public Result findProcessType(){

        List<ProcessType> list = processTypeService.findProcessType();
        return Result.ok(list);
    }

    /**
     * 獲取審批模板數據
     * @return
     */
    @GetMapping("/getProcessTemplate/{processTemplateId}")
    public Result getProcessTemplate(@PathVariable Long processTemplateId){

        ProcessTemplate processTemplate = processTemplateService.getById(processTemplateId);
        return Result.ok(processTemplate);
    }

    /**
     * 啟動流程
     * @return
     */
    @PostMapping("/startUp")
    public Result startUp(@RequestBody ProcessFormVo formVo){

        processService.startUp(formVo);
        return Result.ok();
    }

    /**
     * 待處理
     * @return
     */
    @GetMapping("/findPending/{page}/{limit}")
    public Result findPending(@PathVariable Long page,@PathVariable Long limit){

        Page<Process> pageParam = new Page<>();

        return Result.ok(processService.findPending(pageParam));
    }

    /**
     * 查看審批祥情
     * @return
     */
    @GetMapping("/show/{id}")
    public Result show(@PathVariable Long id){

        Map<String,Object> map = processService.show(id);

        return Result.ok(map);
    }

    /**
     * 審批
     * @return
     */
    @PostMapping("/approve")
    public Result approve( @RequestBody ApprovalVo approvalVo){

        processService.approve(approvalVo);

        return Result.ok();
    }

    /**
     * 查詢已處理過的任務
     * @return
     */
    @GetMapping("/findProcessed/{page}/{limit}")
    public Result findProcessed(@PathVariable Long page,@PathVariable Long limit){

        Page<Process> pageParam = new Page<>(page,limit);
        IPage<ProcessVo> pageModel = processService.findProcessed(pageParam);

        return Result.ok(pageModel);
    }

    /**
     * 查詢已發起的任務
     * @return
     */
    @GetMapping("/findStarted/{page}/{limit}")
    public Result findStarted(@PathVariable Long page, @PathVariable Long limit){

        Page<ProcessVo> pageParam = new Page<>(page,limit);
        IPage<ProcessVo> pageModel = processService.findStarted(pageParam);

        return Result.ok(pageModel);
    }



}
