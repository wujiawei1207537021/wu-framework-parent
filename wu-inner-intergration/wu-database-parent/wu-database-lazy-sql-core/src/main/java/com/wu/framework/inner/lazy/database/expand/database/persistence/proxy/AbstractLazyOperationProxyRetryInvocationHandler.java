package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.layer.stereotype.proxy.AbstractProxyRetryInvocationHandler;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.toolkit.LazyCureContextHolder;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * description LazyOperationProxy 抽象类
 * 下游DDL代理、DQL代理、DML代理
 *
 * @author 吴佳伟
 * @date 2023/01/15 21:58
 */
@Slf4j
public abstract class AbstractLazyOperationProxyRetryInvocationHandler extends AbstractProxyRetryInvocationHandler {


    /**
     * description: 确定数据源头
     *
     * @param
     * @return
     * @author 吴佳伟
     * @date: 15.1.23 22:03
     */
    public abstract DataSource determineConnection() throws SQLException;

    /**
     * 重试结束后操作
     *
     * @param hasRetry 是否重试
     */
    @Override
    public void afterRetryInvoke(boolean hasRetry) {
        if (hasRetry) {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /**
     * invoke 后续操作
     */
    public void afterInvoke() {
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        //  从当前线程中获取治愈次数 如果治愈次数为空 默认治愈一次
        Integer cureTime = LazyCureContextHolder.peek();
        if (ObjectUtils.isEmpty(cureTime)) {
            cureTime = 1;
        }
        Object retryInvoke = retryInvoke(proxy, method, args, cureTime, null, false);
        afterInvoke();
        return retryInvoke;
    }

    /**
     * 切换数据库
     *
     * @param connection 链接对象
     */
    public Connection switchSchema(Connection connection) {
        LazyDynamicEndpoint ds = DynamicLazyDSContextHolder.peek();
        if (null != ds && !ObjectUtils.isEmpty(ds.getSchema())) {
            String schema = ds.getSchema();
            try {
                connection.setSchema(schema);
                log.debug("切换schema:【{}】 成功", schema);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("切换数据库失败:【{}】", e.getMessage());
            }
        }
        return connection;
    }
}
