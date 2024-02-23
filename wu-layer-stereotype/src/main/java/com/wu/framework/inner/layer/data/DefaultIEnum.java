package com.wu.framework.inner.layer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultIEnum implements IEnum {
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
