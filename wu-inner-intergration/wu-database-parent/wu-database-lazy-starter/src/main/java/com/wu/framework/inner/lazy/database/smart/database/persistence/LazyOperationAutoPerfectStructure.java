package com.wu.framework.inner.lazy.database.smart.database.persistence;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 完善表结构
 */
@Deprecated
@Slf4j
public class LazyOperationAutoPerfectStructure {

    private final LazyLambdaStream lambdaStream;
    private final LazyOperation lazyOperation;

    public LazyOperationAutoPerfectStructure(LazyLambdaStream lambdaStream, LazyOperation lazyOperation) {
        this.lambdaStream = lambdaStream;
        this.lazyOperation = lazyOperation;
    }

    /**
     * 完善表结构
     *
     * @param entityClass
     * @return
     */
    public void perfectTable(Class entityClass) {
        final ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
        final Collection<LazyTableInfo> collection = lambdaStream.of(LazyTableInfo.class).select(LazyWrappers.<LazyTableInfo>lambdaWrapper().
                eqIgnoreEmpty(LazyTableInfo::getTableSchema, classLazyTableEndpoint.getSchema()).
                eqIgnoreEmpty(LazyTableInfo::getTableName, classLazyTableEndpoint.getTableName())).collection();
        if (ObjectUtils.isEmpty(collection)) {
            // 创建表
            String createTableSQL = classLazyTableEndpoint.creatTableSQL();
            for (String sql : createTableSQL.split(NormalUsedString.SEMICOLON)) {
                lazyOperation.executeSQL(sql, Integer.class);
            }
            log.info("create table {} success", classLazyTableEndpoint.getTableName());
        } else {
            // 更新表数据
            final Collection<LazyColumn> lazyColumnCollection = lambdaStream.of(LazyColumn.class).select(LazyWrappers.<LazyColumn>lambdaWrapper().
                    eqIgnoreEmpty(LazyColumn::getTableSchema, classLazyTableEndpoint.getSchema()).
                    eqIgnoreEmpty(LazyColumn::getTableName, classLazyTableEndpoint.getTableName())).collection();

            List<FieldLazyTableFieldEndpoint> currentColumnNameList = new ArrayList<>();

            for (LazyColumn lazyColumn : lazyColumnCollection) {
                FieldLazyTableFieldEndpoint convertedField = new FieldLazyTableFieldEndpoint();
                convertedField.setColumnName(lazyColumn.getColumnName());
                convertedField.setColumnType(lazyColumn.getColumnType());
                currentColumnNameList.add(convertedField);
            }
            String alterTableSQL = classLazyTableEndpoint.alterTableSQL(currentColumnNameList);
            if (!ObjectUtils.isEmpty(alterTableSQL)) {
                final List<Integer> integers = lazyOperation.executeSQL(alterTableSQL, Integer.class);
            }
        }
    }
}
