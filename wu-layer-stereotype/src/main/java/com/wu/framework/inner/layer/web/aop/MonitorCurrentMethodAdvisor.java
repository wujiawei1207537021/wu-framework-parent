package com.wu.framework.inner.layer.web.aop;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.layer.web.config.EasyControllerConfig;
import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/9 5:07 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Import(EasyControllerConfig.class)
public class MonitorCurrentMethodAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;

    public MonitorCurrentMethodAdvisor(@NonNull MonitorCurrentMethodInterceptor monitorCurrentMethodInterceptor, EasyControllerConfig easyControllerConfig) {
        this.advice = monitorCurrentMethodInterceptor;
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(EasyController.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(EasyController.class);
        Pointcut classAnnotation = AnnotationMatchingPointcut.forClassAnnotation(EasyController.class);
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
    public static class MonitorCurrentMethodInterceptor implements MethodInterceptor {
        private final EasyControllerConfig easyControllerConfig;

        public MonitorCurrentMethodInterceptor(EasyControllerConfig easyControllerConfig) {
            this.easyControllerConfig = easyControllerConfig;
        }


        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            long start = System.currentTimeMillis();
            Object object = invocation.proceed();
            long end = System.currentTimeMillis();
            Method method = invocation.getMethod();
            if (easyControllerConfig.isPrintExecuteTime()) {
                System.out.printf("当前方法: %s 执行时间:%s(毫秒) %n", method, end - start);
            }
            if (easyControllerConfig.isPrintParams()) {

                List<String> paramList = new ArrayList<>();

                Parameter[] parameters = method.getParameters();
                Object[] arguments = invocation.getArguments();
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    paramList.add(parameter.getName() + " : " + arguments[i]);
                }
                System.out.printf("当前方法: %s\n执行参数params: %s. \n返回参数: %s",
                        method.getName(),
                        String.join("  ", paramList),
                        object);
            }
            if(easyControllerConfig.isPrintExecuteTime()||easyControllerConfig.isPrintParams()){
                // 当前方法执行完成
                System.out.print("\n当前方法执行完成----------\n");
            }

            return object;
        }
    }


}