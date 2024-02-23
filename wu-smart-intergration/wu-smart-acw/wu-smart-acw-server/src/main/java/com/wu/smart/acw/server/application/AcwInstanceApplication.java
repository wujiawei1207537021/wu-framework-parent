package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;

import java.util.List;

public interface AcwInstanceApplication {

    /**
     * 查询出所有的数据服务器
     *
     * @param acwInstanceUo
     * @return
     */
    Result<List<AcwInstanceUo>> list(AcwInstanceUo acwInstanceUo);

    /**
     * describe 数据保存
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/29 22:29
     **/
    Result<Void> save(AcwInstanceUo acwInstanceUo);

    /**
     * 数据删除
     *
     * @param id
     * @return
     */
    Result delete(String id);

    /**
     * 重新加载数据源
     *
     * @return
     */
    Result loading();

    /**
     * 重新初始化数据库服务器
     *
     * @param id 服务器ID
     * @return
     */
    Result reload(String id);

    /**
     * 数据库实例备份
     *
     * @param instanceId
     */
    void backUps(String instanceId);

    /**
     * describe  查询出分页的数据
     *
     * @param size          分页大小
     * @param current       当前页数
     * @param acwInstanceUo 查询参数对象
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    Result<LazyPage<AcwInstanceUo>> retrievePage(int size, int current, AcwInstanceUo acwInstanceUo);

    /**
     * 查询出所有的数据页面使用
     *
     * @param acwInstanceUo
     * @return
     */
    Result<List<AcwInstanceUo>> retrieveAll(AcwInstanceUo acwInstanceUo);

    /**
     * 测试连接
     * @param acwInstanceUo
     * @return
     */
    Result testConnection(AcwInstanceUo acwInstanceUo);
}
