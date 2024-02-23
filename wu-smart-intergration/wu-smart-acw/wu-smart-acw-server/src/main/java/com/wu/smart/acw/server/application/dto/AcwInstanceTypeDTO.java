package com.wu.smart.acw.server.application.dto;

import lombok.Data;

@Data
public class AcwInstanceTypeDTO {

    private String typeName;

    /**
     * 类型
     */
    private String type;
    /**
     * 驱动
     */
    private String driver;
}
