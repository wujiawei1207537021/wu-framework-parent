package com.wu.framework.easy.mysql.binlog.listener.listener;


import com.wu.framework.easy.listener.DynamicListenerType;
import com.wu.framework.easy.listener.core.AbstractEasyListenerAnnotationBeanPostProcessor;
import com.wu.framework.easy.listener.stereotype.EasyListener;
import com.wu.framework.easy.listener.stereotype.ListenerProcessorStrategy;
import com.wu.framework.easy.listener.stereotype.mysql.binlog.EasyMySQLBinlogListener;
import com.wu.framework.easy.mysql.binlog.listener.config.DefaultTableAdapter;
import com.wu.framework.easy.mysql.binlog.listener.config.EasyMySQLBinlogListenerEndpointRegistrar;
import com.wu.framework.easy.mysql.binlog.listener.config.MethodMySQLBinlogBinlogListenerEndpoint;
import com.wu.framework.easy.mysql.binlog.listener.config.MySQLBinlogConcurrentListenerContainerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * @author wujiawei
 */
@ConditionalOnProperty(prefix = "spring.datasource", value = "enable-binlog", havingValue = "true")
@ListenerProcessorStrategy(strategy = DynamicListenerType.BINLOG)
@Import(DefaultTableAdapter.class)
public class EasyBinlogListenerAnnotationBeanPostProcessor<K, V> extends AbstractEasyListenerAnnotationBeanPostProcessor<EasyMySQLBinlogListener> {


    private final EasyMySQLBinlogListenerEndpointRegistrar registrar = new EasyMySQLBinlogListenerEndpointRegistrar();


    private BeanFactory beanFactory;


    /**
     * 类注解普遍方法监听
     *
     * @param classLevelListeners 监听注解修饰的类
     * @param multiMethods        监听注解修饰类中的方法
     * @param bean                监听注解修饰对象
     * @param beanName            监听注解修饰对象名称
     */
    @Override
    public void processMultiMethodListeners(Collection<EasyMySQLBinlogListener> classLevelListeners, List<Method> multiMethods, Object bean, String beanName) {

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
    public void processListener(EasyMySQLBinlogListener listener, Method method, Object bean, String beanName) {
        final MethodMySQLBinlogBinlogListenerEndpoint endpoint = new MethodMySQLBinlogBinlogListenerEndpoint();
        endpoint.setTables(listener.tables());
        endpoint.setSchema(listener.schema());
        endpoint.setConcurrency(Integer.parseInt(listener.concurrency()));
        endpoint.setConsumer(listener.consumer());
        endpoint.setPattern(listener.pattern());
        endpoint.setServerId(listener.serverId());
        endpoint.setHost(listener.host());
        endpoint.setPort(listener.port());
        endpoint.setUsername(listener.username());
        endpoint.setPassword(listener.password());
        endpoint.setId(bean.getClass().getName() + "#" + method.getName());
        processListenerEndpoint(endpoint, method, bean, beanName);
    }

    /**
     * 处理 EasyListener 监听
     *
     * @param listener 监听者
     * @param method   监听的方法
     * @param bean     监听的对象
     * @param beanName 监听对象的名称
     */
    @Override
    public void processEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
        final MethodMySQLBinlogBinlogListenerEndpoint endpoint = new MethodMySQLBinlogBinlogListenerEndpoint();
        endpoint.setTables(listener.topics());
        endpoint.setConcurrency(Integer.parseInt(listener.concurrency()));
        endpoint.setConsumer(listener.consumer());
        EasyMySQLBinlogListener.Pattern[] patterns = {EasyMySQLBinlogListener.Pattern.INSERT, EasyMySQLBinlogListener.Pattern.UPDATE,
                EasyMySQLBinlogListener.Pattern.DELETE};
        endpoint.setPattern(patterns);
        endpoint.setId(bean.getClass().getName() + "#" + method.getName());
        processListenerEndpoint(endpoint, method, bean, beanName);
    }

    /**
     * 处理注解中指定类型注解的 监听
     *
     * @param listener 监听者
     * @param method   监听的方法
     * @param bean     监听的对象
     * @param beanName 监听对象的名称
     */
    @Override
    public void processAssignEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
        EasyMySQLBinlogListener binlogListener = listener.binlogListener();
        processListener(binlogListener, method, bean, beanName);
    }

    /**
     * hasStrategy
     *
     * @param listener 监听者
     * @return 返回数据
     */
    @Override
    public Boolean hasStrategy(EasyListener listener) {
        return !ObjectUtils.isEmpty(listener.binlogListener().tables());
    }

    public void processListenerEndpoint(MethodMySQLBinlogBinlogListenerEndpoint endpoint, Method method, Object bean, String beanName) {
        endpoint.setMethod(method);
        endpoint.setBean(bean);
        endpoint.setBeanName(beanName);
        MySQLBinlogConcurrentListenerContainerFactory<K, V> factory = new MySQLBinlogConcurrentListenerContainerFactory<>();
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
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        registrar.setBeanFactory(beanFactory);
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
