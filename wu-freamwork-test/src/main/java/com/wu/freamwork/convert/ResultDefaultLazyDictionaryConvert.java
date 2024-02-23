package com.wu.freamwork.convert;

import com.wu.framework.inner.layer.data.dictionary.api.ConvertApi;
import com.wu.framework.inner.layer.data.dictionary.convert.DefaultLazyDictionaryConvert;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * description 适配result结果的转换
 *
 * @author 吴佳伟
 * @date 2023/08/30 13:40
 */
@Component
public class ResultDefaultLazyDictionaryConvert extends DefaultLazyDictionaryConvert {
    public ResultDefaultLazyDictionaryConvert(ConvertApi convertApi) {
        super(convertApi);
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
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 字段转译
     *
     * @param source
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
