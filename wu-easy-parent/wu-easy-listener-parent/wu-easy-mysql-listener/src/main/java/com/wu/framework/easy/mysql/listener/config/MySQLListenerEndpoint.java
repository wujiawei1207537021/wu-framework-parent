package com.wu.framework.easy.mysql.listener.config;

import com.wu.framework.easy.listener.core.config.ListenerEndpoint;
import com.wu.framework.easy.listener.stereotype.mysql.EasyMySQLListener;

import java.util.Collection;

public interface MySQLListenerEndpoint extends ListenerEndpoint {

    String getId();

    Collection<String> getTopics();

    /**
     * 语句
     *
     * @return
     */
    String getStatement();

    int getConcurrency();

    String getConsumer();

    // 模式
    EasyMySQLListener.Pattern getPattern();
}
