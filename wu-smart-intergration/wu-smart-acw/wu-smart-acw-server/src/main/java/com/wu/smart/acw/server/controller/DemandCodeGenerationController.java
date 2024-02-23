package com.wu.smart.acw.server.controller;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.TableConfigurationQo;
import com.wu.smart.acw.server.service.DemandCodeGenerationService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Api(tags = "需求代码生成")
@EasyController("/demand/generation")
public class DemandCodeGenerationController {

    private final DemandCodeGenerationService demandCodeGenerationService;

    public DemandCodeGenerationController(DemandCodeGenerationService demandCodeGenerationService) {
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
