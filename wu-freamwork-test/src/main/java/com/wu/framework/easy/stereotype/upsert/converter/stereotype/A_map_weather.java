package com.wu.framework.easy.stereotype.upsert.converter.stereotype;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@EasySmart(tableName = "upsert", columnFamily = "a_map_weather", perfectTable = true)
@Data
public class A_map_weather {
    private String date;
    private String city;
    private String adcode;
    private String windpower;
    private String updateTime;
    private String reporttime;
    private String winddirection;
    private String province;
    private Integer hour;
    private Boolean isDeleted;
    private Timestamp createTime;
    private String weather;
    private String temperature;
    private String humidity;
    private BigInteger id;
}