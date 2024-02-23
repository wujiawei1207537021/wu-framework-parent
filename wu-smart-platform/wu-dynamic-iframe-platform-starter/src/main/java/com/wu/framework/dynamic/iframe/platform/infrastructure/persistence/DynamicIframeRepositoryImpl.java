package com.wu.framework.dynamic.iframe.platform.infrastructure.persistence;


import com.wu.framework.dynamic.iframe.platform.infrastructure.converter.DynamicIframeConverter;
import com.wu.framework.dynamic.iframe.platform.infrastructure.entity.DynamicIframeDO;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframe;
import com.wu.framework.dynamic.iframe.platform.model.dynamic.iframe.DynamicIframeRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class DynamicIframeRepositoryImpl  implements DynamicIframeRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

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
    public Result<DynamicIframe> story(DynamicIframe dynamicIframe) {
        DynamicIframeDO dynamicIframeDO = DynamicIframeConverter.fromDynamicIframe(dynamicIframe);
        lazyLambdaStream.upsert(dynamicIframeDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增动态Iframe
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/27 10:05 晚上
     **/

    @Override
    public Result<DynamicIframe> batchStory(List<DynamicIframe> dynamicIframeList) {
        List<DynamicIframeDO> dynamicIframeDOList = dynamicIframeList.stream().map(DynamicIframeConverter::fromDynamicIframe).collect(Collectors.toList());
        lazyLambdaStream.upsert(dynamicIframeDOList);
        return ResultFactory.successOf();
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
    public Result<DynamicIframe> findOne(DynamicIframe dynamicIframe) {
        DynamicIframeDO dynamicIframeDO = DynamicIframeConverter.fromDynamicIframe(dynamicIframe);
        DynamicIframe dynamicIframeOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(dynamicIframeDO), DynamicIframe.class);
        return ResultFactory.successOf(dynamicIframeOne);
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
    public Result<List<DynamicIframe>> findList(DynamicIframe dynamicIframe) {
        DynamicIframeDO dynamicIframeDO = DynamicIframeConverter.fromDynamicIframe(dynamicIframe);
        List<DynamicIframe> dynamicIframeList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(dynamicIframeDO), DynamicIframe.class);
        return ResultFactory.successOf(dynamicIframeList);
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
    public Result<LazyPage<DynamicIframe>> findPage(int size,int current,DynamicIframe dynamicIframe) {
        DynamicIframeDO dynamicIframeDO = DynamicIframeConverter.fromDynamicIframe(dynamicIframe);
        LazyPage lazyPage = new LazyPage(current,size);
        LazyPage<DynamicIframe> dynamicIframeLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(dynamicIframeDO),lazyPage, DynamicIframe.class);
        return ResultFactory.successOf(dynamicIframeLazyPage);
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
    public Result<DynamicIframe> remove(DynamicIframe dynamicIframe) {
        DynamicIframeDO dynamicIframeDO = DynamicIframeConverter.fromDynamicIframe(dynamicIframe);
        //  lazyLambdaStream.delete(dynamicIframeDO);
        return ResultFactory.successOf();
    }

}