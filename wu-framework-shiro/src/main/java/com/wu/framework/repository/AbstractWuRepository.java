package com.wu.framework.repository;


import com.wu.framework.shiro.util.ShiroContextUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.ParameterizedType;

@Deprecated
public abstract class AbstractWuRepository<R> implements WuRepository<R>, ApplicationContextAware {

    @Autowired
    public R repository;

    private ApplicationContext applicationContext;

    public AbstractWuRepository() {
//        instanceR();
    }


    public void instanceR() {
        Class<R> type = this.getClassOfR();
        try {
            repository=   ShiroContextUtil.getApplicationContext().getBean(type);
//            repository = applicationContext.getBean(type);
        } catch (Exception e) {
            // Oops, no default constructor
            throw new RuntimeException(e);
        }
    }


    public Class<R> getClassOfR() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<R> type = (Class<R>) superClass.getActualTypeArguments()[0];
        return type;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        System.out.println("........."+applicationContext);
        this.applicationContext = applicationContext;
    }


}
