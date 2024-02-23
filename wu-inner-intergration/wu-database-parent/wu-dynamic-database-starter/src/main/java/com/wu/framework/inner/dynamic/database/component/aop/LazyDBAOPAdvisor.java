package com.wu.framework.inner.dynamic.database.component.aop;

import com.wu.framework.inner.dynamic.database.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.stereotype.LazyDS;
import lombok.NonNull;
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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * 切换数据源
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/7/4 6:05 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyDBAOPAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private final Advice advice;

    private final Pointcut pointcut;

    public LazyDBAOPAdvisor(@NonNull LazyDBInterceptor lazyDBInterceptor) {
        this.advice = lazyDBInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(LazyDS.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(LazyDS.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(LazyDS.class);
        return new ComposablePointcut(cpc).union(mpc).union(classAnnotation);
    }

    /**
     * Get the Pointcut that drives this advisor.
     */
    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }


    @Override
    public Advice getAdvice() {
        return advice;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    /**
     * description 切面处理
     *
     * @author Jia wei Wu
     * @date 2020/9/11 上午9:28
     */
    public static class LazyDBInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // 切换数据源
            LazyDS lazyDS = determineLazyDS(invocation);
            DynamicLazyDSContextHolder.push(lazyDS);
            try {
                return invocation.proceed();
            } finally {
                DynamicLazyDSContextHolder.clear();
            }
        }

        /**
         * @param
         * @return describe 确定 Lazy DS
         * @author Jia wei Wu
         * @date 2021/7/4 5:04 下午
         **/
        public LazyDS determineLazyDS(MethodInvocation invocation) {
            Method method = invocation.getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            LazyDS lazyDS = method.isAnnotationPresent(LazyDS.class) ? method.getAnnotation(LazyDS.class)
                    : AnnotationUtils.findAnnotation(declaringClass, LazyDS.class);
            return lazyDS;
        }

    }

}
