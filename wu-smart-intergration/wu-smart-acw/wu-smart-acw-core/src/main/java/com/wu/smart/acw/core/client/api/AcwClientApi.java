package com.wu.smart.acw.core.client.api;

import com.wu.framework.response.Result;
import com.wu.smart.acw.core.client.api.command.AcwClientGenJavaAPICommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

/**
 * 客户端接收生成代码参数
 */
@Deprecated
@HttpExchange(accept = "application/json", contentType = "application/json",url = "/acw-client/java")
@Tag(name = "客户端实例Feign提供者")
public interface AcwClientApi {


    /**
     * describe 根据表生成本地Java对应模型
     *
     * @param acwClientGenJavaAPICommandList 新增客户端实例参数
     * @return {@link Result <?>} 返回结果
     * @author Jia wei Wu
     * @date 2023/12/05 09:47 晚上
     **/

    @Operation(summary = "根据表生成本地Java对应模型")
    @PostExchange("/api/generateLocalJava")
    Result<?> generateLocalJava(@RequestBody List<AcwClientGenJavaAPICommand> acwClientGenJavaAPICommandList);

}