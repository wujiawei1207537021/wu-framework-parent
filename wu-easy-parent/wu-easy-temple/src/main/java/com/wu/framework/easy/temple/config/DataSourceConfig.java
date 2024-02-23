package com.wu.framework.easy.temple.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;

/**
 * description  手动配置数据源
 *
 * @author Jia wei Wu
 * @date 2021/2/9 下午5:29
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DataSourceConfig {

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean(name = "dataSourceMySQL")
    public DataSource dataSourceMySQL() {
//        MysqlDataSource build = DataSourceBuilder.create().type(MysqlDataSource.class).build();
        HikariDataSource build = DataSourceBuilder.create()
                .url("jdbc:mysql://127.0.0.1:3306/temp?rewriteBatchedStatements=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai")
                .username("root")
                .password("wujiawei")
                .type(HikariDataSource.class).build();
        return build;
    }


    /**
     * @param
     * @return describe H2数据库
     * @author Jia wei Wu
     * @date 2021/4/15 8:21 下午
     **/
//    @Bean(name = "dataSourceH2")
    public DataSource dataSourceH2() {
        JdbcDataSource build = DataSourceBuilder.create().type(org.h2.jdbcx.JdbcDataSource.class).build();
        build.setUrl("jdbc:h2:file:./database/db");
        build.setUser("root");
        build.setPassword("wujiawei");
        return build;
    }
}