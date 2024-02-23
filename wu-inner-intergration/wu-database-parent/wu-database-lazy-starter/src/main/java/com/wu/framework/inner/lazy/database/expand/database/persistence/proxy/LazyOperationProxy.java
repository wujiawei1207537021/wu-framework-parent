package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Import;
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
@Slf4j
@ConditionalOnBean(value = DataSource.class)
@Import(PerfectLazyOperation.class)
public class LazyOperationProxy implements InvocationHandler, InitializingBean {

    private final static Map<Class<? extends LazyOperationMethod>, LazyOperationMethod> LAZY_OPERATION_METHOD_MAP = new HashMap<>();

    private final List<LazyOperationMethod> lazyOperationMethods;

    private final LazyDynamicAdapter lazyDynamicAdapter;


    // 当前链接对象
    private Connection currentConnection;
    // 是否添加事务
    private boolean transactional = false;

    public LazyOperationProxy(List<LazyOperationMethod> lazyOperationMethods, LazyDynamicAdapter lazyDynamicAdapter) {
        this.lazyOperationMethods = lazyOperationMethods;
        this.lazyDynamicAdapter = lazyDynamicAdapter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ProxyStrategicApproach mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ProxyStrategicApproach.class);
        if (null != mergedAnnotation) {
            LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_METHOD_MAP.get(mergedAnnotation.proxyClass());
            // TODO 动态数据源切换

            // 不是事物重新获取链接
//            if (!transactional) {
//                currentConnection = determineConnection();
//            }
            try {
                // 判断不是事物重新获取链接
                final Object execute = lazyOperationMethod.execute(transactional ? this.currentConnection : determineConnection(), args);
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
    }

    /**
     * 确定链接对象
     *
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 5:02 下午
     **/
    public Connection determineConnection() throws SQLException {
        final DataSource dataSource = lazyDynamicAdapter.determineDataSource();
        return dataSource.getConnection();
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
