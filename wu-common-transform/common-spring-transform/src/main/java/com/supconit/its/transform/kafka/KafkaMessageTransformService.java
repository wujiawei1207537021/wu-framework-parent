package com.supconit.its.transform.kafka;

import com.supconit.its.transform.entity.kafka.KafkaJsonMessage;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.supconit.its.transform.entity.KafkaJsonMessageWrapper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * kafka数据转换接口
 *
 * @author 黄珺
 * @date 2019/8/28
 **/
public interface KafkaMessageTransformService<T> {

    /**
     * 数据转换，将kafka source topic消息转换为目标对象
     *
     * @param originMessage 原始消息
     * @return V 转换后的对象，将被发送至kafka target topic
     */
    default List<KafkaJsonMessageWrapper<T>> transforms(String originMessage) {
        if (transform(originMessage) == null) {
            return null;
        }
        return Collections.singletonList(transform(originMessage));
    }

    /**
     * 数据转换，将kafka source topic消息转换为目标对象
     *
     * @param originMessage 原始消息
     * @return V 转换后的对象，将被发送至kafka target topic
     */
    default KafkaJsonMessageWrapper<T> transform(String originMessage) {
        return null;
    }


    /**
     * 返回一个只带有value并发送至默认topic的kafka消息
     *
     * @param value 转换后的对象
     * @return KafkaJsonMessageWrapper
     */
    default KafkaJsonMessageWrapper<T> getKafkaJsonMessage(T value) {
        return getKafkaJsonMessage(null, null, null, value, null);
    }

    /**
     * 返回一个只带有value并发送至指定topic的kafka消息
     *
     * @param topic      目标topic名称
     * @param value      转换后的对象
     * @param schemaName 模式名称，为空使用默认即第一个模式
     * @return KafkaJsonMessageWrapper
     */
    default KafkaJsonMessageWrapper<T> getKafkaJsonMessage(String topic, T value, String schemaName) {
        return getKafkaJsonMessage(topic, null, null, value, schemaName);
    }

    /**
     * 返回一个带有key、value并发送至指定topic的kafka消息
     *
     * @param topic      目标topic名称
     * @param key        消息key值，用于elastic的_id值
     * @param value      转换后的对象
     * @param schemaName 模式名称，为空使用默认即第一个模式
     * @return KafkaJsonMessageWrapper
     */
    default KafkaJsonMessageWrapper<T> getKafkaJsonMessage(String topic, String key, T value, String schemaName) {
        return getKafkaJsonMessage(topic, key, null, value, schemaName);
    }

    /**
     * 返回一个带有key、value、timestamp并发送至默认topic的kafka消息，
     *
     * @param key       消息key值，用于elastic的_id值
     * @param timestamp 数据时间戳，可用于elastic索引按时间切分,单位毫秒
     * @param value     转换后的对象
     * @return KafkaJsonMessageWrapper
     */
    default KafkaJsonMessageWrapper<T> getKafkaJsonMessage(String key, Long timestamp, T value) {
        return getKafkaJsonMessage(null, key, timestamp, value, null);
    }

    /**
     * 返回一个带有key、value、timestamp并发送至指定topic的kafka消息
     *
     * @param topic      目标topic名称
     * @param key        消息key值，用于elastic的_id值
     * @param timestamp  数据时间戳，可用于elastic索引按时间切分,单位毫秒
     * @param value      转换后的对象
     * @param schemaName 模式名称，为空使用默认即第一个模式
     * @return KafkaJsonMessageWrapper
     */
    default KafkaJsonMessageWrapper<T> getKafkaJsonMessage(String topic, String key, Long timestamp, T value, String schemaName) {
        final KafkaJsonMessage<T> kafkaJsonMessage = KafkaJsonMessage.newInstance(value, schemaName);
        return new KafkaJsonMessageWrapper<>(topic, timestamp, key, kafkaJsonMessage);
    }


    /**
     * 返回只带有value的kafka消息集合
     *
     * @param values 转换后的对象集合
     * @return KafkaJsonMessageWrapper
     */
    default List<KafkaJsonMessageWrapper<T>> getKafkaJsonMessageList(List<T> values) {
        return getKafkaJsonMessageList(null, null, null, values, null, null);
    }

    /**
     * 返回带有value并指定topic的kafka消息集合
     *
     * @param topic      目标topic名称
     * @param values     转换后的对象集合
     * @param schemaName 模式名称，为空使用默认即第一个模式
     * @return KafkaJsonMessageWrapper
     */
    default List<KafkaJsonMessageWrapper<T>> getKafkaJsonMessageList(String topic, List<T> values, String schemaName) {
        return getKafkaJsonMessageList(topic, null, null, values, schemaName, null);
    }

    /**
     * 返回一个带有key、value并指定topic的kafka消息
     *
     * @param topic         目标topic名称
     * @param keyColumnName 消息key值字段列名，用于elastic的_id值
     * @param values        转换后的对象集合
     * @param schemaName    模式名称，为空使用默认即第一个模式
     * @return KafkaJsonMessageWrapper
     */
    default List<KafkaJsonMessageWrapper<T>> getKafkaJsonMessage(String topic, String keyColumnName, List<T> values, String schemaName) {
        return getKafkaJsonMessageList(topic, keyColumnName, null, values, schemaName, null);
    }

