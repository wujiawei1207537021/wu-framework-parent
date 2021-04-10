package com.wu.framework.easy.stereotype.upsert.entity.stereotye;

import com.wu.framework.easy.stereotype.upsert.converter.SQLAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTable;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.SmartMark;
import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * description 本地存储类注解
 *
 * @author Jia wei Wu
 * @date 2020/9/3 上午10:02
 */
@Data
public class LocalStorageClassAnnotation {

    public final static String DOMAIN_CLASS_TEMP =
            "package com.wu.framework.inner.lazy.database.test.pojo;\n" +
                    "\n" +
                    "import lombok.AllArgsConstructor;\n" +
                    "import lombok.Data;\n" +
                    "import lombok.NoArgsConstructor;\n" +
                    "import lombok.experimental.Accessors;\n" +
                    "import java.io.Serializable;\n" +
                    "@Accessors(chain = true)\n" +
                    "@Data\n" +
                    "@AllArgsConstructor\n" +
                    "@NoArgsConstructor"
                    + "\n";
    private static final String PREFIX = "easy_upsert_";
    //    private static final Logger log = LoggerFactory.getLogger(LocalStorageClassAnnotation.class);
    @Deprecated
    public static Map<Class, LazyTableAnnotation> CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP = new HashMap<>();
    public static Map<Class, LazyTable> CLASS_EASY_SMART_MAP = new HashMap<>();

