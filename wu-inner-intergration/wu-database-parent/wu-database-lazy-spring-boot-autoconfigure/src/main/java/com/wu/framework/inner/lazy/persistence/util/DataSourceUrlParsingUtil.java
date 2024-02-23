package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * description 数据库Url 解析工具
 *
 * @author Jia wei Wu
 * @date 2022/5/12 10:57 上午
 * @see SourceFactory
 */
@Deprecated
public final class DataSourceUrlParsingUtil {

    /**
     * description 通过url解析出host
     *
     * @param url 数据库连接地址  url：jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf8
     * @return String getHost 数据连接地址
     * @author Jia wei Wu
     * @date 2022/5/12 10:58 上午
     */
    public static String host(String url) {
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
    public static int port(String url) {
        Assert.notNull(url, "数据库连接地址不能为空");
        String substring = url.substring(url.indexOf("//") + 2, url.lastIndexOf("/"));
        String split = substring.substring(substring.indexOf(":") + 1, substring.indexOf("/") == -1 ? substring.length() : substring.indexOf("/"));
        return Integer.parseInt(split);
    }

    /**
     * description 通过url解析出 schema
     *
     * @param url 数据库连接地址  url：jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf8
     * @return String schema 默认数据库名称
     * @author Jia wei Wu
     */
    public static String schema(String url) {
        Assert.notNull(url, "数据库连接地址不能为空");
        String substring = url.substring(url.indexOf("//") + 2, url.lastIndexOf("?") == -1 ? url.length() : url.lastIndexOf("?"));
        String[] split = substring.split("/");
        return split[1];
    }

    /**
     * 返回连接地址对应的数据库类型
     *
     * @param url 数据库连接地址
     * @return 数据库连接类型
     */
    public static LazyDataSourceType lazyDatabaseType(String url) {
        if (ObjectUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("jdbc:mysql:")) {
            return LazyDataSourceType.MySQL;
        } else if (url.startsWith("jdbc:h2:")) {
            return LazyDataSourceType.H2;
        } else if (url.startsWith("jdbc:clickhouse:")) {
            return LazyDataSourceType.CLICK_HOUSE;
        } else if (url.startsWith("jdbc:postgresql:")) {
            return LazyDataSourceType.POSTGRESQL;
        }
        return null;
    }
}
