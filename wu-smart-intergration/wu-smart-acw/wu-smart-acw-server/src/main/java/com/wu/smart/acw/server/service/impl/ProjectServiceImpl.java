package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.ProjectUo;
import com.wu.smart.acw.server.service.ProjectService;
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

    public ProjectServiceImpl(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }


    @Override
    public Result save(ProjectUo project) {
        lazyOperation.smartUpsert(project);
        return ResultFactory.successOf(project);
    }
}