    /**
     * 返回一个带有key、value、timestamp的kafka消息，发送至默认的topic
     *
     * @param keyColumnName   消息key值字段列名，用于elastic的_id值
     * @param timestampColumn 数据时间戳字段列名，可用于elastic索引按时间切分,单位毫秒
     * @param values          转换后的对象集合
     * @return KafkaJsonMessageWrapper
     */
    default List<KafkaJsonMessageWrapper<T>> getKafkaJsonMessageList(String keyColumnName, String timestampColumn, List<T> values) {
        return getKafkaJsonMessageList(null, keyColumnName, timestampColumn, values, null, null);
    }

    /**
     * 返回一个带有key、value、timestamp的kafka消息，发送至默认的topic
     *
     * @param keyColumnName   消息key值字段列名，用于elastic的_id值
     * @param timestampColumn 数据时间戳字段列名，可用于elastic索引按时间切分,单位毫秒
     * @param values          转换后的对象集合
     * @param timeFormat      时间格式
     * @return KafkaJsonMessageWrapper
     */
    default List<KafkaJsonMessageWrapper<T>> getKafkaJsonMessageList(String keyColumnName, String timestampColumn, List<T> values, String timeFormat) {
        return getKafkaJsonMessageList(null, keyColumnName, timestampColumn, values, null, timeFormat);
    }

    /**
     * 返回一个带有key、value、timestamp的kafka消息并指定topic
     *
     * @param topic           目标topic名称
     * @param keyColumnName   消息key值字段，用于elastic的_id值
     * @param timestampColumn 数据时间戳字段，可用于elastic索引按时间切分,单位毫秒
     * @param values          转换后的对象列表
     * @param schemaName      模式名称，为空使用默认即第一个模式
     * @param timeFormat      时间格式
     * @return List<KafkaJsonMessageWrapper>
     */
    default List<KafkaJsonMessageWrapper<T>> getKafkaJsonMessageList(String topic, String keyColumnName, String timestampColumn, List<T> values, String schemaName, String timeFormat) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        Field keyFiled = null;
        Field timestampFiled = null;
        SimpleDateFormat formatter = null;
        if (!StringUtils.isEmpty(keyColumnName)) {
            try {
                keyFiled = values.get(0).getClass().getDeclaredField(keyColumnName);
                keyFiled.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("错误的主键字段");
            }
        }
        if (!StringUtils.isEmpty(timestampColumn)) {
            try {
                timestampFiled = values.get(0).getClass().getDeclaredField(timestampColumn);
                timestampFiled.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("错误的时间戳字段");
            }
        }
        Field finalTimestampFiled = timestampFiled;
        Field finalKeyFiled = keyFiled;

        if (timeFormat != null) {
            formatter = new SimpleDateFormat(timeFormat, Locale.CHINESE);
        }
        SimpleDateFormat finalFormatter = formatter;

        List<KafkaJsonMessageWrapper<T>> kafkaJsonMessageWrapperList = values.stream()
                .map(value -> {
                    final KafkaJsonMessage<T> kafkaJsonMessage = KafkaJsonMessage.newInstance(value, schemaName);
                    T payload = kafkaJsonMessage.getPayload();
                    String key = null;
                    Long timestamp = null;

                    try {
                        if (finalKeyFiled != null) {
                            key = String.valueOf(finalKeyFiled.get(payload));
                        }
                        if (finalTimestampFiled != null) {
                            Object timestampObj = finalTimestampFiled.get(payload);
                            if (finalFormatter != null) {
                                try {
                                    timestamp = finalFormatter.parse((String) timestampObj).getTime();
                                } catch (ParseException e) {
                                    throw new IllegalArgumentException("无法解析时间字符串，请检查时间格式", e);
                                }
                            } else {
                                if (timestampObj instanceof Long) {
                                    Long timestampLong = (Long) timestampObj;
                                    if (timestampLong < 10000000000L) {
                                        timestampLong *= 1000;
                                    }
                                    timestamp = timestampLong;
                                } else if (timestampObj instanceof String) {
                                    String timestampStr = (String) timestampObj;
                                    if (timestampStr.length() == 10) {
                                        // 秒级精度
                                        timestampStr = timestampStr + "000";
                                    } else if (timestampStr.length() != 13) {
                                        throw new IllegalArgumentException("错误的时间戳字段[" + timestampColumn + "]");
                                    }
                                    timestamp = Long.parseLong(timestampStr);
                                } else if (timestampObj instanceof Date) {
                                    Date timestampDate = (Date) timestampObj;
                                    timestamp = timestampDate.getTime();
                                } else {
                                    throw new IllegalArgumentException("错误的时间戳字段，类型只能为[String,Long,Date]");
                                }
                            }
                        }
                    } catch (IllegalAccessException ignore) {
                    }

                    return new KafkaJsonMessageWrapper<>(topic, timestamp, key, kafkaJsonMessage);
                }).collect(Collectors.toList());

        return kafkaJsonMessageWrapperList;
    }
}
