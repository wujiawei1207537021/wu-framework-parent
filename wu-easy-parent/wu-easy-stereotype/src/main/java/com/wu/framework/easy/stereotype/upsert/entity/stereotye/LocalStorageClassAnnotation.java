package com.wu.framework.easy.stereotype.upsert.entity.stereotye;

import com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * description 本地存储类注解
 *
 * @author 吴佳伟
 * @date 2020/9/3 上午10:02
 */
@Data
public class LocalStorageClassAnnotation {

    private static final String PREFIX = "easy_upsert_";
    private static final Logger log = LoggerFactory.getLogger(LocalStorageClassAnnotation.class);

    public static Map<Class, CustomTableAnnotation> CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP = new HashMap<>();


    public static CustomTableAnnotation getCustomTableAnnotationAttr(Class clazz, boolean isForceDuplicateNameSwitch) {
        if (!CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.containsKey(clazz)) {
            String kafkaCode = PREFIX + EasyAnnotationConverter.getKafkaCode(clazz);
            String className = clazz.getName();
            String name = EasyAnnotationConverter.getTableName(clazz);
            String comment = EasyAnnotationConverter.getComment(clazz);
            String kafkaTopicName = EasyAnnotationConverter.getKafkaTopicName(clazz);
            String kafkaSchemaName = EasyAnnotationConverter.getKafkaSchemaName(clazz, isForceDuplicateNameSwitch);


            CustomTableAnnotation customTableAnnotation = new CustomTableAnnotation();
            customTableAnnotation.setComment(comment);
            customTableAnnotation.setClassName(className);
            customTableAnnotation.setName(name);
            customTableAnnotation.setKafkaSchemaName(kafkaSchemaName);
            customTableAnnotation.setKafkaTopicName(kafkaTopicName);
            customTableAnnotation.setKafkaCode(kafkaCode);
            log.info("Initialize {} annotation parameters  className:[{}],tableName:[{}],comment:[{}],kafkaTopicName:[{}],kafkaSchemaName:[{}],kafkaCode:[{}]", clazz,
                    className, name, comment, kafkaTopicName, kafkaSchemaName, kafkaCode);
            CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.put(clazz, customTableAnnotation);
        }
        return CLASS_CUSTOM_TABLE_ANNOTATION_ATTR_MAP.get(clazz);
    }

}
