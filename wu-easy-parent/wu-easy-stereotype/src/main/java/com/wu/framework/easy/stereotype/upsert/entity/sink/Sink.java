package com.wu.framework.easy.stereotype.upsert.entity.sink;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/7/16 下午1:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sink {

    public static String SOURCE_CONNECTOR_PREFIX = "source:";
    public static String SINK_CONNECTOR_PREFIX = "sink:";

    public static String CONFIG_KEY_NAME = "tableName";
    public static String CONFIG_KEY_CONNECTOR_CLASS = "connector.class";
    public static String CONFIG_KEY_VALUE_CONVERTER = "value.converter";

    /***
     * 任务名称
     */
    @NonNull
    private String name;
    /**
     * 数据源类型 1：数据库 2：jms 3：redis 4：http 5：ibmmq
     */
    private Integer sourceType;
    /**
     * 数据源任务参数  {"connection.url":"jdbc:mysql:\\127.0.0.1:3306\mysql"}
     */
    private Map<String, String> sourceConfig;

    /**
     * 目标源类型  1：数据库 2：elasticsearch 3：http
     */
    private Integer sinkType;

    /**
     * 目标源任务参数  {"connection.url":"jdbc:mysql:\\127.0.0.1:3306\mysql"}
     */
    private Map<String, String> sinkConfig;

}
