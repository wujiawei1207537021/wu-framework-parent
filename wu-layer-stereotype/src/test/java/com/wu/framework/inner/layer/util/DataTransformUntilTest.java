package com.wu.framework.inner.layer.util;


class DataTransformUntilTest {

    public static void main(String[] args) {
        final Integer integer = DataTransformUntil.simulationBaseBean(Integer.class, 300);
        System.out.println(integer);
    }
}