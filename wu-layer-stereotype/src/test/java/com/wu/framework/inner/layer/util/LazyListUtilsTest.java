package com.wu.framework.inner.layer.util;


import java.util.ArrayList;
import java.util.List;

class LazyListUtilsTest {


    public static void main(String[] args) {
        List<Integer> strings=new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            strings.add(i);
        }
        for (List<Integer> stringList : LazyListUtils.splitList(strings, 100)) {
            for (Integer i : stringList) {
                System.out.println(i);
            }
        }
    }

}