package com.AlexChang.vo.system;


import lombok.Data;

/**
 * ClassName:SysLoginQueryVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 02:00
 * @Version:1.0
 */

@Data
public class SysLoginQueryVo {

    //@ApiModelProperty(value = "用戶帳號")
    private String username;

    private String createTimeBegin;
    private String createTimeEnd;
}
