package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface LambdaTable<T, R> {


    /**
     * @param
     * @return
     * @describe 主表
     * @author Jia wei Wu
     * @date 2021/8/8 12:27 下午
     **/
    LambdaSplicing<T, R> table(Class primaryTable);

    @Getter
    @AllArgsConstructor
    public enum LambdaTableType {
        SELECT("select", "查询");
        private String value;
        private String msg;
    }
}
