package com.wu.framework.inner.layer.data.encryption.config;

import com.wu.framework.inner.layer.data.encryption.adapter.ConvertEncryptionDecryptionAdapter;
import com.wu.framework.inner.layer.data.encryption.adapter.EncryptionDecryptionAdapter;
import com.wu.framework.inner.layer.data.encryption.aop.NormalConvertEncryptionDecryptionPointcutAdvisor;
import com.wu.framework.inner.layer.data.encryption.convert.ConvertEncryptionDecryption;
import com.wu.framework.inner.layer.data.encryption.convert.DefaultConvertEncryptionDecryption;
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
public class EncryptionDecryptionConvertAutoConfig {

    /**
     * 默认加解密转换器
     *
     * @return 加解密转换适配器对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnBean(EncryptionDecryptionAdapter.class)
//    @ConditionalOnMissingBean(ConvertEncryptionDecryption.class)
    /* 允许多个转换器**/
    public ConvertEncryptionDecryption defaultConvertEncryptionDecryption(EncryptionDecryptionAdapter encryptionDecryptionAdapter) {
        return new DefaultConvertEncryptionDecryption(encryptionDecryptionAdapter);
    }

    /**
     * 加密转换适配器
     *
     * @param convertEncryptionDecryptionList 加解密转换适配器对象
     * @return 加密转换适配器
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnBean(ConvertEncryptionDecryption.class)
    @ConditionalOnMissingBean(ConvertEncryptionDecryptionAdapter.class)
    public ConvertEncryptionDecryptionAdapter convertEncryptionDecryptionAdapter(List<ConvertEncryptionDecryption> convertEncryptionDecryptionList) {
        return new ConvertEncryptionDecryptionAdapter(convertEncryptionDecryptionList);
    }

    /**
     * 加密切面
     *
     * @param convertEncryptionDecryptionAdapter 加解密适配器
     * @return 加密切面对象
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnBean(ConvertEncryptionDecryptionAdapter.class)
    @ConditionalOnMissingBean(NormalConvertEncryptionDecryptionPointcutAdvisor.class)
    public NormalConvertEncryptionDecryptionPointcutAdvisor normalConvertEncryptionDecryptionPointcutAdvisor(ConvertEncryptionDecryptionAdapter convertEncryptionDecryptionAdapter) {
        return new NormalConvertEncryptionDecryptionPointcutAdvisor(convertEncryptionDecryptionAdapter);
    }


}
