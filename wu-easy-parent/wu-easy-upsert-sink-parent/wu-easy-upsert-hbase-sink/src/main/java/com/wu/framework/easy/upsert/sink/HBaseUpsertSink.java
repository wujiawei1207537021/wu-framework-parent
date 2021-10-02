package com.wu.framework.easy.upsert.sink;

import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.easy.upsert.core.dynamic.function.EasyUpsertFunction;
import com.wu.framework.inner.layer.data.ClassSchema;
import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/12/20 8:27 下午
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.hbase", value = "zookeeper-quorum")
//@ConditionalOnBean(HBaseOperationProxy.class)
@EasyUpsertStrategy(value = EasyUpsertType.HBASE)
public class HBaseUpsertSink implements IEasyUpsert {

    private final HBaseOperation hBaseOperation;
    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;

    public HBaseUpsertSink(HBaseOperation hBaseOperation, SpringUpsertAutoConfigure springUpsertAutoConfigure) {
        this.hBaseOperation = hBaseOperation;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
    }


    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException, ExecutionException, InterruptedException {
        splitListThen(list, springUpsertAutoConfigure.getBatchLimit(), hBaseOperation::upsertList);
        log.info("分步操作完成✅");
        return true;
    }

}
