package com.wuframework.system.component.QR;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 二维码登录配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "qr")
public class QRProperties {

    private Dingtalk dingtalk;
    private QQ qq;
    private Alipay alipay;

    @Data
    public static class Dingtalk {
        /**
         * 服务地址
         */
        private String serverUrl;
        /**
         * appid
         */
        private String accessKey;
        /**
         * 密钥
         */
        private String accessSecret;
        /**
         * 回调地址
         */
        private String redirectUri;
        /**
         * 二维码地址(配置文件优先级大)
         */
        private String qRCodeUrl;
    }

    @Data
    public static class QQ {
        /**
         * 服务地址
         */
        private String serverUrl;
        /**
         * appid
         */
        private String appId;
        /**
         * 密钥
         */
        private String appKey;
        /**
         * 回调地址
         */
        private String redirectUri;
        /**
         * 二维码地址(配置文件优先级大)
         */
        private String qRCodeUrl;
    }

    @Data
    public static class Alipay {
        /**
         * 服务地址
         */
        private String serverUrl;
        /**
         * appid
         */
        private String appId;
        /**
         * 私钥
         */
        private String privateKey;
        /**
         * 公钥
         */
        private String alipayPublicKey;
        /**
         * 回调地址
         */
        private String redirectUri;
        /**
         * 二维码地址(配置文件优先级大)
         */
        private String qRCodeUrl;
    }

}
