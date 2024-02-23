package com.wu.framework.dynamic.iframe.platform.infrastructure.converter;


import com.wu.framework.dynamic.iframe.platform.infrastructure.entity.DynamicIframeDO;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframe;

/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/

public class DynamicIframeConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/
    public static DynamicIframe toDynamicIframe(DynamicIframeDO dynamicIframeDO) {
        if (null != dynamicIframeDO) {
        DynamicIframe dynamicIframe = new DynamicIframe(); 
           dynamicIframe.setCreateTime(dynamicIframeDO.getCreateTime());
           dynamicIframe.setDynamicParameter(dynamicIframeDO.getDynamicParameter());
           dynamicIframe.setId(dynamicIframeDO.getId());
           dynamicIframe.setIsDeleted(dynamicIframeDO.getIsDeleted());
           dynamicIframe.setIsStation(dynamicIframeDO.getIsStation());
           dynamicIframe.setUpdateTime(dynamicIframeDO.getUpdateTime());
           dynamicIframe.setUrl(dynamicIframeDO.getUrl());
            return dynamicIframe;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/
    public static DynamicIframeDO fromDynamicIframe(DynamicIframe dynamicIframe) {
        if (null != dynamicIframe) {
        DynamicIframeDO dynamicIframeDO = new DynamicIframeDO(); 
           dynamicIframeDO.setCreateTime(dynamicIframe.getCreateTime());
           dynamicIframeDO.setDynamicParameter(dynamicIframe.getDynamicParameter());
           dynamicIframeDO.setId(dynamicIframe.getId());
           dynamicIframeDO.setIsDeleted(dynamicIframe.getIsDeleted());
           dynamicIframeDO.setIsStation(dynamicIframe.getIsStation());
           dynamicIframeDO.setUpdateTime(dynamicIframe.getUpdateTime());
           dynamicIframeDO.setUrl(dynamicIframe.getUrl());
            return dynamicIframeDO;
        }
        return null;
    }

}