package com.demo.kafka.runner;

import com.demo.kafka.User;
import com.supconit.its.transform.config.DataProcessConfig;
import com.supconit.its.transform.entity.KafkaJsonMessageWrapper;
import com.supconit.its.transform.entity.kafka.KafkaJsonMessage;
import com.supconit.its.transform.kafka.KafkaProducer;
import com.wu.kafka.until.JsonKafkaSchemaFileConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-15 2:31 下午
 */
@Component
public class TestRunner {

    String KEY = "BusDynamicArrayLeaveStationDataRunner|" + ThreadLocalRandom.current().nextInt(100);
    @Resource
    private KafkaProducer kafkaProducer;
    @Resource
    private DataProcessConfig dataProcessConfig;

    @Scheduled(fixedRate = 15000)
    public void run() {

        KafkaJsonMessage<String> kafkaJsonMessage = new KafkaJsonMessage();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(i);
            user.setUserName("名字" + i);
            users.add(user);
        }
        kafkaJsonMessage.setPayload(JsonKafkaSchemaFileConverter.listBean2String(users));
        KafkaJsonMessageWrapper<String> kafkaMessage = new KafkaJsonMessageWrapper(dataProcessConfig.getKafkaSourceTopic(), System.currentTimeMillis(), KEY, kafkaJsonMessage);
        kafkaProducer.send(kafkaMessage);

    }
}
