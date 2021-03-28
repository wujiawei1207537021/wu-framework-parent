package com.wu.database.hbase.run;

import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.persistence.HBaseOperation;
import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.proxy.HBaseOperationProxy;
import com.wu.framework.easy.stereotype.web.EasyController;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.boot.CommandLineRunner;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/15 8:25 下午
 */
@EasyController
public class DemoRun implements CommandLineRunner {
    private final Admin admin;
    private final Connection connection;
    private final HBaseOperationProxy hBaseOperationProxy;
    private final HBaseOperation hBaseOperation;

    public DemoRun(Admin admin, Connection connection, HBaseOperationProxy hBaseOperationProxy, HBaseOperation hBaseOperation) {
        this.admin = admin;
        this.connection = connection;
        this.hBaseOperationProxy = hBaseOperationProxy;
        this.hBaseOperation = hBaseOperation;
    }


    public void run() throws Exception {
        hBaseOperation.insert();
        HTableDescriptor[] hTableDescriptors = admin.listTables();
//        HTableDescriptor desc = new HTableDescriptor("easy1");
//        desc.addFamily(new HColumnDescriptor("cf1"));
//        admin.createTable(desc);
        Table easy = connection.getTable(TableName.valueOf("easy12"));
//        System.out.println(admin.tableExists(TableName.valueOf("easy")));
//        admin.disableTable(TableName.valueOf("easy1"));
//        admin.deleteTable(TableName.valueOf("easy1"));
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("easy12"));

        hTableDescriptor.addFamily(new HColumnDescriptor("A1"));
        hTableDescriptor.addFamily(new HColumnDescriptor("A2"));
        hTableDescriptor.addFamily(new HColumnDescriptor("A3"));
        admin.createTable(hTableDescriptor);

        System.out.println(hTableDescriptors);
    }


    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        run();
    }
}