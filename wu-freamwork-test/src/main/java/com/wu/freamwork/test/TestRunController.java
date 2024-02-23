package com.wu.freamwork.test;

import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@EasyController("/test")
public class TestRunController {
    private final List<TestRunner> testRunnerList;

    public TestRunController(List<TestRunner> testRunnerList) {
        this.testRunnerList = testRunnerList;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @GetMapping("/run")
    public void run()  {
        for (TestRunner testRunner : testRunnerList) {
            try {
                testRunner.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
