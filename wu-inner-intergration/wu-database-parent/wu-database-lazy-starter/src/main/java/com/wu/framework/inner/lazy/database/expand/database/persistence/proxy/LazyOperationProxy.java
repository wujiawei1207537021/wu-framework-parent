package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.expand.database.persistence.PerfectLazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextRefreshedEvent;
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
 * describe : 自定义接口实现方法执行代理类
 * @date : 2020/6/25 下午11:19
 */
@ConditionalOnBean(value = DataSource.class)
@Import(PerfectLazyOperation.class)
public class LazyOperationProxy implements InvocationHandler, InitializingBean{

    private final static Map<Class<? extends LazyOperationMethod>, LazyOperationMethod> LAZY_OPERATION_METHOD_MAP = new HashMap<>();

    private final List<LazyOperationMethod> lazyOperationMethods;
    protected String primary;
    protected Map<String, DataSource> DynamicDataSourceMap;
    private final ApplicationContext applicationContext;

    public LazyOperationProxy(List<LazyOperationMethod> lazyOperationMethods,  ApplicationContext applicationContext) {
        this.lazyOperationMethods = lazyOperationMethods;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProxyStrategicApproach mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ProxyStrategicApproach.class);
        if (null != mergedAnnotation) {
            LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_METHOD_MAP.get(mergedAnnotation.proxyClass());
            try {
                return lazyOperationMethod.execute(determineDataSource(""), args);
            } catch (Exception exception) {
                throw exception;
            }
        } else {
            if(method.getParameterCount()==0){
                return method.invoke(this, args);
            }else {
                return method.invoke(null, args);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        lazyOperationMethods.forEach(lazyOperationMethod -> LAZY_OPERATION_METHOD_MAP.put(lazyOperationMethod.getClass(), lazyOperationMethod));
        Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
        this.primary = dataSourceMap.keySet().iterator().next();
        DynamicDataSourceMap = dataSourceMap;
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, DataSource> dataSourceMap = contextRefreshedEvent.getApplicationContext().getBeansOfType(DataSource.class);
        this.primary = dataSourceMap.keySet().iterator().next();
        DynamicDataSourceMap = dataSourceMap;
    }

    /**
     * description 确定数据源
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/30 3:26 下午
     */
    public DataSource determineDataSource(String name) {
        return DynamicDataSourceMap.getOrDefault(name, DynamicDataSourceMap.get(primary));
    }
}
