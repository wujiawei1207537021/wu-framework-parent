package com.wu.framework.inner.lazy.persistence.converter;

import com.wu.framework.inner.layer.util.JsonUtils;

import java.util.Map;

class EntityConverterTest {

    public static void main(String[] args) {
        String sql = "INSERT INTO `` (`Table`, `Non_unique`, `Key_name`, `Seq_in_index`, `Column_name`, `Collation`, `Cardinality`, `Sub_part`, `Packed`, `Null`, `Index_type`, `Comment`, `Index_comment`, `Visible`, `Expression`) VALUES ('application', 0, 'PRIMARY', 1, 'application_id', 'A', 2, NULL, NULL, '', 'BTREE', '', '', 'YES', NULL);";
        String entity = EntityConverter.createEntity(sql);
        System.out.println(entity);
        String demoSql = "INSERT INTO `upms`.`tenant_user` (`create_time`, `create_user`, `email`, `full_name`, `id`, `is_delete`, `login_name`, `mobile`, `password`, `position`, `status`, `update_time`, `update_user`, `user_name`, `verifyemail`, `verifyphone`, `work_num`) VALUES ('2022-09-14 15:13:19', 5476, NULL, '你好呀', 5635, 0, '15705596111', '15705596111', '$2a$10$VhUSaSkfOA4r4mDI.lsNJeeVErY/uC9HYJtVBsUS964vgnzB7Mffq', NULL, 0, '2022-09-14 15:13:19', 5476, '你好呀', 0, 0, NULL);";
        Map<String, Object> json = EntityConverter.createJson(demoSql);
        System.out.println(JsonUtils.toJsonString(json));
    }
}