package com.wu.framework.response.repository;

import com.wu.framework.response.Result;
import org.springframework.web.bind.annotation.*;


public interface LazyCrudRepository<T, ID> extends LazyRepository<T> {

    /**
     * describe  新增
     *
     * @param s 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @PostMapping("/save")
    <S extends T> Result<S> save(@RequestBody S s);

    /**
     * describe  根据更新
     *
     * @param s 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @PutMapping("/update")
    <S extends T> Result<S> update(@RequestBody S s);

    /**
     * describe  根据主键ID查询
     *
     * @param id 主键ID
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @GetMapping("/retrieve/{id}")
    Result<T> findById(@PathVariable ID id);

    /**
     * describe  根据主键ID查询是否存在
     *
     * @param id 主键ID
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @GetMapping("/exists/{id}")
    Result<Boolean> existsById(@PathVariable ID id);


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
    Result deleteById(@PathVariable ID id);


}
