package com.wu.framework.easy.stereotype.upsert.util;

import lombok.SneakyThrows;
import org.springframework.util.ObjectUtils;

import java.io.*;

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
        File file = new File(ObjectUtils.isEmpty(path)?fileName:path + File.separator + fileName);
        File parent = file.getParentFile();
        System.out.println("文件所在目录:" + file.getAbsolutePath());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) file.createNewFile();
        return new BufferedWriter(new FileWriter(file));
    }

    public static BufferedWriter createFile(String path, String prefix, String suffix, String fileName) {
        return createFile(path, prefix + fileName + suffix);
    }

    /**
    * @describe 获取文件内容
    * @param
    * @return
    * @author Jia wei Wu
    * @date 2021/3/3 10:46 下午
    **/
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
    * @describe 文件添加数据
    * @param
    * @return
    * @author Jia wei Wu
    * @date 2021/3/3 10:46 下午
    **/
    public static BufferedWriter addFileContent(String fileName) throws IOException {
         String fileContent = readFileContent(fileName);
         BufferedWriter bufferedWriter = createFile(null, fileName);
         bufferedWriter.write(fileContent);
         return bufferedWriter;

    }


}
