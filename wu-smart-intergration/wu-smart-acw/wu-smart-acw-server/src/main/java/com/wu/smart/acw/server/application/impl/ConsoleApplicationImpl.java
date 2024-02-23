package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.server.application.ConsoleApplication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 14:10
 */
@Service
public class ConsoleApplicationImpl implements ConsoleApplication {

    private final LazyLambdaStream lazyLambdaStream;


    public ConsoleApplicationImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;

    }

    /**
     * describe 执行sql语句
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param sql        执行的sql
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:47
     **/
    @Override
    public Result<List<List<LinkedHashMap>>> sqlConsole(String instanceId, String schemaName, String sql) {

        Assert.notNull(instanceId, "数据库实例ID不能为空");
        Assert.notNull(schemaName, "数据库不能为空");
        Assert.notNull(sql, "执行的sql不能为空");


        AcwInstanceUo acwInstanceUo = lazyLambdaStream
                .selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                        .eq(AcwInstanceUo::getIsDeleted, false)
                        .eq(AcwInstanceUo::getId, instanceId)
                        .limit(1));
        Assert.notNull(acwInstanceUo, "数据库实例不存在");
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwInstanceUo.getId());
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        List<List<LinkedHashMap>> listList = new ArrayList<>();
        String[] sqlList = sql.split(";");
        try {
            for (String splitSql : sqlList) {
                if (ObjectUtils.isEmpty(splitSql) || NormalUsedString.NEWLINE.equals(splitSql)) {
                    continue;
                }
                List<LinkedHashMap> easyHashMaps = lazyLambdaStream.executeSQL(splitSql, LinkedHashMap.class);
                listList.add(easyHashMaps);
            }
            return ResultFactory.successOf(listList);
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e.getMessage());
            return null;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }

    /***
     * 导出执行sql upsert语句
     * @param instanceId
     * @param schemaName
     * @param sql
     * @return
     */
    @Override
    public String sqlConsoleUpsertExport(String instanceId, String schemaName, String sql) {
        Assert.notNull(instanceId, "数据库实例ID不能为空");
        Assert.notNull(schemaName, "数据库不能为空");
        Assert.notNull(sql, "执行的sql不能为空");


        AcwInstanceUo acwInstanceUo = lazyLambdaStream
                .selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                        .eq(AcwInstanceUo::getIsDeleted, false)
                        .eq(AcwInstanceUo::getId, instanceId)
                        .limit(1));
        Assert.notNull(acwInstanceUo, "数据库实例不存在");
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setName(acwInstanceUo.getId());
        lazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        try {
            List<EasyHashMap> easyHashMaps = lazyLambdaStream.executeSQL(sql, EasyHashMap.class);
            return LazyTableUpsertConverterFactory.upsert(easyHashMaps);
        } catch (Exception e) {
            e.printStackTrace();
            RuntimeExceptionFactory.of(e.getMessage());
            return null;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }
    }
}
