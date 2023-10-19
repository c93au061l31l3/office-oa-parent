package com.AlexChang.security.filter;

import com.AlexChang.common.jwt.JwtHelper;
import com.AlexChang.common.result.ResponseUtil;
import com.AlexChang.common.result.Result;
import com.AlexChang.common.result.ResultCodeEnum;
import com.AlexChang.security.custom.CustomUser;
import com.AlexChang.vo.system.LoginVo;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:TokenLoginFilter
 * Description:
 *
 * @author Alex
 * @create 2023-10-14 下午 08:00
 * @Version:1.0
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    //構造方法
    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate){

        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher
                (new AntPathRequestMatcher("/admin/system/index/login","POST"));
        this.redisTemplate = redisTemplate;
    }

    //登入認證
    //獲取輸入的用戶名或密碼，調用認證方法
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException{

        try {
            //獲取用戶信息
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            //封裝成對象
            Authentication authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            //調用方法完成認證
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //認證成功後調用方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        //獲取當前用戶
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        //生成token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        //獲取當前用戶的權限數據，放到redis裡，key:username  value:權限數據
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        //返回
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        ResponseUtil.out(response, Result.ok(map));
    }

    //認證失敗要調用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, Result.build(null,ResultCodeEnum.LOGIN_ERROR));
    }
}
