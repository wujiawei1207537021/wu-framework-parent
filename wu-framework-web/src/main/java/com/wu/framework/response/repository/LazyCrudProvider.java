package com.wu.framework.response.repository;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.response.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * describe : crud 控制层
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/31 14:07
 */
public interface LazyCrudProvider<T/*实体对象*/, MODEL/*返回数据模型*/, ID/*主键ID*/> extends LazyProvider<T/*实体对象*/, MODEL/*返回数据模型*/> {

    /**
     * describe  新增
     *
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    Result<MODEL> save(@RequestBody MODEL model);

    /**
     * describe  批量存储 增量更新
     *
     * @param models 实体对象
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
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("更新")
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
    @ApiOperation("根据主键ID查询")
    @GetMapping("/retrieve/{id}")
    Result<MODEL> findById(@PathVariable("id") ID id);

    /**
     * 查询一条数据
     *
     * @param comparison
     * @return
     */
    @ApiOperation("查询一条数据")
    @PostMapping("/retrieve/one")
    Result<MODEL> findOne(@RequestBody LambdaBasicComparison<T> comparison);

    /**
     * 查询所有数据（内部使用）
     *
     * @param comparison
     * @return
     */
    @ApiOperation("查询所有数据")
    @PostMapping("/retrieve/all")
    Result<List<MODEL>> findAll(@RequestBody LambdaBasicComparison<T> comparison);

    /**
     * describe  查询出所有的数据页面使用
     *
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出所有的数据")
    @GetMapping("/retrieve")
    Result<List<MODEL>> retrieveAll(@ModelAttribute T t);

    /**
     * describe  查询出分页的数据
     *
     * @param size    分页大小
     * @param current 当前页数
     * @param t       查询参数对象
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出分页的数据")
    @GetMapping("/retrieve/page")
    Result<LazyPage<MODEL>> retrievePage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                         @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current,
                                         @ModelAttribute T t);

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
    Result<Boolean> existsById(@PathVariable("id") ID id);


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
    Result deleteById(@PathVariable("id") ID id);

    /**
     * describe  根据主键ID 批量删除
     *
     * @param ids 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("根据主键ID 删除")
    @DeleteMapping("/batch")
    Result batchDeleteById(@RequestBody List<ID> ids);
}
