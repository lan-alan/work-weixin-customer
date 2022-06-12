package com.alanjgg.wwc;

import com.alanjgg.wwc.api.RequestApi;
import com.alanjgg.wwc.common.Constants;
import com.alanjgg.wwc.service.WxKeFuInfoService;
import com.alanjgg.wwc.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.*;

@Slf4j
@SpringBootTest
class WorkWeixinCustomerApplicationTests {

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

}
