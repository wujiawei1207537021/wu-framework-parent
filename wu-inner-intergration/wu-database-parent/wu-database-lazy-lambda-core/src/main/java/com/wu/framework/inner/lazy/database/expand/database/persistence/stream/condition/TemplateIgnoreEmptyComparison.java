package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

import org.springframework.util.ObjectUtils;

/**
 * description 模版条件
 *
 * @author Jia wei Wu
 * @date 2022/08/17 4:12 下午
 */
public interface TemplateIgnoreEmptyComparison<T, R, V, C extends TemplateIgnoreEmptyComparison<T, R, V, C>> extends TemplateComparison<T, R, V, C> {


    /**
     * 忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C eqIgnoreEmpty(R row, V var) {
        return eq(!ObjectUtils.isEmpty(var), row, var);
    }

    /**
     * 大于比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C gtIgnoreEmpty(R row, V var) {
        return gt(!ObjectUtils.isEmpty(var), row, var);
    }


    /**
     * 小于比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C ltIgnoreEmpty(R row, V var) {
        return lt(!ObjectUtils.isEmpty(var), row, var);
    }


    /**
     * like比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C likeIgnoreEmpty(R row, V var) {
        return like(!ObjectUtils.isEmpty(var), row, var);
    }

    /**
     * no like 比较忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C notLikeIgnoreEmpty(R row, V var) {
        return notLike(!ObjectUtils.isEmpty(var), row, var);
    }


    /**
     * in 查询 忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C inIgnoreEmpty(R row, V var) {
        return in(!ObjectUtils.isEmpty(var), row, var);
    }


    /**
     * not in 查询 忽略空数据
     *
     * @param row 行
     * @param var 数据
     * @return C
     */
    default C notInIgnoreEmpty(R row, V var) {
        return notIn(!ObjectUtils.isEmpty(var), row, var);
    }


}
