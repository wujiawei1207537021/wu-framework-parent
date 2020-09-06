package com.wu.freamwork.database.generator.repository;

import com.wu.framework.inner.database.domain.Page;
import com.wu.framework.inner.database.stereotype.Param;
import com.wu.freamwork.database.generator.entity.ColumnEntity;
import com.wu.freamwork.database.generator.entity.TableEntity;

import java.util.List;
import java.util.Map;

public interface MySQLGeneratorDao {

    List<Map<String, Object>> queryList(Page page, @Param("tableName") String tableName);

    TableEntity queryTable(@Param("tableName") String tableName);

    List<ColumnEntity> queryColumns(@Param("tableName") String tableName);

    /**
     * 查询一条记录信息
     *
     * @param tableName
     * @return
     */
    Map queryColumnsMap(@Param("!tableName") String tableName);
}
