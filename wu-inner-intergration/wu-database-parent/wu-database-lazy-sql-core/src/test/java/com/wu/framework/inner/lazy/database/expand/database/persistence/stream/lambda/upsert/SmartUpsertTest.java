package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.upsert;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

public class SmartUpsertTest {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setUsername("username");
        dataBaseUser.setAddress("地址");
        dataBaseUser.setAge(18);
        // 没有表会自动创建表
        /**
         * -- ——————————————————————————
         * -- create table user  用户信息表
         * -- add by  wujiawei  2022-11-12
         * -- ——————————————————————————
         * CREATE TABLE IF NOT EXISTS `user` (
         * address_id  int(11)      COMMENT 'null'
         * ,age  int(11)      COMMENT ''
         * ,age_type  int(11)      COMMENT ''
         * ,birthday  varchar(255)      COMMENT ''
         * ,id  int(11)   not null   AUTO_INCREMENT COMMENT ''
         * ,sex  varchar(255)      COMMENT ''
         * ,username  varchar(255)      COMMENT ''
         * ,PRIMARY KEY (id) USING BTREE
         *  , UNIQUE KEY `a_a_u` (`age`,`age_type`,`username`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';
         * -- ------end
         * -- ——————————————————————————
         */
        // 同 执行SQL: insert into user (id,username,age) VALUES (null,'username',18)  ON DUPLICATE KEY UPDATE
        // id=values (id),username=values (username),age=values (age)
        lazyLambdaStream.upsert(dataBaseUser);

    }
}
