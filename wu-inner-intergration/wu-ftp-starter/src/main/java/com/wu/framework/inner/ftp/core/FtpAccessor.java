package com.wu.framework.inner.ftp.core;

import com.wu.framework.inner.ftp.connection.FtpConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @Description
 * @Author Jia wei Wu
 * @Date 2020-05-22 2:58 下午
 */
public class FtpAccessor implements InitializingBean {

    /**
     * Logger available to subclasses
     */
    protected final Log logger = LogFactory.getLog(getClass());

    private FtpConnectionFactory connectionFactory;

    public FtpAccessor(FtpConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void afterPropertiesSet() {
        Assert.state(getConnectionFactory() != null, "FtpConnectionFactory is required");
    }

    /**
     * Returns the connectionFactory.
     *
     * @return Returns the connectionFactory. Can be {@literal null}
     */
    @Nullable
    public FtpConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Sets the connection factory.
     *
     * @param connectionFactory The connectionFactory to set.
     */
    public void setConnectionFactory(FtpConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public FtpConnectionFactory getRequiredConnectionFactory() {

        FtpConnectionFactory connectionFactory = getConnectionFactory();

        if (connectionFactory == null) {
            throw new IllegalStateException("FtpConnectionFactory is required");
        }

        return connectionFactory;
    }
}
