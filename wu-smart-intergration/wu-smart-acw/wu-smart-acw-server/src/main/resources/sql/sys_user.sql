/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : acw

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 18/07/2022 19:51:59
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user`
(
    `column_name`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '额外字段',
    `password`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'null',
    `scope`           varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `username`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `is_deleted`      tinyint(1) DEFAULT '0' COMMENT '是否删除',
    `create_time`     datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `permission_list` json                                    DEFAULT NULL COMMENT '权限',
    `role_sign_list`  json                                    DEFAULT NULL COMMENT '角色',
    `status`          int(11) DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `s_u` (`scope`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;

insert into sys_user (column_name, password, scope, username, id, is_deleted, create_time, update_time, permission_list,
                      role_sign_list, status)
VALUES (null, '$2a$10$.XKWPdtVzCOT67l.TbCRL.eGeH0E3EHgI2xREYJkGTjgT.2.ylEca', 'web', 'admin', 66, false,
        '2023-08-11 08:29:42.0', '2023-08-11 08:29:42.0', null, '[
    "4251356456"
  ]', true)
ON DUPLICATE KEY UPDATE column_name=values(column_name),
                        password=values(password),
                        scope=values(scope),
                        username=values(username),
                        id=values(id),
                        is_deleted=values(is_deleted),
                        create_time=values(create_time),
                        update_time=values(update_time),
                        permission_list=values(permission_list),
                        role_sign_list=values(role_sign_list),
                        status=values(status);
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
