package com.wu.framework.inner.layer.data.translation.config;


import com.wu.framework.inner.layer.data.translation.adapter.TranslationAdapter;
import com.wu.framework.inner.layer.data.translation.advanced.TranslationAdvanced;
import com.wu.framework.inner.layer.data.translation.advanced.TranslationAdvancedTarget;
import com.wu.framework.inner.layer.data.translation.aop.NormalTranslationPointcutAdvisor;
import com.wu.framework.inner.layer.data.translation.api.DefaultTranslationAPI;
import com.wu.framework.inner.layer.data.translation.api.DefaultTranslationObject2AcsIIAPI;
import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * 转译配置类
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class TranslationAutoConfigure {


    /**
     * 转换适配者 目标
     *
     * @param translationAPIList 转换的api
     * @return
     */
    @Bean
    @ConditionalOnBean(TranslationAPI.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TranslationAdvancedTarget translationAdvancedTarget(List<TranslationAPI> translationAPIList) {
        return new TranslationAdvancedTarget(translationAPIList);
    }


    /**
     * 转换适配器
     *
     * @param translationAdvancedList
     * @return
     */
    @Bean
    @ConditionalOnBean(TranslationAdvanced.class)
    @ConditionalOnMissingBean({TranslationAdapter.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TranslationAdapter translationAdapter(List<TranslationAdvanced> translationAdvancedList) {
        return new TranslationAdapter(translationAdvancedList);
    }


    /**
     * 转译适配器切面注入
     *
     * @param translationAdapter 转换适配器
     * @return
     */
    @Bean
    @ConditionalOnBean(TranslationAdapter.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public NormalTranslationPointcutAdvisor normalTranslationPointcutAdvisor(TranslationAdapter translationAdapter) {
        return new NormalTranslationPointcutAdvisor(translationAdapter);
    }

    /**
     * 默认DefaultTranslationAPI
     * @return DefaultTranslationAPI
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public DefaultTranslationAPI defaultTranslationAPI(){
        return new DefaultTranslationAPI();
    }

    /**
     * 字段转换成acsII
     * @return DefaultTranslationObject2AcsIIAPI
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public DefaultTranslationObject2AcsIIAPI defaultTranslationObject2AcsIIAPI(){
        return new DefaultTranslationObject2AcsIIAPI();
    }
}
