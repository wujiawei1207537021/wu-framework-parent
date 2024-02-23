package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.config;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/15 3:18 下午
 */
@ConditionalOnBean(DataSource.class)
public class LazyDataBaseStreamConfig {
//
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

    /**
     * 升级版 LazyLambdaStream
     *
     * @param lazyOperation
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public LazyLambdaStream lazyLambda(LazyOperation lazyOperation) {
        return new LazyLambdaStream(lazyOperation);
    }


}
