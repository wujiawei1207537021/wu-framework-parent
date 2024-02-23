package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.dto.ProjectDto;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.core.domain.uo.AcwProjectUo;
import com.wu.smart.acw.server.application.ProjectApplication;
import com.wu.smart.acw.server.application.TableClassApplication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/1 10:05 下午
 */
@Service
public class ProjectServiceImpl implements ProjectApplication {
    private final LazyLambdaStream lazyLambdaStream;
    private final TableClassApplication listClassService;

    public ProjectServiceImpl(LazyLambdaStream lazyLambdaStream, TableClassApplication listClassService) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.listClassService = listClassService;
    }


    @Override
    public Result save(AcwProjectUo project) {
        lazyLambdaStream.upsert(project);
        final Long id = project.getId();
        final String instanceId = project.getInstanceId();
//        final Long schemaId = project.getDatabaseSchemaId();
//        listClassService.singleTable(id, instanceId, schema);
        return ResultFactory.successOf(project);
    }

    @Override
    public Result<LazyPage<AcwProjectUo>> retrievePage(int size, int current, AcwProjectUo acwProjectUo) {
        LazyPage<ProjectDto> lazyPage = new LazyPage(current, size);
        LazyPage resultLazyPage = lazyLambdaStream.of(AcwProjectUo.class).
                selectPage(LazyWrappers.lambdaWrapperBean(acwProjectUo)
                                .leftJoin(
                                        LazyWrappers.<AcwProjectUo, AcwInstanceUo>lambdaWrapperJoin()
                                                .eq(AcwProjectUo::getInstanceId, AcwInstanceUo::getId))
                                .orderByDesc(AcwProjectUo::getCreateTime)
                                .as(AcwProjectUo::getId, ProjectDto::getId)
                                .as(AcwInstanceUo::getInstanceName, ProjectDto::getInstanceName),
                        lazyPage, ProjectDto.class
                );
        return ResultFactory.successOf(resultLazyPage);
    }

    @Override
    public Result<List<AcwProjectUo>> retrieveAll(AcwProjectUo acwProjectUo) {
        final Collection collection = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwProjectUo)
                .orderByDesc(AcwProjectUo::getCreateTime)
        );
        return ResultFactory.successOf(collection.stream().toList());
    }
}
