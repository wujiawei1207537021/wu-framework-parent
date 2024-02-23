package com.wu.smart.acw.server.application.dto;

import lombok.Data;

@Data
public class AcwRedisKeyValueDTO {

    private String key;

    private String value;

    private String instanceId;

    private int database;

    private String type = "key";

}
