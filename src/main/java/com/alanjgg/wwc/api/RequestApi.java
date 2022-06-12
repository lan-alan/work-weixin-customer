package com.alanjgg.wwc.api;

import com.alanjgg.wwc.common.Constants;
import com.alanjgg.wwc.common.ResponseCode;
import com.alanjgg.wwc.result.AccessTokenResult;
import com.alanjgg.wwc.utils.HttpUtil;
import com.alanjgg.wwc.utils.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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
            String url = Constants.ACCESS_TOKEN;
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
            // 如果从缓存上获取的accessToken是空的，那么就通过接口获取
            if (StringUtils.isBlank(accessToken)) {
                accessToken = getAccessToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 上传临时素材
     */
    public String tempMediaUpload(File file, String accessToken) {

        String url = Constants.TEMP_MEDIA_UPLOAD;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        paramMap.put("type", Constants.TempMediaType.IMAGE.getType());
        String uri = getUri(url, paramMap);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        headers.setContentLength(file.length());
        headers.setContentDispositionFormData("media", file.getName());
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(file.getPath());
        param.add("file", resource);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> data = restTemplate.postForEntity(uri, formEntity, String.class);

        return data.getBody();
    }

    /**
     * 添加客服账号
     *
     * @param name        客服昵称
     * @param mediaId     头像
     * @param accessToken
     * @return
     */
    public String kfAccountAdd(String name, String mediaId, String accessToken) {
        String url = Constants.KF_ACCOUNT_ADD + "?access_token=" + accessToken;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("name", name);
        paramMap.put("media_id", mediaId);
        String result = HttpUtil.doPostJson(url, JSONObject.toJSONString(paramMap));
        return result;
    }

    private static String getUri(String url, Map<String, String> param) {
        String uri = null;
        // 创建uri
        URIBuilder builder = null;
        try {
            builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            uri = builder.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

}
