package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwRedisInstanceApplication;
import com.wu.smart.acw.server.application.command.acw.redis.instance.*;
import com.wu.smart.acw.server.application.dto.AcwRedisInstanceDTO;
import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstance;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "ACW-Redis服务器提供者")
@EasyController("/lazy/acw/redis/instance")
public class AcwRedisInstanceProvider {

    @Autowired
    private AcwRedisInstanceApplication acwRedisInstanceApplication;

    /**
     * describe 新增Redis服务器
     *
     * @param
     * @param acwRedisInstanceStoryCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @PostMapping("/story")
    public Result<AcwRedisInstance> story(@RequestBody AcwRedisInstanceStoryCommand acwRedisInstanceStoryCommand) {
        return acwRedisInstanceApplication.story(acwRedisInstanceStoryCommand);
    }

    /**
     * describe 更新Redis服务器
     *
     * @param
     * @param acwRedisInstanceUpdateCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @PutMapping("/updateOne")
    public Result<AcwRedisInstance> updateOne(@RequestBody AcwRedisInstanceUpdateCommand acwRedisInstanceUpdateCommand) {
        return acwRedisInstanceApplication.updateOne(acwRedisInstanceUpdateCommand);
    }

    /**
     * describe 查询单个Redis服务器
     *
     * @param
     * @param acwRedisInstanceQueryOneCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @GetMapping("/findOne")
    public Result<AcwRedisInstance> findOne(@ModelAttribute AcwRedisInstanceQueryOneCommand acwRedisInstanceQueryOneCommand) {
        return acwRedisInstanceApplication.findOne(acwRedisInstanceQueryOneCommand);
    }

    /**
     * describe 查询多个Redis服务器
     *
     * @param
     * @param acwRedisInstanceQueryListCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @GetMapping("/findList")
    public Result<List<AcwRedisInstanceDTO>> findList(@ModelAttribute AcwRedisInstanceQueryListCommand acwRedisInstanceQueryListCommand) {
        return acwRedisInstanceApplication.findList(acwRedisInstanceQueryListCommand);
    }

    /**
     * describe 分页查询多个Redis服务器
     *
     * @param
     * @param acwRedisInstanceQueryListCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<AcwRedisInstanceDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                          @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute AcwRedisInstanceQueryListCommand acwRedisInstanceQueryListCommand) {
        return acwRedisInstanceApplication.findPage(size, current, acwRedisInstanceQueryListCommand);
    }

    /**
     * describe 删除Redis服务器
     *
     * @param
     * @param acwRedisInstanceRemoveCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @DeleteMapping("/remove")
    public Result<AcwRedisInstance> remove(@ModelAttribute AcwRedisInstanceRemoveCommand acwRedisInstanceRemoveCommand) {
        return acwRedisInstanceApplication.remove(acwRedisInstanceRemoveCommand);
    }

    /**
     * 测试链接
     * @param acwRedisInstanceStoryCommand 链接信息
     * @return void
     */
    @PostMapping("/test")
    public Result<Void> test(@RequestBody AcwRedisInstanceStoryCommand acwRedisInstanceStoryCommand) {
        return acwRedisInstanceApplication.test(acwRedisInstanceStoryCommand);
    }
}