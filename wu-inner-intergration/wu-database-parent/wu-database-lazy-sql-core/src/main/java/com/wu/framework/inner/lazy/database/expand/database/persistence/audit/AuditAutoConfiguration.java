package com.wu.framework.inner.lazy.database.expand.database.persistence.audit;

import com.wu.framework.inner.lazy.database.datasource.proxy.audit.LazyAuditHolderAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.audit.lazy.LazyMysqlAudit;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;

/**
 * description 适配器配置bean 注入
 *
 * @author 吴佳伟
 * @date 2023/05/13 12:34
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AuditAutoConfiguration {

    /**
     * description:  审计适配器
     *
     * @return
     * @date: 13.5.23 13:01
     */
    @ConditionalOnBean(value = {DataSource.class})
    @Bean
    @ConditionalOnMissingBean(AuditAdapter.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AuditAdapter auditAdapter(ApplicationContext applicationContext) {

        AuditAdapter auditAdapter = new AuditAdapter(applicationContext);
        LazyAuditHolderAdapter.addLazyAuditAdapter("auditAdapter", auditAdapter);
        return auditAdapter;
    }

    /**
     * describe mysql sql 审计
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/12 20:01
     **/
    @ConditionalOnBean(value = {DataSource.class})
    @Bean
    @ConditionalOnMissingBean(LazyMysqlAudit.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyMysqlAudit lazyMysqlAudit() {
        return new LazyMysqlAudit();
    }
}
