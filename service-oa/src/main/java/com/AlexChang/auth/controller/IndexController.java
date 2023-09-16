package com.AlexChang.auth.controller;

import com.AlexChang.common.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:IndexController
 * Description:
 *
 * @author Alex
 * @create 2023-09-16 下午 09:26
 * @Version:1.0
 */

@Tag(name = "後臺登入管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    //Login
    @PostMapping("login")
    public Result login(){

        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");

        return Result.ok(map);
    }

    //info
    @GetMapping("info")
    public Result info(){
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
