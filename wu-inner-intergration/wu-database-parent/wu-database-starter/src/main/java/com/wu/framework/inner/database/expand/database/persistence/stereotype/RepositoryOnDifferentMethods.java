package com.wu.framework.inner.database.expand.database.persistence.stereotype;

import com.wu.framework.inner.database.expand.database.persistence.LayerOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;
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
@Indexed
public @interface RepositoryOnDifferentMethods {

    @AliasFor(attribute = "name")
    LayerOperationMethodEnum value() default LayerOperationMethodEnum.MISS;

    @AliasFor(attribute = "value")
    LayerOperationMethodEnum name() default LayerOperationMethodEnum.MISS;


    /**
     * @describe: 持久层声明代理方法
     * @author : Jia wei Wu
     * @date : 2020/11/22 上午11:05
     * @version : 1.0
     */
    @Getter
    @AllArgsConstructor
    enum LayerOperationMethodEnum {
        /**
         * {@link LayerOperation#upsertList(List)}
         */
        UPSERT_LIST("upsertList", "批量更新或插入"),
        /**
         * {@link LayerOperation#insertList(List)}
         */
        INSERT_LIST("insertList", "插入list"),
        /**
         * {@link LayerOperation#insert(Object)}
         */
        INSERT("insert", "插入 单个"),
        /**
         * {@link LayerOperation#activeUpsert(Object)}
         */
        ACTIVE_UPSERT("activeUpsert","更新或者插入单个 去除空值"),
        /**
         * {@link LayerOperation#updateById(Object)}
         */
        UPDATE_BY_ID("updateById", "根据ID更新"),
        /**
         * {@link LayerOperation#updateAllByIdList(List)}
         */
        UPDATE_ALL_BY_ID_LIST("updateAllByIdList", "根据主键ids更新list"),
        /**
         * {@link LayerOperation#deleteByIdList(List)}
         */
        DELETE_BY_ID_LIST("deleteByIdList", "批量删除"),
        /**
         * {@link LayerOperation#deleteById(Object)}
         */
        DELETE_BY_ID("deleteById", "删除"),
//        /**
//         * {@link com.wu.framework.inner.database.custom.database.persistence.LayerOperation#deleteAll(Object)}
//         */
//        DELETE_ALL("deleteAll", "删除所有"),
        /**
         * {@link LayerOperation#selectOne(Object)}
         */
        SELECT_ONE("selectOne", "selectOne"),
        /**
         * {@link LayerOperation#selectAll(Object)}
         */
        SELECT_ALL("selectAll", "查询所有"),
        /**
         * {@link LayerOperation#executeSQL(String, Class)}
         */
        EXECUTE_SQL("executeSQL", "执行SQL"),

        /**
         * {@link LayerOperation#executeSQLForBean(String, Class)}
         */
        EXECUTE_SQL_FOR_BEAN("executeSQLForBean", "执行SQLFor Bean"),
        /**
         * {@link LayerOperation#miss()}
         */
        MISS("miss", "丢失");
        private String methodName;
        private String desc;
    }

}
