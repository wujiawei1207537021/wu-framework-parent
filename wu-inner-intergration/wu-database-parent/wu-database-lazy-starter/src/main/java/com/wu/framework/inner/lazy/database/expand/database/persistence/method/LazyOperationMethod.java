package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.sql.Connection;
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
     * @param
     * @param param
     * @return
     * @describe 获取持久性存储库
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @NonNull
    PersistenceRepository analyzePersistenceRepository(Object param) throws Exception;

    /**
     * description 执行SQL 语句
     *
     * @param dataSource
     * @param sourceParams
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    default Object execute(DataSource dataSource, Object[] sourceParams) throws Exception {
        for (Object param : sourceParams) {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(analyzePersistenceRepository(param).getQueryString());
            try {
                return preparedStatement.execute();
            } catch (SQLException sqlException) {
                throw sqlException;
            } finally {
                connection.close();
                preparedStatement.close();
            }
        }
        return null;

    }

}
