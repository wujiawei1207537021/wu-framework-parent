package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义接口实现方法执行代理类
 * @date : 2020/6/25 下午11:19
 */
@ConditionalOnBean(value = DataSource.class)
public class LazyOperationProxy implements InvocationHandler, InitializingBean {

    private final static Map<Class<? extends LazyOperationMethod>, LazyOperationMethod> LAZY_OPERATION_METHOD_MAP = new HashMap<>();
    private final DataSource dataSource;
    private final List<LazyOperationMethod> lazyOperationMethods;

    public LazyOperationProxy(DataSource dataSource, List<LazyOperationMethod> lazyOperationMethods) {
        this.dataSource = dataSource;
        this.lazyOperationMethods = lazyOperationMethods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProxyStrategicApproach mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ProxyStrategicApproach.class);
        if (null != mergedAnnotation) {
            LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_METHOD_MAP.get(mergedAnnotation.proxyClass());
            try {
                return lazyOperationMethod.execute(dataSource, args);
            } catch (Exception exception) {
                throw exception;
            }
        } else {
            return method.invoke(null, args);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        lazyOperationMethods.forEach(lazyOperationMethod -> LAZY_OPERATION_METHOD_MAP.put(lazyOperationMethod.getClass(), lazyOperationMethod));
    }
}
