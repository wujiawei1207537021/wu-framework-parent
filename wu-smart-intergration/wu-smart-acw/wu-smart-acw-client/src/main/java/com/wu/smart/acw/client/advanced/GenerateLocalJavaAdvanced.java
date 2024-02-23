package com.wu.smart.acw.client.advanced;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.reverse.ReverseClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.smart.acw.core.client.api.command.AcwClientGenJavaAPICommand;
import com.wu.smart.acw.core.constant.AcwNettyMessageType;
import io.netty.channel.Channel;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractHandleChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;

import java.util.List;

/**
 * @see HandleChannelTypeAdvanced
 * 客户端接收服务端传递的消息
 */
public class GenerateLocalJavaAdvanced extends AbstractHandleChannelTypeAdvanced<NettyProxyMsg> {

    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        byte[] data = nettyProxyMsg.getData();
        List<AcwClientGenJavaAPICommand> acwClientGenJavaAPICommandList =
                JSONObject.parseArray(new String(data), AcwClientGenJavaAPICommand.class);
        for (AcwClientGenJavaAPICommand acwClientGenJavaAPICommand : acwClientGenJavaAPICommandList) {
            LazyOperationConfig.ReverseEngineering reverseEngineering = acwClientGenJavaAPICommand.getReverseEngineering();
            AcwClientGenJavaAPICommand.InnerReverseClassLazyTableEndpoint innerReverseClassLazyTableEndpoint = acwClientGenJavaAPICommand.getInnerReverseClassLazyTableEndpoint();
            ReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint = innerReverseClassLazyTableEndpoint.toReverseClassLazyTableEndpoint();

            LazyTableUtil.createJava(reverseClassLazyTableEndpoint, reverseEngineering);
        }
    }

    /**
     * @param nettyProxyMsg 通道数据
     * @return boolean
     */
    @Override
    protected boolean doSupport(NettyProxyMsg nettyProxyMsg) {
        byte type = nettyProxyMsg.getType();
        return AcwNettyMessageType.GENERATE_LOCAL_JAVA == type;
    }
}
