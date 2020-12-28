package com.wu.framework.inner.lazy.database.io;

import java.io.InputStream;


public class CustomResources {
    /**
     * 根据传入的参数，获取一个字节输入流
     *
     * @param filePath
     * @return
     */
    public static InputStream getResourceAsStream(String filePath) {
        return CustomResources.class.getClassLoader().getResourceAsStream(filePath);
    }
}
