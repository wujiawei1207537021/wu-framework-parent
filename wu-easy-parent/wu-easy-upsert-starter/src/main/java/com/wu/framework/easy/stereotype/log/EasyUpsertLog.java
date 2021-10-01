package com.wu.framework.easy.stereotype.log;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.SQLAnalyze;
import lombok.Data;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/1 3:38 下午
 */
@Data
public class EasyUpsertLog {

    /**
     * 日志类型
     */
    private String type;
    /**
     * 日志内容
     */
    private String context;
    /**
     * 作者
     */
    private String author = SQLAnalyze.AUTHOR;

}
