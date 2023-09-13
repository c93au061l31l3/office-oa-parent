package com.AlexChang.vo.system;

/**
 * ClassName:LoginVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 01:19
 * @Version:1.0
 */

//登入對象
public class LoginVo {

    private String username; //手機號碼

    private String password; //密碼

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
