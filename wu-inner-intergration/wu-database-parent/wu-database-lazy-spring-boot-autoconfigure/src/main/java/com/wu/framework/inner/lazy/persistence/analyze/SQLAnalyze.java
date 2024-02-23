package com.wu.framework.inner.lazy.persistence.analyze;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.layer.stereotype.analyze.AnalyzeParameter;
import com.wu.framework.inner.layer.stereotype.analyze.LayerClassAnalyze;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.converter.SQLConverter;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface SQLAnalyze extends LayerClassAnalyze {

    Logger log = LoggerFactory.getLogger(SQLAnalyze.class);

    String AUTHOR = "wujiawei";
    /**
     * 打印建表语句
     *
     * @param clazz 数据传输类
     */
//    SQL 描述
    String SQL_DESC =
            "-- ——————————————————————————\n" +
                    "-- create table %s  %s  \n" +  // 表名  表描述
                    "-- add by  %s  %s  \n" +   // 作者  日期
                    "-- ——————————————————————————" + "\n";
    //    删表
    String SQL_DROP = "DROP TABLE IF EXISTS `%s`;\n";
    // 默认字段
    List<String> SQL_DEFAULT_FIELD = Arrays.asList("id", "is_deleted", "create_time", "update_time");
    //    默认字段语句
    String SQL_DEFAULT_FIELD_STATEMENT =
            "`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                    "`is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',\n" +
                    "`create_time` datetime DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',\n" +
                    "`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                    "PRIMARY KEY (`id`) USING BTREE\n";
    /**
     * 默认字段语句模版
     */
    String SQL_DEFAULT_FIELD_STATEMENT_STENCIL =
            "`%s` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                    "`is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',\n" +
                    "`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                    "`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                    "PRIMARY KEY (`%s`) USING BTREE\n";

    // 防止多线程并发
    ConcurrentMap<Class, LazyTableEndpoint> CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP = new ConcurrentHashMap<>();


    /**
     * description 扫描包下面所有class
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/7/13 下午6:40
     */
    static List<Class> scanClass(String classPath) {
        return scanClass(classPath, null);
    }

    static List<Class> scanClass(String classPath, Class<? extends Annotation> annotation) {
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

    static void clearConsole() {
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
     * description 不完整创建sql查询语句
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 下午12:39
     */
    static String createSelectSQL(List<FieldLazyTableFieldEndpoint> convertedFieldList, String tableName) {
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
        String beanName = CamelAndUnderLineConverter.lineToHumpField(tableName);
        builder.append(" <select id=\"select" + beanName + "\" resultType=\"" + beanName + "\"> \n");
        builder.append("SELECT T.* FROM ");
        builder.append(tableName).append(" T \n");
        builder.append("<include refid=\"SEARCH_CONDITION_SQL\"/> \n </select>\n");
        System.out.println(builder);
        return builder.toString();
    }


    /**
     * 获取 表名称
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/3 下午9:48
     **/
    default <T> String tableName(Class<T> clazz) {
        LazyTable tableNameAnnotation = AnnotatedElementUtils.findMergedAnnotation(clazz, LazyTable.class);
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
    default <T> String upsertPreparedStatementSQL(Collection collection, Class<T> clazz) {
        return SQLConverter.upsertPreparedStatementSQL(collection, clazz, null);
    }


    /**
     * description 解析class 适配class 为uo的类型
     * 天然适应Uo
     *
     * @param analyzeParameter
     * @author Jia wei Wu
     * @date 2021/4/7 下午1:47
     */
    @Override
    default LayerClass analyze(AnalyzeParameter analyzeParameter) {
        LayerClass mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(analyzeParameter.getClazz(), LayerClass.class);
        if (ObjectUtils.isEmpty(mergedAnnotation) || ObjectUtils.isEmpty(mergedAnnotation.name())) {
            mergedAnnotation = new LayerClass() {

                /**
                 * Returns the annotation type of this annotation.
                 *
                 * @return the annotation type of this annotation
                 */
                @Override
                public Class<? extends Annotation> annotationType() {
                    return LayerClass.class;
                }

                /**
                 * class name 例如:layer_class
                 *
                 */
                @Override
                public String name() {
                    String simpleName = analyzeParameter.getClazz().getSimpleName();
                    // 天然适应Uo
                    if (simpleName.endsWith("Uo")) {
                        simpleName = simpleName.substring(0, simpleName.length() - 2);
                    }
                    return CamelAndUnderLineConverter.humpToLine2(simpleName);
                }
            };
        }
        return mergedAnnotation;

    }


}
