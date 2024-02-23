package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;

import java.util.Base64;
import java.util.function.Supplier;

/**
 * description base64 加解密
 *
 * @author 吴佳伟
 * @date 2023/08/29 13:28
 * @see EncryptionDecryptionEnum#MD5
 */
public class EncryptionDecryptionBase64 extends AbstractEncryptionDecryption {


    /**
     * 是否支持当前加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @return
     */
    @Override
    public boolean support(EncryptionDecryptionEnum encryptionDecryptionEnum) {
        return EncryptionDecryptionEnum.BASE64.equals(encryptionDecryptionEnum);
    }

    /**
     * @param source   原数据
     * @param supplier 消费参数
     * @return 加密后数据
     */
    @Override
    public Object encryption(Object source, Supplier<?> supplier) {
        try {
            byte[] encode = Base64.getEncoder().encode(source.toString().getBytes());
            return new String(encode);
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
        byte[] decode = Base64.getDecoder().decode(encryptionSource.toString().getBytes());
        return new String(decode);
    }


}
