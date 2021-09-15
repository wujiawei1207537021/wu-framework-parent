package com.wu.framework.inner.sys.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * 处理 通过api 获取指定枚举字典值 并提共map 集合  自动转换功能
 */

public abstract class ConvertAdapterAbstract implements ConvertAdapter {

    Logger log = LoggerFactory.getLogger(ConvertAdapterAbstract.class);

    /**
     * @return
     * @description 初始化配置
     * @params
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/17 3:54 下午
     */

    abstract void init(Object... objects);

    /**
     * @return
     * @description 模糊转换
     * @params
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/17 3:40 下午
     */
    @Override
    public void convertObjects(Object... objects) {
        if (ObjectUtils.isEmpty(objects)) {
            return;
        }
        init(objects);
        for (Object object : objects) {
            if (ObjectUtils.isEmpty(object)) {
                continue;
            }
            if (object instanceof Collection) {
                convertCollection((Collection) object);
            } else {
                convert(object);
            }
        }
    }

    protected void convertCollection(Collection collection) {
        for (Object o : collection) {
            if (o instanceof Collection) {
                convertCollection((Collection) o);
            } else {
                convert(o);
            }
        }
    }


    /**
     * 单个对象转换需要配置枚举
     *
     * @param object
     */
    abstract void convert(Object object);


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
