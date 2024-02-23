package com.wu.framework.easy.mysql.binlog.listener.consumer;

import com.wu.framework.easy.listener.core.consumer.ConsumerListener;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/13 23:12
 */
public interface BinLogConsumerListener<Schema, Payload> extends ConsumerListener<Schema, Payload> {

    /**
     * describe 获取Payload 的class
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/13 23:15
     **/
    Class<? extends Payload> payloadClass();

    /**
     * describe 监听主题
     *
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/13 23:07
     **/
    @Override
    default String topic() {
        return LazyTableUtil.getTableName(payloadClass());
    }
}
