package com.alanjgg.wwc.common;

import com.alanjgg.wwc.config.WeiXinConfig;

/**
 * @author Alan
 * @Description
 * @date 2022/3/15
 */
public class Constants {

    /**
     * 企业ID
     */
    public static final String CORPID = WeiXinConfig.CORPID;

    /**
     * 微信客服secret
     */
    public static final String CORPSECRET = WeiXinConfig.CORPSECRET;

    /**
     * 企业api
     */
    public static final String QY_API = "https://qyapi.weixin.qq.com/";

    /**
     * 获取企业access_token
     */
    public static final String ACCESS_TOKEN = "cgi-bin/gettoken";

}
