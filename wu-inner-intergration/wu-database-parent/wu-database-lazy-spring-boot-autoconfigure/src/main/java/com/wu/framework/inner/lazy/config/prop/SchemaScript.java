package com.wu.framework.inner.lazy.config.prop;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库sql 脚本
 */
@Data
public final class SchemaScript {
    /**
     * 对应resources 下的文件地址例如 new ClassPathResource("schema.sql");
     */
    private List<String> classPathResources = new ArrayList<>();
    /**
     * 对应当前服务器下文件地址 例如 new FileSystemResource("/Users/xxx/schema.sql");
     */
    private List<String> fileSystemResources = new ArrayList<>();
    /**
     * 对应网络服务器下文件地址 例如 new UrlResource("http://ip:port/tenant-sql-173.sql");
     */
    private List<String> urlResources = new ArrayList<>();
}