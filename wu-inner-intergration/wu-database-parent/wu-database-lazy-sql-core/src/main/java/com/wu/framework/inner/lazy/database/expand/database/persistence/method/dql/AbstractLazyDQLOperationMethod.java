package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql;

import com.wu.framework.inner.lazy.database.expand.database.persistence.method.AbstractLazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;

/**
 * description dql 抽象方法
 *
 * @author Jia wei Wu
 * @date 2023/01/15 21:48
 */
public abstract class AbstractLazyDQLOperationMethod extends AbstractLazyOperationMethod implements LazyDQLOperationMethod {
    protected AbstractLazyDQLOperationMethod(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    // 数据解析

}
