package com.wu.smart.acw.server.application;

import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.qo.TableConfigurationQo;

public interface DemandCodeGenerationApplication {

    /**
     * 生成表
     *
     * @param table
     * @return
     */
    Result generationTable(TableConfigurationQo table);

    /**
     * 生成
     *
     * @param table
     * @return
     */
    Result generation(TableConfigurationQo table);
}
