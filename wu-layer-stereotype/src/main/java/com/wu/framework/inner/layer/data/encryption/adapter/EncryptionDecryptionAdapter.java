package com.wu.framework.inner.layer.data.encryption.adapter;

import com.wu.framework.inner.layer.data.encryption.EncryptionDecryption;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;

import java.util.List;
import java.util.function.Supplier;

/**
 * description 加密解密适配器
 *
 * @author 吴佳伟
 * @date 2023/08/29 13:26
 */
public class EncryptionDecryptionAdapter {

    private final List<EncryptionDecryption> encryptionDecryptionList;

    public EncryptionDecryptionAdapter(List<EncryptionDecryption> encryptionDecryptionList) {
        this.encryptionDecryptionList = encryptionDecryptionList;
    }

    /**
     * 加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @param source                   原数据
     * @param supplier                 额外参数
     * @return 加密后数据
     */
    public Object encryption(EncryptionDecryptionEnum encryptionDecryptionEnum, Object source, Supplier supplier) {
        for (EncryptionDecryption encryptionDecryption : encryptionDecryptionList) {
            if (encryptionDecryption.support(encryptionDecryptionEnum)) {
                return encryptionDecryption.encryption(source, supplier);
            }
        }
        throw new IllegalArgumentException("无法对数据指定方式：" + encryptionDecryptionEnum + " 数据：" + source + "进行加密");
    }

    /**
     * 加密
     *
     * @param encryptionDecryptionEnum 加密方式
     * @param source                   原数据
     * @return 加密后数据
     */
    public Object encryption(EncryptionDecryptionEnum encryptionDecryptionEnum, Object source) {
        return encryption(encryptionDecryptionEnum, source, null);
    }


    /**
     * @param encryptionSource 加密后数据
     * @param supplier                 额外参数
     * @return 解密后数据
     *
     */
    public Object decryption(EncryptionDecryptionEnum encryptionDecryptionEnum, Object encryptionSource, Supplier supplier) {
        for (EncryptionDecryption encryptionDecryption : encryptionDecryptionList) {
            if (encryptionDecryption.support(encryptionDecryptionEnum)) {
                return encryptionDecryption.decryption(encryptionSource,supplier);
            }
        }
        throw new IllegalArgumentException("无法对数据指定方式：" + encryptionDecryptionEnum + " 数据：" + encryptionSource + "进行解密");
    }

    /**
     * @param encryptionSource 加密后数据
     * @return 解密后数据
     */
    public Object decryption(EncryptionDecryptionEnum encryptionDecryptionEnum, Object encryptionSource) {
        return decryption(encryptionDecryptionEnum, encryptionSource,null);
    }

}
