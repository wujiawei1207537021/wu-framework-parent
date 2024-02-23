package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
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
public class HBaseUpsert implements IEasyUpsert {

    private final HBaseOperation hBaseOperation;
    private final UpsertConfig upsertConfig;

    public HBaseUpsert(HBaseOperation hBaseOperation, UpsertConfig upsertConfig) {
        this.hBaseOperation = hBaseOperation;
        this.upsertConfig = upsertConfig;
    }


    @Override
    public <T> Object upsert(List<T> list) throws Exception {
        Integer total = (list.size() + upsertConfig.getBatchLimit() - 1) / upsertConfig.getBatchLimit();
        log.info("计划处理步骤 【{}】 步", total);
        int stepCount = 1;
        for (List<T> ts : splitList(list, upsertConfig.getBatchLimit())) {
            log.info("处理步骤第 【{}】 步 ,总步数 【{}】", stepCount, total);
            easyUpsertExecutor.submit((Callable) () -> hBaseOperation.upsertList(ts)).get();
            stepCount++;
        }
        log.info("分步操作完成✅");
        return true;
    }

}
