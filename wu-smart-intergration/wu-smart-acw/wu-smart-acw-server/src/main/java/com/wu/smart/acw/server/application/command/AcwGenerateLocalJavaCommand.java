package com.wu.smart.acw.server.application.command;

import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.config.enums.WebArchitecture;
import lombok.Data;

import java.util.List;

@Data
public class AcwGenerateLocalJavaCommand {

    /**
     * 实例id
     */
    private String instanceId;
    /**
     * 数据库
     */
    private String schemaName;
    /**
     * 表
     */
    private List<String> tableList;

    /**
     * 添加前缀
     */
    private String prefix;

    /**
     * 架构 mvc、ddd
     */
    private WebArchitecture webArchitecture;

    /**
     * orm 框架
     */
    private OrmArchitecture ormArchitecture;
    /**
     * 绝对路径
     */
    private  String absolutePath;
    /**
     * 包名称
     */
    private String packageName;

}
