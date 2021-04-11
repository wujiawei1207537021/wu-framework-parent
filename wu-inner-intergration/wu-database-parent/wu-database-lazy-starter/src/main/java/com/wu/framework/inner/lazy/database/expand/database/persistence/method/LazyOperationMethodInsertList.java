package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.expand.database.persistence.constant.LayerOperationMethodCounts;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 批量插入 自定义数据库持久层操作方法插入列表
 * @date : 2020/7/4 下午7:22
 */
@ProxyStrategicApproach(methodName = LayerOperationMethodCounts.INSERT_LIST)
public class LazyOperationMethodInsertList extends AbstractLazyOperationMethod implements MySQLDataProcessAnalyze{


    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws Exception {
        String queryString;
        // 第一个参数 list
        if (args[0] instanceof Collection && !ObjectUtils.isEmpty(args)) {
            Collection collection = (Collection) args[0];
            Class clazz = collection.iterator().next().getClass();
            MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult = dataPack(Collections.singletonList(collection), classLazyTableAnalyze(clazz));
            PersistenceRepository persistenceRepository = new PersistenceRepository();
            persistenceRepository.setQueryString(mySQLProcessResult.getSql());
            persistenceRepository.setBinaryList(mySQLProcessResult.getBinaryList());
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
