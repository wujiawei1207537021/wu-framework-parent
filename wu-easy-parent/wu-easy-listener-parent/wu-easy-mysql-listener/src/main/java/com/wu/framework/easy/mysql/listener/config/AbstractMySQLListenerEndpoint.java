package com.wu.framework.easy.mysql.listener.config;


import com.wu.framework.easy.listener.stereotype.mysql.EasyMySQLListener;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractMySQLListenerEndpoint implements MySQLListenerEndpoint {
    private final Collection<String> topics = new ArrayList<>();


    private String id;

    private int concurrency = 1;

    private String statement;

    private long sleep;


    // 消费者
    private String consumer;
    // 模式
    private EasyMySQLListener.Pattern pattern;


    @Override
    public Collection<String> getTopics() {
        return topics;
    }

    public void setTopics(String... topics) {
        Assert.notNull(topics, "'topics' must not be null ");
        this.topics.clear();
        this.topics.addAll(Arrays.asList(topics));
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
    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public long getSleep() {
        return sleep;
    }

    public void setSleep(long sleep) {
        this.sleep = sleep;
    }

    @Override
    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Override
    public EasyMySQLListener.Pattern getPattern() {
        return pattern;
    }

    public void setPattern(EasyMySQLListener.Pattern pattern) {
        this.pattern = pattern;
    }
}
