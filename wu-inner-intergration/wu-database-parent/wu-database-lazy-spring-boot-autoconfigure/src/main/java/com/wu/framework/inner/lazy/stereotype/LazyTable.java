package com.wu.framework.inner.lazy.stereotype;

import com.wu.framework.inner.layer.data.LayerData;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description LazyTable 灵性
 *
 * @author Jia wei Wu
 * @date 2020/12/14 下午12:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerClass
@LayerData
public @interface LazyTable {


    /**
     * 表引擎
     *
     * @return
     */
    Engine engine() default Engine.InnoDB;

    /**
     * 表名
     *
     * @return
     */
    @AliasFor(attribute = "name", annotation = LayerClass.class)
    String tableName() default "";

    /**
     * 完善表
     *
     * @return
     */
    boolean perfectTable() default false;

    /**
     * 数据下钻
     * the field use Annotation with {@link LayerData#dataDrillDown()}
     */
    @AliasFor(attribute = "dataDrillDown", annotation = LayerData.class)
    boolean dataDrillDown() default false;

    /**
     * 表注释
     *
     * @return String
     */
    String comment() default "";


    /**
     * 数据库名 schema
     *
     * @return String
     */
    String schema() default "";


    /**
     * 智能填充bean属性
     * 针对数据源 如mysql查询结果、http请求结果中包含的数据字段不再当前对象中
     */
    boolean smartFillField() default false;

    /**
     * 是否存在
     */
    boolean exist() default true;

    /**
     * describe: 数据库引擎
     *
     * @author : Jia wei Wu
     * @version : 1.0
     * @date : 2022/6/29 20:54
     */
    @AllArgsConstructor
    @Getter
    enum Engine {
        /**
         * InnoDB
         */
        InnoDB("InnoDB"),
        /**
         * ARCHIVE
         */
        ARCHIVE("ARCHIVE"),
        /**
         * BLACKHOLE
         */
        BLACKHOLE("BLACKHOLE"),
        /**
         * CSV
         */
        CSV("CSV"),
        /**
         * MEMORY
         */
        MEMORY("MEMORY"),
        /**
         * MRG_MYISAM
         */
        MRG_MYISAM("MRG_MYISAM"),
        /**
         * MyISAM
         */
        MyISAM("MyISAM"),
        /**
         * PERFORMANCE_SCHEMA
         */
        PERFORMANCE_SCHEMA("PERFORMANCE_SCHEMA");
        private String name;

    }
}
