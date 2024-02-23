package com.wu.framework.inner.lazy.source.h2;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvanced;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvancedAbstract;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class H2SourceAdvancedTarget extends H2SourceAdvancedTargetAbstract {
    /**
     * 支持
     *
     * @param lazyDataSourceType 数据源类型
     * @return 布尔类型
     */
    @Override
    public boolean support(LazyDataSourceType lazyDataSourceType) {
        return LazyDataSourceType.H2.equals(lazyDataSourceType);
    }

    /**
     * description 通过url解析出host
     *
     * @param url 数据库连接地址  url：jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf8
     * @return String getHost 数据连接地址
     * @author Jia wei Wu
     * @date 2022/5/12 10:58 上午
     */
    @Override
    public String getHost(String url) {
        return null;
    }

    /**
     * description 通过url解析出 getPort
     *
     * @param url 数据库连接地址  url：jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf8
     * @return String getPort 数据连接端口
     * @author Jia wei Wu
     * @date 2022/5/12 10:58 上午
     */
    @Override
    public int getPort(String url) {
        return -1;
    }

    /**
     * 获取url
     *
     * @param host getHost
     * @param port 端口
     */
    @Override
    public String getUrl(String host, int port) {
        // jdbc:h2:tcp://localhost:9092/database;MODE=MYSQL;
        String urlFormat = "jdbc:h2:tcp://%s:%s/information_schema";
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
        // jdbc:h2:tcp://localhost:9092/information_schema;MODE=MYSQL
        // jdbc:h2:./h2-db:xx;MODE=MYSQL
        if (url.startsWith("jdbc:h2:tcp:")) {
            return url.substring(url.lastIndexOf("/") + 1, !url.contains(";") ? url.length() : url.indexOf(";"));
        } else {
            return url.substring(url.lastIndexOf(":") + 1, !url.contains(";") ? url.length() : url.indexOf(";"));
        }

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
        if (url.startsWith("jdbc:h2:")) {
            return LazyDataSourceType.H2;
        }
        return null;
    }
}
