package com.wu.framework.inner.lazy.source.mysql;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class MysqlSourceAdvancedTarget extends MysqlSourceAdvancedTargetAbstract {
    /**
     * 支持
     *
     * @param lazyDataSourceType 数据源类型
     * @return 布尔类型
     */
    @Override
    public boolean support(LazyDataSourceType lazyDataSourceType) {
        return LazyDataSourceType.MySQL.equals(lazyDataSourceType);
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
        Assert.notNull(url, "数据库连接地址不能为空");
        int firstIndex = url.indexOf("//");
        int lastIndex = url.lastIndexOf("/");
        if (firstIndex == -1 || lastIndex == -1) {
            return null;
        }
        String substring = url.substring(firstIndex, lastIndex);
        substring = substring.replace("//", "");
        String[] split = substring.split(":");
        return split[0];
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
        Assert.notNull(url, "数据库连接地址不能为空");
        String substring = url.substring(url.indexOf("//") + 2, url.lastIndexOf("/"));
        String split = substring.substring(substring.indexOf(":") + 1, substring.indexOf("/") == -1 ? substring.length() : substring.indexOf("/"));
        return Integer.parseInt(split);
    }

    /**
     * 获取url
     *
     * @param host getHost
     * @param port 端口
     */
    @Override
    public String getUrl(String host, int port) {
        String urlFormat = "jdbc:mysql://%s:%s/information_schema?allowMultiQueries=true&useUnicode=true&autoReconnect=true&useAffectedRows=true&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&databaseTerm=SCHEMA";
        return String.format(urlFormat, host, port);
    }

    /**
     * 解析url中的schema
     *
     * @param url 连接url
     *            <p>
     *            数据库连接地址  url：jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf8
     *            </p>
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
        String host = getHost(url);
        int port = this.getPort(url);
        String urlFormat = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai&databaseTerm=SCHEMA";
        return String.format(urlFormat, host, port, "information_schema");
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
        if (url.startsWith("jdbc:mysql:")) {
            return LazyDataSourceType.MySQL;
        }
        return null;
    }
}
