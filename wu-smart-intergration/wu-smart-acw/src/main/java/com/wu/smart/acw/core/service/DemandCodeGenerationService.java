package com.wu.smart.acw.core.service;

import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.TableConfigurationQo;

public interface DemandCodeGenerationService {

    /**
     * 生成表
     *
     * @param table
     * @return
     */
    Result generationTable(TableConfigurationQo table);

    /**
     * 生成
     * @param table
     * @return
     */
    Result generation(TableConfigurationQo table);
}
