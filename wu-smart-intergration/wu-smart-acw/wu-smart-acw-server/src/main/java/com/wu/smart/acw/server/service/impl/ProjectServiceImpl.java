package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.ProjectUo;
import com.wu.smart.acw.server.service.ProjectService;
import com.wu.smart.acw.server.service.TableClassService;
import org.springframework.stereotype.Service;

/**
 * describe :
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2022/1/1 10:05 下午
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final LazyOperation lazyOperation;
    private final TableClassService listClassService;

    public ProjectServiceImpl(LazyOperation lazyOperation, TableClassService listClassService) {
        this.lazyOperation = lazyOperation;
        this.listClassService = listClassService;
    }


    @Override
    public Result save(ProjectUo project) {
        lazyOperation.smartUpsert(project);
        final Long id = project.getId();
        final Long databaseServerId = project.getDatabaseServerId();
        final String schema = project.getSchema();
        listClassService.singleTable(id, databaseServerId, schema);
        return ResultFactory.successOf(project);
    }
}
