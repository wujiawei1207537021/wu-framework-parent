package com.wu.framework.inner.lazy.hbase.expland.persistence.method;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import com.wu.framework.inner.lazy.hbase.expland.analyze.HBaseClassAnalyze;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/28 10:12 下午
 */
public abstract class HBaseOperationMethodAbstract implements HBaseOperationMethod {

    private final Admin admin;
    private final Connection connection;


    public final HBaseClassAnalyze hBaseClassAnalyze = new HBaseClassAnalyze();

    protected HBaseOperationMethodAbstract(Admin admin, Connection connection) {
        this.admin = admin;
        this.connection = connection;
    }


    @Override
    public Object before(HBaseExecuteParams o) throws Exception {
        return o;
    }

    @Override
    public Object run(HBaseExecuteParams hBaseExecuteParam) throws Exception {
        // 执行前处理操作
        perfectTable(connection.getAdmin(), hBaseExecuteParam.getObjects()[0]);
        return execute(hBaseExecuteParam.getConnection(), hBaseExecuteParam.getObjects());
    }

    @Override
    public Object after(HBaseExecuteParams o) throws Exception {
        return o;
    }

    /**
     * @param
     * @return
     * @describe 是否完善表
     * @author Jia wei Wu
     * @date 2021/4/7 6:55 下午
     **/
    boolean perfectTable(Admin admin, Object source) throws IOException {
        Class clazz;
        if (source instanceof Collection) {
            clazz = ((Collection) source).iterator().next().getClass();
        } else if (source instanceof Class) {
            clazz = (Class) source;
        } else {
            clazz = source.getClass();
        }
        EasySmart easySmart = LocalStorageClassAnnotation.easySmart(clazz, true);
        if (easySmart.perfectTable()) {
            final TableName tableName = TableName.valueOf(easySmart.tableName());
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName).
                    setColumnFamily(new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(Bytes.toBytes(easySmart.columnFamily())));
            TableDescriptor build = tableDescriptorBuilder.build();
            if (admin.tableExists(tableName)) {
                admin.modifyTable(build);
            } else {
                admin.createTable(build);
            }
            return true;
        }else {
            return false;
        }
    }


}
