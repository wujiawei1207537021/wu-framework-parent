package com.wu.framework.inner.ftp.connection;

import com.wu.framework.inner.ftp.config.FtpConfig;
import org.apache.commons.net.SocketClient;
import org.springframework.dao.support.PersistenceExceptionTranslator;

/**
 * description
 *
 * @Author Jia wei Wu
 * @Date 2020-05-22 3:01 下午
 */
public interface FtpConnectionFactory extends PersistenceExceptionTranslator {

    FtpConnection getConnection();

    FtpConnection getConnection(String host, Integer port);

    FtpConnection getConnection(SocketClient socketClient);

    FtpConnection getConnection(FtpConfig ftpConfig);
}
