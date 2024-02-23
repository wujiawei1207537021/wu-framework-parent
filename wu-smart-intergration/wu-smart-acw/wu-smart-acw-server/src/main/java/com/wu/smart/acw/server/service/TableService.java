package com.wu.smart.acw.server.service;

import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe : 表操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/31 22:42
 */
public interface TableService {

    /**
     * describe 数据存储
     *
     * @param tableName 表名
     * @param data      数据
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:46
     **/
    Result dataStorage(String tableName, List<EasyHashMap> data);
}
