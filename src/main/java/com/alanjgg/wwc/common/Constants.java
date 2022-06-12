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
    public static final String ACCESS_TOKEN = QY_API + "cgi-bin/gettoken";

    /**
     * 上传临时素材
     */
    public static final String TEMP_MEDIA_UPLOAD = QY_API + "cgi-bin/media/upload";

    /**
     * 添加客服账号
     */
    public static final String KF_ACCOUNT_ADD = QY_API + "cgi-bin/kf/account/add";

    /**
     * 临时素材类型
     */
    public enum TempMediaType {

        // 图片（image）、语音（voice）、视频（video），普通文件（file）
        IMAGE("image"), VOICE("voice"), VIDEO("video"), FILE("file");

        private String type;

        TempMediaType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

    }

}


