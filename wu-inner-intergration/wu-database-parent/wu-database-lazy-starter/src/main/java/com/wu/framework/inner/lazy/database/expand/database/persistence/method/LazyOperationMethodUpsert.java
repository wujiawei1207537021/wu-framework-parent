package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.constant.LayerOperationMethodCounts;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义数据库持久层操作方法向上插入
 * @date : 2020/7/3 下午10:28
 */
@RepositoryOnDifferentMethods(methodName = LayerOperationMethodCounts.UPSERT_LIST)
public class LazyOperationMethodUpsert extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws Exception {
        // 第一个参数 list
        if (args[0] instanceof Collection && !ObjectUtils.isEmpty(args)) {
            Collection collection = (Collection) args[0];
            Class clazz = collection.iterator().next().getClass();
            String queryString = SQLConverter.upsertPreparedStatementSQL(collection, clazz);
            PersistenceRepository persistenceRepository = new PersistenceRepository();
            persistenceRepository.setQueryString(queryString);
            persistenceRepository.setResultClass(clazz);
            return persistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
    }
}
