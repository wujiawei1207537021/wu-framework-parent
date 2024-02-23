package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.as.FunctionAsComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;

/**
 * description 使用as 作为条件
 *
 * @author Jia wei Wu
 * @date 2022/12/30 12:46 下午
 */
public interface BasicAsComparison<T, R, V, C> {

    /**
     * describe 使用函数添加有别名
     *
     * @param function 函数
     * @param asName   别名
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/1 23:07
     **/
    C functionAs(String function, String asName);

    /**
     * describe 使用函数添加有别名
     *
     * @param function  函数 例如：count(1)
     * @param asRowName 别名 例如：c
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/1 23:08
     **/
    <T2> C functionAs(String function, Snippet<T2, ?> asRowName);

    /**
     * describe 使用函数添加有别名
     *
     * @param function  函数 例如：count(1)
     * @param asRowName 别名 例如：c
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/1 23:08
     **/
    default <T1, T2, T3> C functionAs(FunctionAsComparison<T1, ?> function, Snippet<T3, ?> asRowName) {
        return functionAs(function.getFunctionFragment(), asRowName);
    }

    ;

    /**
     * 链表中的 as
     * object as  select "nihao" as asName from user
     *
     * @param row    列、函数、字符串
     * @param asName 别名
     * @return
     */
    C oas(String row, String asName);

    /**
     * 链表中的 as
     * object as  select "nihao" as asName from user
     *
     * @param row       列、函数、字符串
     * @param asRowName 别名 Lambda表达式
     * @return
     */
    <T2> C oas(String row, Snippet<T2, ?> asRowName);


    /**
     * 链表中的 as
     *
     * @param clazz 根据class 中的字段进行映射 等价于多个 as(R row, String asName);
     *              class 必须是当前LambdaStream 查询中的数据库模型 ⚠️字段不能多
     * @return
     */
    C as(Class<?> clazz);

    /**
     * 链表中的 as
     *
     * @param row    列
     * @param asName 别名
     * @return
     */
    C as(R row, String asName);


    /**
     * 链表中的 as
     *
     * @param row       列
     * @param asRowName 列名称
     * @return
     */
    <T1, T2> C as(Snippet<T1, ?> row, Snippet<T2, ?> asRowName);

    /**
     * 链表中的 as 来源于sql 片段
     *
     * @param comparison
     * @param asRowName
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @return
     */
    <T1, T2, T3> C as(BasicComparison<T1, ?, ?, ?> comparison, Snippet<T3, ?> asRowName);

    /**
     * 链表中的 as 来源于sql 片段
     *
     * @param comparison
     * @param asRowName
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @return
     */
    <T1, T2, T3> C as(BasicComparison<T1, ?, ?, ?> comparison, String asRowName);

    /**
     * 链表中 忽略字段
     *
     * @param row 忽略的列
     * @return
     */
    <T1> C ignoreAs(Snippet<T1, ?> row);


    /**
     * only use as
     * 仅仅使用as（忽略其他的字段）
     *
     * @return
     */
    C onlyUseAs();
}
