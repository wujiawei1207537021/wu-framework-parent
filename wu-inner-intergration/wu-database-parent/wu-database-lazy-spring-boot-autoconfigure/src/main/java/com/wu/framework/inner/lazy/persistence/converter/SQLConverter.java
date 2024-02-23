package com.wu.framework.inner.lazy.persistence.converter;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.analyze.EasyAnnotationConverter;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/4/18 11:21 上午
 */
public class SQLConverter {


    /**
     * description 创建建表语句
     *
     * @param clazz 类
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/19 下午2:45
     */
    public static String creatTableSQL(Class clazz) {
        return LazyTableUtil.analyzeLazyTable(clazz).creatTableSQL();
    }

    /**
     * description 创建sql查询语句
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/7/8 下午1:06
     */
    public static String createSelectSQL(Class clazz) {
        /**
         *     <sql id="SEARCH_CONDITION_SQL">
         *         <where>
         *             <if test="condition.netName!=null and condition.netName !=''">
         *                 AND T.NET_NAME LIKE CONCAT('%',#{condition.netName,jdbcType=VARCHAR},'%')
         *             </if>
         *             <if test="condition.status !=null and condition.status !=''">
         *                 AND T.STATUS = #{condition.status,jdbcType=VARCHAR}
         *             </if>
         *             <if test="condition.rentCountUp !=null and condition.rentCountUp !=''">
         *                 AND T.RENT_COUNT > #{condition.rentCountUp,jdbcType=NUMERIC}
         *             </if>
         *             <if test="condition.rentCountDown !=null and condition.rentCountDown !=''">
         *                 AND T.RENT_COUNT &lt; #{condition.rentCountDown,jdbcType=NUMERIC}
         *             </if>
         *             <if test="condition.restoreCountUp !=null and condition.restoreCountUp !=''">
         *                 AND T.RESTORE_COUNT > #{condition.restoreCountUp,jdbcType=NUMERIC}
         *             </if>
         *             <if test="condition.restoreCountDown !=null and condition.restoreCountDown !=''">
         *                 AND T.RESTORE_COUNT &lt; #{condition.restoreCountDown,jdbcType=NUMERIC}
         *             </if>
         *         </where>
         *     </sql>
         *
         *
         *         <select id="selectPageBikeStation" resultType="BikeStationDto">
         *         SELECT T.*
         *         FROM
         *         ct_pub_bike_infr_station_capcon_bas T
         *         <include refid="SEARCH_CONDITION_SQL"/>
         *     </select>
         *
         */
        StringBuilder builder = new StringBuilder("  <sql id=\"SEARCH_CONDITION_SQL\"> \n <where> \n");

        // 条件
        List<FieldLazyTableFieldEndpoint> convertedFieldList = LazyTableFieldUtil.analyzeFieldOnAnnotation(clazz, null);
        for (FieldLazyTableFieldEndpoint convertedField : convertedFieldList) {
            String convertedFieldName = convertedField.getColumnName();
            String fieldName = convertedField.getName();
            builder.append("<if test=\"condition." + fieldName + "!=null and condition." + fieldName + " !=''\"> \n");
//            if (convertedField.getClazz().equals(String.class)) {
//                // 包含    AND T.address LIKE CONCAT('%',#{condition.address}, '%')
//                builder.append(" AND T." + convertedFieldName + " LIKE CONCAT('%',#{condition." + fieldName + "}, '%') \n");
//            } else {
            builder.append(" AND T." + convertedFieldName + " = #{condition." + fieldName + "} \n");
//            }
            builder.append("</if> \n");
        }
        builder.append("  </where> \n </sql>\n");
        // 查询sql
        String tableName = LazyTableUtil.getTableName(clazz);
        builder.append(" <select id=\"select" + clazz.getSimpleName() + "\" resultType=\"" + clazz.getName() + "\"> \n");
        builder.append("SELECT T.* FROM ");
        builder.append(tableName).append(" T \n");
        builder.append("<include refid=\"SEARCH_CONDITION_SQL\"/> \n </select>\n");
        System.out.println(builder);
        return builder.toString();
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
        final String tableName = LazyTableUtil.getTableName(clazz);
        stringBuffer.append(tableName);
        // where
        stringBuffer.append(" where ");
        boolean punctuationFlag = false;
        List<FieldLazyTableFieldEndpoint> convertedFieldList = LazyTableFieldUtil.analyzeFieldOnAnnotation(clazz, LayerField.LayerFieldType.ID);

        for (FieldLazyTableFieldEndpoint convertedField : convertedFieldList) {
            try {
                Field field = o.getClass().getDeclaredField(convertedField.getName());
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object fieldVal = field.get(o);
                if (ObjectUtils.isEmpty(fieldVal)) {
                    throw new IllegalArgumentException("根据id主键字段删除 不能为空");
                }
                // add data
                if (punctuationFlag) {
                    stringBuffer.append(" and ");
                }
                stringBuffer.append(convertedField.getColumnName()).append(" = '").append(fieldVal).append("'");
                punctuationFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return stringBuffer.toString();
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
        final String tableName = LazyTableUtil.getTableName(o.getClass());

        stringBuffer.append(tableName);
        stringBuffer.append(" set ");
        List<LazyTableFieldEndpoint> convertedFieldList = LazyTableFieldUtil.analyzeFieldOnAnnotation(o.getClass(), null);
        boolean punctuationFlag = false;
        boolean andFlag = false;
        StringBuffer stringBufferWhere = new StringBuffer(" where ");
        for (LazyTableFieldEndpoint convertedField : convertedFieldList) {
            try {

                Field field = o.getClass().getDeclaredField(convertedField.getName());
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object fieldVal = field.get(o);
                if (convertedField.getFieldIndexType().equals(LayerField.LayerFieldType.FIELD_TYPE)) {
                    if (punctuationFlag) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(convertedField.getColumnName()).append(" = '").append(fieldVal).append("'");
                    punctuationFlag = true;
                } else {
                    if (ObjectUtils.isEmpty(fieldVal)) {
                        continue;
                    }
                    if (andFlag) {
                        stringBufferWhere.append(" and ");
                    }
                    stringBufferWhere.append(convertedField.getColumnName()).append(" = '").append(fieldVal).append("'");
                    andFlag = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // where
        stringBuffer.append(stringBufferWhere);
        System.err.println(stringBuffer);
        return stringBuffer.toString();
    }


    /**
     * description 打印可以执行的sql脚本
     *
     * @param collection
     * @return String
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/9/17 下午1:21
     */
    public static <T> String upsertPreparedStatementSQL(Collection collection, Class<T> clazz, Map iEnumList) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        List<String> fieldNames = new ArrayList<>();
        List<String> updateFieldNames = new ArrayList<>();
        // 添加表名
        final String tableName = LazyTableUtil.getTableName(clazz);
        stringBuilder.append(tableName);
        // 添加字段
        Field[] fields = clazz.getDeclaredFields();
        stringBuilder.append("(");
        /**
         * 是否添加逗号
         */
        for (Field declaredField : fields) {
            if (LazyDatabaseJsonMessage.ignoredFields.contains(declaredField.getName())) {
                continue;
            }
            LazyTableField tableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableField.class);
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
            updateFieldNames.add(fieldName + "=VALUES (" + fieldName + ")");
        }

        stringBuilder.append(String.join(",", fieldNames));

        stringBuilder.append(")  VALUES " + "\n");
        // 添加 数据
        int collectionIndex = 0;
        for (Object o : collection) {
            List<Object> fieldValList = new ArrayList<>();
            collectionIndex++;
            stringBuilder.append("(");
            for (Field declaredField : o.getClass().getDeclaredFields()) {
                try {
                    declaredField.setAccessible(true);
                    if (LazyDatabaseJsonMessage.ignoredFields.contains(declaredField.getName())) {
                        continue;
                    }
                    Object fieldVal = declaredField.get(o);
                    LazyTableField lazyTableField = AnnotationUtils.getAnnotation(declaredField, LazyTableField.class);
                    if (!ObjectUtils.isEmpty(lazyTableField) && !lazyTableField.exist()) {
                        continue;
                    }
                    if (ObjectUtils.isEmpty(fieldVal)) {
                        if (null != lazyTableField && !ObjectUtils.isEmpty(lazyTableField.fieldDefaultValue())) {
                            fieldVal = lazyTableField.fieldDefaultValue();
                        } else {
                            fieldVal = "NULL";
                        }
                    } else {
                        // 时间格式处理
                        Object tempFieldVal = getDate(fieldVal, sf);
                        if (null == tempFieldVal) {
                            fieldVal = EasyAnnotationConverter.annotationConvertConversion(declaredField, fieldVal, iEnumList);
                            fieldVal = fieldVal.toString().replaceAll("'", "\"");
                        } else {
                            fieldVal = tempFieldVal;
                        }
                        fieldVal = "'" + fieldVal + "'";
                    }
                    fieldValList.add(fieldVal);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            stringBuilder.append(fieldValList.stream().map(o1 -> o1.toString()).collect(Collectors.joining(",")));
            stringBuilder.append(")");
            if (collectionIndex != collection.size()) {
                stringBuilder.append(",\n");
            }
            stringBuilder.append("\n");
        }
        // 更新
        stringBuilder.append(" ON DUPLICATE KEY UPDATE \n");
        stringBuilder.append(updateFieldNames.stream().collect(Collectors.joining(",")));
        return stringBuilder.toString();
    }

    /**
     * description 获取时间格式   TODO  时间转换器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/9/17 下午2:31
     */
    static String getDate(Object o, SimpleDateFormat sf) {
        if (Date.class.equals(o.getClass())) {
            return sf.format((Date) o);
        }
        if (LocalDateTime.class.equals(o.getClass())) {
            return ((LocalDateTime) o).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

    /**
     * 打印插入更新的mybatis语句
     *
     * @param clazz 数据传输类
     */
    public static void upsertSQL(Class clazz) {
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        List<String> fieldNames = new ArrayList<>();
        List<Integer> ignoredIndex = new ArrayList<>();
        // 添加表名
        final String tableName = LazyTableUtil.getTableName(clazz);
        stringBuilder.append(tableName);
        // 添加字段
        Field[] fields = clazz.getDeclaredFields();
        stringBuilder.append("(");
        for (int i = 0; i < fields.length; i++) {
            Field declaredField = fields[i];
            LazyTableField tableField = AnnotationUtils.getAnnotation(declaredField, LazyTableField.class);
            String fieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            if (!ObjectUtils.isEmpty(tableField)) {
                if (!tableField.exist()) {
                    ignoredIndex.add(i);
                    continue;
                }
                if (!ObjectUtils.isEmpty(tableField.value())) {
                    fieldName = tableField.value();
                }
            }
            fieldNames.add(fieldName);
//            stringBuilder.append(fieldName);
//            if (i != fields.length - 1) {
//                stringBuilder.append(",");
//            }
        }
        stringBuilder.append(String.join(",", fieldNames));
        stringBuilder.append(")  VALUES " + "\n" + "<foreach collection=\"dtoList\" item=\"dto\" separator=\",\"> \n (");
        // 添加 foreach
        for (int i = 0; i < fields.length; i++) {
            if (ignoredIndex.contains(i)) {
                continue;
            }
            stringBuilder.append("#{dto.").append(fields[i].getName()).append("}");
            if (i != fields.length - 1) {
                stringBuilder.append(",\n");
            }
        }
        stringBuilder.append(") \n </foreach> \n ON DUPLICATE KEY UPDATE \n");
        // 更新
        for (int i = 0; i < fieldNames.size(); i++) {
            if (ignoredIndex.contains(i)) {
                continue;
            }
            stringBuilder.append(fieldNames.get(i)).append("=VALUES (").append(fieldNames.get(i)).append(")");
            if (i != fieldNames.size() - 1) {
                stringBuilder.append(",\n");
            }
        }
        System.out.println("插入语句：\n" + stringBuilder);
    }


}
