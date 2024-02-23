package com.wu.framework.inner.lazy.config;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.enums.DDLAuto;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.config.enums.WebArchitecture;
import com.wu.framework.inner.lazy.config.prop.SchemaScript;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasySmartFillFieldConverter;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * describe LazyOperationConfig 配置
 *
 * @author Jia wei Wu
 * @date 2022/1/23 3:46 下午
 **/
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Data
@ConfigurationProperties(prefix = LazyOperationConfig.LAZY_OPERATION_CONFIG_PREFIX)
public class LazyOperationConfig implements InitializingBean {

    public static final String LAZY_OPERATION_CONFIG_PREFIX = "spring.lazy";
    /**
     * 打印sql
     */
    public static boolean printfQuery = false;
    /**
     * 使用；lazy 框架设置schema，自动创建数据库
     * 如：默认url连接
     * spring:
     * datasource:
     * url: jdbc:mysql://127.0.0.1:3306/information_schema?allowMultiQueries=true&useUnicode=true&autoReconnect=true&useAffectedRows=true&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&databaseTerm=SCHEMA
     * username: root #有权限创建数据
     * password: wujiawei
     * driver-class-name: com.mysql.cj.jdbc.Driver
     *
     * @see com.wu.framework.inner.lazy.database.expand.database.persistence.factory.config.LazySchemaAutoCreateProcessor
     */
    private boolean enableAutoSchema = false;
    /**
     * 自动填充忽略的数据库
     */
    private List<String> ignoredDatabase = Arrays.asList("mysql", "information_schema", "performance_schema", "sys");
    /**
     * 自动填充时 填充忽略的字段 自增的、含有默认值、可以为null的字段
     */
    private List<String> fillIgnoredFields = Arrays.asList("id");
    /**
     * 自动填充时 最大条数(触发后使用多线程处理)
     */
    private Long fillMaximum = 1024L;
    /**
     * DDL  操作
     *
     * @see LazyOperationConfig#ddlConfigure
     */

    /**
     * ddlConfigure 配置
     */
    private DdlConfigure ddlConfigure = new DdlConfigure();
    /**
     * 允许逆向工程 为true时 ReverseEngineering 有效
     */
    private boolean enableReverseEngineering = false;

    /**
     * 逆向工程
     */
    private ReverseEngineering reverseEngineering = new ReverseEngineering();


    public boolean isPrintfQuery() {
        return printfQuery;
    }

    public void setPrintfQuery(boolean printfQuery) {
        LazyOperationConfig.printfQuery = printfQuery;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LazyDatabaseJsonMessage.ddlIgnoredFields = ddlConfigure.ddlIgnoredFields;
        LazyDatabaseJsonMessage.ddlIgnoredTablePrefix = ddlConfigure.ddlIgnoredTablePrefix;
        LazyDatabaseJsonMessage.ddlIgnoredTableSuffix = ddlConfigure.ddlIgnoredTableSuffix;
        LazyDatabaseJsonMessage.extraFields = ddlConfigure.enableExtraFields ? ddlConfigure.extraFields : new ArrayList<>();
    }

    /**
     * DDL 忽略属性
     */
    @Data
    public static final class DdlConfigure {

        /**
         * DDL  操作
         */
        private DDLAuto ddlAuto = DDLAuto.UPDATE;
        /**
         * DDL 完善表承诺书
         * 此操作会删除字段完全按照模型完善表⚠️
         */
        private String ddlPerfectCommitment;
        /**
         * DDL 忽略字段
         */
        private List<String> ddlIgnoredFields = Arrays.asList("id");
        /**
         * DDL 忽略表前缀
         */
        private List<String> ddlIgnoredTablePrefix = Arrays.asList();

