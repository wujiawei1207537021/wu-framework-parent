package com.wu.framework.easy.stereotype.upsert.process;

/**
 * description 预处理、数据处理、转换
 *
 * @author 吴佳伟
 * @date 2020/10/22 下午2:10
 */
public interface DataProcess {

    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/10/22 下午2:20
     */
    ProcessResult classAnalyze(Class clazz);

    /**
     * description 数据包装
     *
     * @param sourceData 源数据
     * @return ProcessResult
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/10/22 下午2:23
     */
    ProcessResult dataPack(Object sourceData);


    /**
     * description 预处理结果
     *
     * @author 吴佳伟
     * @date 2020/10/22 下午3:06
     */
    interface ProcessResult {

    }

}
