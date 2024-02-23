package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.lazy.database.dynamic.LazyDynamicAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.config.LazyOperationProxyBeanAutoConfiguration;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.CureAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyOperationProxyFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import com.wu.framework.inner.lazy.database.util.LazyDataSourceUtils;
import com.wu.framework.inner.lazy.stereotype.proxy.ProxyLazyStrategicApproach;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotatedElementUtils;

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
 * describe : 自定义接口实现方法执行代理类 包含执行 DDL、DQL、DML
 * @date : 2020/6/25 下午11:19
 * @see LazyOperationProxyBeanAutoConfiguration#lazyOperationProxy(List, LazyDynamicAdapter, CureAdapter, LazyTranslationAdapter)
 * @see LazyOperationProxyFactory
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnBean(value = DataSource.class)
public class LazyOperationProxy extends AbstractLazyOperationProxyRetryInvocationHandler implements InvocationHandler, InitializingBean {

    protected final ConcurrentHashMap<Class<? extends LazyOperationMethod>, LazyOperationMethod> LAZY_OPERATION_METHOD_MAP = new ConcurrentHashMap<>();

    private final List<LazyOperationMethod> lazyOperationMethods;

    private final LazyDynamicAdapter lazyDynamicAdapter;
    private final CureAdapter cureAdapter;// 治愈适配器
    /**
     * 跨表转译适配器
     */
    private final LazyTranslationAdapter lazyTranslationAdapter;

    public LazyOperationProxy(List<LazyOperationMethod> lazyOperationMethods, LazyDynamicAdapter lazyDynamicAdapter, CureAdapter cureAdapter, LazyTranslationAdapter lazyTranslationAdapter) {
        this.lazyOperationMethods = lazyOperationMethods;
        this.lazyDynamicAdapter = lazyDynamicAdapter;
        this.cureAdapter = cureAdapter;
        this.lazyTranslationAdapter = lazyTranslationAdapter;
    }

    /**
     * @param proxy     代理对象
     * @param method    代理方法
     * @param args      代理方法参数
     * @param retryTime 重试
     * @param throwable 异常
     * @param hasRetry  是否已经重试
     * @return 返回的执行结果
     * @throws Throwable 异常信息
     */
    @Override
    public Object retryInvoke(Object proxy, Method method, Object[] args, int retryTime, Throwable throwable, boolean hasRetry) throws Throwable {
        ProxyLazyStrategicApproach mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ProxyLazyStrategicApproach.class);
        if (null != mergedAnnotation) {
            Connection connection;
            boolean isConnectionTransactional;

            boolean autoCommit;
            LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_METHOD_MAP.get(mergedAnnotation.proxyClass());
            if (null == lazyOperationMethod) {
                throw new IllegalArgumentException("无法找到对应class ：【" + mergedAnnotation.proxyClass() + "】的代理实现");
            }
            final DataSource dataSource = lazyDynamicAdapter.determineDataSource();
            // 切换数据库
            try {
                connection = LazyDataSourceUtils.getConnection(dataSource);
                switchSchema(connection);
            } catch (Throwable jdbcThrowable) {
                return cureAdapter.cure(this, proxy, method, args, retryTime, jdbcThrowable);
//                 throw cannotGetJdbcConnectionException;
            }
            isConnectionTransactional = LazyDataSourceUtils.isConnectionTransactional(connection, dataSource);
            autoCommit = connection.getAutoCommit();
            try {
                if (connection.isClosed()) {
                    log.warn("this.connection  关闭了");
                }

                // 判断不是事物重新获取链接
                final Object execute = lazyOperationMethod.execute(connection, args);
                // 是否提交
                if (connection != null && !isConnectionTransactional && !autoCommit) {
                    log.debug("Committing JDBC Connection [" + connection + "]");
                    connection.commit();
                }
                lazyTranslationAdapter.transformation(execute);
                return execute;
            } catch (Exception exception) {
                // 事物回滚
                if (connection != null && !isConnectionTransactional && !autoCommit) {
                    log.debug("Rolling back JDBC Connection [" + connection + "]");
                    connection.rollback();
                }
                //
                return cureAdapter.cure(this, proxy, method, args, retryTime, exception);
//                exception.printStackTrace();
//                throw exception;
            } finally {
                // 释放链接
                LazyDataSourceUtils.releaseConnection(connection, dataSource);
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
     * 确定数据源
     *
     * @return DataSource 当前数据源
     * @author Jia wei Wu
     * @date 2022/1/1 5:02 下午
     **/
    public DataSource determineConnection() throws SQLException {
        return lazyDynamicAdapter.determineDataSource();
    }
}
