package com.wu.framework.easy.listener.core;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * describe : 抽象消息侦听器容器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/17 6:16 下午
 */
public abstract class AbstractMessageListenerContainer<K, V> implements GenericMessageListenerContainer<K, V>, BeanNameAware, ApplicationEventPublisherAware,
        ApplicationContextAware {


}
