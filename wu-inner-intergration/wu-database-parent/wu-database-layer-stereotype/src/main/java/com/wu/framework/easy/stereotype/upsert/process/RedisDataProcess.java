package com.wu.framework.easy.stereotype.upsert.process;

import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LazyTableAnnotation;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;

/**
 * description Redis 数据预处理
 *
 * @author Jia wei Wu
 * @date 2020/10/22 下午2:25
 */
public class RedisDataProcess {


    /**
     * description 类解析
     *
     * @param clazz
     * @return Object
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/22 下午2:20
     */
    public LazyTableAnnotation classAnalyze(Class clazz) {
        return LocalStorageClassAnnotation.getEasyTableAnnotation(clazz, true);
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
    public DataProcess.ProcessResult dataPack(Object sourceData) {
        return null;
    }
}
