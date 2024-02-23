package com.wu.framework.inner.layer.data.encryption.aop;

import com.wu.framework.inner.layer.data.encryption.NormalConvertEncryptionDecryptionMapper;
import com.wu.framework.inner.layer.data.encryption.adapter.ConvertEncryptionDecryptionAdapter;
import com.wu.framework.inner.layer.data.encryption.enums.EncryptionDecryptionTypeEnum;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * 加密切面
 */
public class NormalConvertEncryptionDecryptionPointcutAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;


    public NormalConvertEncryptionDecryptionPointcutAdvisor(ConvertEncryptionDecryptionAdapter convertEncryptionDecryptionAdapter) {
        this.advice = new MonitorCurrentMethodInterceptor(convertEncryptionDecryptionAdapter);
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(NormalConvertEncryptionDecryptionMapper.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(NormalConvertEncryptionDecryptionMapper.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(NormalConvertEncryptionDecryptionMapper.class);
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

        private final ConvertEncryptionDecryptionAdapter defaultConvertEncryptionDecryptionAdapter;

        public MonitorCurrentMethodInterceptor(ConvertEncryptionDecryptionAdapter defaultConvertEncryptionDecryptionAdapter) {
            this.defaultConvertEncryptionDecryptionAdapter = defaultConvertEncryptionDecryptionAdapter;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            long start = System.currentTimeMillis();
            Object returnValue = invocation.proceed();
            defaultConvertEncryptionDecryptionAdapter.transformation(returnValue, EncryptionDecryptionTypeEnum.ENCRYPTION);
            long end = System.currentTimeMillis();
            System.out.printf("转换加密当前方法%s执行时间:%s(毫秒) %n", invocation.getMethod().getName(), end - start);
            return returnValue;
        }
    }


}