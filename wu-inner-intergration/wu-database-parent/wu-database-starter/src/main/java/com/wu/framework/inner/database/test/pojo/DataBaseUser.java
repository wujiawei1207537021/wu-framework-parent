package com.wu.framework.inner.database.test.pojo;

import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EasyTable(value = "user", comment = "用户信息表")
@AllArgsConstructor
@NoArgsConstructor
public class DataBaseUser implements Serializable {
    //
    @CustomId(value = "id")
    private int id;

    //    @EasyTableField(value = "username",comment = "username")
    @CustomId(value = "username")
    private String username;

    @EasyTableField(value = "birthday")
    private String birthday;

    @EasyTableField("sex")
    private String sex;


    @EasyTableField(value = "address", exist = false)
    private String address;

    @CustomId(value = "age")
    private int age;
}
