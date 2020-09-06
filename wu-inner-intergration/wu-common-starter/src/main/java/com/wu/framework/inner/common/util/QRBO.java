package com.wu.framework.inner.common.util;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : 吴佳伟
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

        try {
            byte[] bytes = digest.digest(cipherText.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException var4) {
            throw new IllegalStateException(
                    "UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }


    public String contents() {
      return url+"?"+encrypt();
    }
    public String clearText() {
        return url+cipherText.getParam();
    }


    /**
     * @describe: 密码文字
     * @author : 吴佳伟
     * @date : 2020/6/17 11:11 下午
     * @version : 1.0
     */
    @Data
    public static class CipherText{
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
