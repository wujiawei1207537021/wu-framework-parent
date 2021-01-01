package com.wu.framework.easy.stereotype.upsert.entity.stereotye;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

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

    private static final String PREFIX = "easy_upsert_";
    private static final Logger log = LoggerFactory.getLogger(LocalStorageClassAnnotation.class);

    public static Map<Class, EasyTableAnnotation> CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP = new HashMap<>();


    public static EasyTableAnnotation getEasyTableAnnotation(Class clazz, boolean isForceDuplicateNameSwitch) {
        if (!CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.containsKey(clazz)) {
            String kafkaTopicName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
            String kafkaCode = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            String className = clazz.getName();
            String tableName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
            String comment = "";
            String kafkaSchemaName = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());

            if (null != easySmart) {
                if (!ObjectUtils.isEmpty(easySmart.kafkaTopicName())) {
                    kafkaTopicName = easySmart.kafkaTopicName();
                }
                if (!ObjectUtils.isEmpty(easySmart.kafkaCode())) {
                    kafkaCode = easySmart.kafkaCode();
                }
                if (!ObjectUtils.isEmpty(easySmart.tableName())) {
                    tableName = easySmart.tableName();
                }
                if (!ObjectUtils.isEmpty(easySmart.kafkaSchemaName())) {
                    kafkaSchemaName = easySmart.kafkaSchemaName();
                }
            }
            if (isForceDuplicateNameSwitch) {
                kafkaSchemaName = CamelAndUnderLineConverter.humpToLine2(clazz.getName().replace(".", "_"));
            }

            EasyTableAnnotation easyTableAnnotation = new EasyTableAnnotation();
            easyTableAnnotation.setComment(comment);
            easyTableAnnotation.setClassName(className);
            easyTableAnnotation.setClazz(clazz);
            easyTableAnnotation.setTableName(tableName);
            easyTableAnnotation.setKafkaSchemaName(kafkaSchemaName);
            easyTableAnnotation.setKafkaTopicName(kafkaTopicName);
            easyTableAnnotation.setKafkaCode(kafkaCode);
            easyTableAnnotation.setConvertedFieldList(SQLConverter.fieldNamesOnAnnotation(clazz,null));
            log.info("Initialize {} annotation parameters  className:[{}],tableName:[{}],comment:[{}],kafkaTopicName:[{}],kafkaSchemaName:[{}],kafkaCode:[{}]", clazz,
                    className, tableName, comment, kafkaTopicName, kafkaSchemaName, kafkaCode);
            CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.put(clazz, easyTableAnnotation);
        }
        return CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.get(clazz);
    }

}
