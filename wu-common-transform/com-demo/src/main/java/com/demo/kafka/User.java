package com.demo.kafka;

import com.wu.kafka.stereotype.KafkaSchema;
import com.wu.kafka.stereotype.KafkaSchemaFile;
import lombok.Data;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-15 2:26 下午
 */
@Data
@KafkaSchema(name = "test_kafka_user")
public class User {

//    @KafkaSchemaFile(field = "test_id")
    private Integer id;

    @KafkaSchemaFile(field = "test_name")
    private String userName;
}
