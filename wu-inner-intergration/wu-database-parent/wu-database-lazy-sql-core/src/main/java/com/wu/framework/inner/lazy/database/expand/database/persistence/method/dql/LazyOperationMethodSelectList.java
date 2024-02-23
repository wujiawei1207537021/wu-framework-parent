package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 根据ID更新  自定义数据库持久层操作方法我选择列表
 * @date : 2020/7/4 下午7:22
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodSelectList extends AbstractLazyDQLOperationMethod {


    public LazyOperationMethodSelectList(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    /**
     * @param sourceParams
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        String queryString = "";
        Object object = sourceParams[0];
        Class<?> clazz = object.getClass();
        queryString = selectPreparedStatementSQL(object);
        PersistenceRepository persistenceRepository = createPersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
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
    public Object doExecute(Connection connection, Object[] sourceParams) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        PersistenceRepository persistenceRepository = null;
        try {
            persistenceRepository = analyzePersistenceRepository(sourceParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return Arrays.asList();
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
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(clazz);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(clazz);
        stringBuffer.append(lazyTableEndpoint.getTableName());
        // where
        stringBuffer.append(" where ");
        boolean punctuationFlag = false;
        List<LazyTableFieldEndpoint> convertedFieldList = LazyTableFieldUtil.analyzeFieldOnAnnotation(clazz, null);
        for (LazyTableFieldEndpoint convertedField : convertedFieldList) {
            try {
                Field field = o.getClass().getDeclaredField(convertedField.getName());
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
                stringBuffer.append(convertedField.getColumnName()).append(" = '").append(fieldVal).append("'");
                punctuationFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

}
