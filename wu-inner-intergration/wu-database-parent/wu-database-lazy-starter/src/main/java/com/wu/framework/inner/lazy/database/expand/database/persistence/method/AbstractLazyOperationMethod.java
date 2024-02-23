package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaBasicTypeDefaultValue;
import com.wu.framework.inner.layer.stereotype.MethodParamFunction;
import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.ConvertedField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasyHashMap;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description 抽象自定义数据库持久层操作方法
 *
 * @author Jia wei Wu
 * @date 2020/7/28 上午8:56
 */
public abstract class AbstractLazyOperationMethod implements LazyOperationMethod, MySQLDataProcessAnalyze {




    /**
     * 是否基本数据类型
     *
     * @param
     * @return
     * @author Jiawei Wu
     * @date 2021/1/3 12:54 下午
     **/
    public static boolean isWrapClass(Class clazz) {
        try {
            if (String.class.isAssignableFrom(clazz)) return true;
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

//    /**
//     * description 执行SQL 语句
//     *
//     * @param dataSource 数据源
//     * @param params     代理方法参数
//     * @return
//     * @params
//     * @author Jia wei Wu
//     * @date 2020/11/22 上午11:02
//     */
//    @Override
//    public Object execute(DataSource dataSource, Object[] params) throws Exception {
//        Connection connection = dataSource.getConnection();
//        PersistenceRepository persistenceRepository = analyzePersistenceRepository(params);
//        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
//        try {
//            return preparedStatement.execute();
//        } catch (SQLException sqlException) {
//            throw sqlException;
//        } finally {
//            connection.close();
//            preparedStatement.close();
//        }
//    }

    /**
     * @param dataSource 数据源
     * @param param      单个对象或是单条记录
     * @return
     * describe 精准执行
     * @author Jia wei Wu
     * @date 2021/4/18 10:13 上午
     **/
    public Object accurateExecution(DataSource dataSource, Object param) throws Exception {
        final Object o = executionFunction(dataSource, preparedStatement -> {
            preparedStatement.execute();
            return preparedStatement;
        }, param);
        return o;
    }

    /**
     * @param dataSource          数据源
     * @param methodParamFunction 返回数据中含有 PreparedStatement
     * @param param               参数
     * @return   执行结果
     * describe 执行函数
     * @author Jia wei Wu
     * @date 2021/4/18 10:46 上午
     **/
    public Object executionFunction(DataSource dataSource, MethodParamFunction<PreparedStatement> methodParamFunction, Object param) throws Exception {
        Connection connection = dataSource.getConnection();
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(param);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            return methodParamFunction.defaultMethod(preparedStatement);
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }


    /**
     * description 结果集转换器
     * @param resultSet 结果集
     * @param resultType  返回结果类型字符串
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 4:02 下午
     */
    public <E> List<E> resultSetConverter(ResultSet resultSet, String resultType) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Class domainClass = null;
        try {
            domainClass = Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSetConverter(resultSet, domainClass);
    }

    /**
     * description 结果集转换器
     * @param resultSet 结果集
     * @param domainClass  返回结果类型
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 4:02 下午
     */
    public <E> List<E> resultSetConverter(ResultSet resultSet, Class<E> domainClass) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
            //封装结果集
            List list = new ArrayList();//定义返回值

            // Map 数值
            if (Map.class.isAssignableFrom(domainClass)) {

                while (resultSet.next()) {
                    Map hashMap = (Map) domainClass.newInstance();
                    //取出结果集的元信息：ResultSetMetaData
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    if (EasyHashMap.class.isAssignableFrom(domainClass)) {
                        String tableName = resultSetMetaData.getTableName(1);
                        ((EasyHashMap) hashMap).setUniqueLabel(CamelAndUnderLineConverter.capitalizeFirstLetter(tableName));
                    }
                    //取出总列数
                    int columnCount = resultSetMetaData.getColumnCount();
                    //遍历总列数
                    for (int i = 1; i <= columnCount; i++) {
                        //获取每列的名称，列名的序号是从1开始的
                        String columnName = resultSetMetaData.getColumnName(i);
                        // 获取数据库列字段类型
                        String columnClassName = resultSetMetaData.getColumnClassName(i);
                        //根据得到列名，获取每列的值
                        Object columnValue = resultSet.getObject(i);
//                        if(null==columnValue)columnValue=JavaBasicTypeDefaultValue.DEFAULT_CLASS_NAME_VALUE_HASHMAP.get(columnClassName);
                        if (EasyHashMap.class.isAssignableFrom(domainClass)) {
                            ((EasyHashMap) hashMap).put(CamelAndUnderLineConverter.lineToHumpField(columnName), columnValue,columnClassName);
                        }else {
                            hashMap.put(CamelAndUnderLineConverter.lineToHumpField(columnName), columnValue);
                        }

                    }
                    //把赋好值的对象加入到集合中
                    list.add(hashMap);
                }

            } else if (isWrapClass(domainClass)) {
                //基本数据类型
                while (resultSet.next()) {
                    Object convertBasicTypeBean = JavaBasicTypeDefaultValue.convertBasicTypeBean(domainClass, resultSet.getObject(1));
                    list.add(convertBasicTypeBean);
                }
            } else {
                List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(domainClass,null);
                Map<String, String> convertedFieldMap = convertedFieldList.stream().collect(Collectors.toMap(ConvertedField::getFieldName, ConvertedField::getFieldName));
                while (resultSet.next()) {
                    //实例化要封装的实体类对象
                    E obj = (E) domainClass.newInstance();
                    //取出结果集的元信息：ResultSetMetaData
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    //取出总列数
                    int columnCount = rsmd.getColumnCount();
                    //遍历总列数
                    for (int i = 1; i <= columnCount; i++) {
                        //获取每列的名称，列名的序号是从1开始的
                        String columnName = rsmd.getColumnName(i).toLowerCase();
                        columnName = CamelAndUnderLineConverter.lineToHumpField(columnName);
                        String columnLabel = rsmd.getColumnLabel(i);
                        String fieldName = convertedFieldMap.getOrDefault(columnName, convertedFieldMap.get(columnLabel));
                        if (ObjectUtils.isEmpty(fieldName)) {
                            continue;
                        }
                        //根据得到列名，获取每列的值
//                        Object columnValue = resultSet.getObject(columnName);
                        Field field = domainClass.getDeclaredField(fieldName);
                        Class<?> fieldType = field.getType();
                        field.setAccessible(true);
                        resultSet.getObject(i, fieldType);
//                        columnValue = convertToTheCorrespondingType(columnValue, field.getType());
                        Object columnValue = resultSet.getObject(i, fieldType);
                        field.set(obj, columnValue);
                    }
                    //把赋好值的对象加入到集合中
                    list.add(obj);
                }
            }
            return list;
    }

    /**
     * 转换成对应的对象
     *
     * @param obj
     * @param clazz
     * @return
     */
    protected <T> T convertToTheCorrespondingObject(Object obj, Class<T> clazz) {
        return null;
    }

    /**
     * 装载sql参数
     */
    public String loadSqlParameters(String sqlFormat, Object... params) {
        return String.format(sqlFormat, params);
    }

}