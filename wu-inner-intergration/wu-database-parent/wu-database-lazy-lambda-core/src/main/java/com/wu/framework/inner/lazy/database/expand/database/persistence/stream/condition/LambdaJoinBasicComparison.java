package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.SnippetUtil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.LambdaMeta;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;

import java.lang.reflect.ParameterizedType;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/23 8:36 下午
 */
public class LambdaJoinBasicComparison<T1, T2> extends AbstractJoinBasicComparison<T1, T2, Snippet<T1, ?>, Snippet<T2, ?>> {


    protected Class<T1> t1Class;
    protected Class<T2> t2Class;


    /**
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public SqlPart getSqlPart() {
        return sqlPart;
    }

    /**
     * 获取T 的class
     *
     * @return
     */
    @Override
    public Class<T1> getClassT() {
        return getClassT1();
    }

    /**
     * 获取T1 的class
     *
     * @return
     */
    @Override
    public Class<T1> getClassT1() {
        if (null != t1Class) {
            return t1Class;
        } else {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T1> type = (Class<T1>) superClass.getActualTypeArguments()[0];
            return type;
        }

    }

    /**
     * 获取T2 的class
     *
     * @return
     */
    @Override
    public Class<T2> getClassT2() {
        if (null != t2Class) {
            return t2Class;
        } else {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T2> type = (Class<T2>) superClass.getActualTypeArguments()[1];
            return type;
        }
    }


