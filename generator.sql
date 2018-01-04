/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-01-04 16:43:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for generator
-- ----------------------------
DROP TABLE IF EXISTS `generator`;
CREATE TABLE `generator` (
  `gen_table` varchar(255) NOT NULL,
  `gen_model` varchar(255) DEFAULT NULL,
  `gen_mapper` varchar(255) DEFAULT NULL,
  `gen_mapper_xml` varchar(255) DEFAULT NULL,
  `gen_service` varchar(255) DEFAULT NULL,
  `gen_service_impl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`gen_table`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
