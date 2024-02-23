package com.wu.framework.inner.lazy.database.domain;


import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
@LazyTable(tableName = "user", comment = "用户信息详情表")
@AllArgsConstructor
@NoArgsConstructor
public class DataBaseUserInfo implements Serializable {
    //
    @LazyTableFieldId(value = "id", idType = LazyTableFieldId.IdType.AUTOMATIC_ID)
    private Integer id;

    //    @EasySmartField(methodName = "username",comment = "username")
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

    @LazyTableField(comment = "角色ID")
    private Integer roleId;

}
