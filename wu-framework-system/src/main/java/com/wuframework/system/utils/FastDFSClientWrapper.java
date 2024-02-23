//package com.wuframework.system.utils;
//
//import com.github.tobato.fastdfs.domain.StorePath;
//import com.github.tobato.fastdfs.service.FastFileStorageClient;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.charset.Charset;
//
///**
// * @description: fastfds工具类
// * @author: Xiongxz
// * @create: 2018-08-23 23:18
// *{@link com.wuframework.file.wrapper.FastDFSClientWrapper}
// **/
//@Deprecated
//@Slf4j
////@Component
//public class FastDFSClientWrapper {
//
//    @Autowired
//    private FastFileStorageClient storageClient;
//
//    /**
//     * 上传文件
//     *
//     * @param file 文件对象
//     * @return 文件访问地址
//     * @throws IOException
//     */
//    public String uploadFile(final MultipartFile file) {
//        log.info("文件开始上传······");
//        log.info("文件名称：{}", file.getOriginalFilename());
//        log.info("文件大小：{}", file.getSize());
//        try {
//            final StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
//                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
//            final String path = getResAccessUrl(storePath);
//            if (StringUtils.isNotBlank(path)) {
//                log.info("上传成功-路径：{}", path);
//                return path;
//            }
//        } catch (final IOException e) {
//            e.printStackTrace();
//            log.error("文件 IO 读写报错!");
//
//        } catch (final Exception e) {
//            e.printStackTrace();
//            log.error("上传文件错误：", e.getMessage());
//        }
//        return "";
//    }
//
//    /**
//     * 上传文件
//     *
//     * @param file File对象
//     * @throws IOException
//     */
//    public String uploadFileStream(final File file) throws IOException {
//        log.info("结果文件上传·····");
//        log.info("文件名称：{}", file.getName());
//        final FileInputStream fileInputStream = new FileInputStream(file);
//        try {
//            final StorePath storePath = storageClient.uploadFile(fileInputStream, fileInputStream.available(), FilenameUtils.getExtension(file.getName()), null);
//            final String path = getResAccessUrl(storePath);
//            if (StringUtils.isNotBlank(path)) {
//                log.info("上传成功-路径：{}", path);
//                return path;
//            }
//        } catch (final Exception e) {
//            e.printStackTrace();
//            log.error("上传文件错误：", e.getMessage());
//        }
//        return "";
//    }
//
//    /**
//     * 将一段字符串生成一个文件上传
//     *
//     * @param content       文件内容
//     * @param fileExtension
//     * @return
//     */
//    public String uploadFile(final String content, final String fileExtension) {
//        final byte[] buff = content.getBytes(Charset.forName("UTF-8"));
//        final ByteArrayInputStream stream = new ByteArrayInputStream(buff);
//        final StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
//        return getResAccessUrl(storePath);
//    }
//
//    /**
//     * 封装图片完整URL地址
//     *
//     * @param storePath
//     * @return
//     */
//    private String getResAccessUrl(final StorePath storePath) {
//        final String fileUrl = storePath.getFullPath();
//        return fileUrl;
//    }
//
//    /**
//     * 传图片并同时生成一个缩略图 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
//     *
//     * @param file 文件对象
//     * @return 文件访问地址
//     * @throws IOException
//     */
//    public String uploadImageAndCrtThumbImage(final MultipartFile file) throws IOException {
//        final StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
//                FilenameUtils.getExtension(file.getOriginalFilename()), null);
//        return getResAccessUrl(storePath);
//    }
//
//    /**
//     * 删除文件
//     *
//     * @param fileUrl 文件访问地址
//     * @return
//     */
//    public Boolean deleteFile(final String fileUrl) {
//        if (StringUtils.isEmpty(fileUrl)) {
//            return false;
//        }
//        try {
//            final StorePath storePath = StorePath.praseFromUrl(fileUrl);
//            log.info("删除文件地址：{}", storePath.getGroup() + storePath.getPath());
//            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
//            return true;
//        } catch (final Exception e) {
//            e.printStackTrace();
//            log.error("文件删除报错：{}", e.getMessage());
//            return false;
//        }
//    }
//}
//
