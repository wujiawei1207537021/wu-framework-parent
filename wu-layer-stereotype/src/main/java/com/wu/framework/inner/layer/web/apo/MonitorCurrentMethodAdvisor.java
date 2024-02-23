package com.wu.framework.inner.layer.web.apo;

import com.wu.framework.inner.layer.web.EasyController;
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

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/9 5:07 下午
 */
public class MonitorCurrentMethodAdvisor extends AbstractPointcutAdvisor  {
    private final Advice advice;

    private final Pointcut pointcut;

    public MonitorCurrentMethodAdvisor(@NonNull MonitorCurrentMethodInterceptor monitorCurrentMethodInterceptor) {
        this.advice = monitorCurrentMethodInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(EasyController.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(EasyController.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(EasyController.class);
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




    public static class MonitorCurrentMethodInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            long start = System.currentTimeMillis();
            Object object = invocation.proceed();
            long end = System.currentTimeMillis();
            System.out.printf("当前方法%s执行时间:%s(毫秒) %n", invocation.getMethod().getName(), end - start);
            return object;
        }
    }


}