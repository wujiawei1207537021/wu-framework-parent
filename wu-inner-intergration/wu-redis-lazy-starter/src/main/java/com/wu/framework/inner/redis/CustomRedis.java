package com.wu.framework.inner.redis;

import org.springframework.beans.factory.InitializingBean;

public interface CustomRedis extends InitializingBean {

    default void initCustomRedis() {
        System.out.println(this.getClass().getName() + "使用自定义一redis");
    }

    ;

    @Override
    default void afterPropertiesSet() throws Exception {
        System.out.println(this.getClass().getName() + "使用自定义一redis");
    }

    ;
}
