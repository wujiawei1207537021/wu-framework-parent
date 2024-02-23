package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.AbstractLazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.factory.LazyTableStructureConverterFactory;
import com.wu.framework.inner.lazy.factory.LazyTableUpdateConverterFactory;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableStructure;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.wu.framework.inner.layer.data.NormalUsedString.BACKTICK;
import static com.wu.framework.inner.layer.data.NormalUsedString.SINGLE_QUOTE;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :  自定义数据库持久层操作方法插入或更新（根据主键、唯一性索引）
 * @date : 2020/7/3 下午10:28
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodSaveOrUpdate extends AbstractLazyOperationMethod {


    public LazyOperationMethodSaveOrUpdate(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    /**
     * description 通过参数获取持久性存储库对象
     *
     * @param sourceParams
     * @return
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {


        LazyTableStructure lazyTableStructure = LazyTableStructureConverterFactory.dataStructure(sourceParams);
        final LazyTableEndpoint endpoint = lazyTableStructure.schema();
        final List<List<LazyTableFieldEndpoint>> payload = lazyTableStructure.payload();
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
        stringBuffer.append(NormalUsedString.VALUES);

        stringBuffer.append(
                payload.stream().map(
                                // every item
                                item -> NormalUsedString.LEFT_BRACKET + item.stream().map(o -> null == o.getFieldValue() ? null : SINGLE_QUOTE + o.getFieldValue() + SINGLE_QUOTE).collect(Collectors.joining(NormalUsedString.COMMA)) + NormalUsedString.RIGHT_BRACKET).
                        collect(Collectors.joining(NormalUsedString.COMMA)));

        String sql = stringBuffer.toString();
        PersistenceRepository persistenceRepository = createPersistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.INSERT);
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
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {
        Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] insertList = (Object[]) param;
            for (Object saveOrUpdate : insertList) {
                accurateExecution(connection, saveOrUpdate);
            }
        } else {
            accurateExecution(connection, param);
        }
        return sourceParams.length;
    }

    /**
     * @param connection   数据源
     * @param saveOrUpdate 单个对象或是单条记录
     * @return describe 精准执行
     * @author Jia wei Wu
     * @date 2021/4/26 5:12 下午
     */
    @Override
    public Object accurateExecution(Connection connection, Object saveOrUpdate) throws Exception {
        // 解析主键、唯一性索引


        LazyTableStructure lazyTableStructure = LazyTableStructureConverterFactory.dataStructure(saveOrUpdate);
        // 获取主键
        List<LazyTableFieldEndpoint> lazyTableFieldEndpoints = lazyTableStructure.payload().get(0);
        boolean anyMatchKey = lazyTableFieldEndpoints.stream().anyMatch(lazyTableFieldEndpoint -> lazyTableFieldEndpoint.isKey() && null != lazyTableFieldEndpoint.getFieldValue());
        if (anyMatchKey) {

            LazyTableFieldEndpoint fieldEndpoint = lazyTableStructure.schema()
                    .getFieldEndpoints()
                    .stream()
                    .filter(LazyTableFieldEndpoint::isKey)
                    .findFirst()
                    .get();
            String pk = fieldEndpoint.getColumnName();
            Object pkValue = fieldEndpoint.getFieldValue();
            // 根据主键查询
            String selectPKSql = String.format("select count(1) from %s where %s = %s ", lazyTableStructure.schema().getTableName(), pk, pkValue);
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectPKSql);
            try {
                ResultSet resultSet = selectPreparedStatement.executeQuery();
                List<Integer> countPk = resultSetConverter(resultSet, Integer.class);
                if (countPk.get(0) == 1) {
                    // 根据主键更新
                    String updateSql = LazyTableUpdateConverterFactory.updateByPK(saveOrUpdate);
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                    try {
                        return preparedStatement.execute();
                    } catch (Exception e) {
                        throw new SQLException(updateSql, e);
                    } finally {
                        preparedStatement.close();
                    }
                }
            } catch (Exception e) {
                throw new SQLException(selectPKSql, e);
            } finally {
                selectPreparedStatement.close();
            }


        }
        // 唯一性索引
        Collection<LazyTableFieldEndpoint> whereLazyTableFieldEndpointList =
                lazyTableStructure
                        .payload().get(0)
                        .stream()
                        .filter(lazyTableFieldEndpoint -> {

                            // 根据唯一性索引进行分组，而后根据分组后的数据比对value是否为空
                            return Arrays.stream(
                                            lazyTableFieldEndpoint.getLazyTableIndexEndpoints())
                                    .anyMatch(
                                            // TODO 唯一性索引名称进行分组
                                            lazyTableIndexEndpoint ->
                                                    LayerField.LayerFieldType.UNIQUE.equals(lazyTableIndexEndpoint.getFieldIndexType())
                                    ) && null != lazyTableFieldEndpoint.getFieldValue();
                        })
                        .collect(Collectors.toList());
        // 是否存在唯一性索引
        boolean anyMatchUniqueKey = lazyTableFieldEndpoints.stream()
                .anyMatch(
                        lazyTableFieldEndpoint ->
                                Arrays
                                        .stream(lazyTableFieldEndpoint
                                                .getLazyTableIndexEndpoints())
                                        .anyMatch(
                                                // TODO 唯一性索引名称进行分组
                                                lazyTableIndexEndpoint ->
                                                        LayerField.LayerFieldType.UNIQUE.equals(lazyTableIndexEndpoint.getFieldIndexType())
                                        ) && null != lazyTableFieldEndpoint.getFieldValue());


        if (anyMatchUniqueKey) {
            // 根据唯一性索引更新数据
            String selectCountUniqueSql = String.format("select count(1) from %s where %s ",
                    lazyTableStructure.schema().getTableName(),
                    whereLazyTableFieldEndpointList
                            .stream()
                            .map(
                                    lazyTableFieldEndpoint ->
                                            NormalUsedString.SPACE + lazyTableFieldEndpoint.getColumnName() + NormalUsedString.EQUALS +
                                                    NormalUsedString.SINGLE_QUOTE + lazyTableFieldEndpoint.getFieldValue() + NormalUsedString.SINGLE_QUOTE
                            )
                            .collect(Collectors.joining(NormalUsedString.SPACE + NormalUsedString.AND))
            );
            PreparedStatement selectCountUniquePreparedStatement = connection.prepareStatement(selectCountUniqueSql);
            try {
                ResultSet resultSet = selectCountUniquePreparedStatement.executeQuery();
                List<Long> countUnique = resultSetConverter(resultSet, Long.class);
                if (countUnique.get(0) > 0L) {
                    String updateSql = LazyTableUpdateConverterFactory.updateUniqueKey(saveOrUpdate);
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                    try {
                        return preparedStatement.execute();
                    } catch (Exception e) {
                        throw new SQLException(updateSql, e);
                    } finally {
                        preparedStatement.close();
                    }
                }
            } catch (Exception e) {
                throw new SQLException(selectCountUniqueSql, e);
            } finally {
                selectCountUniquePreparedStatement.close();
            }
        }
        //  数据存储
        String insertSql = LazyTableUpsertConverterFactory.insert(saveOrUpdate);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

        try {
            final boolean execute = preparedStatement.execute();
            return execute;
        } catch (Exception e) {
            throw new SQLException(insertSql, e);

        } finally {
            preparedStatement.close();
        }
    }

}
