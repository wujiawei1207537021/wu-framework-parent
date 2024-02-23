package com.wu.framework.database.generator.service;

import com.wu.framework.database.generator.config.GeneratorConfig;
import com.wu.framework.database.generator.entity.ColumnEntity;
import com.wu.framework.database.generator.entity.EncapsulatedTableInfo;
import com.wu.framework.database.generator.entity.TableEntity;
import com.wu.framework.database.generator.utils.GenUtils;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.analyze.SQLAnalyze;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

@Service
public class SysGeneratorService {

    private final GeneratorConfig generatorConfig;

    private final LazyLambdaStream lazyLambdaStream;

    public SysGeneratorService(GeneratorConfig generatorConfig, LazyLambdaStream lazyLambdaStream) {
        this.generatorConfig = generatorConfig;
        this.lazyLambdaStream = lazyLambdaStream;
    }

    public LazyPage queryList(String tableName, Integer size, Integer current) {
        LazyPage lazyPage = new LazyPage<>();
        lazyPage.setCurrent(current);
        lazyPage.setSize(size);
        String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database())   %s order by create_time orderByDesc ";
        if (ObjectUtils.isEmpty(tableName)) {
            tableName = "";
        } else {
            tableName = " and table_name like '%" + tableName + "%'";
        }
        lazyLambdaStream.selectPage(lazyPage, LinkedHashMap.class, String.format(sql, tableName));
        return lazyPage;
    }

    public TableEntity queryTable(String tableName) {
        String SQL = "select table_name tableName, engine as engine, table_comment comments, create_time createTime from information_schema.tables where table_schema = (select database()) and table_name =%s";
        return lazyLambdaStream.executeSQLForBean(String.format(SQL, "'" + tableName + "'"), TableEntity.class);
    }

    public List<ColumnEntity> queryColumns(String tableName) {
        String SQL = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_name =%s and table_schema = (select database()) order by ordinal_position";
        return lazyLambdaStream.executeSQL(String.format(SQL, "'" + tableName + "'"), ColumnEntity.class);
    }

    public byte[] generatorCode(String[] tableNames, String moduleName, String packageName, String author) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            //查询表信息
            TableEntity table = queryTable(tableName);
            //查询列信息
            List<ColumnEntity> columns = queryColumns(tableName);
            //查询一条记录信息
            Map columnValMap = queryTableColumnDefaultValue(tableName);
            EncapsulatedTableInfo encapsulatedTableInfo = new EncapsulatedTableInfo();
            encapsulatedTableInfo.setTableEntity(table);
            encapsulatedTableInfo.setColumnEntityList(columns);
            encapsulatedTableInfo.setARecord(columnValMap);
            //生成代码
            GenUtils.generatorCode(generatorConfig, encapsulatedTableInfo, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询一条记录信息
     *
     * @param tableName
     * @return
     */
    public Map queryTableColumnDefaultValue(String tableName) {
        String SQL = "select  * from %s limit 1";
        return lazyLambdaStream.executeSQLForBean(String.format(SQL, tableName), EasyHashMap.class);
    }


    /**
     * description 表字段对应查询条件
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 下午12:31
     */
    public String tableQueryConditions(String tableName) {
        List<ColumnEntity> columnEntityList = queryColumns(tableName);
        List<FieldLazyTableFieldEndpoint> convertedFieldList = columnEntityList.stream().map(columnEntity -> {
            FieldLazyTableFieldEndpoint convertedField = new FieldLazyTableFieldEndpoint();
            convertedField.setColumnName(columnEntity.getColumnName());
            convertedField.setName(CamelAndUnderLineConverter.lineToHumpField(columnEntity.getColumnName()));
            convertedField.setColumnType(columnEntity.getDataType());
            return convertedField;
        }).collect(Collectors.toList());
        return SQLAnalyze.createSelectSQL(convertedFieldList, tableName);
    }
}
