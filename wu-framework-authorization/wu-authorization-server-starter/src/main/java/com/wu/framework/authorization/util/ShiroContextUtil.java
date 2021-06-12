package com.wu.framework.authorization.util;

import com.wu.framework.authorization.function.AuthorizationFunction;
import com.wu.framework.response.exceptions.CustomException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;


@Component
public class ShiroContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub
        ShiroContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanId) throws BeansException {
        return applicationContext.getBean(beanId);
    }

    public static <T> T getBeansWithAnnotation(Class<T> interfac, Class<? extends Annotation> annotation, String code, AuthorizationFunction<T, Class<? extends Annotation>> authorizationFunction) throws BeansException {
        if (ObjectUtils.isEmpty(code)) {
            throw new CustomException("code is null ");
        }
        Collection<T> tCollection = ShiroContextUtil.getApplicationContext().getBeansOfType(interfac).values();
/**
 *  函数处理事件
 *
 *
 *  for (T t : tCollection) {
 *
 *
 *             A a = (A) t.getClass().getAnnotation(annotation);
 *             if (ObjectUtils.isEmpty(a)) {
 *                 throw new CustomException("not found code by QRService :" + code);
 *             }
 *             if (code.equals(qrService.methodName().methodName())) {
 *                 return t;
 *             }
 *
 *         }
 *         throw new CustomException("fail to find  code by  :" + code);
 *
 */
        return authorizationFunction.getBeanWithAnnotation(tCollection, annotation);
    }


}