package com.wu.framework.inner.layer.data.encryption.config;

import com.wu.framework.inner.layer.data.encryption.*;
import com.wu.framework.inner.layer.data.encryption.adapter.EncryptionDecryptionAdapter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * description 加解密配置
 *
 * @author 吴佳伟
 * @date 2023/08/29 17:31
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class EncryptionDecryptionAutoConfig {

    /**
     * md5 加密
     *
     * @return md5 加密对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean(EncryptionDecryptionMd5.class)
    public EncryptionDecryption encryptionDecryptionMd5() {
        return new EncryptionDecryptionMd5();
    }

    /**
     * base64 加解密
     *
     * @return base64 加解密对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean(EncryptionDecryptionBase64.class)
    public EncryptionDecryptionBase64 encryptionDecryptionBase64() {
        return new EncryptionDecryptionBase64();
    }

    /**
     * 对称加密算法 加解密
     *
     * @return 对称加密算法 加解密对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean(EncryptionDecryptionAdvancedEncryptionStandard.class)
    public EncryptionDecryption encryptionDecryptionAdvancedEncryptionStandard() {
        return new EncryptionDecryptionAdvancedEncryptionStandard();
    }

    /**
     * 非对称加密算法 加解密
     *
     * @return 非对称加密算法 加解密对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean(EncryptionDecryptionAsymmetricCryptography.class)
    public EncryptionDecryption encryptionDecryptionAsymmetricCryptography() {
        return new EncryptionDecryptionAsymmetricCryptography();
    }

    /**
     * 散列算法（Hashing） 加解密
     *
     * @return 散列算法（Hashing） 加解密对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean(EncryptionDecryptionSecureHashAlgorithm.class)
    public EncryptionDecryption encryptionDecryptionSecureHashAlgorithm() {
        return new EncryptionDecryptionSecureHashAlgorithm();
    }

    /**
     * 加密解密适配器
     *
     * @return 加密解密适配器对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnBean(EncryptionDecryption.class)
    @ConditionalOnMissingBean(EncryptionDecryptionAdapter.class)
    public EncryptionDecryptionAdapter encryptionDecryptionAdapter(List<EncryptionDecryption> encryptionDecryptionList) {
        return new EncryptionDecryptionAdapter(encryptionDecryptionList);
    }
}
