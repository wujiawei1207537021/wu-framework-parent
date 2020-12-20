package com.wu.framework.easy.stereotype.upsert.process;

/**
 * description ActiveMQ 数据预处理
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午2:25
 */
public class ActiveMQDataProcess implements DataProcess {


    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:20
     */
    @Override
    public ProcessResult classAnalyze(Class clazz) {
        return null;
    }

    /**
     * description 数据包装
     *
     * @param sourceData 源数据
     * @return ProcessResult
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:23
     */
    @Override
    public ProcessResult dataPack(Object sourceData) {
        return null;
    }
}
