package com.wu.framework.inner.lazy.database.expand.database.persistence.cure.lazy;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseDDLOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.AbstractCure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.Cure;
import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.SchemaCure;
import com.wu.framework.inner.lazy.database.util.SqlUtils;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * description schema 自动治愈
 *
 * @author Jia wei Wu
 * @date 2023/01/11 17:15
 */
@Slf4j
public class LazySchemaCure extends AbstractCure implements SchemaCure, Cure {

    private final LazyDataSourceProperties lazyDataSourceProperties;
    private final LazyBaseDDLOperation lazyBaseDDLOperation;

    public LazySchemaCure(LazyDataSourceProperties lazyDataSourceProperties, LazyBaseDDLOperation lazyBaseDDLOperation) {
        this.lazyDataSourceProperties = lazyDataSourceProperties;
        this.lazyBaseDDLOperation = lazyBaseDDLOperation;
    }

    /**
     * 判断是否支持 SQLException 异常治愈
     *
     * @param sqlSyntaxErrorException
     */
    @Override
    public boolean supportsSQLException(SQLException sqlSyntaxErrorException) {
        String sqlState = sqlSyntaxErrorException.getSQLState();
        // Failed to obtain JDBC Connection; nested exception is java.sql.SQLSyntaxErrorException: Unknown database 'acw33334499000122'
        // vendorCode 1049：数据库不存在
        int vendorCode = sqlSyntaxErrorException.getErrorCode();
        return "42000".equals(sqlState) && 1049 == vendorCode;
    }


    /**
     * 治愈
     *
     * @param retryTime
     * @param throwable 异常信息
     */
    @Override
    public void cureByThrowable(int retryTime, Throwable throwable) throws Throwable {
        log.warn("通过异常自动治愈schema");
        String message = throwable.getMessage();

        List<String> schemas = SqlUtils.schema(message);

        // 创建数据库
        create(schemas);
        // retry
        log.debug("执行创建数据库:" + String.join(",", schemas), throwable);
    }

    /**
     * 创建数据库
     */
    public void create(List<String> schemas) {
        // 解析数据库url信息
        // 清空当前线程使用的schema
        List<String> schemaCreates = new ArrayList<>();
        for (String schema : schemas) {
            if (LazyDatabaseJsonMessage.specialSchema.contains(schema)) {
                log.warn("拒绝创建数据:" + schema);
                break;
            }
            String format = "CREATE  DATABASE IF NOT EXISTS `%s` CHARACTER SET utf8mb4 ;";
            schemaCreates.add(String.format(format, schema));

        }
        lazyBaseDDLOperation.stringScriptRunner(schemaCreates.toArray(new String[0]));
        log.info("初始化创建数据库:【{}】", schemas);
        // 关闭数据库dataSource

    }
}
