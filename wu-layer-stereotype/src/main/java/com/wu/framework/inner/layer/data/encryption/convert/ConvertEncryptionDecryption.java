package com.wu.framework.inner.layer.data.encryption.convert;


import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionTypeEnum;
import org.springframework.core.Ordered;

/**
 * 加解密转换接口
 */
public interface ConvertEncryptionDecryption extends Ordered {
    /**
     * 字段转译
     *
     * @param object 原始数据
     * @param encryptionDecryptionTypeEnum 加解密类型
     */
    void transformation(Object object, EncryptionDecryptionTypeEnum encryptionDecryptionTypeEnum);

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    boolean support(Object source);
}
