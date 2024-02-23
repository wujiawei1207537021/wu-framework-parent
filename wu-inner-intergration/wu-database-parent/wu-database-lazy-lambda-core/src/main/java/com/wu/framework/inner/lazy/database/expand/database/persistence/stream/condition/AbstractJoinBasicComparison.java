package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.enums.RowValueType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import org.springframework.util.ObjectUtils;

/**
 * describe: 关联查询条件
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/20 11:44 下午
 */
public abstract class AbstractJoinBasicComparison<T1, T2, R1, R2> implements BasicComparison<T1, R1, R2, AbstractJoinBasicComparison<T1, T2, R1, R2>> {


    protected SqlPart sqlPart = new SqlPart(NormalUsedString.SPACE + NormalUsedString.ON + NormalUsedString.SPACE);

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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> apply(boolean condition, String applySql, Object... values) {
        if (condition) {
            if (ObjectUtils.isEmpty(applySql)) {
                return this;
            }
            sqlPart.put(String.format(applySql, values), NormalUsedString.SPACE, RowValueType.EXPRESSION, NormalUsedString.SPACE);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> groupBy(R1... rows) {
        for (R1 row : rows) {
            String rowName = column1ToString(row);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> orderBy(String order, R1... rows) {
        for (R1 row : rows) {
            String rowName = column1ToString(row);
            Condition condition = new Condition();
            condition.setRowName(rowName);
            condition.setType(order);
            sqlPart.orderBy(condition);
        }
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> eq(boolean condition, R1 row, R2 var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.EQUALS, RowValueType.EXPRESSION, column2ToString(var));
            sqlPart.setPrimaryClass(getClassT2());
        }
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> or(boolean condition, R1 row, R2 var) {
        if (condition) {
            sqlPart.put(Condition.AndOr.OR, column1ToString(row), NormalUsedString.EQUALS, RowValueType.EXPRESSION, column2ToString(var));
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> eqo(boolean condition, R1 row, Object var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.EQUALS, var);
            sqlPart.setPrimaryClass(getClassT1());
        }
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> eqRighto(R2 row, Object var) {
        return eqRighto(true, row, var);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> eqRighto(boolean condition, R2 row, Object var) {
        if (condition) {
            sqlPart.put(column2ToString(row), NormalUsedString.EQUALS, var);
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> ne(boolean condition, R1 row, R2 var) {
        sqlPart.put(column1ToString(row), NormalUsedString.EXCLAMATION_MARK + NormalUsedString.EQUALS, RowValueType.EXPRESSION, column2ToString(var));
        sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> neo(boolean condition, R1 row, Object var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.EXCLAMATION_MARK + NormalUsedString.EQUALS, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
        }
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> neRighto(R2 row, Object var) {
        return neRighto(true, row, var);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> neRighto(boolean condition, R2 row, Object var) {
        if (condition) {
            sqlPart.put(column2ToString(row), NormalUsedString.EXCLAMATION_MARK + NormalUsedString.EQUALS, var);
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notNull(R1 row) {
        sqlPart.put(column1ToString(row), NormalUsedString.IS +
                NormalUsedString.SPACE + NormalUsedString.NOT + NormalUsedString.SPACE, null);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> isNull(R1 row) {
        sqlPart.put(column1ToString(row), NormalUsedString.IS +
                NormalUsedString.SPACE, null);
        return this;
    }

    /**
     * 右边数据是空
     *
     * @param row
     * @return
     */
    public AbstractJoinBasicComparison<T1, T2, R1, R2> isNullRight(R2 row) {
        sqlPart.put(column2ToString(row), NormalUsedString.IS +
                NormalUsedString.SPACE, null);
        return this;
    }

    /**
     * describe 不是空条件
     *
     * @param row 行
     * @return C 返回数据
     * @return
     */
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notNullRight(R2 row) {
        sqlPart.put(column2ToString(row), NormalUsedString.IS +
                NormalUsedString.SPACE + NormalUsedString.NOT + NormalUsedString.SPACE, null);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> gt(boolean condition, R1 row, R2 var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.RIGHT_CHEV, RowValueType.EXPRESSION, column2ToString(var));
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> gto(boolean condition, R1 row, Object var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.RIGHT_CHEV, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
        }
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    public AbstractJoinBasicComparison<T1, T2, R1, R2> gtRighto(boolean condition, R2 row, Object var) {
        if (condition) {
            sqlPart.put(column2ToString(row), NormalUsedString.RIGHT_CHEV, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
        }
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    public AbstractJoinBasicComparison<T1, T2, R1, R2> gtRighto(R2 row, Object var) {
        return gtRighto(true, row, var);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> lt(boolean condition, R1 row, R2 var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.LEFT_CHEV, RowValueType.EXPRESSION, column2ToString(var));
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> lto(boolean condition, R1 row, Object var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.LEFT_CHEV, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> ltRighto(boolean condition, R2 row, Object var) {
        if (condition) {
            sqlPart.put(column2ToString(row), NormalUsedString.LEFT_CHEV, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
        }
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    public AbstractJoinBasicComparison<T1, T2, R1, R2> ltRighto(R2 row, Object var) {
        return ltRighto(true, row, var);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> like(boolean condition, R1 row, R2 var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.LIKE, RowValueType.EXPRESSION, column2ToString(var));
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> likeO(boolean condition, R1 row, Object var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.LIKE, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> likeRightO(boolean condition, R2 row, Object var) {
        if (condition) {
            sqlPart.put(column2ToString(row), NormalUsedString.LIKE, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
        }
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    public AbstractJoinBasicComparison<T1, T2, R1, R2> likeRightO(R2 row, Object var) {

        return likeRightO(true, row, var);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notLike(boolean condition, R1 row, R2 var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.NOT + NormalUsedString.LIKE, RowValueType.EXPRESSION, column2ToString(var));
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notLikeO(boolean condition, R1 row, Object var) {
        if (condition) {
            sqlPart.put(column1ToString(row), NormalUsedString.NOT + NormalUsedString.LIKE, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notLikeRightO(boolean condition, R2 row, Object var) {
        if (condition) {
            sqlPart.put(column2ToString(row), NormalUsedString.NOT + NormalUsedString.LIKE, RowValueType.EXPRESSION, var);
            sqlPart.setPrimaryClass(getClassT2());
        }
        return this;
    }

    /**
     * @param row
     * @param var
     * @return describe not like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notLikeRightO(R2 row, Object var) {
        return notLikeRightO(true, row, var);
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> in(boolean condition, R1 row, R2 var) {
//        if (condition) {
//            if (Collection.class.isAssignableFrom(var.getClass())) {
//                final String in = ((Collection<?>) var).stream()
//                        .map(va -> NormalUsedString.SINGLE_QUOTE + va.toString() + NormalUsedString.SINGLE_QUOTE)
//                        .collect(Collectors.joining(NormalUsedString.COMMA));
//                sqlPart.put(columnNameSourceToString(row), NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + in + NormalUsedString.RIGHT_BRACKET);
//            } else {
//                sqlPart.put(columnNameSourceToString(row), NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + var + NormalUsedString.RIGHT_BRACKET);
//            }
//
//        }
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> inOr(boolean condition, R1 row, R2 var) {
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> ino(boolean condition, R1 row, Object var) {
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notIn(boolean condition, R1 row, R2 var) {
//        if (condition) {
//            if (Collection.class.isAssignableFrom(var.getClass())) {
//                final String in = ((Collection<?>) var).stream()
//                        .map(va -> NormalUsedString.SINGLE_QUOTE + va.toString() + NormalUsedString.SINGLE_QUOTE)
//                        .collect(Collectors.joining(NormalUsedString.COMMA));
//                sqlPart.put(columnNameSourceToString(row), NormalUsedString.NOT + NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + in + NormalUsedString.RIGHT_BRACKET);
//            } else {
//                sqlPart.put(columnNameSourceToString(row), NormalUsedString.NOT +NormalUsedString.IN, RowValueType.EXPRESSION, NormalUsedString.LEFT_BRACKET + var + NormalUsedString.RIGHT_BRACKET);
//            }
//
//        }
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> notIno(boolean condition, R1 row, Object var) {
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> between(boolean condition, R1 row, Object leftVar, Object rightVar) {
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> betweenO(boolean condition, R1 row, Object leftVar, Object rightVar) {
        return this;
    }

    /**
     * @param limit 要取的数据数量
     * @return
     */
    @Override
    public AbstractJoinBasicComparison<T1, T2, R1, R2> limit(long limit) {
        sqlPart.setLimitSql(" limit " + limit + NormalUsedString.SPACE);
        return this;
    }

    /**
     * @param skipped 跳过数据数量
     * @param limit   要取的数据数量
     * @return
     */
    @Override
    public AbstractJoinBasicComparison<T1, T2, R1, R2> limit(long skipped, long limit) {
//        LIMIT 1,10
        sqlPart.setLimitSql(" limit " + skipped + NormalUsedString.COMMA + limit + NormalUsedString.SPACE);
        return this;
    }

    /**
     * @param lastSql 最后的sql
     * @return
     */
    @Override
    public AbstractJoinBasicComparison<T1, T2, R1, R2> lastSql(String lastSql) {
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
     * 获取T1 的class
     *
     * @return
     */
    protected abstract Class<T1> getClassT1();

    /**
     * 获取T2 的class
     *
     * @return
     */
    protected abstract Class<T2> getClassT2();

    /**
     * row 转换成字符串
     *
     * @param row
     * @return
     */
    protected abstract String column1ToString(R1 row);

    /**
     * row 转换成字符串
     *
     * @param row
     * @return
     */
    protected abstract String column2ToString(R2 row);
}
