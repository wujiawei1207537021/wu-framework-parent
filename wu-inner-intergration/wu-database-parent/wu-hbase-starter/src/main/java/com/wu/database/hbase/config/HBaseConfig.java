package com.wu.database.hbase.config;


import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.config.HBaseConfigProperties;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/23 8:37 下午
 */
@Configuration
public class HBaseConfig {

    private final HBaseConfigProperties hBaseConfigProperties;


    private static ExecutorService pool = Executors.newScheduledThreadPool(20);    //设置hbase连接池

    public HBaseConfig(HBaseConfigProperties hBaseConfigProperties) {
        this.hBaseConfigProperties = hBaseConfigProperties;
    }


    @Bean
    public Connection hbaseClientConnection() throws IOException {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        Map<String, String> confMap = hBaseConfigProperties.getConfMaps();
        for (Map.Entry<String, String> confEntry : confMap.entrySet()) {
            conf.set(confEntry.getKey(), confEntry.getValue());
        }
        System.out.println("init hbaseClientConnection success");
        return ConnectionFactory.createConnection(conf, pool);
    }

    @Bean
    public Admin hbaseClientAdmin(Connection connection) throws IOException {
        return connection.getAdmin();
    }

}
