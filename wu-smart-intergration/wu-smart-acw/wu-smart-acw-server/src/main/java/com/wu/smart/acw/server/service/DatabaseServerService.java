package com.wu.smart.acw.server.service;

import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.DatabaseServerUo;

import java.util.List;

public interface DatabaseServerService {

    /**
     * 查询出所有的数据服务器
     *
     * @param databaseServerUo
     * @return
     */
    Result<List<DatabaseServerUo>> list(DatabaseServerUo databaseServerUo);

    /**
     * describe 数据保存
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/29 22:29
     **/
    Result save(DatabaseServerUo databaseServerUo);

    /**
     * 数据删除
     *
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 重新加载数据源
     *
     * @return
     */
    Result loading();
}
