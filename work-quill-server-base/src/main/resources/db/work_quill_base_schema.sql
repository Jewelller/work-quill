/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MariaDB
 Source Server Version : 110302
 Source Host           : localhost:3306
 Source Schema         : work_quill_base

 Target Server Type    : MariaDB
 Target Server Version : 110302
 File Encoding         : 65001

 Date: 05/06/2024 02:31:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `user_id` bigint(20) NOT NULL,
  `role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'ROLE_USER',
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, 'ROLE_USER');
INSERT INTO `authority` VALUES (2, 'ROLE_USER');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `defualt_index`(`user_id`, `username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '管理员', '$2a$10$3y2hCUTNvo46reU4vtqGbeDPrGDkf9bzLTLF058ilqIrRAKfisNRa', 1);
INSERT INTO `user` VALUES (2, 'user', '测试用户', '$2a$10$3y2hCUTNvo46reU4vtqGbeDPrGDkf9bzLTLF058ilqIrRAKfisNRa', 1);

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `auto_add_default_role`;
delimiter ;;
CREATE TRIGGER `auto_add_default_role` AFTER INSERT ON `user` FOR EACH ROW BEGIN
-- 	SET @default_role := (SELECT COLUMN_DEFAULT FROM information_schema.COLUMNS WHERE TABLE_NAME = 'authority' AND COLUMN_NAME = 'role');

	INSERT INTO authority(user_id, role)
		VALUES(new.user_id, 'ROLE_USER');
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
