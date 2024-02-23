package com.wu.framework.inner.layer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jia wei Wu
 */

@Getter
@AllArgsConstructor
public enum DefaultIEnum implements IEnum {
    /**
     * 默认操作
     */
    DEFAULT;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getItem() {
        return null;
    }
}
