package com.wu.framework.easy.temple.controller;

import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.upsert.EasyUpsertRedisSearch;
import com.wu.framework.easy.upsert.QuickEasyUpsertRedisSearch;
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
 * 数据快速插入redis_search
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/12/19 5:23 下午
 */
@Api(tags = "Redis_Search 数据快速存储")
@EasyController("/redis/search")
public class UpsertRediSearchController {
    private final IUpsert iUpsert;


    public UpsertRediSearchController(IUpsert iUpsert) {
        this.iUpsert = iUpsert;
    }


    @ApiOperation(tags = "REDIS_SEARCH数据快速存储", value = "多个注解并用")
    @LazyRedis(database = 1)
    @GetMapping("/normal/upsert")
    @EasyUpsert(type = EasyUpsertType.REDIS)
    public Result normalUpsert() {
        iUpsert.upsert(UserLog.createUserLogList(10), UserLog.createUserLogList(100));
        return ResultFactory.successOf();
    }

    @ApiOperation(tags = "REDIS_SEARCH数据快速存储", value = "单个注解")
    @GetMapping("/upsert")
    @EasyUpsertRedisSearch(database = 2)
    public Result upsert() {
        final List<UserLog> userLogList = UserLog.createUserLogList(1000000);
        iUpsert.upsert(userLogList, userLogList);
        return ResultFactory.successOf();
    }

    @ApiOperation(tags = "REDIS_SEARCH数据快速存储", value = "单个注解")
    @GetMapping("/quick/upsert")
    @QuickEasyUpsertRedisSearch(database = 3)
    public Object quickUpsert() {
        return UserLog.createUserLogList(1000000);
    }

}
