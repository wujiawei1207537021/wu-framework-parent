package com.wu.framework.easy.stereotype.dynamic.aop;

import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
import com.wu.framework.easy.stereotype.upsert.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.stereotype.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.stereotype.dynamic.toolkit.DynamicEasyUpsertDSContextHolder;
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
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * description  自定义快速一数据源切换(Kafka、MySQL多数据源-mybatis)切面绑定
 *
 * @author 吴佳伟
 * @date 2020/9/11 上午9:24
 */
public class QuickEasyUpsertAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice advice;

    private Pointcut pointcut;

    public QuickEasyUpsertAnnotationAdvisor(@NonNull QuickEasyUpsertAnnotationInterceptor quickEasyUpsertAnnotationInterceptor) {
        this.advice = quickEasyUpsertAnnotationInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(QuickEasyUpsert.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(QuickEasyUpsert.class);
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
     * description  自定义快速一数据源切换(Kafka、MySQL多数据源-mybatis)切面处理
     *
     * @author 吴佳伟
     * @date 2020/9/11 上午9:28
     */
    public static class QuickEasyUpsertAnnotationInterceptor implements MethodInterceptor {

        private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;

        public QuickEasyUpsertAnnotationInterceptor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
            this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // 切换数据源
//        QuickEasyUpsert quickEasyUpsert = determineQuickEasyUpsert(invocation);
            EasyUpsertDS easyUpsertDS = determineCustomDS(invocation);
            try {
                DynamicEasyUpsertDSContextHolder.push(easyUpsertDS);
                Object object = invocation.proceed();
                if (null==object){
                    return null;
                }
                if (object instanceof Collection) {
                    abstractDynamicEasyUpsert.determineIEasyUpsert().upsert((List<? extends Object>) object);
                } else {
                    abstractDynamicEasyUpsert.determineIEasyUpsert().upsert(Arrays.asList(object));
                }
                return object;
            } finally {
                DynamicEasyUpsertDSContextHolder.poll();
            }
        }

        public QuickEasyUpsert determineQuickEasyUpsert(MethodInvocation invocation) {
            Method method = invocation.getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            QuickEasyUpsert ds = AnnotatedElementUtils.findMergedAnnotation(method,QuickEasyUpsert.class) !=null?
                    AnnotatedElementUtils.findMergedAnnotation(method, QuickEasyUpsert.class)
                    : AnnotatedElementUtils.findMergedAnnotation(declaringClass, QuickEasyUpsert.class);
            return ds;
        }

        public EasyUpsertDS determineCustomDS(MethodInvocation invocation) {
            Method method = invocation.getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            EasyUpsertDS ds = AnnotatedElementUtils.findMergedAnnotation(method,EasyUpsertDS.class)!=null ?
                    AnnotatedElementUtils.findMergedAnnotation(method, EasyUpsertDS.class)
                    : AnnotatedElementUtils.findMergedAnnotation(declaringClass, EasyUpsertDS.class);
            return ds;
        }
    }


}


