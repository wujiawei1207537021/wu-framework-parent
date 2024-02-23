package com.wu.framework.inner.ftp.util;

/**
 * description
 *
 * @Author Jia wei Wu
 * @Date 2020-05-22 2:30 下午
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * FTP文件路由过滤器，
 * 将只下载当天，且下载目录中不存在的文件
 */
@Slf4j
//@Component
public class WifiDownloadFileFilter implements GenericFileFilter<Object> {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Value("${ftp.wifi-info.unzip-temp-dir}")
    private String tempPath;

    @Value("${ftp.wifi-info.local-files-prefix}")
    private String filePrefix;

    @Value("${ftp.wifi-info.local-files-suffix}")
    private String fileSuffix;

    /**
     * 过滤下载文件
     *
     * @param genericFile
     * @return true=下载，false=不下载
     */
    @Override
    public boolean accept(GenericFile<Object> genericFile) {
        long lastModified = genericFile.getLastModified();
        String fileName = genericFile.getFileName();
        return isLatestFile(lastModified) && !isInLocalDir(fileName) ? true : false;
    }

    /**
     * 文件是否已在本地目录中
     *
     * @param fileName
     * @return true=不存在   false=已存在了
     */
    private boolean isInLocalDir(String fileName) {
        try {

            //获取本地文件夹中已下载的文件名
            File fileDir = new File(tempPath);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            String path = tempPath + filePrefix + simpleDateFormat.format(new Date()) + fileSuffix;
            File file = new File(path);

            if (!file.exists()) {//如果不存在就创建
                file.createNewFile();
                FileUtil.appendMethod(path, fileName + "\r\n");
                return true;
            }

            List<String> localFileNames = FileUtil.readFileByLines(file);
            if (localFileNames.contains(fileName)) {
                return false;
            } else {
                FileUtil.appendMethod(path, fileName + "\r\n");
                return true;
            }
        } catch (Exception e) {
            log.error("获取本地已下载文件列表出错", e);
            return false;
        }
    }

    /**
     * 文件是否为今天的数据
     *
     * @param lastModified
     * @return true=是今天的文件 false=不是今天的文件
     */
    public boolean isLatestFile(long lastModified) {
        Date lastDate = new Date(lastModified);
        String lastDateStr = simpleDateFormat.format(lastDate);

        String todayStr = simpleDateFormat.format(new Date());
        return todayStr.equals(lastDateStr);
    }

}

