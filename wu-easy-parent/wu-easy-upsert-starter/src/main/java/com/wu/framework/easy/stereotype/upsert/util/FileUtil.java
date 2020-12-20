package com.wu.framework.easy.stereotype.upsert.util;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/12/6 4:46 下午
 */
public class FileUtil {

    /**
     * @param path     路径
     * @param fileName 文件名
     * @return BufferedWriter
     * @describe : 创建文件 返回字节写入对象 需要下游调用close()
     * @author Jia wei Wu
     * @date 2020/12/6 4:47 下午
     **/
    @SneakyThrows
    public static BufferedWriter createFile(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        File parent = file.getParentFile();
        System.out.println("文件所在目录:" + file.getAbsolutePath());
        if (!parent.exists()) {
            parent.mkdir();
        }
        if (!file.exists()) file.createNewFile();
        return new BufferedWriter(new FileWriter(file));
    }

    public static BufferedWriter createFile(String path, String prefix, String suffix, String fileName) {
        return createFile(path, prefix + fileName + suffix);
    }


}
