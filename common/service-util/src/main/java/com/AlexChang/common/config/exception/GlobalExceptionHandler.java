package com.AlexChang.common.config.exception;

import com.AlexChang.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:GlobalExceptionHandler
 * Description:
 *
 * @author Alex
 * @create 2023-09-15 下午 08:35
 * @Version:1.0
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    //全局異常處理方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("執行全局異常處理");
    }

    //特定異常處理
    @ExceptionHandler(ArithmeticException.class) //會先找特定，找不到才接著找全局
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("特定全局異常處理");
    }

    //自訂義異常處理
    @ExceptionHandler(CustException.class)
    @ResponseBody
    public Result error(CustException e){
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }
}
