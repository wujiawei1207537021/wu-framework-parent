package com.wu.smart.acw.server.domain.model.acw.client.java.path;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPath;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 客户端使用创建Java代码常用路径 
 *
 * @author Jia wei Wu
 * @date 2023/12/08 06:04 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwClientJavaPathRepository {


    /**
     * describe 新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 新增客户端使用创建Java代码常用路径     
     * @return {@link  Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    Result<AcwClientJavaPath> story(AcwClientJavaPath acwClientJavaPath);

    /**
     * describe 批量新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathList 批量新增客户端使用创建Java代码常用路径     
     * @return {@link Result<List<AcwClientJavaPath>>} 客户端使用创建Java代码常用路径新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    Result<List<AcwClientJavaPath>> batchStory(List<AcwClientJavaPath> acwClientJavaPathList);

    /**
     * describe 查询单个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 查询单个客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    Result<AcwClientJavaPath> findOne(AcwClientJavaPath acwClientJavaPath);

    /**
     * describe 查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 查询多个客户端使用创建Java代码常用路径     
     * @return {@link Result<List<AcwClientJavaPath>>} 客户端使用创建Java代码常用路径DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    Result<List<AcwClientJavaPath>> findList(AcwClientJavaPath acwClientJavaPath);

    /**
     * describe 分页查询多个客户端使用创建Java代码常用路径
     *
     * @param size 当前页数
     * @param current 当前页
     * @param acwClientJavaPath 分页查询多个客户端使用创建Java代码常用路径     
     * @return {@link Result<LazyPage<AcwClientJavaPath>>} 分页客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    Result<LazyPage<AcwClientJavaPath>> findPage(int size,int current,AcwClientJavaPath acwClientJavaPath);

    /**
     * describe 删除客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 删除客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    Result<AcwClientJavaPath> remove(AcwClientJavaPath acwClientJavaPath);

    /**
     * describe 是否存在客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 是否存在客户端使用创建Java代码常用路径     
     * @return {@link Result<Boolean>} 客户端使用创建Java代码常用路径是否存在     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    Result<Boolean> exists(AcwClientJavaPath acwClientJavaPath);

}