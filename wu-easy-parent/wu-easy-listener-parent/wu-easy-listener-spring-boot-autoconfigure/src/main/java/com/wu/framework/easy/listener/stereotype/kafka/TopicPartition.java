package com.wu.framework.easy.listener.stereotype.kafka;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface TopicPartition {

    /**
     * The topic to listen on.
     *
     * @return the topic to listen on. Property place holders
     * and SpEL expressions are supported, which must resolve
     * to a String.
     */
    String topic();

    /**
     * The partitions within the topic. Partitions specified here can't be duplicated in
     * {@link #partitionOffsets()}. Each string can contain a comma-delimited list of
     * partitions, or ranges of partitions (e.g. {@code 0-5, 7, 10-15}.
     *
     * @return the partitions within the topic. Property place holders and SpEL
     * expressions are supported, which must resolve to Integers (or Strings that can be
     * parsed as Integers).
     */
    String[] partitions() default {};

    /**
     * The partitions with initial offsets within the topic. There must only be one
     * instance of {@link PartitionOffset} if its 'partition' property is '*'.
     * Partitions specified here can't be duplicated in the {@link #partitions()}.
     *
     * @return the {@link PartitionOffset} array.
     */
    PartitionOffset[] partitionOffsets() default {};

}
