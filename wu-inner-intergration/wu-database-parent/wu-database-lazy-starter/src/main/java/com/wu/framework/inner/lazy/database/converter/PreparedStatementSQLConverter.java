package com.wu.framework.inner.lazy.database.converter;


import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.domain.LayerAnalyzeField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTable;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTableField;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义 生成新增更新或插入 支持字段映射 sql 可以执行的sql 工具
 *
 * @author Jia wei Wu
 * @date 2020/6/23 10:15 上午
 */
public class PreparedStatementSQLConverter {


    /**
     * 转换成可执行的更新或更新的sql
     *
     * @param collection
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String upsertPreparedStatementSQL(Collection collection, Class<T> clazz) {
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        // 添加表名
        String tableName = tableName(clazz);
        stringBuilder.append(tableName).append("(");
        // 添加字段
        List<LayerAnalyzeField> layerAnalyzeFieldList = fieldNamesOnAnnotation(clazz);
//        layerAnalyzeFieldList=layerAnalyzeFieldList.stream().filter(convertedField->!convertedField.getFieldIndexType().equals(LayerField.LayerFieldType.AUTOMATIC)).collect(Collectors.toList());
        for (LayerAnalyzeField layerAnalyzeField : layerAnalyzeFieldList) {
            if (layerAnalyzeFieldList.indexOf(layerAnalyzeField) != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(layerAnalyzeField.getConvertedFieldName());
        }
        stringBuilder.append(") \n");
        stringBuilder.append(" VALUES \n");
        boolean punctuationFlag = false;
        for (Object o : collection) {
            if (punctuationFlag) {
                stringBuilder.append(", \n");
            }
            stringBuilder.append("(");
            for (LayerAnalyzeField layerAnalyzeField : layerAnalyzeFieldList) {
                try {
                    Field field = o.getClass().getDeclaredField(layerAnalyzeField.getFieldName());
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    if (layerAnalyzeFieldList.indexOf(layerAnalyzeField) != 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(" '").append(field.get(o)).append("'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            stringBuilder.append(")");
            punctuationFlag = true;
        }
        // 更新
        stringBuilder.append(" \n ON DUPLICATE KEY UPDATE \n");
        punctuationFlag = false;
        for (LayerAnalyzeField layerAnalyzeField : layerAnalyzeFieldList) {
            if (punctuationFlag) {
                stringBuilder.append(",\n");
            }
            stringBuilder.append(layerAnalyzeField.getConvertedFieldName()).append("=VALUES (").append(layerAnalyzeField.getConvertedFieldName()).append(")");
            punctuationFlag = true;
        }
        System.out.println("执行的sql : " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 获取 表名称
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/3 下午9:48
     **/
    public static <T> String tableName(Class<T> clazz) {
        LazyTable tableNameAnnotation = AnnotationUtils.getAnnotation(clazz, LazyTable.class);
        if (!ObjectUtils.isEmpty(tableNameAnnotation) && !ObjectUtils.isEmpty(tableNameAnnotation.tableName())) {
            if (!ObjectUtils.isEmpty(tableNameAnnotation.schema())) {
                return tableNameAnnotation.schema() + "." + tableNameAnnotation.tableName();
            }
            return tableNameAnnotation.tableName();
        } else {
            return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
        }
    }

