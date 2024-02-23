package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
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
public class LazyOperationProxy implements InvocationHandler, InitializingBean {

    private final static Map<Class<? extends LazyOperationMethod>, LazyOperationMethod> LAZY_OPERATION_METHOD_MAP = new HashMap<>();

    private final List<LazyOperationMethod> lazyOperationMethods;
    private final ApplicationContext applicationContext;
    protected String primary;
    protected Map<String, DataSource> DynamicDataSourceMap;


    // 当前链接对象
    private Connection currentConnection;
    // 是否添加事务
    private boolean transactional = false;

    public LazyOperationProxy(List<LazyOperationMethod> lazyOperationMethods, ApplicationContext applicationContext) {
        this.lazyOperationMethods = lazyOperationMethods;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProxyStrategicApproach mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ProxyStrategicApproach.class);
        if (null != mergedAnnotation) {
            LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_METHOD_MAP.get(mergedAnnotation.proxyClass());
            // TODO 动态数据源切换

            // 不是事物重新获取链接
            if (!transactional) {
                this.currentConnection = determineConnection("");
            }
            try {
                final Object execute = lazyOperationMethod.execute(this.currentConnection, args);
                return execute;
            } catch (Exception exception) {
                exception.printStackTrace();
                throw exception;
            } finally {
                // 是不是事务方可
                if (!transactional) {
                    if (null != currentConnection && !currentConnection.isClosed()) {
                        currentConnection.close();
                    }
                }

            }
        } else {
            if (method.getParameterCount() == 0) {
                return method.invoke(this, args);
            } else {
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
     *
     * @param name
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/30 3:26 下午
     */
    public DataSource determineDataSource(String name) {
        return DynamicDataSourceMap.getOrDefault(name, DynamicDataSourceMap.get(primary));
    }

    /**
     * describe 确定链接对象
     *
     * @param name 数据库别名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 5:02 下午
     **/
    public Connection determineConnection(String name) {
        final DataSource dataSource = determineDataSource(name);
        try {
            this.currentConnection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentConnection;
    }

    /**
     * describe 设置 当前链接对象
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 4:57 下午
     **/
    public void setCurrentConnection(Connection currentConnection) {
        this.currentConnection = currentConnection;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }
}
