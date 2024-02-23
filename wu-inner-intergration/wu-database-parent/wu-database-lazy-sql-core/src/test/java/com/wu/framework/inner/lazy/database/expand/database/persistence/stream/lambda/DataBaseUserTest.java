package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;


import com.wu.framework.inner.lazy.stereotype.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
@LazyTable(tableName = "user", comment = "用户信息表")
@AllArgsConstructor
@NoArgsConstructor
public class DataBaseUserTest implements Serializable {
    //
    @LazyTableFieldId(value = "id", idType = LazyTableFieldId.IdType.AUTOMATIC_ID)
    private Integer id;

    //    @EasySmartField(methodName = "username",comment = "username")
    @LazyTableFieldUnique(value = "username", upsertStrategy = LazyFieldStrategy.NEVER)
    private String username;

    @LazyTableField(value = "birthday")
    private String birthday;

    @LazyTableField("sex")
    private String sex;

    @LazyTableField(value = "address", exist = false)
    private String address;

    @LazyTableFieldUnique(value = "age")
    private Integer age;

    @LazyTableFieldUnique()
    private Integer ageType;


    private Integer addressId;
}
