package com.wu.framework.database.generator.repository;

import com.wu.framework.database.generator.entity.ColumnEntity;
import com.wu.framework.database.generator.entity.TableEntity;
import com.wu.framework.inner.database.domain.Page;

import java.util.List;
import java.util.Map;


public interface GeneratorRepository {
    List<Map<String, Object>> queryList(Page page);

    TableEntity queryTable(String tableName);

    List<ColumnEntity> queryColumns(String tableName);
}
