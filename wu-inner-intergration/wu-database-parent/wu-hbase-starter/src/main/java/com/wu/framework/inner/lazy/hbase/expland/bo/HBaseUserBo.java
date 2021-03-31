package com.wu.framework.inner.lazy.hbase.expland.bo;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasyUnique;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/3/27 9:41 下午
 */
@Accessors(chain = true)
@Data
@EasySmart(tableName = "hbase_user",hBaseRow = "hbaseRow",columnFamily = "A3")
public class HBaseUserBo {

    @EasyUnique
    private int id;
    private String userName;
    private String age;
    private String sex;
    private LocalDate birthday=LocalDate.now();
}
