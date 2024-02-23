package com.wu.framework.inner.file.wrapper.config.dev;


import com.github.tobato.fastdfs.conn.Connection;
import com.github.tobato.fastdfs.conn.ConnectionManager;
import com.github.tobato.fastdfs.conn.FdfsConnectionPool;
import com.github.tobato.fastdfs.domain.TrackerLocator;
import com.github.tobato.fastdfs.exception.FdfsConnectException;
import com.github.tobato.fastdfs.proto.FdfsCommand;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理TrackerClient连接池分配
 *
 * @author tobato
 */
@Data
@Configuration
@ConfigurationProperties(prefix = DevFdfsClientConstants.ROOT_CONFIG_PREFIX)
public class DevTrackerConnectionManager extends ConnectionManager implements CommandLineRunner {


    private String accessPath;

    /**
     * Tracker定位
     */
    private TrackerLocator trackerLocator;

    /**
     * tracker服务配置地址列表
     */
    @NotNull
    private List<String> trackerList = new ArrayList<String>();

    /**
     * 构造函数
     */
    public DevTrackerConnectionManager() {
        super();
    }

    /**
     * 构造函数
     */
    public DevTrackerConnectionManager(FdfsConnectionPool pool) {
        super(pool);
    }


    /**
     * 获取连接并执行交易
     *
     * @param command
     * @return
     */
    public <T> T executeFdfsTrackerCmd(FdfsCommand<T> command) {
        Connection conn = null;
        InetSocketAddress address = null;
        // 获取连接
        try {
            address = trackerLocator.getTrackerAddress();
            LOGGER.debug("获取到Tracker连接地址{}", address);
            conn = getConnection(address);
            trackerLocator.setActive(address);
        } catch (FdfsConnectException e) {
            trackerLocator.setInActive(address);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unable to borrow buffer from pool", e);
            throw new RuntimeException("Unable to borrow buffer from pool", e);
        }
        // 执行交易
        return execute(address, conn, command);
    }

    public List<String> getTrackerList() {
        return trackerList;
    }

    public void setTrackerList(List<String> trackerList) {
        this.trackerList = trackerList;
    }

    /**
     * 初始化方法
     **/
    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("init trackerLocator {}", trackerList);
        trackerLocator = new TrackerLocator(trackerList);
    }
}
