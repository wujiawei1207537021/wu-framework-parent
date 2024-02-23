package com.wu.framework.inner.lazy.persistence.converter;

import com.wu.framework.inner.lazy.persistence.domain.JavaVerification;

class SQLConverterTest {

    public static void main(String[] args) {
        SQLConverter.createMybatisResultMap(JavaVerification.class);
    }
}