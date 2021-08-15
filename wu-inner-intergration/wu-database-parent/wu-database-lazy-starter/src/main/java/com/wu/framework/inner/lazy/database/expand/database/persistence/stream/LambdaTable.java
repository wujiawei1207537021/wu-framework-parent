package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @param
 * @author Jia wei Wu
 * @describe 声明表并创建相应表的过滤条件
 * @return
 * @date 2021/8/15 12:07 下午
 **/
public interface LambdaTable<T, R> {


    /**
     * @param
     * @return
     * @describe 主表
     * @author Jia wei Wu
     * @date 2021/8/8 12:27 下午
     **/
    LambdaStreamCollector<T, R> table(Class primaryTable);

    /** 操作表类型
     * @param lambdaTableType
     * @return
     */
    LambdaStreamCollector<T, R> lambdaTableType(LambdaTableType lambdaTableType);

    /**
     *
     */
    @Getter
    @AllArgsConstructor
    enum LambdaTableType {
        SELECT("select", "查询");
        private String value;
        private String msg;
    }
}
