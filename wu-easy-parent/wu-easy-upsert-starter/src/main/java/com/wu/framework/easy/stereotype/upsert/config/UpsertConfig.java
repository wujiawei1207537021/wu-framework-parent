package com.wu.framework.easy.stereotype.upsert.config;


import com.google.common.collect.Maps;
import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.converter.ConverterClass2KafkaSchema;
import com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter;
import com.wu.framework.easy.stereotype.upsert.entity.UpsertJsonMessage;
import com.wu.framework.easy.stereotype.upsert.entity.kafka.KafkaJsonMessage;
import com.wu.framework.easy.stereotype.upsert.entity.kafka.TargetJsonSchema;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description 插入或者更新 配置
 *
 * @author 吴佳伟
 * @date 2020/7/16 上午11:29
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties("easy.upsert.config")
public class UpsertConfig implements InitializingBean {


    /**
     * 目标数据源的schema
     */
    private List<TargetJsonSchema> schema;
    /**
     * 强制重名开关
     */
    private boolean forceDuplicateNameSwitch = false;

    /**
     * 打印sql
     */
    private boolean printSql = false;

    /**
     * 忽略的字段
     */
    private List<String> ignoredFields;

    /**
     * 已经会自动创建schema
     */
    @Deprecated
    private List<Class> schemaClass = new ArrayList<>();

    /**
     * 已经会自动创建schema
     */
    @Deprecated
    private List<String> schemaClassPath;

    /**
     * 主数据源类型
     */
    private EasyUpsertType easyUpsertType = EasyUpsertType.AUTO;

    /**
     * 批量条数限制
     */
    private Integer batchLimit = 1000;


    @Deprecated
    @PostConstruct
    public void init() {
//        if (!accessViaKafkaSwitch) {
//            // 拦截bean Kafka 注入
//            return;
//        }
//        super.init();
        if (ObjectUtils.isEmpty(schema)) {
            setSchema(new ArrayList<>());
        }
        // 指定路径下的类
        scanClass();
        setSchema(new ArrayList<>(getSchema()));
        if (ObjectUtils.isEmpty(schemaClass)) {
            return;
        }

        for (Class value : schemaClass) {
            EasyTable easyTable = AnnotationUtils.getAnnotation(value, EasyTable.class);
            TargetJsonSchema targetJsonSchema = new TargetJsonSchema();
            targetJsonSchema.setName(EasyAnnotationConverter.getKafkaSchemaName(value, forceDuplicateNameSwitch));
            if (!ObjectUtils.isEmpty(easyTable) && !ObjectUtils.isEmpty(easyTable.name())) {
                targetJsonSchema.setName(easyTable.name());
            }
            for (TargetJsonSchema jsonSchema : getSchema()) {
                if (jsonSchema.getName().equals(targetJsonSchema.getName())) {
                    continue;
                }
            }
            targetJsonSchema = ConverterClass2KafkaSchema.converterClass2TargetJsonSchema(value, forceDuplicateNameSwitch);
            schema.add(targetJsonSchema);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == schema) {
            return;
        }
        KafkaJsonMessage.targetSchemaMap = Maps.uniqueIndex(schema, TargetJsonSchema::getName);
        if (!ObjectUtils.isEmpty(ignoredFields)) {
            UpsertJsonMessage.ignoredFields.addAll(ignoredFields);
        }
        for (TargetJsonSchema targetJsonSchema : schema) {
            log.info("动态加载Schema:{}-成功", targetJsonSchema.getName());
        }
        log.info("动态Schema-加载 {} 成功", this.getSchema().size());

    }

    @Deprecated
    private void scanClass() {
        if (ObjectUtils.isEmpty(schemaClassPath)) {
            return;
        }
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

        TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> true;
        provider.addIncludeFilter(includeFilter);
        Set<BeanDefinition> beanDefinitionSet = new HashSet<>();
        for (String s : schemaClassPath) {
            // 指定扫描的包名
            Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(s);
            beanDefinitionSet.addAll(candidateComponents);
        }
        beanDefinitionSet.forEach(beanDefinition -> {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinition;
            try {
                schemaClass.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
//            System.out.println(definition.getBeanClassName());
        });
    }


}
