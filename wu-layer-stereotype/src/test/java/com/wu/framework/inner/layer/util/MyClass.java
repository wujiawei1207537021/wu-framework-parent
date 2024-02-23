package com.wu.framework.inner.layer.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class MyClass {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Class<?> clazz = MyClass.class;

        ProtectionDomain protectionDomain = clazz.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URL location = codeSource.getLocation();
        String path = location.getPath();

        String absolutePath = new File(path).getAbsolutePath();

        System.out.println("绝对路径：" + absolutePath);


    }
}
