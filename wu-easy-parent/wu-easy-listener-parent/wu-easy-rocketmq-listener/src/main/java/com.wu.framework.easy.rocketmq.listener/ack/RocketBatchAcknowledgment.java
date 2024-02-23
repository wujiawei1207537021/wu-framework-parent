package com.wu.framework.easy.rocketmq.listener.ack;

import com.wu.framework.easy.listener.core.support.Acknowledgment;
import org.apache.rocketmq.client.consumer.LitePullConsumer;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/5 8:54 下午
 */
public class RocketBatchAcknowledgment implements Acknowledgment {
    private LitePullConsumer pullConsumer;

    public RocketBatchAcknowledgment(LitePullConsumer pullConsumer) {
        this.pullConsumer = pullConsumer;
    }

    @Override
    public void acknowledge() {
        pullConsumer.commitSync();
    }
}
