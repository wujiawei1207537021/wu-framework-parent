package com.wu.framework.easy.stereotype.upsert.converter;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import com.wu.framework.easy.stereotype.upsert.entity.UpsertJsonMessage;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.EasyTableAnnotation;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter.annotationConvertConversion;

/**
 * 自定义 生成新增更新或插入 支持字段映射 sql
 *
 * @author Jia wei Wu
 * @date 2020/6/23 10:15 上午
 */
public class SQLConverter {

    public static final String AUTHOR = "wujiawei";
    /**
     * 打印建表语句
     *
     * @param clazz 数据传输类
     */
//    SQL 描述
    public static final String SQL_DESC =
            "-- ——————————————————————————\n" +
                    "-- create table %s  %s  \n" +  // 表名  表描述
                    "-- add by  %s  %s  \n" +   // 作者  日期
                    "-- ——————————————————————————" + "\n";
    //    删表
    public static final String SQL_DROP = "DROP TABLE IF EXISTS `%s`;\n";
    //    默认字段
    public static final String SQL_DEFAULT_FIELD =
            "`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                    "`is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',\n" +
                    "`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                    "`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                    "PRIMARY KEY (`id`) USING BTREE\n";

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
        stringBuilder.append(tableName(clazz));
        // 添加字段
        Field[] fields = clazz.getDeclaredFields();
        stringBuilder.append("(");
        for (int i = 0; i < fields.length; i++) {
            Field declaredField = fields[i];
            EasySmartField tableField = AnnotationUtils.getAnnotation(declaredField, EasySmartField.class);
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

    public static String createTableSQL(Class clazz) {
        EasyTableAnnotation easyTableAnnotation = LocalStorageClassAnnotation.getEasyTableAnnotation(clazz, true);

//        EasySmart tableNameAnnotation = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
//        List<String> fieldNames = new ArrayList<>();
//        List<Integer> ignoredIndex = new ArrayList<>();
//        String tableComment = "";
//        String tableName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
//        // 添加表名
//        if (!ObjectUtils.isEmpty(tableNameAnnotation)) {
//            if (!ObjectUtils.isEmpty(tableNameAnnotation.tableName())) {
//                tableName = tableNameAnnotation.tableName();
//            }
//            tableComment = tableNameAnnotation.comment();
//        }
//        StringBuilder createTableSQLBuffer = new StringBuilder(
//                String.format(SQL_DESC, tableName, tableComment, AUTHOR, LocalDate.now()));
//        createTableSQLBuffer.append(String.format(SQL_DROP, tableName));
//        createTableSQLBuffer.append("CREATE TABLE `").append(tableName).append("` ( \n");
//        // 是否为一索引
//        List<String> uniqueList = new ArrayList<>();
//        // 添加字段
//        Field[] fields = clazz.getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Field declaredField = fields[i];
//            EasySmartField tableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, EasySmartField.class);
//            String fieldName = "`" + CamelAndUnderLineConverter.humpToLine2(declaredField.getName()) + "`";
//            String type = EasySmartField.FileType.getTypeByClass(declaredField.getType());
//            String comment = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
//            if (!ObjectUtils.isEmpty(tableField)) {
//                if (!tableField.exist()) {
//                    ignoredIndex.add(i);
//                    continue;
//                }
//                comment = tableField.comment();
//                if (!ObjectUtils.isEmpty(tableField.value())) {
//                    fieldName = tableField.value();
//                }
//                if (!ObjectUtils.isEmpty(tableField.type())) {
//                    type = tableField.type();
//                }
//                // 记录索引字段
//                if (tableField.indexType().equals(EasySmartField.TableFileIndexType.UNIQUE)) {
//                    uniqueList.add(fieldName);
//                }
//            }
//            fieldNames.add(fieldName);
//            createTableSQLBuffer.append(fieldName).append(type).append(" COMMENT '").append(comment).append("', \n");
//        }
//        createTableSQLBuffer.append(SQL_DEFAULT_FIELD);
////        UNIQUE KEY `plate_num_color` (`plate_num`,`plate_color`),
//        if (!ObjectUtils.isEmpty(uniqueList)) {
//            createTableSQLBuffer.append(" , UNIQUE KEY `");
//            createTableSQLBuffer.append(String.join("_", uniqueList));
//            createTableSQLBuffer.append("` (`");
//            createTableSQLBuffer.append(String.join("`,`", uniqueList));
//            createTableSQLBuffer.append("`)");
//        }
//        createTableSQLBuffer.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='");
//        createTableSQLBuffer.append(tableComment).append("';\n");
//        createTableSQLBuffer.append("-- ------end \n" +
//                "-- ——————————————————————————\n");
//        System.out.println(createTableSQLBuffer);
//        return createTableSQLBuffer.toString();
        return easyTableAnnotation.creatTableSQL();
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
            return tableNameAnnotation.tableName();
        } else {
            return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
        }
    }

