package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyTableStructureConverterFactory;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableStructure;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.wu.framework.inner.layer.data.NormalUsedString.BACKTICK;
import static com.wu.framework.inner.layer.data.NormalUsedString.SINGLE_QUOTE;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :  自定义数据库持久层操作方法插入
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodInsert extends AbstractLazyOperationMethod {
    private final LazyOperationConfig operationConfig;


    public LazyOperationMethodInsert(LazyOperationConfig operationConfig) {
        this.operationConfig = operationConfig;
    }


    /**
     * description 通过参数获取持久性存储库对象
     *
     * @param insert
     * @return
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object insert) throws Exception {

        LazyTableStructure lazyTableStructure = LazyTableStructureConverterFactory.dataStructure(insert);
        final LazyTableEndpoint endpoint = lazyTableStructure.schema();
        final List<List<Object>> payload = lazyTableStructure.payload();
        final String tableName = endpoint.getTableName();
        final String schema = endpoint.getSchema();

        StringBuffer stringBuffer = new StringBuffer(Persistence.ExecutionEnum.INSERT.getExecution());
        if (ObjectUtils.isEmpty(schema)) {
            stringBuffer.append(tableName);
        } else {
            stringBuffer.append(schema + NormalUsedString.DOT + tableName);
        }

        stringBuffer.append(NormalUsedString.LEFT_BRACKET);

        stringBuffer.append(endpoint.getFieldEndpoints().
                stream().
                filter(fieldLazyTableFieldEndpoint -> !LazyTableFieldId.IdType.AUTOMATIC_ID.equals(fieldLazyTableFieldEndpoint.getIdType())).
                map(fieldLazyTableFieldEndpoint -> {
                    final String columnName = fieldLazyTableFieldEndpoint.getColumnName();
                    if (LazyDatabaseJsonMessage.specialFields.contains(columnName.toUpperCase(Locale.ROOT))) {
                        return BACKTICK + columnName + BACKTICK;
                    } else {
                        return columnName;
                    }
                }).
                collect(Collectors.joining(NormalUsedString.COMMA)));
        stringBuffer.append(NormalUsedString.RIGHT_BRACKET);
        stringBuffer.append("values");

        stringBuffer.append(
                payload.stream().map(
                                // every item
                                item -> NormalUsedString.LEFT_BRACKET + item.stream().map(o -> null == o ? null : SINGLE_QUOTE + o + SINGLE_QUOTE).collect(Collectors.joining(NormalUsedString.COMMA)) + NormalUsedString.RIGHT_BRACKET).
                        collect(Collectors.joining(NormalUsedString.COMMA)));

        String sql = stringBuffer.toString();
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create(operationConfig);
        persistenceRepository.setQueryString(sql);

        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(Connection connection, Object[] sourceParams) throws Exception {
        Object param = sourceParams[0];

        if (param instanceof Object[]) {
            Object[] insertList = (Object[]) param;
            for (Object insert : insertList) {
                accurateExecution(connection, insert);
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
     * @author Jia wei Wu
     * @date 2021/4/26 5:12 下午
     */
    @Override
    public Object accurateExecution(Connection connection, Object param) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(param);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());

        try {
            final boolean execute = preparedStatement.execute();
            return execute;
        } catch (Exception e) {
//            log.error(persistenceRepository.getQueryString());
            throw new RuntimeException(e);

        } finally {
            preparedStatement.close();
        }
    }

}
