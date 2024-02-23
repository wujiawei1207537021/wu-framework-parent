package com.wu.framework.inner.layer.data.lock.aop;

import com.wu.framework.inner.layer.data.el.ExpressionEvaluator;
import com.wu.framework.inner.layer.data.lock.EasyLock;
import com.wu.framework.inner.layer.data.lock.ILock;
import com.wu.framework.inner.layer.data.lock.endpoint.LockEndPoint;
import com.wu.framework.inner.layer.data.lock.endpoint.LockLayerAnnotationConverterAdapter;
import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/03 8:20 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class MonitorEasyLock extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;

    public MonitorEasyLock(@NonNull MonitorEasyLock.MonitorEasyLockInterceptor interceptor) {
        this.advice = interceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(EasyLock.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(EasyLock.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(EasyLock.class);
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


    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public static class MonitorEasyLockInterceptor implements MethodInterceptor {


        private final ILock iLock;
        LockLayerAnnotationConverterAdapter annotationConverterAdapter = new LockLayerAnnotationConverterAdapter();

        private ExpressionEvaluator<String> evaluator = new ExpressionEvaluator<>();

        public MonitorEasyLockInterceptor(ILock iLock) {
            this.iLock = iLock;
        }


        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            EasyLock easyLock = determineAnnotation(invocation, EasyLock.class);
            LockEndPoint lockEndPoint = annotationConverterAdapter.converter(easyLock);

            String key = lockEndPoint.getKey();
            String lockGroup = lockEndPoint.getLockGroup();
            long time = lockEndPoint.getTime();
            TimeUnit unit = lockEndPoint.getUnit();
            lockGroup = ObjectUtils.isEmpty(lockGroup) ? "default-group" : lockGroup;
            key = ObjectUtils.isEmpty(key) ? "default-key" : getSpringElKey(key, invocation);

            lockEndPoint.setLockGroup(lockGroup);
            lockEndPoint.setKey(key);
            iLock.lock(lockEndPoint);
            boolean isLocked;
            if (time <= 0) {
                isLocked = iLock.tryLock(lockEndPoint);
            } else {
                isLocked = iLock.tryLock(lockEndPoint, time, unit);
            }
            // 判断对象是否被锁住
            if (isLocked) {
                try {
                    return invocation.proceed();
                } catch (Exception e) {
                    e.printStackTrace();
                    iLock.unlock(lockEndPoint);
                } finally {
                    // 释放锁
                    iLock.unlock(lockEndPoint);
                }
            } else {
                System.out.println("数据正在处理中");
            }
            return null;
        }

        public String getSpringElKey(String key, MethodInvocation invocation) {
            Object target = invocation.getThis();
            Method method = invocation.getMethod();
            Object[] arguments = invocation.getArguments();
            assert target != null;
            Class<?> targetClass = target.getClass();
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(target, targetClass,
                    method, arguments);

            AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
            return evaluator.condition(key, methodKey, evaluationContext, String.class);

        }

        /**
         * @param
         * @return describe 获取指定类型的注解
         * @author Jia wei Wu
         * @date 2021/7/10 12:15 下午
         **/
        public <T extends Annotation> T determineAnnotation(MethodInvocation invocation, Class<T> annotationClass) {
            Method method = invocation.getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            T annotation = AnnotatedElementUtils.hasAnnotation(method, annotationClass) ? AnnotatedElementUtils.findMergedAnnotation(method, annotationClass)
                    : AnnotatedElementUtils.findMergedAnnotation(declaringClass, annotationClass);
            return annotation;
        }

    }


}
