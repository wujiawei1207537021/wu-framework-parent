package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.ConvertedField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新  自定义数据库持久层操作方法我选择一种
 * @date : 2020/7/4 下午7:22
 */
@Component
public class LazyOperationMethodSelectOne extends AbstractLazyOperationMethod {


    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {
        String queryString = "";
        Object object = param;
        Class clazz = object.getClass();
        queryString = selectPreparedStatementSQL(object);
//        System.out.println(queryString);
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    @Override
    public Object execute(DataSource dataSource, Object[] sourceParams) throws SQLException {

        Connection connection = dataSource.getConnection();
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(sourceParams[0]);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
            if (result.size() > 1) {
                throw new IllegalArgumentException(" expected one but found " + result.size());
            }
            if (ObjectUtils.isEmpty(result)) {
                return null;
            }
            return result.get(0);
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }


    /**
     * 查询生sql
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午4:00
     **/
    public <T> String selectPreparedStatementSQL(Object o) {
        Class clazz = o.getClass();
        //  SELECT FROM
        StringBuffer stringBuffer = new StringBuffer(" SELECT * FROM  ");
        stringBuffer.append(tableName(clazz));
        // where
        stringBuffer.append(" where ");
        boolean punctuationFlag = false;
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(clazz,null);
        for (ConvertedField convertedField : convertedFieldList) {
            try {
                Field field = o.getClass().getDeclaredField(convertedField.getFieldName());
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object fieldVal = field.get(o);
                if (ObjectUtils.isEmpty(fieldVal)) {
                    continue;
                }
                // add data
                if (punctuationFlag) {
                    stringBuffer.append(" and ");
                }
                stringBuffer.append(convertedField.getConvertedFieldName()).append(" = '").append(fieldVal).append("'");
                punctuationFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
