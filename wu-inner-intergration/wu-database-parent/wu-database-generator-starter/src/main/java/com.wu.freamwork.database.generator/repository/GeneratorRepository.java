package com.wu.freamwork.database.generator.repository;

import com.wu.framework.inner.database.domain.Page;
import com.wu.freamwork.database.generator.entity.ColumnEntity;
import com.wu.freamwork.database.generator.entity.TableEntity;

import java.util.List;
import java.util.Map;


public interface GeneratorRepository {
    List<Map<String, Object>> queryList(Page page);

    TableEntity queryTable(String tableName);

    List<ColumnEntity> queryColumns(String tableName);
}
