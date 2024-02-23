package com.wu.framework.easy.redis.listener.config;


import com.wu.framework.easy.listener.core.config.ListenerEndpointRegistrar;
import com.wu.framework.easy.redis.listener.RedisListenerEndpointRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class EasyRedisListenerEndpointRegistrar implements ListenerEndpointRegistrar<MethodRedisListenerEndpoint, RedisConcurrentListenerContainerFactory> {


    RedisListenerEndpointRegistry registry = new RedisListenerEndpointRegistry();


    private boolean startImmediately = true;

    private BeanFactory beanFactory;

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
        this.beanFactory = beanFactory;
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

    public RedisListenerEndpointRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(RedisListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    public boolean isStartImmediately() {
        return startImmediately;
    }

    public void setStartImmediately(boolean startImmediately) {
        this.startImmediately = startImmediately;
    }

    /**
     * 注册 端点
     *
     * @param endpoint
     * @param factory
     */
    @Override
    public void registerEndpoint(MethodRedisListenerEndpoint endpoint, RedisConcurrentListenerContainerFactory factory) {
        Assert.notNull(endpoint, "Endpoint must be set");

        if (this.startImmediately) { // Register and start immediately
            this.registry.registerListenerContainer(endpoint, factory, startImmediately);
        }
    }
}
