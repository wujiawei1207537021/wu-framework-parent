package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * describe: 完善表
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/2 5:03 下午
 */
@Slf4j
@Component
public class LazyOperationMethodPerfect extends AbstractLazyOperationMethod {
    private final LazyOperationConfig operationConfig;

    public LazyOperationMethodPerfect(LazyOperationConfig operationConfig) {
        this.operationConfig = operationConfig;
    }


    /**
     * @param params
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object params) throws Exception {

        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create(operationConfig);

        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(Connection connection, Object[] sourceParams) throws Exception {
        final Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            for (Object o : objects) {
                final Class<?> entityClass = (Class<?>) o;
                perfect(connection, entityClass);
            }
        } else {
            final Class<?> entityClass = (Class<?>) param;
            perfect(connection, entityClass);
        }
        return true;
    }


}
