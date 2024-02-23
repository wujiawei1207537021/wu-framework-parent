
use acw_simple11;

-- ----------------------------
-- LazyTableInfo data for acw_simple11.interface_in_param   第【 0 ~ 1 】条数据
-- LazyTableInfo table_comment for null
-- ----------------------------
insert into acw_simple11.interface_in_param (`condition`,api_id,create_time,default_value,default_value_type,id,is_deleted,name,update_time) VALUES ('string',3,'2023-08-15 08:58:38','string',0,'string',null,'string','2023-08-15 17:40:11')  ON DUPLICATE KEY UPDATE
    `condition`=values (`condition`),api_id=values (api_id),create_time=values (create_time),default_value=values (default_value),default_value_type=values (default_value_type),id=values (id),is_deleted=values (is_deleted),name=values (name),update_time=values (update_time);

-- ----------------------------
-- LazyTableInfo data for acw_simple11.interface_info   第【 0 ~ 1 】条数据
-- LazyTableInfo table_comment for null
-- ----------------------------
insert into acw_simple11.interface_info (api_comment,api_id,api_method,api_path,api_result_type,api_status,api_type,create_time,execute_type,is_deleted,update_time) VALUES ('测试接口',3,'GET','/interface/run/demo',0,0,'SELECT','2023-08-15 17:34:44','SELECT',false,'2023-08-15 17:39:20')  ON DUPLICATE KEY UPDATE
    api_comment=values (api_comment),api_id=values (api_id),api_method=values (api_method),api_path=values (api_path),api_result_type=values (api_result_type),api_status=values (api_status),api_type=values (api_type),create_time=values (create_time),execute_type=values (execute_type),is_deleted=values (is_deleted),update_time=values (update_time);

-- ----------------------------
-- LazyTableInfo data for acw_simple11.interface_out_param   第【 0 ~ 2 】条数据
-- LazyTableInfo table_comment for null
-- ----------------------------
insert into acw_simple11.interface_out_param (api_id,create_time,id,is_deleted,mapping_name,name,update_time) VALUES (3,'2023-08-15 08:58:38','string',false,'apiMethod1','api_method','2023-08-15 18:49:44'),(3,'2023-08-15 18:41:57',null,false,'apiPath1','api_path','2023-08-15 18:49:45')  ON DUPLICATE KEY UPDATE
    api_id=values (api_id),create_time=values (create_time),id=values (id),is_deleted=values (is_deleted),mapping_name=values (mapping_name),name=values (name),update_time=values (update_time);

-- ----------------------------
-- LazyTableInfo data for acw_simple11.interface_table   第【 0 ~ 1 】条数据
-- LazyTableInfo table_comment for null
-- ----------------------------
insert into acw_simple11.interface_table (api_id,create_time,id,is_deleted,is_main_table,table_name,update_time) VALUES (3,'2023-08-15 08:58:38',1,false,true,'interface_info','2023-08-15 18:36:45')  ON DUPLICATE KEY UPDATE
    api_id=values (api_id),create_time=values (create_time),id=values (id),is_deleted=values (is_deleted),is_main_table=values (is_main_table),table_name=values (table_name),update_time=values (update_time);

-- ----------------------------
-- LazyTableInfo data for acw_simple11.sys_user   第【 0 ~ 1 】条数据
-- LazyTableInfo table_comment for null
-- ----------------------------
insert into acw_simple11.sys_user (column_name,password,scope,username,id,is_deleted,create_time,update_time,permission_list,role_sign_list,status) VALUES (null,'$2a$10$.XKWPdtVzCOT67l.TbCRL.eGeH0E3EHgI2xREYJkGTjgT.2.ylEca','web','admin','66',false,'2023-08-11 08:29:42','2023-08-11 08:29:42',null,'["4251356456"]',1)  ON DUPLICATE KEY UPDATE
    column_name=values (column_name),password=values (password),scope=values (scope),username=values (username),id=values (id),is_deleted=values (is_deleted),create_time=values (create_time),update_time=values (update_time),permission_list=values (permission_list),role_sign_list=values (role_sign_list),status=values (status);