    /**
     * describe 拼接sql
     * <p>!! 会有 sql 注入风险 !!</p>
     * <p>例1: apply("id = 1")</p>
     * <p>例2: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")</p>
     * <p>例3: apply("date_format(dateColumn,'%Y-%m-%d') = {%sreturn this;} ", LocalDate.now())</p>
     *
     * @param condition 是否
     * @param applySql  拼接sql
     * @param values    拼接sql中的占位符
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2022/6/29 19:44 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> apply(boolean condition, String applySql, Object... values) {
        super.apply(condition, applySql, values);
        return this;
    }

    /**
     * 分组
     *
     * @param rows 分组字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:48
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> groupBy(Snippet<T1, ?>... rows) {
        super.groupBy(rows);
        return this;
    }

    /**
     * describe 排序
     *
     * @param order 排序 DESC或者ASC
     * @param rows  排序字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:48
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> orderBy(String order, Snippet<T1, ?>... rows) {
        super.orderBy(order, rows);
        return this;
    }

    /**
     * describe 等于条件
     *
     * @param condition
     * @param row
     * @param var
     * @return
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> eq(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.eq(condition, row, var);
        return this;
    }

    /**
     * 或查询
     *
     * @param condition 是否
     * @param row       行
     * @param var       行数据
     * @return C 返回数据
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> or(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.or(condition, row, var);
        return this;
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
    @Override
    public LambdaJoinBasicComparison<T1, T2> eqo(boolean condition, Snippet<T1, ?> row, Object var) {
        super.eqo(condition, row, var);
        return this;
    }

    /**
     * describe 右边的表字段数据 等于条件
     *
     * @param row 行
     * @param var 行数据
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> eqRighto(Snippet<T2, ?> row, Object var) {
        super.eqRighto(row, var);
        return this;
    }

    /**
     * describe 右边的表字段数据 等于条件
     *
     * @param condition 是否
     * @param row       行
     * @param var       行数据
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> eqRighto(boolean condition, Snippet<T2, ?> row, Object var) {
        super.eqRighto(condition, row, var);
        return this;
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
    @Override
    public LambdaJoinBasicComparison<T1, T2> ne(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.ne(condition, row, var);
        return this;
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
    @Override
    public LambdaJoinBasicComparison<T1, T2> neo(boolean condition, Snippet<T1, ?> row, Object var) {
        super.neo(condition, row, var);
        return this;
    }

    /**
     * describe 不等于条件
     *
     * @param row 行
     * @param var 行数据
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> neRighto(Snippet<T2, ?> row, Object var) {
        super.neRighto(row, var);
        return this;
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
    @Override
    public LambdaJoinBasicComparison<T1, T2> neRighto(boolean condition, Snippet<T2, ?> row, Object var) {
        super.neRighto(condition, row, var);
        return this;
    }

    /**
     * describe 不是空条件
     *
     * @param row 行
     * @return C 返回数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notNull(Snippet<T1, ?> row) {
        super.notNull(row);
        return this;
    }

    /**
     * describe 是空条件
     *
     * @param row 行
     * @return C 返回数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> isNull(Snippet<T1, ?> row) {
        super.isNull(row);
        return this;
    }

    /**
     * 右边数据是空
     *
     * @param row
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> isNullRight(Snippet<T2, ?> row) {
        super.isNullRight(row);
        return this;
    }

    /**
     * describe 不是空条件
     *
     * @param row 行
     * @return C 返回数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notNullRight(Snippet<T2, ?> row) {
        super.notNullRight(row);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> gt(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.gt(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> gto(boolean condition, Snippet<T1, ?> row, Object var) {
        super.gto(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> gtRighto(boolean condition, Snippet<T2, ?> row, Object var) {
        super.gtRighto(condition, row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> gtRighto(Snippet<T2, ?> row, Object var) {
        super.gtRighto(row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> lt(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.lt(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> lto(boolean condition, Snippet<T1, ?> row, Object var) {
        super.lto(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> ltRighto(boolean condition, Snippet<T2, ?> row, Object var) {
        super.ltRighto(condition, row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> ltRighto(Snippet<T2, ?> row, Object var) {
        super.ltRighto(row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> like(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.like(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> likeO(boolean condition, Snippet<T1, ?> row, Object var) {
        super.likeO(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> likeRightO(boolean condition, Snippet<T2, ?> row, Object var) {
        super.likeRightO(condition, row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> likeRightO(Snippet<T2, ?> row, Object var) {
        super.likeRightO(row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe not like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> notLike(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.notLike(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe not like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> notLikeO(boolean condition, Snippet<T1, ?> row, Object var) {
        super.notLikeO(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return describe not like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> notLikeRightO(boolean condition, Snippet<T2, ?> row, Object var) {
        super.notLikeRightO(condition, row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe not like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> notLikeRightO(Snippet<T2, ?> row, Object var) {
        super.notLikeRightO(row, var);
        return this;
    }

    /**
     * in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> in(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.in(condition, row, var);
        return this;
    }

    /**
     * in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> ino(boolean condition, Snippet<T1, ?> row, Object var) {
        super.ino(condition, row, var);
        return this;
    }

    /**
     * not in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notIn(boolean condition, Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.notIn(condition, row, var);
        return this;
    }

    /**
     * not in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notIno(boolean condition, Snippet<T1, ?> row, Object var) {
        super.notIno(condition, row, var);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param leftVar
     * @param rightVar
     * @return describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> between(boolean condition, Snippet<T1, ?> row, Object leftVar, Object rightVar) {
        super.between(condition, row, leftVar, rightVar);
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param leftVar
     * @param rightVar
     * @return describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> betweenO(boolean condition, Snippet<T1, ?> row, Object leftVar, Object rightVar) {
        super.betweenO(condition, row, leftVar, rightVar);
        return this;
    }

    /**
     * @param limit 要取的数据数量
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> limit(long limit) {
        super.limit(limit);
        return this;
    }

    /**
     * @param skipped 跳过数据数量
     * @param limit   要取的数据数量
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> limit(long skipped, long limit) {
        super.limit(skipped, limit);
        return this;
    }

    /**
     * @param lastSql 最后的sql
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> lastSql(String lastSql) {
        super.lastSql(lastSql);
        return this;
    }

    /**
     * @param applySql
     * @param values
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> apply(String applySql, Object... values) {
        super.apply(applySql, values);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> eq(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.eq(row, var);
        return this;
    }

    /**
     * 或查询
     *
     * @param row 行
     * @param var 行数据
     * @return C 返回数据
     * @return 对象
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> or(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.or(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> ne(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.ne(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> gt(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.gt(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> lt(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.lt(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> like(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.like(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notLike(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.notLike(row, var);
        return this;
    }

    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> in(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.in(row, var);
        return this;
    }

    /**
     * 将in查询替换为 or
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> inOr(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.inOr(row, var);
        return this;
    }

    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notIn(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.notIn(row, var);
        return this;
    }

    /**
     * @param row
     * @param leftVar
     * @param rightVar
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> between(Snippet<T1, ?> row, Object leftVar, Object rightVar) {
        super.between(row, leftVar, rightVar);
        return this;
    }

    /**
     * describe 降序
     *
     * @param rows 排序字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:51
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> orderByDesc(Snippet<T1, ?>... rows) {
        super.orderByDesc(rows);
        return this;
    }

    /**
     * describe 升序
     *
     * @param rows 排序字段
     * @return C
     * @author Jia wei Wu
     * @date 2022/6/26 21:51
     **/
    @Override
    public LambdaJoinBasicComparison<T1, T2> orderByAsc(Snippet<T1, ?>... rows) {
        super.orderByAsc(rows);
        return this;
    }

