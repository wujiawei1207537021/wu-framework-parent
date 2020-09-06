package com.wu.freamwork.controller.ws;

import com.wu.framework.inner.swagger.annotation.CustomController;
import com.wu.freamwork.controller.ws.enums.ConstructionMarketEnums;
import com.wu.freamwork.domain.LineCardInformationTable;
import com.wu.freamwork.domain.ResultPo;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2020/7/14 下午3:12
 */
@CustomController("/DSS/ws")
public class TestController {

    @GetMapping()
    public ResultPo getResult(String key) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class clazz = null;
        for (ConstructionMarketEnums constructionMarketEnums : ConstructionMarketEnums.values()) {
            if (constructionMarketEnums.getKey().equals(key)) {
                clazz = constructionMarketEnums.getClazz();
            }
        }
        if (ObjectUtils.isEmpty(clazz)) {
            return getDefault();
        } else {
            return run1(clazz);
        }

    }

    public <T> ResultPo run1(Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        ResultPo resultPo = new ResultPo();
        List<T> list = new ArrayList<>();
        resultPo.setData(list);
        resultPo.setCode(200);
        for (int i = 0; i < 10; i++) {
            T t = clazz.newInstance();
            Field field = clazz.getDeclaredField("ID");
            field.setAccessible(true);
            field.set(t, String.valueOf(i));
            list.add(t);
        }
        resultPo.setData(list);
        resultPo.setInfo("请求信息");
        return resultPo;
    }

    public ResultPo getDefault() {
        ResultPo resultPo = new ResultPo();
        return resultPo;
    }

}
