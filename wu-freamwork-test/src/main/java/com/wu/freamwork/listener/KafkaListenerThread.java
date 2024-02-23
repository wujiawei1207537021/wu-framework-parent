package com.wu.freamwork.listener;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.support.Acknowledgment;
import com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListener;
import org.springframework.stereotype.Component;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/11/2 10:20 下午
 */
@Component
public class KafkaListenerThread {


    //    @EasyListener(topics = "english_word", concurrency = "1")
    @EasyKafkaListener(topics = "english_word", concurrency = "1")
//    @KafkaListener(topics = "english_word", concurrency = "1",groupId = "wujiawei")
    public void xxx(ConsumerRecord consumerRecord, Acknowledgment acknowledgment) {
        System.out.println(consumerRecord.payload());
    }
}
