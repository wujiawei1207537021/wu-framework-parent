package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * description 自定义数据库持久层操作方法
 * @author 吴佳伟
 * @date 2020/7/28 上午8:55
 */
public interface LayerOperationMethod {

    @Nullable
    CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException;

    default Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        try {
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
                preparedStatement.close();
        }
        return false;
    }

}
