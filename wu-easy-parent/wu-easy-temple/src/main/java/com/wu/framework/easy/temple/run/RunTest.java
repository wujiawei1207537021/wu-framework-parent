package com.wu.framework.easy.temple.run;


import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:32
 */
@EasyController
public class RunTest {

    private final RunService runService;

    public RunTest(RunService runService) {
        this.runService = runService;
    }


    @EasyExcel(fileName = "导出数据")
    @GetMapping("/run/{size}")
    public List<UserLog> run(@PathVariable Integer size) {
        return runService.run(size);
    }

    @EasyExcel(fileName = "非原生注解导出数据", fieldColumnAnnotation = EasyTableField.class, multipleSheet = true, limit = 10)
    @GetMapping("/run1")
    public List<UserLog> run1() {
        return runService.run1();
    }

    @GetMapping("/run2/{size}")
    public void run2(@PathVariable Integer size) {
        runService.run(size);
    }

    @GetMapping("/run3")
    public void run3() {
        runService.run1();
    }
}
