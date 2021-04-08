package com.wu.framework.easy.stereotype.upsert.converter.stereotype;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@EasySmart(tableName = "upsert", columnFamily = "a_map_weather", perfectTable = true)
@Data
public class A_map_weather {
    private Date date;
    private String city;
    private String adcode;
    private String windpower;
    private Timestamp updateTime;
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