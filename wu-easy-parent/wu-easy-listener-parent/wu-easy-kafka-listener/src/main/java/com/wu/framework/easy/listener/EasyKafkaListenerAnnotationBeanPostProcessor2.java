//package com.wu.framework.easy.listener;
//
//import com.wu.framework.easy.com.wu.framework.easy.redis.listener.config.EasyKafkaListenerEndpointRegistrar;
//import com.wu.framework.easy.com.wu.framework.easy.redis.listener.config.KafkaConcurrentListenerContainerFactory;
//import com.wu.framework.easy.com.wu.framework.easy.redis.listener.config.MethodKafkaListenerEndpoint;
//import com.wu.framework.easy.listener.core.EasyListenerAnnotationBeanPostProcessor;
//import com.wu.framework.easy.listener.stereotype.EasyListener;
//import com.wu.framework.easy.listener.stereotype.ListenerProcessorStrategy;
//import com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListener;
//import org.springframework.aop.support.AopUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanInitializationException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.ListableBeanFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.core.MethodIntrospector;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.kafka.annotation.KafkaHandler;
//import org.springframework.lang.NonNull;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.ReflectionUtils;
//
//import javax.sql.DataSource;
//import java.lang.reflect.Method;
//import java.util.*;
//
//@ConditionalOnBean(value = DataSource.class)
//@ListenerProcessorStrategy(strategy = DynamicListenerType.KAFKA)
//public class EasyKafkaListenerAnnotationBeanPostProcessor<K, V> implements EasyListenerAnnotationBeanPostProcessor<EasyKafkaListener> {
//
//
//    private final EasyKafkaListenerEndpointRegistrar registrar = new EasyKafkaListenerEndpointRegistrar();
//
//
//    private BeanFactory beanFactory;
//
//
////    @Override
////    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
////        if (!this.nonAnnotatedClasses.contains(bean.getClass())) {
////            Class<?> targetClass = AopUtils.getTargetClass(bean);
////            Collection<EasyKafkaListener> classLevelListeners = findListenerAnnotations(targetClass);
////            final boolean hasClassLevelListeners = classLevelListeners.size() > 0;
////            final List<Method> multiMethods = new ArrayList<>();
////            Map<Method, Set<EasyKafkaListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
////                    (MethodIntrospector.MetadataLookup<Set<EasyKafkaListener>>) method -> {
////                        Set<EasyKafkaListener> listenerMethods = findListenerAnnotations(method);
////                        return (!listenerMethods.isEmpty() ? listenerMethods : null);
////                    });
////            if (hasClassLevelListeners) {
////                Set<Method> methodsWithHandler = MethodIntrospector.selectMethods(targetClass,
////                        (ReflectionUtils.MethodFilter) method ->
////                                AnnotationUtils.findAnnotation(method, KafkaHandler.class) != null);
////                multiMethods.addAll(methodsWithHandler);
////            }
////            if (annotatedMethods.isEmpty()) {
////                this.nonAnnotatedClasses.add(bean.getClass());
////                this.logger.trace(() -> "No @EasyKafkaListener annotations found on bean columnType: " + bean.getClass());
////            } else {
////                // Non-empty set of methods
////                for (Map.Entry<Method, Set<EasyKafkaListener>> entry : annotatedMethods.entrySet()) {
////                    Method method = entry.getKey();
////                    for (EasyKafkaListener listener : entry.getValue()) {
////                        processKafkaListener(listener, method, bean, beanName);
////                    }
////                }
////                this.logger.debug(() -> annotatedMethods.size() + " @EasyKafkaListener methods processed on bean '"
////                        + beanName + "': " + annotatedMethods);
////            }
////            if (hasClassLevelListeners) {
////                processMultiMethodListeners(classLevelListeners, multiMethods, bean, beanName);
////            }
////        }
////        return bean;
////    }
//
//    /**
//     * 处理 EasyListener 监听
//     *
//     * @param listener 监听者
//     * @param method   监听的方法
//     * @param bean     监听的对象
//     * @param beanName 监听对象的名称
//     */
//    @Override
//    public void processEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
//        final MethodKafkaListenerEndpoint endpoint = new MethodKafkaListenerEndpoint();
//        endpoint.setTopics(listener.topics());
//        endpoint.setConcurrency(Integer.parseInt(listener.concurrency()));
//
//        endpoint.setConsumer(com.wu.framework.easy.redis.listener.consumer());
//
//        processListenerEndpoint(endpoint, method, bean, beanName);
//    }
//
//    /**
//     * 处理注解中指定类型注解的 监听
//     *
//     * @param listener 监听者
//     * @param method   监听的方法
//     * @param bean     监听的对象
//     * @param beanName 监听对象的名称
//     */
//    @Override
//    public void processAssignEasyListener(EasyListener listener, Method method, Object bean, String beanName) {
//
//        final MethodKafkaListenerEndpoint endpoint = new MethodKafkaListenerEndpoint();
//        final EasyKafkaListener kafkaListener = listener.kafkaListener();
//        endpoint.setTopics(kafkaListener.topics());
//        endpoint.setConcurrency(Integer.parseInt(kafkaListener.concurrency()));
//        endpoint.setConsumer(kafkaListener.groupId());
//        processListenerEndpoint(endpoint, method, bean, beanName);
//    }
//
//    /**
//     * hasStrategy
//     *
//     * @param listener 监听者
//     * @return 返回数据
//     */
//    @Override
//    public Boolean hasStrategy(EasyListener listener) {
//        return !ObjectUtils.isEmpty(listener.kafkaListener().topics());
//    }
//
//    public void processListenerEndpoint(MethodKafkaListenerEndpoint endpoint, Method method, Object bean, String beanName) {
//        endpoint.setMethod(method);
//        endpoint.setBean(bean);
//        endpoint.setBeanName(beanName);
//        endpoint.setId(bean.getClass().getName() + "#" + method.getName());
//        KafkaConcurrentListenerContainerFactory<K, V> factory = new KafkaConcurrentListenerContainerFactory<>();
//        factory.setBeanFactory(beanFactory);
//        registrar.registerEndpoint(endpoint, factory);
//    }
//
//    /**
//     * Callback that supplies the owning factory to a bean instance.
//     * <p>Invoked after the population of normal bean properties
//     * but before an initialization callback such as
//     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
//     *
//     * @param beanFactory owning BeanFactory (never {@code null}).
//     *                    The bean can immediately call methods on the factory.
//     * @throws BeansException in case of initialization errors
//     * @see BeanInitializationException
//     */
//    @Override
//    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
//        registrar.setBeanFactory(beanFactory);
//        this.beanFactory = beanFactory;
//    }
//
//    /**
//     * Invoked right at the end of the singleton pre-instantiation phase,
//     * with a guarantee that all regular singleton beans have been created
//     * already. {@link ListableBeanFactory#getBeansOfType} calls within
//     * this method won't trigger accidental side effects during bootstrap.
//     * <p><b>NOTE:</b> This callback won't be triggered for singleton beans
//     * lazily initialized on demand after {@link BeanFactory} bootstrap,
//     * and not for any other bean scope either. Carefully use it for beans
//     * with the intended bootstrap semantics only.
//     */
//    @Override
//    public void afterSingletonsInstantiated() {
//
//    }
//
//    /**
//     * Get the order value of this object.
//     * <p>Higher values are interpreted as lower priority. As a consequence,
//     * the object with the lowest value has the highest priority (somewhat
//     * analogous to Servlet {@code load-on-startup} values).
//     * <p>Same order values will result in arbitrary sort positions for the
//     * affected objects.
//     *
//     * @return the order value
//     * @see #HIGHEST_PRECEDENCE
//     * @see #LOWEST_PRECEDENCE
//     */
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
