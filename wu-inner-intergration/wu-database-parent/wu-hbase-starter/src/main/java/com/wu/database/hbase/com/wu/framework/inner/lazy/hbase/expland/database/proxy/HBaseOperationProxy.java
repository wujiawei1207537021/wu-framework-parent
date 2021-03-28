package com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.proxy;

import org.apache.hadoop.hbase.client.Admin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/27 10:57 下午
 */
//@ConditionalOnBean(HBaseConfig.class)
public class HBaseOperationProxy implements InvocationHandler {



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke");
        return null;
    }

}
