package com.wu.smart.acw.server.rpc;

import com.wu.framework.authorization.annotation.NoAuthorizationRequired;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyRpc;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.server.api.AcwServerClientInstanceApi;
import com.wu.smart.acw.core.server.api.command.AcwClientInstanceAPICommand;
import com.wu.smart.acw.server.application.AcwClientInstanceApplication;
import com.wu.smart.acw.server.application.command.acw.acw.client.instance.*;
import com.wu.smart.acw.server.application.dto.AcwClientInstanceDTO;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstance;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
@LazyRpc("/api")
public class AcwServerClientInstanceApiRpc implements AcwServerClientInstanceApi {

    @Resource
    private AcwClientInstanceApplication acwClientInstanceApplication;

    /**
     * describe 新增客户端实例
     *
     * @param acwClientInstanceStoryCommand 新增客户端实例
     * @return {@link Result< AcwClientInstance >} 客户端实例新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
    @NoAuthorizationRequired
    @Operation(summary = "新增客户端实例")
    @PostMapping("/story")
    public Result<AcwClientInstance> story(@RequestBody AcwClientInstanceStoryCommand acwClientInstanceStoryCommand) {
        return acwClientInstanceApplication.story(acwClientInstanceStoryCommand);
    }

    /**
     * describe 批量新增客户端实例
     *
     * @param acwClientInstanceStoryCommandList 批量新增客户端实例
     * @return {@link Result<List<AcwClientInstance>>} 客户端实例新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Operation(summary = "批量新增客户端实例")
    @PostMapping("/batchStory")
    public Result<List<AcwClientInstance>> batchStory(@RequestBody List<AcwClientInstanceStoryCommand> acwClientInstanceStoryCommandList) {
        return acwClientInstanceApplication.batchStory(acwClientInstanceStoryCommandList);
    }

    /**
     * describe 更新客户端实例
     *
     * @param acwClientInstanceUpdateCommand 更新客户端实例
     * @return {@link Result<AcwClientInstance>} 客户端实例领域对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Operation(summary = "更新客户端实例")
    @PutMapping("/updateOne")
    public Result<AcwClientInstance> updateOne(@RequestBody AcwClientInstanceUpdateCommand acwClientInstanceUpdateCommand) {
        return acwClientInstanceApplication.updateOne(acwClientInstanceUpdateCommand);
    }

    /**
     * describe 查询单个客户端实例
     *
     * @param acwClientInstanceQueryOneCommand 查询单个客户端实例
     * @return {@link Result< AcwClientInstanceDTO >} 客户端实例DTO对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Operation(summary = "查询单个客户端实例")
    @GetMapping("/findOne")
    public Result<AcwClientInstanceDTO> findOne(@ModelAttribute AcwClientInstanceQueryOneCommand acwClientInstanceQueryOneCommand) {
        return acwClientInstanceApplication.findOne(acwClientInstanceQueryOneCommand);
    }

    /**
     * describe 查询多个客户端实例
     *
     * @param acwClientInstanceQueryListCommand 查询多个客户端实例
     * @return {@link Result<List<AcwClientInstanceDTO>>} 客户端实例DTO对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Operation(summary = "查询多个客户端实例")
    @GetMapping("/findList")
    public Result<List<AcwClientInstanceDTO>> findList(@ModelAttribute AcwClientInstanceQueryListCommand acwClientInstanceQueryListCommand) {
        return acwClientInstanceApplication.findList(acwClientInstanceQueryListCommand);
    }

    /**
     * describe 分页查询多个客户端实例
     *
     * @param acwClientInstanceQueryListCommand 分页查询多个客户端实例
     * @return {@link Result< LazyPage <AcwClientInstanceDTO>>} 分页客户端实例DTO对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Operation(summary = "分页查询多个客户端实例")
    @GetMapping("/findPage")
    public Result<LazyPage<AcwClientInstanceDTO>> findPage(@RequestParam(defaultValue = "10", value = "size") int size,
                                                           @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute AcwClientInstanceQueryListCommand acwClientInstanceQueryListCommand) {
        return acwClientInstanceApplication.findPage(size, current, acwClientInstanceQueryListCommand);
    }

    /**
     * describe 删除客户端实例
     *
     * @param acwClientInstanceRemoveCommand 删除客户端实例
     * @return {@link Result<AcwClientInstance>} 客户端实例
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Operation(summary = "删除客户端实例")
    @DeleteMapping("/remove")
    public Result<AcwClientInstance> remove(@ModelAttribute AcwClientInstanceRemoveCommand acwClientInstanceRemoveCommand) {
        return acwClientInstanceApplication.remove(acwClientInstanceRemoveCommand);
    }

    /**
     * describe 新增客户端实例
     *
     * @param acwClientInstanceAPICommand 新增客户端实例参数
     * @return {@link Result<?>} 返回结果
     * @author Jia wei Wu
     * @date 2023/12/05 09:47 晚上
     **/
    @Override
    public Result<?> story(AcwClientInstanceAPICommand acwClientInstanceAPICommand) {
        return null;
    }
}
