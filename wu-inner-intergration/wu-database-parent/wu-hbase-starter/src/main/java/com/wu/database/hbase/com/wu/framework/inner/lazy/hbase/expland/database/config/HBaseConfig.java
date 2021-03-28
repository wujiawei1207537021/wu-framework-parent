package com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.config;


import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.persistence.HBaseOperation;
import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.persistence.proxy.HBaseOperationProxy;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 注入hbase 连接需要的bean
 * @date : 2021/3/23 8:37 下午
 */
public class HBaseConfig {

    private final HBaseConfigProperties hBaseConfigProperties;
    private final HBaseOperationProxy hBaseOperationProxy;


    private static ExecutorService pool = Executors.newScheduledThreadPool(20);    //设置hbase连接池

    public HBaseConfig(HBaseConfigProperties hBaseConfigProperties, HBaseOperationProxy hBaseOperationProxy) {
        this.hBaseConfigProperties = hBaseConfigProperties;
        this.hBaseOperationProxy = hBaseOperationProxy;
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

    /**
    * @describe hBase 数据操作接口
    * @param
    * @return
    * @author Jia wei Wu
    * @date 2021/3/28 9:32 下午
    **/
    @Bean
    public HBaseOperation hBaseOperation(){
        return (HBaseOperation) Proxy.newProxyInstance(HBaseOperation.class.getClassLoader(), new Class[]{HBaseOperation.class}, hBaseOperationProxy);
    }

}
