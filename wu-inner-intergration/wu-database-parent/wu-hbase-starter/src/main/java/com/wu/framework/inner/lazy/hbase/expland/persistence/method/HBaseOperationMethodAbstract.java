package com.wu.framework.inner.lazy.hbase.expland.persistence.method;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import com.wu.framework.inner.lazy.hbase.expland.analyze.HBaseClassAnalyze;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/28 10:12 下午
 */
public abstract class HBaseOperationMethodAbstract implements HBaseOperationMethod {


//    public abstract Object run(Admin admin,Object[] args);

    public final HBaseClassAnalyze hBaseClassAnalyze = new HBaseClassAnalyze();

    @Override
    public Object before(Object o) {
        return null;
    }

    @Override
    public Object run(Object o) {
        return null;
    }

    @Override
    public Object after(Object o) {
        return null;
    }

    /**
     * @param
     * @return
     * @describe 是否完善表
     * @author Jia wei Wu
     * @date 2021/4/7 6:55 下午
     **/
    boolean perfectTable(Admin admin, Class clazz) throws IOException {
         EasySmart analyze = hBaseClassAnalyze.analyze(clazz);
         if(analyze.perfectTable()){
             final TableName tableName = TableName.valueOf(analyze.tableName());

             TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName).
                     setColumnFamily(new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(Bytes.toBytes(analyze.columnFamily())));
             TableDescriptor build = tableDescriptorBuilder.build();

             if(admin.tableExists(tableName)){
                 admin.modifyTable(build);
             }else {
                 admin.createTable(build);
             }
         }
        return false;
    }

}
