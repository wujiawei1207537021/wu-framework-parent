package com.wu.freamwork.domain;

import com.wu.framework.inner.layer.data.dictionary.ConvertField;
import com.wu.framework.inner.layer.data.dictionary.ConvertFieldBean;
import lombok.Data;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/08/30 12:36
 */
@Data
public class DictionConvertUserTest {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户性别
     */
    @ConvertField(convertItem = "SEX")
    private Integer sex;

    /**
     * 用户性别转换
     */
    private String sexName;

    /**
     * 用户角色
     */
    @ConvertFieldBean
    private DictionConvertRoleTest dictionConvertRoleTest;

    /**
     * 用户角色
     */
    @Data
    public static final class DictionConvertRoleTest {
        /**
         * 角色名
         */
        private String roleName;
        @ConvertField(convertItem = "ROLE_TYPE")
        /**
         * 角色类型
         */
        private Integer roleType;
        private String roleTypeName;
    }
}



