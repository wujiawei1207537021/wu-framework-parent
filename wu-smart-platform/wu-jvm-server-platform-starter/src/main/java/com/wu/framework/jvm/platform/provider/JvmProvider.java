package com.wu.framework.jvm.platform.provider;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.jvm.platform.Cpu;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.management.ManagementFactory;


import com.sun.management.OperatingSystemMXBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

@EasyController("/jvm")
public class JvmProvider {


    @ApiOperation(value = "获取运行时内存信息")
    @GetMapping("/runTimeMemory")
    public Result<HashMap<String, Long>> runTimeMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
//        System.out.println("java虚拟机从操纵系统那里挖到的最大的内存   maxMemory " + maxMemory + "M");
//        System.out.println("java虚拟机已经从操作系统那里挖过来的内存   totalMemory : " + totalMemory + "M");
//        System.out.println("java虚拟机从操纵系统挖过来还没用上的内存   freeMemory : " + freeMemory + "M");
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        HashMap<String, Long> runTimeMemory = new HashMap<String, Long>();
        runTimeMemory.put("maxMemory", maxMemory);
        runTimeMemory.put("totalMemory", totalMemory);
        runTimeMemory.put("freeMemory", freeMemory);
        runTimeMemory.put("useMemory", totalMemory - freeMemory);
        return ResultFactory.successOf(runTimeMemory);
    }

    @ApiOperation(value = "gc")
    @GetMapping("/gc")
    public Result<Void> gc() {
        System.gc();
        return ResultFactory.successOf();
    }

    @ApiOperation(value = "cpu")
    @GetMapping("/cpu")
    public Result<Cpu> cpu() {
        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double processCpuUsage = osBean.getProcessCpuLoad() * 100;// 进程cpu使用
        double cpuUsage = osBean.getCpuLoad() * 100; // 系统cpu使用



        String name = osBean.getName();// cup 名称
        String arch = osBean.getArch();// cup 架构
        String version = osBean.getVersion();// 版本
        int availableProcessors = osBean.getAvailableProcessors();// 核数

        Cpu cpu = new Cpu();
        cpu.setCpuNum(availableProcessors);
        cpu.setSys(new BigDecimal(cpuUsage).setScale(2,RoundingMode.HALF_UP).doubleValue());// 保留两位小数
        cpu.setName(name);
        cpu.setArch(arch);
        cpu.setVersion(version);
        cpu.setUsed(new BigDecimal(processCpuUsage).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return ResultFactory.successOf(cpu);
    }

}
