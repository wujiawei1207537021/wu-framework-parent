package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl.LazyDDLOperationMethod;
import com.wu.framework.inner.lazy.database.util.LazyDataSourceUtils;
import com.wu.framework.inner.lazy.stereotype.proxy.ProxyLazyBaseLayerStrategicApproach;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : DDL 操作代理对象
 * @date : 2020/6/25 下午11:19
 * @see com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDDLOperation
 */
@Slf4j
@ConditionalOnBean(value = DataSource.class)
public class LazyDDLOperationProxy extends AbstractLazyOperationProxyRetryInvocationHandler implements InvocationHandler, InitializingBean {

    protected final ConcurrentHashMap<String, LazyOperationMethod> LAZY_OPERATION_DDL_METHOD_MAP = new ConcurrentHashMap<>();

    private final List<LazyDDLOperationMethod> lazyDDLOperationMethodList;

    private final LazyDynamicAdapter lazyDynamicAdapter;


    public LazyDDLOperationProxy(List<LazyDDLOperationMethod> lazyDDLOperationMethodList, LazyDynamicAdapter lazyDynamicAdapter) {
        this.lazyDDLOperationMethodList = lazyDDLOperationMethodList;
        this.lazyDynamicAdapter = lazyDynamicAdapter;
    }

    /**
     * @param proxy     代理对象
     * @param method    代理方法
     * @param args      代理方法参数
     * @param retryTime 重试
     * @param throwable 异常
     * @param hasRetry  是否已经重试
     * @return 返回的执行结果
     * @throws Throwable
     */
    @Override
    public Object retryInvoke(Object proxy, Method method, Object[] args, int retryTime, Throwable throwable, boolean hasRetry) throws Throwable {

        ProxyLazyBaseLayerStrategicApproach mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ProxyLazyBaseLayerStrategicApproach.class);
        if (null != mergedAnnotation) {
            Connection connection;
            boolean isConnectionTransactional;

            boolean autoCommit;
            LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_DDL_METHOD_MAP.get(mergedAnnotation.proxyClassName());
            if (null == lazyOperationMethod) {
                log.error("无法找到对应class ：【{}】的代理实现", mergedAnnotation.proxyClassName());
            }
            final DataSource dataSource = lazyDynamicAdapter.determineDataSource();
            // 切换数据库
            try {
                connection = LazyDataSourceUtils.getConnection(dataSource);
            } catch (CannotGetJdbcConnectionException cannotGetJdbcConnectionException) {
                throw cannotGetJdbcConnectionException;
            }
            switchSchema(connection);
            isConnectionTransactional = LazyDataSourceUtils.isConnectionTransactional(connection, dataSource);
            autoCommit = connection.getAutoCommit();
            try {
                if (connection.isClosed()) {
                    System.out.println("this.connection  关闭了");
                }

                // 判断不是事物重新获取链接
                final Object execute = lazyOperationMethod.execute(connection, args);
                // 是否提交
                if (connection != null && !isConnectionTransactional && !autoCommit) {
                    log.debug("Committing JDBC Connection [" + connection + "]");
                    connection.commit();
                }
                return execute;
            } catch (Exception exception) {
                // 事物回滚
                if (connection != null && !isConnectionTransactional && !autoCommit) {
                    log.debug("Rolling back JDBC Connection [" + connection + "]");
                    connection.rollback();
                }
                //
                exception.printStackTrace();
                throw exception;
            } finally {
                // 释放链接
                LazyDataSourceUtils.releaseConnection(connection, dataSource);
            }
        } else {
            if (method.getParameterCount() == 0) {
                return method.invoke(this, args);
            } else {
                // TODO 栈内存溢出
                return method.invoke(this, args);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        lazyDDLOperationMethodList.forEach(lazyOperationMethod -> LAZY_OPERATION_DDL_METHOD_MAP.put(lazyOperationMethod.getClass().getName(), lazyOperationMethod));
    }


    /**
     * 确定数据源
     *
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 5:02 下午
     **/
    public DataSource determineConnection() throws SQLException {
        return lazyDynamicAdapter.determineDataSource();
    }

}
