package com.AlexChang.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName:ServiceAuthApplication
 * Description:
 *
 * @author Alex
 * @create 2023-09-13 下午 07:07
 * @Version:1.0
 */

@SpringBootApplication
@MapperScan("com.AlexChang.auth.mapper")
public class ServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class,args);
    }
}
