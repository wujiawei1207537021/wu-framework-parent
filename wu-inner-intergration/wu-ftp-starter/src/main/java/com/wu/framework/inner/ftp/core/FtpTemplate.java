package com.wu.framework.inner.ftp.core;

import com.wu.framework.inner.ftp.connection.FtpConnectionFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-22 2:56 下午
 */
public class FtpTemplate<K, V> extends FtpAccessor implements FtpOperations<K, V>, BeanClassLoaderAware {


    public FtpTemplate(FtpConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {

    }
}
