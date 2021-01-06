package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :  自定义数据库持久层操作方法插入
 * @date : 2020/7/3 下午10:28
 */
@RepositoryOnDifferentMethods(RepositoryOnDifferentMethods.LayerOperationMethodEnum.INSERT)
public class LazyOperationMethodInsert extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString;

        // 第一个参数 list
        if (!ObjectUtils.isEmpty(args)) {
            Object o = args[0];
            Class clazz = o.getClass();
            queryString = PreparedStatementSQLConverter.insertPreparedStatementSQL(Arrays.asList(o), clazz);
            PersistenceRepository persistenceRepository = new PersistenceRepository();
            persistenceRepository.setQueryString(queryString);
            persistenceRepository.setResultClass(clazz);
            return persistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
    }

    /**
     * description 执行SQL 语句
     *
     * @param preparedStatement
     * @param resultType
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        return super.execute(preparedStatement, resultType);
    }
}
