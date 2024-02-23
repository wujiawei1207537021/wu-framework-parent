package com.wu.framework.inner.lazy.database.smart.database.persistence;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyColumnIndex;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperationAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.factory.LazySmartLazyOperationFactory;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.field.AbstractLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.index.AbstractLazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * describe : 自动填充数据
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/11/14 7:13 下午
 * @see LazySmartLazyOperationFactory
 */
@Slf4j
public class LazyOperationSmartAutoStuffed implements SmartLazyOperationAutoStuffed {
    public final LazyLambdaStream lazyLambdaStream;
    private final LazyOperationConfig operationConfig;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 20, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20),
            // 线程满时使用当前线程执行任务
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public LazyOperationSmartAutoStuffed(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.operationConfig = new LazyOperationConfig();
    }

    public LazyOperationSmartAutoStuffed(LazyOperationConfig operationConfig, LazyLambdaStream lazyLambdaStream) {
        this.operationConfig = operationConfig;
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe 简单的数据插入
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/16 7:36 下午
     **/
    private void stuffedSimple(LazyTableInfo table, List<LazyColumn> lazyColumnList, Long num) {

        Assert.notNull(num, "'num' must not be null ");
        if (operationConfig.getIgnoredDatabase().contains(table.getTableSchema())) {
            log.warn("受保护的数据库:{}不会自动填充,更多配置参考配置文件添加 spring.lazy.ignored-database:{} ", table.getTableSchema(), operationConfig.getIgnoredDatabase());
            return;
        }

        final String schema = table.getTableSchema();
        final String tableName = table.getTableName();
        if (ObjectUtils.isEmpty(lazyColumnList)) {
            return;
        }

        List<EasyHashMap> list = new ArrayList<>();
        // 模拟数据
        for (int i = 0; i <= num; i++) {
            final EasyHashMap<Object, Object> objectObjectEasyHashMap = new EasyHashMap<>(schema + NormalUsedString.DOT + tableName);
            objectObjectEasyHashMap.setKeyAdjust(false);
            for (LazyColumn lazyColumn : lazyColumnList) {
                final String columnName = lazyColumn.getColumnName();
                Long characterMaximumLength = lazyColumn.getCharacterMaximumLength();
                final String extra = lazyColumn.getExtra();
                final String isNullable = lazyColumn.getIsNullable();
                // 主键自增忽略 auto_increment
                if ("auto_increment".equals(extra)) {
                    continue;
                }
                // 填充忽略的字段&允许null
                if (operationConfig.getFillIgnoredFields().contains(columnName) && "YES".equals(isNullable)) {
                    continue;
                }
                final String columnDataType = lazyColumn.getDataType();
                final MysqlColumnTypeEnum mysqlColumnTypeEnum = MysqlColumnTypeEnum.MYSQL_COLUMN_TYPE_ENUM_MAP.get(columnDataType);
                if (null == mysqlColumnTypeEnum) {
                    log.warn("自动填充数据schema:{} tableName:{} 无法找到类型{}，当前字段{}数据填充为null", schema, tableName, columnDataType, columnName);
                    continue;
                }

                final Class javaType = mysqlColumnTypeEnum.getJavaType();

//                 日期数据处理
                //序号	名称	描述	定义方式	格式	范围
                //1	date	日期	date	YYYY-MM-DD	'1000-01-01' to '9999-12-31'
                //2	time	时间	time[.fraction]	hh:mm:ss[.000000]	'-838:59:59.000000' to '838:59:59.000000'
                //3	datetime	日期+时间	datetime[.fraction]	YYYY-MM-DD hh:mm:ss[.000000]	'1000-01-01 00:00:00' to '9999-12-31 23:59:59'
                //4	timestamp	时间戳	timestamp[.fraction]	YYYY-MM-DD hh:mm:ss[.000000]	'1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC
                //5	year	年	year	YYYY	'1901' to '2155'
                if (LocalDateTime.class.isAssignableFrom(javaType)) {
//                    objectObjectEasyHashMap.put(columnName, "2022-12-12 00:00:00");
                    objectObjectEasyHashMap.put(columnName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
                } else if (Date.class.isAssignableFrom(javaType)) {
                    objectObjectEasyHashMap.put(columnName, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
                } else if (Date.class.isAssignableFrom(javaType)) {
                    objectObjectEasyHashMap.put(columnName, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
                } else if (File.class.isAssignableFrom(javaType)) {
                    try {
                        objectObjectEasyHashMap.put(columnName, File.createTempFile("smart-auto-stuffed-temp", "temp"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 字段指定的长度 characterMaximumLength
//                    varchar、char使用设置的长度  其他字段使用MysqlColumnTypeEnum默认长度
                    if (columnDataType.contains("varchar") || columnDataType.contains("char")) {
                        objectObjectEasyHashMap.put(columnName,
                                DataTransformUntil.simulationBaseBean(javaType, Math.toIntExact(characterMaximumLength))
                        );
                    } else {
                        objectObjectEasyHashMap.put(columnName,
                                DataTransformUntil.simulationBaseBean(javaType, mysqlColumnTypeEnum.getBound())
                        );
                    }
                    // decimal(3,2) 三个数字，保留两位小数 TODO
                    if (columnDataType.contains("decimal")) {
                        objectObjectEasyHashMap.put(columnName, 0);
                    }
                }
            }
            list.add(objectObjectEasyHashMap);
        }

        // 过滤唯一性索引问题

        // 插入数据
        try {
            lazyLambdaStream.insert(list);
        } catch (Throwable throwable) {
            log.error("当前数据插入失败，请检查当前数据库表结构{}", lazyColumnList);
            throw throwable;
        }
    }

    /**
     * SELECT
     * *
     * FROM
     * information_schema.COLUMNS
     * WHERE
     * TABLE_SCHEMA = 'lazy'
     * AND TABLE_NAME = 'sys_user';
     *
     * @param table 表
     * @param num   数量
     */
    @Override
    public void stuffed(LazyTableInfo table, Long num) {
        Assert.notNull(num, "'num' must not be null ");
        if (operationConfig.getIgnoredDatabase().contains(table.getTableSchema())) {
            log.warn("受保护的数据库:{}不会自动填充,更多配置参考配置文件添加 spring.lazy.ignored-database:{} ", table.getTableSchema(), operationConfig.getIgnoredDatabase());
            return;
        }

        final String schema = table.getTableSchema();
        final String tableName = table.getTableName();
        //查询数据库表结构
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        final List<LazyColumn> lazyColumnList = new ArrayList<>(lazyLambdaStream.selectList(LazyWrappers.<LazyColumn>lambdaWrapper()
                .eq(LazyColumn::getTableSchema, schema)
                .eq(LazyColumn::getTableName, tableName)));
        if (ObjectUtils.isEmpty(lazyColumnList)) {
            return;
        }

        // 多线程处理
        if (num > operationConfig.getFillMaximum()) {
            // 次数
            final long time = num / operationConfig.getFillMaximum();
            //  剩余大小
            final long remainingSize = num % operationConfig.getFillMaximum();
            for (int i = 0; i < time; i++) {
                //重新启动一个线程 填充数据
                log.info("执行次数:{},添加的数据库:{},表:{}", i, schema, tableName);
                threadPoolExecutor.submit(() -> {
                    DynamicLazyDSContextHolder.push(peek);
                    stuffedSimple(table, lazyColumnList, operationConfig.getFillMaximum());
                });
            }
            //重新启动一个线程 填充数据
            threadPoolExecutor.submit(() -> {
                DynamicLazyDSContextHolder.push(peek);
                stuffedSimple(table, lazyColumnList, remainingSize);
            });
        } else {
            DynamicLazyDSContextHolder.push(peek);
            stuffedSimple(table, lazyColumnList, num);
        }
    }

    /**
     * describe 根据class填充数据
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/19 9:28 下午
     **/
    @Override
    public <T> void stuffed(Class<T> tableClass, Long num) {
        Assert.notNull(num, "'num' must not be null ");
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(tableClass);
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(tableClass);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
        if (operationConfig.getIgnoredDatabase().contains(lazyTableEndpoint.getSchema())) {
            log.warn("受保护的数据库:{}不会自动填充,更多配置参考配置文件添加 spring.lazy.ignored-database:{} ", lazyTableEndpoint.getSchema(), operationConfig.getIgnoredDatabase());
            return;
        }

        // 多线程处理
        if (num > operationConfig.getFillMaximum()) {
            // 次数
            final long time = num / operationConfig.getFillMaximum();
            //  剩余大小
            final long remainingSize = num % operationConfig.getFillMaximum();
            for (int i = 0; i < time; i++) {
                //重新启动一个线程 填充数据
                threadPoolExecutor.submit(() -> {
                    List<T> tList = new ArrayList<>();
                    for (int j = 0; j < operationConfig.getFillMaximum(); j++) {
                        tList.add(DataTransformUntil.simulationBean(tableClass));
                    }
                    lazyLambdaStream.insert(tList);
                });
            }
            //重新启动一个线程 填充数据
            threadPoolExecutor.submit(() -> {
                List<T> tList = new ArrayList<>();
                for (int i = 0; i < remainingSize; i++) {
                    tList.add(DataTransformUntil.simulationBean(tableClass));
                }
                lazyLambdaStream.insert(tList);
            });
        } else {
            List<T> tList = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                tList.add(DataTransformUntil.simulationBean(tableClass));
            }
            lazyLambdaStream.insert(tList);
        }
    }


    /**
     * describe 根据表明创建出Java文件
     *
     * @param schema    数据库
     * @param tableName 表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 12:23 上午
     **/
    @Override
    public void stuffedJava(String schema, String tableName) {
        LazyTableInfo lazyTableInfo = lazyLambdaStream.
                selectOne(LazyWrappers.<LazyTableInfo>lambdaWrapper().
                        eqIgnoreEmpty(LazyTableInfo::getTableSchema, schema).
                        eqIgnoreEmpty(LazyTableInfo::getTableName, tableName));

        // 查询索引
        List<LazyColumnIndex> lazyColumnIndexList = lazyLambdaStream.selectList(
                LazyWrappers.<LazyColumnIndex>lambdaWrapper().eq(LazyColumnIndex::getTableSchema, schema)
                        .eq(LazyColumnIndex::getTableName, tableName)
        );

        // 字段索引降维度
        Map<String, List<LazyColumnIndex>> columnIndexMap = lazyColumnIndexList.stream().
                collect(Collectors.groupingBy(LazyColumnIndex::getColumnName));

        List<LazyTableFieldEndpoint> fieldLazyTableFieldEndpointList = lazyLambdaStream
                .selectList(
                        LazyWrappers.<LazyColumn>lambdaWrapper()
                                .eqIgnoreEmpty(LazyColumn::getTableSchema, schema)
                                .eqIgnoreEmpty(LazyColumn::getTableName, tableName))
                .stream().map(lazyColumn -> {
                    LazyTableFieldEndpoint fieldEndpoint = AbstractLazyTableFieldEndpoint.getInstance();
                    final String columnName = lazyColumn.getColumnName();
                    fieldEndpoint.setColumnName(columnName);
                    fieldEndpoint.setName(CamelAndUnderLineConverter.lineToHumpField(columnName));
                    fieldEndpoint.setComment(lazyColumn.getColumnComment());
                    fieldEndpoint.setColumnType(lazyColumn.getColumnType());
                    fieldEndpoint.setDataType(lazyColumn.getDataType());
                    fieldEndpoint.setExtra(lazyColumn.getExtra());
                    fieldEndpoint.setDefaultValue(lazyColumn.getColumnDefault());
                    fieldEndpoint.setNotNull(!NormalUsedString.YES.equalsIgnoreCase(lazyColumn.getIsNullable()));
                    fieldEndpoint.setScale(lazyColumn.getNumericScale());
                    // 存在索引
                    if (columnIndexMap.containsKey(lazyColumn.getColumnName())) {
                        List<LazyColumnIndex> lazyColumnIndexs = columnIndexMap.get(lazyColumn.getColumnName());

                        LazyTableIndexEndpoint[] indexEndpoints = lazyColumnIndexs.stream().filter(lazyColumnIndex -> !"PRIMARY".equals(lazyColumnIndex.getIndexName())).map(lazyColumnIndex -> {
                            AbstractLazyTableIndexEndpoint instance = AbstractLazyTableIndexEndpoint.getInstance();
                            // 索引名称
                            instance.setIndexName(lazyColumnIndex.getIndexName());
                            // 索引类型
                            instance.setFieldIndexType(lazyColumnIndex.isNonUnique() ?
                                    LayerField.LayerFieldType.NORMAL : LayerField.LayerFieldType.UNIQUE);
                            return instance;
                        }).toList().toArray(new LazyTableIndexEndpoint[0]);
                        fieldEndpoint.setLazyTableIndexEndpoints(indexEndpoints);
                        // PRI 主键
                        fieldEndpoint.setKey(lazyColumnIndexs.stream().anyMatch(lazyColumnIndex -> NormalUsedString.PRIMARY.equals(lazyColumnIndex.getIndexName())));
                    }
                    return fieldEndpoint;
                }).sorted((fieldLazyTableFieldEndpoint1, fieldLazyTableFieldEndpoint2)
                        -> Collator.getInstance(Locale.CHINA)
                        .compare(fieldLazyTableFieldEndpoint1.getColumnName(), fieldLazyTableFieldEndpoint2.getColumnName()))
                .collect(Collectors.toList());
        ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint = new ReverseClassLazyTableEndpoint();
        reverseClassLazyTableEndpoint.setSchema(schema);
        reverseClassLazyTableEndpoint.setTableName(tableName);
        reverseClassLazyTableEndpoint.setClassName(CamelAndUnderLineConverter.lineToHumpClass(tableName));
        reverseClassLazyTableEndpoint.setInLazyTableFieldEndpoints(fieldLazyTableFieldEndpointList);
        reverseClassLazyTableEndpoint.setOutLazyTableFieldEndpoints(fieldLazyTableFieldEndpointList);
        reverseClassLazyTableEndpoint.setComment(lazyTableInfo.getTableComment());
        reverseClassLazyTableEndpoint.setPackageName(operationConfig.getReverseEngineering().getPackageName() + NormalUsedString.DOT + "domain");

        String entitySuffix = operationConfig.getReverseEngineering().getEntitySuffix();
        if (!ObjectUtils.isEmpty(entitySuffix)) {
            String className = reverseClassLazyTableEndpoint.getClassName();
            reverseClassLazyTableEndpoint.setClassName(className + entitySuffix);
        }
        LazyTableUtil.createJava(reverseClassLazyTableEndpoint, operationConfig.getReverseEngineering());
    }

}
