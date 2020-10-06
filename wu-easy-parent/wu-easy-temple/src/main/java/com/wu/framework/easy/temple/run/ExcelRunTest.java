package com.wu.framework.easy.temple.run;


import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:32
 */
@EasyController("/excel")
public class ExcelRunTest {

    private final RunService runService;

    public ExcelRunTest(RunService runService) {
        this.runService = runService;
    }


    @EasyExcel(fileName = "导出数据")
    @GetMapping("/run/{size}")
    public List<UserLog> run(@PathVariable Integer size) {
        return runService.run(size);
    }



    @EasyExcel(fileName = "非原生注解导出数据", filedColumnAnnotation = EasyTableFile.class,multipleSheet = true, limit = 10)
    @GetMapping("/run2/{size}")
    public List<UserLog> run2(@PathVariable Integer size) {
        return runService.run(size);
    }

}
