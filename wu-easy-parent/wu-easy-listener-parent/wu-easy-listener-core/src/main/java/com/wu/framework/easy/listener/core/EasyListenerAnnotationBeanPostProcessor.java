package com.wu.framework.easy.listener.core;


import com.wu.framework.easy.listener.stereotype.EasyListener;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * describe : EasyListenerAnnotationBeanPostProcessor  简单的侦听器注释 Bean 后处理器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/15 10:04 下午
 */
public interface EasyListenerAnnotationBeanPostProcessor<L extends Annotation> extends BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {


    EasyListenerAnnotationBeanPostProcessorStrategy.AnnotationEnhancer ENHANCER = null;

    @Override
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        final Class<L> listenerClass = getListenerClass();
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Collection<L> classLevelListeners = findListenerAnnotations(targetClass, listenerClass);
        final boolean hasClassLevelListeners = classLevelListeners.size() > 0;
        final List<Method> multiMethods = new ArrayList<>();
        Map<Method, Set<L>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<Set<L>>) method -> {
                    Collection<L> listenerMethods = findListenerAnnotations(method, listenerClass);
                    return (!listenerMethods.isEmpty() ? new HashSet<>(listenerMethods) : null);
                });

        // Non-empty set of methods
        for (Map.Entry<Method, Set<L>> entry : annotatedMethods.entrySet()) {
            Method method = entry.getKey();
            for (L listener : entry.getValue()) {
                processListener(listener, method, bean, beanName);
            }

        }
        if (hasClassLevelListeners) {
            processMultiMethodListeners(classLevelListeners, multiMethods, bean, beanName);
        }

        return bean;
    }

    /**
     * 类注解普遍方法监听
     *
     * @param classLevelListeners
     * @param multiMethods
     * @param bean
     * @param beanName
     */
    void processMultiMethodListeners(Collection<L> classLevelListeners, List<Method> multiMethods, Object bean, String beanName);

    /**
     * 监听注解
     *
     * @param listener
     * @param method
     * @param bean
     * @param beanName
     */
    void processListener(L listener, Method method, Object bean, String beanName);


    /**
     * 处理 EasyListener 监听
     *
     * @param listener
     * @param method
     * @param bean
     * @param beanName
     */
    void processEasyListener(EasyListener listener, Method method, Object bean, String beanName);


    /**
     * 处理注解中指定类型注解的 监听
     *
     * @param listener
     * @param method
     * @param bean
     * @param beanName
     */
    void processAssignEasyListener(EasyListener listener, Method method, Object bean, String beanName);


    default <A extends Annotation> Collection<A> findListenerAnnotations(AnnotatedElement element, Class<A> annotationType) {
        Set<A> listeners = new HashSet<>();
        A ann = AnnotatedElementUtils.findMergedAnnotation(element, annotationType);
        if (ann != null) {
            ann = enhance(element, ann, annotationType);
            listeners.add(ann);
        }
        return listeners;
    }

    default <A extends Annotation> A enhance(AnnotatedElement element, A ann, Class<A> annotationType) {
        if (ENHANCER == null) {
            return ann;
        } else {
            return AnnotationUtils.synthesizeAnnotation(
                    ENHANCER.apply(AnnotationUtils.getAnnotationAttributes(ann), element), annotationType, null);
        }
    }


    default Method checkProxy(Method methodArg, Object bean) {
        Method method = methodArg;
        if (AopUtils.isJdkDynamicProxy(bean)) {
            try {
                // Found a @PulsarListener method on the target class for this JDK proxy ->
                // is it also present on the proxy itself?
                method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
                for (Class<?> iface : proxiedInterfaces) {
                    try {
                        method = iface.getMethod(method.getName(), method.getParameterTypes());
                        break;
                    } catch (@SuppressWarnings("unused") NoSuchMethodException noMethod) {
                        // NOSONAR
                    }
                }
            } catch (SecurityException ex) {
                ReflectionUtils.handleReflectionException(ex);
            } catch (NoSuchMethodException ex) {
                throw new IllegalStateException(String.format(
                        "@PulsarListener method '%s' found on bean target class '%s', " +
                                "but not found in any interface(s) for bean JDK proxy. Either " +
                                "pull the method up to an interface or switch to subclass (CGLIB) " +
                                "proxies by setting proxy-target-class/proxyTargetClass " +
                                "attribute to 'true'", method.getName(),
                        method.getDeclaringClass().getSimpleName()), ex);
            }
        }
        return method;
    }


    /**
     * 获取当前类中对应的监听注解的class
     *
     * @return
     */
    default Class<L> getListenerClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        Class<L> annotationClass = (Class<L>) actualTypeArguments[0];
        return annotationClass;
    }

    /**
     * hasStrategy
     *
     * @param listener 监听者
     * @return 返回数据
     */
    Boolean hasStrategy(EasyListener listener);
}