    /**
     * 忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> eqIgnoreEmpty(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.eqIgnoreEmpty(row, var);
        return this;
    }

    /**
     * 大于比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> gtIgnoreEmpty(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.gtIgnoreEmpty(row, var);
        return this;
    }

    /**
     * 小于比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> ltIgnoreEmpty(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.ltIgnoreEmpty(row, var);
        return this;
    }

    /**
     * like比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> likeIgnoreEmpty(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.likeIgnoreEmpty(row, var);
        return this;
    }

    /**
     * no like 比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notLikeIgnoreEmpty(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.notLikeIgnoreEmpty(row, var);
        return this;
    }

    /**
     * in 查询 忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> inIgnoreEmpty(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.inIgnoreEmpty(row, var);
        return this;
    }

    /**
     * not in 查询 忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notInIgnoreEmpty(Snippet<T1, ?> row, Snippet<T2, ?> var) {
        super.notInIgnoreEmpty(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> eqo(Snippet<T1, ?> row, Object var) {
        super.eqo(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> neo(Snippet<T1, ?> row, Object var) {
        super.neo(row, var);
        return this;
    }

    /**
     * 忽略空数据
     *
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> eqoIgnoreEmpty(Snippet<T1, ?> row, Object var) {
        super.eqoIgnoreEmpty(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> gto(Snippet<T1, ?> row, Object var) {
        super.gto(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> lto(Snippet<T1, ?> row, Object var) {
        super.lto(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> likeO(Snippet<T1, ?> row, Object var) {
        super.likeO(row, var);
        return this;
    }

    /**
     * @param row
     * @param var
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notLikeO(Snippet<T1, ?> row, Object var) {
        super.notLikeO(row, var);
        return this;
    }

    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> ino(Snippet<T1, ?> row, Object var) {
        super.ino(row, var);
        return this;
    }

    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> notIno(Snippet<T1, ?> row, Object var) {
        super.notIno(row, var);
        return this;
    }

    /**
     * 忽略空数据
     * <p>
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> inIgnoreEmptyO(Snippet<T1, ?> row, Object var) {
        super.inIgnoreEmptyO(row, var);
        return this;
    }

    /**
     * @param row
     * @param leftVar
     * @param rightVar
     * @return
     */
    @Override
    public LambdaJoinBasicComparison<T1, T2> betweenO(Snippet<T1, ?> row, Object leftVar, Object rightVar) {
        super.betweenO(row, leftVar, rightVar);
        return this;
    }

    @Override
    protected String column1ToString(Snippet<T1, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class tClass = meta.instantiatedClass();
        t1Class = tClass;
        final String field = CamelAndUnderLineConverter.methodToField(meta.methodName());
        final String fieldRowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(tClass);
        return tableName + NormalUsedString.DOT + fieldRowName;
    }


    @Override
    protected String column2ToString(Snippet<T2, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class tClass = meta.instantiatedClass();
        t2Class = tClass;
        final String field = CamelAndUnderLineConverter.methodToField(meta.methodName());
        final String fieldRowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(tClass);
        return tableName + NormalUsedString.DOT + fieldRowName;
    }
}
