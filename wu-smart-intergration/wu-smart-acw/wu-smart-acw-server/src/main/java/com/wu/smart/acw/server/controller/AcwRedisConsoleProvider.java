package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwRedisConsoleApplication;
import com.wu.smart.acw.server.application.command.acw.redis.instance.*;
import com.wu.smart.acw.server.application.dto.AcwRedisDatabaseDTO;
import com.wu.smart.acw.server.application.dto.AcwRedisKeyDTO;
import com.wu.smart.acw.server.application.dto.AcwRedisKeyValueDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "ACW-Redis服务器提供者")
@EasyController("/lazy/acw/redis/console")
public class AcwRedisConsoleProvider {

    private final AcwRedisConsoleApplication acwRedisConsoleApplication;

    public AcwRedisConsoleProvider(AcwRedisConsoleApplication acwRedisConsoleApplication) {
        this.acwRedisConsoleApplication = acwRedisConsoleApplication;
    }




    /**
     * 获取当前实例中的所有的key
     *
     * @param acwRedisInstanceQueryKeyCommand key 查询参数
     * @return key dto
     */
    @GetMapping("/findKeys")
    public Result<LazyPage<AcwRedisKeyDTO>> findKeys(@ModelAttribute AcwRedisInstanceQueryKeyCommand acwRedisInstanceQueryKeyCommand) {
        return acwRedisConsoleApplication.findKeys(acwRedisInstanceQueryKeyCommand);
    }

    /**
     * 获取当前实例中的所有的key、value
     *
     * @param acwRedisInstanceQueryKeyCommand key 查询参数
     * @return key dto
     */
    @GetMapping("/findKeysValues")
    public Result<LazyPage<AcwRedisKeyValueDTO>> findKeysValues(@ModelAttribute AcwRedisInstanceQueryKeyCommand acwRedisInstanceQueryKeyCommand) {
        return acwRedisConsoleApplication.findKeysValues(acwRedisInstanceQueryKeyCommand);
    }

    /**
     * 获取key 的value
     * @param acwRedisInstanceQueryKeyValuesCommand 参数
     * @return 返回结果
     */
    @GetMapping("/findKeyValue")
    public Result<LazyPage<AcwRedisKeyValueDTO>> findKeyValue(@ModelAttribute AcwRedisInstanceQueryKeyValuesCommand acwRedisInstanceQueryKeyValuesCommand) {
        return acwRedisConsoleApplication.findKeyValue(acwRedisInstanceQueryKeyValuesCommand);
    }

    /**
     * 获取当前实例中的所有的database
     *
     * @param acwRedisInstanceQueryDatabaseCommand databases 查询参数
     * @return key dto
     */
    @GetMapping("/findDataBases")
    public Result<LazyPage<AcwRedisDatabaseDTO>> findDataBases(@ModelAttribute AcwRedisInstanceQueryDatabaseCommand acwRedisInstanceQueryDatabaseCommand) {
        return acwRedisConsoleApplication.findDataBases(acwRedisInstanceQueryDatabaseCommand);
    }

    /**
     * 添加redis数据
     *
     * @param acwRedisInstanceSetKeyCommand 添加redis数据
     * @return void
     */
    @PostMapping("/setKey")
    public Result<Void> setKey(@RequestBody AcwRedisInstanceSetKeyCommand acwRedisInstanceSetKeyCommand) {
        return acwRedisConsoleApplication.setKey(acwRedisInstanceSetKeyCommand);
    }
    /**
     * 移除redis数据
     *
     * @param acwRedisInstanceRemoveKeyCommand remove redis数据
     * @return void
     */
    @PutMapping("/removeKey")
    public Result<Void> removeKey(@RequestBody AcwRedisInstanceRemoveKeyCommand acwRedisInstanceRemoveKeyCommand) {
        return acwRedisConsoleApplication.removeKey(acwRedisInstanceRemoveKeyCommand);
    }
}