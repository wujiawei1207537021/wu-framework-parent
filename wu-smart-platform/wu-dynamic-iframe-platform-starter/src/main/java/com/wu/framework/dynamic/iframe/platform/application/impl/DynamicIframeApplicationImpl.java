package com.wu.framework.dynamic.iframe.platform.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.dynamic.iframe.platform.application.DynamicIframeApplication;
import com.wu.framework.dynamic.iframe.platform.application.assembler.DynamicIframeDTOAssembler;
import com.wu.framework.dynamic.iframe.platform.application.command.DynamicIframeCommand;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframe;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframeRepository;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class DynamicIframeApplicationImpl implements DynamicIframeApplication {

    @Autowired
    DynamicIframeRepository dynamicIframeRepository;
    /**
     * describe 新增动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @Override
    public Result<DynamicIframe> story(DynamicIframeCommand dynamicIframeCommand) {
        DynamicIframe dynamicIframe = DynamicIframeDTOAssembler.toDynamicIframe(dynamicIframeCommand);
        return dynamicIframeRepository.story(dynamicIframe);
    }
    /**
     * describe 更新动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @Override
    public Result<DynamicIframe> updateOne(DynamicIframeCommand dynamicIframeCommand) {
        DynamicIframe dynamicIframe = DynamicIframeDTOAssembler.toDynamicIframe(dynamicIframeCommand);
        return dynamicIframeRepository.story(dynamicIframe);
    }

    /**
     * describe 查询单个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @Override
    public Result<DynamicIframe> findOne(DynamicIframeCommand dynamicIframeCommand) {
        DynamicIframe dynamicIframe = DynamicIframeDTOAssembler.toDynamicIframe(dynamicIframeCommand);
        return dynamicIframeRepository.findOne(dynamicIframe);
    }

    /**
     * describe 查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @Override
    public Result<List<DynamicIframe>> findList(DynamicIframeCommand dynamicIframeCommand) {
        DynamicIframe dynamicIframe = DynamicIframeDTOAssembler.toDynamicIframe(dynamicIframeCommand);
        return dynamicIframeRepository.findList(dynamicIframe);
    }

    /**
     * describe 分页查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @Override
    public Result<LazyPage<DynamicIframe>> findPage(int size,int current,DynamicIframeCommand dynamicIframeCommand) {
        DynamicIframe dynamicIframe = DynamicIframeDTOAssembler.toDynamicIframe(dynamicIframeCommand);
        return dynamicIframeRepository.findPage(size,current,dynamicIframe);
    }

    /**
     * describe 删除动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @Override
    public Result<DynamicIframe> remove(DynamicIframeCommand dynamicIframeCommand) {
     DynamicIframe dynamicIframe = DynamicIframeDTOAssembler.toDynamicIframe(dynamicIframeCommand);
     return dynamicIframeRepository.remove(dynamicIframe);
    }

}