package com.wu.kafka.entity;

import com.wu.kafka.stereotype.KafkaSchema;
import com.wu.kafka.stereotype.KafkaSchemaFile;
import lombok.Data;

@Data
@KafkaSchema
public class MesEntity {


    @KafkaSchemaFile(field = "hh", type = "string")
    private String userId;

    private String userName;

}
