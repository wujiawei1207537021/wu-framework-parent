package com.wu.framework.easy.upsert.core.dynamic;


import com.wu.framework.easy.upsert.autoconfigure.factory.EasyThreadFactory;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.easy.upsert.core.dynamic.function.EasyUpsertFunction;
import com.wu.framework.inner.layer.data.ClassSchema;
import com.wu.framework.inner.layer.data.DefaultClassSchema;
import com.wu.framework.inner.layer.data.LayerDataAnalyzeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public interface IEasyUpsert extends LayerDataAnalyzeAdapter, InitializingBean {
    Logger log = LoggerFactory.getLogger(IEasyUpsert.class);

    ThreadPoolExecutor easyUpsertExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20), new EasyThreadFactory());

    <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException, ExecutionException, InterruptedException;


    /**
     * 模糊插入 支持任意对象
     *
     * @param objects
     * @param <T>
     * @return
     */
    default <T> Object fuzzyUpsert(Object... objects) {
        Arrays.stream(objects).parallel().forEach(object -> {
            if (object instanceof List) {
                try {
                    List<T> sourceList = (List<T>) object;
                    upsert(sourceList, new DefaultClassSchema(sourceList.get(0).getClass()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                List<List> listList = extractData(null, object);
                listList.stream().parallel().forEach(list -> {
                    try {
                        final List<T> sourceList = (List<T>) list;
                        upsert(sourceList, new DefaultClassSchema(sourceList.get(0).getClass()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        return true;
    }


    /**
     *  数据切分后分组分线程操作
     * @param source
     * @param groupSize
     * @param easyUpsertFunction
     * @param <T>
     * @throws ExecutionException
     * @throws InterruptedException
     */
    default <T> void splitListThen(List<T> source, int groupSize, EasyUpsertFunction easyUpsertFunction) throws ExecutionException, InterruptedException {
        Integer total = (source.size() + groupSize - 1) / groupSize;
        log.info("计划处理步骤 【{}】 步", total);
        AtomicInteger stepCount = new AtomicInteger(0);

        for (List<T> ts : splitList(source, groupSize)) {
            stepCount.getAndIncrement();
            final Future<?> submit = easyUpsertExecutor.submit(() -> {
                easyUpsertFunction.handle(ts);
                log.info("处理步骤第 【{}】 步 ,总步数 【{}】", stepCount, total);
            });
            submit.get();
        }
    }

    @Override
    default void afterPropertiesSet() throws Exception {

    }
}
