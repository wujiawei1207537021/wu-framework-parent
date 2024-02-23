package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;

import java.util.function.Supplier;

/**
 * description 加解密
 *
 * @author 吴佳伟
 * @date 2023/08/29 13:28
 */
public interface EncryptionDecryption {

    /**
     * 是否支持当前加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @return
     */
    boolean support(EncryptionDecryptionEnum encryptionDecryptionEnum);

    /**
     * 加密
     *
     * @param source 原数据
     * @return 加密后数据
     */
    default Object encryption(Object source) {
       return encryption(source, null);
    }


    /**
     * @param source   原数据
     * @param supplier 消费参数
     * @return 加密后数据
     */
    Object encryption(Object source, Supplier<?> supplier);


    /**
     * @param encryptionSource 加密后数据
     * @return 解密后数据
     */
    default Object decryption(Object encryptionSource) {
        return decryption(encryptionSource, null);
    }


    /**
     * @param encryptionSource 加密后数据
     * @param supplier         消费参数
     * @return 解密后数据
     */
    Object decryption(Object encryptionSource, Supplier<?> supplier);
}
