/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : custom

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 17/01/2020 10:15:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `sys_config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统配置ID',
  `config_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置类型',
  `config_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置名称',
  `config_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '值',
  `config_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置描述',
  `user_id_create` bigint(20) NULL DEFAULT NULL COMMENT '条目创建人',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '记录创建日期',
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否启用（1启用，0禁用）',
  PRIMARY KEY (`sys_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '    部门id',
  `parent_id` int(10) NULL DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `manager_user_id` int(10) NULL DEFAULT NULL COMMENT '负责人',
  `manager_telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人联系方式',
  `discover_user_id` int(10) NULL DEFAULT NULL COMMENT '检测师用户ID',
  `jurisdiction` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属辖区',
  `dept_unit_type` tinyint(1) NULL DEFAULT NULL COMMENT '单位类型(0监管单位，1被监管单位)',
  `dept_type` tinyint(4) NOT NULL COMMENT '部门类型',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `longitude` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `latitude` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位地址',
  `order_num` tinyint(4) NULL DEFAULT NULL COMMENT '排序',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用，0禁用）',
  `dept_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门备注',
  `credit_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `dept_permit_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经营许可证',
  `dept_zone_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在地区，关联area表',
  `dept_classify` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门分类：农贸市场、农批市场、超市、其他部门分类',
  `market_size` tinyint(1) NULL DEFAULT NULL COMMENT '市场规模0(1-20小型)1(20-40中型)2(40-100大型)',
  `layout_path` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市场布局图地址',
  `tenant_id` int(10) NULL DEFAULT NULL COMMENT '租户ID',
  `market_type` tinyint(1) NULL DEFAULT NULL COMMENT '市场类别(0城市，1乡村)',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建日期',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改日期',
  `manager_user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人姓名',
  `parent_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点类型',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_type`;
