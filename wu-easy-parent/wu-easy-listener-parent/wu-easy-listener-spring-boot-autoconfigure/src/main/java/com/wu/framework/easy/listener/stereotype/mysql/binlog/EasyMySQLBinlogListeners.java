package com.wu.framework.easy.listener.stereotype.mysql.binlog;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Indexed
public @interface EasyMySQLBinlogListeners {

    EasyMySQLBinlogListener[] value();
}