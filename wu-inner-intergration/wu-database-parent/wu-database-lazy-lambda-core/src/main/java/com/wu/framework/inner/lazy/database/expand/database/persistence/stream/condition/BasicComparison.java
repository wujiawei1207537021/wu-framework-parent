package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 基本比较
 * @date : 2021/8/21 6:38 下午
 */
public interface BasicComparison<T, R, V, C extends BasicComparison<T, R, V, C>>
        extends TemplateStringComparison<T, R, C>,
        TemplateIgnoreEmptyComparison<T, R, V, C>,
        TemplateComparison<T, R, V, C> {


}
