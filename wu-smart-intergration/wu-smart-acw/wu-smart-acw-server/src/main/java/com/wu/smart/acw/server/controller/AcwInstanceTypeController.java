package com.wu.smart.acw.server.controller;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.DatabaseInstanceTypeApplication;
import com.wu.smart.acw.server.application.dto.AcwInstanceTypeDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Jia wei Wu
 */
@Tag(name = "ACW-数据库实例类型")
@EasyController("/database/instance/type")
public class AcwInstanceTypeController {

    private final DatabaseInstanceTypeApplication databaseInstanceTypeApplication;

    public AcwInstanceTypeController(DatabaseInstanceTypeApplication databaseInstanceTypeApplication) {
        this.databaseInstanceTypeApplication = databaseInstanceTypeApplication;
    }

    /**
     * describe  查询出数据库实例类型
     *
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出数据库实例类型")
    @GetMapping("/retrieve")
    Result<List<AcwInstanceTypeDTO>> retrieveAll() {
        return databaseInstanceTypeApplication.retrieveAll();
    }


}
