package com.alanjgg.wwc.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Alan
 * @Description
 * @date 2022/3/16
 */
@Data
public class AccessTokenResult {
    /**
     * 状态码
     */
    @JSONField(name = "errcode")
    private Integer errcode;

    /**
     * 信息
     */
    @JSONField(name = "errmsg")
    private String errmsg;

    /**
     * access_token
     */
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * 有效时间
     */
    @JSONField(name = "expires_in")
    private Integer expiresIn;

}
