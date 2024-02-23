package com.wu.framework.data.relay.platform.listener;

import com.wu.framework.data.relay.platform.domain.RelayUo;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.mysql.binlog.listener.consumer.BinLogConsumerListener;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/13 23:04
 */
public class RelayListener implements BinLogConsumerListener<String, RelayUo> {

    /**
     * describe 获取Payload 的class
     *
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/13 23:15
     **/
    @Override
    public Class<? extends RelayUo> payloadClass() {
        return RelayUo.class;
    }

    /**
     * describe 监听
     *
     * @param consumerRecord@return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/13 23:08
     **/
    @Override
    public void onMessage(ConsumerRecord<String, RelayUo> consumerRecord) {

    }
}
