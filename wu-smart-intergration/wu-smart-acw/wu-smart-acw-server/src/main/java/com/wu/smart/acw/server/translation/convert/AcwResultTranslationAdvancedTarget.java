package com.wu.smart.acw.server.translation.convert;

import com.wu.framework.inner.layer.data.translation.advanced.TranslationAdvancedTarget;
import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

/**
 * description acw 结果转换适配器适配者目标
 *
 * @author 吴佳伟
 * @date 2023/09/25 15:20
 */
@Component
public class AcwResultTranslationAdvancedTarget extends TranslationAdvancedTarget {
    public AcwResultTranslationAdvancedTarget(List<TranslationAPI> translationAPIList) {
        super(translationAPIList);
    }

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    @Override
    public boolean support(Object source) {
        if (Result.class.isAssignableFrom(source.getClass())) {
            Result result = (Result) source;
            final Object resultData = result.getData();
            if (ObjectUtils.isEmpty(resultData)) {
                return false;
            } else {
                if (LazyPage.class.isAssignableFrom(resultData.getClass())) {
                    return true;
                } else {
                    return true;
                }
            }
        } else if (Collection.class.isAssignableFrom(source.getClass())) {
            return true;
        } else if (LazyPage.class.isAssignableFrom(source.getClass())) {
            return true;
        }
        return false;
    }

    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    @Override
    public void transformation(Object source) {
        if (Result.class.isAssignableFrom(source.getClass())) {
            Result result = (Result) source;
            final Object resultData = result.getData();
            if (ObjectUtils.isEmpty(resultData)) {
            } else {
                if (LazyPage.class.isAssignableFrom(resultData.getClass())) {
                    LazyPage page = (LazyPage) resultData;
                    super.transformation(page.getRecord());
                } else {
                    super.transformation(resultData);
                }
            }
        } else if (Collection.class.isAssignableFrom(source.getClass())) {
            super.transformation(source);
        } else if (LazyPage.class.isAssignableFrom(source.getClass())) {
            LazyPage page = (LazyPage) source;
            super.transformation(page.getRecord());
        }
    }
}
