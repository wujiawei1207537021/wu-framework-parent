package com.wu.smart.acw.server.application.impl;

import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.qo.ApiParamQo;
import com.wu.smart.acw.core.domain.qo.ApiQo;
import com.wu.smart.acw.core.domain.uo.*;
import com.wu.smart.acw.server.application.ApiApplication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/1 10:15 下午
 */
@Service
public class ApiServiceImpl implements ApiApplication {

    private final LazyLambdaStream lambda;


    public ApiServiceImpl(LazyLambdaStream lambda) {
        this.lambda = lambda;
    }

    public static void main(String[] args) {


//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(LoginUserBO.class);
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(LoginUserBO.class);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
        System.out.println(lazyTableEndpoint);
        DataTransformUntil.transform(ApiQo.class, AcwApplicationApiUo.class);
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

        AcwApplicationApiUo acwApplicationApiUo = new AcwApplicationApiUo();
        acwApplicationApiUo.setId(apiQo.getId());
        acwApplicationApiUo.setApplicationId(apiQo.getApplicationId());
        acwApplicationApiUo.setTag(apiQo.getTag());
        acwApplicationApiUo.setMethod(apiQo.getMethod());
        acwApplicationApiUo.setPath(apiQo.getPath());
//        acwApplicationApiUo.setTableIds(apiQo.getTableIds().stream().map(Object::toString).collect(Collectors.joining(NormalUsedString.COMMA)));
        acwApplicationApiUo.setDescription(apiQo.getDescription());


        // 新增api
        lambda.upsert(acwApplicationApiUo);
        Long apiId = apiQo.getId() != null ? apiQo.getId() : acwApplicationApiUo.getId();
        //删除api 与表的关系
        lambda.delete(LazyWrappers.<AcwApplicationApiTableUo>lambdaWrapper().eq(AcwApplicationApiTableUo::getApiId, apiId));
        for (String tableName : apiQo.getTableNameList()) {
            AcwApplicationApiTableUo acwApplicationApiTableUo = new AcwApplicationApiTableUo();

            acwApplicationApiTableUo.setIsDeleted(false);
            acwApplicationApiTableUo.setApiId(apiId);
            acwApplicationApiTableUo.setTableName(tableName);
            lambda.upsert(acwApplicationApiTableUo);
        }
        // 删除参数api关联关系
        lambda.delete(LazyWrappers.<AcwApplicationApiParamUo>lambdaWrapper().eq(AcwApplicationApiParamUo::getApiId, apiId));
        List<ApiParamQo> apiParamQoList = apiQo.getApiParamQoList();
        List<AcwApplicationApiParamUo> acwApplicationApiParamUoList = apiParamQoList.stream().map(apiParamQo -> {
            AcwApplicationApiParamUo acwApplicationApiParamUo = new AcwApplicationApiParamUo();
            acwApplicationApiParamUo.setIsDeleted(false);
            acwApplicationApiParamUo.setApiId(apiId);
            acwApplicationApiParamUo.setColumnName(apiParamQo.getColumnName());
            acwApplicationApiParamUo.setTerm(apiParamQo.getTerm());
            acwApplicationApiParamUo.setType(apiParamQo.getType());
            acwApplicationApiParamUo.setName(apiParamQo.getName());
            return acwApplicationApiParamUo;
        }).collect(Collectors.toList());
        lambda.upsert(acwApplicationApiParamUoList);

        // 删除表 table  class 关联关系
//        AcwTableClassUo updateTableClassUo = new AcwTableClassUo();
//        updateTableClassUo.setIsDeleted(true);
//        lambda.update(updateTableClassUo,LazyWrappers.<AcwTableClassUo>lambdaWrapper().eq(AcwTableClassUo::getApiId,apiId));

        Long applicationId = acwApplicationApiUo.getApplicationId();
        List<String> tableNameList = apiQo.getTableNameList();
        // 查询表对应的class是否存在
        AcwTableClassUo acwTableClassUo = lambda.of(AcwTableClassUo.class).select(LazyWrappers.<AcwTableClassUo>lambdaWrapper()
                .in(AcwTableClassUo::getTableName, tableNameList)
                .eqIgnoreEmpty(AcwTableClassUo::getProjectId, applicationId)
        ).collectOne(AcwTableClassUo.class);

        if (!ObjectUtils.isEmpty(acwTableClassUo)) {
            // 获取主表对应的class
            Long classId = acwTableClassUo.getClassId();

            // 新增method
            final AcwMethodUo acwMethodUo = new AcwMethodUo();
            acwMethodUo.setClassId(classId).setBody("");

            lambda.insert(acwMethodUo);
        }
        return ResultFactory.successOf(acwApplicationApiUo);
    }
}
