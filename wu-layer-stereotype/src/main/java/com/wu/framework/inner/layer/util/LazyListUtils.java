package com.wu.framework.inner.layer.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public final class LazyListUtils {
    public static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * @param source    原始list 集合
     * @param <T>       list 集合范型
     * @param groupSize 切割大小
     * @return describe 将List分割成多个List
     * @author Jia wei Wu
     * @date 2020/12/6 5:34 下午
     **/
    public static <T> List<List<T>> splitList(List<T> source, int groupSize) {
        int length = source.size();
        // 计算可以分成多少组
        int num = (length + groupSize - 1) / groupSize;
        List<List<T>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * groupSize;
            // 结束位置
            int toIndex = Math.min((i + 1) * groupSize, length);
            newList.add(source.subList(fromIndex, toIndex));
        }
        return newList;
    }

    /**
     * 数据切分后分组分线程操作
     *
     * @param source    原始list 集合
     * @param <T>       list 集合范型
     * @param groupSize 切割大小
     * @param listConsumer 数据消费
     * @param <T> 数据类型
     */
    public static <T> void splitListThen(List<T> source, int groupSize, Consumer<List<T>> listConsumer) throws ExecutionException, InterruptedException {
        Integer total = (source.size() + groupSize - 1) / groupSize;
        log.info("计划处理步骤 【{}】 步", total);
        AtomicInteger stepCount = new AtomicInteger(0);

        for (List<T> ts : LazyListUtils.splitList(source, groupSize)) {
            stepCount.getAndIncrement();
            final Future<?> submit = executorService.submit(() -> {
                listConsumer.accept(ts);
                log.info("处理步骤第 【{}】 步 ,总步数 【{}】", stepCount, total);
            });
            submit.get();
        }
    }
}
