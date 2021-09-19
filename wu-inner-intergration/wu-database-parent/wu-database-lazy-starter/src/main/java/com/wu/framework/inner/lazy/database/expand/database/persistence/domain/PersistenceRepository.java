package com.wu.framework.inner.lazy.database.expand.database.persistence.domain;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 预执行SQL需要的属性
 * @date : 2020/11/21 下午8:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersistenceRepository {
    @NonNull
    private String queryString;//sql
    private String resultType;//实体类的全限定类名
    private Class resultClass;//实体类

    private LambdaTable.LambdaTableType executionType= LambdaTable.LambdaTableType.SELECT;//执行类型

    /**
     * 二进制数据
     */
    private List<InputStream> binaryList;


    public String getResultType() {
        if (ObjectUtils.isEmpty(resultClass)) {
            return resultType;
        }
        return resultClass.getName();
    }

    @NonNull
    public String getQueryString() {
        System.err.printf("执行SQL: %s%n", queryString);
        return queryString;
    }
}
