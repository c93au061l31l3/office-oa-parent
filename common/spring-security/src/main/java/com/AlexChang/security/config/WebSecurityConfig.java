package com.AlexChang.security.config;

import com.AlexChang.security.custom.CustomMd5PasswordEncoder;
import com.AlexChang.security.filter.TokenAuthenticationFilter;
import com.AlexChang.security.filter.TokenLoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/**
 * ClassName:WebSecurityConfig
 * Description:
 *
 * @author Alex
 * @create 2023-10-14 下午 04:43
 * @Version:1.0
 */
@Configuration
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity(prePostEnabled = true) //
@Slf4j
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomMd5PasswordEncoder customMd5PasswordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;


//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return authenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(customMd5PasswordEncoder);
        return new ProviderManager(authProvider);
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
        http
                //关闭csrf跨站请求伪造
                .csrf().disable()
                // 开启跨域以便前端调用接口
                .cors().and()
                .authorizeHttpRequests()
                // 指定某些接口不需要通过验证即可访问。登陆接口肯定是不需要认证的
                .requestMatchers("/admin/system/index/login").permitAll()
                // 这里意思是其它所有接口需要认证才能访问
                .anyRequest().authenticated()
                .and()
                //TokenAuthenticationFilter放到UsernamePasswordAuthenticationFilter的前面，这样做就是为了除了登录的时候去查询数据库外，其他时候都用token进行认证。
                .addFilterBefore(new TokenAuthenticationFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new TokenLoginFilter(authenticationManager(),redisTemplate))
                ;

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(customMd5PasswordEncoder);
//    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 指定UserDetailService和加密器
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(customMd5PasswordEncoder);
//    }

    /**
     * 配置哪些请求不拦截
     * 排除swagger相关请求
     */


//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().requestMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v3/**", "/swagger-ui.html/**", "/doc.html");
    }

    /**
     *跨域资源配置
     */
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource()
//    {
//        final CorsConfiguration configuration = new CorsConfiguration();
//
//        //此处发现如果不加入自己的项目地址，会被拦截。
//        //configuration.setAllowedOriginPatterns(List.of("http://localhost:8800"));
//        configuration.setAllowedOriginPatterns(List.of("http://localhost:9528"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
//        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
//        configuration.setAllowCredentials(true);
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }

}
