package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.convert.LayerOperationConvert;
import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import com.wu.framework.inner.layer.data.schema.SchemaMap;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.MethodParamFunction;
import com.wu.framework.inner.lazy.config.enums.DDLAuto;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyColumnIndex;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.field.AbstractLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.index.AbstractLazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.map.DbMap;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.persistence.util.SqlMessageFormatUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description 抽象自定义数据库持久层操作方法
 *
 * @author Jia wei Wu
 * @date 2020/7/28 上午8:56
 */
public abstract class AbstractLazyOperationMethod implements LazyOperationMethod {


    private final LazyOperationParameter lazyOperationParameter;
    Logger log = LoggerFactory.getLogger(AbstractLazyOperationMethod.class);

    protected AbstractLazyOperationMethod(LazyOperationParameter lazyOperationParameter) {
        this.lazyOperationParameter = lazyOperationParameter;
    }

    /**
     * 是否基本数据类型
     *
     * @param clazz class
     * @return boolean
     * @author Jiawei Wu
     * @date 2021/1/3 12:54 下午
     * * @see LayerOperationConvert
     * * {@link LayerOperationConvert}
     **/
    @Deprecated
    public static boolean isWrapClass(Class<?> clazz) {
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
     * 获取 LazyOperationParameter
     *
     * @return LazyOperationParameter
     */
    protected LazyOperationParameter lazyOperationParameter() {
        return this.lazyOperationParameter;
    }

    /**
     * 创建 预执行SQL需要的属性
     *
     * @return 预执行SQL需要的属性
     */
    protected PersistenceRepository createPersistenceRepository() {
        if (ObjectUtils.isEmpty(lazyOperationParameter())) {
            return PersistenceRepositoryFactory.create();
        }
        return PersistenceRepositoryFactory.create(lazyOperationParameter().getLazyOperationConfig());
    }

    /**
     * description 通过参数获取持久性存储库对象
     *
     * @param sourceParams 参数
     * @return PersistenceRepository 持久层对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object[] sourceParams) {
        PersistenceRepository persistenceRepository;
        try {
            persistenceRepository = doAnalyzePersistenceRepository(sourceParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        lazyOperationParameter().getSqlInterceptorAdapter().interceptor(persistenceRepository);
        return persistenceRepository;
    }

    /**
     * 执行解析
     *
     * @param sourceParams 原始数据
     * @return 解析后的 PersistenceRepository 对象
     */

    public abstract PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception;

    /**
     * describe  执行SQL 语句
     *
     * @param connection   当前线程链接对象
     * @param sourceParams 原始参数
     * @return Object 返回对象
     * @author Jia wei Wu
     * @date 2022/1/2 8:10 下午
     **/
    @Override
    public Object execute(Connection connection, Object[] sourceParams) throws Exception {
//        StopWatch stopWatch = new StopWatch();
//
//        stopWatch.start();
        Object o = doExecute(connection, sourceParams);
//        stopWatch.stop();
//        log.debug("SQL执行时间(秒):"+stopWatch.getTotalTimeMillis());
        return o;
    }

    /**
     * describe  执行SQL 语句
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 8:10 下午
     **/
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {
        for (Object param : sourceParams) {
            PreparedStatement preparedStatement = connection.prepareStatement(analyzePersistenceRepository(new Object[]{param}).getQueryString());
            try {
                return preparedStatement.execute();
            } catch (SQLException sqlException) {
                throw sqlException;
            } finally {
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
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(new Object[]{param});
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
     * @throws SQLException,NoSuchFieldException,InstantiationException,IllegalAccessException
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
        List<E> list = new ArrayList<E>();//定义返回值

        // Map 数值
        if (Map.class.isAssignableFrom(domainClass)) {

            while (resultSet.next()) {
                Map hashMap = new HashMap();
                //取出结果集的元信息：ResultSetMetaData
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                if (EasyHashMap.class.isAssignableFrom(domainClass)) {
                    hashMap = new EasyHashMap<>();
                    String tableName = resultSetMetaData.getTableName(1);
                    ((EasyHashMap) hashMap).setUniqueLabel(CamelAndUnderLineConverter.capitalizeFirstLetter(tableName));
                } else if (SchemaMap.class.isAssignableFrom(domainClass)) {
                    hashMap = new SchemaMap<>();
                } else if (DbMap.class.isAssignableFrom(domainClass)) {
                    hashMap = new DbMap();
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
                    // as 别名
                    String columnLabel = resultSetMetaData.getColumnLabel(i);
                    // 优先使用as
                    columnName = ObjectUtils.isEmpty(columnLabel) ? columnName : columnLabel;
//                        if(null==columnValue)columnValue=JavaBasicTypeDefaultValue.DEFAULT_CLASS_NAME_VALUE_HASHMAP.get(columnClassName);
                    if (EasyHashMap.class.isAssignableFrom(domainClass)) {
                        ((EasyHashMap) hashMap).put(CamelAndUnderLineConverter.lineToHumpField(columnName), columnValue, columnClassName);
                    } else if (SchemaMap.class.isAssignableFrom(domainClass)) {
                        hashMap.put(CamelAndUnderLineConverter.lineToHumpField(columnName), columnValue);
                    } else {
                        hashMap.put(columnName, columnValue);
                    }

                }
                //把赋好值的对象加入到集合中
                list.add((E) hashMap);
            }

        } else if (isWrapClass(domainClass)) {
            //基本数据类型
            while (resultSet.next()) {
//                Object convertBasicTypeBean = JavaBasicTypeDefaultValue.convertBasicTypeBean(domainClass, resultSet.getObject(1));
                Object rowData = resultSet.getObject(1);
                E handler = LazyDataFactory.handler(rowData, domainClass);
                list.add(handler);
            }
        } else {
            List<LazyTableFieldEndpoint> convertedFieldList = LazyTableFieldUtil.analyzeFieldOnAnnotation(domainClass, null);
            // 驼峰字段
            Map<String, String> convertedFieldMap = convertedFieldList.stream().collect(Collectors.toMap(LazyTableFieldEndpoint::getName, LazyTableFieldEndpoint::getName));
            // 下划线字段
            convertedFieldMap.putAll(convertedFieldList.stream().collect(Collectors.toMap(lazyTableFieldEndpoint -> CamelAndUnderLineConverter.humpToLine2(lazyTableFieldEndpoint.getName()), LazyTableFieldEndpoint::getName)));
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
                    // 表别名 优先
                    String columnLabel = resultSetMetaData.getColumnLabel(i);
                    String fieldName = convertedFieldMap.getOrDefault(columnLabel, convertedFieldMap.getOrDefault(columnName, null));
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
     * 装载sql参数
     */
    public String loadSqlParameters(String sqlFormat, Object... params) {
        Object[] objects = Arrays.stream(params).map(o -> o).toArray(Object[]::new);
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        String sql = SqlMessageFormatUtil.format(sqlFormat, objects);
//        String.format(sqlFormat, params);
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
        final ClassLazyTableEndpoint lazyTableEndpoint = easyHashMap.toEasyTableAnnotation(false, true);
        return perfect(connection, lazyTableEndpoint);
    }

    /**
     * describe 完善表(添加 DDLAuto 选择完善方式类型)
     *
     * @param connection  连接对象
     * @param entityClass 实体对象class
     * @return boolean 执行成功或者失败
     * @author Jia wei Wu
     * @date 2022/1/2 5:41 下午
     **/
    @Deprecated
    public synchronized boolean perfect(Connection connection, Class entityClass) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        return perfect(connection, entityClass, DDLAuto.PERFECT);
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
    public synchronized boolean perfect(Connection connection, Class entityClass, DDLAuto ddlAuto) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {

        // 本地缓存实体class
        if (LazyDatabaseJsonMessage.localCacheEntityClass.contains(entityClass)) {
            return true;
        }
        if (!EasyHashMap.class.isAssignableFrom(entityClass)) {
            LazyDatabaseJsonMessage.localCacheEntityClass.add(entityClass);
        }
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(entityClass);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
        return perfect(connection, lazyTableEndpoint, ddlAuto);
    }

    /**
     * describe 完善表
     *
     * @param connection        连接对象
     * @param lazyTableEndpoint 实体对象 schema
     * @param ddlAuto           ddl 类型
     * @return boolean 执行成功或者失败
     * @author Jia wei Wu
     * @date 2022/1/2 5:41 下午
     **/
    public synchronized boolean perfect(Connection connection, LazyTableEndpoint lazyTableEndpoint, DDLAuto ddlAuto) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        boolean exist = lazyTableEndpoint.isExist();
        if (exist) {
            Statement statement = connection.createStatement();
            String schema = ObjectUtils.isEmpty(lazyTableEndpoint.getSchema()) ? (ObjectUtils.isEmpty(connection.getCatalog()) ? connection.getSchema() : connection.getCatalog()) : lazyTableEndpoint.getSchema();

            // 查询表是否存在
            ResultSet resultSet = statement.executeQuery(loadSqlParameters("select * from information_schema.tables where table_schema='{0}' and table_name='{1}' ", schema, lazyTableEndpoint.getTableName()));

            List<LazyTableInfo> lazyTableInfos = resultSetConverter(resultSet, LazyTableInfo.class);
            if (ObjectUtils.isEmpty(lazyTableInfos)) {
                // 创建表
                String createTableSQL = lazyTableEndpoint.creatTableSQL();
                for (String sql : createTableSQL.split(NormalUsedString.SEMICOLON)) {
                    statement.execute(sql);
                }

                log.warn("create table {} success", lazyTableEndpoint.getTableName());
            } else {
                // 表存在  不需要创建
            }
            if (DDLAuto.CREATE.equals(ddlAuto)) {
                // 默认都会被执行的
            } else {
                // 更新表数据
                // 查询表原始字段
                ResultSet resultSetLazyColumn = statement.executeQuery(loadSqlParameters("select * from information_schema.COLUMNS where table_schema='{0}' and table_name='{1}' ", schema, lazyTableEndpoint.getTableName()));
                List<LazyColumn> lazyColumnCollection = resultSetConverter(resultSetLazyColumn, LazyColumn.class);


                // 查询表索引
                ResultSet resultSetLazyColumnIndex = statement.executeQuery(loadSqlParameters(" SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE table_name = '{0}' AND table_schema =  '{1}' ", LazyTableFieldUtil.cleanSpecialColumn(lazyTableEndpoint.getTableName()), schema));
                List<LazyColumnIndex> lazyColumnIndexList = resultSetConverter(resultSetLazyColumnIndex, LazyColumnIndex.class);

                // 字段索引降维度
                Map<String, List<LazyColumnIndex>> columnIndexMap = lazyColumnIndexList.stream().collect(Collectors.groupingBy(LazyColumnIndex::getColumnName));

                List<LazyTableFieldEndpoint> currentColumnNameList = new ArrayList<>();
                for (LazyColumn lazyColumn : lazyColumnCollection) {
                    LazyTableFieldEndpoint oldFieldLazyTableFieldEndpoint = AbstractLazyTableFieldEndpoint.getInstance();
                    oldFieldLazyTableFieldEndpoint.setColumnName(lazyColumn.getColumnName());
//                oldFieldLazyTableFieldEndpoint.setOrdinalPosition(lazyColumn.getOrdinalPosition());
                    oldFieldLazyTableFieldEndpoint.setDefaultValue(lazyColumn.getColumnDefault());
                    oldFieldLazyTableFieldEndpoint.setNotNull(!NormalUsedString.YES.equalsIgnoreCase(lazyColumn.getIsNullable()));
                    oldFieldLazyTableFieldEndpoint.setDataType(lazyColumn.getDataType());
//                oldFieldLazyTableFieldEndpoint.setCharacterMaximumLength(lazyColumn.getCharacterMaximumLength());
                    oldFieldLazyTableFieldEndpoint.setScale(lazyColumn.getNumericScale());
                    oldFieldLazyTableFieldEndpoint.setColumnType(lazyColumn.getColumnType());


                    oldFieldLazyTableFieldEndpoint.setExtra(lazyColumn.getExtra());
                    // 权限
//                oldFieldLazyTableFieldEndpoint.setPrivileges(lazyColumn.getPrivileges());
                    oldFieldLazyTableFieldEndpoint.setComment(lazyColumn.getColumnComment());
                    // 存在索引
                    if (columnIndexMap.containsKey(lazyColumn.getColumnName())) {
                        List<LazyColumnIndex> lazyColumnIndexs = columnIndexMap.get(lazyColumn.getColumnName());

                        LazyTableIndexEndpoint[] indexEndpoints = lazyColumnIndexs.stream().map(lazyColumnIndex -> {
                            AbstractLazyTableIndexEndpoint instance = AbstractLazyTableIndexEndpoint.getInstance();
                            // 索引名称
                            instance.setIndexName(lazyColumnIndex.getIndexName());
                            // 索引类型
                            instance.setFieldIndexType(lazyColumnIndex.isNonUnique() ? LayerField.LayerFieldType.NORMAL : LayerField.LayerFieldType.UNIQUE);
                            return instance;
                        }).collect(Collectors.toList()).toArray(new LazyTableIndexEndpoint[0]);
                        oldFieldLazyTableFieldEndpoint.setLazyTableIndexEndpoints(indexEndpoints);
                        // PRI 主键
                        oldFieldLazyTableFieldEndpoint.setKey(lazyColumnIndexs.stream().anyMatch(lazyColumnIndex -> NormalUsedString.PRIMARY.equals(lazyColumnIndex.getIndexName())));
                    }
                    currentColumnNameList.add(oldFieldLazyTableFieldEndpoint);
                }
                String alterTableSQL = null;
                boolean dropColumn = DDLAuto.PERFECT.equals(ddlAuto);
                alterTableSQL = lazyTableEndpoint.alterTableSQL(currentColumnNameList, dropColumn);
                if (!ObjectUtils.isEmpty(alterTableSQL)) {
                    try {
                        boolean execute = statement.execute(alterTableSQL);
                    } catch (SQLException e) {
                        List<String> alterTableColumnSQL = lazyTableEndpoint.alterTableColumnSQL(currentColumnNameList, dropColumn);
                        for (String alertTableColumn : alterTableColumnSQL) {
                            try {
                                statement.execute(alertTableColumn);
                            } catch (Exception exception) {
                                log.error("执行 单个字段更新 sql: " + alertTableColumn + "\n失败: " + exception);
                            }
                        }
                        throw new SQLException("执行sql 失败: " + alterTableSQL, e);
                    }
                }

            }
            statement.close();
            return true;
        } else {
            log.warn("Ignore creating tables:" + lazyTableEndpoint.getTableName());
            return false;
        }
    }

    /**
     * describe 完善表
     *
     * @param connection        连接对象
     * @param lazyTableEndpoint 实体对象 schema
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 5:41 下午
     **/
    public synchronized boolean perfect(Connection connection, LazyTableEndpoint lazyTableEndpoint) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        return perfect(connection, lazyTableEndpoint, DDLAuto.PERFECT);
    }

}
