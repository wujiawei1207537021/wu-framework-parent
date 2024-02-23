package com.wu.framework.inner.layer.data.limit.aop;

import com.wu.framework.inner.layer.data.el.ExpressionEvaluator;
import com.wu.framework.inner.layer.data.limit.EasyAccessLimit;
import com.wu.framework.inner.layer.data.limit.IAccessLimit;
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

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/9 5:07 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class MonitorEasyAccessLimit extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;

    public MonitorEasyAccessLimit(@NonNull MonitorEasyAccessLimitInterceptor monitorEasyAccessLimitInterceptor) {
        this.advice = monitorEasyAccessLimitInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(EasyAccessLimit.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(EasyAccessLimit.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(EasyAccessLimit.class);
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
    public static class MonitorEasyAccessLimitInterceptor implements MethodInterceptor {


        private final IAccessLimit iAccessLimit;


        private ExpressionEvaluator<String> evaluator = new ExpressionEvaluator<>();


        public MonitorEasyAccessLimitInterceptor(IAccessLimit iAccessLimit) {
            this.iAccessLimit = iAccessLimit;
        }


        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            EasyAccessLimit accessLimit = determineAnnotation(invocation, EasyAccessLimit.class);
            String key = accessLimit.key();
            if (ObjectUtils.isEmpty(key)) {
                key = "default";
            } else {
                key = getKey(accessLimit, invocation);
            }
            EasyAccessLimit.AccessLimitModel accessLimitModel = accessLimit.accessLimitModel();


            if (EasyAccessLimit.AccessLimitModel.ALL.equals(accessLimitModel)) {
                iAccessLimit.andThen(accessLimit, key);
            }
            try {
                Object object = invocation.proceed();
                if (EasyAccessLimit.AccessLimitModel.SUCCESS.equals(accessLimitModel)) {
                    iAccessLimit.andThen(accessLimit, key);
                }
                return object;
            } catch (Throwable throwable) {
                if (EasyAccessLimit.AccessLimitModel.FAIL.equals(accessLimitModel)) {
                    iAccessLimit.andThen(accessLimit, key);
                }
            }
            return null;
        }

        public String getKey(EasyAccessLimit accessLimit, MethodInvocation invocation) {
            String key = accessLimit.key();
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