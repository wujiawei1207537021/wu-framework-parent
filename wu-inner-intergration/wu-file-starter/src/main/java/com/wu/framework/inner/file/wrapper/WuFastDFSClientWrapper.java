package com.wu.framework.inner.file.wrapper;

import com.github.tobato.fastdfs.service.FastFileStorageClient;

import com.wu.framework.inner.file.wrapper.config.WuFdfsFileProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * fastfds工具类
 *
 * @author Xiongxz
 * @date 2018-08-23 23:18
 **/
@Slf4j
public class WuFastDFSClientWrapper extends WuFastDFSClientParentWrapper {


    @Autowired
    public WuFastDFSClientWrapper(FastFileStorageClient storageClient, WuFdfsFileProperties wuFdfsFileProperties) {
        super(storageClient, wuFdfsFileProperties);
    }



}

