//package com.wu.ftp.util;
//
///**
// * description
// * @Author Jia wei Wu
// * @Date 2020-05-22 2:31 下午
// */
//
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//import java.util.zip.ZipInputStream;
//import java.util.zip.ZipOutputStream;
//
///**
// * 文件解压缩工具类
// *
// * @Author: 孙龙
// * @Date: 2018/4/24.
// */
//@Slf4j
//public class ZipUtil {
//    public static final String BCP_PATTERN = ".*?\\.bcp";
//    public static final String HP_PATTERN = ".*?\\.hp";
//    public static final String ZIP_PATTERN = ".*?\\.zip";
//
//    /**
//     * 此方法描述的是：ZIP压缩
//     *
//     * @param source
//     *            源文件
//     * @param dest
//     *            压缩文件
//     * @version: 2015年3月2日 上午9:17:26
//     */
//    public static String zip(File source, File dest) {
//        ZipOutputStream out = null;
//        BufferedOutputStream bo = null;
//        try {
//            File zipParent = dest.getParentFile();
//            if (!zipParent.exists()) {
//                zipParent.mkdirs();
//            }
//            out = new ZipOutputStream(new FileOutputStream(dest));
//            bo = new BufferedOutputStream(out);
//            zip(out, source, source.getName(), bo);
//            return dest.getAbsolutePath();
//        } catch (Exception e) {
//            log.error("文件读取错误：" + e.getMessage());
//        } finally {
//            if (bo != null) {
//                try {
//                    bo.close();
//                } catch (IOException e) {
//                }
//            }
//            if (out != null) {
//                try {
//                    out.close(); // 输出流关闭
//                } catch (IOException e) {
//                }
//            }
//        }
//        return null;
//    }
//
//    public static void zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws Exception { // 方法重载
//        out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
//        FileInputStream in = new FileInputStream(f);
//        BufferedInputStream bi = new BufferedInputStream(in);
//        int b;
//        try {
//            while ((b = bi.read()) != -1) {
//                bo.write(b); // 将字节流写入当前zip目录
//            }
//        } finally {
//            bi.close();
//            in.close(); // 输入流关闭
//            bo.close();
//        }
//    }
//
//    /**
//     * 解压加密压缩文件
//     *
//     * @param zipFile
//     * @param outPath
//     * @param passwd
//     * @return
//     * @throws ZipException
//     * @throws IOException
//     */
//    public static List<File> unEncryptZip(File zipFile, String outPath, String fileNameRegexp, String passwd) {
//        List<File> extractedFileList = null;
//        try {
//            ZipFile zFile = new ZipFile(zipFile);
//
//            if (!zFile.isValidZipFile()) {
//                throw new ZipException("压缩文件不合法,可能被损坏.文件名：" + zipFile.getName());
//            }
//            File destDir = new File(outPath);
//            if (destDir.isDirectory() && !destDir.exists()) {
//                destDir.mkdir();
//            }
//            if (zFile.isEncrypted()) {
//                zFile.setPassword(passwd);
//            }
//            zFile.extractAll(outPath);
//            List<FileHeader> headerList = zFile.getFileHeaders();
//            extractedFileList = new ArrayList<File>();
//            for (FileHeader fileHeader : headerList) {
//                if (!fileHeader.isDirectory()) {
//                    if (!Pattern.matches(fileNameRegexp, fileHeader.getFileName())) {
//                        //解压到没用的文件，直接删除
//                        new File(destDir, fileHeader.getFileName()).delete();
//                        continue;
//                    }
//                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
//                }
//            }
//            FileHandlerMap.readedFilesWithMobiles.put(zipFile.getName(), 1);
//        } catch (ZipException e) {
//            log.error("解压文件时出错：" + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return extractedFileList;
//    }
//
//    /**
//     * 解压文件,并将匹配表达式的文件读取到内存
//     *
//     * @param zipFile
//     * @param outPath
//     * @return
//     * @throws ZipException
//     * @throws IOException
//     */
//    public static List<File> unZip(File zipFile, String outPath, String fileNameRegexp) {
//        List<File> extractedFileList = null;
//        try {
//            net.lingala.zip4j.core.ZipFile zFile = new net.lingala.zip4j.core.ZipFile(zipFile);
//            zFile.setFileNameCharset("GBK");
//            if (!zFile.isValidZipFile()) {
//                throw new ZipException("压缩文件不合法,可能被损坏.文件名：" + zipFile.getName());
//            }
//            File destDir = new File(outPath);
//            if (destDir.isDirectory() && !destDir.exists()) {
//                destDir.mkdir();
//            }
//
//            zFile.extractAll(outPath);
//
//            List<FileHeader> headerList = zFile.getFileHeaders();
//            extractedFileList = new ArrayList<>();
//            for (FileHeader fileHeader : headerList) {
//                if (!fileHeader.isDirectory()) {
//                    if (!Pattern.matches(fileNameRegexp, fileHeader.getFileName())) {
//                        new File(destDir, fileHeader.getFileName()).delete();//匹配错误直接删除
//                        continue;
//                    }
//                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
//                }
//            }
//            FileHandlerMap.readedFilesWithMobiles.put(zipFile.getName(), 1);
//        } catch (ZipException e) {
//            log.error("解压文件时出错：" + e.getMessage());
//            e.printStackTrace();
//        } finally {
//
//        }
//        return extractedFileList;
//    }
//
//    /**
//     * 此方法描述的是：获得ZIP文件同名解压目录
//     *
//     * @version: 2015年3月5日 下午2:10:41
//     */
//    public static String getUnpackForder(File zipFile) {
//        String filePath = zipFile.getAbsolutePath();
//        return filePath.substring(0, filePath.lastIndexOf("."));
//    }
//
//    /**
//     * 此方法描述的是：获得ZIP文件指定解压目录
//     *
//     * @version: 2015年3月6日 下午10:28:36
//     */
//    public static String getUnpackForder(File zipFile, String subDir) {
//        return zipFile.getParent() + File.separator + subDir;
//    }
//
//    /**
//     * 批量读取文件，每一行解析为一个字符串；
//     *
//     * @param files
//     * @return
//     */
//    public static List<String> batchReadFile(List<File> files, String charset) throws Exception {
//        List<String> textList = new ArrayList<>();
//        for (File file : files) {
//            List<String> list = readTextFile(file, charset);
//            textList.addAll(list);
//
//        }
//        return textList;
//    }
//
//    /**
//     * 将文件中内容读取到内存中
//     *
//     * @param file
//     * @return
//     */
//    public static List<String> readTextFile(File file, String charset) throws Exception {
//        List<String> list = new ArrayList<>();
//        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset);
//        BufferedReader bufferedReader = new BufferedReader(reader);
//        String lineText;
//        while ((lineText = bufferedReader.readLine()) != null) {
//            list.add(lineText);
//        }
//        return list;
//
//    }
//
//}
