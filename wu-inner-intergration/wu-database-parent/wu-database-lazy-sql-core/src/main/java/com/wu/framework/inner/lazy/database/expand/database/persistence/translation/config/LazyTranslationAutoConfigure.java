package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.config;


import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.advanced.LazyTranslationAdvanced;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.advanced.LazyTranslationAdvancedTarget;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.aop.LazyTranslationPointcutAdvisor;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.DefaultLazyTableTranslation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
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
public class LazyTranslationAutoConfigure {

    /**
     * 默认转译对象
     *
     * @return
     */
    @Bean
//    @ConditionalOnMissingBean({LazyTranslationAPI.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyTranslationAPI lazyTranslationAPI() {
        return new DefaultLazyTableTranslation();
    }

    /**
     * 转换适配者 目标
     *
     * @param lazyTranslationAPIList 转换的api
     * @return
     */
    @Bean
    @ConditionalOnBean(LazyTranslationAPI.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyTranslationAdvancedTarget lazyTranslationAdvancedTarget(List<LazyTranslationAPI> lazyTranslationAPIList) {
        return new LazyTranslationAdvancedTarget(lazyTranslationAPIList);
    }


    /**
     * 转换适配器
     *
     * @param lazyTranslationAdvancedList
     * @return
     */
    @Bean
    @ConditionalOnBean(LazyTranslationAdvanced.class)
    @ConditionalOnMissingBean({LazyTranslationAdapter.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyTranslationAdapter lazyTranslationAdapter(List<LazyTranslationAdvanced> lazyTranslationAdvancedList) {
        return new LazyTranslationAdapter(lazyTranslationAdvancedList);
    }


    /**
     * 转译适配器切面注入
     *
     * @param translationAdapter 转换适配器
     * @return
     */
    @Bean
    @ConditionalOnBean(LazyTranslationAdapter.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyTranslationPointcutAdvisor lazyTranslationPointcutAdvisor(LazyTranslationAdapter translationAdapter) {
        return new LazyTranslationPointcutAdvisor(translationAdapter);
    }

}
