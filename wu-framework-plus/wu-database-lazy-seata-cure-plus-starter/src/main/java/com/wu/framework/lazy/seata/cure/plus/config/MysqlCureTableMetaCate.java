package com.wu.framework.lazy.seata.cure.plus.config;

import com.wu.framework.inner.layer.util.SpringContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.CureAdapter;
import io.seata.common.loader.LoadLevel;
import io.seata.rm.datasource.sql.struct.TableMeta;
import io.seata.rm.datasource.sql.struct.cache.MysqlTableMetaCache;
import io.seata.sqlparser.util.JdbcConstants;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * description seata 获取表信息治愈
 *
 * @author 吴佳伟
 * @date 2023/08/08 12:49
 */
@LoadLevel(name = JdbcConstants.MYSQL)
public class MysqlCureTableMetaCate extends MysqlTableMetaCache {

    @Override
    protected TableMeta fetchSchema(Connection connection, String tableName) throws SQLException {
        try {
            return super.fetchSchema(connection, tableName);
        } catch (SQLException sqlEx) {
            // 治愈字段
            try {
                getCureAdapter().cureOne(sqlEx);
                return fetchSchema(connection, tableName, false, sqlEx);
            } catch (Throwable e) {
                throw sqlEx;
            }

        } catch (Exception e) {
            throw new SQLException(String.format("Failed to fetch schema of %s", tableName), e);
        }
    }

    /**
     * 被迫治愈一次
     *
     * @param connection
     * @param tableName
     * @param retry
     * @param sqlEx
     * @return
     * @throws SQLException
     */
    protected TableMeta fetchSchema(Connection connection, String tableName, Boolean retry, SQLException sqlEx) throws SQLException {
        if (retry) {
            return super.fetchSchema(connection, tableName);
        } else {
            throw sqlEx;
        }
    }

    public CureAdapter getCureAdapter() {
        CureAdapter cureAdapter = SpringContextHolder.getBean(CureAdapter.class);
        if(cureAdapter==null){
            throw new  IllegalArgumentException("无法获取治愈适配器");
        }
        return cureAdapter;
    }
}
