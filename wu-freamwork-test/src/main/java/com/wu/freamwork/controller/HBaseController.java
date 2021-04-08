package com.wu.freamwork.controller;

import com.wu.framework.easy.stereotype.upsert.converter.stereotype.A_map_weather;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/4/8 下午4:11
 */
@EasyController("/HBase")
public class HBaseController implements CommandLineRunner {

    private final HBaseOperation hBaseOperation;

    private final LazyOperation lazyOperation;

    public HBaseController(HBaseOperation hBaseOperation, LazyOperation lazyOperation) {
        this.hBaseOperation = hBaseOperation;
        this.lazyOperation = lazyOperation;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        Page<A_map_weather> page = new Page<A_map_weather>(1, 1000);
        do {
            lazyOperation.page(page, A_map_weather.class, "SELECT * from a_map_weather");
            List<A_map_weather> record = (List<A_map_weather>) page.getRecord();

            System.out.println("当前查询页数:" + page.getCurrent());
            hBaseOperation.insertList(record);
            page.setCurrent(page.getCurrent() + 1);
        } while (page.getRecord() != null && page.getRecord().size() == page.getSize());


    }
}
