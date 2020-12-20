package com.wu.framework.inner.database.expand.database.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

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


    public String getResultType() {
        if (ObjectUtils.isEmpty(resultClass)) {
            return resultType;
        }
        return resultClass.getName();
    }
}
