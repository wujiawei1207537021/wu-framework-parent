package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.AbstractLazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import java.sql.*;
import java.util.Collection;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 去除null的字段
 * @date : 2020/7/3 下午10:28
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodUpsertRemoveNull extends AbstractLazyOperationMethod {


    public LazyOperationMethodUpsertRemoveNull(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    /**
     * description 通过参数获取持久性存储库对象
     *
     * @param sourceParams 反射传过来的对象
     * @return PersistenceRepository 持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        String sql = LazyTableUpsertConverterFactory.upsertRemoveNull(sourceParams[0]);
        PersistenceRepository persistenceRepository = createPersistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.UPSERT_REMOVE_NULL);
        persistenceRepository.setQueryString(sql);
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
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {
        Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            // 是否修改
            for (Object o : objects) {
                if (Collection.class.isAssignableFrom(o.getClass())) {
                    Collection collection = (Collection) o;
                    for (Object item : collection) {
                        Object generatedKey = accurateExecution(connection, item);
                    }
                    log.warn("使用灵活更新、去除null、创建表 插入的对象数据是时list 消耗性能，建议初始化表后使用upsert方法操作！");
                } else {
                    Object generatedKey = accurateExecution(connection, o);
                }
            }
        } else {
            Object generatedKey = accurateExecution(connection, param);
        }
        return sourceParams;
    }

    /**
     * @param connection 数据源
     * @param param      单个对象或是单条记录
     * @return describe 精准执行
     * @author Jia wei Wu
     * @date 2021/4/18 10:13 上午
     **/
    @Override
    public Object accurateExecution(Connection connection, Object param) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(new Object[]{param});
        String sql = persistenceRepository.getQueryString();
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(param.getClass());
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(param.getClass());
        List<LazyTableFieldEndpoint> lazyTableFieldEndpoints = lazyTableEndpoint.specifiedFieldAnnotation(LayerField.LayerFieldType.ID);
        PreparedStatement preparedStatement;
        if (ObjectUtils.isEmpty(lazyTableFieldEndpoints)) {
            preparedStatement = connection.prepareStatement(sql);
        } else {
            // 会创建id自增
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }

        try {
            preparedStatement.execute();


            if (!ObjectUtils.isEmpty(lazyTableFieldEndpoints)) {
                Object generatedKey = null;
                // 只会有一个
                LazyTableFieldEndpoint fieldEndpoint = lazyTableFieldEndpoints.get(0);
                Class<?> fieldClazz = fieldEndpoint.getClazz();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedKey = resultSet.getObject(1, fieldClazz);
                }

                fieldEndpoint.getField().set(param, generatedKey);
            }

            return null;
        } catch (SQLException sqlException) {
            throw new SQLException(sql, sqlException);
        } finally {
            preparedStatement.close();
        }
    }

}
