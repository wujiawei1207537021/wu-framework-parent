package com.wu.framework.easy.upsert.config;

import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.upsert.dynamic.aop.EasyUpsertMySQLAnnotationAdvisor;
import com.wu.framework.easy.upsert.dynamic.aop.QuickEasyUpsertMySQLAnnotationAdvisor;
import com.wu.framework.easy.upsert.sink.MySQLEasyUpsertSink;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/7/9 9:45 下午
 */
@Import({MySQLEasyUpsertSink.class})
public class UpsertSinkConfig {

    @Bean
    @ConditionalOnMissingBean
    public EasyUpsertMySQLAnnotationAdvisor easyUpsertMySQLAnnotationAdvisor() {
        EasyUpsertMySQLAnnotationAdvisor advisor = new EasyUpsertMySQLAnnotationAdvisor();
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE);
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public QuickEasyUpsertMySQLAnnotationAdvisor quickEasyUpsertMySQLAnnotationAdvisor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        QuickEasyUpsertMySQLAnnotationAdvisor advisor = new QuickEasyUpsertMySQLAnnotationAdvisor(abstractDynamicEasyUpsert);
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE);
        return advisor;
    }

}
