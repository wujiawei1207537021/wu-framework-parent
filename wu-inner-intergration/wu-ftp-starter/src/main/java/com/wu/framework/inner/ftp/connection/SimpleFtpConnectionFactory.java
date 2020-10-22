package com.wu.framework.inner.ftp.connection;

import com.wu.framework.inner.ftp.config.FtpConfig;
import com.wu.framework.inner.ftp.core.FtpTemplate;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.util.ObjectUtils;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-22 5:04 下午
 */
public class SimpleFtpConnectionFactory implements FtpConnectionFactory {

    private Logger log = LoggerFactory.getLogger(SimpleFtpConnectionFactory.class);

    private Integer connectTimeout = 1000 * 30;

    private String encoding = "UTF-8";

    private FtpConfig ftpConfig = null;

    private SocketClient socketClient;


    public SimpleFtpConnectionFactory() {

    }

    public SimpleFtpConnectionFactory(FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    public SimpleFtpConnectionFactory(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

//    private SimpleFtpConnectionFactory build() {
//       return new SimpleFtpConnectionFactory(ftpConfig);
//    }

    @Override
    public FtpConnection getConnection() {
        return getConnection(ftpConfig);
    }

    @Override
    public FtpConnection getConnection(String host, Integer port) {
        SocketClient socketClient = new FTPClient();
        socketClient.setConnectTimeout(connectTimeout);//设置连接超时时间
        try {
            socketClient.connect(host, port);// 连接FTP服务器
            return getConnection(socketClient);
        } catch (Exception e) {
            log.error("error to connect host:{},port:{},message:{}", host, port, e.getMessage());
            return null;
        }
    }

    @Override
    public FtpConnection getConnection(SocketClient socketClient) {
//        return () -> socketClient;
        return new AbstractFtpConnection(socketClient) {
        };
    }

    @Override
    public FtpConnection getConnection(FtpConfig ftpConfig) {
        if (ObjectUtils.isEmpty(ftpConfig)) {
            log.error("i can't  init config :{}", ftpConfig);
            return null;
        }
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(connectTimeout);//设置连接超时时间
        try {
            ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort());// 连接FTP服务器
            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());// 登陆FTP服务器
            ftpClient.setControlEncoding(ftpConfig.getEncoding());
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();//开启被动模式，否则文件上传不成功，也不报错
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("连接FTP失败，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功!");
            }
            return new AbstractFtpConnection(ftpClient) {
            };
        } catch (Exception e) {
            log.error("error to connect ftpConfig:{},message:{}", ftpConfig, e.getMessage());
            return null;
        }
    }

    public FtpTemplate build() {
        return new FtpTemplate(new SimpleFtpConnectionFactory(socketClient));
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException e) {
        return null;
    }
}
