package com.wu.framework.database.generator.service;

import com.wu.framework.database.generator.config.GeneratorConfig;
import com.wu.framework.database.generator.entity.ColumnEntity;
import com.wu.framework.database.generator.entity.EncapsulatedTableInfo;
import com.wu.framework.database.generator.entity.TableEntity;
import com.wu.framework.database.generator.repository.MySQLGeneratorDao;
import com.wu.framework.database.generator.utils.GenUtils;
import com.wu.framework.inner.database.domain.Page;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class SysGeneratorService {

    private final MySQLGeneratorDao mySQLGeneratorDao;
    private final GeneratorConfig generatorConfig;

    public SysGeneratorService(MySQLGeneratorDao mySQLGeneratorDao, GeneratorConfig generatorConfig) {
        this.mySQLGeneratorDao = mySQLGeneratorDao;
        this.generatorConfig = generatorConfig;
    }

    public Page queryList(String tableName, Integer size, Integer current) {
        System.out.println(mySQLGeneratorDao.toString());
        Page page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        List<Map<String, Object>> list = mySQLGeneratorDao.queryList(page, tableName);
        page.setRecord(list);
        return page;
    }

    public TableEntity queryTable(String tableName) {
        return mySQLGeneratorDao.queryTable(tableName);
    }

    public List<ColumnEntity> queryColumns(String tableName) {
        return mySQLGeneratorDao.queryColumns(tableName);
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
        return mySQLGeneratorDao.queryColumnsMap(tableName);
    }

}