CREATE TABLE `sys_dept_type`  (
  `dept_type_id` int(2) NOT NULL COMMENT '部门类型编号',
  `dept_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '部门类型名称',
  `sort` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `dept_type_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dept_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '部门类型字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标签名',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据值',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `sort` decimal(10, 0) NULL DEFAULT NULL COMMENT '排序（升序）',
  `parent_id` bigint(64) NULL DEFAULT 0 COMMENT '父级编号',
  `create_by` int(64) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注信息',
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否启用（1启用，0禁用）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_value`(`value`) USING BTREE,
  INDEX `sys_dict_label`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `type_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类型编号',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类型备注',
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否启用（1启用，0禁用）',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `time` int(11) NULL DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `gmt_create` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '使用状态(0-停用,1-使用)',
  `default_menu_id` int(20) NULL DEFAULT NULL COMMENT '默认菜单ID',
  `data_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色可查询数据类型',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `sex` int(1) NULL DEFAULT 1 COMMENT '0女性，1男性',
  `dept_id` int(20) NULL DEFAULT NULL COMMENT '所属部门',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `telephone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机号码',
  `user_classify` bigint(64) NULL DEFAULT NULL COMMENT '用户分类，1监管2检测3销售4经营户',
  `status` tinyint(255) NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openId',
  `manufacturer_id` int(11) NULL DEFAULT NULL COMMENT '用户所属厂商id',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 205 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_message`;
CREATE TABLE `sys_user_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `message_id` bigint(20) NOT NULL,
  `read_mark` int(2) NULL DEFAULT 0,
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `read_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Procedure structure for create_calendar
-- ----------------------------
DROP PROCEDURE IF EXISTS `create_calendar`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `create_calendar`(s_date DATE, e_date DATE)
BEGIN

	SET @createSql = 'CREATE TABLE IF NOT EXISTS builddate (
                      `date` date NOT NULL,
		       UNIQUE KEY `unique_date` (`date`) USING BTREE
                   )ENGINE=InnoDB DEFAULT CHARSET=utf8';
	prepare stmt from @createSql;
	execute stmt;

	WHILE s_date <= e_date DO
		INSERT IGNORE INTO builddate VALUES (DATE(s_date)) ;
		SET s_date = s_date + INTERVAL 1 DAY ;
	END WHILE ;

END
;;
delimiter ;

-- ----------------------------
-- Function structure for getChildFoodLeastStr
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildFoodLeastStr`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getChildFoodLeastStr`(foodId VARCHAR(10)) RETURNS varchar(4000) CHARSET utf8 COLLATE utf8_bin
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);
SET sTemp = '$';
SET sTempChd = cast(foodId as CHAR);
WHILE sTempChd is not NULL DO
SET sTemp =CONCAT(sTemp,',',sTempChd);
SELECT group_concat(LENGTH(food_id)>=8) INTO sTempChd FROM food where FIND_IN_SET(parent_id,sTempChd)>0;
END WHILE;
return sTemp;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getChildFoodStr
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildFoodStr`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getChildFoodStr`(foodId VARCHAR(10)) RETURNS varchar(4000) CHARSET utf8 COLLATE utf8_bin
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);
SET sTemp = '$';
SET sTempChd = cast(foodId as CHAR);
WHILE sTempChd is not NULL DO
SET sTemp =CONCAT(sTemp,',',sTempChd);
SELECT group_concat(food_id) INTO sTempChd FROM food where FIND_IN_SET(parent_id,sTempChd)>0;
END WHILE;
return sTemp;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getChildList`(rootId VARCHAR ( 100 )) RETURNS varchar(4000) CHARSET utf8
BEGIN
	DECLARE
		str VARCHAR ( 4000 );
	DECLARE
		cid VARCHAR ( 1000 );

	SET str = '$';

	SET cid = rootId;
	WHILE
			cid IS NOT NULL DO

			SET str = concat( str, ',', cid );
		SELECT
			group_concat( dept_id ) INTO cid
		FROM
			sys_dept
		WHERE
			FIND_IN_SET( parent_id, cid ) > 0;

	END WHILE;
	RETURN RIGHT(str,LENGTH(str)-2);

END
;;
delimiter ;

-- ----------------------------
-- Function structure for getChildListByType
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildListByType`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getChildListByType`(rootId VARCHAR ( 100 ),type VARCHAR ( 100 )) RETURNS varchar(4000) CHARSET utf8
BEGIN
	DECLARE
		str VARCHAR ( 4000 );
	DECLARE
		cid VARCHAR ( 1000 );

	SET str = '$';

	SET cid = rootId;
	WHILE
			cid IS NOT NULL DO

			SET str = concat( str, ',', cid );
		SELECT
			group_concat( dept_id ) INTO cid
		FROM
			sys_dept
		WHERE
			FIND_IN_SET( parent_id, cid ) > 0
			AND dept_type = type;

	END WHILE;
	RETURN RIGHT(str,LENGTH(str)-2);

END
;;
delimiter ;

-- ----------------------------
-- Function structure for getDeptChildListByType
-- ----------------------------
DROP FUNCTION IF EXISTS `getDeptChildListByType`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getDeptChildListByType`(deptId INT, deptType INT) RETURNS varchar(2000) CHARSET utf8 COLLATE utf8_bin
BEGIN
DECLARE deptList VARCHAR (1000);
SELECT dept_id into deptList
FROM
	(
		SELECT
			t1.dept_id,
      t1.dept_type,
 		IF (
 			find_in_set(parent_id, @pids) > 0,
 			@pids := concat(@pids, ',', dept_id),
			0
) AS ischild
		FROM
			(
				SELECT
					dept_id,
					parent_id,
					dept_name,
					dept_type
				FROM
					sys_dept t
				ORDER BY
					parent_id,
					dept_id
			) t1,
			(SELECT @pids := deptId) t2
	) t3
WHERE
	t3.dept_type = deptType
 AND ischild != 0;
RETURN deptList;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getFoodProjectAmount
-- ----------------------------
DROP FUNCTION IF EXISTS `getFoodProjectAmount`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getFoodProjectAmount`( foodId VARCHAR ( 20 ), projectId INT ) RETURNS varchar(5) CHARSET utf8
BEGIN
  DECLARE
    t_food_id VARCHAR ( 20 );-- 食品编号
  DECLARE
    t_project_id INT;-- 项目编号
  DECLARE
    t_project_amount VARCHAR ( 5 );-- 项目限量值
  DECLARE
    temp_food_id VARCHAR ( 20 );--  食品编码

  SET t_food_id = foodId;

  SET t_project_id = projectId;
  WHILE
      t_food_id != '0' DO-- 查询限量值
    SELECT
      food_project.project_amount INTO t_project_amount
    FROM
      food_project
    WHERE
      food_id = t_food_id
      AND project_id = t_project_id;
    IF
      t_project_amount IS NULL THEN-- 查询父节点并赋值
      SELECT
        food.parent_id INTO temp_food_id
      FROM
        food
      WHERE
        food_id = t_food_id;

      SET t_food_id = temp_food_id;
      ELSE -- 返回限量值
      RETURN t_project_amount;

    END IF;

  END WHILE;
  RETURN '0';

END
;;
delimiter ;

-- ----------------------------
-- Function structure for getParentFoodList
-- ----------------------------
DROP FUNCTION IF EXISTS `getParentFoodList`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getParentFoodList`(foodId VARCHAR(255)) RETURNS varchar(1000) CHARSET utf8 COLLATE utf8_bin
BEGIN
DECLARE fid varchar(100) default '';
DECLARE str varchar(1000) default foodId;

WHILE foodId is not null  do
    SET fid =(SELECT parent_id FROM food WHERE food_id = foodId);
    IF fid is not null THEN
        SET str = concat(str,',',fid);
        SET foodId = fid;
    ELSE
        SET foodId = fid;
    END IF;
END WHILE;
return str;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getParentList
-- ----------------------------
DROP FUNCTION IF EXISTS `getParentList`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getParentList`(deptId INT,deptType INT) RETURNS varchar(1000) CHARSET utf8 COLLATE utf8_bin
BEGIN

DECLARE tagerDeptId VARCHAR (1000);

SELECT  T2.dept_id into tagerDeptId
FROM (
    SELECT
        @r AS _id,
        (SELECT @r := parent_id FROM sys_dept WHERE dept_id = _id) AS parent_id,
        @l := @l + 1 AS lvl
    FROM
        (SELECT @r := deptId, @l := 0) vars,
        sys_dept h
    WHERE @r <> 0) T1
JOIN sys_dept T2
ON T1._id = T2.dept_id
WHERE T2.dept_type=deptType
ORDER BY  T2.dept_id  DESC ;

RETURN tagerDeptId;

END
;;
delimiter ;

-- ----------------------------
-- Function structure for getPY
-- ----------------------------
DROP FUNCTION IF EXISTS `getPY`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getPY`(in_string VARCHAR(21845)) RETURNS varchar(21845) CHARSET utf8
BEGIN
#截取字符串，每次做截取后的字符串存放在该变量中，初始为函数参数in_string值
DECLARE tmp_str VARCHAR(21845) CHARSET gbk DEFAULT '' ;
#tmp_str的长度
DECLARE tmp_len SMALLINT DEFAULT 0;
#tmp_str的长度
DECLARE tmp_loc SMALLINT DEFAULT 0;
#截取字符，每次 left(tmp_str,1) 返回值存放在该变量中
DECLARE tmp_char VARCHAR(2) CHARSET gbk DEFAULT '';
#结果字符串
DECLARE tmp_rs VARCHAR(21845)CHARSET gbk DEFAULT '';
#拼音字符，存放单个汉字对应的拼音首字符
DECLARE tmp_cc VARCHAR(2) CHARSET gbk DEFAULT '';
#初始化，将in_string赋给tmp_str
SET tmp_str = in_string;
#初始化长度
SET tmp_len = LENGTH(tmp_str);
#如果被计算的tmp_str长度大于0则进入该while
WHILE tmp_len > 0 DO
#获取tmp_str最左端的首个字符，注意这里是获取首个字符，该字符可能是汉字，也可能不是。
SET tmp_char = LEFT(tmp_str,1);
#左端首个字符赋值给拼音字符
SET tmp_cc = tmp_char;
#获取字符的编码范围的位置，为了确认汉字拼音首字母是那一个
SET tmp_loc=INTERVAL(CONV(HEX(tmp_char),16,10),0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,0xBFA6,0xC0AC
,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,0xC8F6,0xCBFA,0xCDDA ,0xCEF4,0xD1B9,0xD4D1);
#判断左端首个字符是多字节还是单字节字符，要是多字节则认为是汉字且作以下拼音获取，要是单字节则不处理。如果是多字节字符但是不在对应的编码范围之内，即对应的不是大写字母则也不做处理，这样数字或者特殊字符就保持原样了
IF (LENGTH(tmp_char)>1 AND tmp_loc>0 AND tmp_loc<24) THEN
#获得汉字拼音首字符
SELECT ELT(tmp_loc,'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q','R','S','T','W','X','Y','Z') INTO tmp_cc;
END IF;
#将当前tmp_str左端首个字符拼音首字符与返回字符串拼接
SET tmp_rs = CONCAT(tmp_rs,tmp_cc);
#将tmp_str左端首字符去除
SET tmp_str = SUBSTRING(tmp_str,2);
#计算当前字符串长度
SET tmp_len = LENGTH(tmp_str);
END WHILE;
#返回结果字符串
RETURN tmp_rs;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getRootFoodName
-- ----------------------------
DROP FUNCTION IF EXISTS `getRootFoodName`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getRootFoodName`(foodId VARCHAR ( 10 )) RETURNS varchar(4000) CHARSET utf8 COLLATE utf8_bin
BEGIN

DECLARE tagerFoodName VARCHAR (1000);

SELECT
-- T2.food_id,
T2.food_name into tagerFoodName
FROM (
    SELECT
        @r AS _id,
        (SELECT @r := parent_id FROM food WHERE food_id = _id) AS parent_id,
        @l := @l + 1 AS lvl
    FROM
        (SELECT @r := foodId, @l := 0) vars,
        food h
    WHERE @r <> 0) T1
JOIN food T2
ON T1._id = T2.food_id
WHERE T2.parent_id = '0'
ORDER BY  T2.parent_id  DESC;

RETURN tagerFoodName;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getZoneChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getZoneChildList`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getZoneChildList`(rootId VARCHAR ( 100 )) RETURNS varchar(4000) CHARSET utf8
BEGIN
	DECLARE
		str VARCHAR ( 4000 );
	DECLARE
		cid VARCHAR ( 4000 );

	SET str = '$';

	SET cid = rootId;
	WHILE
			cid IS NOT NULL DO

			SET str = concat( str, ',', cid );
		SELECT
			group_concat( zone_id ) INTO cid
		FROM
			zone
		WHERE
			FIND_IN_SET( parent_zone_id, cid ) > 0;

	END WHILE;
	RETURN RIGHT(str,LENGTH(str)-2);

END
;;
delimiter ;

-- ----------------------------
-- Function structure for GET_FIRST_PINYIN_CHAR
-- ----------------------------
DROP FUNCTION IF EXISTS `GET_FIRST_PINYIN_CHAR`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `GET_FIRST_PINYIN_CHAR`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
    DETERMINISTIC
BEGIN
    DECLARE V_RETURN VARCHAR(255);
    DECLARE V_BOOL INT DEFAULT 0;
          DECLARE FIRST_VARCHAR VARCHAR(1);

    SET FIRST_VARCHAR = left(CONVERT(P_NAME USING gbk),1);
    SELECT FIRST_VARCHAR REGEXP '[a-zA-Z]' INTO V_BOOL;
    IF V_BOOL = 1 THEN
      SET V_RETURN = FIRST_VARCHAR;
    ELSE
      SET V_RETURN = ELT(INTERVAL(CONV(HEX(left(CONVERT(P_NAME USING gbk),1)),16,10),
          0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,
          0xBFA6,0xC0AC,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,
          0xC8F6,0xCBFA,0xCDDA,0xCEF4,0xD1B9,0xD4D1),
      'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q','R','S','T','W','X','Y','Z');
    END IF;
    RETURN V_RETURN;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for to_pinyin
-- ----------------------------
DROP FUNCTION IF EXISTS `to_pinyin`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `to_pinyin`(STR_NAME VARCHAR(255) CHARSET gbk) RETURNS varchar(255) CHARSET utf8
BEGIN
    DECLARE mypy VARCHAR(255) CHARSET gbk DEFAULT '';
		DECLARE CD INT DEFAULT 0;
		DECLARE DQWZ INT DEFAULT 1;
    DECLARE DYZWZ INT DEFAULT 0;
    DECLARE HZ VARCHAR(4) DEFAULT '';
    DECLARE PYM VARCHAR(30) DEFAULT NULL;
		SET CD=CHAR_LENGTH(STR_NAME);
    SET DQWZ=1;    
    WHILE DQWZ<=CD DO
			  SET HZ=SUBSTRING(STR_NAME,DQWZ,1);
				SET PYM=NULL;
				SELECT PY into PYM FROM t_pinyin WHERE ZI=HZ;
        SET PYM=IFNULL(PYM,HZ);
        SET DYZWZ=POSITION(',' IN PYM);
        IF (DYZWZ>=1) THEN
          SET PYM=LEFT(PYM,DYZWZ-1);
        END IF;
        SET mypy=CONCAT(mypy,PYM);
				SET DQWZ=DQWZ+1;
    END WHILE;
    RETURN UPPER(mypy);
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
