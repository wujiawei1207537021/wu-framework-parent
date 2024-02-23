package com.wuframework.system.serivce;

import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.entity.SysUserMessage;

public interface SysUserMessageService {
    /**
     * 查询用户信息
     *
     * @param defaultSysUserDetails
     * @param universalSearchQO
     * @param page
     * @return
     */
    Result queryMessage(DefaultSysUserDetails defaultSysUserDetails, UniversalSearchQO universalSearchQO, Page page);

    /**
     * 用户消息添加
     *
     * @param sysUserMessage
     * @return
     */
    Result saveMessage(SysUserMessage sysUserMessage);
}
