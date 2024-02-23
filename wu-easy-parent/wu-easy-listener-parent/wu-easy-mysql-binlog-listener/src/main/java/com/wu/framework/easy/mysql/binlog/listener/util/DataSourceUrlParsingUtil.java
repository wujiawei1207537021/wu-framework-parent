package com.wu.framework.easy.mysql.binlog.listener.util;

import org.springframework.util.Assert;

/**
 * description 数据库Url 解析工具
 *
 * @author Jia wei Wu
 * @date 2022/5/12 10:57 上午
 * @see com.wu.framework.inner.lazy.persistence.util.DataSourceUrlParsingUtil
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
        String substring = url.substring(url.indexOf("//"), url.lastIndexOf("/"));
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
        String split = substring.substring(substring.indexOf(":") + 1, substring.indexOf("/"));
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
        String substring = url.substring(url.indexOf("//") + 2, url.lastIndexOf("?"));
        String[] split = substring.split("/");
        return split[1];
    }

}
