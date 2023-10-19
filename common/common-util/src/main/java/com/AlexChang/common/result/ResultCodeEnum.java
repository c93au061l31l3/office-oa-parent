package com.AlexChang.common.result;

import lombok.Getter;

/**
 * ClassName:ResultCodeEnum
 * Description:
 *
 * @author Alex
 * @create 2023-09-14 上午 11:42
 * @Version:1.0
 */

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201,"失敗"),
//    SERVICE_ERROR(2012,"服務異常"),
    LOGIN_ERROR(204,"認證失敗")
//    LOGIN_AUTH(208,"未登入"),
    //PERMISSION(209,"沒有權限")
    ;

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
