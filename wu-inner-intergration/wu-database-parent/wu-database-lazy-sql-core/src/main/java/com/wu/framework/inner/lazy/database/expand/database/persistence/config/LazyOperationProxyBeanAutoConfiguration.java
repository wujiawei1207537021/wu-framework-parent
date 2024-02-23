package com.wu.framework.inner.lazy.database.expand.database.persistence.config;


import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.datasource.proxy.LazyProxyDataSource;
import com.wu.framework.inner.lazy.database.datasource.proxy.util.LazyDataSourceProxyUtils;
import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDDLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDQLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.CureAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.config.LazyBeanPostProcessor;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyDDLOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql.LazyDQLOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyDDLOperationProxy;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyDQLOperationProxy;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTableTranslationOneAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTableTranslationOneToManyAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperationAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazyOperationSmartAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationProxyBeanAutoConfiguration {

    /**
     * 动态数据适配Lazy 适配器
     *
     * @param dataSourceMap 注入的数据源
     * @return LazyDynamicAdapter
     */
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyDynamicAdapter.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyDynamicAdapter lazyDynamicAdapter(Map<String, DataSource> dataSourceMap) {
        Map<String, LazyProxyDataSource> lazyProxyDataSourceMap = new ConcurrentHashMap<>();
        dataSourceMap.forEach((key, dataSource) -> lazyProxyDataSourceMap.put(key, LazyDataSourceProxyUtils.proxy(dataSource)));
        return new LazyDynamicAdapter(lazyProxyDataSourceMap);
    }


    /**
     * @param lazyOperationMethods   自定义数据库持久层操作方法集合
     * @param cureAdapter            治愈适配器
     * @param lazyDynamicAdapter     动态数据源适配器
     * @param lazyTranslationAdapter 转译适配器
     * @return describe 延迟操作代理Proxy
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnBean(value = {DataSource.class, LazyDynamicAdapter.class})
    @ConditionalOnMissingBean(value = LazyOperationProxy.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationProxy lazyOperationProxy(List<LazyOperationMethod> lazyOperationMethods,
                                                 LazyDynamicAdapter lazyDynamicAdapter,
                                                 CureAdapter cureAdapter,
                                                 LazyTranslationAdapter lazyTranslationAdapter) {
        return new LazyOperationProxy(lazyOperationMethods, lazyDynamicAdapter, cureAdapter, lazyTranslationAdapter);
    }

    /**
     * @param lazyOperationProxy 自定义接口实现方法执行代理类 包含执行 DDL、DQL、DML
     * @return describe 延迟操作代理
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnMissingBean(value = LazySqlOperation.class)
    @ConditionalOnBean(value = {LazyOperationProxy.class})
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazySqlOperation lazySqlOperation(LazyOperationProxy lazyOperationProxy) {
        return (LazySqlOperation) Proxy.newProxyInstance(LazySqlOperation.class.getClassLoader(), new Class[]{LazySqlOperation.class}, lazyOperationProxy);
    }


    /**
     * 升级版 mysql LazyLambdaStream
     *
     * @param lazySqlOperation 懒人操作 下游代理实现的抽象接口
     * @return LazyLambdaStream
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(LazySqlOperation.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyLambdaStream lazyLambdaStream(LazySqlOperation lazySqlOperation) {
        return new LazyLambdaStream(lazySqlOperation);
    }


    @ConditionalOnBean(LazyLambdaStream.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean(SmartLazyOperationAutoStuffed.class)
    public SmartLazyOperationAutoStuffed smartLazyOperationAutoStuffed(LazyOperationConfig operationConfig, LazyLambdaStream lazyLambdaStream) {
        return new LazyOperationSmartAutoStuffed(operationConfig, lazyLambdaStream);
    }

    /**
     * describe 延迟操作代理DDL Proxy
     * @param lazyDDLOperationMethodList ddl 操作
     * @param lazyDynamicAdapter         动态数据源适配器
     * @return LazyDDLOperationProxy
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnBean(value = {DataSource.class, LazyDynamicAdapter.class})
    @ConditionalOnMissingBean(value = LazyDDLOperationProxy.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyDDLOperationProxy lazyDDLOperationProxy(List<LazyDDLOperationMethod> lazyDDLOperationMethodList,
                                                       LazyDynamicAdapter lazyDynamicAdapter
    ) {
        return new LazyDDLOperationProxy(lazyDDLOperationMethodList, lazyDynamicAdapter);
    }

    /**
     * describe 延迟操作DDL代理
     * @param lazyDDLOperationProxy ddl 操作代理对象
     * @return LazyBaseDDLOperation
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnBean(value = {DataSource.class, LazyDDLOperationProxy.class})
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyBaseDDLOperation lazyBaseDDLOperation(LazyDDLOperationProxy lazyDDLOperationProxy) {
        return (LazyBaseDDLOperation) Proxy.newProxyInstance(LazyBaseDDLOperation.class.getClassLoader(), new Class[]{LazyBaseDDLOperation.class}, lazyDDLOperationProxy);
    }


    /**
     * @param lazyDQLOperationMethods dql 操作
     * @param lazyTranslationAdapter  转译适配器
     * @param lazyDynamicAdapter      动态数据源处理
     * @return describe 延迟操作代理DQLProxy
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnBean(value = {DataSource.class, LazyDynamicAdapter.class})
    @ConditionalOnMissingBean(value = LazyDQLOperationProxy.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyDQLOperationProxy lazyDQLOperationProxy(List<LazyDQLOperationMethod> lazyDQLOperationMethods,
                                                       LazyDynamicAdapter lazyDynamicAdapter,
                                                       LazyTranslationAdapter lazyTranslationAdapter
    ) {
        return new LazyDQLOperationProxy(lazyDQLOperationMethods, lazyDynamicAdapter, lazyTranslationAdapter);
    }

    /**
     * describe 延迟操作DDL代理
     * @param lazyDQLOperationProxy ddl操作代理对象
     * @return LazyBaseDQLOperation
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnBean(value = {DataSource.class, LazyDQLOperationProxy.class})
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyBaseDQLOperation lazyBaseDQLOperation(LazyDQLOperationProxy lazyDQLOperationProxy) {
        return (LazyBaseDQLOperation) Proxy.newProxyInstance(LazyBaseDQLOperation.class.getClassLoader(), new Class[]{LazyBaseDQLOperation.class}, lazyDQLOperationProxy);
    }


    /**
     * 完美处理方式
     *
     * @param exportDataConfiguration 导出数据配置
     * @param lazyLambdaStream        数据库操作lambda 表达式写法 LazyLambdaStream
     * @return PerfectLazyOperation
     */
    @ConditionalOnBean(value = {DataSource.class, LazyLambdaStream.class})
    @ConditionalOnMissingBean(value = PerfectLazyOperation.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PerfectLazyOperation perfectLazyOperation(ExportDataConfiguration exportDataConfiguration, LazyLambdaStream lazyLambdaStream) {
        return new PerfectLazyOperation(exportDataConfiguration, lazyLambdaStream);
    }


    /**
     * LazyScan 扫描处理器
     *
     * @param operation                操作
     * @param operationConfig          操作配置信息
     * @param lazyDataSourceProperties 数据库配置喜讯你
     * @return LazyBeanPostProcessor
     */
    @ConditionalOnBean(value = {DataSource.class, LazyOperation.class})
    @ConditionalOnMissingBean(value = LazyBeanPostProcessor.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyBeanPostProcessor lazyBeanPostProcessor(LazyOperation operation,
                                                       LazyOperationConfig operationConfig,
                                                       LazyDataSourceProperties lazyDataSourceProperties) {
        return new LazyBeanPostProcessor(operation, operationConfig, lazyDataSourceProperties);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 数据库转译

    /**
     * 默认转译对象
     *
     * @return LazyTranslationAPI
     */
    @Bean
//    @ConditionalOnMissingBean({LazyTranslationAPI.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyTranslationAPI lazyTableTranslationAPI() {
        return new LazyTableTranslationOneAPI();
    }


    @Bean
//    @ConditionalOnMissingBean({LazyTranslationAPI.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyTranslationAPI lazyTableTranslationOneToManyAPI() {
        return new LazyTableTranslationOneToManyAPI();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

