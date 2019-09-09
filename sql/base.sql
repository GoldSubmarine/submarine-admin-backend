/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.39.36
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 192.168.39.36:3306
 Source Schema         : submarine

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 09/09/2019 17:32:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_entity
-- ----------------------------
DROP TABLE IF EXISTS `base_entity`;
CREATE TABLE `base_entity`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称（中文）',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父级id',
  `pids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '父级ids',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES (1, '根节点', 'root', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称（中文）',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限值',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父级id',
  `pids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '父级ids',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '根节点', 'root', NULL, '', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (1166273333582938114, '首页', 'dashboard', 1, '1,', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (1166273440365723650, '系统设置', 'system', 1, '1,', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (1166273575871102977, '权限管理', 'permission', 1166273440365723650, '1,1166273440365723650,', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (1166273628836773889, '菜单管理', 'menu', 1166273440365723650, '1,1166273440365723650,', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (1169887615621013506, '部门管理', 'dept', 1166273440365723650, '1,1166273440365723650,', NULL, 'admin', '2019-09-06 16:18:38', 'admin', '2019-09-06 16:18:47', 0);
INSERT INTO `menu` VALUES (1169900212583931906, '角色管理', 'role', 1166273440365723650, '1,1166273440365723650,', NULL, 'admin', '2019-09-06 17:08:41', 'admin', '2019-09-06 17:08:47', 0);
INSERT INTO `menu` VALUES (1169900316246155265, '用户管理', 'user', 1166273440365723650, '1,1166273440365723650,', NULL, 'admin', '2019-09-06 17:09:06', 'admin', '2019-09-06 17:09:06', 0);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称（中文）',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限值',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父级id',
  `pids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '父级ids',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '根节点', NULL, NULL, NULL, NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-07 09:45:41', 0);
INSERT INTO `permission` VALUES (1166274289691312129, '系统设置', '', 1, '1,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1166274342820560897, '权限管理', 'permission', 1166274289691312129, '1,1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:00:24', 0);
INSERT INTO `permission` VALUES (1166274502715817985, '新增', 'permission.add', 1166274342820560897, '1,1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:00:41', 0);
INSERT INTO `permission` VALUES (1166274565542297601, '编辑', 'permission.edit', 1166274342820560897, '1,1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:00:45', 0);
INSERT INTO `permission` VALUES (1166274619166474242, '删除', 'permission.del', 1166274342820560897, '1,1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:00:49', 0);
INSERT INTO `permission` VALUES (1166274761865084929, '菜单管理', 'menu', 1166274289691312129, '1,1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:00:59', 0);
INSERT INTO `permission` VALUES (1166274811303346178, '新增', 'menu.add', 1166274761865084929, '1,1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1166274920577548289, '编辑', 'menu.edit', 1166274761865084929, '1,1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1166274968761712641, '删除', 'menu.del', 1166274761865084929, '1,1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1169507224628236289, '查询', 'permission.find', 1166274342820560897, '1,1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:36', 0);
INSERT INTO `permission` VALUES (1169507316957450241, '查询', 'menu.find', 1166274761865084929, '1,1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:36', 0);
INSERT INTO `permission` VALUES (1169542890862936065, '部门管理', 'dept', 1166274289691312129, '1,1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:01:06', 0);
INSERT INTO `permission` VALUES (1169542975571099650, '查询', 'dept.find', 1169542890862936065, '1,1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 07:28:37', 'admin', '2019-09-06 15:13:10', 0);
INSERT INTO `permission` VALUES (1169545442803347458, '新增', 'dept.add', 1169542890862936065, '1,1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1169546311867351042, '编辑', 'dept.edit', 1169542890862936065, '1,1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1169549849653735426, '删除', 'dept.del', 1169542890862936065, '1,1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1169875030167265282, '角色管理', 'role', 1166274289691312129, '1,1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:01:14', 0);
INSERT INTO `permission` VALUES (1169876415491350529, '查询', 'role.find', 1169875030167265282, '1,1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 15:36:03', 'admin', '2019-09-06 17:01:26', 0);
INSERT INTO `permission` VALUES (1169876903532208129, '新增', 'role.add', 1169875030167265282, '1,1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 15:36:04', 'admin', '2019-09-06 17:01:31', 0);
INSERT INTO `permission` VALUES (1169877188686159873, '编辑', 'role.edit', 1169875030167265282, '1,1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 15:37:12', 'admin', '2019-09-06 17:01:36', 0);
INSERT INTO `permission` VALUES (1169877688869494786, '删除', 'role.del', 1169875030167265282, '1,1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 07:39:11', 'admin', '2019-09-06 17:01:41', 0);
INSERT INTO `permission` VALUES (1169877853609172993, '用户管理', 'user', 1166274289691312129, '1,1166274289691312129,', NULL, 'admin', '2019-09-06 15:39:50', 'admin', '2019-09-06 17:01:48', 0);
INSERT INTO `permission` VALUES (1169877924039925762, '查询', 'user.find', 1169877853609172993, '1,1166274289691312129,1169877853609172993,', NULL, 'admin', '2019-09-06 15:40:07', 'admin', '2019-09-06 15:40:07', 0);
INSERT INTO `permission` VALUES (1169877976909127681, '新增', 'user.add', 1169877853609172993, '1,1166274289691312129,1169877853609172993,', NULL, 'admin', '2019-09-06 15:40:20', 'admin', '2019-09-06 15:40:20', 0);
INSERT INTO `permission` VALUES (1169878129711816705, '编辑', 'user.edit', 1169877853609172993, '1,1166274289691312129,1169877853609172993,', NULL, 'admin', '2019-09-06 07:40:56', 'admin', '2019-09-06 15:41:36', 0);
INSERT INTO `permission` VALUES (1169878267599560706, '删除', 'user.del', 1169877853609172993, '1,1166274289691312129,1169877853609172993,', NULL, 'admin', '2019-09-06 15:41:29', 'admin', '2019-09-06 15:41:29', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称（中文）',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', 'superAdmin', NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1170993076252475394, 1, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);
INSERT INTO `role_menu` VALUES (1170993076260864002, 1166273333582938114, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);
INSERT INTO `role_menu` VALUES (1170993076265058305, 1166273440365723650, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);
INSERT INTO `role_menu` VALUES (1170993076265058306, 1166273575871102977, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);
INSERT INTO `role_menu` VALUES (1170993076265058307, 1166273628836773889, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);
INSERT INTO `role_menu` VALUES (1170993076269252610, 1169887615621013506, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);
INSERT INTO `role_menu` VALUES (1170993076269252611, 1169900212583931906, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);
INSERT INTO `role_menu` VALUES (1170993076269252612, 1169900316246155265, 1, NULL, 'admin', '2019-09-09 17:31:20', 'admin', '2019-09-09 17:31:20', 0);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '权限id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1170993031725744130, 1166274342820560897, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031755104258, 1166274502715817985, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031755104259, 1166274565542297601, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031755104260, 1166274619166474242, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031763492866, 1169507224628236289, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031763492867, 1166274761865084929, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031763492868, 1166274811303346178, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031771881473, 1166274920577548289, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031771881474, 1166274968761712641, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031771881475, 1169507316957450241, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031776075778, 1169542890862936065, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031776075779, 1169542975571099650, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031776075780, 1169545442803347458, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031776075781, 1169546311867351042, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031776075782, 1169549849653735426, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031784464385, 1169875030167265282, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031784464386, 1169876415491350529, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031784464387, 1169876903532208129, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031784464388, 1169877188686159873, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031784464389, 1169877688869494786, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031792852994, 1169877853609172993, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031792852995, 1169877924039925762, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031792852996, 1169877976909127681, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031792852997, 1169878129711816705, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);
INSERT INTO `role_permission` VALUES (1170993031797047297, 1169878267599560706, 1, NULL, 'admin', '2019-09-09 17:31:09', 'admin', '2019-09-09 17:31:09', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `sex` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态（启用禁用）',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id',
  `dept_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '部门ids（包含自身）',
  `dept_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `jwt_secret` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'jwt密钥',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$tUlBdRd01o8rXtE.KS1qi.WZmRB2mFyHG.H0qbh5UM851vl8IIV/S', '超级管理员', '13000000000', '123124@qq.com', NULL, 'male', 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png', 'enable', 1, '1,', '根节点', '0<A7S\'p78O>[<zc&', NULL, NULL, NULL, 'admin', '2019-09-09 17:18:52', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, NULL, NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
