package com.wu.framework.inner.common.util;

import lombok.Data;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 二维码业务实体
 * @date : 2020/6/17 11:02 下午
 */
@Data
public class QRBO {

    private String url;

    private CipherText cipherText;

    public String encrypt() {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var5) {
            throw new IllegalStateException(
                    "MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        byte[] bytes = digest.digest(cipherText.toString().getBytes(StandardCharsets.UTF_8));
        return String.format("%032x", new BigInteger(1, bytes));
    }


    public String contents() {
        return url + "?" + encrypt();
    }

    public String clearText() {
        return url + cipherText.getParam();
    }


    /**
     * @author : Jia wei Wu
     * @version : 1.0
     * @describe: 密码文字
     * @date : 2020/6/17 11:11 下午
     */
    @Data
    public static class CipherText {
        /**
         * 类型
         */
        private String type;
        /**
         * 参数
         */
        private String param;


    }

}
