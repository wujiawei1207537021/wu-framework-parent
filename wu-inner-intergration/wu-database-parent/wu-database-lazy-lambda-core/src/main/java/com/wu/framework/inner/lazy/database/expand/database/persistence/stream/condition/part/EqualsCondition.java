package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part;

import lombok.experimental.Accessors;

/**
 * 等于条件
 */
@Accessors(chain = true)
public class EqualsCondition extends Condition {
    public EqualsCondition() {
        super("=");
    }


}