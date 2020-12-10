/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : simple

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login`  (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
    `role_id` int(11) NOT NULL COMMENT '角色标识',
    `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名称',
    `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES (1, 3, 'test', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `login` VALUES (2, 2, 'admin', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `login` VALUES (3, 1, 'kangkang', 'E10ADC3949BA59ABBE56E057F20F883E');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限标识',
    `role_id` int(11) NOT NULL COMMENT '角色标识',
    `per_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
    `per_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限内容',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 1, 'mobile', 'mobile:view');
INSERT INTO `permission` VALUES (2, 2, 'platform', 'platform:view');
INSERT INTO `permission` VALUES (3, 3, 'system', 'system:view');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
    `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色标识',
    `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    `role_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色内容',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '移动用户', 'mobile');
INSERT INTO `role` VALUES (2, '平台用户', 'platform');
INSERT INTO `role` VALUES (3, '系统用户', 'system');

SET FOREIGN_KEY_CHECKS = 1;