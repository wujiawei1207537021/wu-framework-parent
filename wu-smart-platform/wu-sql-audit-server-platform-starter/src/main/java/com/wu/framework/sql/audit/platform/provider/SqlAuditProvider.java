package com.wu.framework.sql.audit.platform.provider;


import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.sql.audit.platform.application.SqlAuditApplication;
import com.wu.framework.sql.audit.platform.application.command.SqlAuditCommand;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAudit;
import com.wu.framework.sql.audit.platform.infrastructure.entity.SqlAuditDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Slf4j
@Tag(name = "提供者")
@EasyController("/sql/audit")
public class SqlAuditProvider extends AbstractLazyCrudProvider<SqlAuditDO,SqlAuditDO, String> {

    @Autowired
    private SqlAuditApplication sqlAuditApplication;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @PostMapping("/story")
    public Result<SqlAudit> story(@RequestBody SqlAuditCommand sqlAuditCommand) {
        return sqlAuditApplication.story(sqlAuditCommand);
    }

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @PutMapping("/updateOne")
    public Result<SqlAudit> updateOne(@RequestBody SqlAuditCommand sqlAuditCommand) {
        return sqlAuditApplication.updateOne(sqlAuditCommand);
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @GetMapping("/findOne")
    public Result<SqlAudit> findOne(@ModelAttribute SqlAuditCommand sqlAuditCommand) {
        return sqlAuditApplication.findOne(sqlAuditCommand);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @GetMapping("/findList")
    public Result<List<SqlAudit>> findList(@ModelAttribute SqlAuditCommand sqlAuditCommand) {
        return sqlAuditApplication.findList(sqlAuditCommand);
    }

    /**
     * 导出审计信息
     *
     * @param sqlAuditCommand
     * @return
     */
    @EasyExcel(fileName = "导出审计信息")
    @GetMapping("/export/findList")
    public List<SqlAudit> exportFindList(@ModelAttribute SqlAuditCommand sqlAuditCommand) {
        return sqlAuditApplication.findList(sqlAuditCommand).getData();
    }

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<SqlAudit>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                               @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute SqlAuditCommand sqlAuditCommand) {
        return sqlAuditApplication.findPage(size, current, sqlAuditCommand);
    }

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/24 09:53 上午
     **/

    @DeleteMapping("/remove")
    public Result<SqlAudit> remove(@RequestBody SqlAuditCommand sqlAuditCommand) {
        return sqlAuditApplication.remove(sqlAuditCommand);
    }
}