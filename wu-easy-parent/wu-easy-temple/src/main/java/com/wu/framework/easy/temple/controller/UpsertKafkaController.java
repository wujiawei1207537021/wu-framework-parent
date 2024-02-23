package com.wu.framework.easy.temple.controller;

import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.inner.layer.web.EasyController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * description 测试 数据存储kafka
 *
 * @author 吴佳伟
 * @date 2021/5/11 1:21 下午
 */
@Api(tags = "kafka数据快速存储")
@EasyController("/kafka")
public class UpsertKafkaController {

    private final IUpsert iUpsert;

    public UpsertKafkaController(IUpsert iUpsert) {
        this.iUpsert = iUpsert;
    }

    /**
     * description IUpsert操作数据入kafka
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/15 上午9:50
     */
    @EasyUpsert(type = EasyUpsertType.KAFKA)
    @ApiOperation(tags = "kafka数据快速存储", value = "IUpsert操作数据入kafka")
    @GetMapping()
    public void upsert(@RequestParam(required = false, defaultValue = "100") Integer size) {
        List<UserLog> userLogList = UserLog.createUserLogList(size);
        iUpsert.upsert(userLogList, userLogList, new UserLog());
    }

}
