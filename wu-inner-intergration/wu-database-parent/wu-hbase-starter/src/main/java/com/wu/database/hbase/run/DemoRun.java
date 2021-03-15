package com.wu.database.hbase.run;

import com.wu.database.hbase.HBaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/15 8:25 下午
 */
@Controller
public class DemoRun {

    @Autowired
    private HBaseUtils hBaseUtils;


    @PostConstruct
    public void run() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String str = hBaseUtils.scanAllRecord("student");//扫描表
            System.out.println("获取到hbase的内容：" + str);
            map.put("hbaseContent", str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}