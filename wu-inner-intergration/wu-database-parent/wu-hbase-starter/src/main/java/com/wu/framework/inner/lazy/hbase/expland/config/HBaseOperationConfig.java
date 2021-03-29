package com.wu.framework.inner.lazy.hbase.expland.config;

import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
import com.wu.framework.inner.lazy.hbase.expland.persistence.proxy.HBaseOperationProxy;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Proxy;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/29 10:11 下午
 */
public class HBaseOperationConfig {
    private final HBaseOperationProxy hBaseOperationProxy;

    public HBaseOperationConfig(HBaseOperationProxy hBaseOperationProxy) {
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
