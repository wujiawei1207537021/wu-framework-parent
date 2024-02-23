package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.convert.LayerOperationConvert;
import com.wu.framework.inner.layer.stereotype.MethodParamFunction;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyDataFactory;
import com.wu.framework.inner.lazy.persistence.analyze.DefaultMySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.persistence.conf.*;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

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
public abstract class AbstractLazyOperationMethod implements LazyOperationMethod {
    DefaultMySQLDataProcessAnalyze processAnalyze = new DefaultMySQLDataProcessAnalyze();

    Logger log = LoggerFactory.getLogger(AbstractLazyOperationMethod.class);

    /**
     * 是否基本数据类型
     *
     * @param clazz
     * @return boolean
     * @author Jiawei Wu
     * @date 2021/1/3 12:54 下午
     * * @see LayerOperationConvert
     * * {@link LayerOperationConvert}
     **/
    @Deprecated
    public static boolean isWrapClass(Class clazz) {
        try {
            if (String.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Byte[].class.isAssignableFrom(clazz)) {
                return true;
            }
            if (Byte.class.isAssignableFrom(clazz)) {
                return true;
            }
            if (byte[].class.isAssignableFrom(clazz)) {
                return true;
            }
            if (byte.class.isAssignableFrom(clazz)) {
                return true;
            }
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * describe  执行SQL 语句
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 8:10 下午
     **/
    @Override
    public Object execute(Connection connection, Object[] sourceParams) throws Exception {
        for (Object param : sourceParams) {
            PreparedStatement preparedStatement = connection.prepareStatement(analyzePersistenceRepository(param).getQueryString());
            try {
                return preparedStatement.execute();
            } catch (SQLException sqlException) {
                throw sqlException;
            } finally {
                connection.close();
                preparedStatement.close();
            }
        }
        return null;
    }

    /**
     * @param connection 数据源
     * @param param      单个对象或是单条记录
     * @return describe 精准执行
     * @author Jia wei Wu
     * @date 2021/4/18 10:13 上午
     **/
    public Object accurateExecution(Connection connection, Object param) throws Exception {
        return executionFunction(connection, preparedStatement -> {
            preparedStatement.execute();
            return preparedStatement;
        }, param);
    }

    /**
     * @param connection          数据源
     * @param methodParamFunction 返回数据中含有 PreparedStatement
     * @param param               参数
     * @return 执行结果
     * describe 执行函数
     * @author Jia wei Wu
     * @date 2021/4/18 10:46 上午
     **/
    public Object executionFunction(Connection connection, MethodParamFunction<PreparedStatement> methodParamFunction, Object param) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(param);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            return methodParamFunction.defaultMethod(preparedStatement);
        } finally {
            preparedStatement.close();
        }
    }


