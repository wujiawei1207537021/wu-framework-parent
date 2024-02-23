package com.wu.framework.inner.lazy.persistence.analyze;

import com.wu.framework.inner.layer.data.IBeanUpsert;
import com.wu.framework.inner.layer.data.JavaBasicTypeDefaultValue;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.LayerDefault;
import com.wu.framework.inner.layer.util.BinHexSwitchUtil;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.core.io.InputStreamSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * description MySQL 数据预处理解析
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午2:25
 */
public interface MySQLDataProcessAnalyze extends LayerDefault, SQLAnalyze {


    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:20
     */
    default ClassLazyTableEndpoint classAnalyze(Class clazz) {
        return LazyTableUtil.analyzeLazyTable(clazz);
    }


    /**
     * @return
     * @params 数据解析
     * @author Jiawei Wu
     * @date 2020/12/31 10:56 下午
     **/
    default ClassLazyTableEndpoint dataAnalyze(Class clazz, EasyHashMap easyHashMap) {
        ClassLazyTableEndpoint lazyTableAnnotation;
        if (EasyHashMap.class.isAssignableFrom(clazz)) {
            lazyTableAnnotation = easyHashMap.toEasyTableAnnotation(false, true);
        } else {
            lazyTableAnnotation = classAnalyze(clazz);
        }
        return lazyTableAnnotation;
    }

