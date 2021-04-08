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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/29 7:19 下午
 */
@RepositoryOnDifferentMethods(methodName = HBaseOperationMethodCounts.INSERT_LIST)
public class HBaseOperationInsertListMethod extends HBaseOperationMethodAbstract {

    private final Admin admin;

    public HBaseOperationInsertListMethod(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Object execute(Connection connection, Object[] args) throws Exception {
        Object entity = args[0];
        
        Class entityClass;
        if(entity instanceof List){
            entityClass=((List) entity).get(0).getClass();
            // 处理数据
            EasySmart easySmart = LocalStorageClassAnnotation.easySmart(entityClass, true);
            perfectTable(admin,entityClass);
            Table table = connection.getTable(TableName.valueOf(easySmart.tableName()));

            List<ConvertedField> convertedFields = SQLConverter.fieldNamesOnAnnotation(entityClass, null);
            List<Put> putList=new ArrayList<>();
            for (Object o : ((List) entity)) {
                Put put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
                for (ConvertedField convertedField : convertedFields) {
                    Field field = ReflectionUtils.findField(entityClass, convertedField.getFieldName());
                    assert field != null;
                    field.setAccessible(true);
                    Object fieldValue = field.get(o);
                    put.addColumn(Bytes.toBytes(easySmart.columnFamily()), Bytes.toBytes(convertedField.getConvertedFieldName()),
                            Bytes.toBytes(ObjectUtils.isEmpty(fieldValue) ? "" : fieldValue.toString()));
                }
                putList.add(put);
            }
            table.put(putList);

        }else {
            entityClass=entity.getClass();
            // 处理数据
            EasySmart easySmart = LocalStorageClassAnnotation.easySmart(entityClass, true);
            perfectTable(admin,entityClass);
            Table table = connection.getTable(TableName.valueOf(easySmart.tableName()));

            List<ConvertedField> convertedFields = SQLConverter.fieldNamesOnAnnotation(entityClass, null);
            Put put = new Put(Bytes.toBytes(UUID.randomUUID().toString()));
            for (ConvertedField convertedField : convertedFields) {
                Field field = ReflectionUtils.findField(entityClass, convertedField.getFieldName());
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                put.addColumn(Bytes.toBytes(easySmart.columnFamily()), Bytes.toBytes(convertedField.getConvertedFieldName()),
                        Bytes.toBytes(ObjectUtils.isEmpty(fieldValue) ? "" : fieldValue.toString()));
            }
            table.put(put);
        }

        return null;
    }


}
