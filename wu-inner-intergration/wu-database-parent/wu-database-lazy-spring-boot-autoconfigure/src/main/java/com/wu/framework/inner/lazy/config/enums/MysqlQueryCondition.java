package com.wu.framework.inner.lazy.config.enums;

import com.wu.framework.inner.layer.data.NormalUsedString;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MysqlQueryCondition {
    EQ(NormalUsedString.EQUALS),
    GT(NormalUsedString.RIGHT_CHEV),
    LT(NormalUsedString.LEFT_CHEV),
    LIKE(NormalUsedString.LIKE),
    IN(NormalUsedString.IN),
    NE(NormalUsedString.EXCLAMATION_MARK + NormalUsedString.EQUALS),
    ;
    private String condition;

}
