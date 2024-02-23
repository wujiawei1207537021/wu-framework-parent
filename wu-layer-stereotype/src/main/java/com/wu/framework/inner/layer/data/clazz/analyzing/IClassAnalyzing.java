package com.wu.framework.inner.layer.data.clazz.analyzing;

import com.wu.framework.inner.layer.data.clazz.LazyClass;

/**
 * description class 解析抽象类
 *
 * @author 吴佳伟
 * @date 2023/10/10 15:58
 */
public interface IClassAnalyzing {

    /**
     * 是否支持当前文本转换
     *
     * @param classText class 内容文本
     * @return
     */
    boolean support(String classText);

    /**
     * @param clazz class
     * @return 解析class文件获取的class
     */
    LazyClass getLazyClass(Class<?> clazz);
}
