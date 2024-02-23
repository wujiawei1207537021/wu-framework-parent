package com.wu.framework.easy.mysql.binlog.listener.config;


import com.wu.framework.easy.listener.core.config.ListenerEndpoint;
import com.wu.framework.easy.listener.stereotype.mysql.binlog.EasyMySQLBinlogListener;

import java.util.Collection;

public interface MySQLBinlogListenerEndpoint extends ListenerEndpoint {

    String getId();

    Collection<String> getTables();

    String getSchema();

    int getConcurrency();

    String getConsumer();

    // 模式
    EasyMySQLBinlogListener.Pattern[] getPattern();

    /**
     * 数据库host 默认是空 按照当前数据源解析
     */
    String getHost();

    /**
     * 数据库端口
     */
    String getPort();

    /**
     * 数据库密码
     */

    String getPassword();


    /**
     * 数据库账号
     */
    String getUsername();

    /**
     * serverId
     */
    Long getServerId();


}
