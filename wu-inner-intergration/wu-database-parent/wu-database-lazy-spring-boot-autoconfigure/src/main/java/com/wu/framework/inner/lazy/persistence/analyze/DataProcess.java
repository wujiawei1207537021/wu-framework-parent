package com.wu.framework.inner.lazy.persistence.analyze;

/**
 * description 预处理、数据处理、转换
 * <p>
 * {@link com.wu.framework.inner.layer.stereotype.Layer}
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午2:10
 */
@Deprecated
public interface DataProcess {

    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:20
     */
    Object classAnalyze(Class clazz);

    /**
     * description 数据包装
     *
     * @param sourceData 源数据
     * @return ProcessResult
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:23
     */
    Object dataPack(Object sourceData);


    /**
     * description 预处理结果
     *
     * @author Jia wei Wu
     * @date 2020/10/22 下午3:06
     */
    interface ProcessResult {

    }

}
