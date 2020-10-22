package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :  自定义数据库持久层操作方法插入
 * @date : 2020/7/3 下午10:28
 */
@GetCustomRepositoryOnDifferentMethods(GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.INSERT)
public class LayerOperationMethodInsert extends AbstractLayerOperationMethod {

    @Override
    public CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString;

        // 第一个参数 list
        if (!ObjectUtils.isEmpty(args)) {
            Object o = args[0];
            Class clazz = o.getClass();
            queryString = PreparedStatementSQLConverter.insertPreparedStatementSQL(Arrays.asList(o), clazz);
            CustomPersistenceRepository customPersistenceRepository = new CustomPersistenceRepository();
            customPersistenceRepository.setQueryString(queryString);
            customPersistenceRepository.setResultClass(clazz);
            return customPersistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
    }
}
