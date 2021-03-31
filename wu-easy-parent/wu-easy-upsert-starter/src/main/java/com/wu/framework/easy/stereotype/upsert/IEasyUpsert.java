package com.wu.framework.easy.stereotype.upsert;


import com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter;
import com.wu.framework.easy.stereotype.upsert.factory.EasyThreadFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public interface IEasyUpsert extends InitializingBean {

    ThreadPoolExecutor easyUpsertExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20), new EasyThreadFactory());

    <T> Object upsert(List<T> list) throws Exception;


    /**
     * 模糊插入
     *
     * @param objects
     * @param <T>
     * @return
     * @throws Exception
     */
    default <T> Object fuzzyUpsert(Object... objects) throws Exception {
        for (Object object : objects) {
            if (object instanceof List) {
                upsert((List<T>) object);
            } else {
                List<List> listList = EasyAnnotationConverter.extractData(null, object);
                for (List list : listList) {
                    upsert((List<T>) list);
                }

            }
        }
        return true;
    }

    /**
     * @return
     * @describe 将List分割成多个List
     * @params
     * @author Jia wei Wu
     * @date 2020/12/6 5:34 下午
     **/
    default <T> List<List<T>> splitList(List<T> source, int groupSize) {
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

    @Override
    default void afterPropertiesSet() throws Exception {

    }
}
