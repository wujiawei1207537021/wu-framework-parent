package com.wu.freamwork.controller;

import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.freamwork.ElasticsearchSQLTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.util.List;

/**
 * description Es SQL 查询模板
 *
 * @author 吴佳伟
 * @date 2021/1/27 下午3:14
 */
@EasyController
public class ElasticsearchController {


    @PostConstruct
    public  void comeOn() throws Exception{
        ElasticsearchSQLTemplate elasticsearchSQLTemplate = ElasticsearchSQLTemplate.build(new RestTemplateBuilder().build(), "http://81.69.3.45:30820", -1L);
        String sql = "select * from  \"sys_veh_dyn_gps_es_2021.01.27_d\" ";
        List<EasyHashMap> scroll = elasticsearchSQLTemplate.scroll(sql, EasyHashMap.class);

        //
        BufferedWriter file = FileUtil.createFile("/Users/wujiawei/IdeaProjects/wu-framework-parent", "2021.01.04.txt");
        for (EasyHashMap easyHashMap : scroll) {
            file.newLine();
            file.write(easyHashMap.toString());
        }
        file.close();
    }


}
