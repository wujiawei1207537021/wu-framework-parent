package com.wu.framework.inner.lazy.database.expand.database.persistence.cure;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDDLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDQLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy.LazySchemaCure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy.LazyTableColumnCure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy.LazyTableCure;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/12 19:59
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class CureAutoConfiguration {

    /**
     * describe 表结构治愈
     *
     * @param lazyBaseDDLOperation DDL 语言
     * @param lazyBaseDQLOperation DQL 语言
     * @return TableCure
     * @author Jia wei Wu
     * @date 2023/1/12 20:01
     **/
    @ConditionalOnBean(value = {DataSource.class, LazyBaseDDLOperation.class, LazyBaseDQLOperation.class})
    @Bean
    @ConditionalOnMissingBean(TableCure.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TableCure tableCure(LazyBaseDDLOperation lazyBaseDDLOperation, LazyBaseDQLOperation lazyBaseDQLOperation) {
        return new LazyTableCure(lazyBaseDDLOperation, lazyBaseDQLOperation);
    }

    /**
     * description:  表字段治愈
     *
     * @param lazyBaseDDLOperation
     * @param lazyBaseDQLOperation
     * @return
     * @author 吴佳伟
     * @date: 16.1.23 01:37
     */
    @ConditionalOnBean(value = {DataSource.class, LazyBaseDDLOperation.class, LazyBaseDQLOperation.class})
    @Bean
    @ConditionalOnMissingBean(TableColumnCure.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TableColumnCure tableColumnCure(LazyBaseDDLOperation lazyBaseDDLOperation, LazyBaseDQLOperation lazyBaseDQLOperation) {
        return new LazyTableColumnCure(lazyBaseDDLOperation, lazyBaseDQLOperation);
    }

    /**
     * describe schema治愈
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/12 20:01
     **/
    @Bean
    @ConditionalOnMissingBean(SchemaCure.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnBean(LazyDataSourceProperties.class)
    public SchemaCure schemaCure(LazyDataSourceProperties lazyDataSourceProperties, LazyBaseDDLOperation lazyBaseDDLOperation) {
        return new LazySchemaCure(lazyDataSourceProperties, lazyBaseDDLOperation);
    }

    /**
     * describe 治愈适配器
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/12 20:01
     **/
    @Bean
    @ConditionalOnMissingBean(CureAdapter.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CureAdapter cureAdapter(List<Cure> cureList) {
        return new CureAdapter(cureList);
    }

}
