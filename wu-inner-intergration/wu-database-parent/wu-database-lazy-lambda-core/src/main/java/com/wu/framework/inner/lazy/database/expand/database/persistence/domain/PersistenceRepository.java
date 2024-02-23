package com.wu.framework.inner.lazy.database.expand.database.persistence.domain;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Logger log = LoggerFactory.getLogger(PersistenceRepository.class);
    //sql
    @NonNull
    private String queryString;
    @Deprecated
    //实体类的全限定类名
    private String resultType;
    //实体类
    @NonNull
    private Class<?> resultClass;
    private boolean printfQuery;
    //执行类型
    private LambdaTableType executionType = LambdaTableType.SELECT;

    /**
     * 原始参数
     */
    private SqlPart sqlPart;

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

        // 解析where 条件
        // 针对 select 、update、delete 适配行级数据


        if (printfQuery) {
            log.info("执行SQL: \n 执行类型: =====> {}  \n 执行sql: =====> {}", executionType, queryString);
        } else {
            log.debug("执行SQL: \n 执行类型: =====> {}  \n 执行sql: =====> {}", executionType, queryString);
        }
        return queryString;
    }

    /**
     * 执行sql之前获取 sql
     *
     * @return sql
     */
    public String getBeforeQueryString() {
        return queryString;
    }

    @Override
    public String toString() {
        return "PersistenceRepository{[" + executionType + "]:[" + queryString + "]}";
    }
}
