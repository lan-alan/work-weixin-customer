package com.alanjgg.wwc;

import com.alanjgg.wwc.api.RequestApi;
import com.alanjgg.wwc.common.Constants;
import com.alanjgg.wwc.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class WorkWeixinCustomerApplicationTests {

    @Autowired
    private RequestApi requestApi;

    @Test
    void getAccessTokenTest() {
        String url = Constants.QY_API + Constants.ACCESS_TOKEN;
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

}
