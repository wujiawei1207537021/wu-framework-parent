package com.wu.framework.inner.database;


import com.wu.framework.inner.database.config.mapper.DatabaseMapperConfiguration;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : ddlAuto 操作
 * @date : 2020/9/13 下午9:20
 */
public class DDLAutoOperate {
    private final DatabaseMapperConfiguration databaseMapperConfiguration;


    public DDLAutoOperate(DatabaseMapperConfiguration databaseMapperConfiguration) {
        this.databaseMapperConfiguration = databaseMapperConfiguration;
    }
}
