package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.AbstractBasicComparison;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/9 17:28
 */
public abstract class AbstractSelectBasicComparison<T, R, V>
        extends AbstractBasicComparison<T, R, V>
        implements SelectBasicComparison<T, R, V, AbstractBasicComparison<T, R, V>> {

}
