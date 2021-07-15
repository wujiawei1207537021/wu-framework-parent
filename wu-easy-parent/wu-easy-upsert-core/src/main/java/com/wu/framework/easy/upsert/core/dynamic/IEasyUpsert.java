package com.wu.framework.easy.upsert.core.dynamic;


import com.wu.framework.easy.upsert.autoconfigure.factory.EasyThreadFactory;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.inner.layer.data.ClassSchema;
import com.wu.framework.inner.layer.data.DefaultClassSchema;
import com.wu.framework.inner.layer.data.LayerDataAnalyzeAdapter;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public interface IEasyUpsert extends LayerDataAnalyzeAdapter, InitializingBean {

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
                    upsert(sourceList,new DefaultClassSchema(sourceList.get(0).getClass()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                List<List> listList = extractData(null, object);
                listList.stream().parallel().forEach(list -> {
                    try {
                        final List<T> sourceList = (List<T>) list;
                        upsert(sourceList,new DefaultClassSchema(sourceList.get(0).getClass()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        return true;
    }


    @Override
    default void afterPropertiesSet() throws Exception {

    }
}
