package com.wu.framework.inner.lazy.hbase.expland.persistence.method;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import com.wu.framework.inner.lazy.hbase.expland.constant.HBaseOperationMethodCounts;
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
@RepositoryOnDifferentMethods(methodName = HBaseOperationMethodCounts.INSERT)
public class HBaseOperationInsertMethod extends HBaseOperationMethodAbstract  {

    private final Admin admin;
    private final Connection connection;

    public HBaseOperationInsertMethod(Admin admin, Connection connection) {
        super(admin, connection);
        this.admin = admin;
        this.connection = connection;
    }


    @Override
    public Object execute(Connection connection, Object... args) throws Exception {
        Object entity = args[0];
        EasySmart easySmart = LocalStorageClassAnnotation.easySmart(entity.getClass(), true);
        Table table = connection.getTable(TableName.valueOf(easySmart.tableName()));

        List<ConvertedField> convertedFields = SQLConverter.fieldNamesOnAnnotation(entity.getClass(), null);
        Put put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
        for (ConvertedField convertedField : convertedFields) {
            Field field = ReflectionUtils.findField(entity.getClass(), convertedField.getFieldName());
            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            put.addColumn(Bytes.toBytes(easySmart.columnFamily()), Bytes.toBytes(convertedField.getConvertedFieldName()),
                    Bytes.toBytes(ObjectUtils.isEmpty(fieldValue) ? "" : fieldValue.toString()));
        }
        table.put(put);
        return null;
    }

}
