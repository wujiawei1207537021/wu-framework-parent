package com.wu.smart.acw.server.application;

import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.dto.AcwInstanceTypeDTO;

import java.util.List;

public interface DatabaseInstanceTypeApplication {
    /**
     * 查询出数据库实例类型
     *
     * @return
     */
    Result<List<AcwInstanceTypeDTO>> retrieveAll();
}
