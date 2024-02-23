package com.wu.framework.inner.lazy.database.domain;

import com.wu.framework.inner.lazy.database.stereotype.CustomRepositoryXmlScan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @ProjectName: mybatisDesign
 * @Package: com.paic.mybatis.cfg
 * @ClassName: Mapper
 * @Author: Administrator
 * description: ${description}
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomRepository {
    private String queryString;//sql
    private String resultType;//实体类的全限定类名
    private Class resultClass;//实体类
    private CustomRepositoryXmlScan.ExecuteType executeType = CustomRepositoryXmlScan.ExecuteType.SELECT;//sql执行的类型

    /**
     * if 标签
     */
    private List<IF> ifList;


    public String getResultType() {
        if (ObjectUtils.isEmpty(resultClass)) {
            return resultType;
        }
        return resultClass.getName();
    }

    @Data
    public static class IF {
        /**
         * if 判断
         */
        private String ifAttr;
        /**
         * 判断true展示的内容
         */
        private String ifContext;
    }
}
