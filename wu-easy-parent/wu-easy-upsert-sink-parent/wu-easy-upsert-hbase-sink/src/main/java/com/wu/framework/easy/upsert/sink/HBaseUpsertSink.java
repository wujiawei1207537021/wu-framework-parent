package com.wu.framework.easy.upsert.sink;

import com.wu.framework.easy.upsert.autoconfigure.IEasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
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
    public <T> Object upsert(List<T> list) throws Exception {
        Integer total = (list.size() + springUpsertAutoConfigure.getBatchLimit() - 1) / springUpsertAutoConfigure.getBatchLimit();
        log.info("计划处理步骤 【{}】 步", total);
        int stepCount = 1;
        for (List<T> ts : splitList(list, springUpsertAutoConfigure.getBatchLimit())) {
            log.info("处理步骤第 【{}】 步 ,总步数 【{}】", stepCount, total);
            easyUpsertExecutor.submit((Callable) () -> hBaseOperation.upsertList(ts)).get();
            stepCount++;
        }
        log.info("分步操作完成✅");
        return true;
    }

}
