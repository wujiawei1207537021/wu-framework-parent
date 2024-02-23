package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql.*;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.SqlInterceptor;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.SqlInterceptorAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/24 5:19 下午
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Configuration
public class LazyMethodAutoConfiguration {

    /**
     * describe 创建表结构
     *
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodCreateTable.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodCreateTable lazyOperationMethodCreateTable(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodCreateTable(lazyOperationParameter);
    }

    /**
     * describe 根据ID更新  自定义数据库持久层操作方法按ID删除
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodDeleteById.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodDeleteById lazyOperationMethodDeleteById(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodDeleteById(lazyOperationParameter);
    }

    /**
     * describe 根据ID更新  自定义数据库持久层操作方法I按ID列表删除
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodDeleteByIdList.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodDeleteByIdList lazyOperationMethodDeleteByIdList(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodDeleteByIdList(lazyOperationParameter);
    }

    /**
     * describe  执行操作
     *
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodExecute.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodExecute lazyOperationMethodExecute(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodExecute(lazyOperationParameter);
    }

    /**
     * describe 执行分页操作
     *
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodExecutePage.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodExecutePage lazyOperationMethodExecutePage(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodExecutePage(lazyOperationParameter);
    }

    /**
     * describe 执行返回一条数据
     *
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodExecuteOne.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodExecuteOne lazyOperationMethodExecuteOne(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodExecuteOne(lazyOperationParameter);
    }

    /**
     * describe 自定义数据库持久层操作方法执行SQL
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodExecuteSQL.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodExecuteSQL lazyOperationMethodExecuteSQL(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodExecuteSQL(lazyOperationParameter);
    }

    /**
     * describe 执行SQL  自定义数据库持久层操作方法对Bean执行SQL
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodExecuteSQLForBean.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodExecuteSQLForBean lazyOperationMethodExecuteSQLForBean(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodExecuteSQLForBean(lazyOperationParameter);
    }

    /**
     * describe 自定义数据库持久层操作方法插入
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodInsert.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodInsert lazyOperationMethodInsert(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodInsert(lazyOperationParameter);
    }

    /**
     * describe 自定义数据库持久层操作方法插入或更新
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodSaveOrUpdate.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodSaveOrUpdate lazyOperationMethodSaveOrUpdate(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodSaveOrUpdate(lazyOperationParameter);
    }

    /**
     * describe 分页查询
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodPage.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodPage lazyOperationMethodPage(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodPage(lazyOperationParameter);
    }

    /**
     * describe 完善表
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodPerfect.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodPerfect lazyOperationMethodPerfect(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodPerfect(lazyOperationParameter);
    }

    /**
     * describe 根据ID更新  自定义数据库持久层操作方法我选择列表
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodSelectList.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodSelectList lazyOperationMethodSelectList(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodSelectList(lazyOperationParameter);
    }

    /**
     * describe 根据ID更新  自定义数据库持久层操作方法我选择一种
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodSelectOne.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodSelectOne lazyOperationMethodSelectOne(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodSelectOne(lazyOperationParameter);
    }


    /**
     * describe 根据ID更新 自定义数据库持久层操作方法I按ID更新全部
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodUpdateAllById.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodUpdateAllById lazyOperationMethodUpdateAllById(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodUpdateAllById(lazyOperationParameter);
    }

    /**
     * describe 根据ID更新 自定义数据库持久层操作方法I按ID更新全部
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodUpdateById.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodUpdateById lazyOperationMethodUpdateById(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodUpdateById(lazyOperationParameter);
    }

    /**
     * describe 更新表
     *
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodUpdateTable.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodUpdateTable lazyOperationMethodUpdateTable(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodUpdateTable(lazyOperationParameter);
    }

    /**
     * describe upsert 操作
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodUpsert.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodUpsert lazyOperationMethodUpsert(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodUpsert(lazyOperationParameter);
    }

    /**
     * describe 去除null的字段
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/15 22:38
     **/
    @ConditionalOnProperty(prefix = "spring.datasource", value = "driver-class-name", havingValue = "com.mysql.cj.jdbc.Driver")
    @ConditionalOnMissingBean(value = LazyOperationMethodUpsertRemoveNull.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodUpsertRemoveNull lazyOperationMethodUpsertRemoveNull(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodUpsertRemoveNull(lazyOperationParameter);
    }

    /**
     * describe 执行sql脚本
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/16 23:07
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodScriptRunner.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodScriptRunner lazyOperationMethodScriptRunner(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodScriptRunner(lazyOperationParameter);
    }

    /**
     * describe 执行 字符串 sql脚本
     *
     * @param lazyOperationParameter 操作配置
     * @author Jia wei Wu
     * @date 2022/7/16 23:07
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationMethodStringScriptRunner.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationMethodStringScriptRunner lazyOperationMethodStringScriptRunner(LazyOperationParameter lazyOperationParameter) {
        return new LazyOperationMethodStringScriptRunner(lazyOperationParameter);
    }

    /**
     * describe  sql 拦截器适配器
     *
     * @param sqlInterceptorList 拦截器
     * @author Jia wei Wu
     * @date 2022/7/16 23:07
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = SqlInterceptorAdapter.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SqlInterceptorAdapter sqlInterceptorAdapter(List<SqlInterceptor> sqlInterceptorList) {
        for (SqlInterceptor sqlInterceptor : sqlInterceptorList) {
            log.info("添加Lazy sql 拦截器:{}", sqlInterceptor.getClass().getName());
        }

        return new SqlInterceptorAdapter(sqlInterceptorList);
    }

    /**
     * describe  sql 拦截器适配器
     *
     * @param lazyOperationConfig    配置信息
     * @param lazyTranslationAdapter 转译适配器
     * @param sqlInterceptorAdapter  sql 拦截器
     * @author Jia wei Wu
     * @date 2022/7/16 23:07
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperationParameter.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyOperationParameter lazyOperationParameter(LazyOperationConfig lazyOperationConfig,
                                                         SqlInterceptorAdapter sqlInterceptorAdapter,
                                                         LazyTranslationAdapter lazyTranslationAdapter) {
        return new LazyOperationParameter(lazyOperationConfig, sqlInterceptorAdapter, lazyTranslationAdapter);
    }

}
