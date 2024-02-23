package com.wu.framework.easy.pulsar.config;

import com.wu.framework.easy.listener.core.config.ConcurrentListenerContainerFactory;
import com.wu.framework.easy.pulsar.listener.PulsarConcurrentMessageListenerContainer;
import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.*;

import java.util.regex.Pattern;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2021/10/19$ 2:15 下午$
 */
public class PulsarConcurrentListenerContainerFactory<K, V> implements
        ConcurrentListenerContainerFactory<PulsarConcurrentMessageListenerContainer<K, V>, MethodPulsarListenerEndpoint, K, V> {


    protected BeanFactory beanFactory;

    @Override
    public PulsarConcurrentMessageListenerContainer<K, V> createListenerContainer(MethodPulsarListenerEndpoint endpoint) {

        final PulsarConcurrentMessageListenerContainer<K, V> container = new PulsarConcurrentMessageListenerContainer<K, V>();
        container.setConcurrency(endpoint.getConcurrency());
        container.setPulsarClient(beanFactory.getBean(PulsarClient.class));
        container.setEndpoint(endpoint);
        return container;
    }

    @Override
    public PulsarConcurrentMessageListenerContainer<K, V> createContainer(String... topics) {
        return null;
    }

    @Override
    public PulsarConcurrentMessageListenerContainer<K, V> createContainer(Pattern topicPattern) {
        return null;
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

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    /**
     * Set the ApplicationEventPublisher that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

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
        this.beanFactory = beanFactory;
    }
}
