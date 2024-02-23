package com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.method.AbstractLazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;

/**
 * description ddl抽象方法
 *
 * @author Jia wei Wu
 * @date 2023/01/15 21:48
 */
public abstract class AbstractLazyDDLOperationMethod extends AbstractLazyOperationMethod implements LazyDDLOperationMethod {
    public AbstractLazyDDLOperationMethod(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }
}
