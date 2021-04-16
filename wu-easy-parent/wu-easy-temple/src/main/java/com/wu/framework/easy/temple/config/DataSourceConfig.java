package com.wu.framework.easy.temple.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * description  手动配置数据源
 *
 * @author Jia wei Wu
 * @date 2021/2/9 下午5:29
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "autoDataSource1")
    public DataSource dataSource1() {
        MysqlDataSource build = DataSourceBuilder.create().type(MysqlDataSource.class).build();
        build.setUrl("jdbc:mysql://127.0.0.1:3306/temp?rewriteBatchedStatements=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        build.setUser("root");
        build.setPassword("wujiawei");
        return build;
    }
}
