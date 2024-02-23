package com.wu.smart.acw.server.application.dto;

import lombok.Data;

@Data
public class AcwRedisKeyDTO {

    private String key;

    private String instanceId;

    private int database;

    private String type = "key";

}
