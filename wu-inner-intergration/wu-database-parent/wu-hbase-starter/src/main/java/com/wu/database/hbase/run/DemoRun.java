package com.wu.database.hbase.run;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/15 8:25 下午
 */
@Controller
public class DemoRun {
    private final Admin admin;
    private final Connection connection;

    public DemoRun(Admin admin, Connection connection) {
        this.admin = admin;
        this.connection = connection;
    }

    @PostConstruct
    public void run() throws Exception {
        HTableDescriptor[] hTableDescriptors = admin.listTables();
//        HTableDescriptor desc = new HTableDescriptor("easy1");
//        desc.addFamily(new HColumnDescriptor("cf1"));
//        admin.createTable(desc);
        Table easy = connection.getTable(TableName.valueOf("easy12"));
//        System.out.println(admin.tableExists(TableName.valueOf("easy")));
//        admin.disableTable(TableName.valueOf("easy1"));
//        admin.deleteTable(TableName.valueOf("easy1"));

        System.out.println(hTableDescriptors);
    }


}