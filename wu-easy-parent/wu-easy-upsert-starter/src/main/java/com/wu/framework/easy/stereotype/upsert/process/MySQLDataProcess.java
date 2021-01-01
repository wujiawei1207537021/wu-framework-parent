package com.wu.framework.easy.stereotype.upsert.process;

import com.wu.framework.easy.stereotype.log.EasyUpsertLog;
import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.entity.UpsertJsonMessage;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.EasyTableAnnotation;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.enums.JavaBasicType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter.annotationConvertConversion;

/**
 * description MySQL 数据预处理
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午2:25
 */
public class MySQLDataProcess {


    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:20
     */
    public EasyTableAnnotation classAnalyze(Class clazz) {
        return LocalStorageClassAnnotation.getEasyTableAnnotation(clazz, true);
    }

    /**
     * @return
     * @params 数据解析
     * @author 吴佳伟
     * @date 2020/12/31 10:56 下午
     **/
    public EasyTableAnnotation dataAnalyze(Class clazz, EasyHashMap easyHashMap) {
        EasyTableAnnotation easyTableAnnotation;
        if (EasyHashMap.class.isAssignableFrom(clazz)) {
            easyTableAnnotation = easyHashMap.toEasyTableAnnotation();
        } else {
            easyTableAnnotation = classAnalyze(clazz);
        }
        return easyTableAnnotation;
    }

    /**
     * description 数据包装
     *
     * @param sourceData 源数据
     * @return ProcessResult
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:23
     */
    public String dataPack(List sourceData, EasyTableAnnotation easyTableAnnotation) throws Exception {
        String insert = "insert into %s (%s) VALUES %s  ON DUPLICATE KEY UPDATE \n %s ";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        // 添加表名
        stringBuilder.append(easyTableAnnotation.getTableName());
        // 添加字段
        List<ConvertedField> convertedFieldList = easyTableAnnotation.getConvertedFieldList();
        String column = convertedFieldList.stream().filter(ConvertedField::isExist).
                filter(convertedField -> !UpsertJsonMessage.ignoredFields.contains(convertedField.getFieldName())).
                map(ConvertedField::getConvertedFieldName).
                collect(Collectors.joining(","));
        String updateColumn = convertedFieldList.stream().filter(ConvertedField::isExist).
                filter(convertedField -> !UpsertJsonMessage.ignoredFields.contains(convertedField.getFieldName())).
                map(convertedField -> convertedField.getConvertedFieldName() + "=VALUES (" + convertedField.getConvertedFieldName() + ")").
                collect(Collectors.joining(","));

        String data;
        // 添加 数据
        if (easyTableAnnotation.getClassName().equals(EasyHashMap.class.getName())) {
            data = ((List<EasyHashMap>) sourceData).stream().
                    map(easyHashMap -> "(" + convertedFieldList.stream().map(convertedField -> "'" + easyHashMap.getOrDefault(convertedField.getFieldName(), JavaBasicType.DEFAULT_VALUE_HASHMAP.get(convertedField.getClazz())) + "'").collect(Collectors.joining(",")) + ")").collect(Collectors.joining(","));
        } else {
            final List<Field> fieldList = convertedFieldList.stream().filter(ConvertedField::isExist).
                    filter(convertedField -> !UpsertJsonMessage.ignoredFields.contains(convertedField.getFieldName())).
                    map(convertedField -> ReflectionUtils.findField(easyTableAnnotation.getClazz(), convertedField.getFieldName())).
                    peek(field -> field.setAccessible(true)).
                    collect(Collectors.toList());
            List<String> tempList = new ArrayList<>();
            for (Object source : sourceData) {
                String temp = "(" +
                        fieldList.stream().map(field -> {
                            Object fieldVal = "";
                            try {
                                fieldVal = field.get(source);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            if (null != fieldVal) {
                                fieldVal = annotationConvertConversion(field, fieldVal, easyTableAnnotation.getIEnumList());
                                fieldVal = fieldVal.toString().replaceAll("'", "\"");
                            } else {
                                fieldVal = JavaBasicType.DEFAULT_VALUE_HASHMAP.get(field.getType());
                            }
                            return "'" + fieldVal + "'";
                        }).collect(Collectors.joining(",")) + ")";
                tempList.add(temp);
            }
            data = String.join(",", tempList);
        }
        return String.format(insert, easyTableAnnotation.getTableName(), column, data, updateColumn);
    }


    /**
     * @return
     * @params 完善表
     * @author 吴佳伟
     * @date 2020/12/31 8:18 下午
     **/
    public int perfectTable(EasyTableAnnotation easyTableAnnotation, DataSource dataSource) throws Exception {
        String tableName = easyTableAnnotation.getTableName();
        Connection connection = null;
        int updateColumn = 0;
        try {
            connection = dataSource.getConnection();
            ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
            //
            String perfectTableSQL;
            if (!resultSet.next()) {
                String createTableSQL = easyTableAnnotation.toTableSQL();
                Statement statement = connection.createStatement();
                for (String s : createTableSQL.split(";")) {
                    statement.addBatch(s);
                }
                //执行建表语句
                int[] executeBatch = statement.executeBatch();
                updateColumn = executeBatch.length;
                perfectTableSQL = createTableSQL;
            } else {
                ResultSet columns = connection.getMetaData().getColumns(null, "%", tableName, "%");
                List<ConvertedField> currentColumnNameList = new ArrayList<>();
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int datasize = columns.getInt("COLUMN_SIZE");
                    int digits = columns.getInt("DECIMAL_DIGITS");
                    int nullable = columns.getInt("NULLABLE");
                    ConvertedField convertedField = new ConvertedField();
                    convertedField.setConvertedFieldName(columnName).setType(columnType);
                    currentColumnNameList.add(convertedField);
//                            System.out.println(columnName + " " + columnType + " " + datasize + " " + digits + " " + nullable);
                }
                String alterTableSQL = easyTableAnnotation.alterTableSQL(currentColumnNameList);
                if (!ObjectUtils.isEmpty(alterTableSQL)) {
                    Statement statement = connection.createStatement();
                    updateColumn = statement.executeUpdate(alterTableSQL);
                }
                perfectTableSQL = alterTableSQL;

            }
            //
//            if (!EasyUpsertLog.class.isAssignableFrom(easyTableAnnotation.getClazz())) {
//                // 记录日志
//                EasyUpsertLog easyUpsertLog = new EasyUpsertLog();
//                easyUpsertLog.setContext(perfectTableSQL);
//                easyUpsertLog.setType(EasyUpsertType.MySQL.name());
//                EasyTableAnnotation logEasyTableAnnotation = LocalStorageClassAnnotation.getEasyTableAnnotation(EasyUpsertLog.class, true);
//                Statement logStatement = connection.createStatement();
//                logStatement.execute(dataPack(Collections.singletonList(easyUpsertLog), logEasyTableAnnotation));
//            }
            return updateColumn;
        } catch (Exception exception) {
            throw exception;
        } finally {
            assert connection != null;
            connection.close();
        }

    }

}
