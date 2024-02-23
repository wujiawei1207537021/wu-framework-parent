package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.JavaClassType;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.qo.ApiQo;
import com.wu.smart.acw.core.domain.uo.ApiUo;
import com.wu.smart.acw.core.domain.uo.ClassUo;
import com.wu.smart.acw.core.domain.uo.MethodUo;
import com.wu.smart.acw.core.domain.uo.TableClassUo;
import com.wu.smart.acw.server.service.ApiService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * describe :
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2022/1/1 10:15 下午
 */
@Service
public class ApiServiceImpl implements ApiService {

    private final LazyOperation lazyOperation;
    private final LazyLambdaStream lambda;


    public ApiServiceImpl(LazyOperation lazyOperation, LazyLambdaStream lambda) {
        this.lazyOperation = lazyOperation;
        this.lambda = lambda;
    }

    public static void main(String[] args) {

        DataTransformUntil.transform(ApiQo.class, ApiUo.class);
        Class clazz = RequestMethod.class;
        if (clazz.isEnum()) {
            final Enum delete = Enum.valueOf(clazz, "DELETE");
            System.out.println(delete);
        }
        RequestMethod requestMethod = RequestMethod.valueOf("xx");

    }

    /**
     * describe 新增API
     *
     * @param apiQo @return
     * @author Jia wei Wu
     * @date 2022/1/1 10:18 下午
     **/
    @Override
    public Result save(ApiQo apiQo) {

        ApiUo apiUo = new ApiUo();
        apiUo.setId(apiQo.getId());
        apiUo.setProjectId(apiQo.getProjectId());
        apiUo.setTag(apiQo.getTag());
        apiUo.setMethod(apiQo.getMethod());
        apiUo.setPath(apiQo.getPath());
        apiUo.setTableName(apiQo.getTableName());

        // 新增api
        lazyOperation.smartUpsert(apiUo);
        final Long apiId = apiUo.getId();
        final Long projectId = apiUo.getProjectId();
        final String tableName = apiUo.getTableName();
        // 查询表对应的class是否存在
        final TableClassUo tableClassUo = lambda.of(TableClassUo.class).select(LazyWrappers.<TableClassUo>lambdaWrapper()
                .eq(TableClassUo::getTableName, tableName)
                .eqIgnoreEmpty(TableClassUo::getProjectId, projectId)
        ).collectOne(TableClassUo.class);


        Long classId;
        if (ObjectUtils.isEmpty(tableClassUo)) {
            // 创建class
            final ClassUo saveClassUo = new ClassUo();
            saveClassUo.setProjectId(projectId)
                    .setJavaClassType(JavaClassType.CLASS)
                    .setType(LayerClass.LayerType.CONTROLLER)
                    .setInterfaceClass(null)
                    .setAnnotationList(null)
                    .setName(CamelAndUnderLineConverter.lineToHumpClass(tableName))
                    .setPackageName("com.acw.controller")
                    .setParentClass(null);
            lambda.insert(saveClassUo);
            // 创建class与table的关联表
            final TableClassUo classUo = new TableClassUo();
            classUo.setClassId(saveClassUo.getId()).setProjectId(projectId).setTableName(tableName);
            lambda.insert(classUo);
            classId = saveClassUo.getId();
        } else {
            classId = tableClassUo.getClassId();
        }
        // 新增method
        final MethodUo methodUo = new MethodUo();
        methodUo.setClassId(classId).setBody("");

        lambda.insert(methodUo);

        return ResultFactory.successOf(apiUo);
    }
}
