//package com.wu.framework.easy.temple.listener;
//
//import com.github.shyiko.mysql.binlog.BinaryLogClient;
//import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
//import com.github.shyiko.mysql.binlog.event.EventData;
//import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
//import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
//import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
//import com.wu.framework.easy.listener.core.support.Acknowledgment;
//import com.wu.framework.easy.listener.stereotype.EasyListener;
//import com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListener;
//import com.wu.framework.easy.listener.stereotype.mysql.EasyMySQLListener;
//import com.wu.framework.easy.mysql.listener.config.GeneralLog;
//import com.wu.framework.easy.temple.domain.EasyListenerBo;
//import com.wu.framework.inner.layer.util.DataTransformUntil;
//import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.List;
//
///**
// * describe :
// *
// * @author : Jia wei Wu
// * @version 1.0
// * @date : 2021/10/16 11:42 下午
// */
//@Component
//public class TempListener {
//
//    private final LazySqlOperation operation;
//
//    public TempListener(LazySqlOperation operation) {
//        this.operation = operation;
//    }
//
//    public static void main(String[] args) {
//        BinaryLogClient logClient = new BinaryLogClient(
//                "127.0.0.1", 3306, "temp", "root", "wujiawei"
//        );
//        logClient.registerEventListener(event -> {
//            EventData data = event.getData();
//            // 如果日志是更新记录
//            if (data instanceof UpdateRowsEventData) {
//                long tableId = ((UpdateRowsEventData) data).getTableId();
//                System.out.println("update: " + tableId);
//            } else if (data instanceof WriteRowsEventData) {
//                long tableId = ((WriteRowsEventData) data).getTableId();
//                // 如果日志是写操作
//                System.out.println("write: " + tableId);
//            } else if (data instanceof DeleteRowsEventData) {
//                long tableId = ((DeleteRowsEventData) data).getTableId();
//                // 如果日志是删除操作
//                System.out.println("delete: " + tableId);
//            }
//        });
//
//        // 开始监听
//        try {
//            logClient.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @PostConstruct
//    public void init() {
//        for (int i = 0; i < 1; i++) {
//            operation.smartUpsert(DataTransformUntil.simulationBean(EasyListenerBo.class));
//        }
//    }
//
//    /**
//     * describe 数据监听
//     *
//     * @param
//     * @return
//     * @author Jia wei Wu
//     * @date 2021/10/16 11:43 下午
//     **/
//    @EasyListener(topics = "easy_listener", consumer = "EasyListener", kafkaListener = @EasyKafkaListener(topics = "", groupId = "wujiawei"),
//            mySQLListener = @EasyMySQLListener(statement = "select * from easy_listener", pattern = EasyMySQLListener.Pattern.STATEMENT))
//    public void listener(ConsumerRecord<String, List<GeneralLog>> consumerRecord, Acknowledgment acknowledgment) {
//        for (GeneralLog payload : consumerRecord.payload()) {
//            final byte[] arguments = payload.getArgument();
//            final String s = new String(arguments);
//            if (s.contains("show")) {
//                System.out.println(s);
//            }
//
//        }
//
//        acknowledgment.acknowledge();
//
//    }
//
//
//}
