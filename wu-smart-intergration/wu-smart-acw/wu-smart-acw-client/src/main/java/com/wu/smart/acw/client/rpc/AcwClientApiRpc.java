package com.wu.smart.acw.client.rpc;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyRpc;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.client.api.AcwClientApi;
import com.wu.smart.acw.core.client.api.command.AcwClientGenJavaAPICommand;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Deprecated
@LazyRpc("/acw-client/java")
public class AcwClientApiRpc implements AcwClientApi {
    /**
     * describe 根据表生成本地Java对应模型
     *
     * @param acwClientGenJavaAPICommandList 新增客户端实例参数
     * @return {@link Result <?>} 返回结果
     * @author Jia wei Wu
     * @date 2023/12/05 09:47 晚上
     **/
    @PostMapping("/api/generateLocalJava")
    @Override
    public Result<?> generateLocalJava(List<AcwClientGenJavaAPICommand> acwClientGenJavaAPICommandList) {
        for (AcwClientGenJavaAPICommand acwClientGenJavaAPICommand : acwClientGenJavaAPICommandList) {
            LazyOperationConfig.ReverseEngineering reverseEngineering = acwClientGenJavaAPICommand.getReverseEngineering();
            AcwClientGenJavaAPICommand.InnerReverseClassLazyTableEndpoint innerReverseClassLazyTableEndpoint = acwClientGenJavaAPICommand.getInnerReverseClassLazyTableEndpoint();
            ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint =innerReverseClassLazyTableEndpoint.toReverseClassLazyTableEndpoint();

            LazyTableUtil.createJava(reverseClassLazyTableEndpoint, reverseEngineering);
        }
        return ResultFactory.successOf();
    }
}
