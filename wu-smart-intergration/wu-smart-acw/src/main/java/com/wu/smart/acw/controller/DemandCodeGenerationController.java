package com.wu.smart.acw.controller;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.smart.acw.domain.TableConfiguration;
import com.wu.smart.acw.service.DemandCodeGenerationService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Api(tags = "需求代码生成")
@EasyController("/demand")
public class DemandCodeGenerationController {

    private final DemandCodeGenerationService demandCodeGenerationService;

    public DemandCodeGenerationController(DemandCodeGenerationService demandCodeGenerationService) {
        this.demandCodeGenerationService = demandCodeGenerationService;
    }

    @PostMapping("/generationTable")
    public Result generationTable(@ModelAttribute TableConfiguration table) {
        return demandCodeGenerationService.generationTable(table);
    }


    /**
     *  生成
     * @param table
     * @return
     */
    @PostMapping("/generation")
    public Result generation(@ModelAttribute TableConfiguration table){
        return demandCodeGenerationService.generation(table);
    }


}
