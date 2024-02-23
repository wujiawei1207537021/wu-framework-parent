package com.wu.freamwork.translation.domain;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.freamwork.translation.api.TemperatureTranslationAPI;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * description 用户区域转换测试
 *
 * @author 吴佳伟
 * @date 2023/09/26 15:25
 */
@LazyTable(tableName = "user_zone_translation_advanced", comment = "用户区域转换测试")
@Data
public class UserZoneTranslationAdvancedDO {

    @LazyTableField(comment = "区域ID")
    private Long id;

    @LazyTableField(comment = "区域名称")
    private String name;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;


    /**
     * 温度
     */
    @TranslationField(translationSourceName = "id", translationAPI = TemperatureTranslationAPI.class)
    @LazyTableField(exist = false)
    private String temperature;

}
