package com.wu.smart.acw.server.controller;

import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwDependencyUo;
import com.wu.smart.acw.core.domain.uo.AcwProjectUo;
import com.wu.smart.acw.server.application.ProjectApplication;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * describe : 项目管理
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/9/19 5:31 下午
 */
@Tag(name = "ACW-项目管理")
@EasyController("/project")
public class AcwProjectController
        extends AbstractLazyCrudProvider<AcwProjectUo, AcwProjectUo, Long> {
    private final ProjectApplication projectService;

    private final LazyLambdaStream lambdaStream;

    public AcwProjectController(ProjectApplication projectService, LazyLambdaStream lambdaStream) {
        this.projectService = projectService;
        this.lambdaStream = lambdaStream;
    }

    @Override
    @ApiOperation(value = "新增/更新项目")
    public Result<AcwProjectUo> save(@RequestBody AcwProjectUo project) {
        return projectService.save(project);
    }

    /**
     * describe  根据更新
     *
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("更新")
    @PutMapping("/update")
    public Result<AcwProjectUo> update(AcwProjectUo model) {
        return super.update(model);
    }

    @Override
    @ApiOperation(value = "删除项目")
    public Result deleteById(@PathVariable("id") Long id) {
        Integer collection = lambdaStream.of(AcwProjectUo.class).delete(LazyWrappers.<AcwProjectUo>lambdaWrapper().eqIgnoreEmpty(AcwProjectUo::getId, id));
        return ResultFactory.successOf(collection);
    }

    @ApiOperation(value = "新增/更新依赖")
    @PostMapping("/dependency")
    public Result dependency(@RequestBody AcwDependencyUo acwDependencyUo) {
        lambdaStream.upsert(acwDependencyUo);
        return ResultFactory.successOf();
    }

    /**
     * describe  查询出所有的数据
     *
     * @param acwProjectUo
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */
    @Override
    public Result<List<AcwProjectUo>> retrieveAll(AcwProjectUo acwProjectUo) {
        return projectService.retrieveAll(acwProjectUo);
    }

    /**
     * describe  查询出分页的数据
     *
     * @param size         分页大小
     * @param current      当前页数
     * @param acwProjectUo 查询参数对象
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<LazyPage<AcwProjectUo>> retrievePage(int size, int current, AcwProjectUo acwProjectUo) {
        return projectService.retrievePage(size, current, acwProjectUo);
    }
}
