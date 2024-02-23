package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.enums.RowValueType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.BasicAsComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.BasicConcatComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.SnippetUtil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.LambdaMeta;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazySQLUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/21 7:09 下午
 */
public abstract class AbstractBasicComparison<T, R, V>
        implements BasicComparison<T, R, V, AbstractBasicComparison<T, R, V>>,
        BasicAsComparison<T, R, V, AbstractBasicComparison<T, R, V>>,
        BasicConcatComparison<T, R, V, AbstractBasicComparison<T, R, V>> {

    protected SqlPart sqlPart = new SqlPart(NormalUsedString.SPACE + NormalUsedString.WHERE + NormalUsedString.SPACE);

    /**
     * 内关联
     *
     * @param internalJoinBasicComparison
     * @param <T2>
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> internalJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> internalJoinBasicComparison) {
        final SqlPart sqlPart = internalJoinBasicComparison.getSqlPart();
        final Class entityClass = sqlPart.getPrimaryClass();
        final String tableName = LazyTableUtil.getTableName(entityClass);
        sqlPart.setPrefix(" inner join ");
        sqlPart.setConditionType(NormalUsedString.ON);
        sqlPart.setPrimaryTable(tableName);
        sqlPart.setPrimaryClass(entityClass);
        this.sqlPart.join(sqlPart);
        return this;
    }

    /**
     * 左关联
     *
     * @param leftJoinBasicComparison
     * @param <T2>
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> leftJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> leftJoinBasicComparison) {
        final SqlPart sqlPart = leftJoinBasicComparison.getSqlPart();
        final Class entityClass = sqlPart.getPrimaryClass();
        final String tableName = LazyTableUtil.getTableName(entityClass);
        sqlPart.setPrefix("left join ");
        sqlPart.setConditionType(NormalUsedString.ON);
        sqlPart.setPrimaryTable(tableName);
        sqlPart.setPrimaryClass(entityClass);
        this.sqlPart.join(sqlPart);
        return this;
    }

    /**
     * 右边 关联
     *
     * @param rightJoinBasicComparison
     * @param <T2>
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> rightJoin(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> rightJoinBasicComparison) {
        final SqlPart sqlPart = rightJoinBasicComparison.getSqlPart();
        final Class entityClass = sqlPart.getPrimaryClass();
        final String tableName = LazyTableUtil.getTableName(entityClass);
        sqlPart.setPrefix("right join ");
        sqlPart.setConditionType(NormalUsedString.ON);
        sqlPart.setPrimaryTable(tableName);
        sqlPart.setPrimaryClass(entityClass);
        this.sqlPart.join(sqlPart);
        return this;
    }

    /**
     * 链表中的 where 条件
     *
     * @param whereAbstractJoinBasicComparison
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> where(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> whereAbstractJoinBasicComparison) {
        final SqlPart sqlPart = whereAbstractJoinBasicComparison.getSqlPart();
        this.sqlPart.getConditionList().addAll(sqlPart.getConditionList());
        return this;
    }

    /**
     * 链表中的 or 条件
     *
     * @param orAbstractJoinBasicComparison
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> or(BasicComparison orAbstractJoinBasicComparison) {
        final SqlPart sqlPart = orAbstractJoinBasicComparison.getSqlPart();
        // 添加括号
        this.sqlPart.or(sqlPart);
        return this;
    }

    /**
     * 链表中的 having 条件
     *
     * @param havingBasicComparison
     * @return
     */
    public <T2> AbstractBasicComparison<T, R, V> having(AbstractJoinBasicComparison<T, T2, Snippet<T, ?>, Snippet<T2, ?>> havingBasicComparison) {
        final SqlPart sqlPart = havingBasicComparison.getSqlPart();
        final Class entityClass = sqlPart.getPrimaryClass();
        final String tableName = LazyTableUtil.getTableName(entityClass);
        sqlPart.setPrefix("HAVING  ");
        sqlPart.setConditionType(NormalUsedString.EMPTY);
//        sqlPart.setPrimaryTable(tableName);
        this.sqlPart.having(sqlPart);
        return this;
    }


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
    @Override
    public AbstractBasicComparison<T, R, V> functionAs(String function, String asName) {
        sqlPart.columnAs(function, asName);
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
    public <T2> AbstractBasicComparison<T, R, V> functionAs(String function, Snippet<T2, ?> asRowName) {
        final LambdaMeta meta = SnippetUtil.extract(asRowName);
        assert meta != null;
        final String methodName = meta.methodName();
        final String field = CamelAndUnderLineConverter.methodToField(methodName);
        String asName = CamelAndUnderLineConverter.humpToLine2(field);
        sqlPart.columnAs(function, asName);
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
    public AbstractBasicComparison<T, R, V> oas(String row, String asName) {
        sqlPart.columnAs(NormalUsedString.SINGLE_QUOTE + row + NormalUsedString.SINGLE_QUOTE, asName);
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
    public <T2> AbstractBasicComparison<T, R, V> oas(String row, Snippet<T2, ?> asRowName) {
        final LambdaMeta meta = SnippetUtil.extract(asRowName);
        assert meta != null;
        final String methodName = meta.methodName();
        final String field = CamelAndUnderLineConverter.methodToField(methodName);
        String asName = CamelAndUnderLineConverter.humpToLine2(field);
        sqlPart.columnAs(NormalUsedString.SINGLE_QUOTE + row + NormalUsedString.SINGLE_QUOTE, asName);
        return this;
    }

    /**
     * 链表中的 as
     *
     * @param clazz 根据class 中的字段进行映射 等价于多个 as(R row, String asName);
     * @return
     */
    @Override
    public AbstractBasicComparison<T, R, V> as(Class<?> clazz) {
        SqlSourceClass sqlSourceClass = SqlSourceClass.getInstance(clazz);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(clazz);
        List<LazyTableFieldEndpoint> fieldEndpoints = lazyTableEndpoint.getFieldEndpoints();
        for (LazyTableFieldEndpoint fieldEndpoint : fieldEndpoints) {
            String asName = fieldEndpoint.getColumnName();
//            mysql.user.role_id
            String tableFullColumnName = lazyTableEndpoint.getFullTableName() + NormalUsedString.DOT + asName;
            sqlPart.columnAs(tableFullColumnName, asName);
        }
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
    public AbstractBasicComparison<T, R, V> as(R row, String asName) {
        sqlPart.columnAs(columnToString(row), asName);
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
    public <T1, T2> AbstractBasicComparison<T, R, V> as(Snippet<T1, ?> row, Snippet<T2, ?> asRowName) {
        final LambdaMeta meta = SnippetUtil.extract(asRowName);
        assert meta != null;
        final String methodName = meta.methodName();
        final String field = CamelAndUnderLineConverter.methodToField(methodName);
        String asName = CamelAndUnderLineConverter.humpToLine2(field);

        final LambdaMeta rowMeta = SnippetUtil.extract(row);
        assert rowMeta != null;
        final Class rowClass = rowMeta.instantiatedClass();
        final String rowMethodName = rowMeta.methodName();
        final String rowField = CamelAndUnderLineConverter.methodToField(rowMethodName);
        String rowName = CamelAndUnderLineConverter.humpToLine2(rowField);

        final String tableName = LazyTableUtil.getTableName(rowClass);
        sqlPart.columnAs(tableName + NormalUsedString.DOT + rowName, asName);
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
    public <T1, T2, T3> AbstractBasicComparison<T, R, V> as(BasicComparison<T1, ?, ?, ?> comparison,
                                                            Snippet<T3, ?> asRowName) {
        final LambdaMeta meta = SnippetUtil.extract(asRowName);
        assert meta != null;
        final String methodName = meta.methodName();
        final String field = CamelAndUnderLineConverter.methodToField(methodName);
        String asName = CamelAndUnderLineConverter.humpToLine2(field);
        SqlPart sqlPart = comparison.getSqlPart();
        Class<T1> classT = comparison.getClassT();
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        LazyTableEndpoint lazyTableEndpoint = SourceFactory.defaultAnalyzeLazyTableFromClass(classT);
        sqlPart.setExecutionEnum(Persistence.ExecutionEnum.SELECT);
        sqlPart.setPrimaryClass(classT);
        sqlPart.setPrimaryTable(lazyTableEndpoint.getFullTableName());
        String sql = sqlPart.sql();
        this.sqlPart.columnAs(NormalUsedString.LEFT_BRACKET + sql + NormalUsedString.RIGHT_BRACKET,
                asName);
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
    public <T1, T2, T3> AbstractBasicComparison<T, R, V> as(BasicComparison<T1, ?, ?, ?> comparison, String asRowName) {
        SqlPart sqlPart = comparison.getSqlPart();
        Class<T1> classT = comparison.getClassT();
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(classT);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        sqlPart.setExecutionEnum(Persistence.ExecutionEnum.SELECT);
        sqlPart.setPrimaryClass(classT);
        sqlPart.setPrimaryTable(lazyTableEndpoint.getFullTableName());
        String sql = sqlPart.sql();
        this.sqlPart.columnAs(NormalUsedString.LEFT_BRACKET + sql + NormalUsedString.RIGHT_BRACKET,
                asRowName);
        return this;
    }

    /**
     * 链表中 忽略字段
     *
     * @param row 忽略的列
     * @return
     */
    @Override
    public <T1> AbstractBasicComparison<T, R, V> ignoreAs(Snippet<T1, ?> row) {
        final LambdaMeta rowMeta = SnippetUtil.extract(row);
        assert rowMeta != null;
        final Class rowClass = rowMeta.instantiatedClass();
        final String rowMethodName = rowMeta.methodName();
        final String field = CamelAndUnderLineConverter.methodToField(rowMethodName);
        String rowName = CamelAndUnderLineConverter.humpToLine2(field);
        final String tableName = LazyTableUtil.getTableName(rowClass);
        sqlPart.ignoreColumnAs(tableName + NormalUsedString.DOT + rowName);
        return this;
    }

    /**
     * only use as
     * 仅仅使用as（忽略其他的字段）
     *
     * @return
     */
    @Override
    public AbstractBasicComparison<T, R, V> onlyUseAs() {
        sqlPart.setOnlyUseAs(true);
        return this;
    }

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
    @Override
    public AbstractBasicComparison<T, R, V> apply(boolean condition, String applySql, Object... values) {
        if (condition) {
            if (ObjectUtils.isEmpty(applySql)) {
                return this;
            }
            sqlPart.put(String.format(applySql, values),
                    NormalUsedString.SPACE,
                    RowValueType.EXPRESSION,
                    NormalUsedString.SPACE);
        }
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param v
     * @return describe 等于条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> eq(boolean condition, R row, V v) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.EQUALS, valueToString(v));
        }
        return this;
    }

    /**
     * 或查询
     *
     * @param condition 是否
     * @param row       行
     * @param v         行数据
     * @return C 返回数据
     */
    @Override
    public AbstractBasicComparison<T, R, V> or(boolean condition, R row, V v) {
        if (condition) {
            sqlPart.put(Condition.AndOr.OR, columnToString(row), NormalUsedString.EQUALS, valueToString(v));
        }
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
    public AbstractBasicComparison<T, R, V> eqo(boolean condition, R row, Object var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.EQUALS, var);
        }
        return this;
    }


    /**
     * describe 不等于条件
     *
     * @param condition 是否
     * @param row       行
     * @param v         行数据
     * @return C 返回数据
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> ne(boolean condition, R row, V v) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.EXCLAMATION_MARK + NormalUsedString.EQUALS, valueToString(v));
        }
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
    public AbstractBasicComparison<T, R, V> neo(boolean condition, R row, Object var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.EXCLAMATION_MARK + NormalUsedString.EQUALS, var);
        }
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
    public AbstractBasicComparison<T, R, V> notNull(R row) {
        sqlPart.put(columnToString(row), NormalUsedString.IS_NOT, null);
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
    public AbstractBasicComparison<T, R, V> isNull(R row) {
        sqlPart.put(columnToString(row), NormalUsedString.IS, null);
        return this;
    }

    /**
     * describe 大于
     *
     * @param condition
     * @param row
     * @param var
     * @return
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> gt(boolean condition, R row, V var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.RIGHT_CHEV, valueToString(var));
        }
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
    public AbstractBasicComparison<T, R, V> gto(boolean condition, R row, Object var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.RIGHT_CHEV, var);
        }
        return this;
    }

    /**
     * describe 小于
     *
     * @param condition
     * @param row
     * @param var
     * @return AbstractBasicComparison
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    @Override
    public AbstractBasicComparison<T, R, V> lt(boolean condition, R row, V var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.LEFT_CHEV, valueToString(var));
        }
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
    public AbstractBasicComparison<T, R, V> lto(boolean condition, R row, Object var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.LEFT_CHEV, var);
        }
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
    public AbstractBasicComparison<T, R, V> like(boolean condition, R row, V var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.LIKE, valueToString(var));
        }
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
    public AbstractBasicComparison<T, R, V> likeO(boolean condition, R row, Object var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.LIKE, var);
        }
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
    public AbstractBasicComparison<T, R, V> notLike(boolean condition, R row, V var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.NOT + NormalUsedString.SPACE + NormalUsedString.LIKE, valueToString(var));
        }
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
    public AbstractBasicComparison<T, R, V> notLikeO(boolean condition, R row, Object var) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.NOT + NormalUsedString.SPACE + NormalUsedString.LIKE, var);
        }
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
    public AbstractBasicComparison<T, R, V> in(boolean condition, R row, V var) {
        if (condition) {
            if (Collection.class.isAssignableFrom(var.getClass())) {
                final String in = ((Collection<?>) var).stream()
                        .map(va -> LazySQLUtil.sqlValue(va).toString())
                        .collect(Collectors.joining(NormalUsedString.COMMA));
                sqlPart.put(columnToString(row), NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + LazySQLUtil.sqlValue(in, false) + NormalUsedString.RIGHT_BRACKET);
            } else {
                sqlPart.put(columnToString(row), NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + LazySQLUtil.sqlValue(var, false) + NormalUsedString.RIGHT_BRACKET);
            }

        }
        return this;
    }

    /**
     * 将in查询替换为 or
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return C
     */
    @Override
    public AbstractBasicComparison<T, R, V> inOr(boolean condition, R row, V var) {
        if (condition) {
            if (Collection.class.isAssignableFrom(var.getClass())) {
                String columnName = columnToString(row);
                String or = ((Collection<?>) var).stream()
                        .map(va ->
                                NormalUsedString.SPACE + columnName +
                                        NormalUsedString.SPACE + NormalUsedString.EQUALS +
                                        NormalUsedString.SPACE + LazySQLUtil.sqlValue(va) + NormalUsedString.SPACE)
                        .collect(Collectors.joining(NormalUsedString.OR));

                sqlPart.put(NormalUsedString.LEFT_BRACKET, or, RowValueType.EXPRESSION, NormalUsedString.RIGHT_BRACKET);
            } else {
                sqlPart.put(columnToString(row), NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + LazySQLUtil.sqlValue(var, false) + NormalUsedString.RIGHT_BRACKET);
            }

        }
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
    public AbstractBasicComparison<T, R, V> ino(boolean condition, R row, Object var) {
        if (condition) {
            if (Collection.class.isAssignableFrom(var.getClass())) {
                final String in = ((Collection<?>) var).stream()
                        .map(va -> LazySQLUtil.sqlValue(va).toString())
                        .collect(Collectors.joining(NormalUsedString.COMMA));
                sqlPart.put(columnToString(row), NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + LazySQLUtil.sqlValue(in, false) + NormalUsedString.RIGHT_BRACKET);
            } else {
                sqlPart.put(columnToString(row), NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + LazySQLUtil.sqlValue(var, false) + NormalUsedString.RIGHT_BRACKET);
            }

        }
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
    public AbstractBasicComparison<T, R, V> notIn(boolean condition, R row, V var) {
        if (condition) {
            if (Collection.class.isAssignableFrom(var.getClass())) {
                final String in = ((Collection<?>) var).stream()
                        .map(va -> NormalUsedString.SINGLE_QUOTE + va.toString() + NormalUsedString.SINGLE_QUOTE)
                        .collect(Collectors.joining(NormalUsedString.COMMA));
                sqlPart.put(columnToString(row), NormalUsedString.NOT + NormalUsedString.SPACE + NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + in + NormalUsedString.RIGHT_BRACKET);
            } else {
                sqlPart.put(columnToString(row), NormalUsedString.NOT + NormalUsedString.SPACE + NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + var + NormalUsedString.RIGHT_BRACKET);
            }

        }
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
    public AbstractBasicComparison<T, R, V> notIno(boolean condition, R row, Object var) {
        if (condition) {
            if (Collection.class.isAssignableFrom(var.getClass())) {
                final String in = ((Collection<?>) var).stream()
                        .map(va -> NormalUsedString.SINGLE_QUOTE + va.toString() + NormalUsedString.SINGLE_QUOTE)
                        .collect(Collectors.joining(NormalUsedString.COMMA));
                sqlPart.put(columnToString(row), NormalUsedString.NOT + NormalUsedString.SPACE + NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + in + NormalUsedString.RIGHT_BRACKET);
            } else {
                sqlPart.put(columnToString(row), NormalUsedString.NOT + NormalUsedString.SPACE + NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + var + NormalUsedString.RIGHT_BRACKET);
            }

        }
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
    public AbstractBasicComparison<T, R, V> between(boolean condition, R row, Object leftVar, Object rightVar) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.BETWEEN, leftVar);
            sqlPart.put(columnToString(row), NormalUsedString.AND, rightVar);
        }
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
    public AbstractBasicComparison<T, R, V> betweenO(boolean condition, R row, Object leftVar, Object rightVar) {
        if (condition) {
            sqlPart.put(columnToString(row), NormalUsedString.BETWEEN, leftVar);
            sqlPart.put(columnToString(row), NormalUsedString.AND, rightVar);
        }
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
    public AbstractBasicComparison<T, R, V> groupBy(R... rows) {
        for (R row : rows) {
            String rowName = columnToString(row);
            Condition condition = new Condition();
            condition.setRowName(rowName);
            sqlPart.groupBy(condition);
        }
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
    public AbstractBasicComparison<T, R, V> orderBy(String order, R... rows) {
        for (R row : rows) {
            String rowName = columnToString(row);
            Condition condition = new Condition();
            condition.setRowName(rowName);
            condition.setType(order);
            sqlPart.orderBy(condition);
        }
        return this;
    }

    /**
     * @param limit 要取的数据数量
     * @return
     */
    @Override
    public AbstractBasicComparison<T, R, V> limit(long limit) {
        sqlPart.setLimitSql(" limit " + limit + NormalUsedString.SPACE);
        return this;
    }

    /**
     * @param skipped 跳过数据数量
     * @param limit   要取的数据数量
     * @return
     */
    @Override
    public AbstractBasicComparison<T, R, V> limit(long skipped, long limit) {
//        LIMIT 1,10
        sqlPart.setLimitSql(" limit " + skipped + NormalUsedString.COMMA + limit + NormalUsedString.SPACE);
        return this;
    }

    /**
     * @param lastSql 最后的sql
     * @return
     */
    @Override
    public AbstractBasicComparison<T, R, V> lastSql(String lastSql) {
        sqlPart.setLastSql(lastSql);
        return this;
    }

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
    public Class<T> getClassT() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
        return type;
    }

    /**
     * 列到字符串 表名.子段名
     *
     * @param row
     * @return
     */
    protected String columnToString(R row) {
        return (String) row;
    }


    protected Object valueToString(V v) {
        return v;
    }

}
