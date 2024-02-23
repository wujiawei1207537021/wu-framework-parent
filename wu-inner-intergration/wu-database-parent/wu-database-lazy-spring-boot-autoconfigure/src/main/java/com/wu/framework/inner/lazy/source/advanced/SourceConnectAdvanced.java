package com.wu.framework.inner.lazy.source.advanced;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;

/**
 * 数据元撇脂连接适配者
 * @see SourceAdvanced
 */
public interface SourceConnectAdvanced {

    /**
     * 支持
     *
     * @param lazyDataSourceType 数据源类型
     * @return 布尔类型
     */
    boolean support(LazyDataSourceType lazyDataSourceType);

    /**
     * description 通过url解析出host
     *
     * @param url 数据库连接地址  url：jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf8
     * @return String getHost 数据连接地址
     * @author Jia wei Wu
     * @date 2022/5/12 10:58 上午
     */
     String getHost(String url);
    /**
     * description 通过url解析出 getPort
     *
     * @param url 数据库连接地址  url：jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf8
     * @return String getPort 数据连接端口
     * @author Jia wei Wu
     * @date 2022/5/12 10:58 上午
     */
    int getPort(String url);
    /**
     * 获取url
     *
     * @param host getHost
     * @param port 端口
     */
    String getUrl(String host, int port);


    /**
     * 解析url中的schema
     *
     * @param url 连接url
     * @return schema
     */
    String getUrlSchema(String url);

    /**
     * 获取 information_schema
     * @param url 连接url
     * @return 返回 information_schema 的url连接地址
     */
    String getDefaultInformationSchemaUrl(String url);

    /**
     * 通过URL解析出数据源类型
     *
     * @param url url
     * @return 数据源类型
     */
    LazyDataSourceType getLazyDataSourceType(String url);


}
