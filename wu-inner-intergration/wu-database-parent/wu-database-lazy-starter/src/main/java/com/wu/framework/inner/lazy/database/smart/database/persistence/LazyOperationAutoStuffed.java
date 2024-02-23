package com.wu.framework.inner.lazy.database.smart.database.persistence;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.MysqlColumnTypeEnum;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.AutoStuffed;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 */
@Slf4j
public class LazyOperationAutoStuffed implements AutoStuffed {
    public final LazyLambdaStream lazyLambdaStream;
    private final LazyOperation operation;
    private final LazyOperationConfig operationConfig;
    String sqlTemp = "SELECT*FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='%s' AND TABLE_NAME='%s';";


    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 20, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20));

    public LazyOperationAutoStuffed(LazyOperation operation, LazyOperationConfig operationConfig, LazyLambdaStream lazyLambdaStream) {
        this.operation = operation;
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
//                if (Date.class.isAssignableFrom(javaType)) {
//                    // TODO
//                    objectObjectEasyHashMap.put(columnName, LocalDateTime.now());
//                } else
                if (File.class.isAssignableFrom(javaType)) {
                    try {
                        objectObjectEasyHashMap.put(columnName, File.createTempFile("temp", "temp"));
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
            operation.insert(list);
        } catch (Throwable throwable) {
            log.error("当前数据插入失败，请检查当前数据库表结构{}", lazyColumnList);
            throwable.printStackTrace();
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
        final List<LazyColumn> lazyColumnList = operation.executeSQL(sqlTemp, LazyColumn.class, schema, tableName);
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
                threadPoolExecutor.submit(() -> stuffedSimple(table, lazyColumnList, operationConfig.getFillMaximum()));
            }
            //重新启动一个线程 填充数据
            threadPoolExecutor.submit(() -> stuffedSimple(table, lazyColumnList, remainingSize));
        } else {
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
        final ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(tableClass);
        if (operationConfig.getIgnoredDatabase().contains(classLazyTableEndpoint.getSchema())) {
            log.warn("受保护的数据库:{}不会自动填充,更多配置参考配置文件添加 spring.lazy.ignored-database:{} ", classLazyTableEndpoint.getSchema(), operationConfig.getIgnoredDatabase());
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
                    operation.insert(tList);
                });
            }
            //重新启动一个线程 填充数据
            threadPoolExecutor.submit(() -> {
                List<T> tList = new ArrayList<>();
                for (int i = 0; i < remainingSize; i++) {
                    tList.add(DataTransformUntil.simulationBean(tableClass));
                }
                operation.insert(tList);
            });
        } else {
            List<T> tList = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                tList.add(DataTransformUntil.simulationBean(tableClass));
            }
            operation.insert(tList);
        }
    }

    /**
     * describe 根据表明创建出Java文件
     *
     * @param schema 数据库
     * @param table  表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 12:23 上午
     **/
    @Override
    public void stuffedJava(String schema, String table) {
        List<FieldLazyTableFieldEndpoint> fieldLazyTableFieldEndpointList = lazyLambdaStream.of(LazyColumn.class)
                .select(
                        LazyWrappers.<LazyColumn>lambdaWrapper()
                                .eqIgnoreEmpty(LazyColumn::getTableSchema, schema)
                                .eqIgnoreEmpty(LazyColumn::getTableName, table))
                .collection().stream().map(lazyColumn -> {
                    FieldLazyTableFieldEndpoint fieldEndpoint = new FieldLazyTableFieldEndpoint();
                    final String columnName = lazyColumn.getColumnName();
                    fieldEndpoint.setColumnName(columnName);
                    fieldEndpoint.setName(CamelAndUnderLineConverter.lineToHumpField(columnName));
                    fieldEndpoint.setComment(lazyColumn.getColumnComment());
                    fieldEndpoint.setColumnType(lazyColumn.getColumnType());
                    fieldEndpoint.setDataType(lazyColumn.getDataType());
                    return fieldEndpoint;
                }).collect(Collectors.toList());
        ClassLazyTableEndpoint tableEndpoint = new ClassLazyTableEndpoint();
        tableEndpoint.setSchema(schema);
        tableEndpoint.setTableName(table);
        tableEndpoint.setClassName(CamelAndUnderLineConverter.lineToHumpClass(table));
        tableEndpoint.setFieldEndpoints(fieldLazyTableFieldEndpointList);
        tableEndpoint.setPackageName(operationConfig.getReverseEngineering().getPackageName()+NormalUsedString.DOT + "domain");

        LazyTableUtil.createJava(tableEndpoint, operationConfig.getReverseEngineering());
    }

}
