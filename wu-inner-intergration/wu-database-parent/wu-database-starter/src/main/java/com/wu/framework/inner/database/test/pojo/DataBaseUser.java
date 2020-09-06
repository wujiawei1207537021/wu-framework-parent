package com.wu.framework.inner.database.test.pojo;

import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomId;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTable;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomTableFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@CustomTable(value = "user",comment = "用户信息表")
@AllArgsConstructor
@NoArgsConstructor
public class DataBaseUser implements Serializable {
//
    @CustomId(value = "id")
    private int id;

//    @CustomTableFile(value = "username",comment = "username")
    @CustomId(value = "username")
    private String username;

    @CustomTableFile(value = "birthday")
    private String birthday;

    @CustomTableFile( "sex")
    private String sex;


    @CustomTableFile(value = "address",exist = false)
    private String address;

    @CustomId(value = "age")
    private int age;
}
