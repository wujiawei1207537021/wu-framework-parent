package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.command.acw.redis.instance.*;
import com.wu.smart.acw.server.application.dto.AcwRedisDatabaseDTO;
import com.wu.smart.acw.server.application.dto.AcwRedisKeyDTO;
import com.wu.smart.acw.server.application.dto.AcwRedisKeyValueDTO;

public interface AcwRedisConsoleApplication {

    /**
     * 获取当前实例中的所有的key
     *
     * @param acwRedisInstanceQueryKeyCommand key 查询参数
     * @return key dto
     */
    Result<LazyPage<AcwRedisKeyDTO>> findKeys(AcwRedisInstanceQueryKeyCommand acwRedisInstanceQueryKeyCommand);


    /**
     * 获取当前实例中的所有的database
     * @param acwRedisInstanceQueryDatabaseCommand 查询条件
     * @return 获取当前实例中的所有的database
     */
    Result<LazyPage<AcwRedisDatabaseDTO>> findDataBases(AcwRedisInstanceQueryDatabaseCommand acwRedisInstanceQueryDatabaseCommand);

    /**
     * 添加redis数据
     * @param acwRedisInstanceSetKeyCommand redis 数据库
     * @return
     */
    Result<Void> setKey(AcwRedisInstanceSetKeyCommand acwRedisInstanceSetKeyCommand);

    /**
     * 获取当前实例中的所有的key、value
     * @param acwRedisInstanceQueryKeyCommand 参数
     * @return 结果
     */
    Result<LazyPage<AcwRedisKeyValueDTO>> findKeysValues(AcwRedisInstanceQueryKeyCommand acwRedisInstanceQueryKeyCommand);

    /**
     * 移除redis数据
     * @param acwRedisInstanceRemoveKeyCommand 参数
     * @return 返回结果
     */
    Result<Void> removeKey(AcwRedisInstanceRemoveKeyCommand acwRedisInstanceRemoveKeyCommand);

    /**
     * 获取key 的value
     * @param acwRedisInstanceQueryKeyValuesCommand 参数
     * @return 返回结果
     */
    Result<LazyPage<AcwRedisKeyValueDTO>> findKeyValue(AcwRedisInstanceQueryKeyValuesCommand acwRedisInstanceQueryKeyValuesCommand);
}
