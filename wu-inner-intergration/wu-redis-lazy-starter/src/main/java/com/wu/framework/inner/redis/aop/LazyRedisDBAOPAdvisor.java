package com.wu.framework.inner.redis.aop;

import com.wu.framework.inner.redis.annotation.LazyRedis;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
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

import java.lang.reflect.Method;

public class LazyRedisDBAOPAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice advice;

    private Pointcut pointcut;

    public LazyRedisDBAOPAdvisor(@NonNull LazyRedisDBInterceptor lazyRedisDBInterceptor) {
        this.advice = lazyRedisDBInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(LazyRedis.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(LazyRedis.class);
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
     * @author Jia wei Wu
     * @date 2020/9/11 上午9:28
     */
    public static class LazyRedisDBInterceptor implements MethodInterceptor {

        private final LazyRedisTemplate lazyRedisTemplate;

        public LazyRedisDBInterceptor(LazyRedisTemplate lazyRedisTemplate) {
            this.lazyRedisTemplate = lazyRedisTemplate;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // 切换数据源
            LazyRedis lazyRedis = determineLazyRedis(invocation);
            lazyRedisTemplate.setDyDatabase(lazyRedis.database());
            return invocation.proceed();
        }

        /**
        * @describe 确定 Lazy Redis
        * @param
        * @return
        * @author Jia wei Wu
        * @date 2021/7/4 5:04 下午
        **/
        public LazyRedis determineLazyRedis(MethodInvocation invocation) {
            Method method = invocation.getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            LazyRedis lazyRedis = method.isAnnotationPresent(LazyRedis.class) ? method.getAnnotation(LazyRedis.class)
                    : AnnotationUtils.findAnnotation(declaringClass, LazyRedis.class);
            return lazyRedis;
        }

    }

}
