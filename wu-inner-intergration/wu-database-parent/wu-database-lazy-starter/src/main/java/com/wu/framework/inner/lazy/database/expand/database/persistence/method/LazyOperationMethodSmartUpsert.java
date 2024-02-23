package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.util.BinHexSwitchUtil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.persistence.analyze.DefaultMySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.SneakyThrows;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 灵活更新 去除null的字段、创建表
 * 插入的对象数据不能时list 空数据过滤问题
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodSmartUpsert extends AbstractLazyOperationMethod {

    private final DefaultMySQLDataProcessAnalyze defaultMySQLDataProcessAnalyze = new DefaultMySQLDataProcessAnalyze();
    private final LazyOperationConfig operationConfig;

    public LazyOperationMethodSmartUpsert(LazyOperationConfig operationConfig) {
        this.operationConfig = operationConfig;
    }


    /**
     * @param param
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {

        // TODO EASYHASHMAP

        //

        Persistence persistence = smartUpsert(param);
        StringBuffer stringBuffer = new StringBuffer(persistence.getExecutionEnum().getExecution());
        stringBuffer.append(persistence.getTableName());
        stringBuffer.append(NormalUsedString.LEFT_BRACKET);
        stringBuffer.append(String.join(",", persistence.getColumnList()));
        stringBuffer.append(") values ( ");
        stringBuffer.append(persistence.getCondition());
        stringBuffer.append(" ) ON DUPLICATE KEY UPDATE ");
        stringBuffer.append(persistence.getColumnList().stream().map(s -> s + " =VALUES (" + s + ")").collect(Collectors.joining(",")));
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
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(Connection connection, Object[] sourceParams) throws Exception {

        Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            // 是否修改
            AtomicBoolean modify = new AtomicBoolean(false);
            for (Object o : objects) {

                if (Collection.class.isAssignableFrom(o.getClass())) {
                    Collection collection = (Collection) o;
                    for (Object item : collection) {
                        if (!modify.get()) {
                            perfect(connection, item.getClass());
                        }
                        modify.set(true);
                        Object generatedKey = accurateExecution(connection, item);
                    }
                    log.warn("使用灵活更新、去除null、创建表 插入的对象数据是时list 消耗性能，建议初始化表后使用upsert方法操作！");
                } else {
                    if (!modify.get()) {
                        perfect(connection, o.getClass());
                    }
                    modify.set(true);
                    Object generatedKey = accurateExecution(connection, o);
                }

            }
        } else {
            perfect(connection, param.getClass());
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

        final ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(param.getClass());
        List<FieldLazyTableFieldEndpoint> lazyTableFieldEndpoints = classLazyTableEndpoint.specifiedFieldAnnotation(LayerField.LayerFieldType.ID);
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(param);
        PreparedStatement preparedStatement;
        if (ObjectUtils.isEmpty(lazyTableFieldEndpoints)) {
            preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        } else {
            // 会创建id自增
            preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString(), Statement.RETURN_GENERATED_KEYS);
        }

        try {
//            if (ObjectUtils.isEmpty(lazyTableFieldEndpoints)) {
//                preparedStatement.execute();
//            }else {
//                // 会创建id自增
//                preparedStatement.executeUpdate();
//            }
            preparedStatement.execute();

            String generatedKey = "0";
            if (!ObjectUtils.isEmpty(lazyTableFieldEndpoints)) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            final List<EasyHashMap> easyHashMaps = resultSetConverter(resultSet, EasyHashMap.class);
                if (resultSet.next()) {
                    generatedKey = resultSet.getString(1);
                }

                // 只会有一个
                FieldLazyTableFieldEndpoint fieldEndpoint = lazyTableFieldEndpoints.get(0);
                Class clazz = fieldEndpoint.getClazz();
                Object id = null;
                if (Integer.class.equals(clazz)) {
                    id = Integer.valueOf(generatedKey);
                } else if (int.class.equals(clazz)) {
                    id = Integer.valueOf(generatedKey);
                } else if (Long.class.equals(clazz)) {
                    id = Long.valueOf(generatedKey);
                } else if (long.class.equals(clazz)) {
                    id = Long.valueOf(generatedKey);
                } else if (String.class.equals(clazz)) {
                    id = generatedKey;
                }
                fieldEndpoint.getField().set(param, id);
            }

            return generatedKey;
        } finally {
            preparedStatement.close();
        }
    }

    /**
     * description 活动插入物已准备好
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/26 3:54 下午
     */
    @SneakyThrows
    public Persistence smartUpsert(Object object) {
        // 数据库列
        List<String> columnList = new ArrayList<>();
        // 列对应值
        List<String> columnValueList = new ArrayList<>();

        // 表名
        final Class<?> aClass = object.getClass();
        String tableName = LazyTableUtil.getTableName(aClass);

        if (EasyHashMap.class.isAssignableFrom(aClass)) {
            EasyHashMap easyHashMap = (EasyHashMap) object;
            if (easyHashMap.isModifyUniqueLabel()) {
                easyHashMap.forEach((key, value) -> {
                    if (!ObjectUtils.isEmpty(value)) {
                        columnList.add(key.toString());
                        final byte[] binary = defaultMySQLDataProcessAnalyze.isBinary(value);
                        if (ObjectUtils.isEmpty(binary)) {
                            columnValueList.add(value.toString());
                        } else {
                            columnValueList.add(BinHexSwitchUtil.bytesToHexSql(binary));
                        }
                    }
                });
            } else {
                throw new IllegalAccessException("自动创建的uniqueLabel 无法被使用为表名 " + easyHashMap.getUniqueLabel());
            }
        } else {
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object o = declaredField.get(object);
                if (o == null) {
                    continue;
                }
                LazyTableField tableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableField.class);
                if (tableField != null && !tableField.exist()) {
                    continue;
                }
                String column = ObjectUtils.isEmpty(tableField) || ObjectUtils.isEmpty(tableField.name()) ?
                        CamelAndUnderLineConverter.humpToLine2(declaredField.getName()) : tableField.name();
                columnList.add(column);

                final byte[] binary = defaultMySQLDataProcessAnalyze.isBinary(o);
                if (ObjectUtils.isEmpty(binary)) {
                    columnValueList.add("'" + o + "'");
                } else {
                    columnValueList.add(BinHexSwitchUtil.bytesToHexSql(binary));
                }

            }

        }
        return new Persistence()
                .setExecutionEnum(Persistence.ExecutionEnum.INSERT)
                .setTableName(tableName)
                .setColumnList(columnList)
                .setCondition(String.join(NormalUsedString.COMMA, columnValueList));
    }

}
