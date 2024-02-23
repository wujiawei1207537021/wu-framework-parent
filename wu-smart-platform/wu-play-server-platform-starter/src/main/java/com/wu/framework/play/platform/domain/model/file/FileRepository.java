package com.wu.framework.play.platform.domain.model.file;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.play.platform.domain.model.file.File;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe file 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface FileRepository {


    /**
     * describe 新增
     *
     * @param file 新增     
     * @return {@link  Result<File>} 新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<File> story(File file);

    /**
     * describe 批量新增
     *
     * @param fileList 批量新增     
     * @return {@link Result<List<File>>} 新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<File>> batchStory(List<File> fileList);

    /**
     * describe 查询单个
     *
     * @param file 查询单个     
     * @return {@link Result<File>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<File> findOne(File file);

    /**
     * describe 查询多个
     *
     * @param file 查询多个     
     * @return {@link Result<List<File>>} DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<List<File>> findList(File file);

    /**
     * describe 分页查询多个
     *
     * @param size 当前页数
     * @param current 当前页
     * @param file 分页查询多个     
     * @return {@link Result<LazyPage<File>>} 分页领域对象     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<LazyPage<File>> findPage(int size,int current,File file);

    /**
     * describe 删除
     *
     * @param file 删除     
     * @return {@link Result<File>}      
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<File> remove(File file);

    /**
     * describe 是否存在
     *
     * @param file 是否存在     
     * @return {@link Result<Boolean>} 是否存在     
     
     * @author Jia wei Wu
     * @date 2024/02/19 10:26 上午
     **/

    Result<Boolean> exists(File file);

}