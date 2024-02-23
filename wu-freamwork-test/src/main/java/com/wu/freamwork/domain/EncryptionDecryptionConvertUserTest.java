package com.wu.freamwork.domain;

import com.wu.framework.inner.layer.data.encryption.EncryptionField;
import com.wu.framework.inner.layer.data.encryption.EncryptionFieldBean;
import lombok.Data;

/**
 * description 用户加解密测试对象
 *
 * @author 吴佳伟
 * @date 2023/08/30 12:36
 */
@Data
public class EncryptionDecryptionConvertUserTest {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户性别
     */

    private Integer sex;

    /**
     * 用户性别转换
     */
    @EncryptionField
    private String sexName;

    /**
     * 用户角色
     */
    @EncryptionFieldBean
    private DictionConvertRoleTest dictionConvertRoleTest;

    /**
     * 用户角色
     */
    @Data
    public static final class DictionConvertRoleTest {
        /**
         * 角色名
         */
        @EncryptionField
        private String roleName;

        /**
         * 角色类型
         */

        private Integer roleType;
        @EncryptionField
        private String roleTypeName;
    }
}



