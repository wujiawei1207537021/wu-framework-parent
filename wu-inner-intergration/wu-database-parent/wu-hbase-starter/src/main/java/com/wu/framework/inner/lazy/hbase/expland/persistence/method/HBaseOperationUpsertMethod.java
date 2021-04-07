package com.wu.framework.inner.lazy.hbase.expland.persistence.method;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import com.wu.framework.inner.layer.stereotype.LayerField;
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
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/29 7:19 下午
 */
@RepositoryOnDifferentMethods(methodName = HBaseOperationMethodCounts.UPSERT)
public class HBaseOperationUpsertMethod extends HBaseOperationMethodAbstract {

    private final Admin admin;

    public HBaseOperationUpsertMethod(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Object execute(Connection connection, Object[] args) throws Exception {
        Object entity = args[0];
        EasySmart easySmart = LocalStorageClassAnnotation.easySmart(entity.getClass(), true);
        Table table = connection.getTable(TableName.valueOf(easySmart.tableName()));
        perfectTable(admin, entity.getClass());
        List<ConvertedField> convertedFields = SQLConverter.fieldNamesOnAnnotation(entity.getClass(), null);

        String hBaseRow = UUID.randomUUID().toString();
        List<ConvertedField> uniqueConvertedFieldList = convertedFields.stream().
                filter(convertedField -> convertedField.getFieldIndexType().equals(LayerField.LayerFieldType.UNIQUE)).collect(Collectors.toList());

        if (!ObjectUtils.isEmpty(uniqueConvertedFieldList)) {
            hBaseRow = uniqueConvertedFieldList.stream().map(convertedField -> {
                Field field = ReflectionUtils.findField(entity.getClass(), convertedField.getFieldName());
                field.setAccessible(true);
                Object o = "";
                try {
                    o = field.get(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return o.toString();
            }).collect(Collectors.joining("-"));
        } else {
            System.err.println(String.format("the uniqueness field cannot be found, and the current result cannot be updated in class %s ", entity.getClass()));
        }

        Put put = new Put(Bytes.toBytes(hBaseRow));
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
