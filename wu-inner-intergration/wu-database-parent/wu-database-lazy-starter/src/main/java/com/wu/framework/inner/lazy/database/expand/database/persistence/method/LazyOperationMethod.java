package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * description 自定义数据库持久层操作方法
 *
 * @author Jia wei Wu
 * @date 2020/7/28 上午8:55
 */
public interface LazyOperationMethod {

    /**
    * @describe 获取持久性存储库
    * @param
    * @return
    * @author Jia wei Wu
    * @date 2021/4/17 3:38 下午
    **/
    @NonNull
    PersistenceRepository analyzePersistenceRepository(Method method, Object[] args) throws Exception;

    /**
     * description 执行SQL 语句
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     **/
    default Object execute(PreparedStatement preparedStatement, PersistenceRepository persistenceRepository) throws SQLException {
        try {
            return preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            preparedStatement.close();
        }
    }

}
