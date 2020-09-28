package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:32
 */
@EasyController
public class RunTest {

    private final RunService runService;
    private final DataSource dataSource;

    public RunTest(RunService runService, DataSource dataSource) {
        this.runService = runService;
        this.dataSource = dataSource;
    }


    @GetMapping("/run/{size}")
    public List<UserLog> run(@PathVariable Integer size) {
        return runService.run(size);
    }

    @GetMapping("/run1")
    public List<UserLog> run1() {
        return runService.run1();
    }

    @GetMapping("/run2/{size}")
    public List<UserLog> run2(@PathVariable Integer size) {
        runService.run2(size);
        return null;
    }
}
