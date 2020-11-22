package com.wu.framework.inner.database.expand.database.persistence.method;

import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义数据库持久层操作方法向上插入
 * @date : 2020/7/3 下午10:28
 */
@RepositoryOnDifferentMethods(RepositoryOnDifferentMethods.LayerOperationMethodEnum.UPSERT_LIST)
public class LayerOperationMethodUpInsert extends AbstractLayerOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws IllegalArgumentException {

        // 第一个参数 list
        if (args[0] instanceof Collection && !ObjectUtils.isEmpty(args)) {
            Collection collection = (Collection) args[0];
            Class clazz = collection.iterator().next().getClass();
            String queryString = PreparedStatementSQLConverter.upsertPreparedStatementSQL(collection, clazz);
            PersistenceRepository persistenceRepository = new PersistenceRepository();
            persistenceRepository.setQueryString(queryString);
            persistenceRepository.setResultClass(clazz);
            return persistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
    }
}
