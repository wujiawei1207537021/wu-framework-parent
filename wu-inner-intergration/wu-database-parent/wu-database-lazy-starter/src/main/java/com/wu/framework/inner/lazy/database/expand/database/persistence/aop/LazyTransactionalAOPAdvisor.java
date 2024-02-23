package com.wu.framework.inner.lazy.database.expand.database.persistence.aop;

import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import com.wu.framework.inner.lazy.stereotype.LazyTransactional;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.Connection;

public class LazyTransactionalAOPAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;

    public LazyTransactionalAOPAdvisor(@NonNull LazyTransactionalAOPAdvisorInterceptor transactionalAOPAdvisorInterceptor) {
        this.advice = transactionalAOPAdvisorInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(LazyTransactional.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(LazyTransactional.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(LazyTransactional.class);
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


    /**
     * description 切面处理
     *
     * @author Jia wei Wu
     * @date 2020/9/11 上午9:28
     */
    public static class LazyTransactionalAOPAdvisorInterceptor implements MethodInterceptor {
        private final LazyOperationProxy lazyOperationProxy;

        public LazyTransactionalAOPAdvisorInterceptor(LazyOperationProxy lazyOperationProxy) {
            this.lazyOperationProxy = lazyOperationProxy;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            final Connection currentConnection = lazyOperationProxy.determineConnection();
            lazyOperationProxy.setTransactional(true);
            lazyOperationProxy.setCurrentConnection(currentConnection);
            //  开启事物
            final LazyTransactional transactional = determineLazyTransactional(invocation);
            currentConnection.setAutoCommit(false);

            Object proceed = new Object();
            try {
                proceed = invocation.proceed();
                // 手动提交
                currentConnection.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
                final Class<? extends Throwable>[] rollbackFor = transactional.rollbackFor();
                if (!ObjectUtils.isEmpty(rollbackFor)) {
                    for (Class<? extends Throwable> throwable : rollbackFor) {
                        if (exception.getClass().isAssignableFrom(throwable)) {
                            currentConnection.rollback();
                        }
                    }
                }
            } finally {
                if (!currentConnection.isClosed()) {
                    currentConnection.close();
                }
                lazyOperationProxy.setTransactional(false);
            }
            return proceed;
        }

        /**
         * describe describe 确定 LazyTransactional
         *
         * @param
         * @return
         * @author Jia wei Wu
         * @date 2022/1/1 4:10 下午
         **/
        public LazyTransactional determineLazyTransactional(MethodInvocation invocation) {
            Method method = invocation.getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            LazyTransactional transactional = method.isAnnotationPresent(LazyTransactional.class) ? method.getAnnotation(LazyTransactional.class)
                    : AnnotationUtils.findAnnotation(declaringClass, LazyTransactional.class);
            return transactional;
        }


    }

}