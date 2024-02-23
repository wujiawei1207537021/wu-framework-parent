package com.wu.framework.easy.rocketmq.listener.ack;

import com.wu.framework.easy.listener.core.support.Acknowledgment;
import org.apache.rocketmq.client.consumer.LitePullConsumer;


/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/12/14 9:32 下午
 */
public class RocketAcknowledgment implements Acknowledgment {

    private final LitePullConsumer pullConsumer;

    public RocketAcknowledgment(LitePullConsumer pullConsumer) {
        this.pullConsumer = pullConsumer;
    }

    @Override
    public void acknowledge() {
        pullConsumer.commitSync();
    }
}
