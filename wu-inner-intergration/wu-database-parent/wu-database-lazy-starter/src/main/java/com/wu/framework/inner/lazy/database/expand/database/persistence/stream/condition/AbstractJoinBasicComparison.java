package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import com.wu.framework.inner.layer.data.NormalUsedString;

/**
 * describe: 关联查询条件
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/20 11:44 下午
 */
public abstract class AbstractJoinBasicComparison<T1, T2, R1, R2> implements BasicComparison<T1, R1, R2, AbstractJoinBasicComparison<T1, T2, R1, R2>> {


    protected ConditionList conditionList = new ConditionList(NormalUsedString.SPACE + NormalUsedString.ON + NormalUsedString.SPACE);

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
        conditionList.put(column1ToString(row), NormalUsedString.EQUALS, ConditionList.RowValueType.EXPRESSION, column2ToString(var));
        conditionList.setEntityClass(getClassT2());
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
        conditionList.put(column1ToString(row), NormalUsedString.RIGHT_CHEV, ConditionList.RowValueType.EXPRESSION, column2ToString(var));
        conditionList.setEntityClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> lt(boolean condition, R1 row, R2 var) {
        conditionList.put(column1ToString(row), NormalUsedString.LEFT_CHEV, ConditionList.RowValueType.EXPRESSION, column2ToString(var));
        conditionList.setEntityClass(getClassT2());
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
    public AbstractJoinBasicComparison<T1, T2, R1, R2> like(boolean condition, R1 row, R2 var) {
        conditionList.put(column1ToString(row), NormalUsedString.LIKE, ConditionList.RowValueType.EXPRESSION, column2ToString(var));
        conditionList.setEntityClass(getClassT2());
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
     * @return describe 获取条件集合
     * @author Jia wei Wu
     * @date 2021/8/21 7:57 下午
     **/
    @Override
    public ConditionList getConditionList() {
        return conditionList;
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
