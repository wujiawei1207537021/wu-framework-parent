package com.wu.framework.inner.lazy.source.sqlite;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvanced;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvancedAbstract;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * 处理sqlite 相关配置数据源
 * @see SqliteSourceAdvancedTargetAbstract
 */
public class SqliteSourceAdvancedTarget extends SqliteSourceAdvancedTargetAbstract {
    /**
     * 支持
     *
     * @param lazyDataSourceType 数据源类型
     * @return 布尔类型
     */
    @Override
    public boolean support(LazyDataSourceType lazyDataSourceType) {
        return LazyDataSourceType.SQLITE.equals(lazyDataSourceType);
    }

    /**
     * 获取url
     *
     * @param host getHost
     * @param port 端口
     */
    @Override
    public String getUrl(String host, int port) {
        // jdbc:sqlite::resource:dbs/sqlite.db
        String urlFormat = "jdbc:sqlite::resource:dbs/sqlite.db";
        return String.format(urlFormat, host, port);
    }

    /**
     * 解析url中的schema
     *
     * @param url 连接url
     * @return schema
     */
    @Override
    public String getUrlSchema(String url) {

        Assert.notNull(url, "数据库连接地址不能为空");
        return null;
        // jdbc:sqlite::resource:dbs/sqlite.db
//        if (url.startsWith("jdbc:sqlite")) {
//            return url.substring(url.lastIndexOf("/") + 1, !url.contains(";") ? url.length() : url.indexOf(";"));
//        } else {
//            return url.substring(url.lastIndexOf(":") + 1, !url.contains(";") ? url.length() : url.indexOf(";"));
//        }

    }

    /**
     * 获取 information_schema
     *
     * @param url 连接url
     * @return 返回 information_schema 的url连接地址
     */
    @Override
    public String getDefaultInformationSchemaUrl(String url) {
        return null;
    }

    /**
     * 通过URL解析出数据源类型
     *
     * @param url url
     * @return 数据源类型
     */
    @Override
    public LazyDataSourceType getLazyDataSourceType(String url) {
        if (ObjectUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("jdbc:sqlite")) {
            return LazyDataSourceType.SQLITE;
        }
        return null;
    }
}
