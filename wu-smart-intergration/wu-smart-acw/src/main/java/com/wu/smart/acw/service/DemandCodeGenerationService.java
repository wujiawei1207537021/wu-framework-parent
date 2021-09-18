package com.wu.smart.acw.service;

import com.wu.framework.response.Result;
import com.wu.smart.acw.domain.TableConfiguration;

public interface DemandCodeGenerationService {

    /**
     * 生成表
     *
     * @param table
     * @return
     */
    Result generationTable(TableConfiguration table);

    /**
     * 生成
     * @param table
     * @return
     */
    Result generation(TableConfiguration table);
}
