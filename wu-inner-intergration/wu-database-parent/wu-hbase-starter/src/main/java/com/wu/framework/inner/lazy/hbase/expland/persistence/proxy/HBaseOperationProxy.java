package com.wu.framework.inner.lazy.hbase.expland.persistence.proxy;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import com.wu.framework.inner.lazy.hbase.expland.persistence.method.HBaseOperationMethod;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/27 10:57 下午
 */
@ConditionalOnBean(Connection.class)
public class HBaseOperationProxy implements InvocationHandler, InitializingBean {

    private final List<HBaseOperationMethod> hBaseOperationMethodList;
    private final Connection connection;

    private Map<String, HBaseOperationMethod> HBASE_OPERATION_METHOD_MAP;

    public HBaseOperationProxy(List<HBaseOperationMethod> hBaseOperationMethodList, Connection connection) {
        this.hBaseOperationMethodList = hBaseOperationMethodList;
        this.connection = connection;
    }


    // TODO 名称相同的方法不区分类型无法做到重载
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (HBASE_OPERATION_METHOD_MAP.containsKey(method.getName())) {
            return HBASE_OPERATION_METHOD_MAP.get(method.getName()).execute(connection, args);
        } else {
            throw new RuntimeException(String.format("Can't find a way %s", method.getName()));
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        HBASE_OPERATION_METHOD_MAP = hBaseOperationMethodList.stream().collect(Collectors.toMap(hBaseOperationMethod -> {
            RepositoryOnDifferentMethods mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(hBaseOperationMethod.getClass(), RepositoryOnDifferentMethods.class);
            return mergedAnnotation.methodName();
        }, m -> m));

    }
}
