package com.wu.framework.jvm.platform;

import lombok.Data;

@Data
public class Mem {

    private double total;  // 内存总量
    private double used;   // 已用内存
    private double free;   // 剩余内存

}
