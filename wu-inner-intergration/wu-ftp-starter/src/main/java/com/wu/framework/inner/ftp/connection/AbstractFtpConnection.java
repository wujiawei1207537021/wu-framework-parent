package com.wu.framework.inner.ftp.connection;

import com.wu.framework.inner.ftp.core.FtpTemplate;
import org.apache.commons.net.SocketClient;

/**
 * @Description
 * @Author Jia wei Wu
 * @Date 2020-05-22 4:47 下午
 */
public abstract class AbstractFtpConnection extends SocketClient implements FtpConnection {

    private final SocketClient socketClient;

    public AbstractFtpConnection(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public SocketClient getSocketClient() {
        return socketClient;
    }

    public FtpTemplate build() {
        return new FtpTemplate(new SimpleFtpConnectionFactory(socketClient));
    }
}
