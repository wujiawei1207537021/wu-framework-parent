package com.wu.framework.inner.database.test.pojo;

import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.CustomId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EasyTable(value = "user",comment = "用户信息表")
@AllArgsConstructor
@NoArgsConstructor
public class DataBaseUser implements Serializable {
//
    @CustomId(value = "id")
    private int id;

//    @EasyTableFile(value = "username",comment = "username")
    @CustomId(value = "username")
    private String username;

    @EasyTableFile(value = "birthday")
    private String birthday;

    @EasyTableFile( "sex")
    private String sex;


    @EasyTableFile(value = "address",exist = false)
    private String address;

    @CustomId(value = "age")
    private int age;
}
