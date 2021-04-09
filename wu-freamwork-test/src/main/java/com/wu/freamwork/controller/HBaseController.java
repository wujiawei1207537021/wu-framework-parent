package com.wu.freamwork.controller;

import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import com.wu.framework.easy.stereotype.upsert.converter.stereotype.A_map_weather;
import com.wu.framework.easy.stereotype.upsert.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.hbase.expland.bo.HBaseUserBo;
import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * description HBase测试操作
 *
 * @author 吴佳伟
 * @date 2021/4/8 下午4:11
 */
@Api(tags = "HBase测试操作")
@EasyController("/HBase")
public class HBaseController implements CommandLineRunner {

    private final HBaseOperation hBaseOperation;

    private final LazyOperation lazyOperation;
    private final IUpsert iUpsert;

    public HBaseController(HBaseOperation hBaseOperation, LazyOperation lazyOperation, IUpsert iUpsert) {
        this.hBaseOperation = hBaseOperation;
        this.lazyOperation = lazyOperation;
        this.iUpsert = iUpsert;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

//        Page<A_map_weather> page = new Page<A_map_weather>(1, 1000);
//        do {
//            lazyOperation.page(page, A_map_weather.class, "SELECT * from a_map_weather");
//            List<A_map_weather> record = (List<A_map_weather>) page.getRecord();
//
//            System.out.println("当前查询页数:" + page.getCurrent());
//            hBaseOperation.insertList(record);
//            page.setCurrent(page.getCurrent() + 1);
//        } while (page.getRecord() != null && page.getRecord().size() == page.getSize());


    }

    @QuickEasyUpsert(type = EasyUpsertType.HBASE)
    @ApiOperation(tags = "HBase测试操作",value = "保存数据到HBase")
    @GetMapping("/save/HBase")
    public List<HBaseUserBo>  saveHBase(@RequestParam(defaultValue = "100") Integer size){
        List<HBaseUserBo> hBaseUserBoList = new ArrayList<>();
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            hBaseUserBoList.add(new HBaseUserBo().setUserName("hbase_user").setAge("12").setSex("男").setId(i));
        }
        return hBaseUserBoList;
    }
}
