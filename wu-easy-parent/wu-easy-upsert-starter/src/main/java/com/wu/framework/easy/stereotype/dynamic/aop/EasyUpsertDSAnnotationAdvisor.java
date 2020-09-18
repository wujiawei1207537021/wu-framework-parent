package com.wu.framework.easy.stereotype.dynamic.aop;


import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
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
import org.springframework.core.annotation.AnnotationUtils;
import com.wu.framework.easy.stereotype.dynamic.toolkit.DynamicEasyUpsertDSContextHolder;

import java.lang.reflect.Method;

/**
 * description 自定义一数据源切面绑定
 *
 * @author 吴佳伟
 * @date 2020/9/11 上午9:24
 */
public class EasyUpsertDSAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice advice;

    private Pointcut pointcut;

    public EasyUpsertDSAnnotationAdvisor(@NonNull EasyUpsertDSAnnotationInterceptor easyUpsertDSAnnotationInterceptor) {
        this.advice = easyUpsertDSAnnotationInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(EasyUpsertDS.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(EasyUpsertDS.class);
        return new ComposablePointcut(cpc).union(mpc);
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
     * @author 吴佳伟
     * @date 2020/9/11 上午9:28
     */
    public static class EasyUpsertDSAnnotationInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // 切换数据源
            EasyUpsertDS easyUpsertDS = determineCustomDS(invocation);
            try {
                DynamicEasyUpsertDSContextHolder.push(easyUpsertDS);
                return invocation.proceed();
            } finally {
                DynamicEasyUpsertDSContextHolder.poll();
            }
        }

        public EasyUpsertDS determineCustomDS(MethodInvocation invocation) {
            Method method = invocation.getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            EasyUpsertDS ds = method.isAnnotationPresent(EasyUpsertDS.class) ? method.getAnnotation(EasyUpsertDS.class)
                    : AnnotationUtils.findAnnotation(declaringClass, EasyUpsertDS.class);
            return ds;
        }
    }

}
