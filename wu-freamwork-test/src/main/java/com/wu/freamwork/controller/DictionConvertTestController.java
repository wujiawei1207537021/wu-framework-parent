package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.data.dictionary.NormalConvertMapper;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.freamwork.domain.DictionConvertUserTest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * description 字典转换测试
 *
 * @author 吴佳伟
 * @date 2023/08/30 12:35
 */
@Tag(name = "字典转换测试")
@EasyController("/diction/convert")
public class DictionConvertTestController {


    /**
     * 返回list集合
     *
     * @return
     */
    @NormalConvertMapper
    @GetMapping("/findList")
    public List<DictionConvertUserTest> findList() {
        List<DictionConvertUserTest> dictionConvertUserTests = DataTransformUntil.simulationBeanList(DictionConvertUserTest.class, 10);
        for (int i = 0; i < dictionConvertUserTests.size(); i++) {
            DictionConvertUserTest dictionConvertUserTest = dictionConvertUserTests.get(i);
            dictionConvertUserTest.setSex(i % 3);
        }
        return dictionConvertUserTests;
    }

    /**
     * 返回分页
     *
     * @return
     */
    @NormalConvertMapper
    @GetMapping("/findPage")
    public LazyPage<DictionConvertUserTest> findPage() {
        LazyPage<DictionConvertUserTest> dictionConvertUserTestLazyPage = new LazyPage<>();
        dictionConvertUserTestLazyPage.setRecord(findList());
        return dictionConvertUserTestLazyPage;
    }

    /**
     * 返回result  list
     *
     * @return
     */
    @NormalConvertMapper
    @GetMapping("/findListResult")
    public Result<List<DictionConvertUserTest>> findListResult() {
        return ResultFactory.successOf(findList());
    }

    /**
     * 返回result page
     *
     * @return
     */
    @NormalConvertMapper
    @GetMapping("/findPageResult")
    public Result<LazyPage<DictionConvertUserTest>> findPageResult() {
        return ResultFactory.successOf(findPage());
    }

}
