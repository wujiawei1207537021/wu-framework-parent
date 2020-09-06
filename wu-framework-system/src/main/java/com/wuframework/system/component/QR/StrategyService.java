package com.wuframework.system.component.QR;

import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 策略 管理bean 通过名称获取bean
 * strategy
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
@Documented
@Service
public @interface StrategyService {
    String value();
}
