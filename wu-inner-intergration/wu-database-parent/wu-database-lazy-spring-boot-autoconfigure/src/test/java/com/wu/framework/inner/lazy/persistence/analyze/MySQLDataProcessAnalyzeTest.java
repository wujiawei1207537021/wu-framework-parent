package com.wu.framework.inner.lazy.persistence.analyze;

class MySQLDataProcessAnalyzeTest {

    public static void main(String[] args) {
        Object a = true;
        System.out.println(a.getClass().isAssignableFrom(boolean.class));
        Object b = 1;
        System.out.println(b.getClass().isAssignableFrom(int.class));
        Object c = 1l;
        System.out.println(c.getClass().isAssignableFrom(long.class));
    }

}