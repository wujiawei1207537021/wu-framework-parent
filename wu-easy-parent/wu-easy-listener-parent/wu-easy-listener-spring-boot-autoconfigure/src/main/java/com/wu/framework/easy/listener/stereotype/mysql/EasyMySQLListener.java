package com.wu.framework.easy.listener.stereotype.mysql;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;


/**
 * 开启日志模式(推荐)
 * <p>
 * -- 1、设置
 * <p>
 * -- SET GLOBAL log_output = 'TABLE';SET GLOBAL general_log = 'ON';  //日志开启
 * <p>
 * -- SET GLOBAL log_output = 'TABLE'; SET GLOBAL general_log = 'OFF';  //日志关闭
 * <p>
 * -- 2、查询
 * <p>
 * SELECT * from mysql.general_log ORDER BY event_time DESC;
 * <p>
 * -- 3、清空表（delete对于这个表，不允许使用，只能用truncate）
 * <p>
 * -- truncate table mysql.general_log;
 */

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Repeatable(EasyMySQLListeners.class)
@Indexed
public @interface EasyMySQLListener {


    /**
     * 主题
     *
     * @return 主题(表)
     */
    String[] topics() default {};

    /**
     * 线程数量
     *
     * @return 线程数量
     */
    String concurrency() default "1";

    /**
     * 执行sql语句
     *
     * @return 执行sql语句
     */
    String statement() default "";

    /**
     * 刷新时间 毫秒
     *
     * @return 刷新时间
     */
    int sleep() default 100;

    // 消费者
    String consumer() default "#default";

    // 模式 返回数据 GeneralLog
    Pattern pattern() default Pattern.GENERAL_LOG;

    enum Pattern {
        // 监听执行语句
        STATEMENT,
        //GeneralLog
        GENERAL_LOG

    }


}

