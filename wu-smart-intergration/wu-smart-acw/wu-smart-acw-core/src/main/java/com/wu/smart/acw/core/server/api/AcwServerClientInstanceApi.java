package com.wu.smart.acw.core.server.api;


import com.wu.framework.response.Result;
import com.wu.smart.acw.core.server.api.command.AcwClientInstanceAPICommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * describe 客户端实例
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:47 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.feign.DefaultFeignLazyApi
 **/
@Deprecated
@HttpExchange(accept = "application/json", contentType = "application/json")
@Tag(name = "服务端客户端实例Feign提供者")
public interface AcwServerClientInstanceApi {


    /**
     * describe 新增客户端实例
     *
     * @param acwClientInstanceAPICommand 新增客户端实例参数
     * @return {@link Result<?>} 返回结果
     * @author Jia wei Wu
     * @date 2023/12/05 09:47 晚上
     **/

    @Operation(summary = "新增客户端实例")
    @PostExchange("/api/story")
    Result<?> story(@RequestBody AcwClientInstanceAPICommand acwClientInstanceAPICommand);

}