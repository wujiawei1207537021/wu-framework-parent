package com.wu.smart.acw.server.domain.database.instance.type;

import lombok.Data;

@Data
public class DatabaseInstanceType {

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
