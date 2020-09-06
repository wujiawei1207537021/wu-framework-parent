package com.wuframework;


import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Import(WuframeworkConfig.class)
@Component
public @interface EnableAuthorizationServer { }
