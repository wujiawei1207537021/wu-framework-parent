package com.wu.bionic.point.config;

import com.wu.bionic.point.BreakPointMemory;
import com.wu.bionic.point.so.DefaultBreakPointSo;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description 调度
 *
 * @author Jia wei Wu
 * @date 2021/2/3 下午4:26
 */
@Component
public class Scheduling {

    public static ApplicationContext applicationContext;
    private final BreakPointMemory breakPointMemory;
    // 唤醒状态
    protected boolean wakeStatus = false;

    ThreadPoolExecutor breakPointExecutor = new ThreadPoolExecutor(5, 3000, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(20));


    public Scheduling(BreakPointMemory breakPointMemory, ApplicationContext applicationContext) {
        this.breakPointMemory = breakPointMemory;
        Scheduling.applicationContext = applicationContext;
    }


    /**
     * description 唤醒
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/3 下午4:16
     */
    //每一个小时执行一次
    @Scheduled(cron = "0 0 * * * ?")
    @PostConstruct
    public void wake() {
        if (!wakeStatus) {
            wakeStatus = true;
            Collection<DefaultBreakPointSo> acquisition = breakPointMemory.acquisition();
            // 中断数据重新启动 (多线程方式)
            acquisition.forEach((defaultBreakPointSo) -> {
                breakPointExecutor.execute(() -> {
                    Thread.currentThread().setName(defaultBreakPointSo.getName());
                    int time = 0;
                    while (time < 100) {
                        try {
//                            System.out.println(String.format("正在紧急重试方法【%s】", defaultBreakPointSo.getName()));
                            defaultBreakPointSo.invoke();
                            System.out.println(String.format("重试方法【%s】成功", defaultBreakPointSo.getName()));
                            return;
                        } catch (Exception e) {
                            time++;
                            try {
                                Thread.sleep(3000);
                                System.err.println(String.format("唤醒失败3s后重试,重试第%s次 \n%s", time, e));
                                e.printStackTrace();
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                    System.err.println(String.format("重试方法【%s】失败", defaultBreakPointSo.getName()));
                });
            });
            wakeStatus = false;
        }


    }

}
