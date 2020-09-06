package com.supconit.its.transform.config;

import com.supconit.its.transform.entity.kafka.TargetJsonSchema;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author 黄珺
 * @date 2019-08-30
 */
@Configuration
@ConfigurationProperties(prefix = "data-process")
public class DataProcessConfig {

    /**
     * 数据处理任务名称
     */
    private String taskName;

    /**
     * kafka broker地址，以逗号分割
     */
    private String bootstrapServers;

    /**
     * 源topic名称，以逗号分割
     */
    private String kafkaSourceTopic;

    /**
     * 消费者线程数，不能超过所有源topic的分区数之和
     */
    private String concurrency;

    /**
     * 默认目标topic名称，可在转换时手动指定
     */
    private String defaultKafkaTargetTopic;

    /**
     * 读取source topic的消费者组id
     */
    private String consumerGroupId;

    /**
     * 目标数据源的schema
     */
    private List<TargetJsonSchema> schema;

    @PostConstruct
    public void init() {
        if (schema != null && schema.size() != 0) {
            for (TargetJsonSchema targetJsonSchema : schema) {
                List<TargetJsonSchema.Field> fields = targetJsonSchema.getFields();
                if (fields != null && fields.size() != 0) {
                    for (TargetJsonSchema.Field field : fields) {
                        field.init();
                    }
                }
            }
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getKafkaSourceTopic() {
        return kafkaSourceTopic;
    }

    public void setKafkaSourceTopic(String kafkaSourceTopic) {
        this.kafkaSourceTopic = kafkaSourceTopic;
    }

    public String getDefaultKafkaTargetTopic() {
        return defaultKafkaTargetTopic;
    }

    public void setDefaultKafkaTargetTopic(String defaultKafkaTargetTopic) {
        this.defaultKafkaTargetTopic = defaultKafkaTargetTopic;
    }

    public String getConsumerGroupId() {
        return consumerGroupId;
    }

    public void setConsumerGroupId(String consumerGroupId) {
        this.consumerGroupId = consumerGroupId;
    }

    public List<TargetJsonSchema> getSchema() {
        return schema;
    }

    public void setSchema(List<TargetJsonSchema> schema) {
        this.schema = schema;
    }

    public String getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(String concurrency) {
        this.concurrency = concurrency;
    }
}
