package com.AlexChang.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName:BaseEntity
 * Description:
 *
 * @author Alex
 * @create 2023-09-10 下午 02:03
 * @Version:1.0
 */

@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("create_time")
    private Date createTime;;

    @TableField("update_time")
    private Date updateTime;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * @TableField(exist = false) 註解在bean屬性上，表示當前屬性不是數據庫字段，
     *      但會在項目中使用，這樣在新增...等地方使用bean時，mybatis-plus就會忽略此屬性，不會報錯
     * */
    @TableField(exist = false)
    private Map<String, Objects> param = new HashMap<>();

}
