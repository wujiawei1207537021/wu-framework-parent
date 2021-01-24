package com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 获取不同方法上指定执行类
 * @date : 2020/7/3 下午10:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Indexed
public @interface RepositoryOnDifferentMethods {

    @AliasFor(attribute = "name")
    LayerOperationMethodEnum value() default LayerOperationMethodEnum.MISS;

    @AliasFor(attribute = "value")
    LayerOperationMethodEnum name() default LayerOperationMethodEnum.MISS;


    /**
     * @author : Jia wei Wu
     * @version : 1.0
     * @describe: 持久层声明代理方法
     * @date : 2020/11/22 上午11:05
     */
    @Getter
    @AllArgsConstructor
    enum LayerOperationMethodEnum {
        /**
         * {@link LazyOperation#upsertList(List)}
         */
        UPSERT_LIST("upsertList", "批量更新或插入"),
        /**
         * {@link LazyOperation#insertList(List)}
         */
        INSERT_LIST("insertList", "插入list"),
        /**
         * {@link LazyOperation#insert(Object)}
         */
        INSERT("insert", "插入 单个"),
        /**
         * {@link LazyOperation#activeUpsert(Object)}
         */
        ACTIVE_UPSERT("activeUpsert", "更新或者插入单个 去除空值"),
        /**
         * {@link LazyOperation#updateById(Object)}
         */
        UPDATE_BY_ID("updateById", "根据ID更新"),
        /**
         * {@link LazyOperation#updateAllByIdList(List)}
         */
        UPDATE_ALL_BY_ID_LIST("updateAllByIdList", "根据主键ids更新list"),
        /**
         * {@link LazyOperation#deleteByIdList(List)}
         */
        DELETE_BY_ID_LIST("deleteByIdList", "批量删除"),
        /**
         * {@link LazyOperation#deleteById(Object)}
         */
        DELETE_BY_ID("deleteById", "删除"),
//        /**
//         * {@link com.wu.framework.inner.database.custom.database.persistence.LazyOperation#deleteAll(Object)}
//         */
//        DELETE_ALL("deleteAll", "删除所有"),
        /**
         * {@link LazyOperation#selectOne(Object)}
         */
        SELECT_ONE("selectOne", "selectOne"),
        /**
         * {@link LazyOperation#selectAll(Object)}
         */
        SELECT_ALL("selectAll", "查询所有"),
        /**
         * {@link LazyOperation#executeSQL(String, Class)}
         */
        EXECUTE_SQL("executeSQL", "执行SQL"),

        /**
         * {@link LazyOperation#executeSQLForBean(String, Class)}
         */
        EXECUTE_SQL_FOR_BEAN("executeSQLForBean", "执行SQLFor Bean"),
        /**
         * {@link LazyOperation#miss()}
         */
        MISS("miss", "丢失");
        private String methodName;
        private String desc;
    }

}
