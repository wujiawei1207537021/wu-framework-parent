package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义数据库持久层操作方法向上插入
 * @date : 2020/7/3 下午10:28
 */
@GetCustomRepositoryOnDifferentMethods(GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.UPSERT_LIST)
public class LayerOperationMethodUpInsert extends AbstractLayerOperationMethod {

    @Override
    public CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException {

        // 第一个参数 list
        if (args[0] instanceof Collection && !ObjectUtils.isEmpty(args)) {
            Collection collection = (Collection) args[0];
            Class clazz = collection.iterator().next().getClass();
            String queryString = PreparedStatementSQLConverter.upsertPreparedStatementSQL(collection, clazz);
            CustomPersistenceRepository customPersistenceRepository = new CustomPersistenceRepository();
            customPersistenceRepository.setQueryString(queryString);
            customPersistenceRepository.setResultClass(clazz);
            return customPersistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
    }
}
