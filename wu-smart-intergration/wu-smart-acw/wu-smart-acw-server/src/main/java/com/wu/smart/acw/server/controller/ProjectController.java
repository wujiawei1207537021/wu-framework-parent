package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.DependencyUo;
import com.wu.smart.acw.core.domain.uo.ProjectUo;
import com.wu.smart.acw.server.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * describe : 项目管理
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/9/19 5:31 下午
 */
@Api(tags = "ACW-项目管理")
@EasyController("/project")
public class ProjectController {
    private final ProjectService projectService;

    private final LazyOperation lazyOperation;
    private final LazyLambdaStream lambdaStream;

    public ProjectController(ProjectService projectService, LazyOperation lazyOperation, LazyLambdaStream lambdaStream) {
        this.projectService = projectService;
        this.lazyOperation = lazyOperation;
        this.lambdaStream = lambdaStream;
    }

    @ApiOperation(value = "新增/更新项目")
    @PostMapping()
    public Result save(@RequestBody ProjectUo project) {
        return projectService.save(project);
    }

    @ApiOperation(value = "删除项目")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        Integer collection = lambdaStream.of(ProjectUo.class).delete(LazyWrappers.<ProjectUo>lambdaWrapper().eqIgnoreEmpty(ProjectUo::getId, id));
        return ResultFactory.successOf(collection);
    }

    @ApiOperation(value = "新增/更新依赖")
    @PostMapping("/dependency")
    public Result dependency(@RequestBody DependencyUo dependencyUo) {
        lazyOperation.smartUpsert(dependencyUo);
        return ResultFactory.successOf();
    }

}
