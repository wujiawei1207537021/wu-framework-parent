package com.wu.framework.inner.lazy.database.test.pojo;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.CustomId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EasySmart(value = "user", comment = "用户信息表")
@AllArgsConstructor
@NoArgsConstructor
public class DataBaseUser implements Serializable {
    //
    @CustomId(value = "id")
    private int id;

    //    @EasySmartField(value = "username",comment = "username")
    @CustomId(value = "username")
    private String username;

    @EasySmartField(value = "birthday")
    private String birthday;

    @EasySmartField("sex")
    private String sex;


    @EasySmartField(value = "address", exist = false)
    private String address;

    @CustomId(value = "age")
    private int age;
}
