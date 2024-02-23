package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.ApiDownLinkMethodQo;
import com.wu.smart.acw.core.domain.uo.AcwApplicationApiDownLinkMethodUo;
import com.wu.smart.acw.core.domain.uo.AcwClassUo;
import com.wu.smart.acw.core.domain.uo.AcwMethodUo;
import com.wu.smart.acw.core.domain.uo.AcwTableClassUo;
import com.wu.smart.acw.server.application.ApiDownLinkMethodApplication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/1 10:56 下午
 */
@Service
public class ApiDownLinkMethodServiceImpl implements ApiDownLinkMethodApplication {

    private final LazyLambdaStream lazyLambdaStream;

    public ApiDownLinkMethodServiceImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe API 下联 Method 表
     *
     * @param apiDownLinkMethodQo@return
     * @author Jia wei Wu
     * @date 2022/1/1 11:00 下午
     **/
    @Override
    public Result save(ApiDownLinkMethodQo apiDownLinkMethodQo) {
        final String tableName = apiDownLinkMethodQo.getTableName();
        final Long apiId = apiDownLinkMethodQo.getApiId();
        final Long projectId = apiDownLinkMethodQo.getProjectId();

        // 查询table 对应的class
        AcwTableClassUo acwTableClassUo = lazyLambdaStream.of(AcwTableClassUo.class).selectOne(
                LazyWrappers.<AcwTableClassUo>lambdaWrapper()
                        .eq(AcwTableClassUo::getTableName, tableName)
                        .eq(AcwTableClassUo::getProjectId, projectId), AcwTableClassUo.class);

        if (ObjectUtils.isEmpty(acwTableClassUo)) {
            // 新增class
            final AcwClassUo acwClassUo = new AcwClassUo();
            acwClassUo.setProjectId(projectId).
                    setType(LayerClass.LayerType.CONTROLLER).
                    setName(CamelAndUnderLineConverter.lineToHumpClass(tableName));
            lazyLambdaStream.upsert(acwClassUo);
            Long classUoId = acwClassUo.getId();
            //    新增 AcwTableClassUo
            acwTableClassUo = new AcwTableClassUo();
            acwTableClassUo.setTableName(tableName);
            acwTableClassUo.setProjectId(projectId);
            acwTableClassUo.setClassId(classUoId);
            lazyLambdaStream.upsert(acwTableClassUo);
        }
        // 新增方法
        final Long classId = acwTableClassUo.getClassId();

        final AcwMethodUo acwMethodUo = new AcwMethodUo();

        acwMethodUo.setClassId(classId).setName(""); // 方法名称 方法体
        lazyLambdaStream.upsert(acwMethodUo);
        final Long methodId = acwMethodUo.getId();
        // 方法关联class
        AcwApplicationApiDownLinkMethodUo acwApplicationApiDownLinkMethodUo = new AcwApplicationApiDownLinkMethodUo();
        acwApplicationApiDownLinkMethodUo.setApiId(apiId).setProjectId(projectId).setMethodId(methodId);
        lazyLambdaStream.upsert(acwApplicationApiDownLinkMethodUo);
        return null;
    }
}
