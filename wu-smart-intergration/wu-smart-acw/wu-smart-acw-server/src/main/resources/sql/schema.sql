-- ——————————————————————————
-- create table access_token  令牌信息  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `access_token` ( 
authentication blob     COMMENT ''
,authentication_id varchar(128)     COMMENT ''
,client_id varchar(256)     COMMENT ''
,refresh_token varchar(256)     COMMENT ''
,token blob     COMMENT ''
,token_id varchar(256)  not null    COMMENT ''
,user_name varchar(256)     COMMENT ''
,UNIQUE KEY `t` (token_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='令牌信息';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_annotation_code  ACW 使用的代码注解  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_annotation_code` ( 
class_name  varchar(255)   not null  DEFAULT '哈哈哈'   COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,value  varchar(255)      COMMENT 'null'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACW 使用的代码注解';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_instance  数据库服务器  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_instance` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,driver_class_name  varchar(255)      COMMENT '链接驱动'
,host  varchar(255)      COMMENT 'getHost'
,id  varchar(255)      COMMENT ''
,initialize_to_local  tinyint(1)      COMMENT '初始化数据库到本地'
,instance_name  varchar(255)      COMMENT ''
,is_deleted tinyint(1)     COMMENT '是否删除'
,lazy_data_source_type  varchar(255)      COMMENT 'null'
,password  varchar(255)      COMMENT '密码'
,port  int(11)      COMMENT '端口'
,sort  int(11)    DEFAULT 0   COMMENT '排序'
,status  int(11)      COMMENT '状态'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,url  varchar(255)      COMMENT '链接地址'
,username  varchar(255)      COMMENT '用户名'
,UNIQUE KEY `i` (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库服务器';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_application_api_param  api参数  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_application_api_param` ( 
api_id  bigint      COMMENT 'apiId'
,column_name  varchar(255)      COMMENT '数据库字段ID'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT 'api参数主键'
,is_deleted  tinyint(1)      COMMENT '是否删除'
,name  varchar(255)      COMMENT '参数名称'
,term  varchar(255)      COMMENT '条件'
,type  varchar(255)      COMMENT '类型 路径参数、请求参数、请求体参数'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `a_c_t` (api_id,column_name,term)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='api参数';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_table_class  表和class的关联表  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_table_class` ( 
`schema`  varchar(255)      COMMENT ''
,class_id  bigint      COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,instance_id  varchar(255)      COMMENT 'null'
,is_deleted  tinyint(1)      COMMENT '是否删除'
,project_id  bigint      COMMENT ''
,table_name  varchar(255)      COMMENT ''
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `c_p_t` (class_id,project_id,table_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='表和class的关联表';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_application_api_table  api与表的关联关系  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_application_api_table` ( 
api_id  bigint      COMMENT 'apiId'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT '主键'
,is_deleted  tinyint(1)      COMMENT '是否删除'
,table_name  varchar(255)      COMMENT '表ID'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `a_t` (api_id,table_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='api与表的关联关系';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_dependency  依赖  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_dependency` ( 
artifact_id  varchar(255)      COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,group_id  varchar(255)      COMMENT ''
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,optional  tinyint(1)      COMMENT 'null'
,scope  varchar(255)      COMMENT 'null'
,type  varchar(255)      COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,version  varchar(255)      COMMENT ''
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `a_g_v` (artifact_id,group_id,version)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='依赖';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_table_auto_stuffed_record  数据库表填充记录  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_table_auto_stuffed_record` ( 
auto_stuffed_num  bigint      COMMENT '自动填充数量'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,database_schema_id  bigint      COMMENT '数据库schema ID'
,id  varchar(255)      COMMENT '自动填充数据记录ID'
,instance_id  varchar(255)      COMMENT '数据库实例ID'
,instance_name  varchar(255)      COMMENT '数据库实例名称'
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,schema_name  varchar(255)      COMMENT '数据库schema名称'
,status  tinyint(1)      COMMENT '自动填充状态'
,table_name  varchar(255)      COMMENT '自动填充表'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,UNIQUE KEY `i` (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库表填充记录';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_method  ACW 方法  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_method` ( 
body  varchar(255)      COMMENT 'null'
,class_id  bigint      COMMENT 'null'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,in_params  varchar(255)      COMMENT 'null'
,name  varchar(255)      COMMENT 'null'
,out_params  varchar(255)      COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACW 方法';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_application_api  应用API  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_application_api` ( 
application_id  bigint      COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,description  varchar(255)      COMMENT '接口描述'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,method  varchar(255)      COMMENT ''
,path  varchar(255)      COMMENT ''
,tag  varchar(255)      COMMENT ''
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `a_m_p_t` (application_id,method,path,tag)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='应用API';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_line_code    
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_line_code` ( 
code  varchar(255)      COMMENT 'null'
,line  bigint      COMMENT 'null'
,version  double      COMMENT 'null'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_instance_back_ups  数据库实例备份信息  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_instance_back_ups` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  varchar(255)      COMMENT 'null'
,instance_id  varchar(255)      COMMENT '实例ID'
,is_deleted tinyint(1)     COMMENT '是否删除'
,path  varchar(255)      COMMENT '备份文件地址'
,schema_num  int(11)      COMMENT '完成数据库数量'
,status  int(11)      COMMENT '状态：进行中、完成、失败'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库实例备份信息';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_application  应用  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_application` ( 
application_id  bigint   not null   AUTO_INCREMENT COMMENT ''
,application_name  varchar(255)      COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,project_id  bigint      COMMENT ''
,schema_name  varchar(255)      COMMENT ''
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (application_id) USING BTREE
,UNIQUE KEY `s` (schema_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='应用';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_class  对应的class字节码  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_class` ( 
annotation_list  json      COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,included_classes  json      COMMENT 'null'
,interface_class  varchar(255)      COMMENT 'null'
,java_class_type  varchar(255)      COMMENT 'null'
,name  varchar(255)      COMMENT ''
,package_name  varchar(255)      COMMENT ''
,parent_class  varchar(255)      COMMENT 'null'
,project_id  bigint      COMMENT ''
,type  varchar(255)      COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `n_p_p` (name,package_name,project_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='对应的class字节码';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_field  字段对象  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_field` ( 
class_id  bigint      COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,name  varchar(255)      COMMENT ''
,type text     COMMENT ''
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `c_n` (class_id,name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字段对象';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_table_column  数据库表字段（即将弃用）  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_table_column` ( 
character_maximum_length  bigint      COMMENT 'null'
,character_octet_length  varchar(255)      COMMENT 'null'
,character_set_name  varchar(255)      COMMENT 'null'
,collation_name  varchar(255)      COMMENT 'null'
,column_comment text     COMMENT ''
,column_default  varchar(255)      COMMENT 'null'
,column_key  varchar(255)      COMMENT 'null'
,column_name varchar(100)     COMMENT '字段名'
,column_type text     COMMENT ''
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,data_type  varchar(255)      COMMENT 'null'
,datetime_precision  bigint      COMMENT 'null'
,extra  varchar(255)      COMMENT 'null'
,generation_expression text     COMMENT ''
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,is_nullable  varchar(255)      COMMENT 'null'
,numeric_precision  varchar(255)      COMMENT 'null'
,numeric_scale  varchar(255)      COMMENT 'null'
,ordinal_position  bigint      COMMENT 'null'
,privileges  varchar(255)      COMMENT 'null'
,schema_name varchar(50)     COMMENT '数据库'
,srs_id  varchar(255)      COMMENT 'null'
,table_catalog varchar(20)     COMMENT 'catalog'
,table_id  bigint      COMMENT '表ID'
,table_name varchar(100)     COMMENT '表名'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `c_s_t_t` (column_name,schema_name,table_catalog,table_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库表字段（即将弃用）';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_schema_back_ups  数据库备份信息  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_schema_back_ups` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  varchar(255)      COMMENT 'null'
,instance_id  varchar(255)      COMMENT '实例ID'
,is_deleted tinyint(1)     COMMENT '是否删除'
,path  varchar(255)      COMMENT '备份文件地址'
,schema_name  varchar(255)      COMMENT '数据库名称'
,status  int(11)      COMMENT '状态：进行中、完成、失败'
,table_num  int(11)      COMMENT '数据库实例中对应的表数量'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库备份信息';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_schema  ACW 数据库库信息  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_schema` ( 
character_set  varchar(255)      COMMENT 'null'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,ext  varchar(255)      COMMENT 'null'
,id  bigint   not null   AUTO_INCREMENT COMMENT '主键'
,initialize_to_local  tinyint(1)      COMMENT '初始化数据库到本地'
,instance_id  varchar(255)   not null    COMMENT '数据库实例ID'
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,schema_name  varchar(255)      COMMENT '数据库名称'
,sorting_rules  varchar(255)      COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `i_s` (instance_id,schema_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACW 数据库库信息';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_application_api_down_link_method  ACW API 下联 Method 表  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_application_api_down_link_method` ( 
api_id  bigint      COMMENT 'null'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,method_id  bigint      COMMENT 'null'
,project_id  bigint      COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACW API 下联 Method 表';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_project  ACW项目  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_project` ( 
acw_dependency_uo_list  json      COMMENT 'null'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,instance_id  varchar(255)      COMMENT 'null'
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,orm_frame_enums  varchar(255)      COMMENT 'null'
,owner  varchar(255)      COMMENT ''
,project_name  varchar(255)      COMMENT ''
,ui_frame_enums  varchar(255)      COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,version  varchar(255)      COMMENT ''
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `o_p_v` (owner,project_name,version)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACW项目';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_table  数据库表信息（即将弃用）  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_table` ( 
auto_increment  varchar(255)      COMMENT 'null'
,avg_row_length  varchar(255)      COMMENT 'null'
,check_time  varchar(255)      COMMENT 'null'
,checksum  varchar(255)      COMMENT 'null'
,create_options  varchar(255)      COMMENT 'null'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,data_free  varchar(255)      COMMENT 'null'
,data_length  varchar(255)      COMMENT 'null'
,engine  varchar(255)      COMMENT 'null'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,index_length  varchar(255)      COMMENT 'null'
,initialize_to_local  tinyint(1)      COMMENT '初始化数据库到本地'
,instance_id  varchar(255)      COMMENT '数据库实例ID'
,instance_name  varchar(255)      COMMENT '数据库实例ID'
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,max_data_length  varchar(255)      COMMENT 'null'
,row_format  varchar(255)      COMMENT 'null'
,schema_name  varchar(255)      COMMENT ''
,schema_name_id  bigint      COMMENT ''
,table_catalog  varchar(255)      COMMENT 'null'
,table_collation  varchar(255)      COMMENT 'null'
,table_comment  varchar(255)      COMMENT 'null'
,table_name  varchar(255)      COMMENT ''
,table_rows  bigint      COMMENT 'null'
,table_type  varchar(255)      COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,version  varchar(255)      COMMENT 'null'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `i_s_t` (instance_id,schema_name_id,table_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库表信息（即将弃用）';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_code  ACW 行 code  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_code` ( 
acw_line_code_list  json      COMMENT 'null'
,file_type  varchar(255)      COMMENT 'null'
,name  varchar(255)      COMMENT 'null'
,package_name  varchar(255)      COMMENT 'null'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACW 行 code';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table instance_back_ups  数据库备份信息  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `instance_back_ups` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间'
,id varchar(255)     COMMENT 'null'
,instance_id varchar(255)     COMMENT '实例ID'
,is_deleted tinyint(1)     COMMENT '是否删除'
,path varchar(255)     COMMENT '备份文件地址'
,schema_num int     COMMENT '完成数据库数量'
,status int     COMMENT '状态：进行中、完成、失败'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库备份信息';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table schema_back_ups  数据库备份信息  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `schema_back_ups` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间'
,id varchar(255)     COMMENT 'null'
,instance_id varchar(255)     COMMENT '实例ID'
,is_deleted tinyint(1)     COMMENT '是否删除'
,path varchar(255)     COMMENT '备份文件地址'
,schema_name varchar(255)     COMMENT '数据库名称'
,status int     COMMENT '状态：进行中、完成、失败'
,table_num int     COMMENT '数据库实例中对应的表数量'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库备份信息';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_client_java_path  客户端使用创建Java代码常用路径  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_client_java_path` ( 
absolute_path varchar(255)  not null    COMMENT '使用的路径'
,client_id varchar(50)     COMMENT '客户端ID'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间'
,id  varchar(255)   not null    COMMENT '主键'
,instance_id varchar(50)     COMMENT '实例ID'
,package_name varchar(255)     COMMENT '常用的包名称'
,schema_name varchar(50)     COMMENT '常用的数据库'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `a_c_i_p_s` (absolute_path,client_id,instance_id,package_name,schema_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端使用创建Java代码常用路径';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_table  数据库表信息（即将弃用）  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_table` ( 
auto_increment varchar(255)     COMMENT 'null'
,avg_row_length varchar(255)     COMMENT 'null'
,check_time varchar(255)     COMMENT 'null'
,checksum varchar(255)     COMMENT 'null'
,create_options varchar(255)     COMMENT 'null'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,data_free varchar(255)     COMMENT 'null'
,data_length varchar(255)     COMMENT 'null'
,engine varchar(255)     COMMENT 'null'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,index_length varchar(255)     COMMENT 'null'
,initialize_to_local tinyint(1)     COMMENT '初始化数据库到本地'
,instance_id varchar(255)     COMMENT '数据库实例ID'
,instance_name varchar(255)     COMMENT '数据库实例ID'
,is_deleted tinyint(1)   DEFAULT '0'   COMMENT '是否删除'
,max_data_length varchar(255)     COMMENT 'null'
,row_format varchar(255)     COMMENT 'null'
,schema_name varchar(255)     COMMENT ''
,schema_name_id bigint     COMMENT ''
,table_catalog varchar(255)     COMMENT 'null'
,table_collation varchar(255)     COMMENT 'null'
,table_comment varchar(255)     COMMENT 'null'
,table_name varchar(255)     COMMENT ''
,table_rows varchar(255)     COMMENT 'null'
,table_type varchar(255)     COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,version varchar(255)     COMMENT 'null'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `i_s_t` (instance_id,schema_name_id,table_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库表信息（即将弃用）';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_table_auto_stuffed_record_d_o  数据库表填充记录  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_table_auto_stuffed_record_d_o` ( 
auto_stuffed_num  bigint      COMMENT '自动填充数量'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  varchar(255)      COMMENT '主键'
,initialize_to_local  tinyint(1)      COMMENT '初始化数据库到本地'
,instance_id  varchar(255)      COMMENT '数据库实例ID'
,is_deleted  tinyint(1)    DEFAULT '0'   COMMENT '是否删除'
,schema_id  bigint      COMMENT '数据库schema ID'
,schema_name  varchar(255)      COMMENT '数据库schema名称'
,table_name  varchar(255)      COMMENT '自动填充表'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `i` (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库表填充记录';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_client_instance  客户端实例  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_client_instance` ( 
client_id  varchar(255)      COMMENT '客户端ID 当前客户端自己的ID如果为空默认是ip'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,ip  varchar(255)      COMMENT '客户端IP'
,path  varchar(255)      COMMENT '客户端路径'
,port  varchar(255)      COMMENT '客户端端口'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `c` (client_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户端实例';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_instance  数据库服务器  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_instance` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,driver_class_name varchar(255)     COMMENT '链接驱动'
,getHost varchar(255)     COMMENT 'getHost'
,getPort int     COMMENT '端口'
,id varchar(255)     COMMENT ''
,initialize_to_local tinyint(1)     COMMENT '初始化数据库到本地'
,instance_name varchar(255)     COMMENT ''
,is_deleted tinyint(1)     COMMENT '是否删除'
,password varchar(255)     COMMENT '密码'
,sort int   DEFAULT '0'   COMMENT '排序'
,status int     COMMENT '状态'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,url varchar(255)     COMMENT '链接地址'
,username varchar(255)     COMMENT '用户名'
,UNIQUE KEY `i` (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据库服务器';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_schema  ACW 数据库库信息  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_schema` ( 
character_set varchar(255)     COMMENT 'null'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,database_schema_name varchar(255)     COMMENT '数据库名称'
,ext varchar(255)     COMMENT 'null'
,id  bigint   not null   AUTO_INCREMENT COMMENT '主键'
,initialize_to_local tinyint(1)     COMMENT '初始化数据库到本地'
,instance_id varchar(255)  not null    COMMENT '数据库实例ID'
,is_deleted tinyint(1)   DEFAULT '0'   COMMENT '是否删除'
,schema_name varchar(255)     COMMENT '数据库名称'
,sorting_rules varchar(255)     COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `i_s` (instance_id,schema_name),UNIQUE KEY `d_i` (database_schema_name,instance_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ACW 数据库库信息';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_redis_instance  Redis服务器  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_redis_instance` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,getHost varchar(255)     COMMENT 'getHost'
,getPort int     COMMENT '端口'
,id  int(11)   not null   AUTO_INCREMENT COMMENT ''
,instance_name varchar(255)     COMMENT ''
,is_deleted tinyint(1)     COMMENT '是否删除'
,password varchar(255)     COMMENT '密码'
,status int     COMMENT '状态'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,username varchar(255)     COMMENT '用户名'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Redis服务器';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table acw_table_association_relation  表关联关系  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `acw_table_association_relation` ( 
`schema` varchar(14)     COMMENT '数据库实例schema'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id varchar(255)     COMMENT ''
,instance_id varchar(50)     COMMENT '数据库实例ID'
,is_deleted tinyint(1)     COMMENT '是否删除'
,relation double     COMMENT '关系，最大是100'
,relation_table varchar(50)     COMMENT '关系表'
,relation_table_column varchar(50)     COMMENT '关系表字段'
,source_table varchar(50)     COMMENT '原始表'
,source_table_column varchar(50)     COMMENT '原始表字段'
,type int     COMMENT '类型(1 自动产生的、2 手动添加的)'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `s_i_r_r_s_s_t` (`schema`,instance_id,relation_table,relation_table_column,source_table,source_table_column,type),UNIQUE KEY `i` (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='表关联关系';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table interface  接口  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `interface` ( 
application_name varchar(20)     COMMENT '应用名称'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,description varchar(255)     COMMENT '接口描述'
,parent_path json     COMMENT '父路径'
,path varchar(100)     COMMENT '路径'
,request_methods varchar(255)     COMMENT '请求方法'
,tag varchar(100)     COMMENT '接口标签'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='接口';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table menu  菜单  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `menu` ( 
`desc` varchar(255)     COMMENT '菜单描述'
,code  varchar(255)      COMMENT '菜单code'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间'
,icon varchar(255)     COMMENT '菜单icon'
,id  bigint   not null   AUTO_INCREMENT COMMENT '主键'
,iframe int     COMMENT '菜单iframe 0：菜单路由 1：转发'
,is_deleted tinyint(1)     COMMENT '是否删除'
,menu varchar(255)     COMMENT ''
,name varchar(255)     COMMENT '菜单名称'
,parent_code varchar(255)     COMMENT '菜单父编码'
,sort varchar(255)     COMMENT '菜单sort'
,type int     COMMENT '菜单type（0:目录 1：菜单 2：按钮）'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,url varchar(255)     COMMENT '菜单url'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `menu_unique` (code)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table user_role  用户角色关联关系  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `user_role` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT '关联ID'
,is_deleted tinyint(1)     COMMENT '是否删除'
,role_id bigint     COMMENT '角色ID'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,user_id bigint     COMMENT '用户ID'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色关联关系';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table dictionary  字典  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `dictionary` ( 
code varchar(255)     COMMENT '字典编码'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,description varchar(255)     COMMENT '描述'
,id  bigint   not null   AUTO_INCREMENT COMMENT 'id'
,name varchar(255)     COMMENT '字典名称'
,type varchar(255)     COMMENT '类型'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table role  角色  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `role` ( 
code varchar(255)     COMMENT '角色code'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT '主键'
,is_deleted tinyint(1)     COMMENT '是否删除'
,name varchar(255)     COMMENT '角色名称'
,parent_code varchar(255)     COMMENT '角色父编码'
,status tinyint(1)     COMMENT '状态'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table sys_user  用户  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `sys_user` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT '用户ID'
,is_deleted tinyint(1)     COMMENT 'null'
,password varchar(255)     COMMENT '密码'
,scope  varchar(255)      COMMENT 'null'
,status tinyint(1)     COMMENT '状态'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,username varchar(255)     COMMENT '用户名'
,PRIMARY KEY (id) USING BTREE
,UNIQUE KEY `u` (username)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table dictionary_data  字典数据  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `dictionary_data` ( 
code varchar(255)     COMMENT '数据编码'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,description varchar(255)     COMMENT '描述'
,dictionary_code varchar(255)     COMMENT '字典编码'
,id  bigint   not null   AUTO_INCREMENT COMMENT 'id'
,name varchar(255)     COMMENT '数据名称'
,pcode varchar(255)     COMMENT '父节点编码'
,sort_value varchar(255)     COMMENT '排序值'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典数据';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table role_menu  角色菜单  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `role_menu` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  bigint   not null   AUTO_INCREMENT COMMENT '主键'
,is_deleted tinyint(1)     COMMENT '是否删除'
,menu_id bigint     COMMENT '菜单ID'
,role_id bigint     COMMENT '角色ID'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色菜单';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table automation  自动化  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `automation` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id varchar(255)  not null    COMMENT '主键'
,is_deleted tinyint   DEFAULT '0'   COMMENT '是否删除'
,name varchar(255)     COMMENT '名称'
,next_time datetime     COMMENT '下一次执行时间'
,status tinyint     COMMENT '状态：启用停用'
,time_interval int     COMMENT '执行自动化时间间隔（没有则不执行）'
,update_time datetime    on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='自动化';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table automation_node_action_record  自动化记录  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `automation_node_action_record` ( 
automation_node_action_id varchar(255)     COMMENT '自动化节点执行ID'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id varchar(255)  not null    COMMENT '主键'
,is_deleted tinyint   DEFAULT '0'   COMMENT '是否删除'
,result varchar(255)     COMMENT '节点执行结果'
,status tinyint     COMMENT '节点执行状态'
,update_time datetime    on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='自动化记录';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table automation_node_http_action  自动化http节点动作  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `automation_node_http_action` ( 
automation_node_id varchar(255)     COMMENT '自动化节点ID'
,body text     COMMENT '请求体'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,headers json     COMMENT '请求头'
,http_method varchar(255)     COMMENT '请求方法'
,id  varchar(255)   not null    COMMENT '主键'
,is_deleted tinyint   DEFAULT '0'   COMMENT '是否删除'
,params json     COMMENT '请求参数'
,update_time datetime    on update CURRENT_TIMESTAMP COMMENT '更新时间'
,url varchar(255)     COMMENT '请求路径'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='自动化http节点动作';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table automation_node  自动化节点  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `automation_node` ( 
action_type varchar(255)     COMMENT '动作类型：http 、other'
,automation_id varchar(255)     COMMENT '自动化ID'
,create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,id  varchar(255)   not null    COMMENT '主键'
,is_deleted tinyint   DEFAULT '0'   COMMENT '是否删除'
,name varchar(255)     COMMENT '名称'
,sort  int(11)      COMMENT '排序'
,status tinyint     COMMENT '状态：启用停用'
,update_time datetime    on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='自动化节点';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table doc_pdf_merge    
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `doc_pdf_merge` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,is_deleted tinyint(1)   DEFAULT '0'   COMMENT '是否删除'
,merge_url varchar(255)     COMMENT 'null'
,pdf_url_list varchar(255)     COMMENT 'null'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table dynamic_iframe  动态Iframe  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `dynamic_iframe` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,dynamic_parameter json     COMMENT '动态参数'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,is_deleted tinyint(1)   DEFAULT '0'   COMMENT '是否删除'
,is_station tinyint(1)   DEFAULT '0'   COMMENT '是否站内'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,url varchar(255)     COMMENT 'iframe地址'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='动态Iframe';
-- ------end 
-- ——————————————————————————

-- ——————————————————————————
-- create table dynamic_sql_window  动态SQL窗口  
-- add by  wujiawei  2024-02-22  
-- ——————————————————————————
CREATE TABLE IF NOT EXISTS `dynamic_sql_window` ( 
create_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '创建时间'
,dynamic_sql  varchar(255)      COMMENT '动态sql'
,id  bigint   not null   AUTO_INCREMENT COMMENT ''
,instance_id  varchar(255)      COMMENT '实例ID'
,is_deleted tinyint(1)   DEFAULT '0'   COMMENT '是否删除'
,is_station tinyint(1)   DEFAULT '0'   COMMENT '是否站内'
,update_time datetime   DEFAULT CURRENT_TIMESTAMP   on update CURRENT_TIMESTAMP COMMENT '更新时间'
,PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='动态SQL窗口';
-- ------end 
-- ——————————————————————————

