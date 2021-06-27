package com.wu.framework.easy.stereotype.upsert.converter;


import com.alibaba.fastjson.JSON;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.EasyAnnotationConverter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description 将class 转换为 Sink 任务
 *
 * @author Jia wei Wu
 * @date 2020/7/16 下午1:43
 */
public class ConverterClass2SinkConfig {

    private final static String MYSQL_URL = "jdbc:mysql://172.17.1.58:30806/baseinfo";
    private final static String MYSQL_USER = "baseinfo";
    private final static String MYSQL_PASSWORD = "supconit";
    private final static String INSERT_MODE = "upsert";
    private final static String ES_URL = "http://172.17.1.58:30820";

    /**
     * ### sink任务toDB
     * <p>
     * {
     * "tableName": "net_car_company_db",
     * "sinkConfig": {
     * "topics": "connect_sink_net_car_company_db",
     * "connection.url": "jdbc:mysql://172.17.1.58:30806/baseinfo",
     * "connection.user": "root",
     * "connection.password": "supconit",
     * "table.tableName.format": "ct_taxi_rh_org_opemng_bas_comp",
     * "pk.fields": "comp_id",
     * "db.timezone": "Asia/Shanghai",
     * "insert.mode": "upsert"
     * },
     * "sinkType": 1
     * }
     */
    public static Map DBConfig(Class clazz, String namePrefix, String url, String user, String password) {
        // 数据库名称
        String name = EasyAnnotationConverter.getCustomTableValue(clazz);

        // topic
        String topic = getKafkaTopicName(clazz);
        Map sink = new HashMap();
        if (!ObjectUtils.isEmpty(namePrefix)) {
            sink.put("name", namePrefix + name + "_todb");
        } else {
            sink.put("name", name + "_todb");
        }
        Map sinkConfig = new HashMap();
        sink.put("sinkConfig", sinkConfig);
        sink.put("sinkType", 1);
        sinkConfig.put("topics", topic);
        sinkConfig.put("connection.url", url);
        sinkConfig.put("connection.user", user);
        sinkConfig.put("connection.password", password);
        sinkConfig.put("table.name.format", name);
        if (!ObjectUtils.isEmpty(EasyAnnotationConverter.getCustomUniquePK(clazz))) {
            sinkConfig.put("pk.fields", EasyAnnotationConverter.getCustomUniquePK(clazz).stream().collect(Collectors.joining(",")));
        }
        sinkConfig.put("db.timezone", "Asia/Shanghai");
        sinkConfig.put("insert.mode", INSERT_MODE);
        System.out.println(JSON.toJSONString(sink));
        return sink;
    }


    public static Map DBConfig(Class clazz, String namePrefix) {
        return DBConfig(clazz, namePrefix, MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
    }

    public static Map DBConfig(Class clazz) {
        return DBConfig(clazz, null);
    }


    /**
     * ### sink任务toES
     * <p>
     * {
     * "tableName": "net_car_company_toes",
     * "sinkConfig": {
     * "topics": "connect_sink_net_car_company_es",
     * "connection.url": "http://172.17.1.58:30820",
     * "index.format": "net_car_company_es_${timestamp}",
     * "timestamp.format": "yyyy.MM",
     * "key.ignore": "true"
     * },
     * "sinkType": 2
     * }
     *
     * @param clazz
     * @return
     */
    public static Map ESConfig(Class clazz) {
        // 数据库名称
        String name = EasyAnnotationConverter.getCustomTableValue(clazz);
        // topic
        String topic = getKafkaTopicName(clazz);
        Map sink = new HashMap();
        sink.put("name", name + "_toes");
        Map sinkConfig = new HashMap();
        sink.put("sinkConfig", sinkConfig);
        sink.put("sinkType", 2);
        sinkConfig.put("topics", topic);
        sinkConfig.put("connection.url", ES_URL);
        sinkConfig.put("index.format", topic + "_${timestamp}");
        sinkConfig.put("timestamp.format", "yyyy.MM");
        sinkConfig.put("key.ignore", "true");
        System.out.println(JSON.toJSONString(sink));
        return sink;
    }

    public static String getKafkaTopicName(Class clazz) {
        EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null != easySmart && !ObjectUtils.isEmpty(easySmart.kafkaTopicName())) {
            return easySmart.kafkaTopicName();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

}
