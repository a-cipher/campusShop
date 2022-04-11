/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : campusshop

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2022-04-11 21:02:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) NOT NULL,
  `area_description` varchar(1000) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES ('3', '东苑', '东苑', '12', '2017-06-04 19:12:58', '2017-06-04 19:12:58');
INSERT INTO `area` VALUES ('4', '南苑', '南苑', '10', '2017-06-04 19:13:09', '2017-06-04 19:13:09');
INSERT INTO `area` VALUES ('5', '西苑', '西苑', '9', '2017-06-04 19:13:18', '2017-06-04 19:13:18');
INSERT INTO `area` VALUES ('6', '北苑', '北苑', '7', '2017-06-04 19:13:29', '2017-06-04 19:13:29');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('6', '13', '30', '<p>哈哈颂</p>', '2022-04-09 07:50:16');

-- ----------------------------
-- Table structure for headline
-- ----------------------------
DROP TABLE IF EXISTS `headline`;
CREATE TABLE `headline` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(1000) DEFAULT NULL COMMENT '名称',
  `linked` varchar(2000) NOT NULL COMMENT '链接',
  `picture` varchar(2000) NOT NULL COMMENT '图片',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0 不可用 1可用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `uuid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of headline
-- ----------------------------
INSERT INTO `headline` VALUES ('16', 'test1', 'headtitle/2017061320315746624.jpg', 'headtitle/2017061320315746624.jpg', '1', '1', '2021-03-04 19:18:33', '2021-03-04 19:18:35', '42627666b7454e96bdf7c1c5737770a7');
INSERT INTO `headline` VALUES ('17', 'test2', 'headtitle/2017061320371786788.jpg', 'headtitle/2017061320371786788.jpg', '2', '1', '2021-03-04 19:43:58', '2021-03-04 19:44:00', '8546c66f42094c33866c47d77de51e7a');
INSERT INTO `headline` VALUES ('18', null, 'headtitle/2017061320393452772.jpg', 'headtitle/2017061320393452772.jpg', '3', '1', '2021-03-04 19:45:02', '2021-03-04 19:45:00', '78807e7c4a71434da3cfbdb73edc32be');
INSERT INTO `headline` VALUES ('19', null, 'headtitle/2017061320400198256.jpg', 'headtitle/2017061320400198256.jpg', '4', '1', '2021-03-04 19:45:03', '2021-03-04 19:45:02', '240c4db091c14b499f6e53462aa6048c');

-- ----------------------------
-- Table structure for local_account
-- ----------------------------
DROP TABLE IF EXISTS `local_account`;
CREATE TABLE `local_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户表id',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_local_profile` (`username`),
  KEY `fk_local_profile` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of local_account
-- ----------------------------
INSERT INTO `local_account` VALUES ('1', '1', 'admin_one', '$apr1$456$iNUomGfKVW8AXA0SFWI4k1', '2021-03-04 17:54:26', '2021-03-04 17:54:33');
INSERT INTO `local_account` VALUES ('4', '12', 'fanchenshop', '$apr1$123456$Oayh418mfUbuTUGloOnT50', '2021-03-04 09:51:14', '2021-03-04 09:51:14');
INSERT INTO `local_account` VALUES ('5', '13', 'test_one', '$apr1$123$pSVq0C1qlXJwF8nS7Uifi.', '2022-02-21 09:04:50', '2022-02-21 09:04:50');
INSERT INTO `local_account` VALUES ('6', '14', 'shop_one', '$apr1$123$gWhMEcmJjkqyK8GYPr9HY1', '2022-02-21 09:13:18', '2022-02-21 09:13:18');
INSERT INTO `local_account` VALUES ('7', '15', 'shop_two', '$apr1$123$caECjjjXq.4kYdNhpmLPh0', '2022-04-09 04:21:21', '2022-04-09 04:21:21');
INSERT INTO `local_account` VALUES ('8', '16', 'shop_three', '$apr1$123$83/GP/N.FOgBJrEFazYbb0', '2022-04-09 04:21:46', '2022-04-09 04:21:46');
INSERT INTO `local_account` VALUES ('9', '17', 'test_two', '$apr1$123$My8F/T5C8Y6.YRNLlfwDn0', '2022-04-09 04:22:12', '2022-04-09 04:22:12');

