package com.wu.framework.response.repository;

import com.wu.framework.response.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe : crud 控制层
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/31 14:07
 */
public interface LazyCrudProvider<T, ID> extends LazyProvider<T> {

    /**
     * describe  新增
     *
     * @param s 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation(value = "新增")
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
    @ApiOperation("更新")
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
    @ApiOperation("根据主键ID查询")
    @GetMapping("/retrieve/{id}")
    Result<T> findById(@PathVariable ID id);

    /**
     * describe  查询出所有的数据
     *
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出所有的数据")
    @GetMapping("/retrieve")
    Result<List<T>> retrieveAll();

    /**
     * describe  查询出分页的数据
     *
     * @param size    分页大小
     * @param current 当前页数
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出分页的数据")
    @GetMapping("/retrieve/page")
    Result retrievePage(@ApiParam("分页大小") @RequestParam(defaultValue = "10") int size,
                        @ApiParam("当前页数") @RequestParam(defaultValue = "1") int current);

    /**
     * describe  根据主键ID查询是否存在
     *
     * @param id 主键ID
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("根据主键ID查询是否存在")
    @GetMapping("/exists/{id}")
    Result<Boolean> existsById(@PathVariable ID id);


    /**
     * describe  统计表数据
     *
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("统计表数据")
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
    @ApiOperation("根据主键ID 删除")
    @DeleteMapping("/{id}")
    Result deleteById(@PathVariable ID id);
}
