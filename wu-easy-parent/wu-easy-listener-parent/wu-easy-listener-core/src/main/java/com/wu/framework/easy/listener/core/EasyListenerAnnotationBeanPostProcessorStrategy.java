package com.wu.framework.easy.listener.core;


import com.wu.framework.easy.listener.DynamicListenerType;
import com.wu.framework.easy.listener.stereotype.EasyHandler;
import com.wu.framework.easy.listener.stereotype.EasyListener;
import com.wu.framework.easy.listener.stereotype.ListenerProcessorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.*;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.log.LogAccessor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * describe : EasyListenerAnnotationBeanPostProcessor 注解➕策略
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/15 10:04 下午
 */
@Slf4j
public class EasyListenerAnnotationBeanPostProcessorStrategy implements
        BeanPostProcessor, Ordered, ApplicationContextAware, InitializingBean, SmartInitializingSingleton {


    private final List<EasyListenerAnnotationBeanPostProcessor> easyListenerAnnotationBeanPostProcessorList;

    private final Map<DynamicListenerType, EasyListenerAnnotationBeanPostProcessor> dynamicListenerTypeEasyListenerAnnotationBeanPostProcessorMap = new ConcurrentHashMap<>();


    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

    private final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

    private AnnotationEnhancer enhancer;
    private ApplicationContext applicationContext;


    public EasyListenerAnnotationBeanPostProcessorStrategy(List<EasyListenerAnnotationBeanPostProcessor> easyListenerAnnotationBeanPostProcessorList) {
        this.easyListenerAnnotationBeanPostProcessorList = easyListenerAnnotationBeanPostProcessorList;
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

        log.info("初始化加载简单的侦听器注释 Bean 后处理器列表:【{}】个", easyListenerAnnotationBeanPostProcessorList.size());
        for (EasyListenerAnnotationBeanPostProcessor postProcessor : easyListenerAnnotationBeanPostProcessorList) {
            final ListenerProcessorStrategy strategy = AnnotatedElementUtils.findMergedAnnotation(postProcessor.getClass(), ListenerProcessorStrategy.class);
            dynamicListenerTypeEasyListenerAnnotationBeanPostProcessorMap.put(strategy.strategy(), postProcessor);
            log.info("初始化加载简单的侦听器处理器:【{}】", strategy.strategy());
        }
        buildEnhancer();
    }


    private void buildEnhancer() {
        if (this.applicationContext != null) {
            Map<String, AnnotationEnhancer> enhancersMap =
                    this.applicationContext.getBeansOfType(AnnotationEnhancer.class, false, false);
            if (!enhancersMap.isEmpty()) {
                List<AnnotationEnhancer> enhancers = enhancersMap.values()
                        .stream()
                        .sorted(new OrderComparator())
                        .collect(Collectors.toList());
                this.enhancer = (attrs, element) -> {
                    Map<String, Object> newAttrs = attrs;
                    for (AnnotationEnhancer enh : enhancers) {
                        newAttrs = enh.apply(newAttrs, element);
                    }
                    return attrs;
                };
            }
        }
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
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Apply this {@code BeanPostProcessor} to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>The default implementation returns the given {@code bean} as-is.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (!this.nonAnnotatedClasses.contains(bean.getClass())) {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            Collection<EasyListener> classLevelListeners = findListenerAnnotations(targetClass, EasyListener.class);
            final boolean hasClassLevelListeners = classLevelListeners.size() > 0;
            final List<Method> multiMethods = new ArrayList<>();
            Map<Method, Set<EasyListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                    (MethodIntrospector.MetadataLookup<Set<EasyListener>>) method -> {
                        Collection<EasyListener> listenerMethods = findListenerAnnotations(method, EasyListener.class);
                        return (!listenerMethods.isEmpty() ? new HashSet<>(listenerMethods) : null);
                    });
            if (hasClassLevelListeners) {
                Set<Method> methodsWithHandler = MethodIntrospector.selectMethods(targetClass,
                        (ReflectionUtils.MethodFilter) method ->
                                AnnotationUtils.findAnnotation(method, EasyHandler.class) != null);
                multiMethods.addAll(methodsWithHandler);
            }
            if (annotatedMethods.isEmpty()) {
                this.nonAnnotatedClasses.add(bean.getClass());
                this.logger.trace(() -> "No @EasyListener annotations found on bean columnType: " + bean.getClass());
            } else {
                // Non-empty set of methods
                for (Map.Entry<Method, Set<EasyListener>> entry : annotatedMethods.entrySet()) {
                    Method method = entry.getKey();
                    for (EasyListener listener : entry.getValue()) {
                        processListener(listener, method, bean, beanName);
                    }
                }
                this.logger.debug(() -> annotatedMethods.size() + " @EasyListener methods processed on bean '"
                        + beanName + "': " + annotatedMethods);
            }
            if (hasClassLevelListeners) {
                processMultiMethodListeners(classLevelListeners, multiMethods, bean, beanName);
            }
        }
        return bean;
    }

    private void processMultiMethodListeners(Collection<EasyListener> classLevelListeners, List<Method> multiMethods, Object bean, String beanName) {


    }

    private void processListener(EasyListener listener, Method method, Object bean, String beanName) {
        // 根据 EasyListener 找到其中不同的策略
        // 计算所有的策略
        dynamicListenerTypeEasyListenerAnnotationBeanPostProcessorMap.forEach((dynamicListenerType, easyListenerAnnotationBeanPostProcessor) -> {
            Boolean hasStrategy = easyListenerAnnotationBeanPostProcessor.hasStrategy(listener);
            if (hasStrategy) {
                // 使用策略 指定注解标签
                easyListenerAnnotationBeanPostProcessor.processAssignEasyListener(listener, method, bean, beanName);
            } else {
                if (!ObjectUtils.isEmpty(listener.topics())) {
                    easyListenerAnnotationBeanPostProcessor.processEasyListener(listener, method, bean, beanName);
                }
            }

        });
    }


    private <A extends Annotation> Collection<A> findListenerAnnotations(AnnotatedElement element, Class<A> annotationType) {
        Set<A> listeners = new HashSet<>();
        A ann = AnnotatedElementUtils.findMergedAnnotation(element, annotationType);
        if (ann != null) {
            ann = enhance(element, ann, annotationType);
            listeners.add(ann);
        }
        return listeners;
    }

    private <A extends Annotation> A enhance(AnnotatedElement element, A ann, Class<A> annotationType) {
        if (this.enhancer == null) {
            return ann;
        } else {
            return AnnotationUtils.synthesizeAnnotation(
                    this.enhancer.apply(AnnotationUtils.getAnnotationAttributes(ann), element), annotationType, null);
        }
    }

    /**
     * Post processes each set of annotation attributes.
     *
     * @since 2.7.2
     */
    public interface AnnotationEnhancer extends BiFunction<Map<String, Object>, AnnotatedElement, Map<String, Object>> {

    }
}
