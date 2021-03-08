package com.wu.framework.inner.lazy.database.converter;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.enums.NormalUsedString;
import com.wu.framework.inner.lazy.database.domain.ConvertedField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.test.pojo.DataBaseUser;
import lombok.var;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(clazz);
//        convertedFieldList=convertedFieldList.stream().filter(convertedField->!convertedField.getFieldIndexType().equals(EasySmartField.TableFileIndexType.AUTOMATIC)).collect(Collectors.toList());
        for (ConvertedField convertedField : convertedFieldList) {
            if (convertedFieldList.indexOf(convertedField) != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(convertedField.getConvertedFieldName());
        }
        stringBuilder.append(") \n");
        stringBuilder.append(" VALUES \n");
        boolean punctuationFlag = false;
        for (Object o : collection) {
            if (punctuationFlag) {
                stringBuilder.append(", \n");
            }
            stringBuilder.append("(");
            for (ConvertedField convertedField : convertedFieldList) {
                try {
                    Field field = o.getClass().getDeclaredField(convertedField.getFieldName());
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    if (convertedFieldList.indexOf(convertedField) != 0) {
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
        for (ConvertedField convertedField : convertedFieldList) {
            if (punctuationFlag) {
                stringBuilder.append(",\n");
            }
            stringBuilder.append(convertedField.getConvertedFieldName()).append("=VALUES (").append(convertedField.getConvertedFieldName()).append(")");
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
        EasySmart tableNameAnnotation = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
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
            EasySmartField EasySmartField = AnnotationUtils.getAnnotation(declaredField, EasySmartField.class);
            String fieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            if (!ObjectUtils.isEmpty(EasySmartField)) {
                if (!EasySmartField.exist()) {
                    continue;
                }
                if (!ObjectUtils.isEmpty(EasySmartField.value())) {
                    fieldName = EasySmartField.value();
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
    public static <T> List<ConvertedField> fieldNamesOnAnnotation(Class<T> clazz, EasySmartField.TableFileIndexType tableFileIndexType) {
        List<ConvertedField> convertedFieldList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            EasySmartField EasySmartField = AnnotatedElementUtils.findMergedAnnotation(declaredField, EasySmartField.class);
            String convertedFieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            if (!ObjectUtils.isEmpty(EasySmartField)) {
                if (!EasySmartField.exist()) {
                    continue;
                }
                // 判断是否是我想要的类型
                if (!ObjectUtils.isEmpty(tableFileIndexType) && !tableFileIndexType.equals(EasySmartField.indexType())) {
                    continue;
                }
                if (!ObjectUtils.isEmpty(EasySmartField.value())) {
                    convertedFieldName = EasySmartField.value();
                }
            }
            ConvertedField convertedField = new ConvertedField();
            convertedField.setConvertedFieldName(convertedFieldName);
            convertedField.setFieldName(declaredField.getName());
            convertedField.setClazz(declaredField.getType());
            if (!ObjectUtils.isEmpty(EasySmartField)) {
                convertedField.setFieldIndexType(EasySmartField.indexType());
            }
            convertedFieldList.add(convertedField);
        }
        return convertedFieldList;
    }

    public static <T> List<ConvertedField> fieldNamesOnAnnotation(Class<T> clazz) {
        return fieldNamesOnAnnotation(clazz, null);
    }

    /**
     * 更新的sql
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午2:11
     **/
    public static String updatePreparedStatementSQL(Object o) {
        // update
        StringBuffer stringBuffer = new StringBuffer("update ");
        // table
        stringBuffer.append(tableName(o.getClass())).append(" set ");
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(o.getClass());
        boolean punctuationFlag = false;
        boolean andFlag = false;
        StringBuffer stringBufferWhere = new StringBuffer(" where ");
        for (ConvertedField convertedField : convertedFieldList) {
            try {

                Field field = o.getClass().getDeclaredField(convertedField.getFieldName());
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object fieldVal = field.get(o);
                if (convertedField.getFieldIndexType().equals(EasySmartField.TableFileIndexType.FILE_TYPE)) {
                    if (punctuationFlag) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(convertedField.getConvertedFieldName()).append(" = '").append(fieldVal).append("'");
                    punctuationFlag = true;
                } else {
                    if (ObjectUtils.isEmpty(fieldVal)) {
                        continue;
                    }
                    if (andFlag) {
                        stringBufferWhere.append(" and ");
                    }
                    stringBufferWhere.append(convertedField.getConvertedFieldName()).append(" = '").append(fieldVal).append("'");
                    andFlag = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // where
        stringBuffer.append(stringBufferWhere);
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();

    }

    /**
     * 删除sql
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午4:00
     **/
    public static <T> String deletePreparedStatementSQL(Object o) {
        Class clazz = o.getClass();
        // DELETE FROM
        StringBuffer stringBuffer = new StringBuffer(" DELETE FROM  ");
        stringBuffer.append(tableName(clazz));
        // where
        stringBuffer.append(" where ");
        boolean punctuationFlag = false;
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(clazz, EasySmartField.TableFileIndexType.ID);

        for (ConvertedField convertedField : convertedFieldList) {
            try {
                Field field = o.getClass().getDeclaredField(convertedField.getFieldName());
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
                stringBuffer.append(convertedField.getConvertedFieldName()).append(" = '").append(fieldVal).append("'");
                punctuationFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return stringBuffer.toString();
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
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(clazz);
        for (ConvertedField convertedField : convertedFieldList) {
            try {
                Field field = o.getClass().getDeclaredField(convertedField.getFieldName());
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
                stringBuffer.append(convertedField.getConvertedFieldName()).append(" = '").append(fieldVal).append("'");
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
