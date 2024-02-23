package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.script;

import org.springframework.core.io.Resource;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/18 19:08
 */
public interface SimpleScriptRunnerLambdaStream {

    /**
     * 执行sql 文件
     *
     * @param resources sql 文件
     * @return
     */
    Object scriptRunner(Resource... resources);


    /**
     * 执行sql 脚本
     *
     * @param scripts sql 脚本
     * @return
     */
    Object stringScriptRunner(String... scripts);


}
