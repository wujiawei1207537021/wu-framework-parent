package com.wu.framework.easy.listener.config;

import com.wu.framework.easy.listener.EasyKafkaListenerEndpointRegistry;
import com.wu.framework.easy.listener.core.config.ListenerEndpointRegistrar;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.util.Assert;

/**
 * Helper bean for registering {@link KafkaListenerEndpoint} with
 * a {@link KafkaListenerEndpointRegistry}.
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @author Artem Bilan
 * @author Gary Russell
 * @author Filip Halemba
 * @see org.springframework.kafka.annotation.KafkaListenerConfigurer
 */
public class EasyKafkaListenerEndpointRegistrar implements ListenerEndpointRegistrar<MethodKafkaListenerEndpoint, KafkaConcurrentListenerContainerFactory> {

    private final boolean startImmediately = true;
    EasyKafkaListenerEndpointRegistry registry = new EasyKafkaListenerEndpointRegistry();
    private BeanFactory beanFactory;

    /**
     * 注册 端点
     *
     * @param endpoint 端点
     * @param factory  工厂
     */
    @Override
    public void registerEndpoint(MethodKafkaListenerEndpoint endpoint, KafkaConcurrentListenerContainerFactory factory) {
        Assert.notNull(endpoint, "Endpoint must be set");

        if (this.startImmediately) { // Register and start immediately
            this.registry.registerListenerContainer(endpoint, factory, startImmediately);
        }
    }

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
     *
     * @param beanFactory owning BeanFactory (never {@code null}).
     *                    The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     * @see BeanInitializationException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
