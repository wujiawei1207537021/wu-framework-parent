package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.SQLAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasyHashMap;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义数据库持久层操作方法向上插入
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodUpsert extends AbstractLazyOperationMethod implements SQLAnalyze {

    @Override
    public PersistenceRepository analyzePersistenceRepository(Object params) throws Exception {
        MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult;

        PersistenceRepository persistenceRepository = new PersistenceRepository();
        // 第一个参数 list
        if (params instanceof Collection) {
            List collection = (List) params;
            Class clazz = collection.iterator().next().getClass();
            mySQLProcessResult = upsertDataPack(collection, dataAnalyze(clazz, EasyHashMap.class.isAssignableFrom(clazz) ? (EasyHashMap) collection.get(0) : null));
            persistenceRepository.setResultClass(clazz);
        } else {
            mySQLProcessResult = upsertDataPack(Collections.singletonList(params), dataAnalyze(params.getClass(), EasyHashMap.class.isAssignableFrom(params.getClass()) ? (EasyHashMap) params : null));
            persistenceRepository.setResultClass(params.getClass());
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
        Object param = params[0];
        if (param instanceof Object[]) {
            Object[] upsertList = (Object[]) param;
            for (Object upsert : upsertList) {
                accurateExecution(dataSource, upsert);
            }
        } else {
            accurateExecution(dataSource, param);
        }
        return params.length;
    }

    /**
     * @param dataSource 数据源
     * @param param      单个对象或是单条记录
     * @return
     * @describe 精准执行
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/26 5:12 下午
     */
    @Override
    public Object accurateExecution(DataSource dataSource, Object param) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(param);
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
