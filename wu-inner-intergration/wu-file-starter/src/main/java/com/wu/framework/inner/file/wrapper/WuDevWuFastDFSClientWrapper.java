package com.wu.framework.inner.file.wrapper;

import com.github.tobato.fastdfs.conn.ConnectionPoolConfig;
import com.github.tobato.fastdfs.conn.PooledConnectionFactory;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.wu.framework.inner.file.wrapper.config.dev.DevDefaultFastFileStorageClient;
import com.wu.framework.inner.file.wrapper.config.dev.DevTrackerConnectionManager;
import com.wu.framework.inner.file.wrapper.config.dev.WuDevFdfsFileProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * fastfds工具类
 *
 * @author Xiongxz
 * @date 2018-08-23 23:18
 **/
@Slf4j
public class WuDevWuFastDFSClientWrapper extends WuFastDFSClientParentWrapper {


    private DevDefaultFastFileStorageClient storageClient;

    private WuDevFdfsFileProperties wuDevFdfsFileProperties;


    @Autowired
    public WuDevWuFastDFSClientWrapper(PooledConnectionFactory pooledConnectionFactory,
                                       DevTrackerConnectionManager devTrackerConnectionManager,
                                       ConnectionPoolConfig connectionPoolConfig,
                                       ThumbImageConfig thumbImageConfig,
                                       WuDevFdfsFileProperties wuDevFdfsFileProperties) {

        this.storageClient = new DevDefaultFastFileStorageClient(pooledConnectionFactory, devTrackerConnectionManager, connectionPoolConfig, thumbImageConfig);
        this.wuDevFdfsFileProperties = wuDevFdfsFileProperties;
        this.setStorageClient(storageClient);
        this.setWuFileProperties(wuDevFdfsFileProperties);
    }


}

