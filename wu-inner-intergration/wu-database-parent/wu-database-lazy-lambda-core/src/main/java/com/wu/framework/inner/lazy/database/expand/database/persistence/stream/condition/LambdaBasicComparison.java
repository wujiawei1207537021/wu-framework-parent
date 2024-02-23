package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.select.AbstractSelectBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.SnippetUtil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.LambdaMeta;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/23 8:36 下午
 */
public class LambdaBasicComparison<T> extends AbstractSelectBasicComparison<T, Snippet<T, ?>, Object> {

    // 类
    protected Class<T> classT;
    // 类名称
    protected String className;
    protected SqlPart sqlPart;

    /**
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public SqlPart getSqlPart() {
        if (this.sqlPart == null) {
            // 此处的copy只能拷贝对象 字符串无法引用
            return super.sqlPart.copy();
        } else {
            return this.sqlPart;
        }
    }

    /**
     * 内关联
     *
     * @param internalJoinBasicComparison 内部查询
     * @return LambdaBasicComparison<T></>
     */
    @Override
    public <T2> LambdaBasicComparison<T> internalJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> internalJoinBasicComparison) {
        super.internalJoin(internalJoinBasicComparison);
        return this;
    }

    /**
     * 左关联
     *
     * @param leftJoinBasicComparison 内部查询
     * @return LambdaBasicComparison<T>
     */
    @Override
    public <T2> LambdaBasicComparison<T> leftJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> leftJoinBasicComparison) {
        super.leftJoin(leftJoinBasicComparison);
        return this;
    }

    /**
     * 右边 关联
     *
     * @param rightJoinBasicComparison 内部查询
     * @return LambdaBasicComparison<T>
     */
    @Override
    public <T2> LambdaBasicComparison<T> rightJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> rightJoinBasicComparison) {
        super.rightJoin(rightJoinBasicComparison);
        return this;
    }


    /**
     * describe 使用函数添加有别名
     *
     * @param function 函数
     * @param asName   别名
     * @return LambdaBasicComparison<T>
     * @author Jia wei Wu
     * @date 2023/1/1 23:07
     **/
    @Override
    public LambdaBasicComparison<T> functionAs(String function, String asName) {
        super.functionAs(function, asName);
        return this;
    }

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
    @Override
    public <T2> LambdaBasicComparison<T> functionAs(String function, Snippet<T2, ?> asRowName) {
        super.functionAs(function, asRowName);
        return this;
    }

    /**
     * 链表中的 as
     * object as  select "nihao" as asName from user
     *
     * @param row    列、函数、字符串
     * @param asName 别名
     * @return
     */
    @Override
    public LambdaBasicComparison<T> oas(String row, String asName) {
        super.oas(row, asName);
        return this;
    }

    /**
     * 链表中的 as
     * object as  select "nihao" as asName from user
     *
     * @param row       列、函数、字符串
     * @param asRowName 别名 Lambda表达式
     * @return
     */
    @Override
    public <T2> LambdaBasicComparison<T> oas(String row, Snippet<T2, ?> asRowName) {
        super.oas(row, asRowName);
        return this;
    }

    /**
     * 链表中的 as
     *
     * @param clazz 根据class 中的字段进行映射 等价于多个 as(R row, String asName);
     * @return
     */
    @Override
    public LambdaBasicComparison<T> as(Class<?> clazz) {
        super.as(clazz);
        return this;
    }

    /**
     * 链表中的 as
     *
     * @param row    列
     * @param asName 别名
     * @return
     */
    @Override
    public LambdaBasicComparison<T> as(Snippet<T, ?> row, String asName) {
        super.as(row, asName);
        return this;
    }

    /**
     * 链表中的 as
     *
     * @param row       列
     * @param asRowName 列名称
     * @return
     */
    @Override
    public <T1, T2> LambdaBasicComparison<T> as(Snippet<T1, ?> row, Snippet<T2, ?> asRowName) {
        super.as(row, asRowName);
        return this;
    }

    /**
     * 链表中的 as 来源于sql 片段
     *
     * @param comparison
     * @param asRowName
     * @return
     */
    @Override
    public <T1, T2, T3> LambdaBasicComparison<T> as(BasicComparison<T1, ?, ?, ?> comparison, Snippet<T3, ?> asRowName) {
        super.as(comparison, asRowName);
        return this;
    }

    /**
     * 链表中的 as 来源于sql 片段
     *
     * @param comparison
     * @param asRowName
     * @return
     */
    @Override
    public <T1, T2, T3> LambdaBasicComparison<T> as(BasicComparison<T1, ?, ?, ?> comparison, String asRowName) {
        super.as(comparison, asRowName);
        return this;
    }

    /**
     * 链表中 忽略字段
     *
     * @param row 忽略的列
     * @return
     */
    @Override
    public <T1> LambdaBasicComparison<T> ignoreAs(Snippet<T1, ?> row) {
        super.ignoreAs(row);
        return this;
    }

    /**
     * only use as
     * 仅仅使用as（忽略其他的字段）
     *
     * @return
     */
    @Override
    public LambdaBasicComparison<T> onlyUseAs() {
        super.onlyUseAs();
        return this;
    }

    /**
     * 链表中的 where 条件
     *
     * @param whereAbstractJoinBasicComparison
     * @return
     */
    @Override
    public <T2> LambdaBasicComparison<T> where(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> whereAbstractJoinBasicComparison) {
        super.where(whereAbstractJoinBasicComparison);
        return this;
    }

    /**
     * 获取T 的class
     *
     * @return
     */
    @Override
    public Class<T> getClassT() {
        if (null == classT) {
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType superClass = (ParameterizedType) genericSuperclass;
                this.classT = (Class<T>) superClass.getActualTypeArguments()[0];
            } else if (className != null) {
                try {
                    this.classT = (Class<T>) Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classT;
    }

    public void setClassT(Class<T> classT) {
        this.classT = classT;
    }

    public String getClassName() {
        if (null == className && classT != null) {
            this.className = this.classT.getName();
        }
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 列到字符串
     *
     * @param row
     * @return
     */
    @Override
    protected String columnToString(Snippet<T, ?> row) {
        final LambdaMeta meta = SnippetUtil.extract(row);
        assert meta != null;
        final Class tClass = meta.instantiatedClass();
        if (null == classT) {
            classT = tClass;
            this.getSqlPart().setPrimaryClass(classT);
        }
        final String methodName = meta.methodName();
        if ("toString".equals(methodName)) {
            return methodName;
        }
        final String field = CamelAndUnderLineConverter.methodToField(methodName);
        final String fieldRowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(tClass);
        this.getSqlPart().setPrimaryTable(tableName);
        return tableName + NormalUsedString.DOT + fieldRowName;
    }

    /**
     * 初始化 表
     */
    public LambdaBasicComparison<T> toString(Snippet<T, ?> row, Object o) {
        columnToString(row);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> apply(String applySql, Object... values) {
        super.apply(applySql, values);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> eq(Snippet<T, ?> row, Object var) {
        super.eq(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> ne(Snippet<T, ?> row, Object var) {
        super.ne(row, var);
        return this;
    }


    @Override
    public LambdaBasicComparison<T> gt(Snippet<T, ?> row, Object var) {
        super.gt(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> lt(Snippet<T, ?> row, Object var) {
        super.lt(row, var);
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
    public LambdaBasicComparison<T> like(boolean condition, Snippet<T, ?> row, Object var) {
        super.like(condition, row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> like(Snippet<T, ?> row, Object var) {
        super.like(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> notLike(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> in(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> inOr(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> notIn(Snippet<T, ?> row, Object var) {
        super.notIn(row, var);
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
    public LambdaBasicComparison<T> notNull(Snippet<T, ?> row) {
        super.notNull(row);
        return this;
    }

    /**
     * not in 判断null
     *
     * @param row 行
     * @param var 数据
     * @return 对象
     */
    @Override
    public LambdaBasicComparison<T> notInIgnoreEmpty(Snippet<T, ?> row, Object var) {
        super.notInIgnoreEmpty(row, var);
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
    public LambdaBasicComparison<T> inIgnoreEmpty(Snippet<T, ?> row, Object var) {
        super.inIgnoreEmpty(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> between(Snippet<T, ?> row, Object leftVar, Object rightVar) {
        super.between(row, leftVar, rightVar);
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
    @SafeVarargs
    @Override
    public final LambdaBasicComparison<T> groupBy(Snippet<T, ?>... rows) {
        super.groupBy(rows);
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
    @SafeVarargs
    @Override
    public final LambdaBasicComparison<T> orderByDesc(Snippet<T, ?>... rows) {
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
    @SafeVarargs
    @Override
    public final LambdaBasicComparison<T> orderByAsc(Snippet<T, ?>... rows) {
        super.orderByAsc(rows);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> eqo(Snippet<T, ?> row, Object var) {
        super.eqo(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> neo(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> eqoIgnoreEmpty(Snippet<T, ?> row, Object var) {
        super.eqoIgnoreEmpty(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> gto(Snippet<T, ?> row, Object var) {
        super.gto(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> lto(Snippet<T, ?> row, Object var) {
        super.lto(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> likeO(Snippet<T, ?> row, Object var) {
        super.likeO(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> notLikeO(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> ino(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> notIno(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> inIgnoreEmptyO(Snippet<T, ?> row, Object var) {
        super.inIgnoreEmptyO(row, var);
        return this;
    }

    @Override
    public LambdaBasicComparison<T> betweenO(Snippet<T, ?> row, Object leftVar, Object rightVar) {
        super.betweenO(row, leftVar, rightVar);
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
    public LambdaBasicComparison<T> eqIgnoreEmpty(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> gtIgnoreEmpty(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> ltIgnoreEmpty(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> likeIgnoreEmpty(Snippet<T, ?> row, Object var) {
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
    public LambdaBasicComparison<T> notLikeIgnoreEmpty(Snippet<T, ?> row, Object var) {
        super.notLikeIgnoreEmpty(row, var);
        return this;
    }

    /**
     * @param limit 要取的数据数量
     * @return
     */
    @Override
    public LambdaBasicComparison<T> limit(long limit) {
        super.limit(limit);
        return this;
    }

    /**
     * @param skipped 跳过数据数量
     * @param limit   要取的数据数量
     * @return
     */
    @Override
    public LambdaBasicComparison<T> limit(long skipped, long limit) {
        super.limit(skipped, limit);
        return this;
    }

    /**
     * @param lastSql 最后的sql
     * @return
     */
    @Override
    public LambdaBasicComparison<T> lastSql(String lastSql) {
        super.lastSql(lastSql);
        return this;
    }
}
