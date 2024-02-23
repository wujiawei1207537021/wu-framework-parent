package com.wu.framework.easy.listener.core.config;


import com.wu.framework.easy.listener.core.GenericMessageListenerContainer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/17 7:32 下午
 */
public interface SingletonListenerContainerFactory<C extends GenericMessageListenerContainer<K, V>, Endpoint extends ListenerEndpoint, K, V>
        extends ListenerContainerFactory<C, Endpoint>, ApplicationEventPublisherAware, InitializingBean, ApplicationContextAware {


}
