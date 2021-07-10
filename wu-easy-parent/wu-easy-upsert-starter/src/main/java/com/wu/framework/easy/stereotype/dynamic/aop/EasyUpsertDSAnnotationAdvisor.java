//package com.wu.framework.easy.stereotype.dynamic.aop;
//
//
//import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
//import lombok.NonNull;
//import org.aopalliance.aop.Advice;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.springframework.aop.Pointcut;
//import org.springframework.aop.support.AbstractPointcutAdvisor;
//import org.springframework.aop.support.ComposablePointcut;
//import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.core.annotation.AnnotationUtils;
//
//import java.lang.reflect.Method;
//
///**
// * description 自定义一数据源切面绑定
// *
// * @author Jia wei Wu
// * @date 2020/9/11 上午9:24
// */
//public class EasyUpsertAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
//    private Advice advice;
//
//    private Pointcut pointcut;
//
//    public EasyUpsertAnnotationAdvisor(@NonNull EasyUpsertDSAnnotationInterceptor easyUpsertDSAnnotationInterceptor) {
//        this.advice = easyUpsertDSAnnotationInterceptor;
//        this.pointcut = buildPointcut();
//    }
//
//    private Pointcut buildPointcut() {
//        Pointcut cpc = new AnnotationMatchingPointcut(EasyUpsert.class, EasyUpsert.class, true);
//        return new ComposablePointcut(cpc);
//    }
//
//    /**
//     * Get the Pointcut that drives this advisor.
//     */
//    @Override
//    public Pointcut getPointcut() {
//        return pointcut;
//    }
//
//
//    @Override
//    public Advice getAdvice() {
//        return advice;
//    }
//
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//
//    }
//
//    /**
//     * description 切面处理
//     *
//     * @author Jia wei Wu
//     * @date 2020/9/11 上午9:28
//     */
//    public static class EasyUpsertDSAnnotationInterceptor implements MethodInterceptor {
//
//        @Override
//        public Object invoke(MethodInvocation invocation) throws Throwable {
//            // 切换数据源
//            EasyUpsert easyUpsertDS = determineEasyUpsert(invocation);
//            try {
//                com.wu.framework.easy.upsert.core.dynamic.toolkit.DynamicEasyUpsertContextHolder.push(easyUpsertDS);
//                return invocation.proceed();
//            } finally {
//                com.wu.framework.easy.upsert.core.dynamic.toolkit.DynamicEasyUpsertContextHolder.poll();
//            }
//        }
//
//        public EasyUpsert determineEasyUpsert(MethodInvocation invocation) {
//            Method method = invocation.getMethod();
//            Class<?> declaringClass = method.getDeclaringClass();
//            EasyUpsert ds = method.isAnnotationPresent(EasyUpsert.class) ? method.getAnnotation(EasyUpsert.class)
//                    : AnnotationUtils.findAnnotation(declaringClass, EasyUpsert.class);
//            return ds;
//        }
//    }
//
//}
