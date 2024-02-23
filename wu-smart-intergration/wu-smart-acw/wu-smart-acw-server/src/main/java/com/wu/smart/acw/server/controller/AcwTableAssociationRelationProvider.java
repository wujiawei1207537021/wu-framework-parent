package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwTableAssociationRelationApplication;
import com.wu.smart.acw.server.application.command.acw.table.association.relation.*;
import com.wu.smart.acw.server.application.dto.AcwTableAssociationRelationDTO;
import com.wu.smart.acw.server.domain.model.acw.table.association.relation.AcwTableAssociationRelation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 表关联关系
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "表关联关系提供者")
@EasyController("/lazy/acw/table/association/relation")
public class AcwTableAssociationRelationProvider {

    @Autowired
    private AcwTableAssociationRelationApplication acwTableAssociationRelationApplication;

    /**
     * describe 新增表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Operation(summary = "新增表关联关系")
    @PostMapping("/story")
    public Result<AcwTableAssociationRelation> story(@RequestBody AcwTableAssociationRelationStoryCommand acwTableAssociationRelationStoryCommand) {
        return acwTableAssociationRelationApplication.story(acwTableAssociationRelationStoryCommand);
    }

    /**
     * describe 更新表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Operation(summary = "更新表关联关系")
    @PutMapping("/updateOne")
    public Result<AcwTableAssociationRelation> updateOne(@RequestBody AcwTableAssociationRelationUpdateCommand acwTableAssociationRelationUpdateCommand) {
        return acwTableAssociationRelationApplication.updateOne(acwTableAssociationRelationUpdateCommand);
    }

    /**
     * describe 查询单个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Operation(summary = "查询单个表关联关系")
    @GetMapping("/findOne")
    public Result<AcwTableAssociationRelationDTO> findOne(@ModelAttribute AcwTableAssociationRelationQueryOneCommand acwTableAssociationRelationQueryOneCommand) {
        return acwTableAssociationRelationApplication.findOne(acwTableAssociationRelationQueryOneCommand);
    }

    /**
     * describe 查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Operation(summary = "查询多个表关联关系")
    @GetMapping("/findList")
    public Result<List<AcwTableAssociationRelationDTO>> findList(@ModelAttribute AcwTableAssociationRelationQueryListCommand acwTableAssociationRelationQueryListCommand) {
        return acwTableAssociationRelationApplication.findList(acwTableAssociationRelationQueryListCommand);
    }

    /**
     * describe 分页查询多个表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Operation(summary = "分页查询多个表关联关系")
    @GetMapping("/findPage")
    public Result<LazyPage<AcwTableAssociationRelationDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                                     @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute AcwTableAssociationRelationQueryListCommand acwTableAssociationRelationQueryListCommand) {
        return acwTableAssociationRelationApplication.findPage(size, current, acwTableAssociationRelationQueryListCommand);
    }

    /**
     * 分析数据库中表与表之间的关系
     *
     * @param acwTableAssociationRelationAnalysisSchemaCommand 分析数据库中表与表之间的关系
     * @return
     */
    @Operation(summary = "分析数据库中表与表之间的关系")
    @PutMapping("/analysisSchema")
    public Result<Void> analysisSchema(@RequestBody AcwTableAssociationRelationAnalysisSchemaCommand acwTableAssociationRelationAnalysisSchemaCommand) {
        return acwTableAssociationRelationApplication.analysisSchema(acwTableAssociationRelationAnalysisSchemaCommand);
    }

    /**
     * describe 删除表关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/01 10:16 晚上
     **/

    @Operation(summary = "删除表关联关系")
    @DeleteMapping("/remove")
    public Result<AcwTableAssociationRelation> remove(@ModelAttribute AcwTableAssociationRelationRemoveCommand acwTableAssociationRelationRemoveCommand) {
        return acwTableAssociationRelationApplication.remove(acwTableAssociationRelationRemoveCommand);
    }
}