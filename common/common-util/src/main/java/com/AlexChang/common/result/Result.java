package com.AlexChang.common.result;

import lombok.Data;

/**
 * ClassName:Rest
 * Description:
 *
 * @author Alex
 * @create 2023-09-14 上午 11:48
 * @Version:1.0
 */

@Data
public class Result<T> {

    private Integer code; //狀態碼
    private String message; //返回信息
    private T data; //數據

    //構造私有化
    private Result(){

    }

    //封裝返回數據
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum){
        Result<T> result = new Result<>();

        //封裝數據
        if(body != null){
            result.setData(body);
        }

        //狀態碼
        result.setCode(resultCodeEnum.getCode());

        //返回信息
        result.setMessage(resultCodeEnum.getMessage());

        return result;
    }

    //成功
    public static <T> Result<T> ok(){
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    public static <T> Result<T> ok(T data){
        return Result.build(data,ResultCodeEnum.SUCCESS);
    }

    //失敗
    public static <T> Result<T> fail(){
        return Result.build(null,ResultCodeEnum.FAIL);
    }
    public static <T> Result<T> fail(T data){
        return Result.build(data,ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

}
