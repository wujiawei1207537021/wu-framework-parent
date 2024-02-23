package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.aop;


import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableArgsTranslation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableTranslation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 转译 切面
 */
public class LazyTranslationPointcutAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;


    public LazyTranslationPointcutAdvisor(LazyTranslationAdapter translationAdapter) {
        this.advice = new MonitorCurrentMethodInterceptor(translationAdapter);
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(LazyTableTranslation.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(LazyTableTranslation.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(LazyTableTranslation.class);
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

        private final LazyTranslationAdapter translationAdapter;

        public MonitorCurrentMethodInterceptor(LazyTranslationAdapter translationAdapter) {
            this.translationAdapter = translationAdapter;
        }


        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            Object[] arguments = invocation.getArguments();
            Method method = invocation.getMethod();
            Parameter[] methodParameters = method.getParameters();
            for (int i = 0; i < methodParameters.length; i++) {
                Parameter methodParameter = methodParameters[i];
                LazyTableArgsTranslation annotation = methodParameter.getAnnotation(LazyTableArgsTranslation.class);

                if (annotation != null) {
                    Object argument = arguments[i];
                    translationAdapter.transformation(argument);
                }
            }


            Object returnValue = invocation.proceed();
            long start = System.currentTimeMillis();
            translationAdapter.transformation(returnValue);
            long end = System.currentTimeMillis();
            System.out.printf("转换当前方法%s执行时间:%s(毫秒) %n", invocation.getMethod().getName(), end - start);
            return returnValue;
        }
    }


}