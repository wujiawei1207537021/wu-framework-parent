package com.wu.framework.easy.listener.stereotype.mysql.binlog;

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
 *
 * @author Jia wei Wu
 */

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Repeatable(EasyMySQLBinlogListeners.class)
@Indexed
public @interface EasyMySQLBinlogListener {


    /**
     * 数据库host 默认是空 按照当前数据源解析
     *
     * @return
     */

    String host() default "";

    /**
     * 数据库端口
     *
     * @return
     */
    String port() default "";

    /**
     * 数据库密码
     *
     * @return
     */
    String password() default "";

    /**
     * 数据库账号
     *
     * @return
     */
    String username() default "";

    /**
     * serverId
     */
    long serverId() default 65535L;


    /**
     * 数据库表
     */
    String[] tables() default {};

    /**
     * 默认的数据库
     *
     * @return
     */
    String schema() default "";

    /**
     * 线程数量
     *
     * @return
     */
    String concurrency() default "1";

    /**
     * 消费者
     *
     * @return
     */
    String consumer() default "#default";


    // 模式 返回数据 GeneralLog
    EasyMySQLBinlogListener.Pattern[] pattern() default {EasyMySQLBinlogListener.Pattern.UPDATE, EasyMySQLBinlogListener.Pattern.INSERT, EasyMySQLBinlogListener.Pattern.DELETE};

    enum Pattern {
        // 更新操作
        UPDATE,
        // 创建操作
        INSERT,
        // 删除 待实现
        DELETE,
    }


}

