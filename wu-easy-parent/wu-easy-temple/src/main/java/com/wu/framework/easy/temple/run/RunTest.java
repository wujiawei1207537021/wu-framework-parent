package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.web.bind.annotation.GetMapping;

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

    public RunTest(RunService runService) {
        this.runService = runService;
    }

    @GetMapping("/run1")
    public List<UserLog> run1() {
        return runService.run1();
    }
}
