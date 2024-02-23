package com.wu.framework.inner.layer.util;


import com.wu.framework.inner.layer.data.NormalUsedString;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/12/6 4:46 下午
 */
public class FileUtil {

    /**
     * @param path     路径
     * @param fileName 文件名
     * @return BufferedWriter
     * describe : 创建文件 返回字节写入对象 需要下游调用close()
     * @author Jia wei Wu
     * @date 2020/12/6 4:47 下午
     */
    public static File createFile(String path, String fileName) {
        File file = new File(ObjectUtils.isEmpty(path) ? fileName : path + File.separator + fileName);
        File parent = file.getParentFile();
        System.out.println("文件所在目录:" + file.getAbsolutePath());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * @param file 文件名
     * @return BufferedWriter
     * describe : 创建文件 返回字节写入对象 需要下游调用close()
     * @author Jia wei Wu
     * @date 2020/12/6 4:47 下午
     **/
    public static BufferedWriter createFileBufferedWriter(File file) {
        try {
            return new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @param path     路径
     * @param fileName 文件名
     * @return BufferedWriter
     * describe : 创建文件 返回字节写入对象 需要下游调用close()
     * @author Jia wei Wu
     * @date 2020/12/6 4:47 下午
     **/
    public static BufferedWriter createFileBufferedWriter(String path, String fileName) {
        File file = createFile(path, fileName);
        return createFileBufferedWriter(file);
    }

    /**
     * description: 创建文件
     *
     * @param path     路径
     * @param fileName 文件名称
     * @param prefix   文件后缀
     * @param suffix   文件前缀
     * @return
     * @author 吴佳伟
     * @date: 18.1.23 17:50
     */
    public static BufferedWriter createFileBufferedWriter(String path, String prefix, String suffix, String fileName) {
        return createFileBufferedWriter(path, prefix + fileName + suffix);
    }

    /**
     * @param localClass 本地class FileUtil.class
     * @return describe Class local path  example /Users/wujiawei/IdeaProjects/wu-framework-parent/wu-layer-stereotype/target/classes/
     * @author Jia wei Wu
     * @date 2021/3/3 10:46 下午
     **/
    public static <T> String readLocalClassFolder(Class<T> localClass) {
        Assert.notNull(localClass, "localClass 不能为空");
        URL resource = localClass.getResource(NormalUsedString.SLASH);
        // 写入class文件
        String resourceFilePrefix = resource.getFile();
        return resourceFilePrefix;
    }

    /**
     * @param localClass 本地class FileUtil.class
     * @param <T>        class 类型
     * @return 当前class 对应的在src下文件夹地址
     */
    public static <T> String readLocalSrcMainClassFolder(Class<T> localClass) {
        Assert.notNull(localClass, "localClass 不能为空");
        URL resource = localClass.getResource(NormalUsedString.SLASH);
        // 写入class文件
        String resourceFilePrefix = resource.getFile();
        return resourceFilePrefix.replace(
                File.separator + "target" + File.separator + "classes" + File.separator,
                File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator);
    }

    /**
     * @param localClass 本地class FileUtil.class
     * @param <T>        class 类型
     * @return 当前class 对应的在src下文件地址
     */
    public static <T> String readLocalSrcMainClassPath(Class<T> localClass) {
        Assert.notNull(localClass, "localClass 不能为空");
        URL resource = localClass.getResource(NormalUsedString.SLASH);
        // 写入class文件
        String resourceFilePrefix = resource.getFile();
        String name = localClass.getName();
        // 文件相对路径
        String relativePath = name.replace(NormalUsedString.DOT, File.separator);
        return resourceFilePrefix
                .replace(
                        File.separator + "target" + File.separator + "classes" + File.separator,
                        File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator)
                .replace(
                        File.separator + "target" + File.separator + "test-classes" + File.separator,
                        File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator)
                + relativePath + NormalUsedString.DOT_JAVA;
    }

    /**
     * @param fileName 文件绝对地址
     * @return describe 获取文件内容
     * @author Jia wei Wu
     * @date 2021/3/3 10:46 下午
     **/
    public static String readFileContent(String fileName) {
        return String.join("", readFileLineContent(fileName).values());
    }

    /**
     * @param fileName 文件绝对地址
     * @return Map<Long 行数, String内容>
     */
    public static Map<Integer/*行数*/, String/*内容*/> readFileLineContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        Map<Integer/*行数*/, String/*内容*/> fileLineContentMap = new LinkedHashMap<>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            Integer line = 1;
            while ((tempStr = reader.readLine()) != null) {
                fileLineContentMap.put(line, tempStr);
                line++;
            }
            reader.close();
            return fileLineContentMap;
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
        return fileLineContentMap;
    }

    /**
     * @param
     * @return describe 文件添加数据
     * @author Jia wei Wu
     * @date 2021/3/3 10:46 下午
     **/
    public static BufferedWriter addFileContent(String fileName) throws IOException {
        String fileContent = readFileContent(fileName);
        BufferedWriter bufferedWriter = createFileBufferedWriter(null, fileName);
        bufferedWriter.write(fileContent);
        return bufferedWriter;
    }


    /**
     * description 修改文件 删除某一行含有指定字符
     *
     * @param path       文件路径
     * @param chart      字段
     * @param replaceMap 替换的数据 key 老数据  value 新数据
     * @return 临时文件地址为
     * @exception/throws
     */
    public static void modifyLineWithChart(String path, String chart, Map<String, String> replaceMap) throws IOException {
        Assert.notNull(path, "path 参数不能为空");
        File file = new File(path);
        modifyLineWithChart(file, chart, replaceMap);
    }

    /**
     * description 修改文件 删除某一行含有指定字符
     *
     * @param file       文件
     * @param chart      字段
     * @param replaceMap 替换的数据 key 老数据  value 新数据
     * @return 临时文件地址为
     * @exception/throws
     */
    public static void modifyLineWithChart(File file, String chart, Map<String, String> replaceMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        File tempFile = new File(file.getPath() + ".temp");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
        boolean hasChange = false;
        Set<String> oldChars = replaceMap.keySet();
        // 一行一行的读
        String line = null;
        while ((line = reader.readLine()) != null) {

            // 删除行
            if (!line.contains(chart)) {

                // 字符串替换
                for (String oldChar : oldChars) {
                    if (line.contains(oldChar)) {
                        String newChar = replaceMap.get(oldChar);
                        line = line.replace(oldChar, newChar);
                        hasChange = true;
                    }
                }
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                hasChange = true;
            }
        }
        bufferedWriter.close();
        reader.close();
        if (!hasChange) {
            tempFile.deleteOnExit();
        }
    }


    /**
     * description 聚合文件夹下所有文件到一个文件中（文件名为文件夹名称并且与文件夹同级）
     *
     * @param folderPath      文件夹地址
     * @param depth           是否深度解析 true 深入递归  false 当前文件夹下文件聚合
     * @param aggregateSuffix 聚合文件名称后缀 如：.text
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/4/9 9:49 上午
     */
    public static void aggregateAllFilesOnFolder(String folderPath, boolean depth, String aggregateSuffix) throws IOException {
        Assert.notNull(aggregateSuffix, "文件聚合文件名称后缀 参数不能为空");
        BufferedWriter bufferedWriter = aggregateAllFilesOnFolder(folderPath, depth, null, aggregateSuffix);
        bufferedWriter.close();
    }

    /**
     * description 聚合文件夹下所有文件到一个文件中（文件名为文件夹名称并且与文件夹同级）
     *
     * @param folderPath      文件夹地址
     * @param depth           是否深度解析 true 深入递归  false 当前文件夹下文件聚合
     * @param bufferedWriter  聚合文件夹输出流
     * @param aggregateSuffix 集合文件名称后缀 如：.text
     * @return 需要手动关闭流
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/4/9 9:49 上午
     */
    public static BufferedWriter aggregateAllFilesOnFolder(String folderPath, boolean depth, BufferedWriter bufferedWriter, String aggregateSuffix) throws IOException {
        Assert.notNull(folderPath, "文件夹地址 参数不能为空");
        File folderFile = new File(folderPath);
        if (!folderFile.exists()) {
            throw new RuntimeException("文件夹地址不存在");
        }
        if (folderFile.isDirectory()) {
            if (ObjectUtils.isEmpty(bufferedWriter)) {
                File tempFile = new File(
                        folderFile.getParent() +
                                File.separator +
                                folderFile.getName() + NormalUsedString.DOT + aggregateSuffix);
                bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
            }
            // 是文件夹
            List<File> fileList = Arrays.stream(folderFile.listFiles()).sorted().collect(Collectors.toList());
            for (File file : fileList) {
                System.out.println("读取文件名称:" + file.getName());
                if (file.isDirectory()) {
                    if (depth) {
                        aggregateAllFilesOnFolder(file.getPath(), true, bufferedWriter, aggregateSuffix);
                    }
                } else {
                    // 读取文件
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                    reader.close();
                }
            }
        } else {
            if (ObjectUtils.isEmpty(bufferedWriter)) {
                File parentFile = folderFile.getParentFile();
                File tempFile = new File(
                        parentFile.getParent() +
                                File.separator +
                                parentFile.getName() + NormalUsedString.DOT + aggregateSuffix);
                bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
            }
            aggregateAllFilesOnFolder(folderFile.getParent(), depth, bufferedWriter, aggregateSuffix);
        }
        return bufferedWriter;
    }

    /**
     * description: 聚合多个文件为一个文件
     *
     * @param fileList      文件
     * @param aggregateFile 聚合后文件
     * @return
     * @author 吴佳伟
     * @date: 1.6.23 11:35
     */
    public static void aggregateFiles(List<File> fileList, File aggregateFile) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(aggregateFile));

        for (File file : fileList) {
            if (file == null) {
                continue;
            }
            // 读取文件
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            reader.close();
        }
        bufferedWriter.close();

    }

    public static void main(String[] args) throws IOException {
        modifyLineWithChart("/Users/wujiawei/IdeaProjects/middleground/middleground-tenant-free/sql/crm.sql",
                "DROP TABLE IF EXISTS", new HashMap<String, String>() {
                    {
                        put("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
                    }
                });

        aggregateAllFilesOnFolder("/Users/wujiawei/IdeaProjects/middleground/middleground-tenant-free/sql/middleground", true,
                "sql");
    }


}
