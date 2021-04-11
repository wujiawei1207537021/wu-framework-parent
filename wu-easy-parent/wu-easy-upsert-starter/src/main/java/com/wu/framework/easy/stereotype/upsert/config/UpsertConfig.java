package com.wu.framework.easy.stereotype.upsert.config;


import com.google.common.collect.Maps;
import com.wu.framework.inner.lazy.database.expand.database.persistence.conf.UpsertJsonMessage;
import com.wu.framework.easy.stereotype.upsert.entity.kafka.KafkaJsonMessage;
import com.wu.framework.easy.stereotype.upsert.entity.kafka.TargetJsonSchema;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * description 插入或者更新 配置
 *
 * @author Jia wei Wu
 * @date 2020/7/16 上午11:29
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix =UpsertConfig.UPSERT_PREFIX )
public class UpsertConfig implements InitializingBean {

    public static final String  UPSERT_PREFIX="spring.easy.upsert.config";


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
     * 主数据源类型
     */
    private EasyUpsertType easyUpsertType = EasyUpsertType.AUTO;

    /**
     * 批量条数限制
     */
    private Integer batchLimit = 1000;

    /**
     * 缓存文件地址
     */
    private String cacheFileAddress = System.getProperty("user.dir");

    /**
     * 记录日志
     */
    private boolean recordLog = false;

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


}
