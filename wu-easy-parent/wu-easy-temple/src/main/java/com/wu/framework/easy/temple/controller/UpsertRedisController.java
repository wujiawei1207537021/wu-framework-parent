package com.wu.framework.easy.temple.controller;

import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.upsert.EasyUpsertRedis;
import com.wu.framework.easy.upsert.QuickEasyUpsertRedis;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.redis.annotation.LazyRedis;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 数据快速插入redis
 * @date : 2021/7/4 4:42 下午
 */
@Api(tags = "Redis 数据快速存储")
@EasyController("/redis")
public class UpsertRedisController {
    private final IUpsert iUpsert;


    public UpsertRedisController(IUpsert iUpsert) {
        this.iUpsert = iUpsert;
    }


    @ApiOperation(tags = "Redis 数据快速存储", value = "多个注解并用")
    @LazyRedis(database = 1)
    @GetMapping("/normal/upsert")
    @EasyUpsert(type = EasyUpsertType.REDIS)
    public Result normalUpsert() {
        iUpsert.upsert(UserLog.createUserLogList(10), UserLog.createUserLogList(100));
        return ResultFactory.successOf();
    }

    @ApiOperation(tags = "Redis 数据快速存储", value = "单个注解")
    @GetMapping("/upsert")
    @EasyUpsertRedis(database = 2)
    public Result upsert() {
        final List<UserLog> userLogList = UserLog.createUserLogList(100);
        iUpsert.upsert(userLogList, userLogList);
        return ResultFactory.successOf();
    }

    @ApiOperation(tags = "Redis 数据快速存储", value = "单个注解")
    @GetMapping("/quick/upsert")
    @QuickEasyUpsertRedis(database = 3)
    public Object quickUpsert() {
        return UserLog.createUserLogList(100);
    }

}
