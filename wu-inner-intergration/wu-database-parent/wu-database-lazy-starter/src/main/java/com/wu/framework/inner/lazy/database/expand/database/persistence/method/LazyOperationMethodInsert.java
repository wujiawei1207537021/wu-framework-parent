package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Collections;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :  自定义数据库持久层操作方法插入
 * @date : 2020/7/3 下午10:28
 */
@Deprecated
@Component
public class LazyOperationMethodInsert extends AbstractLazyOperationMethod implements MySQLDataProcessAnalyze {


    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws Exception {
            MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult;

            PersistenceRepository persistenceRepository = new PersistenceRepository();
            // 第一个参数 list
            if (param instanceof Collection) {
                Collection collection = (Collection) param;
                Class clazz = collection.iterator().next().getClass();
                mySQLProcessResult = dataPack(Collections.singletonList(collection), classLazyTableAnalyze(clazz));
                persistenceRepository.setResultClass(clazz);
            } else {
                mySQLProcessResult = dataPack(Collections.singletonList(param), classLazyTableAnalyze(param.getClass()));
                persistenceRepository.setResultClass(param.getClass());
            }
            persistenceRepository.setQueryString(mySQLProcessResult.getSql());
            persistenceRepository.setBinaryList(mySQLProcessResult.getBinaryList());
            return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param dataSource
     * @param params
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(DataSource dataSource, Object[] params) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(params);
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        for (int i = 0; i < persistenceRepository.getBinaryList().size(); i++) {
            preparedStatement.setBinaryStream(i + 1, persistenceRepository.getBinaryList().get(i));
        }
        try {
            return preparedStatement.execute();
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }
}
