package com.wu.framework.inner.lazy.source.postgresql;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvanced;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvancedAbstract;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * @see PostgreSQLSourceAdvancedTargetAbstract
 */
public class PostgreSQLSourceAdvancedTarget extends PostgreSQLSourceAdvancedTargetAbstract {
    /**
     * 支持
     *
     * @param lazyDataSourceType 数据源类型
     * @return 布尔类型
     */
    @Override
    public boolean support(LazyDataSourceType lazyDataSourceType) {
        return LazyDataSourceType.POSTGRESQL.equals(lazyDataSourceType);
    }

    /**
     * 获取url
     *
     * @param host getHost
     * @param port 端口
     */
    @Override
    public String getUrl(String host, int port) {
        // jdbc:postgresql://localhost:5432/postgres?useSSL=false
        String urlFormat = "jdbc:postgresql://%s:%s/postgres";
        return String.format(urlFormat, host, port);
    }

    /**
     * 解析url中的schema
     *
     * @param url 连接url jdbc:postgresql://localhost:5432/postgres?useSSL=false
     * @return schema
     */
    @Override
    public String getUrlSchema(String url) {
        Assert.notNull(url, "数据库连接地址不能为空");
        String substring = url.substring(url.indexOf("//") + 2, url.lastIndexOf("?") == -1 ? url.length() : url.lastIndexOf("?"));
        String[] split = substring.split("/");
        return split[1];
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
        if (url.startsWith("jdbc:postgresql:")) {
            return LazyDataSourceType.POSTGRESQL;
        }
        return null;
    }
}
