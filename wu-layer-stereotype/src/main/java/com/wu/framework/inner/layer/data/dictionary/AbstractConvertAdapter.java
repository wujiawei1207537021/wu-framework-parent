package com.wu.framework.inner.layer.data.dictionary;

import com.wu.framework.inner.layer.data.dictionary.convert.AbstractLazyDictionaryConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 处理 通过api 获取指定枚举字典值 并提共map 集合  自动转换功能
 *
 * @author Jia wei Wu
 * @see AbstractLazyDictionaryConvert
 */
@Deprecated
public abstract class AbstractConvertAdapter implements ConvertAdapter {

    Logger log = LoggerFactory.getLogger(AbstractConvertAdapter.class);

    /**
     * @return description 初始化配置
     * @params
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/17 3:54 下午
     */
    abstract Map<String, Map<String, String>> init(Map<String, Map<String, String>> dictionaryDataMap, Object... objects);

    /**
     * @return description 模糊转换
     * @params
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/17 3:40 下午
     */
    @Override
    public void convertObjects(Map<String, Map<String, String>> dictionaryDataMap, Object... objects) {
        if (ObjectUtils.isEmpty(objects)) {
            return;
        }
        Map<String, Map<String, String>> initMap = init(dictionaryDataMap, objects);
        for (Object object : objects) {
            if (ObjectUtils.isEmpty(object)) {
                continue;
            }
            if (object instanceof Collection) {
                convertCollection(initMap, (Collection) object);
            } else {
                convert(object, initMap);
            }
        }
    }

    protected void convertCollection(Map<String, Map<String, String>> dictionaryDataMap, Collection collection) {
        for (Object o : collection) {
            if (o instanceof Collection) {
                convertCollection(dictionaryDataMap, (Collection) o);
            } else {
                convert(o, dictionaryDataMap);
            }
        }
    }


    /**
     * 单个对象转换需要配置枚举
     *
     * @param object
     */
    abstract void convert(Object object, Map<String, Map<String, String>> dictionaryDataMap);


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
