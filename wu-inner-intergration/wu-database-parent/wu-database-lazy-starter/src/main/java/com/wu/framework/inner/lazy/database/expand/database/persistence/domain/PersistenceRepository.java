package com.wu.framework.inner.lazy.database.expand.database.persistence.domain;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 预执行SQL需要的属性
 * @date : 2020/11/21 下午8:49
 */
@Data
public class PersistenceRepository {
    //sql
    @NonNull
    private String queryString;
    @Deprecated
    //实体类的全限定类名
    private String resultType;
    //实体类
    @NonNull
    private Class resultClass;
    private boolean printfQuery;
    //执行类型
    private LambdaTableType executionType = LambdaTableType.SELECT;

    PersistenceRepository(boolean printfQuery) {
        this.printfQuery = printfQuery;
    }


    public String getResultType() {
        if (ObjectUtils.isEmpty(resultClass)) {
            return resultType;
        }
        return resultClass.getName();
    }

    @NonNull
    public String getQueryString() {
        if (printfQuery) {
            System.err.printf("执行SQL: %s%n", queryString);
        }
        return queryString;
    }
}
