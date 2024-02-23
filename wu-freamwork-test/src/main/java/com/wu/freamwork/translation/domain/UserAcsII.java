package com.wu.freamwork.translation.domain;

import com.wu.framework.inner.layer.data.translation.TranslationField2AcsII;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserAcsII {


    private Integer userId;

    @TranslationField2AcsII(translationSourceName = "userId")
    @Schema(hidden = true)
    private Long userIdAcsII;


    private String userName;

    @TranslationField2AcsII(translationSourceName = "userName")
    @Schema(hidden = true)
    private Long userNameAcsII;

    private Long age;

}
