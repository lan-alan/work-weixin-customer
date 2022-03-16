package com.alanjgg.wwc.api;

import com.alanjgg.wwc.common.Constants;
import com.alanjgg.wwc.common.ResponseCode;
import com.alanjgg.wwc.result.AccessTokenResult;
import com.alanjgg.wwc.utils.HttpUtil;
import com.alanjgg.wwc.utils.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alan
 * @Description
 * @date 2022/3/16
 */
@Slf4j
@Component
public class RequestApi {
    /**
     * 失效时间。企业微信token有效时间为7200秒
     */
    private static final Integer EXPIRE_TIME = 7200;

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN:";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取accessToken
     *
     * @return
     */
    public String getAccessToken() {
        String accessToken = null;
        try {
            String url = Constants.QY_API + Constants.ACCESS_TOKEN;
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("corpid", Constants.CORPID);
            paramMap.put("corpsecret", Constants.CORPSECRET);
            String result = HttpUtil.doGet(url, paramMap);
            AccessTokenResult tokenResult = JSONObject.parseObject(result, AccessTokenResult.class);
            if (Objects.nonNull(tokenResult) && ResponseCode.OK.getValue().equals(tokenResult.getErrcode())) {
                accessToken = tokenResult.getAccessToken();
                String key = ACCESS_TOKEN + Constants.CORPID;
                int seconds = Objects.nonNull(tokenResult.getExpiresIn()) ? tokenResult.getExpiresIn() : EXPIRE_TIME;
                redisUtil.set(key, accessToken, seconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 从缓存上获取accessToken
     *
     * @return
     */
    public String getAccessTokenFromCache() {
        String accessToken = null;
        try {
            String key = ACCESS_TOKEN + Constants.CORPID;
            if (redisUtil.exists(key)) {
                accessToken = redisUtil.get(key, String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

}
