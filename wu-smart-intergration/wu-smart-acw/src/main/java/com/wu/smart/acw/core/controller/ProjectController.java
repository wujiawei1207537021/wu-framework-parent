package com.wu.smart.acw.core.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LambdaStream;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.ProjectUo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * describe : 项目管理
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2021/9/19 5:31 下午
 */
@Api(tags = "项目管理")
@EasyController("/project")
public class ProjectController {

    private final LazyOperation lazyOperation;
    private final LambdaStream lambdaStream;

    public ProjectController(LazyOperation lazyOperation, LambdaStream lambdaStream) {
        this.lazyOperation = lazyOperation;
        this.lambdaStream = lambdaStream;
    }

    @ApiOperation(value = "新增/更新项目")
    @PostMapping()
    public Result save(@RequestBody ProjectUo project) {
        lazyOperation.smartUpsert(project);
        return ResultFactory.successOf();
    }

    @ApiOperation(value = "新增项目")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        val collection = lambdaStream.delete().table(ProjectUo.class).eq("id", id).collection();
        return ResultFactory.successOf(collection);
    }

}
