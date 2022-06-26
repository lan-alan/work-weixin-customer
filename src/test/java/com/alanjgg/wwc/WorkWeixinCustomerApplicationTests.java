package com.alanjgg.wwc;

import cn.hutool.http.HttpRequest;
import com.alanjgg.wwc.api.RequestApi;
import com.alanjgg.wwc.common.Constants;
import com.alanjgg.wwc.service.WxKeFuInfoService;
import com.alanjgg.wwc.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weixin.popular.bean.BaseResult;
import weixin.popular.client.LocalHttpClient;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

@Slf4j
@SpringBootTest
class WorkWeixinCustomerApplicationTests {

    public static Header JSON_HEADER = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

    @Autowired
    private RequestApi requestApi;

    @Autowired
    private WxKeFuInfoService wxKeFuInfoService;

    @Test
    void getAccessTokenTest() {
        String url = Constants.ACCESS_TOKEN;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("corpid", Constants.CORPID);
        paramMap.put("corpsecret", Constants.CORPSECRET);
        String result = HttpUtil.doGet(url, paramMap);
        // hryKMlop402n0eZ75VwTZeOUDqruBjIEdgyUsGhkytkI7ov6QgVO_vZTc6eKm1H4y_KVH313yKWPJzQmvW_xBjv3oEcuNKbCEbaPPkXixu2VCc-n6FULGHI5u-uUGZsFLpJhFyaxf7IGoFxrh6QKfbszUpKEwSyT8onwAtDS8TuNVsNZ8-cu_U6hc0-85Fmlwt5BitDUS-LAVy-cKbetOQ
        //
        log.info("result = {}", result);
    }

    @Test
    void getAccessTokenFromCacheTest() {
        log.info("accessToken = {}", requestApi.getAccessTokenFromCache());
    }

    @Test
    void saveWxKeFuInfoTest() {
        String name = "大龙湖畔";
        String id = UUID.randomUUID().toString().replace("-", "");
        int count = wxKeFuInfoService.saveWxKeFuInfo(name, id);
        log.info("count = {}", count);
    }

    @Test
    void tempMediaUploadTest() {

        String filePath = "/Users/alan.lan/Pictures/背景图片/头像.jpeg";
        File file = new File(filePath);
        log.info("文件名称：{}", file.getName());
        log.info("文件是否存在：{}", file.exists());
        log.info("文件的相对路径：{}", file.getPath());
        log.info("文件的绝对路径：{}", file.getAbsolutePath());
        log.info("文件可以读取：{}", file.canRead());
        log.info("文件可以写入：{}", file.canWrite());
        log.info("文件上级路径：{}", file.getParent());
        log.info("文件大小：{}", file.length() + "B");
        log.info("文件最后修改时间：{}", DateUtils.formatDate(new Date(file.lastModified()), "yyyy-MM-dd HH:mm:ss"));
        log.info("是否是文件类型：{}", file.isFile());
        log.info("是否是文件夹类型：{}", file.isDirectory());
        // {"errcode":0,"errmsg":"ok","type":"image","media_id":"3TLvDhB3b0Q-8dcNOqYxYyAbf_qYmFmuVFGcyGCxRFD7WuMUwH4S9t-BwvupXagqY","created_at":"1650093257"}
        String accessToken = requestApi.getAccessTokenFromCache();
        String result = requestApi.tempMediaUpload(file, accessToken);
        log.info("result = {} ", result);

    }

    @Test
    void kfAccountAddTest() {
        String accessToken = requestApi.getAccessTokenFromCache();
        String name = "夕阳目齐";
        String mediaId = "3TLvDhB3b0Q-8dcNOqYxYyAbf_qYmFmuVFGcyGCxRFD7WuMUwH4S9t-BwvupXagqY";
        String result = requestApi.kfAccountAdd(name, mediaId, accessToken);
        log.info("result = {} ", result);
        Map<String, Object> map = StringUtils.isBlank(result) ? null : JSONObject.parseObject(result, Map.class);
        if (Objects.nonNull(map) && map.containsKey("errcode") && (Integer.parseInt(map.get("errcode").toString()) == 0)) {
            String kfId = map.get("open_kfid").toString();
            int count = wxKeFuInfoService.saveWxKeFuInfo(name, kfId);
            log.info("count = {}", count);
        }
    }

