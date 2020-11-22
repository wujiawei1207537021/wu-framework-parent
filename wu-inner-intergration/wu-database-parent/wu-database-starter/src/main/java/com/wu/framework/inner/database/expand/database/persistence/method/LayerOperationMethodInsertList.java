package com.wu.framework.inner.database.expand.database.persistence.method;

import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 批量插入 自定义数据库持久层操作方法插入列表
 * @date : 2020/7/4 下午7:22
 */
@RepositoryOnDifferentMethods(RepositoryOnDifferentMethods.LayerOperationMethodEnum.INSERT_LIST)
public class LayerOperationMethodInsertList extends AbstractLayerOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString;
        // 第一个参数 list
        if (args[0] instanceof Collection && !ObjectUtils.isEmpty(args)) {
            Collection collection = (Collection) args[0];
            Class clazz = collection.iterator().next().getClass();
            queryString = PreparedStatementSQLConverter.insertPreparedStatementSQL(collection, clazz);
            PersistenceRepository persistenceRepository = new PersistenceRepository();
            persistenceRepository.setQueryString(queryString);
            persistenceRepository.setResultClass(clazz);
            return persistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
    }
}
