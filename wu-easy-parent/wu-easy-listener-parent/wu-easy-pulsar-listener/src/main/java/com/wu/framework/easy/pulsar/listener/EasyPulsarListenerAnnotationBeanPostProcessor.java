package com.wu.framework.easy.pulsar.listener;

import com.wu.framework.easy.listener.DynamicListenerType;
import com.wu.framework.easy.listener.core.AbstractEasyListenerAnnotationBeanPostProcessor;
import com.wu.framework.easy.listener.stereotype.EasyListener;
import com.wu.framework.easy.listener.stereotype.ListenerProcessorStrategy;
import com.wu.framework.easy.listener.stereotype.pulsar.EasyPulsarListener;
import com.wu.framework.easy.pulsar.config.EasyPulsarListenerEndpointRegistrar;
import com.wu.framework.easy.pulsar.config.MethodPulsarListenerEndpoint;
import com.wu.framework.easy.pulsar.config.PulsarConcurrentListenerContainerFactory;
import com.wu.framework.easy.pulsar.config.PulsarConfigProperties;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@ConditionalOnProperty(prefix = PulsarConfigProperties.PULSAR_PREFIX, value = "url")
@ListenerProcessorStrategy(strategy = DynamicListenerType.PULSAR)
public class EasyPulsarListenerAnnotationBeanPostProcessor<K, V> extends AbstractEasyListenerAnnotationBeanPostProcessor<EasyPulsarListener> {


    private EasyPulsarListenerEndpointRegistrar registrar = new EasyPulsarListenerEndpointRegistrar();


    private BeanFactory beanFactory;

    /**
     * 类注解普遍方法监听
     *
     * @param classLevelListeners
     * @param multiMethods
     * @param bean
     * @param beanName
     */
    @Override
    public void processMultiMethodListeners(Collection<EasyPulsarListener> classLevelListeners, List<Method> multiMethods, Object bean, String beanName) {

    }

    /**
     * 监听注解
     *
     * @param listener
     * @param method
     * @param bean
     * @param beanName
     */
    @Override
    public void processListener(EasyPulsarListener listener, Method method, Object bean, String beanName) {
        final String[] topics = listener.topics();
        Assert.notNull(topics, "'topics' must not be null ");

        MethodPulsarListenerEndpoint endpoint = new MethodPulsarListenerEndpoint();
        endpoint.setTopics(topics);
        endpoint.setSubscriptionName(listener.subscriptionName());
        endpoint.setSubscriptionType(listener.subscriptionType());
        endpoint.setMethod(method);
        endpoint.setBean(bean);
        endpoint.setId(bean.getClass().getName() + "#" + method.getName());
        processListenerEndpoint(endpoint, method, bean, beanName);
    }

    /**
     * 处理 EasyListener 监听
     *
     * @param listener
     * @param method
     * @param bean
     * @param beanName
     */
    @Override
    public void processEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
        final MethodPulsarListenerEndpoint endpoint = new MethodPulsarListenerEndpoint();
        endpoint.setTopics(listener.topics());
        endpoint.setSubscriptionName(listener.consumer());
        endpoint.setMethod(method);
        endpoint.setBean(bean);
        endpoint.setId(bean.getClass().getName() + "#" + method.getName());
        endpoint.setSubscriptionType(SubscriptionType.Shared);
        endpoint.setConcurrency(Integer.parseInt(listener.concurrency()));
        processListenerEndpoint(endpoint, method, bean, beanName);
    }

    /**
     * 处理注解中指定类型注解的 监听
     *
     * @param listener
     * @param method
     * @param bean
     * @param beanName
     */
    @Override
    public void processAssignEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
        final EasyPulsarListener pulsarListener = listener.pulsarListener();

        processListener(pulsarListener, method, bean, beanName);

    }

    /**
     * hasStrategy
     *
     * @param listener 监听者
     * @return 返回数据
     */
    @Override
    public Boolean hasStrategy(EasyListener listener) {
        return !ObjectUtils.isEmpty(listener.pulsarListener().topics());
    }

    public void processListenerEndpoint(MethodPulsarListenerEndpoint endpoint, Method method, Object bean, String beanName) {
        endpoint.setMethod(method);
        endpoint.setBean(bean);
        endpoint.setBeanName(beanName);
        PulsarConcurrentListenerContainerFactory factory = new PulsarConcurrentListenerContainerFactory();
        factory.setBeanFactory(beanFactory);
        registrar.registerEndpoint(endpoint, factory);
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

    /**
     * Invoked right at the end of the singleton pre-instantiation phase,
     * with a guarantee that all regular singleton beans have been created
     * already. {@link ListableBeanFactory#getBeansOfType} calls within
     * this method won't trigger accidental side effects during bootstrap.
     * <p><b>NOTE:</b> This callback won't be triggered for singleton beans
     * lazily initialized on demand after {@link BeanFactory} bootstrap,
     * and not for any other bean scope either. Carefully use it for beans
     * with the intended bootstrap semantics only.
     */
    @Override
    public void afterSingletonsInstantiated() {

    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
