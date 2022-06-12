package com.alanjgg.wwc.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author Alan
 * @Description
 * @date 2021/12/19
 */
@Data
public class BaseEntity {

    /**
     * 主键ID
     * mybatis-plus在新增的时候，会给一个主键ID。我们需要自己自增ID，加以下注解
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 是否删除：0-否；1-是
     */
    private Boolean isDelete;

}
