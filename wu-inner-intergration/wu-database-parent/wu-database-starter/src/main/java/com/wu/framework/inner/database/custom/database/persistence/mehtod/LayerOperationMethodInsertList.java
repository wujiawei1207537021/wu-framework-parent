package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @describe: 批量插入 自定义数据库持久层操作方法插入列表
 * @author : 吴佳伟
 * @date : 2020/7/4 下午7:22
 * @version : 1.0
 */
@GetCustomRepositoryOnDifferentMethods( GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.INSERT_LIST)
public class LayerOperationMethodInsertList extends AbstractLayerOperationMethod {

    @Override
    public CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString;
        // 第一个参数 list
        if (args[0] instanceof Collection && !ObjectUtils.isEmpty(args)) {
            Collection collection = (Collection) args[0];
            Class clazz = collection.iterator().next().getClass();
            queryString = PreparedStatementSQLConverter.insertPreparedStatementSQL(collection, clazz);
            CustomPersistenceRepository customPersistenceRepository =  new CustomPersistenceRepository();
            customPersistenceRepository.setQueryString(queryString);
            customPersistenceRepository.setResultClass(clazz);
            return customPersistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method"+method.getName());
        }
    }
}
