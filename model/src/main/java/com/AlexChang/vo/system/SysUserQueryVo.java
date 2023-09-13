package com.AlexChang.vo.system;

import lombok.Data;

/**
 * ClassName:SysUserQueryVo
 * Description:
 *
 * @author Alex
 * @create 2023-09-12 下午 05:00
 * @Version:1.0
 */

@Data
public class SysUserQueryVo {

    private static final long serialVersionUID = 1L;

    private String keyword;

    private String createTimeBegin;
    private String createTimeEnd;

    private Long roleId;
    private Long postId;
    private Long deptId;
}