    /**
     * description 结果集转换器
     *
     * @param resultSet  结果集
     * @param resultType 返回结果类型字符串
     * @return List
     * @exception/throws
     * @author Jia wei Wu
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
     *
     * @param resultSet   结果集
     * @param domainClass 返回结果类型
     * @return
     * @exception/throws
     * @author Jia wei Wu
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
                        ((EasyHashMap) hashMap).put(CamelAndUnderLineConverter.lineToHumpField(columnName), columnValue, columnClassName);
                    } else {
                        hashMap.put(CamelAndUnderLineConverter.lineToHumpField(columnName), columnValue);
                    }

                }
                //把赋好值的对象加入到集合中
                list.add(hashMap);
            }

        } else if (isWrapClass(domainClass)) {
            //基本数据类型
            while (resultSet.next()) {
//                Object convertBasicTypeBean = JavaBasicTypeDefaultValue.convertBasicTypeBean(domainClass, resultSet.getObject(1));
                list.add(resultSet.getObject(1));
            }
        } else {
            List<LazyTableFieldEndpoint> convertedFieldList = LazyTableFieldUtil.analyzeFieldOnAnnotation(domainClass, null);
            Map<String, String> convertedFieldMap = convertedFieldList.stream().collect(
                    Collectors.toMap(LazyTableFieldEndpoint::getName, LazyTableFieldEndpoint::getName));
            while (resultSet.next()) {
                //实例化要封装的实体类对象
                E e = domainClass.newInstance();
                //取出结果集的元信息：ResultSetMetaData
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //取出总列数
                int columnCount = resultSetMetaData.getColumnCount();
                //遍历总列数
                for (int i = 1; i <= columnCount; i++) {
                    //获取每列的名称，列名的序号是从1开始的
                    String columnName = resultSetMetaData.getColumnName(i).toLowerCase();
                    columnName = CamelAndUnderLineConverter.lineToHumpField(columnName);
                    String columnLabel = resultSetMetaData.getColumnLabel(i);
                    String fieldName = convertedFieldMap.getOrDefault(columnName, convertedFieldMap.get(columnLabel));
                    if (ObjectUtils.isEmpty(fieldName)) {
                        continue;
                    }
                    //根据得到列名，获取每列的值
//                        Object columnValue = resultSet.getObject(columnName);
                    Field field = domainClass.getDeclaredField(fieldName);
                    Class<?> fieldType = field.getType();
                    field.setAccessible(true);
//                    resultSet.getObject(i, fieldType);
//                        columnValue = convertToTheCorrespondingType(columnValue, field.getType());
                    Object object = resultSet.getObject(i);
                    LazyDataFactory.handler(e, field, object);
//                    Object columnValue = resultSet.getObject(i, fieldType);
//                    field.set(e, columnValue);
                }
                //把赋好值的对象加入到集合中
                list.add(e);
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
        final PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        final String sql = String.format(sqlFormat, params);
        persistenceRepository.setQueryString(sql);
        return persistenceRepository.getQueryString();
    }


    /**
     * describe 完善表
     *
     * @param connection  连接对象
     * @param easyHashMap 实体对象
     * @return boolean 执行成功或者失败
     * @author Jia wei Wu
     * @date 2022/1/2 5:41 下午
     **/
    public synchronized boolean perfect(Connection connection, EasyHashMap easyHashMap) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        final ClassLazyTableEndpoint classLazyTableEndpoint = easyHashMap.toEasyTableAnnotation();
        return perfect(connection, classLazyTableEndpoint);
    }

    /**
     * describe 完善表
     *
     * @param connection  连接对象
     * @param entityClass 实体对象class
     * @return boolean 执行成功或者失败
     * @author Jia wei Wu
     * @date 2022/1/2 5:41 下午
     **/
    public synchronized boolean perfect(Connection connection, Class entityClass) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {

        // 本地缓存实体class
        if (LazyDatabaseJsonMessage.localCacheEntityClass.contains(entityClass)) {
            return true;
        }
        if (!EasyHashMap.class.isAssignableFrom(entityClass)) {
            LazyDatabaseJsonMessage.localCacheEntityClass.add(entityClass);
        }
        ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
        return perfect(connection, classLazyTableEndpoint);
    }

    /**
     * describe 完善表
     *
     * @param connection             连接对象
     * @param classLazyTableEndpoint 实体对象 schema
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 5:41 下午
     **/
    public synchronized boolean perfect(Connection connection, LazyTableEndpoint classLazyTableEndpoint) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();


        final String schema = ObjectUtils.isEmpty(classLazyTableEndpoint.getSchema()) ? connection.getCatalog() : classLazyTableEndpoint.getSchema();

        ResultSet resultSet = statement.executeQuery(loadSqlParameters("select * from information_schema.tables where table_schema='%s' and table_name='%s' ",
                schema, classLazyTableEndpoint.getTableName()));

        List<LazyTableInfo> lazyTableInfos = resultSetConverter(resultSet, LazyTableInfo.class);

        if (ObjectUtils.isEmpty(lazyTableInfos)) {
            // 创建表
            String createTableSQL = classLazyTableEndpoint.creatTableSQL();
            for (String sql : createTableSQL.split(NormalUsedString.SEMICOLON)) {
                statement.execute(sql);
            }

            log.info("create table {} success", classLazyTableEndpoint.getTableName());
        } else {
            // 更新表数据
            ResultSet resultSetLazyColumn = statement.executeQuery(
                    loadSqlParameters("select * from information_schema.COLUMNS where table_schema='%s' and table_name='%s' ",
                            schema, classLazyTableEndpoint.getTableName()));


            List<LazyColumn> lazyColumnCollection = resultSetConverter(resultSetLazyColumn, LazyColumn.class);

            List<FieldLazyTableFieldEndpoint> currentColumnNameList = new ArrayList<>();
            for (LazyColumn lazyColumn : lazyColumnCollection) {
                FieldLazyTableFieldEndpoint convertedField = new FieldLazyTableFieldEndpoint();
                convertedField.setColumnName(lazyColumn.getColumnName());
                convertedField.setColumnType(lazyColumn.getColumnType());
                currentColumnNameList.add(convertedField);
            }
            String alterTableSQL = classLazyTableEndpoint.alterTableSQL(currentColumnNameList);
            if (!ObjectUtils.isEmpty(alterTableSQL)) {
                boolean execute = statement.execute(alterTableSQL);
            }
        }
        statement.close();
        return true;
    }

}
