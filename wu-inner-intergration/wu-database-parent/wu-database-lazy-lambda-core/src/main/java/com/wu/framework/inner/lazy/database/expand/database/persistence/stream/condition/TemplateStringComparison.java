package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import org.springframework.util.ObjectUtils;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 基本对象比较、不使用函数直接进行比较
 * @date : 2021/8/21 6:38 下午
 */
public interface TemplateStringComparison<T, R, C> {


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
    C eqo(boolean condition, R row, Object var);

    default C eqo(R row, Object var) {
        return eqo(true, row, var);
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
    C neo(boolean condition, R row, Object var);

    default C neo(R row, Object var) {
        return neo(true, row, var);
    }

    /**
     * 忽略空数据
     *
     * @param row
     * @param var
     * @return
     */
    default C eqoIgnoreEmpty(R row, Object var) {
        return eqo(!ObjectUtils.isEmpty(var), row, var);
    }

    /**
     * @param
     * @return describe 大于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    C gto(boolean condition, R row, Object var);

    default C gto(R row, Object var) {
        return gto(true, row, var);
    }

    /**
     * @param
     * @return describe 小于
     * @author Jia wei Wu
     * @date 2021/8/15 4:52 下午
     **/
    C lto(boolean condition, R row, Object var);

    default C lto(R row, Object var) {
        return lto(true, row, var);
    }

    /**
     * @param
     * @return describe like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C likeO(boolean condition, R row, Object var);

    default C likeO(R row, Object var) {
        return likeO(true, row, var);
    }

    /**
     * @param
     * @return describe not like 条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C notLikeO(boolean condition, R row, Object var);

    default C notLikeO(R row, Object var) {
        return notLikeO(true, row, var);
    }

    /**
     * in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return
     */
    C ino(boolean condition, R row, Object var);

    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return
     */
    default C ino(R row, Object var) {
        return ino(true, row, var);
    }

    /**
     * not in 查询
     *
     * @param condition 判断
     * @param row       行
     * @param var       数据
     * @return
     */
    C notIno(boolean condition, R row, Object var);

    /**
     * in 查询
     *
     * @param row 行
     * @param var 数据
     * @return
     */
    default C notIno(R row, Object var) {
        return notIno(true, row, var);
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
    default C inIgnoreEmptyO(R row, Object var) {
        return ino(!ObjectUtils.isEmpty(var), row, var);
    }


    /**
     * @param
     * @param condition
     * @param row
     * @param rightObjectar
     * @return describe 区间
     * @author Jia wei Wu
     * @date 2021/7/16 9:45 下午
     **/
    C betweenO(boolean condition, R row, Object leftVar, Object rightVar);

    default C betweenO(R row, Object leftVar, Object rightVar) {
        return betweenO(true, row, leftVar, rightVar);
    }

//
//    /**
//     * @param
//     * @return describe 获取条件集合
//     * @author Jia wei Wu
//     * @date 2021/8/21 7:57 下午
//     **/
//    SqlPart getSqlPart();
//
//
//    /**
//     * 获取T 的class
//     *
//     * @return
//     */
//    Class<T> getClassT();
//
//
//    /**
//     * 列到字符串 表名.子段名
//     *
//     * @param row
//     * @return
//     */
//    default String columnToString(R row) {
//        return (String) row;
//    }
//
//
//    default Object valueToString(Object v) {
//        return v;
//    }

}
