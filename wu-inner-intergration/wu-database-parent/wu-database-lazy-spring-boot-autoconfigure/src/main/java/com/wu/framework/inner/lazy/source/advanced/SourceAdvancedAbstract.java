package com.wu.framework.inner.lazy.source.advanced;

public abstract class SourceAdvancedAbstract implements SourceAdvanced {

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
        throw new IllegalArgumentException(url);
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
        throw new IllegalArgumentException(url);
    }
}
