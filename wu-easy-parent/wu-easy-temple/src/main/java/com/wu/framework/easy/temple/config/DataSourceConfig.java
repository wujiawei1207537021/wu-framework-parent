package com.wu.framework.easy.temple.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * description  手动配置数据源
 *
 * @author 吴佳伟
 * @date 2021/2/9 下午5:29
 */
//@Configuration
public class DataSourceConfig {

    @Bean(name = "autoDataSource1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().url("jdbc:mysql://k8s.wu2020.top:13306/upsert?rewriteBatchedStatements=true")
                .type(MysqlDataSource.class)
                .username("root")
                .password("wujiawei")
                .build();
    }
}
