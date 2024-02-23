package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.insert;

import com.wu.framework.inner.layer.data.LayerDataAnalyzeAdapter;
import com.wu.framework.inner.layer.util.LazyListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/12/30 6:09 下午
 */
public interface MultipleInsertLambdaStream extends LayerDataAnalyzeAdapter, SimpleInsertLambdaStream {
    Logger log = LoggerFactory.getLogger(MultipleInsertLambdaStream.class);


    /**
     * describe 大批量数据 upsert
     *
     * @param save 存储对象
     * @author Jia wei Wu
     * @date 2022/12/30 8:44 下午
     **/

    default <T> void upsertMultiple(List<T> save) {
        upsertMultiple(save, 1000);
    }


    /**
     * 大批量数据 数据插入
     *
     * @param save 存储对象
     */
    default <T> void insertMultiple(List<T> save) {
        insertMultiple(save, 1000);
    }

    /**
     * describe 大批量数据  upsert
     *
     * @param save      存储对象
     * @param groupSize 每次插入数据的大小
     * @author Jia wei Wu
     * @date 2022/12/30 8:44 下午
     **/

    default <T> void upsertMultiple(List<T> save, int groupSize) {
        splitListThen(save, groupSize, this::upsert);
    }


    /**
     * 大批量数据 数据插入
     *
     * @param save      存储对象
     * @param groupSize 每次插入数据的大小
     */
    default <T> void insertMultiple(List<T> save, int groupSize) {
        splitListThen(save, groupSize, this::insert);
    }

    /**
     * 数据切分后分组分线程操作
     *
     * @param source
     * @param groupSize
     * @param consumer
     * @param <T>
     */
    default <T> void splitListThen(List<T> source, int groupSize, Consumer<List<T>> consumer) {
        Integer total = (source.size() + groupSize - 1) / groupSize;
        log.info("计划处理步骤 【{}】 步", total);
        AtomicInteger stepCount = new AtomicInteger(0);

        for (List<T> ts : LazyListUtils.splitList(source, groupSize)) {
            stepCount.getAndIncrement();
            consumer.accept(ts);
            log.info("处理步骤第 【{}】 步 ,总步数 【{}】", stepCount, total);
        }
    }

}
