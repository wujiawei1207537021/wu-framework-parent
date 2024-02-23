package com.wu.framework.jvm.platform;

import lombok.Data;

@Data
public class Cpu {

    private int cpuNum;   // 核心数
    private double total; // CPU总的使用率
    private double sys;   // CPU系统使用率
    private double used;  // CPU用户使用率
    private double wait;  // CPU当前等待率
    private double free;  // CPU当前空闲率

    private String name;// cup 名称
    private String arch;// cup 架构
    private String version;// 版本



}
