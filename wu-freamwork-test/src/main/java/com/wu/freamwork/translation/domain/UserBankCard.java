package com.wu.freamwork.translation.domain;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.freamwork.translation.api.BankCardNoTranslation2NameAPI;
import lombok.Data;

/**
 * description 用户银行卡
 *
 * @author 吴佳伟
 * @date 2023/10/16 11:00
 */
@Data
public class UserBankCard {
    // 用户ID
    private Long userId;

    // 银行
    @TranslationField(translationSourceName = "bankCardNo", translationAPI = BankCardNoTranslation2NameAPI.class)
    private String bankName;

    // 银行卡号
    private String bankCardNo;

}
