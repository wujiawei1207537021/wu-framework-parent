//package com.wu.freamwork.controller;
//
//import com.wu.framework.easy.upsert.autoconfigure.dynamic.QuickEasyUpsert;
//import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
//import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
//import com.wu.framework.inner.layer.web.EasyController;
//import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
//import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
//import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
//import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
//import com.wu.framework.inner.lazy.hbase.expland.bo.HBaseUserBo;
//import com.wu.framework.inner.lazy.hbase.expland.persistence.HBaseOperation;
//import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
//import com.wu.freamwork.domain.AMapWeather;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * description HBase测试操作
// *
// * @author Jia wei Wu
// * @date 2021/4/8 下午4:11
// */
//@Tag(name = "HBase测试操作")
//@EasyController("/public/HBase")
//public class HBaseController implements CommandLineRunner {
//
//    private final HBaseOperation hBaseOperation;
//
//    private final LazySqlOperation lazySqlOperation;
//    private final IUpsert iUpsert;
//    private final PerfectLazyOperation perfectLazyOperation;
//    private final LazyLambdaStream lazyLambdaStream;
//
//    public HBaseController(HBaseOperation hBaseOperation, LazySqlOperation lazySqlOperation, IUpsert iUpsert, PerfectLazyOperation perfectLazyOperation, LazyLambdaStream lazyLambdaStream) {
//        this.hBaseOperation = hBaseOperation;
//        this.lazySqlOperation = lazySqlOperation;
//        this.iUpsert = iUpsert;
//        this.perfectLazyOperation = perfectLazyOperation;
//        this.lazyLambdaStream = lazyLambdaStream;
//    }
//
//    /**
//     * Callback used to run the bean.
//     *
//     * @param args incoming main method arguments
//     * @throws Exception on error
//     */
//    @Override
//    public void run(String... args) throws Exception {
//
////        mysql2HBase();
//
//    }
//
//    public void mysql2HBase() throws Exception {
//        lazyLambdaStream.scroll(null, LazyWrappers.<AMapWeather>lambdaWrapper(), EasyHashMap.class, page -> hBaseOperation.insert(page.getRecord()));
//    }
//
//    @QuickEasyUpsert(type = EasyUpsertType.HBASE)
//    @ApiOperation(tags = "HBase测试操作", value = "保存数据到HBase")
//    @GetMapping("/save/HBase")
//    public List<HBaseUserBo> saveHBase(@RequestParam(defaultValue = "100") Integer size) {
//        List<HBaseUserBo> hBaseUserBoList = new ArrayList<>();
//        long a = System.currentTimeMillis();
//        for (int i = 0; i < size; i++) {
//            hBaseUserBoList.add(new HBaseUserBo().setUserName("hbase_user").setAge("12").setSex("男").setId(i));
//        }
//        return hBaseUserBoList;
//    }
//}
