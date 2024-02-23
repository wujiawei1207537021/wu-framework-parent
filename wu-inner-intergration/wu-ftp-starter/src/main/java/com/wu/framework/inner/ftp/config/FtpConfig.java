package com.wu.framework.inner.ftp.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description
 *
 * @Author Jia wei Wu
 * @Date 2020-05-22 3:09 下午
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig implements InitializingBean {


    /**
     * 主机IP地址
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    private Integer connectTimeout = 1000 * 30;

    private String encoding = "UTF-8";

    @Override
    public void afterPropertiesSet() throws Exception {

    }
//
//    @Getter
//    private FTP ftp;
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        FTPClient ftpClient = new FTPClient();
//        ftpClient.setConnectTimeout(connectTimeout);//设置连接超时时间
//        try {
//            ftpClient.connect(host, port);// 连接FTP服务器
//        }catch (Exception e){
//            log.error("error to connect host:{},port:{},message:{}",host,port,e.getMessage());
//            return;
//        }
//        ftpClient.login(username, password);// 登陆FTP服务器
//        ftpClient.setControlEncoding(encoding);
//        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//        ftpClient.enterLocalPassiveMode();//开启被动模式，否则文件上传不成功，也不报错
//        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
//            log.info("连接FTP失败，用户名或密码错误。");
//            ftpClient.disconnect();
//        } else {
//            this.ftp = ftpClient;
//            log.info("FTP连接成功!");
//        }
//    }
}
