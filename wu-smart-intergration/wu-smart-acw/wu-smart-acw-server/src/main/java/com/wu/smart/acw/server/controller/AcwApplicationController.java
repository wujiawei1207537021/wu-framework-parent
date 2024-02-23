package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.smart.acw.core.domain.dto.ApplicationDto;
import com.wu.smart.acw.core.domain.uo.AcwApplicationUo;
import com.wu.smart.acw.core.domain.uo.AcwProjectUo;
import com.wu.smart.acw.core.domain.uo.AcwTableUo;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * describe : 应用
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/30 00:14
 */
@Tag(name = "ACW-应用")
@EasyController("/application")
public class AcwApplicationController
//        extends AbstractLazyCrudProvider<AcwApplicationUo, Long>
{
    protected final LazyLambdaStream lazyLambdaStream;

    protected AcwApplicationController(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }


    /**
     * describe  新增
     *
     * @param acwApplicationUo 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result save(@RequestBody AcwApplicationUo acwApplicationUo) {
        lazyLambdaStream.upsert(acwApplicationUo);
        return ResultFactory.successOf();
    }

    /**
     * describe  查询出分页的数据
     *
     * @param size    分页大小
     * @param current 当前页数
     * @param t
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/

    @ApiOperation("查询出分页的数据")
    @GetMapping("/retrieve/page")
    public Result<LazyPage<AcwApplicationUo>> retrievePage(@RequestParam int size, @RequestParam int current, @ModelAttribute AcwApplicationUo t) {
        final LazyPage lazyPage = new LazyPage(current, size);
        final LazyPage resultLazyPage = lazyLambdaStream.selectPage(
                LazyWrappers.<AcwApplicationUo>lambdaWrapper()
                        .eq(AcwApplicationUo::getIsDeleted, false)
                        .leftJoin(
                                LazyWrappers.<AcwApplicationUo, AcwProjectUo>lambdaWrapperJoin()
                                        .eq(AcwApplicationUo::getProjectId, AcwProjectUo::getId)),
                lazyPage, ApplicationDto.class
        );
        return ResultFactory.successOf(resultLazyPage);
    }

    /**
     * describe  查询出所有的数据
     *
     * @param acwApplicationUo
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */
    @ApiOperation("查询出所有的数据")
    @GetMapping("/retrieve")
    public Result<List<ApplicationDto>> retrieveAll(@ModelAttribute AcwApplicationUo acwApplicationUo) {
        List<ApplicationDto> applicationDtoList = lazyLambdaStream.selectList(
                LazyWrappers.<AcwApplicationUo>lambdaWrapper()
                        .eq(AcwApplicationUo::getIsDeleted, false)
                        .leftJoin(
                                LazyWrappers.<AcwApplicationUo, AcwProjectUo>lambdaWrapperJoin()
                                        .eq(AcwApplicationUo::getProjectId, AcwProjectUo::getId))
                        .as(AcwProjectUo::getInstanceId, ApplicationDto::getInstanceId)
                , ApplicationDto.class
        );
        return ResultFactory.successOf(applicationDtoList);
    }

    /**
     * describe  根据主键ID 删除
     *
     * @param id 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("根据主键ID 删除")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        final Integer count = lazyLambdaStream.of(AcwApplicationUo.class).delete(LazyWrappers.<AcwApplicationUo>lambdaWrapper().eq(AcwApplicationUo::getApplicationId, id));
        if (1 == count) {
            return ResultFactory.successOf();
        } else {
            return ResultFactory.of(DefaultResultCode.DATA_NOT_FOUND);
        }
    }

    /**
     * 根据应用ID 获取数据库库表
     *
     * @param applicationId 应用ID
     * @return
     */
    @ApiOperation("根据应用ID 获取数据库库表")
    @GetMapping("/findTables/{applicationId}")
    public Result<List<AcwTableUo>> findTables(@PathVariable String applicationId) {
        AcwProjectUo acwProjectUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwApplicationUo>lambdaWrapper()
                .eq(AcwApplicationUo::getApplicationId, applicationId)
                .eq(AcwApplicationUo::getIsDeleted, false)
                .leftJoin(LazyWrappers.<AcwApplicationUo, AcwProjectUo>lambdaWrapperJoin()
                        .eq(AcwApplicationUo::getProjectId, AcwProjectUo::getId)
                        .eqRighto(AcwProjectUo::getIsDeleted, false))
                .as(AcwProjectUo.class), AcwProjectUo.class
        );
        if (ObjectUtils.isEmpty(acwProjectUo)) {
            RuntimeExceptionFactory.of("无法找到应用对应的项目");
        }
        String instanceId = acwProjectUo.getInstanceId();


        AcwApplicationUo acwApplicationUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwApplicationUo>lambdaWrapper()
                .eq(AcwApplicationUo::getApplicationId, applicationId)
                .eq(AcwApplicationUo::getIsDeleted, false)
        );
        String schemaName = acwApplicationUo.getSchemaName();
        // 切换数据源
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(instanceId);
        classLazyDynamicEndpoint.setSchema(schemaName);
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        // 查询列数据
        List<AcwTableUo> acwTableUoList = lazyLambdaStream.selectList(
                LazyWrappers.<LazyTableInfo>lambdaWrapper()
                        .like(!ObjectUtils.isEmpty(schemaName), LazyTableInfo::getTableSchema, schemaName)
                        .notNull(LazyTableInfo::getTableName)
                        .orderByAsc(LazyTableInfo::getTableName)
                        .oas("false", AcwTableUo::getInitializeToLocal)
                        .oas(String.valueOf(instanceId), AcwTableUo::getInstanceId)
                ,
                AcwTableUo.class
        );
        DynamicLazyDSContextHolder.clear();
        return ResultFactory.successOf(acwTableUoList);
    }
}