    /**
     * @param clazz                      类
     * @param isForceDuplicateNameSwitch 是强制重复名称切换
     * @return
     * @describe see {{@link #LazyTable(Class, boolean)}
     * @author Jia wei Wu
     * @date 2021/3/3 9:34 下午
     **/
    @Deprecated
    public static LazyTableAnnotation getEasyTableAnnotation(Class clazz, boolean isForceDuplicateNameSwitch) {
        if (!CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.containsKey(clazz)) {
            String kafkaTopicName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            LazyTable LazyTable = AnnotationUtils.getAnnotation(clazz, LazyTable.class);
            String kafkaCode = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            String className = clazz.getName();
            String tableName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            String comment = "";
            String kafkaSchemaName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());

            if (null != LazyTable) {
                if (!ObjectUtils.isEmpty(LazyTable.kafkaTopicName())) {
                    kafkaTopicName = LazyTable.kafkaTopicName();
                }
                if (!ObjectUtils.isEmpty(LazyTable.kafkaCode())) {
                    kafkaCode = LazyTable.kafkaCode();
                }
                if (!ObjectUtils.isEmpty(LazyTable.tableName())) {
                    tableName = LazyTable.tableName();
                }
                if (!ObjectUtils.isEmpty(LazyTable.kafkaSchemaName())) {
                    kafkaSchemaName = LazyTable.kafkaSchemaName();
                }
            }
            if (isForceDuplicateNameSwitch) {
                kafkaSchemaName = CamelAndUnderLineConverter.humpToLine2(clazz.getName().replace(".", "_"));
            }

            LazyTableAnnotation LazyTableAnnotation = new LazyTableAnnotation();
            LazyTableAnnotation.setComment(comment);
            LazyTableAnnotation.setClassName(className);
            LazyTableAnnotation.setClazz(clazz);
            LazyTableAnnotation.setTableName(tableName);
            LazyTableAnnotation.setKafkaSchemaName(kafkaSchemaName);
            LazyTableAnnotation.setKafkaTopicName(kafkaTopicName);
            LazyTableAnnotation.setKafkaCode(kafkaCode);
            LazyTableAnnotation.setConvertedFieldList(SQLAnalyze.fieldNamesOnAnnotation(clazz, null));
            LazyTableAnnotation.setSmartFillField(LazyTable.smartFillField());
//            log.info("Initialize {} annotation parameters  className:[{}],tableName:[{}],comment:[{}],kafkaTopicName:[{}],kafkaSchemaName:[{}],kafkaCode:[{}]", clazz,
//                    className, tableName, comment, kafkaTopicName, kafkaSchemaName, kafkaCode);
            CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.put(clazz, LazyTableAnnotation);
        }
        return CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.get(clazz);
    }

    /**
     * @param
     * @return
     * @describe 获取并且完善 LazyTable
     * @author Jia wei Wu
     * @date 2021/3/29 8:43 下午
     **/
    public static LazyTable LazyTable(Class clazz, boolean isForceDuplicateNameSwitch) {
        if (!CLASS_EASY_SMART_MAP.containsKey(clazz)) {
            String kafkaTopicName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            LazyTable LazyTable = AnnotationUtils.getAnnotation(clazz, LazyTable.class);
            String kafkaCode = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            String className = clazz.getName();
            String tableName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            String comment = "";
            String kafkaSchemaName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            boolean perfectTable=false;
            boolean smartFillField = false;
            if (null != LazyTable) {
                if (!ObjectUtils.isEmpty(LazyTable.kafkaTopicName())) {
                    kafkaTopicName = LazyTable.kafkaTopicName();
                }
                if (!ObjectUtils.isEmpty(LazyTable.kafkaCode())) {
                    kafkaCode = LazyTable.kafkaCode();
                }
                if (!ObjectUtils.isEmpty(LazyTable.tableName())) {
                    tableName = LazyTable.tableName();
                }
                if (!ObjectUtils.isEmpty(LazyTable.kafkaSchemaName())) {
                    kafkaSchemaName = LazyTable.kafkaSchemaName();
                }
                smartFillField = LazyTable.smartFillField();
                perfectTable=LazyTable.perfectTable();
            }
            if (isForceDuplicateNameSwitch) {
                kafkaSchemaName = CamelAndUnderLineConverter.humpToLine2(clazz.getName().replace(".", "_"));
            }

            LazyTableAnnotation LazyTableAnnotation = new LazyTableAnnotation();
            LazyTableAnnotation.setComment(comment);
            LazyTableAnnotation.setClassName(className);
            LazyTableAnnotation.setClazz(clazz);
            LazyTableAnnotation.setTableName(tableName);
            LazyTableAnnotation.setKafkaSchemaName(kafkaSchemaName);
            LazyTableAnnotation.setKafkaTopicName(kafkaTopicName);
            LazyTableAnnotation.setKafkaCode(kafkaCode);
            LazyTableAnnotation.setConvertedFieldList(SQLAnalyze.fieldNamesOnAnnotation(clazz, null));
            LazyTableAnnotation.setSmartFillField(smartFillField);
//            log.info("Initialize {} annotation parameters  className:[{}],tableName:[{}],comment:[{}],kafkaTopicName:[{}],kafkaSchemaName:[{}],kafkaCode:[{}]", clazz,
//                    className, tableName, comment, kafkaTopicName, kafkaSchemaName, kafkaCode);

            String finalTableName = tableName;
            String finalKafkaSchemaName = kafkaSchemaName;
            String finalKafkaTopicName = kafkaTopicName;
            String finalKafkaCode = kafkaCode;

            boolean finalSmartFillField = smartFillField;
            boolean finalPerfectTable = perfectTable;
            CLASS_EASY_SMART_MAP.put(clazz, new LazyTable() {

                /**
                 * Returns the annotation type of this annotation.
                 *
                 * @return the annotation type of this annotation
                 */
                @Override
                public Class<? extends Annotation> annotationType() {
                    return LazyTable.class;
                }

                /**
                 * 表名
                 *
                 * @return
                 */
                @Override
                public String value() {
                    return finalTableName;
                }

                @Override
                public String tableName() {
                    return finalTableName;
                }

                /**
                 * 完善表
                 *
                 * @return
                 */
                @Override
                public boolean perfectTable() {
                    return finalPerfectTable;
                }

                /**
                 * 数据下钻
                 * the field use Annotation with {@link SmartMark}
                 */
                @Override
                public boolean dataDrillDown() {
                    return null == LazyTable ? false : LazyTable.dataDrillDown();
                }

                /**
                 * 表注释
                 *
                 * @return String
                 */
                @Override
                public String comment() {
                    return comment;
                }

                /**
                 * kafka  schema 名称
                 *
                 * @return String
                 */
                @Override
                public String kafkaSchemaName() {
                    return finalKafkaSchemaName;
                }

                /**
                 * kafka 主题 为空使用类名
                 *
                 * @return String
                 */
                @Override
                public String kafkaTopicName() {
                    return finalKafkaTopicName;
                }

                /**
                 * kafka code编码
                 *
                 * @return String
                 */
                @Override
                public String kafkaCode() {
                    return finalKafkaCode;
                }

                /**
                 * 数据库名 schema
                 *
                 * @return String
                 */
                @Override
                public String schema() {
                    return null == LazyTable ? "" : LazyTable.schema();
                }

                /**
                 * Elasticsearch 索引前缀
                 *
                 * @return String
                 */
                @Override
                public String indexPrefix() {
                    return null == LazyTable ? "" : LazyTable.indexPrefix();
                }

                /**
                 * Elasticsearch 索引时间格式
                 *
                 * @return String
                 */
                @Override
                public String indexFormat() {
                    return null == LazyTable ? "" : LazyTable.indexFormat();
                }

                /**
                 * Elasticsearch 索引后缀
                 */
                @Override
                public String indexSuffix() {
                    return null == LazyTable ? "" : LazyTable.indexSuffix();
                }

                /**
                 * Elasticsearch 索引类型
                 */
                @Override
                public String indexType() {
                    return null == LazyTable ? "" : LazyTable.indexType();
                }

                /**
                 * redis key
                 */
                @Override
                public String redisKey() {
                    return null == LazyTable ? finalTableName : LazyTable.redisKey();
                }

                /**
                 * 列族
                 *
                 * @return
                 */
                @Override
                public String columnFamily() {
                    return null == LazyTable ? "" : LazyTable.columnFamily();
                }

                /**
                 * 智能填充bean属性
                 * 针对数据源 如mysql查询结果、http请求结果中包含的数据字段不再当前对象中
                 */
                @Override
                public boolean smartFillField() {
                    return finalSmartFillField;
                }
            });
        }
        return CLASS_EASY_SMART_MAP.get(clazz);
    }

}
