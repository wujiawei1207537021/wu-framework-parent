package com.wu.framework.inner.file.wrapper;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wu.framework.inner.file.wrapper.config.WuFileProperties;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @ Description   :  支持不同连接
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/4/3 0003 13:47
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/4/3 0003 13:47
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


@Slf4j
@Data
public class WuFastDFSClientParentWrapper {


    private FastFileStorageClient storageClient;

    private WuFileProperties wuFileProperties;


    public WuFastDFSClientParentWrapper() {
    }

    public WuFastDFSClientParentWrapper(FastFileStorageClient storageClient, WuFileProperties wuFileProperties) {
        this.storageClient = storageClient;
        this.wuFileProperties = wuFileProperties;
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException IO异常
     */
    public String uploadFile(@NonNull MultipartFile file) {
        StorePath storePath = this.uploadFileForStorePath(file);
        String path = getResAccessUrl(storePath);
        if (StringUtils.isNotBlank(path)) {
            log.info("上传成功-路径：{}", path);
            return path;
        }
        return "";
    }

    public StorePath uploadFileForStorePath(@NonNull MultipartFile file) {
        log.info("文件开始上传······");
        log.info("文件名称：{}", file.getOriginalFilename());
        log.info("文件大小：{}", file.getSize());
        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            return storePath;
        } catch (Exception e) {
            e.getStackTrace();
            log.error("上传文件错误：" + e.getMessage());
            throw new RuntimeException(e.getCause());
        }
    }

    /**
     * 上传文件
     *
     * @param file File对象
     * @throws IOException IO异常
     */
    public String uploadFileStream(@NonNull File file) throws IOException {
        log.info("结果文件上传·····");
        log.info("文件名称：{}", file.getName());
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            StorePath storePath = storageClient.uploadFile(fileInputStream, fileInputStream.available(), FilenameUtils.getExtension(file.getName()), null);
            String path = getResAccessUrl(storePath);
            if (StringUtils.isNotBlank(path)) {
                log.info("上传成功-路径：{}", path);
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件错误：" + e.getMessage());
        }
        return "";
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension 文件
     * @return 文件
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    /**
     * 封装图片完整URL地址
     *
     * @param storePath 路径
     * @return 完整路径
     */
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return wuFileProperties.getRealAccessPath() + fileUrl;
    }

    /**
     * 传图片并同时生成一个缩略图 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException IO异常
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return
     */
    public Boolean deleteFile(@NonNull String fileUrl) {
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            log.info("删除文件地址：{}", storePath.getGroup() + storePath.getPath());
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            log.error("文件删除报错：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 删除文件
     * @param group
     * @param path
     * @return
     */
    public Boolean deleteFile(@NonNull String group,String path) {
        try {
            log.info("删除文件地址：{}", group+path);
            storageClient.deleteFile(group, path);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            log.error("文件删除报错：{}", e.getMessage());
            return false;
        }
    }

    public byte[] download(@NonNull String groupName, String  path) {
        DownloadByteArray downloadByteArray = new DownloadByteArray();
       return storageClient.downloadFile(groupName, path, downloadByteArray);
    }





}

