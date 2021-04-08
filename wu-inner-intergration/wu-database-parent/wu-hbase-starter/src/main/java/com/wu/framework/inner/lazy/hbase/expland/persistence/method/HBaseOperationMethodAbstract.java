package com.wu.framework.inner.lazy.hbase.expland.persistence.method;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.inner.lazy.hbase.expland.analyze.HBaseClassAnalyze;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/28 10:12 下午
 */
public abstract class HBaseOperationMethodAbstract implements HBaseOperationMethod {


    public final HBaseClassAnalyze hBaseClassAnalyze = new HBaseClassAnalyze();


    /**
     * @param
     * @return
     * @describe 是否完善表
     * @author Jia wei Wu
     * @date 2021/4/7 6:55 下午
     **/
    boolean perfectTable(Admin admin, Class clazz) throws IOException {
        EasySmart analyze = hBaseClassAnalyze.analyze(clazz);
        if (analyze.perfectTable()) {
            final TableName tableName = TableName.valueOf(analyze.tableName());
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName).
                    setColumnFamily(new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(Bytes.toBytes(analyze.columnFamily())));
            TableDescriptor build = tableDescriptorBuilder.build();
            if (admin.tableExists(tableName)) {
                admin.modifyTable(build);
            } else {
                admin.createTable(build);
            }
        }
        return false;
    }

}
