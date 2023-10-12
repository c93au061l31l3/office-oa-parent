package com.AlexChang.auth.controller;

import com.AlexChang.auth.service.SysMenuService;
import com.AlexChang.auth.service.SysUserService;
import com.AlexChang.common.exception.CustException;
import com.AlexChang.common.jwt.JwtHelper;
import com.AlexChang.common.result.Result;
import com.AlexChang.common.utils.MD5;
import com.AlexChang.model.system.SysUser;
import com.AlexChang.vo.system.LoginVo;
import com.AlexChang.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登入
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){

//        Map<String,Object> map = new HashMap<>();
//        map.put("token","admin-token");
//        return Result.ok(map);

        //1獲取輸入用戶的名字&密碼
        String username = loginVo.getUsername();

        //2根據用戶名查詢數據庫
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserService.getOne(wrapper);

        //3判斷用戶是否存在
        if(sysUser == null){
            throw new CustException(201,"用戶不存在");
        }

        //4判斷密碼
        //獲取數據庫裡的密碼
        String password_db = sysUser.getPassword();
        //獲取輸入的密碼
        String password_input = MD5.encrypt(loginVo.getPassword());
        if(!password_db.equals(password_input)){
            throw new CustException(201,"密碼錯誤");
        }

        //5判断用户是否被禁用  1 可用 0 禁用
        if(sysUser.getStatus().intValue() == 0){
            throw new CustException(201,"用戶已被禁用");
        }

        //6使用jwt根据用户id和用户名称生成token字符串
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());

        //7返回
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    /**
     * 獲取用戶資訊
     * @return
     */
    @GetMapping("info")
    public Result info(HttpServletRequest request){
        //1.从请求头获取用户信息（获取请求头token字符串）
        String token = request.getHeader("token");

        //2 从token字符串获取用户id 或者 用户名称
        Long userId = JwtHelper.getUserId(token);

        //3 根据用户id查询数据库，把用户信息获取出来
        SysUser sysUser = sysUserService.getById(userId);

        //4 根据用户id获取用户可以操作菜单列表
        //查询数据库动态构建路由结构，进行显示
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(userId);

        //5 根据用户id获取用户可以操作按钮列表
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);

        //6 返回相应的数据


        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name",sysUser.getName());
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");

        // 返回用户可以操作菜单
        map.put("routers",routerList);
        //返回用户可以操作按钮
        map.put("buttons",permsList);

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
