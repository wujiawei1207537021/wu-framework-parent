package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.smart.database.Perfect;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazySmartLazyOperation;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/4/11 5:28 下午
 */
@Slf4j
@EasyController("/ss")
public class SaveUpsertSqlFileRunController {

    private final LazySmartLazyOperation lazyOperation;
    private final Perfect perfect;

    public SaveUpsertSqlFileRunController(LazySmartLazyOperation lazyOperation, Perfect perfect) {
        this.lazyOperation = lazyOperation;
        this.perfect = perfect;
    }

    @PostConstruct
    public void run() {

        try {
            lazyOperation.saveUpsertSqlFile("lms_weight");
            lazyOperation.saveUpsertSqlFile("lms_main");
            lazyOperation.saveUpsertSqlFile("lms_bs");

            lazyOperation.saveUpsertSqlFile("etms_2_0_central_user");
            lazyOperation.saveUpsertSqlFile("etms_2_0_central_config");
            lazyOperation.saveUpsertSqlFile("etms_2_0_central_dictionary");

            lazyOperation.saveUpsertSqlFile("temp");
        } catch (Exception exception) {
            exception.printStackTrace();
            log.info("导出数据库数据失败");
        }


    }
}
