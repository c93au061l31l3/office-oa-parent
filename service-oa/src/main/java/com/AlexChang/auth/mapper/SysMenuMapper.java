package com.AlexChang.auth.mapper;

import com.AlexChang.model.system.SysMenu;
import com.AlexChang.model.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName:SysMenuMapper
 * Description:
 *
 * @author Alex
 * @create 2023-10-10 下午 04:47
 * @Version:1.0
 */

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    //多表關聯查詢 : 用戶角色關係表，角色菜單關係表，菜單表
    List<SysMenu> findMenuListByUserId(@Param("userId") Long userId);
}
