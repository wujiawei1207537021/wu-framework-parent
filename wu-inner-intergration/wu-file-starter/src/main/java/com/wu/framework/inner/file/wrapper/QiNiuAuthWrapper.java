package com.wu.framework.inner.file.wrapper;


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Json;
import com.wu.framework.inner.file.wrapper.config.QiNiuAuthProperties;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * fastfds工具类
 *
 * @author Xiongxz
 * @date 2018-08-23 23:18
 **/
@Slf4j
@Data
public class QiNiuAuthWrapper {



    private QiNiuAuthProperties qiNiuAuthProperties;


    public QiNiuAuthWrapper() {
    }

    @Autowired
    public QiNiuAuthWrapper(QiNiuAuthProperties qiNiuAuthProperties) {
        this.qiNiuAuthProperties = qiNiuAuthProperties;
    }

    /**
     * 七牛云上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */


    //七牛云上传
    public String uploadQiniuFile(@NonNull MultipartFile file) throws IOException {
        //配置地区（zone2为华南）
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        final InputStream byteInputStream = file.getInputStream();
        String upToken = qiNiuAuthProperties.create();
        try {
            String key = System.currentTimeMillis() + file.getOriginalFilename();
            Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet =
                    Json.decode(response.bodyString(), DefaultPutRet.class);
            return qiNiuAuthProperties.getAccessPath()+ putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            System.err.println(r.bodyString());

        }
        return "";
    }




}

