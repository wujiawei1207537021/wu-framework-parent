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

 Date: 18/07/2022 19:21:54
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for schema
-- ----------------------------
DROP TABLE IF EXISTS `schema`;
CREATE TABLE `schema`
(
    `name`          varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'null',
    `character_set` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'null',
    `sorting_rules` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'null',
    `ext`           varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'null',
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `is_deleted`    tinyint(1) DEFAULT '0' COMMENT '是否删除',
    `create_time`   datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of schema
-- ----------------------------
BEGIN;
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
