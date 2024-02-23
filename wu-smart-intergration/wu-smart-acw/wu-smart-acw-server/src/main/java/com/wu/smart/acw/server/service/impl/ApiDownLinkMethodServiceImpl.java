package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.ApiDownLinkMethodQo;
import com.wu.smart.acw.core.domain.uo.ApiDownLinkMethodUo;
import com.wu.smart.acw.core.domain.uo.ClassUo;
import com.wu.smart.acw.core.domain.uo.MethodUo;
import com.wu.smart.acw.core.domain.uo.TableClassUo;
import com.wu.smart.acw.server.service.ApiDownLinkMethodService;
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
public class ApiDownLinkMethodServiceImpl implements ApiDownLinkMethodService {

    private final LazyOperation lazyOperation;
    private final LazyLambdaStream lazyLambdaStream;

    public ApiDownLinkMethodServiceImpl(LazyOperation lazyOperation, LazyLambdaStream lazyLambdaStream) {
        this.lazyOperation = lazyOperation;
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
        TableClassUo tableClassUo = lazyLambdaStream.of(TableClassUo.class).select(LazyWrappers.<TableClassUo>lambdaWrapper().
                        eq(TableClassUo::getTableName, tableName).
                        eq(TableClassUo::getProjectId, projectId)).

                collectOne(TableClassUo.class);

        if (ObjectUtils.isEmpty(tableClassUo)) {
            // 新增class
            final ClassUo classUo = new ClassUo();
            classUo.setProjectId(projectId).
                    setType(LayerClass.LayerType.CONTROLLER).
                    setName(CamelAndUnderLineConverter.lineToHumpClass(tableName));
            lazyOperation.smartUpsert(classUo);
            Long classUoId = classUo.getId();
            //    新增 TableClassUo
            tableClassUo = new TableClassUo();
            tableClassUo.setTableName(tableName);
            tableClassUo.setProjectId(projectId);
            tableClassUo.setClassId(classUoId);
            lazyOperation.smartUpsert(tableClassUo);
        }
        // 新增方法
        final Long classId = tableClassUo.getClassId();

        final MethodUo methodUo = new MethodUo();

        methodUo.setClassId(classId).setName(""); // 方法名称 方法体
        lazyOperation.smartUpsert(methodUo);
        final Long methodId = methodUo.getId();
        // 方法关联class
        ApiDownLinkMethodUo apiDownLinkMethodUo = new ApiDownLinkMethodUo();
        apiDownLinkMethodUo.setApiId(apiId).setProjectId(projectId).setMethodId(methodId);
        lazyOperation.smartUpsert(apiDownLinkMethodUo);
        return null;
    }
}
