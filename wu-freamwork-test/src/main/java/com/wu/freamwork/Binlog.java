package com.wu.freamwork;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/11 23:37
 */
public class Binlog {

    public static void main(String[] args) {
        BinaryLogClient logClient = new BinaryLogClient(
                "127.0.0.1", 3306, "temp", "root", "wujiawei"
        );
        logClient.registerEventListener(event -> {
            EventData data = event.getData();
            // 如果日志是更新记录
            if (data instanceof UpdateRowsEventData) {
                long tableId = ((UpdateRowsEventData) data).getTableId();
                System.out.println("update: " + tableId);
            } else if (data instanceof WriteRowsEventData) {
                long tableId = ((WriteRowsEventData) data).getTableId();
                // 如果日志是写操作
                System.out.println("write: " + tableId);
            } else if (data instanceof DeleteRowsEventData) {
                long tableId = ((DeleteRowsEventData) data).getTableId();
                // 如果日志是删除操作
                System.out.println("delete: " + tableId);
            }
        });

        // 开始监听
        try {
            logClient.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
