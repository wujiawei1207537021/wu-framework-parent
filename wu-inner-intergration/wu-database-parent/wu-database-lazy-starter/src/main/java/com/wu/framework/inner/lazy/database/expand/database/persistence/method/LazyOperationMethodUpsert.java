package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 自定义数据库持久层操作方法向上插入
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodUpsert extends AbstractLazyOperationMethod {
    private final LazyOperationConfig operationConfig;

    public LazyOperationMethodUpsert(LazyOperationConfig operationConfig) {
        this.operationConfig = operationConfig;
    }


    /**
     * @param params 参数实体对象的任意状态数组或者是集合
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object params) throws Exception {
        MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult;

        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create(operationConfig);
        // 第一个参数 list
        if (params instanceof Collection) {
            List collection = (List) params;
            Class clazz = collection.iterator().next().getClass();
            mySQLProcessResult =
                    processAnalyze.upsertDataPack(collection, processAnalyze.dataAnalyze(clazz, EasyHashMap.class.isAssignableFrom(clazz) ? (EasyHashMap) collection.get(0) : null));
            persistenceRepository.setResultClass(clazz);
        } else {
            mySQLProcessResult = processAnalyze.upsertDataPack(Collections.singletonList(params), processAnalyze.dataAnalyze(params.getClass(), EasyHashMap.class.isAssignableFrom(params.getClass()) ? (EasyHashMap) params : null));
            persistenceRepository.setResultClass(params.getClass());
        }
        persistenceRepository.setQueryString(mySQLProcessResult.getSql());
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
        Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] upsertList = (Object[]) param;
            for (Object upsert : upsertList) {
                accurateExecution(connection, upsert);
            }
        } else {
            accurateExecution(connection, param);
        }
        return sourceParams.length;
    }

    /**
     * @param connection 数据源
     * @param param      单个对象或是单条记录
     * @return describe 精准执行
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/26 5:12 下午
     */
    @Override
    public Object accurateExecution(Connection connection, Object param) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(param);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            return preparedStatement.execute();
        } finally {
            preparedStatement.close();
        }
    }
}
