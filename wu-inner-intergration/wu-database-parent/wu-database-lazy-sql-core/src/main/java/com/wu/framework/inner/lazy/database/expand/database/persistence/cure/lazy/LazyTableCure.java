package com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDDLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDQLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.AbstractCure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.Cure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.TableCure;
import com.wu.framework.inner.lazy.database.util.SqlUtils;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * description 表治愈
 *
 * @author Jia wei Wu
 * @date 2023/01/11 17:15
 */
@Slf4j
public class LazyTableCure extends AbstractCure implements TableCure, Cure {

    private final LazyBaseDDLOperation lazyBaseDDLOperation;
    private final LazyBaseDQLOperation lazyBaseDQLOperation;

    public LazyTableCure(LazyBaseDDLOperation lazyBaseDDLOperation, LazyBaseDQLOperation lazyBaseDQLOperation) {
        this.lazyBaseDDLOperation = lazyBaseDDLOperation;
        this.lazyBaseDQLOperation = lazyBaseDQLOperation;
    }

    /**
     * 判断是否支持 SQLException 异常治愈
     *
     * @param sqlException
     */
    @Override
    public boolean supportsSQLException(SQLException sqlException) {
        String sqlState = sqlException.getSQLState();
        return "42S02".equals(sqlState);
    }


    /**
     * 治愈
     *
     * @param retryTime
     * @param throwable 异常信息
     */
    @Override
    public void cureByThrowable(int retryTime, Throwable throwable) throws Throwable {
        log.warn("通过异常自动治愈表");
        String message = throwable.getMessage();

        List<String> schemaTables = SqlUtils.tablesInSql(message);
        // 解析sql 中的表、字段
        if (ObjectUtils.isEmpty(schemaTables)) {
            return;
        }
        List<String> tables = schemaTables.stream().map(schemaTable -> {
            if (schemaTable.contains(NormalUsedString.DOT)) {
                return schemaTable.split("\\.")[1];
            } else {
                return schemaTable;
            }
        }).toList();
        // 创建表
        ConcurrentMap<Class<?>, LazyTableEndpoint> tableCache = SourceFactory.getTableCache();
        List<Class<?>> cacheClazz = tableCache.values().stream().filter(lazyTableEndpoint -> {
                    String tableName = lazyTableEndpoint.getTableName();
                    if (!ObjectUtils.isEmpty(lazyTableEndpoint.getSchema())) {
                        tableName = lazyTableEndpoint.getSchema() + NormalUsedString.DOT + lazyTableEndpoint.getTableName();
                    }
                    return schemaTables.contains(tableName) || tables.contains(tableName);
                })
                .map(LazyTableEndpoint::getClazz).collect(Collectors.toList());

        if (ObjectUtils.isEmpty(cacheClazz)) {
            log.warn("无法通过错误信息治愈数据库表:{},错误信息:{}", schemaTables, message);
        }else {
            LazyDatabaseJsonMessage.localCacheEntityClass.removeAll(cacheClazz);
            lazyBaseDDLOperation.createTable(cacheClazz.toArray(new Class[0]));
            //
            log.debug("执行" + cacheClazz.stream().map(clazz->{
                SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(clazz);
                return sqlSourceClass.getLazyTableEndpoint();
            }).map(LazyTableEndpoint::getTableName).collect(Collectors.joining(",")) + "创建数据库表", throwable);

        }

    }
}
