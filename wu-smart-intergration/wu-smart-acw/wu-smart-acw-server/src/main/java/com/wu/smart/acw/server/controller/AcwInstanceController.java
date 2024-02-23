package com.wu.smart.acw.server.controller;


import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.data.encryption.NormalConvertEncryptionDecryptionMapper;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.server.application.AcwInstanceApplication;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jia wei Wu
 */
@Tag(name = "ACW-数据库服务器")
@EasyController("/database/instance")
public class AcwInstanceController extends AbstractLazyCrudProvider<AcwInstanceUo, AcwInstanceUo, String> {

    private final AcwInstanceApplication acwInstanceApplication;

    protected AcwInstanceController(AcwInstanceApplication acwInstanceApplication) {
        this.acwInstanceApplication = acwInstanceApplication;
    }


    @Override
    @ApiOperation(value = "新增/更新数据库服务器")
    @PostMapping("/save")
    public Result save(@RequestBody AcwInstanceUo acwInstanceUo) {
        return acwInstanceApplication.save(acwInstanceUo);
    }


    /**
     * 测试连接
     *
     * @param acwInstanceUo
     * @return
     */
    @ApiOperation(value = "测试连接")
    @PostMapping("/testConnection")
    public Result testConnection(@RequestBody AcwInstanceUo acwInstanceUo) {
        return acwInstanceApplication.testConnection(acwInstanceUo);
    }


    /**
     * describe  根据更新
     *
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    @ApiOperation("更新")
    @PutMapping("/update")
    public Result update(AcwInstanceUo model) {
        return acwInstanceApplication.save(model);
    }

    /**
     * describe  数据库实例备份
     *
     * @param instanceId 实例ID
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("数据库实例备份")
    @PatchMapping("/backUps")
    public void backUps(String instanceId) {
        acwInstanceApplication.backUps(instanceId);
    }


    /**
     * describe  查询出分页的数据
     *
     * @param size    分页大小
     * @param current 当前页数
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @NormalConvertEncryptionDecryptionMapper
    @ApiOperation("查询出分页的数据")
    @GetMapping("/retrieve/page")
    public Result<LazyPage<AcwInstanceUo>> retrievePage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                        @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current,
                                                        @ModelAttribute AcwInstanceUo acwInstanceUo) {
        return acwInstanceApplication.retrievePage(size, current, acwInstanceUo);
    }


    /**
     * 查询所有数据
     *
     * @param comparison
     * @return
     */
    @Override
    public Result<List<AcwInstanceUo>> findAll(LambdaBasicComparison<AcwInstanceUo> comparison) {
        return super.findAll(comparison);
    }

    @ApiOperation(value = "删除数据库服务器")
    @DeleteMapping("/remove/{id}")
    public Result delete(@PathVariable("id") String id) {
        return acwInstanceApplication.delete(id);
    }

    /**
     * describe  查询出所有的数据
     *
     * @param databaseInstanceUo
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */


    /**
     * describe  查询出所有的数据页面使用
     *
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出所有的数据")
    @GetMapping("/retrieve")
    public Result<List<AcwInstanceUo>> retrieveAll(AcwInstanceUo acwInstanceUo) {
        return acwInstanceApplication.retrieveAll(acwInstanceUo);
    }

    /**
     * 重新初始化数据库服务器
     *
     * @param id 服务器ID
     * @return
     */
    @ApiOperation(value = "重新初始化数据库服务器")
    @PatchMapping("/reload/{id}")
    public Result reload(@PathVariable("id") String id) {
        return acwInstanceApplication.reload(id);
    }
}
