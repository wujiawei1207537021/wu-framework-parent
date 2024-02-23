package com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe;

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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface DynamicIframeRepository {


    /**
     * describe 新增动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> story(DynamicIframe dynamicIframe);

    /**
     * describe 批量新增动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> batchStory(List<DynamicIframe> dynamicIframeList);

    /**
     * describe 查询单个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> findOne(DynamicIframe dynamicIframe);

    /**
     * describe 查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<List<DynamicIframe>> findList(DynamicIframe dynamicIframe);

    /**
     * describe 分页查询多个动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<LazyPage<DynamicIframe>> findPage(int size,int current,DynamicIframe dynamicIframe);

    /**
     * describe 删除动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    Result<DynamicIframe> remove(DynamicIframe dynamicIframe);

}