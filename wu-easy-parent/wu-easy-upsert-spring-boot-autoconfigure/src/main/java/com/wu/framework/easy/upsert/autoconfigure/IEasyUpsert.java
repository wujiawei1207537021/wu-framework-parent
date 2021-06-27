package com.wu.framework.easy.upsert.autoconfigure;


import com.wu.framework.easy.upsert.autoconfigure.factory.EasyThreadFactory;
import com.wu.framework.inner.layer.data.LayerDataAnalyzeAdapter;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public interface IEasyUpsert extends LayerDataAnalyzeAdapter, InitializingBean {

    ThreadPoolExecutor easyUpsertExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20), new EasyThreadFactory());

    <T> Object upsert(List<T> list) throws Exception;


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
                    upsert((List<T>) object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                List<List> listList = extractData(null, object);
                listList.stream().parallel().forEach(list -> {
                    try {
                        upsert((List<T>) list);
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
