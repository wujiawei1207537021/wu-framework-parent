package com.wu.framework.database.generator.controller;


import com.wu.framework.database.generator.service.SysGeneratorService;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.Page;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@EasyController("/sys/generator")
public class SysGeneratorController {

    private final SysGeneratorService sysGeneratorService;

    public SysGeneratorController(SysGeneratorService sysGeneratorService) {
        this.sysGeneratorService = sysGeneratorService;
    }

    /**
     * 列表
     */
    @ResponseBody
    @GetMapping("/list")
    public Page list(@RequestParam(required = false, defaultValue = "") String tableName, @RequestParam Integer size, @RequestParam Integer current) {
        Page page = sysGeneratorService.queryList(tableName, size, current);
        return page;
    }

    /**
     * 生成代码
     */
    @GetMapping("/code")
    public void code(String tables, String moduleName, String packageName, String author, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split(","), moduleName, packageName, author);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generator-code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 查询一条记录信息
     *
     * @param tableName
     * @return
     */
    @ResponseBody
    @GetMapping("/table/val")
    public Map queryColumnsMap(String tableName) {
        return sysGeneratorService.queryTableColumnDefaultValue(tableName);
    }


    /**
     * description 表字段对应查询条件
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 下午12:30
     */
    @GetMapping("/table/conditions")
    public String tableQueryConditions(String tableName) {
        return sysGeneratorService.tableQueryConditions(tableName);
    }

}
