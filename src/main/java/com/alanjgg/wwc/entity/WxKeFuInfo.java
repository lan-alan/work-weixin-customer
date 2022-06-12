package com.alanjgg.wwc.entity;

import com.alanjgg.wwc.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Alan
 * @Description
 * @date 2022/4/10
 */
@Data
@TableName(value = "wx_kefu_info")
public class WxKeFuInfo extends BaseEntity {

    private String wxKfName;

    private String wxKfId;

}
