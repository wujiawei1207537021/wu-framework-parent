package com.wuframework.system.serivce.def;

import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.entity.SysUserMessage;
import com.wuframework.system.persistence.jpa.SysUserMessageJpaRepository;
import com.wuframework.system.serivce.SysUserMessageService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("defaultSysUserMessageService")
public class DefaultSysUserMessageService implements SysUserMessageService {
    @Resource
    private SysUserMessageJpaRepository sysUserMessageJpaRepository;

    @Override
    public Result queryMessage(DefaultSysUserDetails defaultSysUserDetails, UniversalSearchQO universalSearchQO, Page page) {
        SysUserMessage sysUserMessage = new SysUserMessage();
        sysUserMessage.setUserId(defaultSysUserDetails.getUserId());
        Example example = Example.of(sysUserMessage);
        List<SysUserMessage> sysUserMessageList = new ArrayList<>();
        if (universalSearchQO.getPagination()) {
            PageRequest pageRequest = PageRequest.of(page.getCurrent() - 1, page.getSize());
            sysUserMessageList = sysUserMessageJpaRepository.findAll(example, pageRequest).toList();
            page.setRecords(sysUserMessageList);
            return ResultFactory.successOf(page);
        }
        sysUserMessageList = sysUserMessageJpaRepository.findAll(example);
        return ResultFactory.successOf(sysUserMessageList);
    }

    /**
     * 用户消息添加
     *
     * @param sysUserMessage
     * @return
     */
    @Override
    public Result saveMessage(SysUserMessage sysUserMessage) {
        sysUserMessageJpaRepository.save(sysUserMessage);
        return ResultFactory.successOf();
    }
}
