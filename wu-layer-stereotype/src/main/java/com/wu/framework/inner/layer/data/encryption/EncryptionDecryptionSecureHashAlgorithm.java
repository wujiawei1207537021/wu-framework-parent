package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.function.Supplier;

/**
 * description 散列算法（Hashing） 加解密
 *
 * @author 吴佳伟
 * @date 2023/08/29 13:28
 * @see EncryptionDecryptionEnum#SHA
 */
public class EncryptionDecryptionSecureHashAlgorithm extends AbstractEncryptionDecryption {


    /**
     * 是否支持当前加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @return
     */
    @Override
    public boolean support(EncryptionDecryptionEnum encryptionDecryptionEnum) {
        return EncryptionDecryptionEnum.SHA.equals(encryptionDecryptionEnum);
    }

    /**
     * @param source   原数据
     * @param supplier 消费参数
     * @return 加密后数据
     */
    @Override
    public Object encryption(Object source, Supplier<?> supplier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(source.toString().getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hexValue = Integer.toHexString(0xFF & hash[i]);
                if (hexValue.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hexValue);
            }
            return hexString;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param encryptionSource 加密后数据
     * @param supplier         消费参数
     * @return 解密后数据
     */
    @Override
    public Object decryption(Object encryptionSource, Supplier<?> supplier) {
        return null;
    }
}
