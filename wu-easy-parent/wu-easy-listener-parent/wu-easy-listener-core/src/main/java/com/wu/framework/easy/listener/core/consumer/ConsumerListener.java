package com.wu.framework.easy.listener.core.consumer;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/13 23:06
 */
public interface ConsumerListener<Schema, Payload> {

    /**
     * describe 监听主题
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/13 23:07
     **/
    String topic();

    /**
     * describe 监听
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/13 23:08
     **/
    void onMessage(ConsumerRecord<Schema, Payload> consumerRecord);
}
