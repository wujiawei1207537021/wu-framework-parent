package com.wu.framework.inner.lazy.database.expand.database.persistence.converter;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.SQLAnalyze;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/4/18 11:21 上午
 */
public class SQLConverter {
    public final static SQLAnalyze sqlAnalyze = new SQLAnalyze() {
    };


    public static String createSelectSQL(Class clazz) {
        return sqlAnalyze.analyze(clazz);
    }
}
