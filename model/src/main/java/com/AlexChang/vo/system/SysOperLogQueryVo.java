package com.AlexChang.vo.system;

import lombok.Data;

/**
 * ClassName:SysOperLogQueryVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 04:51
 * @Version:1.0
 */

@Data
public class SysOperLogQueryVo {

    private String title;
    private String operName;
    private String createTimeBegin;
    private String createTimeEnd;
}
