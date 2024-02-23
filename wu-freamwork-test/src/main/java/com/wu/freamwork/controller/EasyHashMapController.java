package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * description EasyHashMap 测试接口
 *
 * @author Jia wei Wu
 * @date 2022/08/12 4:02 下午
 */
@Tag(name = "EasyHashMap 测试接口")
@EasyController("/easy")
public class EasyHashMapController {


    @GetMapping("/hash")
    public Result map() {
        EasyHashMap<Object, Object> easyHashMap = new EasyHashMap<>();
        easyHashMap.put("id", 12L);

        return ResultFactory.successOf(easyHashMap);
    }
}
