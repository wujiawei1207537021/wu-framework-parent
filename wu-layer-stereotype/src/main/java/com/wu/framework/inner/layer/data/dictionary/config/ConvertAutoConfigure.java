package com.wu.framework.inner.layer.data.dictionary.config;

import com.wu.framework.inner.layer.data.dictionary.ConvertAdapter;
import com.wu.framework.inner.layer.data.dictionary.DefaultConvertAdapterService;
import com.wu.framework.inner.layer.data.dictionary.adapter.LazyDictionaryConvertAdapter;
import com.wu.framework.inner.layer.data.dictionary.aop.NormalConvertMapperPointcutAdvisor;
import com.wu.framework.inner.layer.data.dictionary.api.ConvertApi;
import com.wu.framework.inner.layer.data.dictionary.api.DefaultConvertApi;
import com.wu.framework.inner.layer.data.dictionary.convert.DefaultLazyDictionaryConvert;
import com.wu.framework.inner.layer.data.dictionary.convert.LazyDictionaryConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * 字典转换配置类
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ConvertAutoConfigure {


    /**
     * 字典转换适配器
     *
     * @param convertApi
     * @return
     */
    @Deprecated
    @Bean
    @ConditionalOnSingleCandidate(ConvertApi.class)
    @ConditionalOnMissingBean({ConvertAdapter.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ConvertAdapter convertAdapter(ConvertApi convertApi) {
        return new DefaultConvertAdapterService(convertApi);
    }


    /**
     * 默认字段转换 API 不操作任何数据
     *
     * @return DefaultConvertApi
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DefaultConvertApi defaultConvertDictionary() {
        return new DefaultConvertApi();
    }

    /**
     * 默认字段转换抽象实现
     *
     * @param convertApi 转换获取数据API
     * @return
     */
    @Bean
    @ConditionalOnSingleCandidate(ConvertApi.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DefaultLazyDictionaryConvert defaultConvertDictionary(ConvertApi convertApi) {
        return new DefaultLazyDictionaryConvert(convertApi);
    }


    /**
     * 转换适配器
     *
     * @param lazyDictionaryConvertList
     * @return
     */
    @Bean
    @ConditionalOnBean(LazyDictionaryConvert.class)
    @ConditionalOnMissingBean({LazyDictionaryConvertAdapter.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyDictionaryConvertAdapter convertDictionaryAdapter(List<LazyDictionaryConvert> lazyDictionaryConvertList) {
        return new LazyDictionaryConvertAdapter(lazyDictionaryConvertList);
    }


    /**
     * 字段转换适配器切面注入
     *
     * @param lazyDictionaryConvertAdapter 转换适配器
     * @return
     */
    @Bean
    @ConditionalOnBean(LazyDictionaryConvertAdapter.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public NormalConvertMapperPointcutAdvisor normalConvertMapperPointcutAdvisor(LazyDictionaryConvertAdapter lazyDictionaryConvertAdapter) {
        return new NormalConvertMapperPointcutAdvisor(lazyDictionaryConvertAdapter);
    }
}
