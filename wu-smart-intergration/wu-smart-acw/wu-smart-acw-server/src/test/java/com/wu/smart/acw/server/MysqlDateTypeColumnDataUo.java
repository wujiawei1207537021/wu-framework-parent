package com.wu.smart.acw.server;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/2/15 20:43
 */
@Data
public class MysqlDateTypeColumnDataUo {

    private Integer aInteger = 10;
    private int intNum = 10;
    private Double aDouble = 2.0D;
    private double adouble = 2.0d;
    private Long aLong = 20L;
    private long along = 222l;
    private float afloat = 1.0f;
    private Float aFloat = 2.2F;
    private LocalTime localTime = LocalTime.now();
    private LocalDateTime localDateTime = LocalDateTime.now();
    private LocalDate localDate = LocalDate.now();
    private java.util.Date date = new java.util.Date();
    private java.sql.Date date1 = new java.sql.Date(System.currentTimeMillis());
    private boolean aboolean = true;
    private Boolean aBoolean = false;


}
