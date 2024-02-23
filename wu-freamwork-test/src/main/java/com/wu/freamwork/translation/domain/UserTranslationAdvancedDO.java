package com.wu.freamwork.translation.domain;

import com.wu.framework.inner.layer.data.translation.TranslationBean;
import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import com.wu.freamwork.translation.api.AddressTranslationAPI;
import com.wu.freamwork.translation.api.UserRoleTranslationAPI;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * description 用户信息转换测试表
 *
 * @author 吴佳伟
 * @date 2023/09/25 15:21
 */
@LazyTable(tableName = "user_translation_advanced", comment = "用户信息转换测试表")
@Data
public class UserTranslationAdvancedDO {

    @LazyTableFieldId(value = "id", idType = LazyTableFieldId.IdType.AUTOMATIC_ID)
    private Integer id;

    @LazyTableFieldUnique(value = "username")
    private String username;

    @LazyTableField(value = "birthday")
    private String birthday;

    @LazyTableField("sex")
    private String sex;

    @LazyTableField(value = "address", exist = true)
    private String address;

    @LazyTableFieldUnique(value = "age")
    private Integer age;

    @LazyTableFieldUnique()
    private Integer ageType;


    @LazyTableField(comment = "地址ID")
    private Integer addressId;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 地址名称
     */
    @LazyTableField(exist = false)
    @TranslationField(translationAPI = AddressTranslationAPI.class, translationSourceName = "addressId")
    private String addressName;

    @LazyTableField(comment = "角色ID")
    private Integer roleId;

    /**
     * 用户角色信息
     */
    @LazyTableField(exist = false)
    @TranslationField(translationAPI = UserRoleTranslationAPI.class, translationSourceName = "roleId")
    private UserRoleTranslationAdvancedDO userRoleTranslationAdvancedDO;

    /**
     * zone
     */
    @TranslationBean
    @LazyTableField(exist = false)
    private UserZoneTranslationAdvancedDO userZoneTranslationAdvancedDO;

    /**
     * 用户银行卡信息
     */
    @TranslationBean
    @LazyTableField(exist = false)
    private List<UserBankCard> userBankCardList;

}
