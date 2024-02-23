package com.wu.smart.acw.server.domain.model.model.acw.project;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe ACW项目 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwProjectRepository {


    /**
     * describe 新增ACW项目
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwProject> story(AcwProject acwProject);

    /**
     * describe 批量新增ACW项目
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwProject> batchStory(List<AcwProject> acwProjectList);

    /**
     * describe 查询单个ACW项目
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwProject> findOne(AcwProject acwProject);

    /**
     * describe 查询多个ACW项目
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwProject>> findList(AcwProject acwProject);

    /**
     * describe 分页查询多个ACW项目
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwProject>> findPage(int size, int current, AcwProject acwProject);

    /**
     * describe 删除ACW项目
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwProject> remove(AcwProject acwProject);

}