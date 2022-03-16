package com.alanjgg.wwc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alan
 * @Description
 * @date 2022/3/15
 */
@Configuration
public class WeiXinConfig {

    /**
     * 企业ID
     */
    public static String CORPID;

    /**
     * 微信客服secret
     */
    public static String CORPSECRET;

    @Value("${wx-config.corpid}")
    public void setCorpid(String corpid) {
        WeiXinConfig.CORPID = corpid;
    }

    @Value("${wx-config.corpsecret}")
    public void setCorpsecret(String corpsecret) {
        WeiXinConfig.CORPSECRET = corpsecret;
    }

}
