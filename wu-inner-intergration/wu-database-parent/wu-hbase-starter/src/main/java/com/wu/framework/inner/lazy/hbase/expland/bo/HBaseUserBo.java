package com.wu.framework.inner.lazy.hbase.expland.bo;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/27 9:41 下午
 */
@Accessors(chain = true)
@Data
@EasySmart(tableName = "hbase_user",hBaseRow = "hbaseRow1",columnFamily = "columnFamily")
public class HBaseUserBo {

    private String userName;
    private String age;
    private String sex;
}
