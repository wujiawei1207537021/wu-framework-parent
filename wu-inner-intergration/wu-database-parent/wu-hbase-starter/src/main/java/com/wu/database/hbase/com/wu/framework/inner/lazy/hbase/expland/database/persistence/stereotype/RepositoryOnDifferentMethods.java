package com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.persistence.stereotype;

import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.Operation;
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
    HBaseOperationMethodEnum value() default HBaseOperationMethodEnum.MISS;

    @AliasFor(attribute = "value")
    HBaseOperationMethodEnum name() default HBaseOperationMethodEnum.MISS;


    /**
     * @author : Jia wei Wu
     * @version : 1.0
     * @describe: 持久层声明代理方法
     * @date : 2020/11/22 上午11:05
     */
    @Getter
    @AllArgsConstructor
    enum HBaseOperationMethodEnum {

        /**
         * {@link Operation#executeSQLForBean(String, Class)}
         */
        EXECUTE_SQL_FOR_BEAN("executeSQLForBean", "执行SQLFor Bean"),
        /**
         * {@link Operation#miss()}
         */
        MISS("miss", "丢失");
        private String methodName;
        private String desc;
    }

}
