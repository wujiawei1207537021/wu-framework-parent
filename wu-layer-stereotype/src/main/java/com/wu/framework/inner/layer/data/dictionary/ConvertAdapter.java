package com.wu.framework.inner.layer.data.dictionary;

import com.wu.framework.inner.layer.data.dictionary.convert.LazyDictionaryConvert;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;


/**
 * description 字典适配器
 *
 * @author Jia wei Wu
 * @date 2020/8/17 下午7:48
 * @see LazyDictionaryConvert
 */
@Deprecated
public interface ConvertAdapter extends InitializingBean {

    /**
     * 适配低版本数据转换
     *
     * @param objects
     */
    default void convertObjects(Object... objects) {
        convertObjects(null, objects);
    }


    /**
     * @param dictionaryDataMap 字典数据
     * @param objects           转换对象
     */
    void convertObjects(Map<String, Map<String, String>> dictionaryDataMap, Object... objects);

    /**
     * 字段转译
     *
     * @param object
     */
    default void transformation(Object object) {
        convertObjects(null, object);
    }

}
