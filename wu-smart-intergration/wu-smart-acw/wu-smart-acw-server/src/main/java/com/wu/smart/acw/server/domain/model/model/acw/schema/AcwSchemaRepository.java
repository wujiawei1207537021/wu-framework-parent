package com.wu.smart.acw.server.domain.model.model.acw.schema;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;

import java.util.List;

/**
 * describe ACW 数据库库信息
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface AcwSchemaRepository {


    /**
     * describe 新增ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwSchema> story(AcwSchema acwSchema);

    /**
     * describe 批量新增ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwSchema>> batchStory(List<AcwSchema> acwSchemaList);

    /**
     * describe 查询单个ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwSchema> findOne(AcwSchema acwSchema);

    /**
     * describe 查询多个ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwSchema>> findListWithSize(AcwSchema acwSchema);

    /**
     * describe 分页查询多个ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwSchema>> findPage(int size, int current, AcwSchema acwSchema);

    /**
     * describe 删除ACW 数据库库信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwSchema> remove(AcwSchema acwSchema);

    /**
     * 切换schema
     *
     * @param instanceId
     * @param scheme
     */
    void switchSchema(String instanceId, String scheme);

    /**
     * 重新初始化数据库
     *
     * @param instanceId 实例名称
     * @param schema     数据库
     */
    Result reload(String instanceId, String schema);

    /**
     * 获取schema分页
     *
     * @param size
     * @param current
     * @param acwSchema
     * @return
     */
    Result<LazyPage<AcwSchema>> retrievePage1(int size, int current, AcwSchema acwSchema);

    /**
     * 切换数据源查询数据库
     *
     * @param size
     * @param current
     * @param acwInstanceUo
     * @param acwSchema
     * @return
     */
    Result<LazyPage<AcwSchema>> switchSchemaRetrievePage(int size, int current, AcwInstanceUo acwInstanceUo, AcwSchema acwSchema);

    /**
     * 查询出所有的数据页面使用
     *
     * @param acwSchema
     * @return
     */
    Result<List<AcwSchema>> switchSchemaRetrieve(AcwSchema acwSchema);

    Result<List<AcwSchema>> findList(AcwSchema acwSchema);
}