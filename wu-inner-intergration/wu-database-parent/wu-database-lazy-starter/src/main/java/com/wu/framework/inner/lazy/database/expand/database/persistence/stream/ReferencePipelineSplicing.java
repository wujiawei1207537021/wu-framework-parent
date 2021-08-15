package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 拼接参考流
 * @date : 2021/8/8 11:44 上午
 */
public abstract class ReferencePipelineSplicing<T, R> extends ReferencePipelineLambdaTable<T, R>
        implements LambdaSplicing<T, R> {

    //    protected LambdaTable.LambdaTableType lambdaTableType;
//    protected Class<T> primaryTable;
    protected boolean noWhere = true;
    protected Boolean haveAnd = false;

    // 执行的SQL
    protected StringBuilder SQLExecuted;


    /**
     * description 左关联
     *
     * @param consumer @return
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:47 下午
     */
    @Override
    public LambdaTable<T, R> leftJoin(Consumer consumer) {
        return null;
    }

    /**
     * description 右关联
     *
     * @param consumer @return
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:48 下午
     */
    @Override
    public LambdaTable<T, R> rightJoin(Consumer consumer) {
        return null;
    }

    /**
     * @return
     * @describe 添加where 条件
     * @author Jia wei Wu
     * @date 2021/8/8 12:33 下午
     **/
    @Override
    public LambdaSplicing<T, R> where() {
        if (noWhere) {
            this.noWhere = false;
            this.SQLExecuted.append(" where ");
        }
        return this;
    }

    /**
     * description 参数过滤
     *
     * @param predicate@return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:46 下午
     */
    @Override
    public LambdaSplicing<T, R> filter(Predicate<? super T> predicate) {
        return null;
    }

    /**
     * @param predicate@return
     * @describe and条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    @Override
    public LambdaSplicing<T, R> and(Predicate<? super T> predicate) {
        return null;
    }

    /**
     * @param predicate@return
     * @describe or 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:43 下午
     **/
    @Override
    public LambdaSplicing<T, R> or(Predicate<? super T> predicate) {
        return null;
    }

//    /**
//     * @param condition
//     * @param row
//     * @param var
//     * @return
//     * @describe 等于条件
//     * @author Jia wei Wu
//     * @date 2021/7/16 9:44 下午
//     **/
//    @Override
//    public LambdaStreamCollector<T, R> eq(boolean condition, R row, Object var) {
//        if (condition) {
//            if (haveAnd) {
//                this.SQLExecuted.
//                        append(NormalUsedString.SPACE)
//                        .append(NormalUsedString.AND)
//                        .append(NormalUsedString.SPACE);
//            } else {
//                this.haveAnd = true;
//            }
//            this.SQLExecuted
//                    .append(NormalUsedString.SPACE)
//                    .append(row)
//                    .append(NormalUsedString.SPACE)
//                    .append(NormalUsedString.EQUALS)
//                    .append(NormalUsedString.SPACE)
//                    .append(var);
//        }
//        return this;
//    }



    /**
     * @return
     * @describe 获取执行的sql语句
     * @author Jia wei Wu
     * @date 2021/8/8 2:28 下午
     **/
    @Override
    public String getSqlStatement() {
        return this.SQLExecuted.toString();
    }

    /**
     * 预处理
     */
    protected void pretreatment() {

    }
}
