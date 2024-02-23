package com.wu.smart.acw.server.application.dto;
import lombok.Data;

@Data
public class AcwRedisDatabaseDTO {

    private String instanceId;

    private int database;

    private String type="database";

    /**
     * 统计key数量
     */
    private long keysNum;

}