    /**
     * description upsert数据包装
     * INSERT INTO TEMP_EASY_HASH (DATE,NAME,TYPE) VALUES ('2021-04-18','MAP','EASY')  ON DUPLICATE KEY UPDATE
     * DATE=VALUES (DATE),NAME=VALUES (NAME),TYPE=VALUES (TYPE)
     *
     * @param sourceData 源数据
     * @return ProcessResult
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:23
     */
    default MySQLProcessResult upsertDataPack(List sourceData, ClassLazyTableEndpoint tableEndpoint) throws ProcessException {
        MySQLProcessResult mySQLProcessResult = new MySQLProcessResult();
        String insert = "insert into %s (%s) VALUES %s  ON DUPLICATE KEY UPDATE \n %s ";

        // 添加字段
        List<FieldLazyTableFieldEndpoint> convertedFieldList = tableEndpoint.getFieldEndpoints();
        String column = convertedFieldList.stream().filter(LazyTableFieldEndpoint::isExist).
                filter(convertedField -> !LazyDatabaseJsonMessage.ignoredFields.contains(convertedField.getName())).
                map(LazyTableFieldEndpoint::getColumnName).
                collect(Collectors.joining(NormalUsedString.COMMA));
        String updateColumn = convertedFieldList.stream().filter(LazyTableFieldEndpoint::isExist).
                filter(convertedField -> !LazyDatabaseJsonMessage.ignoredFields.contains(convertedField.getName())).
                map(convertedField -> convertedField.getColumnName() + "=VALUES (" + convertedField.getColumnName() + ")").
                collect(Collectors.joining(NormalUsedString.COMMA));

        String data;
        // 添加 数据
        if (tableEndpoint.getClassName().equals(EasyHashMap.class.getName())) {
            final List<EasyHashMap> easyHashMaps = sourceData;
            data = easyHashMaps.stream().
                    map(
                            easyHashMap -> NormalUsedString.LEFT_BRACKET + convertedFieldList.stream().map(convertedField -> {
                                try {
                                    easyHashMap.beforeObjectProcess();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Object value = easyHashMap.getOrDefault(convertedField.getName(), null);
                                //判断是否为binary数据
                                final byte[] binary = isBinary(value);
                                if (!ObjectUtils.isEmpty(binary)) {
                                    return BinHexSwitchUtil.bytesToHexSql(binary);
                                }

                                if (value == null && !JavaBasicTypeDefaultValue.DEFAULT_VALUE_HASHMAP.containsKey(convertedField.getClazz())) {
                                    throw new RuntimeException("current  data is null and we could not find the default value of columnType " + convertedField.getClazz());
                                }
                                if (null == value) {
                                    if (JavaBasicTypeDefaultValue.DEFAULT_VALUE_HASHMAP.get(convertedField.getClazz()) == null) {
                                        return null;
                                    } else {
                                        value = JavaBasicTypeDefaultValue.DEFAULT_VALUE_HASHMAP.get(convertedField.getClazz());
                                    }
                                }
                                return "'" + value.toString().replaceAll("'", "’") + "'";
                            }).collect(Collectors.joining(NormalUsedString.COMMA)) + NormalUsedString.RIGHT_BRACKET
                    ).collect(Collectors.joining(NormalUsedString.COMMA));
        } else {
            final List<Field> fieldList = convertedFieldList.stream().filter(LazyTableFieldEndpoint::isExist).
                    filter(convertedField -> !LazyDatabaseJsonMessage.ignoredFields.contains(convertedField.getName())).
                    map(convertedField -> ReflectionUtils.findField(tableEndpoint.getClazz(), convertedField.getName())).filter(Objects::nonNull).
                    peek(field -> field.setAccessible(true)).
                    collect(Collectors.toList());
            List<String> tempList = new ArrayList<>();
            for (Object source : sourceData) {
                if (IBeanUpsert.class.isAssignableFrom(tableEndpoint.getClazz())) {
                    ((IBeanUpsert) source).beforeObjectProcess();
                }
                String temp = NormalUsedString.LEFT_BRACKET +
                        fieldList.stream().map(field -> {
                            Object fieldVal = "";
                            try {
                                fieldVal = field.get(source);
                                //判断是否为binary数据
                                final byte[] binary = isBinary(fieldVal);
                                if (!ObjectUtils.isEmpty(binary)) {
                                    return BinHexSwitchUtil.bytesToHexSql(binary);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            if (null != fieldVal) {
                                fieldVal = EasyAnnotationConverter.annotationConvertConversion(field, fieldVal, tableEndpoint.getIEnumList());
                                return "'" + fieldVal.toString().replaceAll("'", "\"") + "'";
                            } else {
//                                fieldVal = JavaBasicTypeDefaultValue.DEFAULT_VALUE_HASHMAP.get(field.getType());
                                return null;
                            }
                        }).collect(Collectors.joining(NormalUsedString.COMMA)) + NormalUsedString.RIGHT_BRACKET;
                tempList.add(temp);
            }
            data = String.join(NormalUsedString.COMMA, tempList);
        }

        String sql = String.format(insert, tableEndpoint.getFullTableName(), column, data, updateColumn);
        mySQLProcessResult.setSql(sql);
        return mySQLProcessResult;
    }

    /**
     * 完善表
     *
     * @param connection    数据源头
     * @param tableEndpoint 注解
     * @return int
     * @author Jiawei Wu
     * @date 2020/12/31 8:18 下午
     **/
    default int perfectTable(ClassLazyTableEndpoint tableEndpoint, Connection connection) throws Exception {
        String tableName = tableEndpoint.getTableName();
        int updateColumn = 0;
        try {

            DatabaseMetaData metaData = connection.getMetaData();
            // 添加 schema
            tableEndpoint.setSchema(
                    ObjectUtils.isEmpty(tableEndpoint.getSchema()) ? connection.getCatalog() : tableEndpoint.getSchema());

//            String catalog = dataSource.getConnection().getCatalog();

            ResultSet resultSet = metaData.getTables(tableEndpoint.getSchema(), null, tableName, new String[]{"TABLE"});
            //
            String perfectTableSQL;
//            while(resultSet.next()){
//                String tableName1 = resultSet.getString("TABLE_NAME");
//                System.out.println("tablename:"+tableName1);
//            }

            if (!resultSet.next()) {
                String createTableSQL = tableEndpoint.creatTableSQL();
                Statement statement = connection.createStatement();
                for (String sql : createTableSQL.split(NormalUsedString.SEMICOLON)) {
                    statement.execute(sql);
                }
                //执行建表语句
                statement.close();
                log.info("create table {} success", tableName);
            } else {
                String string = resultSet.getString(1);
                ResultSet columns = metaData.getColumns(tableEndpoint.getSchema(), "%", tableName, "%");
                List<FieldLazyTableFieldEndpoint> currentColumnNameList = new ArrayList<>();
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int dataSize = columns.getInt("COLUMN_SIZE");
                    int digits = columns.getInt("DECIMAL_DIGITS");
                    int nullable = columns.getInt("NULLABLE");
                    FieldLazyTableFieldEndpoint convertedField = new FieldLazyTableFieldEndpoint();
                    convertedField.setColumnName(columnName);
                    convertedField.setColumnType(columnType);
                    currentColumnNameList.add(convertedField);
//                            System.out.println(columnName + " " + columnType + " " + datasize + " " + digits + " " + nullable);
                }
                String alterTableSQL = tableEndpoint.alterTableSQL(currentColumnNameList);
                if (!ObjectUtils.isEmpty(alterTableSQL)) {
                    Statement statement = connection.createStatement();
                    updateColumn = statement.executeUpdate(alterTableSQL);
                    statement.close();
                }
                perfectTableSQL = alterTableSQL;

            }
            //
//            if (!EasyUpsertLog.class.isAssignableFrom(tableEndpoint.getClazz())) {
//                // 记录日志
//                EasyUpsertLog easyUpsertLog = new EasyUpsertLog();
//                easyUpsertLog.setContext(perfectTableSQL);
//                easyUpsertLog.setType(EasyUpsertType.MySQL.name());
//                LazyTableAnnotation logEasyTableAnnotation = LocalStorageClassAnnotation.getEasyTableAnnotation(EasyUpsertLog.class, true);
//                Statement logStatement = connection.createStatement();
//                logStatement.execute(upsertDataPack(Collections.singletonList(easyUpsertLog), logEasyTableAnnotation));
//            }
            return updateColumn;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            assert connection != null;

        }

    }

    /**
     * @param
     * @return describe 字段是否为二进制数据
     * @author Jia wei Wu
     * @date 2021/3/1 7:07 下午
     **/
    @SneakyThrows
    default byte[] isBinary(Object fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        if (File.class.equals(fieldValue.getClass())) {
            final FileInputStream fileInputStream = new FileInputStream((File) fieldValue);
            return BinHexSwitchUtil.readBytes(fileInputStream);
        } else if (InputStream.class.isAssignableFrom(fieldValue.getClass())) {
            return BinHexSwitchUtil.readBytes((InputStream) fieldValue);
        } else if (InputStreamSource.class.isAssignableFrom(fieldValue.getClass())) {
            return BinHexSwitchUtil.readBytes(((InputStreamSource) fieldValue).getInputStream());
        } else if (Byte[].class.isAssignableFrom(fieldValue.getClass())) {
            return (byte[]) fieldValue;
        } else if (byte[].class.isAssignableFrom(fieldValue.getClass())) {
            return (byte[]) fieldValue;
        }
        return null;
    }


    @Accessors(chain = true)
    @Data
    class MySQLProcessResult implements DataProcess.ProcessResult {
        private String sql;
        private List<InputStream> binaryList;

        private boolean hasBinary = false;

    }
}
