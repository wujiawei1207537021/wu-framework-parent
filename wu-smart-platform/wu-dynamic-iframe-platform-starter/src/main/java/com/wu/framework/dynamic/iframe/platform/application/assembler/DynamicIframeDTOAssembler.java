package com.wu.framework.dynamic.iframe.platform.application.assembler;


import com.wu.framework.dynamic.iframe.platform.application.command.DynamicIframeCommand;
import com.wu.framework.dynamic.iframe.platform.application.dto.DynamicIframeDTO;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframe;

/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/

public class DynamicIframeDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/
    public static DynamicIframe toDynamicIframe(DynamicIframeCommand dynamicIframeCommand) {
        if (null != dynamicIframeCommand) {
        DynamicIframe dynamicIframe = new DynamicIframe(); 
           dynamicIframe.setCreateTime(dynamicIframeCommand.getCreateTime());
           dynamicIframe.setDynamicParameter(dynamicIframeCommand.getDynamicParameter());
           dynamicIframe.setId(dynamicIframeCommand.getId());
           dynamicIframe.setIsDeleted(dynamicIframeCommand.getIsDeleted());
           dynamicIframe.setIsStation(dynamicIframeCommand.getIsStation());
           dynamicIframe.setUpdateTime(dynamicIframeCommand.getUpdateTime());
           dynamicIframe.setUrl(dynamicIframeCommand.getUrl());
            return dynamicIframe;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/
    public static DynamicIframeDTO fromDynamicIframe(DynamicIframe dynamicIframe) {
        if (null != dynamicIframe) {
        DynamicIframeDTO dynamicIframeDTO = new DynamicIframeDTO(); 
           dynamicIframeDTO.setCreateTime(dynamicIframe.getCreateTime());
           dynamicIframeDTO.setDynamicParameter(dynamicIframe.getDynamicParameter());
           dynamicIframeDTO.setId(dynamicIframe.getId());
           dynamicIframeDTO.setIsDeleted(dynamicIframe.getIsDeleted());
           dynamicIframeDTO.setIsStation(dynamicIframe.getIsStation());
           dynamicIframeDTO.setUpdateTime(dynamicIframe.getUpdateTime());
           dynamicIframeDTO.setUrl(dynamicIframe.getUrl());
            return dynamicIframeDTO;
        }
        return null;
    }

}