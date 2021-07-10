package com.wu.framework.easy.upsert.core.dynamic.aop;

import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.toolkit.DynamicEasyUpsertContextHolder;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * description 自定义一数据源切面绑定
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:24
 */
public abstract class AbstractPointcutEasyUpsertAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {


    public abstract Class<? extends Annotation> getAnnotationClass();

    /**
     * @param
     * @return
     * @describe 获取注解
     * @author Jia wei Wu
     * @date 2021/7/10 12:15 下午
     **/
    public Annotation determineEasyUpsert(MethodInvocation invocation) {
        return determineEasyUpsert(invocation, getAnnotationClass());
    }

    /**
     * @param
     * @return
     * @describe 获取指定类型的注解
     * @author Jia wei Wu
     * @date 2021/7/10 12:15 下午
     **/
    public <T extends Annotation> T determineEasyUpsert(MethodInvocation invocation, Class<T> annotationClass) {
        Method method = invocation.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();
        T annotation = AnnotatedElementUtils.hasAnnotation(method,annotationClass) ? AnnotatedElementUtils.findMergedAnnotation(method, annotationClass)
                : AnnotatedElementUtils.findMergedAnnotation(declaringClass, annotationClass);
        return annotation;
    }


    /**
     * Get the Pointcut that drives this advisor.
     */
    @Override
    public Pointcut getPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(getAnnotationClass(), true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(getAnnotationClass());
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(getAnnotationClass());
        return new ComposablePointcut(cpc).union(mpc).union(classAnnotation);
    }


    /**
     * @param
     * @return
     * @describe 切面处理
     * @author Jia wei Wu
     * @date 2021/7/10 11:15 上午
     **/
    @Override
    public Advice getAdvice() {
        return new MethodInterceptor() {

            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                // 切换数据源
                EasyUpsert easyUpsert = determineEasyUpsert(invocation, EasyUpsert.class);
                try {
                    DynamicEasyUpsertContextHolder.push(easyUpsert);
                    return invocation.proceed();
                } finally {
                    DynamicEasyUpsertContextHolder.poll();
                }
            }
        };
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

}
