package com.wu.framework.easy.temple.listener;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.support.Acknowledgment;
import com.wu.framework.easy.listener.stereotype.EasyListener;
import com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListener;
import com.wu.framework.easy.listener.stereotype.mysql.EasyMySQLListener;
import com.wu.framework.easy.mysql.listener.config.GeneralLog;
import com.wu.framework.easy.temple.domain.EasyListenerBo;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/16 11:42 下午
 */
@Component
public class TempListener {

    private final LazyOperation operation;

    public TempListener(LazyOperation operation) {
        this.operation = operation;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 1; i++) {
            operation.smartUpsert(DataTransformUntil.simulationBean(EasyListenerBo.class));
        }
    }

    /**
     * describe 数据监听
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/10/16 11:43 下午
     **/
    @EasyListener(topics = "easy_listener", consumer = "EasyListener", kafkaListener = @EasyKafkaListener(topics = "", groupId = "wujiawei"),
            mySQLListener = @EasyMySQLListener(statement = "select * from easy_listener"))
    public void listener(ConsumerRecord<String, List<GeneralLog>> consumerRecord, Acknowledgment acknowledgment) {
        for (GeneralLog payload : consumerRecord.payload()) {
            final byte[] arguments = payload.getArgument();
            final String s = new String(arguments);
            if (s.contains("show")) {
                System.out.println(s);
            }

        }

        acknowledgment.acknowledge();

    }


}
