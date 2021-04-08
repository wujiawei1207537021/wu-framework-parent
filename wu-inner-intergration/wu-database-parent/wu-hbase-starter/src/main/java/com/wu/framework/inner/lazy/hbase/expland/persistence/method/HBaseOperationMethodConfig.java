package com.wu.framework.inner.lazy.hbase.expland.persistence.method;

import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
import com.wu.framework.inner.lazy.hbase.expland.persistence.proxy.HBaseOperationProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Proxy;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/29 7:20 下午
 */
@ComponentScan
public class HBaseOperationMethodConfig {
    private final HBaseOperationProxy hBaseOperationProxy;

    public HBaseOperationMethodConfig(HBaseOperationProxy hBaseOperationProxy) {
        this.hBaseOperationProxy = hBaseOperationProxy;
    }


    /**
     * @param
     * @return
     * @describe hBase 数据操作接口
     * @author Jia wei Wu
     * @date 2021/3/28 9:32 下午
     **/
    @Bean
    public HBaseOperation hBaseOperation() {
        return (HBaseOperation) Proxy.newProxyInstance(HBaseOperation.class.getClassLoader(), new Class[]{HBaseOperation.class}, hBaseOperationProxy);
    }


}
