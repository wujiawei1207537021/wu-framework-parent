package com.wu.framework.inner.lazy.hbase.expland.persistence.method;


import com.wu.framework.inner.layer.stereotype.analyze.AnalyzeField;
import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.hbase.expland.constant.HBaseOperationMethodCounts;
import com.wu.framework.inner.lazy.hbase.expland.persistence.stereotype.HBaseTable;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/29 7:19 下午
 */
@ProxyStrategicApproach(methodName = HBaseOperationMethodCounts.INSERT)
public class HBaseOperationInsertMethodAdapter extends HBaseOperationMethodAbstractAdapter {

    private final Admin admin;
    private final Connection connection;

    public HBaseOperationInsertMethodAdapter(Admin admin, Connection connection) {
        super(admin, connection);
        this.admin = admin;
        this.connection = connection;
    }


    @Override
    public Object execute(Connection connection, Object... args) throws Exception {
        Object entity = args[0];
        HBaseTable easySmart = analyzeClass(entity.getClass());
        Table table = connection.getTable(TableName.valueOf(easySmart.tableName()));

        List<AnalyzeField> analyzeFieldList = analyzeField(entity.getClass());
        Put put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
        for (AnalyzeField analyzeField : analyzeFieldList) {
            Field field = ReflectionUtils.findField(entity.getClass(), analyzeField.getFieldName());
            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            put.addColumn(Bytes.toBytes(easySmart.columnFamily()), Bytes.toBytes(analyzeField.getConvertedFieldName()),
                    Bytes.toBytes(ObjectUtils.isEmpty(fieldValue) ? "" : fieldValue.toString()));
        }
        table.put(put);
        return null;
    }

}
