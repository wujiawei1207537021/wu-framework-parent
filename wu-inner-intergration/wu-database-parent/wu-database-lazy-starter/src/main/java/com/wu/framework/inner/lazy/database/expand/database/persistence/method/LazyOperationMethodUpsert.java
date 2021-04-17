package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.SQLAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义数据库持久层操作方法向上插入
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodUpsert extends AbstractLazyOperationMethod implements SQLAnalyze {

    @Override
    public PersistenceRepository analyzePersistenceRepository(Object... params) throws IllegalArgumentException {
        // 第一个参数 list
        Collection collection = (Collection) params[0];
        Class clazz = collection.iterator().next().getClass();
        String queryString = upsertPreparedStatementSQL(collection, clazz);
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }
}