-- ----------------------------
-- Table structure for order_form
-- ----------------------------
DROP TABLE IF EXISTS `order_form`;
CREATE TABLE `order_form` (
  `order_id` varchar(64) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `order_username` varchar(255) DEFAULT NULL,
  `order_phone` varchar(255) DEFAULT NULL,
  `order_address` varchar(255) DEFAULT NULL,
  `order_user_remark` varchar(255) DEFAULT NULL,
  `order_shop_remark` varchar(255) DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_form
-- ----------------------------
INSERT INTO `order_form` VALUES ('41b3a0fe9acd4de6a6fcc1dc50128a55', '13', '15', '29', '小美', '1387343210', '湖南省 株洲市 天元区 hut 6栋223', '', '', '3', '2022-03-19 09:45:18', '2022-04-10 06:54:27');
INSERT INTO `order_form` VALUES ('7831cd4fa79f40599ac9fde573b89066', '13', '15', '29', '小美', '1387343210', '湖南省 株洲市 天元区 hut 6栋223', '快点送到', '', '1', '2022-03-19 09:44:38', '2022-04-10 04:57:47');
INSERT INTO `order_form` VALUES ('a78e18cf08b643ca9c083b472a779c04', '13', '15', '29', '小美', '12345671234', '北京 北京市 东城区 hut 6栋223', '好喝', '', '2', '2022-03-26 13:08:13', '2022-04-10 05:07:22');

-- ----------------------------
-- Table structure for person_info
-- ----------------------------
DROP TABLE IF EXISTS `person_info`;
CREATE TABLE `person_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `gender` varchar(2) DEFAULT NULL COMMENT '用户性别',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(128) DEFAULT NULL COMMENT '邮件',
  `head_portrait` varchar(1024) DEFAULT NULL COMMENT '用户头像',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_status` int(11) NOT NULL DEFAULT '0' COMMENT '用户状态 0 禁止使用本商城 1允许使用本商城',
  `user_type` int(11) NOT NULL DEFAULT '1' COMMENT '用户身份标识1 顾客 2 店家 3 超级管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person_info
-- ----------------------------
INSERT INTO `person_info` VALUES ('1', null, null, null, null, null, '2021-03-04 19:05:32', '2021-03-04 19:05:35', '1', '3');
INSERT INTO `person_info` VALUES ('12', null, null, null, null, null, '2021-03-04 09:51:14', '2022-04-09 12:22:38', '1', '2');
INSERT INTO `person_info` VALUES ('13', '爱购物的小美', '女', '13465657878', 'maimaimai@qq.com', 'upload\\user\\13\\164560518157734320.jpg', '2022-02-21 09:04:50', '2022-04-09 02:35:36', '1', '1');
INSERT INTO `person_info` VALUES ('14', '蜜雪冰城老板', '男', '12345678', '22', 'upload\\user\\14\\164560391983745506.jpg', '2022-02-21 09:13:18', '2022-04-09 02:37:47', '1', '2');
INSERT INTO `person_info` VALUES ('15', null, null, null, null, null, '2022-04-09 04:21:21', '2022-04-09 04:22:31', '1', '2');
INSERT INTO `person_info` VALUES ('16', null, null, null, null, null, '2022-04-09 04:21:46', '2022-04-09 04:22:32', '1', '2');
INSERT INTO `person_info` VALUES ('17', null, null, null, null, null, '2022-04-09 04:22:12', '2022-04-09 04:22:33', '1', '1');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称',
  `product_desc` varchar(2000) DEFAULT NULL COMMENT '商品描述',
  `img_path` varchar(2000) DEFAULT '' COMMENT '商品缩略图',
  `normal_price` varchar(100) DEFAULT NULL COMMENT '原价',
  `promotion_price` varchar(100) DEFAULT NULL COMMENT '折扣价',
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_status` int(11) NOT NULL DEFAULT '0' COMMENT '商品状态',
  `product_category_id` int(11) DEFAULT NULL COMMENT '商品类别id',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '商店店铺id',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_shop` (`shop_id`),
  KEY `fk_product_probate` (`product_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('15', 'qq', 'ggg', 'upload\\thumbnail\\product\\15\\164768175594495652.jpg', '3.0', '1.0', '1', '2022-03-19 09:22:36', '2022-03-19 09:22:36', '1', '16', '29');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `product_category_name` varchar(100) NOT NULL COMMENT '类别名称',
  `product_category_description` varchar(500) DEFAULT NULL COMMENT '类别描述',
  `priority` int(11) DEFAULT '0' COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '店铺id',
  PRIMARY KEY (`product_category_id`),
  KEY `fk_probate_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('16', '甜品', '甜品', '1', null, null, '29');

-- ----------------------------
-- Table structure for product_img
-- ----------------------------
DROP TABLE IF EXISTS `product_img`;
CREATE TABLE `product_img` (
  `product_img_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品图片id',
  `img_path` varchar(2000) NOT NULL COMMENT '商品图片路径',
  `img_desc` varchar(2000) DEFAULT NULL COMMENT '图片描述',
  `priority` int(11) DEFAULT '0' COMMENT '图片描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `uuid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `fk_img_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_img
-- ----------------------------
INSERT INTO `product_img` VALUES ('1', '1', null, null, null, '14', '1');
INSERT INTO `product_img` VALUES ('2', 'upload\\picture\\product\\15\\164768175596132221.jpg', null, null, '2022-03-19 09:22:36', '15', '13ade69ca53b40a992bf7371c92f6c3b');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `owner_id` int(11) NOT NULL COMMENT '店铺创建人',
  `area_id` int(11) DEFAULT NULL COMMENT '店铺区域id',
  `shop_category_id` int(11) DEFAULT NULL COMMENT '店铺类别id',
  `shop_name` varchar(256) NOT NULL COMMENT '店铺名字',
  `shop_description` varchar(1024) DEFAULT NULL COMMENT '店铺描述',
  `shop_address` varchar(200) DEFAULT NULL COMMENT '店铺地址',
  `phone` varchar(128) DEFAULT NULL COMMENT '店铺联系方式',
  `shop_img` varchar(1024) DEFAULT NULL COMMENT '店铺门面照片',
  `priority` int(11) DEFAULT '0' COMMENT '优先级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_status` int(11) NOT NULL DEFAULT '0' COMMENT '店铺状态',
  `advice` varchar(255) DEFAULT NULL COMMENT '店铺建议，超级管理员给店铺的建议',
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('29', '14', '3', '3', '蜜雪冰城', '甜蜜蜜', '东苑一栋', '12345678', 'upload\\thumbnail\\shop\\29\\164560399256480119.jpg', '0', '2022-02-23 08:13:13', '2022-04-09 08:06:00', '1', null);
INSERT INTO `shop` VALUES ('30', '14', '4', '1', '变废为宝', '二手产品，变废为宝', '南苑6栋8号门面', '13412344321', 'upload\\thumbnail\\shop\\30\\164947543266112269.jpg', '0', '2022-04-09 03:37:13', '2022-04-09 03:37:47', '1', null);

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺类别id',
  `shop_category_name` varchar(100) NOT NULL DEFAULT '' COMMENT '店铺类别名称',
  `shop_category_desc` varchar(1000) DEFAULT '' COMMENT '店铺类别描述',
  `shop_category_img` varchar(2000) DEFAULT NULL COMMENT '图片',
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '优先级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级ID',
  `uuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_shop_category_self` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_category
-- ----------------------------
INSERT INTO `shop_category` VALUES ('1', '二手市场', '二手商品交易', 'shopcategory/2017061223272255687.png', '99', '2021-03-04 19:09:47', '2021-03-04 19:09:49', null, 'b592999e594f4ef68d5fd5b343a84c3e');
INSERT INTO `shop_category` VALUES ('2', '美容美发', '美容美发', 'shopcategory/2017061223273314635.png', '99', '2021-03-04 19:46:46', '2021-03-04 19:46:43', null, '774f84ae6df64f23b33e7a75ee1d3b8f');
INSERT INTO `shop_category` VALUES ('3', '美食饮品', '美食饮品', 'shopcategory/2017061223274213433.png', '99', '2021-03-04 19:46:47', '2021-03-04 19:46:44', null, 'd558aa8671bc4d0289729653e00bcfb0');
INSERT INTO `shop_category` VALUES ('4', '休闲娱乐', '休闲娱乐', 'shopcategory/2017061223275121460.png', '99', '2021-03-04 19:46:45', '2021-03-04 19:46:45', null, 'c37187f82925409ea9039ddebcffd9d6');
INSERT INTO `shop_category` VALUES ('5', '培训教育', '培训教育', 'shopcategory/2017061223280082147.png', '99', '2021-03-04 19:47:33', '2021-03-04 19:47:31', null, '6aafdce6692e45f48c9c6139c3e59a52');
INSERT INTO `shop_category` VALUES ('6', '租赁市场', '租赁市场', 'shopcategory/2017061223281361578.png', '99', '2021-03-04 19:47:34', '2021-03-04 19:47:32', null, '982ce5a4cd174405986042b92282db6e');

-- ----------------------------
-- Table structure for wechat_account
-- ----------------------------
DROP TABLE IF EXISTS `wechat_account`;
CREATE TABLE `wechat_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `open_id` varchar(512) NOT NULL COMMENT '微信的openId',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_oauth_profile` (`user_id`),
  KEY `uk_oauth` (`open_id`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wechat_account
-- ----------------------------
