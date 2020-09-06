package com.wu.kafka.config;

import com.supconit.its.transform.config.DataProcessConfig;
import com.supconit.its.transform.entity.kafka.TargetJsonSchema;
import com.wu.kafka.enums.DataType;
import com.wu.kafka.stereotype.KafkaSchema;
import com.wu.kafka.stereotype.KafkaSchemaFile;
import com.wu.kafka.until.CamelAndUnderLineConverter;
import com.wu.kafka.until.SpringUntil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

@Data
@Configuration
@ConfigurationProperties(prefix = "data-process")
public class WuDataProcessConfig extends DataProcessConfig {


    private List<Class> schemaClass;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        if(ObjectUtils.isEmpty(getSchema())){
            setSchema(Arrays.asList());
        }
        setSchema(new ArrayList<>(getSchema()));
//        Map<String, Object> stringObjectMap= SpringUntil.applicationContext.getBeansWithAnnotation(KafkaSchema.class);
        for (Class value : schemaClass) {
            KafkaSchema kafkaSchema = (KafkaSchema) value.getAnnotation(KafkaSchema.class);
            TargetJsonSchema targetJsonSchema = new TargetJsonSchema();
            List<TargetJsonSchema.Field> fieldList = new ArrayList<>();
            if (ObjectUtils.isEmpty(kafkaSchema.name())) {
                targetJsonSchema.setName(CamelAndUnderLineConverter.humpToLine2(value.getSimpleName()));
            } else {
                targetJsonSchema.setName(kafkaSchema.name());
            }
            targetJsonSchema.setFields(fieldList);
            System.out.println(kafkaSchema.name());
            for (Field field : value.getDeclaredFields()) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                TargetJsonSchema.Field t = new TargetJsonSchema.Field();
                KafkaSchemaFile kafkaSchemaFile = field.getAnnotation(KafkaSchemaFile.class);
                String fieldName = "";
                String name = "";
                String type = "";
                boolean optional = true;
                if (ObjectUtils.isEmpty(kafkaSchemaFile)) {
                    fieldName = CamelAndUnderLineConverter.humpToLine2(field.getName());
                    type = DataType.getAlias(field.getType());
                } else {
                    fieldName = kafkaSchemaFile.field();
                    type = kafkaSchemaFile.type();
                    optional = kafkaSchemaFile.optional();
                }
                t.setField(fieldName);
                t.setType(type);
                t.setOptional(optional);
                t.init();
                fieldList.add(t);
            }
            getSchema().add(targetJsonSchema);
        }

    }

}
