package com.wu.freamwork.domain;


import com.wu.framework.inner.lazy.stereotype.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@LazyTable(tableName = "lazy_user_test", comment = "用户信息表")
@AllArgsConstructor
@NoArgsConstructor
public class LazyUserTest implements Serializable {
    //
    @LazyTableFieldId(value = "id", idType = LazyTableFieldId.IdType.INPUT_ID)
    private Long id;

    @LazyTableFieldUnique(value = "username")
    private String username;

    @LazyTableField(value = "birthday")
    private LocalDateTime birthday;

    @LazyTableField("sex")
    private Sex sex;


    @LazyTableFieldUnique(value = "age")
    private Integer age;

    @LazyTableField(updateStrategy = LazyFieldStrategy.IGNORED_NULL)
    private Boolean isDeleted;



    @Getter
    public enum Sex{
        MAN,
        WOMAN;
    }
}

