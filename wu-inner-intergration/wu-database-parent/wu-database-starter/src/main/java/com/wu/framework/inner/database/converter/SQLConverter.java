package com.wu.framework.inner.database.converter;


import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import com.wu.framework.inner.database.domain.ConvertedField;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.wu.framework.inner.database.converter.PreparedStatementSQLConverter.fieldNamesOnAnnotation;
import static com.wu.framework.inner.database.converter.PreparedStatementSQLConverter.tableName;

/**
 * 自定义 生成新增更新或插入 支持字段映射 sql
 *
 * @author 吴佳伟
 * @date 2020/6/23 10:15 上午
 */
public class SQLConverter {

    /**
     * 打印插入更新语句
     *
     * @param clazz 数据传输类
     */
    public static void upsertSQL(Class clazz) {
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        CustomTable tableNameAnnotation = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        List<String> fieldNames = new ArrayList<>();
        List<Integer> ignoredIndex = new ArrayList<>();
        // 添加表名
        if (ObjectUtils.isEmpty(tableNameAnnotation)) {
            stringBuilder.append(CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName()));
        } else {
            stringBuilder.append(tableNameAnnotation.value());
        }
        // 添加字段
        Field[] fields = clazz.getDeclaredFields();
        stringBuilder.append("(");
        for (int i = 0; i < fields.length; i++) {
            Field declaredField = fields[i];
            CustomTableFile
                    tableField =  AnnotatedElementUtils.findMergedAnnotation(declaredField, CustomTableFile.class);
            String fieldName;
            if (ObjectUtils.isEmpty(tableField)) {
                fieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            } else {
                if (!tableField.exist()) {
                    ignoredIndex.add(i);
                    continue;
                }
                fieldName = tableField.value();
            }
            fieldNames.add(fieldName);
            stringBuilder.append(fieldName);
            if (i != fields.length - 1) {
                stringBuilder.append(",");
            }
        }
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

    /**
     * 打印建表语句
     *
     * @param clazz 数据传输类
     */
    public static String createTableSQL(Class clazz) {
        StringBuilder sqlBuffer = new StringBuilder();
        CustomTable tableNameAnnotation = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        List<String> fieldNames = new ArrayList<>();
        List<Integer> ignoredIndex = new ArrayList<>();
        String tableComment = "";
        String tableName;
        // 添加表名
        if (ObjectUtils.isEmpty(tableNameAnnotation)) {
            tableName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
        } else {
            tableName = tableNameAnnotation.name();
            tableComment = tableNameAnnotation.comment();
        }
        sqlBuffer.append("-- ——————————————————————————\n" +
                "--  create table " + tableName + " \n" +
                "-- ——————————————————————————" + "\n");
        sqlBuffer.append("DROP TABLE IF EXISTS `");
        sqlBuffer.append(tableName + "`;\n");
        sqlBuffer.append("create table `").append(tableName);
        sqlBuffer.append("` ( \n").append("`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',");

        // 添加字段
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field declaredField = fields[i];
            CustomTableFile tableField =  AnnotatedElementUtils.findMergedAnnotation(declaredField, CustomTableFile.class);
            String fieldName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            String type = CustomTableFile.FileType.getTypeByClass(declaredField.getType());
            String comment = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            if (!ObjectUtils.isEmpty(tableField)) {
                if (!tableField.exist()) {
                    ignoredIndex.add(i);
                    continue;
                }
                comment = tableField.comment();
                fieldName = tableField.value();
                if (!ObjectUtils.isEmpty(tableField.dataType())) {
                    type = tableField.dataType();
                }
            }
            fieldNames.add(fieldName);
            sqlBuffer.append("`").append(fieldName).append("`").append(type).append(" COMMENT '").append(comment).append("',");
        }
        sqlBuffer.append("`is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',\n" +
                "  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='");
        sqlBuffer.append(tableComment).append("';\n");
        sqlBuffer.append("-- ------end \n" +
                "-- ——————————————————————————\n");
        System.out.println(sqlBuffer);
        return sqlBuffer.toString();
    }

    /**
     * description 创建sql查询语句
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
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
        List<ConvertedField> convertedFieldList = fieldNamesOnAnnotation(clazz);
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

    public static void SQL(Class clazz) {
        upsertSQL(clazz);
        createTableSQL(clazz);
    }

}
