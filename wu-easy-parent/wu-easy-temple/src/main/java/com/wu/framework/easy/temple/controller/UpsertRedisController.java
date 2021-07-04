package com.wu.framework.easy.temple.controller;

import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertDS;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.redis.annotation.LazyRedis;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 数据快速插入redis
 * @date : 2021/7/4 4:42 下午
 */
@Api(tags = "REDIS数据快速存储")
@EasyController("/redis")
public class UpsertRedisController {
    private final IUpsert iUpsert;


    public UpsertRedisController(IUpsert iUpsert) {
        this.iUpsert = iUpsert;
    }

    @LazyRedis(database = 1)
    @GetMapping("/upsert")
    @EasyUpsertDS(type = EasyUpsertType.REDIS)
    public Result upsert(){
        iUpsert.upsert(UserLog.createUserLogList(10),UserLog.createUserLogList(100));
        return ResultFactory.successOf();
    }

}
