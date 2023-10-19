package com.AlexChang.auth.activiti;

import org.springframework.stereotype.Component;

/**
 * ClassName:UserBean
 * Description:
 *
 * @author Alex
 * @create 2023-10-19 下午 05:48
 * @Version:1.0
 */

@Component
public class UserBean {

    public String getUsername(int id){
        if(id == 1){
            return "uesr1";
        }
        if(id == 2){
            return "user2";
        }else{
            return "admin";
        }
    }
}
