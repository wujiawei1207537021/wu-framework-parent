package com.wu.bionic.point;

import com.wu.bionic.point.so.DefaultBreakPointSo;
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

import java.lang.reflect.Method;

/**
 * description 断点记忆切面
 *
 * @author Jia wei Wu
 * @date 2021/2/2 下午4:30
 */
public class BreakPointMemoryAbstractPointcutAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private final Advice advice;
    private final Pointcut pointcut;


    public BreakPointMemoryAbstractPointcutAdvisor(BreakPointAnnotationInterceptor breakPointAnnotationInterceptor) {
        this.advice = breakPointAnnotationInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(BreakPoint.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(BreakPoint.class);
        Pointcut mpclazz = AnnotationMatchingPointcut.forClassAnnotation(BreakPoint.class);
        return new ComposablePointcut(cpc).union(mpc).union(mpclazz);
    }

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


    public static class BreakPointAnnotationInterceptor implements MethodInterceptor {

        private final BreakPointMemory breakPointMemory;

        public BreakPointAnnotationInterceptor(BreakPointMemory breakPointMemory) {
            this.breakPointMemory = breakPointMemory;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // 记忆回调
            Object[] params = invocation.getArguments();
            Method method = invocation.getMethod();
            // 记忆存储数据
            DefaultBreakPointSo defaultBreakPointSo = new DefaultBreakPointSo(method).setParams(params);
            breakPointMemory.storage(defaultBreakPointSo);
            Object object = invocation.proceed();
            // 记忆销毁
            breakPointMemory.clear(defaultBreakPointSo);
            System.out.println(String.format("任务完成记忆消失"));
            return object;
        }
    }


}
