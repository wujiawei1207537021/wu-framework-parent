package com.wu.framework.database.generator.service;

import com.wu.framework.database.generator.config.GeneratorConfig;
import com.wu.framework.database.generator.entity.ColumnEntity;
import com.wu.framework.database.generator.entity.EncapsulatedTableInfo;
import com.wu.framework.database.generator.entity.TableEntity;
import com.wu.framework.database.generator.utils.GenUtils;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class SysGeneratorService {

    private final GeneratorConfig generatorConfig;

    private final LazyOperation lazyOperation;

    public SysGeneratorService(GeneratorConfig generatorConfig, LazyOperation lazyOperation) {
        this.generatorConfig = generatorConfig;
        this.lazyOperation = lazyOperation;
    }

    public Page queryList(String tableName, Integer size, Integer current) {
        Page page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database())   %s order by create_time desc limit %s, %s";
        if (ObjectUtils.isEmpty(tableName)) {
            tableName = "";
        } else {
            tableName = " and table_name like '%" + tableName + "%'";
        }
        List<LinkedHashMap> list = lazyOperation.executeSQL(String.format(sql, tableName, current - 1, size), LinkedHashMap.class);
        page.setRecord(list);
        return page;
    }

    public TableEntity queryTable(String tableName) {
        String SQL = "select table_name tableName, engine as engine, table_comment comments, create_time createTime from information_schema.tables where table_schema = (select database()) and table_name =%s";
        return lazyOperation.executeSQLForBean(String.format(SQL, "'"+tableName+"'"), TableEntity.class);
    }

    public List<ColumnEntity> queryColumns(String tableName) {
        String SQL = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_name =%s and table_schema = (select database()) order by ordinal_position";
        return lazyOperation.executeSQL(String.format(SQL, "'"+tableName+"'"), ColumnEntity.class);
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
            Map columnValMap = queryColumnsMap(tableName);
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
    public Map queryColumnsMap(String tableName) {
        String SQL = "select  * from %s limit 1";
        return lazyOperation.executeSQLForBean(String.format(SQL, tableName), Map.class);
    }

}
