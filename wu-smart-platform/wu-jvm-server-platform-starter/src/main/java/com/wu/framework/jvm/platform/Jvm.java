package com.wu.framework.jvm.platform;

import lombok.Data;

@Data
public class Jvm {

    private double total;    // 当前JVM占用的内存总数(M)
    private double max;      // JVM空闲内存(M)
    private double free;     // JVM空闲内存(M)
    private String version;  // JDK版本
    private String home;     // JDK路径


//    /**
//     * JDK启动时间
//     */
//    public String getStartTime()
//    {
//        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
//    }
//
//    /**
//     * JDK运行时间
//     */
//    public String getRunTime() {
//        return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
//    }
}
