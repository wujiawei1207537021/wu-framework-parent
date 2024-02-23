package com.wu.framework.inner.lazy.config;

import com.wu.framework.inner.lazy.config.enums.DDLAuto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * describe LazyOperationConfig 配置
 *
 * @author Jia wei Wu
 * @date 2022/1/23 3:46 下午
 **/
@Data
@ConfigurationProperties(prefix = LazyOperationConfig.LAZY_OPERATION_CONFIG_PREFIX)
public class LazyOperationConfig {

    public static final String LAZY_OPERATION_CONFIG_PREFIX = "spring.lazy";
    /**
     * 打印sql
     */
    public static boolean printfQuery = false;
    /**
     * 忽略的数据库
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
     */
    private DDLAuto ddlAuto = DDLAuto.PERFECT;

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
         */
        private boolean enableLazy = true;
        /**
         * 逆向工程 允许 lombok.Data
         */
        private boolean enableLombokData = true;

        /**
         * 逆向工程 允许 lombok.Accessors
         */
        private boolean enableLombokAccessors = true;


        /**
         * 逆向工程 包名
         */
        private String packageName = "com.wu.framework.inner.lazy.example";

        /**
         * 允许spring-mvc
         */
        private ReverseEngineeringMvc reverseEngineeringMvc=new ReverseEngineeringMvc();

    }

    /**
     * 允许spring-mvc 逆向工程
     */
    @Data
    public static final class ReverseEngineeringMvc{
        /**
         * 允许 基于lazyOperation的mvc项目
         */
        private boolean enableLazyOperationMvc = false;



    }
}
