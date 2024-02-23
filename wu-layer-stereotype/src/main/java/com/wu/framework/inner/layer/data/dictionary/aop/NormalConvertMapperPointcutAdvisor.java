package com.wu.framework.inner.layer.data.dictionary.aop;


import com.wu.framework.inner.layer.data.dictionary.ArgsConvertDictionary;
import com.wu.framework.inner.layer.data.dictionary.NormalConvertMapper;
import com.wu.framework.inner.layer.data.dictionary.adapter.LazyDictionaryConvertAdapter;
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
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/9 5:07 下午
 */
public class NormalConvertMapperPointcutAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;


    public NormalConvertMapperPointcutAdvisor(LazyDictionaryConvertAdapter lazyDictionaryConvertAdapter) {
        this.advice = new MonitorCurrentMethodInterceptor(lazyDictionaryConvertAdapter);
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(NormalConvertMapper.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(NormalConvertMapper.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(NormalConvertMapper.class);
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

        private final LazyDictionaryConvertAdapter lazyDictionaryConvertAdapter;

        public MonitorCurrentMethodInterceptor(LazyDictionaryConvertAdapter lazyDictionaryConvertAdapter) {
            this.lazyDictionaryConvertAdapter = lazyDictionaryConvertAdapter;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Object[] arguments = invocation.getArguments();
            Method method = invocation.getMethod();
            Parameter[] methodParameters = method.getParameters();
            for (int i = 0; i < methodParameters.length; i++) {
                Parameter methodParameter = methodParameters[i];
                ArgsConvertDictionary annotation = methodParameter.getAnnotation(ArgsConvertDictionary.class);

                if (annotation != null) {
                    Object argument = arguments[i];
                    lazyDictionaryConvertAdapter.transformation(argument);
                }
            }

            long start = System.currentTimeMillis();
            Object returnValue = invocation.proceed();
            lazyDictionaryConvertAdapter.transformation(returnValue);
            long end = System.currentTimeMillis();
            System.out.printf("转换字典当前方法%s执行时间:%s(毫秒) %n", invocation.getMethod().getName(), end - start);
            return returnValue;
        }
    }


}