package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;

/**
 * description 模版条件
 *
 * @author Jia wei Wu
 * @date 2022/08/17 4:12 下午
 */
public interface TemplateComparison<T, R, V, C extends TemplateComparison<T, R, V, C>> {


    /**
     * describe 拼接sql
     * <p>!! 会有 sql 注入风险 !!</p>
     * <p>例1: apply("id = 1")</p>
     * <p>例2: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")</p>
     * <p>例3: apply("date_format(dateColumn,'%Y-%m-%d') = {%s}", LocalDate.now())</p>
     *
     * @param condition 是否
     * @param applySql  拼接sql
     * @param values    拼接sql中的占位符
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2022/6/29 19:44 下午
     **/
    C apply(boolean condition, String applySql, Object... values);

    default C apply(String applySql, Object... values) {
        return apply(true, applySql, values);
    }

    /**
     * describe 等于条件
     *
     * @param condition 是否
     * @param row       行
     * @param var       行数据
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    C eq(boolean condition, R row, V var);

    /**
     * @param row 数据库列表达式
     * @param var 列匹配数据
     * @return C
     */
    default C eq(R row, V var) {
        return eq(true, row, var);
    }

    /**
     * 或查询
     *
     * @param condition 是否
     * @param row       行
     * @param var       行数据
     * @return C 返回数据
     */
    C or(boolean condition, R row, V var);

    /**
     * 或查询
     *
     * @param row 行
     * @param var 行数据
     * @return C 返回数据
     */
    default C or(R row, V var) {
        return or(true, row, var);
    }

    /**
     * describe 不等于条件
     *
     * @param condition 是否
     * @param row       行
     * @param var       行数据
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    C ne(boolean condition, R row, V var);

    /**
     * @param row 数据库列表达式
     * @param var 列匹配数据
     * @return C
     */
    default C ne(R row, V var) {
        return ne(true, row, var);
    }

    /**
     * describe 不是空条件
     *
     * @param row 行
     * @return C 返回数据
     */
    C notNull(R row);

    /**
     * describe 是空条件
     *
     * @param row 行
     * @return C 返回数据
     */
    C isNull(R row);


    /**
     * 大于查询
     *
     * @param condition 判断是否满足
     * @param row       数据库列表达式
     * @param var       列匹配数据
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    C gt(boolean condition, R row, V var);

    /**
     * 大于查询
     *
     * @param row 数据库列表达式
     * @param var 列匹配数据
     * @return C
     */
    default C gt(R row, V var) {
        return gt(true, row, var);
    }

    /**
     * @param condition 判断是否满足
     * @param row       数据库列表达式
     * @param var       列匹配数据
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    C lt(boolean condition, R row, V var);

    /**
     * @param row 数据库列表达式
     * @param var 列匹配数据
     * @return C
     */
    default C lt(R row, V var) {
        return lt(true, row, var);
    }

    /**
     * @param condition 判断是否满足
     * @param row       数据库列表达式
     * @param var       列匹配数据
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C like(boolean condition, R row, V var);

    default C like(R row, V var) {
        return like(true, row, var);
    }

    /**
     * @param condition 判断是否满足
     * @param row       数据库列表达式
     * @param var       列匹配数据
     * @return describe not like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C notLike(boolean condition, R row, V var);

    /**
     * @param row 数据库列表达式
     * @param var 列匹配数据
     * @return C
     */
    default C notLike(R row, V var) {
        return notLike(true, row, var);
    }

    /**
     * in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return C
     */
    C in(boolean condition, R row, V var);


    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C in(R row, V var) {
        return in(true, row, var);
    }

    /**
     * 将in查询替换为 or
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return C
     */
    C inOr(boolean condition, R row, V var);

    /**
     * 将in查询替换为 or
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C inOr(R row, V var) {
        return inOr(true, row, var);
    }

    /**
     * not in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return C
     */
    C notIn(boolean condition, R row, V var);

    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C notIn(R row, V var) {
        return notIn(true, row, var);
    }


    /**
     * @param condition 判断是否满足
     * @param row       数据库列表达式
     * @param leftVar   列匹配数据
     * @param rightVar  列匹配数据
     * @return describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C between(boolean condition, R row, Object leftVar, Object rightVar);

    /**
     * @param row      数据库列表达式
     * @param leftVar  列匹配数据
     * @param rightVar 列匹配数据
     * @return C
     */
    default C between(R row, Object leftVar, Object rightVar) {
        return between(true, row, leftVar, rightVar);
    }


    /**
     * 分组
     *
     * @param rows 分组字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:48
     */
    C groupBy(R... rows);

    /**
     * describe 排序
     *
     * @param order 排序 DESC或者ASC
     * @param rows  排序字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:48
     **/
    C orderBy(String order, R... rows);

    /**
     * describe 降序
     *
     * @param rows 排序字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:51
     **/
    default C orderByDesc(R... rows) {
        return orderBy(NormalUsedString.DESC, rows);
    }

    /**
     * describe 升序
     *
     * @param rows 排序字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:51
     **/
    default C orderByAsc(R... rows) {
        return orderBy(NormalUsedString.ASC, rows);
    }

    /**
     * @param limit 要取的数据数量
     * @return
     */
    C limit(long limit);

    /**
     * @param skipped 跳过数据数量
     * @param limit   要取的数据数量
     * @return
     */
    C limit(long skipped, long limit);

    /**
     * @param lastSql 最后的sql
     * @return
     */
    C lastSql(String lastSql);

    /**
     * @param
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    SqlPart getSqlPart();


    /**
     * 获取T 的class
     *
     * @return
     */
    Class<T> getClassT();

}
