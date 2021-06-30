/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : 192.168.0.149:3306
 Source Schema         : simple

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 22/06/2021 17:56:56
*/

create database simple;
use simple;
create table if not EXISTS `info`(
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `number` varchar(20) NOT NULL COMMENT '编号',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `ins_time` datetime DEFAULT TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=innodb default charset=utf8mb4 comment='测试';

