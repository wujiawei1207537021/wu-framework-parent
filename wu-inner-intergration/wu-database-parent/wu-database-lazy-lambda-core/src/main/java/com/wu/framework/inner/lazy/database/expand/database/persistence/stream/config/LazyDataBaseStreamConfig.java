package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/15 3:18 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//@ConditionalOnBean(DataSource.class)
public class LazyDataBaseStreamConfig {

//    /**
//     * @param
//     * @return describe Stream通道对象
//     * @author Jia wei Wu
//     * @date 2021/8/15 3:19 下午
//     **/
//    @Deprecated
//    @Bean
//    @ConditionalOnMissingBean
//    public LazyLambdaStream lambdaStream(LazyOperation lazyOperation) {
//        return new ReferencePipeline(lazyOperation);
//    }
//
//    /**
//     * @param
//     * @return describe 查询的Stream 操作对象
//     * @author Jia wei Wu
//     * @date 2021/8/15 3:19 下午
//     **/
//    @Deprecated
//    @Bean
//    @ConditionalOnMissingBean
//    public SelectReferencePipeline selectReferencePipeline(LazyOperation lazyOperation) {
//        return new SelectReferencePipeline(lazyOperation);
//    }

//    /**
//     * 升级版 mysql LazyLambdaStream
//     *
//     * @param lazyOperation 懒人操作 下游代理实现的抽象接口
//     * @return LazyLambdaStream
//     */
//    @Bean
//    @ConditionalOnMissingBean
////    @ConditionalOnBean(LazyOperation.class)
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public LazyLambdaStream lazyLambdaStream(LazyOperation lazyOperation) {
//        return new LazyLambdaStream(lazyOperation);
//    }


}
