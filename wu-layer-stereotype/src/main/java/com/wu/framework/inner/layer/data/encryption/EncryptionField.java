package com.wu.framework.inner.layer.data.encryption;

import com.wu.framework.inner.layer.data.encryption.aop.NormalConvertEncryptionDecryptionPointcutAdvisor;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionEnum;
import com.wu.framework.inner.layer.stereotype.LayerField;

import java.lang.annotation.*;

/**
 * description 加密字段
 *
 * @author 吴佳伟
 * @date 2023/08/29 12:59
 * @see EncryptionFieldBean
 * @see NormalConvertEncryptionDecryptionMapper
 * @see NormalConvertEncryptionDecryptionPointcutAdvisor
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
public @interface EncryptionField {

    /**
     * 数据加密类型
     *
     * @return
     */
    EncryptionDecryptionEnum encryptionDecryptionType() default EncryptionDecryptionEnum.BASE64;

    /**
     * 当 encryptionDecryptionType=EncryptionDecryptionEnum.AES 时生效
     * AES 加密密钥 🔐
     *
     * @return 密钥
     */
    String aesKey() default "";

    /**
     * 当 encryptionDecryptionType=EncryptionDecryptionEnum.RSA 时生效
     * rsa 密钥对中公钥映射key
     *
     * @return rsa 密钥对中公钥
     */
    String rsaPublicKey() default "";

    /**
     * 当 encryptionDecryptionType=EncryptionDecryptionEnum.RSA 时生效
     * 密钥对中私钥映射key
     *
     * @return 密钥对中私钥
     */
    String rsaPrivateKey() default "";


}
