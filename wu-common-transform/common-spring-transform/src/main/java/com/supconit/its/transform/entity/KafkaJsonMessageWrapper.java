package com.supconit.its.transform.entity;

import com.supconit.its.transform.entity.kafka.KafkaJsonMessage;
import lombok.Data;

/**
 * kafka json消息包装类，包括消息
 *
 * @author 黄珺
 * @date 2019/8/30
 **/
@Data
public class KafkaJsonMessageWrapper<T> {

    /**
     * 目标topic
     */
    private String topic;

    /**
     * 时间戳，手动指定以用于elastic的索引日期切分
     */
    private Long timestamp;

    /**
     * key，一般为数据的id，可以用于指定elastic的_id值,避免重复
     */
    private String key;

    /**
     * json格式的消息内容，带有在配置文件中指定的schema信息
     */
    private KafkaJsonMessage<T> value;

    public KafkaJsonMessageWrapper(String topic, Long timestamp, String key, KafkaJsonMessage<T> value) {
        this.topic = topic;
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
    }
}
