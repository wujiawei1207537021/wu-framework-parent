package com.wu.framework.inner.layer.data.dictionary.apo;

import com.wu.framework.inner.layer.data.dictionary.ConvertAdapter;
import com.wu.framework.inner.layer.data.dictionary.NormalConvertMapper;
import com.wu.framework.inner.layer.data.dictionary.api.ConvertApi;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/9 5:07 下午
 */
@ConditionalOnBean(ConvertApi.class)
public class NormalConvertMapperAspectPointAspectAOP extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private final Advice advice;

    private final Pointcut pointcut;


    public NormalConvertMapperAspectPointAspectAOP(ConvertAdapter convertAdapter) {
        this.advice = new MonitorCurrentMethodInterceptor(convertAdapter);
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

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }


    public static class MonitorCurrentMethodInterceptor implements MethodInterceptor {

        private final ConvertAdapter defaultConvertConvertService;

        public MonitorCurrentMethodInterceptor(ConvertAdapter defaultConvertConvertService) {
            this.defaultConvertConvertService = defaultConvertConvertService;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            long start = System.currentTimeMillis();
            Object returnValue = invocation.proceed();
            defaultConvertConvertService.transformation(returnValue);
            long end = System.currentTimeMillis();
            System.out.printf("转换字典当前方法%s执行时间:%s(毫秒) %n", invocation.getMethod().getName(), end - start);
            return returnValue;
        }
    }


}