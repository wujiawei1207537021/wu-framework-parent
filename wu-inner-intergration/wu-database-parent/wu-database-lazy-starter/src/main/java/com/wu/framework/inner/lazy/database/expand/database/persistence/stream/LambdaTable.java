package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * @param
 * @author Jia wei Wu
 * @describe 声明表并创建相应表的过滤条件
 * @return
 * @date 2021/8/15 12:07 下午
 **/
public interface LambdaTable<T, R> extends LambdaStreamCollector<T, R>{


    /**
     * @param
     * @return
     * @describe 主表
     * @author Jia wei Wu
     * @date 2021/8/8 12:27 下午
     **/
    LambdaSplicing<T, R> table(Class primaryTable);

    /**
     * description 左关联
     *
     * @param
     * @param comparison
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:47 下午
     */
    LambdaSplicing<T, R> leftJoin(BasicComparison comparison);

    /**
     * description 右关联
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:48 下午
     */
    LambdaSplicing<T, R> rightJoin(Consumer consumer);

    /**
     * @param
     * @param comparison
     * @return
     * @describe and条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    LambdaSplicing<T, R> and(BasicComparison comparison);

    /**
     * @param
     * @param comparison
     * @return
     * @describe or 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    LambdaSplicing<T, R> or(BasicComparison comparison);


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
        DELETE("delete", "删除"),
        SELECT("select", "查询");
        private String value;
        private String msg;
    }
}
