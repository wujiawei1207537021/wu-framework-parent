package com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.persistence.proxy;

import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.config.HBaseConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/27 10:57 下午
 */
@ConditionalOnBean(HBaseConfig.class)
public class HBaseOperationProxy implements InvocationHandler {


    // TODO 名称相同的方法不区分类型无法做到重载
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke");
        return null;
    }

}
