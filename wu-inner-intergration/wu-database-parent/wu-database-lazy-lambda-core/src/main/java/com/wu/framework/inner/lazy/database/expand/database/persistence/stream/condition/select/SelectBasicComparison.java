package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;

/**
 * describe : 查询条件选择器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/9 16:52
 */
public interface SelectBasicComparison<T, R, V, C extends BasicComparison<T, R, V, C>>
        extends BasicComparison<T, R, V, C> {
//
//    /**
//     * 分组
//     *
//     * @param rows 分组字段
//     * @return C
//     * @author Jia wei Wu
//     * @date 2022/6/26 21:48
//     */
//    C groupBy(R... rows);
//
//    /**
//     * describe 排序
//     *
//     * @param order 排序 DESC或者ASC
//     * @param rows  排序字段
//     * @return C
//     * @author Jia wei Wu
//     * @date 2022/6/26 21:48
//     **/
//    C orderBy(String order, R... rows);
//
//    /**
//     * describe 降序
//     *
//     * @param rows 排序字段
//     * @return C
//     * @author Jia wei Wu
//     * @date 2022/6/26 21:51
//     **/
//    default C orderByDesc(R... rows) {
//        return orderBy(NormalUsedString.DESC, rows);
//    }
//
//    /**
//     * describe 升序
//     *
//     * @param rows 排序字段
//     * @return C
//     * @author Jia wei Wu
//     * @date 2022/6/26 21:51
//     **/
//    default C orderByAsc(R... rows) {
//        return orderBy(NormalUsedString.ASC, rows);
//    }

}