        /**
         * DDL 忽略表后缀 天然适应Uo
         */
        private List<String> ddlIgnoredTableSuffix = Arrays.asList("Uo");
        /**
         * 是否开启额外字段
         */
        private Boolean enableExtraFields = false;
        /**
         * 额外的字段 当 enableExtraFields=true 时有效
         * <p>
         * "`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
         * "`is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',\n" +
         * "`create_time` datetime DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',\n" +
         * "`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
         * "PRIMARY KEY (`id`) USING BTREE\n";
         *
         * <p>
         * spring:
         * lazy:
         * ddl-configure:
         * ddl-ignored-table-suffix:
         * - DO
         * ddl-auto: perfect
         * extra-fields:
         * - name: isDelete
         * columnName: is_delete
         * columnType: tinyint(1)
         * comment: 是否删除
         * defaultValue: '0'
         * exist: true
         * </p>
         */
        private List<FieldLazyTableFieldEndpoint> extraFields = new ArrayList<FieldLazyTableFieldEndpoint>() {
            {
                FieldLazyTableFieldEndpoint id = new FieldLazyTableFieldEndpoint();
                id.setName("id");
                id.setColumnType("bigint(20)");
                id.setColumnName("id");
                id.setClazz(Long.class);
                id.setComment("主键");
                id.setNotNull(true);
                id.setExtra(NormalUsedString.AUTO_INCREMENT);
                id.setKey(true);
                add(id);

                FieldLazyTableFieldEndpoint isDeleted = new FieldLazyTableFieldEndpoint();
                isDeleted.setName("isDeleted");
                isDeleted.setColumnType("tinyint(1)");
                isDeleted.setDefaultValue("0");
                isDeleted.setColumnName("is_deleted");
                isDeleted.setComment("是否删除");
                add(isDeleted);

                FieldLazyTableFieldEndpoint createTime = new FieldLazyTableFieldEndpoint();
                createTime.setName("createTime");
                createTime.setColumnType("datetime");
                createTime.setDefaultValue("CURRENT_TIMESTAMP");
                createTime.setColumnName("create_time");
                createTime.setComment("创建时间");
                add(createTime);

                FieldLazyTableFieldEndpoint updateTime = new FieldLazyTableFieldEndpoint();
                updateTime.setName("updateTime");
                updateTime.setColumnType("datetime");
                updateTime.setColumnName("update_time");
                updateTime.setDefaultValue("CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
                updateTime.setComment("更新时间");
                add(updateTime);
            }
        };

        /**
         * 初始化脚本
         */
        private SchemaScript schemaScript = new SchemaScript();
        /**
         * 导出表结构
         */
        private boolean outPutTableStructure = false;
    }

    /**
     * 逆向工程
     *
     * @author : Jia wei Wu
     * @version : 1.0
     * @date : 2022/1/23 7:44 下午
     */
    @Data
    public static final class ReverseEngineering {

        /**
         * 逆向工程 允许swagger
         */
        private boolean enableSwagger = true;
        /**
         * 逆向工程 允许lazy
         *
         * @see OrmArchitecture
         */
        @Deprecated
        private boolean enableLazy = true;
        /**
         * 逆向工程 允许lazyCrud
         */
        private boolean enableLazyCrud = false;
        /**
         * 逆向工程 允许 正常的Crud 非继承实现的抽象
         */
        private boolean enableNormalCrud = true;
        /**
         * 逆向工程 允许 lombok.Data
         */
        private boolean enableLombokData = true;

        /**
         * 逆向工程 允许 lombok.Accessors
         */
        private boolean enableLombokAccessors = true;

        /**
         * 逆向工程 允许 mapstruct进行数据转换
         */
        private boolean enableMapStruct = true;

        /**
         * 逆向工程 允许 mybatis
         *
         * @see OrmArchitecture
         */
        @Deprecated
        private boolean enableMyBatis = true;
        /**
         * orm 框架
         */
        private OrmArchitecture ormArchitecture;
        /**
         * 架构 mvc、ddd
         */
        private WebArchitecture webArchitecture = WebArchitecture.WEB_MVC;

        /**
         * 是否添加schema
         */
        private boolean enableSchema = true;

        /**
         * 逆向工程 实体后缀
         */
        private String entitySuffix;
        /**
         * 文件路径 默认入：/Users/wujiawei/IdeaProjects/wu-framework-parent/wu-smart-intergration/wu-smart-acw/wu-smart-acw-server/
         */
        private String resourceFilePrefix = Objects.requireNonNull(EasySmartFillFieldConverter.class.getResource(NormalUsedString.SLASH)).getFile();
        /**
         * 逆向工程 包名
         */
        private String packageName = "com.wu.framework.inner.lazy.example";
        /**
         * API url前缀
         */
        private String apiUrlPrefix = "";


    }


}
