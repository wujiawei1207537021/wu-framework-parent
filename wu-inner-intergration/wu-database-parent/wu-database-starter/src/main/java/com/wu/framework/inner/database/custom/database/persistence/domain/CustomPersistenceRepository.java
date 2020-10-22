package com.wu.framework.inner.database.custom.database.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * @ProjectName: mybatisDesign
 * @Package: com.paic.mybatis.cfg
 * @ClassName: Mapper
 * @Author: Administrator
 * @Description: ${description}
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPersistenceRepository {
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
