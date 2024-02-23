package com.wu.framework.easy.mysql.binlog.listener.ack;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.wu.framework.easy.listener.core.support.Acknowledgment;
import com.wu.framework.easy.mysql.binlog.listener.consumer.BinlogConsumerRecord;


/**
 * @author wujiawei
 */
public class MySQLBinlogAcknowledgment implements Acknowledgment {

    private final BinaryLogClient binaryLogClient;
    private final BinlogConsumerRecord record;

    public MySQLBinlogAcknowledgment(BinaryLogClient binaryLogClient, BinlogConsumerRecord record) {
        this.binaryLogClient = binaryLogClient;
        this.record = record;
    }

    @Override
    public void acknowledge() {

    }
}
