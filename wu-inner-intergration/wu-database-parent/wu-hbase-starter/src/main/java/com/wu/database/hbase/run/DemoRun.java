package com.wu.database.hbase.run;

import com.wu.framework.inner.lazy.hbase.expland.bo.HBaseUserBo;
import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
import com.wu.framework.inner.lazy.hbase.expland.persistence.proxy.HBaseOperationProxy;
import com.wu.framework.easy.stereotype.web.EasyController;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

import javax.annotation.PostConstruct;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/15 8:25 下午
 */
@EasyController
public class DemoRun  {
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

    @PostConstruct
    public void run() throws Exception {
        hBaseOperation.insert(new HBaseUserBo().setUserName("hbase").setAge("12").setSex("男"));
        HTableDescriptor[] hTableDescriptors = admin.listTables();
//        HTableDescriptor desc = new HTableDescriptor("easy1");
//        desc.addFamily(new HColumnDescriptor("cf1"));
//        admin.createTable(desc);
        Table easy = connection.getTable(TableName.valueOf("easy12"));
//        System.out.println(admin.tableExists(TableName.valueOf("easy")));
//        admin.disableTable(TableName.valueOf("easy1"));
//        admin.deleteTable(TableName.valueOf("easy1"));
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("hbase_user"));

        hTableDescriptor.addFamily(new HColumnDescriptor("columnFamily"));
//        hTableDescriptor.addFamily(new HColumnDescriptor("A2"));
//        hTableDescriptor.addFamily(new HColumnDescriptor("A3"));
        admin.createTable(hTableDescriptor);

        System.out.println(hTableDescriptors);
    }


}