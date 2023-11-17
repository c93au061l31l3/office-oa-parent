package com.AlexChang.process.mapper;

import com.AlexChang.model.process.Process;
import com.AlexChang.vo.process.ProcessQueryVo;
import com.AlexChang.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName:OaProcessMapper
 * Description:
 *
 * @author Alex
 * @create 2023-11-08 下午 12:49
 * @Version:1.0
 */

@Mapper
public interface OaProcessMapper extends BaseMapper<Process> {


    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, @Param("vo") ProcessQueryVo processQueryVo);

}
