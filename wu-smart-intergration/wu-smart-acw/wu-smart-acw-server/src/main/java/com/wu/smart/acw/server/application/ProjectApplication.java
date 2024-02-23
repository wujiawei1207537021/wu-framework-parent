package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.AcwProjectUo;

import java.util.List;

public interface ProjectApplication {
    Result save(AcwProjectUo project);

    /**
     * 查询出分页的数据
     *
     * @param size
     * @param current
     * @param acwProjectUo
     * @return
     */
    Result<LazyPage<AcwProjectUo>> retrievePage(int size, int current, AcwProjectUo acwProjectUo);

    /**
     * 查询出所有的数据
     *
     * @param acwProjectUo
     * @return
     */
    Result<List<AcwProjectUo>> retrieveAll(AcwProjectUo acwProjectUo);
}
