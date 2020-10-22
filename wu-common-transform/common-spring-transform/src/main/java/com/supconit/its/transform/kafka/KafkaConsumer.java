package com.supconit.its.transform.kafka;


import com.supconit.its.transform.config.DataProcessConfig;
import com.supconit.its.transform.entity.KafkaJsonMessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 黄珺
 * @date 2019/8/28
 **/
@Component
@Slf4j
public class KafkaConsumer {

    /**
     * 日志发送间隔 1分钟
     */
    private static final int LOG_SEND_INTERVAL = 60 * 1000;

    private final List<KafkaMessageTransformService> transformServiceList;

    private final KafkaProducer kafkaProducer;

//    private final ElasticLogProducer elasticLogProducer;

    private final DataProcessConfig dataProcessConfig;

    /**
     * kafka消费只在一个线程中，无需考虑并发
     */
    private long startTime = 0;
    private long processDataSize = 0;

    @Autowired
    public KafkaConsumer(List<KafkaMessageTransformService> transformServiceList, KafkaProducer kafkaProducer, DataProcessConfig dataProcessConfig) {
        this.transformServiceList = transformServiceList;
        this.kafkaProducer = kafkaProducer;
//        this.elasticLogProducer = elasticLogProducer;
        this.dataProcessConfig = dataProcessConfig;
    }

    @KafkaListener(topics = "${data-process.kafka-source-topic}", groupId = "${data-process.consumer-group-id}")
    public void processMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        log.debug("接收到消息{}", record.value());
        try {
            for (KafkaMessageTransformService transformService : transformServiceList) {
                List<KafkaJsonMessageWrapper> messageWrapperList = transformService.transforms(record.value());
                if (messageWrapperList == null) {
                    log.debug("忽略的消息");
                    processDataSize++;
                    ack.acknowledge();
                    return;
                }

                log.debug("成功转换消息{}", messageWrapperList);
                for (KafkaJsonMessageWrapper messageWrapper : messageWrapperList) {
                    kafkaProducer.send(messageWrapper).get(5, TimeUnit.SECONDS);
                    processDataSize++;
                    log.debug("成功发送消息");
                    ack.acknowledge();
                }

                // 发送转换日志
                if (System.currentTimeMillis() - startTime >= LOG_SEND_INTERVAL) {
                    sendSuccessLog();
                }
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("消息发送失败", e);
            sendWarnLog(e);
        } catch (Exception e) {
            log.error("消息转换失败", e);
            sendFailLog(e);
        }
    }

    private void sendSuccessLog() {
        if (processDataSize != 0) {
//            elasticLogProducer.sendLog(transformInstance(DataAccessLogMessage.Status.SUCCESS, startTime, processDataSize, null));
            processDataSize = 0;
            startTime = System.currentTimeMillis();
        }
    }

    private void sendWarnLog(Exception e) {
        sendSuccessLog();
//        elasticLogProducer.sendLog(transformInstance(DataAccessLogMessage.Status.WARN, startTime, 1,
//                "数据已转换完成，但发送失败：" + e.toString()));
    }

    private void sendFailLog(Exception e) {
        sendSuccessLog();
//        elasticLogProducer.sendLog(transformInstance(DataAccessLogMessage.Status.FAIL, startTime, 1,
//                "数据转换失败：" + e.toString()));
    }

//    private DataAccessLogMessage transformInstance(DataAccessLogMessage.Status status, long startTime, long dataSize, String detail) {
//        Date currentData = new Date();
//        DataAccessLogMessage logMessage = new DataAccessLogMessage();
//        logMessage.setCode(dataProcessConfig.getTaskName());
//        logMessage.setStage(DataAccessLogMessage.Stage.TRANSFORM.name());
//        logMessage.setStatus(status.name());
//        logMessage.setStartTime(new Date(startTime));
//        logMessage.setEndTime(currentData);
//        logMessage.setLogTime(currentData);
//        logMessage.setDataSize(dataSize);
//        logMessage.setDetail(detail);
//        return logMessage;
//    }
}
