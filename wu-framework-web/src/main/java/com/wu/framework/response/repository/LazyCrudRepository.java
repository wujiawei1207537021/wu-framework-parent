package com.wu.framework.response.repository;

import com.wu.framework.response.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


public interface LazyCrudRepository<T/*实体对象*/, MODEL/*返回数据模型*/, ID/*主键ID*/> extends LazyRepository<T/*实体对象*/, MODEL/*返回数据模型*/> {

    /**
     * describe  新增
     *
     * @param model 领域对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @PostMapping("/save")
    Result<MODEL> save(@RequestBody MODEL model);

    /**
     * describe  批量存储 增量更新
     *
     * @param models 领域对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation(value = "新增")
    @PostMapping("/upsert")
    Result<Collection<MODEL>> upsert(@RequestBody Collection<MODEL> models);

    /**
     * describe  根据更新
     *
     * @param model 领域对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @PutMapping("/update")
    Result<MODEL> update(@RequestBody MODEL model);

    /**
     * describe  根据主键ID查询
     *
     * @param id 主键ID
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @GetMapping("/retrieve/{id}")
    Result<MODEL> findById(@PathVariable("id") ID id);

    /**
     * describe  根据主键ID查询是否存在
     *
     * @param id 主键ID
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @GetMapping("/exists/{id}")
    Result<Boolean> existsById(@PathVariable("id") ID id);


    /**
     * describe  统计表数据
     *
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @GetMapping("/count")
    Result<Long> count();

    /**
     * describe  根据主键ID 删除
     *
     * @param id 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable("id") ID id);


}
