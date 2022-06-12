package com.alanjgg.wwc.service.impl;

import com.alanjgg.wwc.entity.WxKeFuInfo;
import com.alanjgg.wwc.mapper.WxKeFuInfoMapper;
import com.alanjgg.wwc.service.WxKeFuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Alan
 * @Description
 * @date 2022/4/10
 */
@Slf4j
@Service
public class WxKeFuInfoServiceImpl implements WxKeFuInfoService {

    @Autowired(required = false)
    private WxKeFuInfoMapper wxKeFuInfoMapper;

    @Override
    public int saveWxKeFuInfo(String kefuName, String kefuId) {
        if (StringUtils.isBlank(kefuName) || StringUtils.isBlank(kefuId)) {
            log.info("参数 kefuName = {}，kefuId = {}， 不能为空。", kefuName, kefuId);
            return 0;
        }
        WxKeFuInfo info = new WxKeFuInfo();
        info.setWxKfName(kefuName);
        info.setWxKfId(kefuId);
        info.setCreateUser("system");
        info.setCreateDate(new Date());
        info.setIsDelete(false);
        return wxKeFuInfoMapper.insert(info);
    }
}
