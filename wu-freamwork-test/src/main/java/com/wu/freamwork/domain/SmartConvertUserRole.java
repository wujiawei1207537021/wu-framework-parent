package com.wu.freamwork.domain;

import com.wu.freamwork.convert.SmartConvertRoleIdField;
import lombok.Data;

/**
 * description 用户角色
 *
 * @author 吴佳伟
 * @date 2023/09/22 12:46
 */
@Data
public class SmartConvertUserRole {

    /**
     * 角色ID
     */

    private Long roleId;
    /**
     * 角色名称
     */
    @SmartConvertRoleIdField(translationSourceName = "roleId", transferDataName = "name")
    private String roleName;

    /**
     * 用户ID
     */
    private Long userId;
}
