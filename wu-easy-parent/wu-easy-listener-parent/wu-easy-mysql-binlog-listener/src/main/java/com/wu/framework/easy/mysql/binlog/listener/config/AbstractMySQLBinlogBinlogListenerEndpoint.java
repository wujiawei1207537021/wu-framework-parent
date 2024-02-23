package com.wu.framework.easy.mysql.binlog.listener.config;


import com.wu.framework.easy.listener.stereotype.mysql.binlog.EasyMySQLBinlogListener;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractMySQLBinlogBinlogListenerEndpoint implements MySQLBinlogListenerEndpoint {
    private final Collection<String> tables = new ArrayList<>();


    private String id;

    private int concurrency = 1;

    private String schema;
    // 消费者
    private String consumer;
    // 模式
    private EasyMySQLBinlogListener.Pattern[] pattern;

    /**
     * 数据库host 默认是空 按照当前数据源解析
     */
    private String host;

    /**
     * 数据库端口
     */
    private String port;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库账号
     */
    private String username;
    /**
     * serverId
     */
    private Long serverId = 65535L;


    @Override
    public Collection<String> getTables() {
        return tables;
    }

    public void setTables(String... tables) {
        Assert.notNull(tables, "'tables' must not be null ");
        this.tables.clear();
        this.tables.addAll(Arrays.asList(tables));
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }


    @Override
    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Override
    public EasyMySQLBinlogListener.Pattern[] getPattern() {
        return pattern;
    }

    public void setPattern(EasyMySQLBinlogListener.Pattern[] pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
