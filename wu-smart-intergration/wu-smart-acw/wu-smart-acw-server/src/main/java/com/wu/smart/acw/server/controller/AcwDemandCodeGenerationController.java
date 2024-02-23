package com.wu.smart.acw.server.controller;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.TableConfigurationQo;
import com.wu.smart.acw.server.application.DemandCodeGenerationApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "需求代码生成")
@EasyController("/demand/generation")
public class AcwDemandCodeGenerationController {

    private final DemandCodeGenerationApplication demandCodeGenerationService;

    public AcwDemandCodeGenerationController(DemandCodeGenerationApplication demandCodeGenerationService) {
        this.demandCodeGenerationService = demandCodeGenerationService;
    }


    @PostMapping("/table")
    public Result generationTable(@ModelAttribute TableConfigurationQo table) {
        return demandCodeGenerationService.generationTable(table);
    }


    /**
     * 生成
     *
     * @param table
     * @return
     */
    @PostMapping()
    public Result generation(@ModelAttribute TableConfigurationQo table) {
        return demandCodeGenerationService.generation(table);
    }


}