    /**
     * 转换成可执行的sql
     *
     * @param collection
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String upsertPreparedStatementSQL(Collection collection, Class<T> clazz) {
        return upsertPreparedStatementSQL(collection, clazz, null);
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
        stringBuilder.append(tableName(clazz));
        // 添加字段
        Field[] fields = clazz.getDeclaredFields();
        stringBuilder.append("(");
        /**
         * 是否添加逗号
         */
        for (Field declaredField : fields) {
            if (UpsertJsonMessage.ignoredFields.contains(declaredField.getName())) {
                continue;
            }
            EasySmartField tableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, EasySmartField.class);
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
                    if (UpsertJsonMessage.ignoredFields.contains(declaredField.getName())) {
                        continue;
                    }
                    Object fieldVal = declaredField.get(o);
                    EasySmartField easySmartField = AnnotationUtils.getAnnotation(declaredField, EasySmartField.class);
                    if (!ObjectUtils.isEmpty(easySmartField) && !easySmartField.exist()) {
                        continue;
                    }
                    if (ObjectUtils.isEmpty(fieldVal)) {
                        if (null != easySmartField && !ObjectUtils.isEmpty(easySmartField.fieldDefaultValue())) {
                            fieldVal = easySmartField.fieldDefaultValue();
                        } else {
                            fieldVal = "NULL";
                        }
                    } else {
                        // 时间格式处理
                        Object tempFieldVal = getDate(fieldVal, sf);
                        if (null == tempFieldVal) {
                            fieldVal = annotationConvertConversion(declaredField, fieldVal, iEnumList);
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
     * description 获取时间格式
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/9/17 下午2:31
     */
    public static String getDate(Object o, SimpleDateFormat sf) {
        if (Date.class.equals(o.getClass())) {
            return sf.format((Date) o);
        }
        if (LocalDateTime.class.equals(o.getClass())) {
            return ((LocalDateTime) o).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

    public static void SQL(Class clazz) {
        upsertSQL(clazz);
        createTableSQL(clazz);
    }

    /**
     * description 扫描包下面所有class
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/7/13 下午6:40
     */
    public static List<Class> scanClass(String classPath) {
        return scanClass(classPath, null);
    }


    public static List<Class> scanClass(String classPath, Class<? extends Annotation> annotation) {
        List<Class> classList = new ArrayList<>();
        if (ObjectUtils.isEmpty(classPath)) {
            return classList;
        }
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> true;
        provider.addIncludeFilter(includeFilter);
        Set<BeanDefinition> beanDefinitionSet = new HashSet<>();
        // 指定扫描的包名
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(classPath);
        beanDefinitionSet.addAll(candidateComponents);

        beanDefinitionSet.forEach(beanDefinition -> {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinition;
            try {
                Class clazz = Class.forName(beanDefinition.getBeanClassName());

                if (!ObjectUtils.isEmpty(annotation)) {
                    if (!ObjectUtils.isEmpty(AnnotationUtils.getAnnotation(clazz, annotation))) {
                        classList.add(clazz);
                    }
                } else {
                    classList.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
//            System.out.println(definition.getBeanClassName());
        });
        return classList;
    }

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.tableName");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception exception) {
            //  Handle exception.
        }
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
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(clazz, null);
        for (ConvertedField convertedField : convertedFieldList) {
            String convertedFieldName = convertedField.getConvertedFieldName();
            String fieldName = convertedField.getFieldName();
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
        String tableName = tableName(clazz);
        builder.append(" <select id=\"select" + clazz.getSimpleName() + "\" resultType=\"" + clazz.getName() + "\"> \n");
        builder.append("SELECT T.* FROM ");
        builder.append(tableName).append(" T \n");
        builder.append("<include refid=\"SEARCH_CONDITION_SQL\"/> \n </select>\n");
        System.out.println(builder.toString());
        return builder.toString();
    }


    /**
     * description 不完整创建sql查询语句
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 下午12:39
     */
    public static String createSelectSQL(List<ConvertedField> convertedFieldList, String tableName) {
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
        for (ConvertedField convertedField : convertedFieldList) {
            String convertedFieldName = convertedField.getConvertedFieldName();
            String fieldName = convertedField.getFieldName();
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
        String beanName = CamelAndUnderLineConverter.lineToHump(tableName);
        builder.append(" <select id=\"select" + beanName + "\" resultType=\"" + beanName + "\"> \n");
        builder.append("SELECT T.* FROM ");
        builder.append(tableName).append(" T \n");
        builder.append("<include refid=\"SEARCH_CONDITION_SQL\"/> \n </select>\n");
        System.out.println(builder.toString());
        return builder.toString();
    }

    /**
     * description  抽取 字段名和需要映射属性名
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/9/17 下午1:29
     */
    public static <T> List<ConvertedField> fieldNamesOnAnnotation(Class<T> clazz, EasySmartField.TableFileIndexType tableFileIndexType) {
        List<ConvertedField> convertedFieldList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            EasySmartField easySmartField = AnnotatedElementUtils.findMergedAnnotation(declaredField, EasySmartField.class);
            String convertedFieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            ConvertedField convertedField = new ConvertedField();
            if (!ObjectUtils.isEmpty(easySmartField)) {
                if (!easySmartField.exist()) {
                    continue;
                }
                // 判断是否是我想要的类型
                if (!ObjectUtils.isEmpty(tableFileIndexType) && !tableFileIndexType.equals(easySmartField.indexType())) {
                    continue;
                }
                if (!ObjectUtils.isEmpty(easySmartField.value())) {
                    convertedFieldName = easySmartField.value();
                }
                convertedField.setComment(easySmartField.comment());
                convertedField.setFieldIndexType(easySmartField.indexType());
            }
            convertedField.setConvertedFieldName(convertedFieldName);
            convertedField.setFieldName(declaredField.getName());
            convertedField.setClazz(declaredField.getType());
            convertedField.setType(EasySmartField.FileType.getTypeByClass(declaredField.getType()));
            convertedFieldList.add(convertedField);
        }
        return convertedFieldList;
    }

    /**
     * @param columnNameList 数据库字段
     * @param tableClass     表类型
     * @return
     * @describe 更改表
     * @author Jia wei Wu
     * @date 2020/12/12 9:12 下午
     **/
    public static String alterTableSQL(List<String> columnNameList, Class tableClass) {
        // 添加列
        //        ALTER TABLE tableName
        //      ADD columnName VARCHAR(255) 'comment'
        String ALTER_TABLE = "ALTER TABLE %s ";
        String ADD_FIELD = " ADD %s %s %s "; // 字段名 字段类型 字段备注
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(tableClass, null);
        String ADD_SQL = convertedFieldList.stream().
                filter(convertedField -> !columnNameList.contains(convertedField.getConvertedFieldName().replaceAll("`", ""))).
                map(convertedField ->
                        String.format(ADD_FIELD,
                                convertedField.getConvertedFieldName(),
                                convertedField.getType(),
                                convertedField.getComment())).
                collect(Collectors.joining(","));
        if (ObjectUtils.isEmpty(ADD_SQL)) return null;
        // 更改列
        //        modify columnName varchar2(255)
        String MODIFY_FIELD = " MODIFY %s %s %s "; // 字段名 字段类型 字段备注
        return String.join("", String.format(ALTER_TABLE, EasyAnnotationConverter.getTableName(tableClass)), ADD_SQL, "");
    }
}
