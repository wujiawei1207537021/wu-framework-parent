package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :  自定义数据库持久层操作方法插入
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodInsert extends AbstractLazyOperationMethod implements MySQLDataProcessAnalyze {


    @Override
    public PersistenceRepository analyzePersistenceRepository(Method method, Object[] args) throws Exception {
        if (ObjectUtils.isEmpty(args)) {
            MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult;

            PersistenceRepository persistenceRepository = new PersistenceRepository();
            // 第一个参数 list
            if (args[0] instanceof Collection) {
                Collection collection = (Collection) args[0];
                Class clazz = collection.iterator().next().getClass();
                mySQLProcessResult = dataPack(Collections.singletonList(collection), classLazyTableAnalyze(clazz));
                persistenceRepository.setResultClass(clazz);
            } else {
                mySQLProcessResult = dataPack(Collections.singletonList(args[0]), classLazyTableAnalyze(args[0].getClass()));
                persistenceRepository.setResultClass(args[0].getClass());
            }
            persistenceRepository.setQueryString(mySQLProcessResult.getSql());
            persistenceRepository.setBinaryList(mySQLProcessResult.getBinaryList());

            return persistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }

    }

    /**
     * description 执行SQL 语句
     *
     * @param preparedStatement
     * @param persistenceRepository
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(PreparedStatement preparedStatement, PersistenceRepository persistenceRepository) throws SQLException {
        for (int i = 0; i < persistenceRepository.getBinaryList().size(); i++) {
            preparedStatement.setBinaryStream(i + 1, persistenceRepository.getBinaryList().get(i));
        }
        return super.execute(preparedStatement, persistenceRepository);
    }
}