    @Test
    public void getWeiXinAccessTokenTest() {

        // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET

//        String uri = "https://api.weixin.qq.com/cgi-bin/token";
//
//        HttpUriRequest httpUriRequest = RequestBuilder.post()
//                .setHeader(JSON_HEADER)
//                .setUri(uri)
//                .addParameter("grant_type", "client_credential")
////                .addParameter("appid", "wx5b9dcf34bbde474c")
//                .addParameter("appid", "wx424eeae2a0648a4f")
//                .addParameter("secret", "52809ee0c5233c1f56791200567e81b9")
//                .build();
//
//        BaseResult result = LocalHttpClient.executeJsonResult(httpUriRequest, BaseResult.class);
//
//        System.out.println("result = " + JSONObject.toJSONString(result));


        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credential");
        paramMap.put("appid", "wx424eeae2a0648a4f");
        paramMap.put("secret", "52809ee0c5233c1f56791200567e81b9");
        String result = HttpUtil.doGet(url, paramMap);
        // {"access_token":"58_ycXNolFMtsKj60MFi55_5De2LzBjfnblauR03lYSU518tWT7_6Ac-FIjvVKOQkP7h09kE8lY0s2rlYFcENlNnEHDoMEw9M3OceLB-MMLDQtJM0IP6l_UIQg7otO9rbQ1rFR26PW4Uctos3x0LQFcAHAZET","expires_in":7200}
        // token = "58_ycXNolFMtsKj60MFi55_5De2LzBjfnblauR03lYSU518tWT7_6Ac-FIjvVKOQkP7h09kE8lY0s2rlYFcENlNnEHDoMEw9M3OceLB-MMLDQtJM0IP6l_UIQg7otO9rbQ1rFR26PW4Uctos3x0LQFcAHAZET"
        log.info("result = {}", result);

    }

    @Test
    public void generateschemeTest() {
        // POST https://api.weixin.qq.com/wxa/generatescheme?access_token=ACCESS_TOKEN

        String accessToken = "58_ycXNolFMtsKj60MFi55_5De2LzBjfnblauR03lYSU518tWT7_6Ac-FIjvVKOQkP7h09kE8lY0s2rlYFcENlNnEHDoMEw9M3OceLB-MMLDQtJM0IP6l_UIQg7otO9rbQ1rFR26PW4Uctos3x0LQFcAHAZET";
        String url = "https://api.weixin.qq.com/wxa/generatescheme?access_token=" + accessToken;
        Map<String, String> paramMap = new HashMap<>();
        JSONObject jumpWxa = new JSONObject();
        jumpWxa.put("path", "/pages/home/home");
        jumpWxa.put("query", "");
        paramMap.put("jump_wxa", jumpWxa.toJSONString());
        paramMap.put("expire_type", "1");
        paramMap.put("expire_interval", "30");
        String result = HttpUtil.doPost(url, paramMap);
        log.info("result = {}", result);

    }

    @Test
    public void getOpenLinkTest() {
        String token = "58_ycXNolFMtsKj60MFi55_5De2LzBjfnblauR03lYSU518tWT7_6Ac-FIjvVKOQkP7h09kE8lY0s2rlYFcENlNnEHDoMEw9M3OceLB-MMLDQtJM0IP6l_UIQg7otO9rbQ1rFR26PW4Uctos3x0LQFcAHAZET";
        String url = "https://api.weixin.qq.com/wxa/generatescheme?access_token=" + token;
        JSONObject jumpWxa = new JSONObject();
        jumpWxa.put("path", "/pages/home/home");
        jumpWxa.put("query", "");

        JSONObject reqMap = new JSONObject();
        reqMap.put("jump_wxa", jumpWxa);
        reqMap.put("expire_type", 1);
        reqMap.put("expire_interval", 30);
        String result = "";
        try {
            HttpRequest request = cn.hutool.http.HttpUtil.createPost(url);
            request.contentType("application/json");
            request.body(reqMap.toJSONString());
            result = request.execute().body();
            result = URLDecoder.decode(result, "UTF-8");
            System.out.print("微信获取getOpenLink回调报文" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
