package com.AlexChang.common.exception;

import com.AlexChang.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * ClassName:CustException
 * Description:
 *
 * @author Alex
 * @create 2023-09-16 上午 10:07
 * @Version:1.0
 */

@Data
public class CustException extends RuntimeException{

    private Integer code; //異常狀態碼
    private String msg; //描述信息

    public CustException(Integer code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    //接收枚舉類對象
    public CustException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

}
