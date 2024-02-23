package com.wu.smart.acw.server.infrastructure.converter;


import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstance;
import com.wu.smart.acw.server.infrastructure.entity.AcwRedisInstanceDO;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class AcwRedisInstanceConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/
    public static AcwRedisInstance toAcwRedisInstance(AcwRedisInstanceDO acwRedisInstanceDO) {
        if (null != acwRedisInstanceDO) {
            AcwRedisInstance acwRedisInstance = new AcwRedisInstance();
            acwRedisInstance.setCreateTime(acwRedisInstanceDO.getCreateTime());
            acwRedisInstance.setHost(acwRedisInstanceDO.getHost());
            acwRedisInstance.setId(acwRedisInstanceDO.getId());
            acwRedisInstance.setInstanceName(acwRedisInstanceDO.getInstanceName());
            acwRedisInstance.setIsDeleted(acwRedisInstanceDO.getIsDeleted());
            acwRedisInstance.setPassword(acwRedisInstanceDO.getPassword());
            acwRedisInstance.setPort(acwRedisInstanceDO.getPort());
            acwRedisInstance.setStatus(acwRedisInstanceDO.getStatus());
            acwRedisInstance.setUpdateTime(acwRedisInstanceDO.getUpdateTime());
            acwRedisInstance.setUsername(acwRedisInstanceDO.getUsername());
            return acwRedisInstance;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/
    public static AcwRedisInstanceDO fromAcwRedisInstance(AcwRedisInstance acwRedisInstance) {
        if (null != acwRedisInstance) {
            AcwRedisInstanceDO acwRedisInstanceDO = new AcwRedisInstanceDO();
            acwRedisInstanceDO.setCreateTime(acwRedisInstance.getCreateTime());
            acwRedisInstanceDO.setHost(acwRedisInstance.getHost());
            acwRedisInstanceDO.setId(acwRedisInstance.getId());
            acwRedisInstanceDO.setInstanceName(acwRedisInstance.getInstanceName());
            acwRedisInstanceDO.setIsDeleted(acwRedisInstance.getIsDeleted());
            acwRedisInstanceDO.setPassword(acwRedisInstance.getPassword());
            acwRedisInstanceDO.setPort(acwRedisInstance.getPort());
            acwRedisInstanceDO.setStatus(acwRedisInstance.getStatus());
            acwRedisInstanceDO.setUpdateTime(acwRedisInstance.getUpdateTime());
            acwRedisInstanceDO.setUsername(acwRedisInstance.getUsername());
            return acwRedisInstanceDO;
        }
        return null;
    }

}