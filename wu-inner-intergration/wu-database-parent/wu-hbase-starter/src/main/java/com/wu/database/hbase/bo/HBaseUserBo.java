package com.wu.database.hbase.bo;

import com.wu.framework.easy.stereotype.upsert.EasySmart;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/27 9:41 下午
 */
@EasySmart(tableName = "")
public class HBaseUserBo {

    private String userName;
    private String age;
    private String sex;
}