    @Deprecated
    public static <T> List<String> fieldNames(Class<T> clazz) {
        List<String> fieldNames = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            LazyTableField tableField = AnnotationUtils.getAnnotation(declaredField, LazyTableField.class);
            String fieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            if (!ObjectUtils.isEmpty(tableField)) {
                if (!tableField.exist()) {
                    continue;
                }
                if (!ObjectUtils.isEmpty(tableField.value())) {
                    fieldName = tableField.value();
                }
            }
            fieldNames.add(fieldName);
        }
        return fieldNames;
    }

    /**
     * description 获取指定字段索引类型的字段
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/7/8 下午2:04
     */
    public static <T> List<LayerAnalyzeField> fieldNamesOnAnnotation(Class<T> clazz, LayerField.LayerFieldType tableFileIndexType) {
        List<LayerAnalyzeField> layerAnalyzeFieldList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            LazyTableField tableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableField.class);
            String convertedFieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            if (!ObjectUtils.isEmpty(tableField)) {
                if (!tableField.exist()) {
                    continue;
                }
                // 判断是否是我想要的类型
                if (!ObjectUtils.isEmpty(tableFileIndexType) && !tableFileIndexType.equals(tableField.indexType())) {
                    continue;
                }
                if (!ObjectUtils.isEmpty(tableField.value())) {
                    convertedFieldName = tableField.value();
                }
            }
            LayerAnalyzeField layerAnalyzeField = new LayerAnalyzeField();
            layerAnalyzeField.setConvertedFieldName(convertedFieldName);
            layerAnalyzeField.setFieldName(declaredField.getName());
            layerAnalyzeField.setClazz(declaredField.getType());
            if (!ObjectUtils.isEmpty(tableField)) {
                layerAnalyzeField.setFieldIndexType(tableField.indexType());
            }
            layerAnalyzeFieldList.add(layerAnalyzeField);
        }
        return layerAnalyzeFieldList;
    }

    public static <T> List<LayerAnalyzeField> fieldNamesOnAnnotation(Class<T> clazz) {
        return fieldNamesOnAnnotation(clazz, null);
    }





    /**
     * 查询生sql
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午4:00
     **/
    public static <T> String selectPreparedStatementSQL(Object o) {
        Class clazz = o.getClass();
        //  SELECT FROM
        StringBuffer stringBuffer = new StringBuffer(" SELECT * FROM  ");
        stringBuffer.append(tableName(clazz));
        // where
        stringBuffer.append(" where ");
        boolean punctuationFlag = false;
        List<LayerAnalyzeField> layerAnalyzeFieldList = fieldNamesOnAnnotation(clazz);
        for (LayerAnalyzeField layerAnalyzeField : layerAnalyzeFieldList) {
            try {
                Field field = o.getClass().getDeclaredField(layerAnalyzeField.getFieldName());
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
                stringBuffer.append(layerAnalyzeField.getConvertedFieldName()).append(" = '").append(fieldVal).append("'");
                punctuationFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

//    public static void main(String[] args) {
//        var dataBaseUser = new DataBaseUser(0, "1", "2020-07-04", "3", "4", 5);
//        String ss = insertPreparedStatementSQL(Arrays.asList(dataBaseUser), DataBaseUser.class);
//        System.out.println(ss);
//        for (Field declaredField : DataBaseUser.class.getDeclaredFields()) {
//            Annotation annotation = AnnotationUtils.getAnnotation(declaredField, EasySmartField.class);
//            Annotation[] declaredAnnotationsByType = declaredField.getDeclaredAnnotationsByType(Annotation.class);
//            if (annotation != null) {
//                System.out.println("shide");
//            }
//        }
//    }

    public static String activeInsertPreparedStatementSQL(Object object) {
        Persistence persistence = PersistenceConverter.activeInsertPrepared(object);
        StringBuffer stringBuffer = new StringBuffer(persistence.getExecutionEnum().getExecution());
        stringBuffer.append(persistence.getTableName());
        stringBuffer.append(NormalUsedString.LEFT_BRACKET);
        stringBuffer.append(String.join(",", persistence.getColumnList()));
        stringBuffer.append(") values ( ");
        stringBuffer.append(persistence.getCondition());
        stringBuffer.append(" ) ON DUPLICATE KEY UPDATE ");
        stringBuffer.append(persistence.getColumnList().stream().map(s -> s + " =VALUES (" + s + ")").collect(Collectors.joining(",")));
        return stringBuffer.toString();
    }

    /**
     * 分页查询sql
     *
     * @param object
     * @return
     * @author Jiawei Wu
     * @date 2021/1/24 6:48 下午
     **/
    public static String pagePreparedStatementSQL(Object object) {
        return null;
    }


    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return this.getClass().getAnnotation(annotationType);
    }

}
