package com.wu.framework.dynamic.iframe.platform.application;

import com.wu.framework.dynamic.iframe.platform.application.command.DynamicIframeCommand;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframe;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface DynamicIframeApplication {


    /**
     * describe 新增动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> story(DynamicIframeCommand dynamicIframeCommand);

    /**
     * describe 更新动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> updateOne(DynamicIframeCommand dynamicIframeCommand);

    /**
     * describe 查询单个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> findOne(DynamicIframeCommand dynamicIframeCommand);

    /**
     * describe 查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result <List<DynamicIframe>> findList(DynamicIframeCommand dynamicIframeCommand);

    /**
     * describe 分页查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result <LazyPage<DynamicIframe>> findPage(int size, int current, DynamicIframeCommand dynamicIframeCommand);

    /**
     * describe 删除动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> remove(DynamicIframeCommand dynamicIframeCommand);

}