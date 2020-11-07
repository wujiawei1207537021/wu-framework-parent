package com.demo.kafka.transform;

import com.supconit.its.transform.entity.KafkaJsonMessageWrapper;
import com.supconit.its.transform.entity.kafka.KafkaJsonMessage;
import com.supconit.its.transform.kafka.KafkaMessageTransformService;
import com.supconit.its.transform.util.JsonUtils;
import com.wu.kafka.until.JsonKafkaSchemaFileConverter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author Jia wei Wu
 * @Date 2020-05-15 2:46 下午
 */
@Component
public class ToEs implements KafkaMessageTransformService<String> {


    @Override
    public List<KafkaJsonMessageWrapper<String>> transforms(String originMessage) {
        KafkaJsonMessage kafkaJsonMessage = JsonUtils.parseObject(originMessage, KafkaJsonMessage.class);
        List<String> users = JsonKafkaSchemaFileConverter.parseArray(kafkaJsonMessage.getPayload().toString());
        return getKafkaJsonMessageList("connect_sink_test_data_todb", users, "test_kafka_user");

    }
}
