/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.39.36
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 192.168.39.36:3306
 Source Schema         : submarine_test

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 12/11/2019 11:22:27
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
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES (1173923443246661633, 'test_user_status', '用户状态（测试）', 'admin', '2019-09-17 19:35:34', 'admin', '2019-09-18 17:21:02', 0);
INSERT INTO `dictionary` VALUES (1174611433811943425, 'flow_type', '流程类型', 'admin', '2019-09-19 17:09:24', 'admin', '2019-09-19 17:09:24', 0);

-- ----------------------------
-- Table structure for dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_item`;
CREATE TABLE `dictionary_item`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dictionary_id` bigint(20) NULL DEFAULT NULL COMMENT '字典id',
  `label` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典标签',
  `value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `sort` int(3) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary_item
-- ----------------------------
INSERT INTO `dictionary_item` VALUES (1174207472353255425, 1173923443246661633, '测试1', 'test1', 1, NULL, 'admin', '2019-09-18 14:24:12', 'admin', '2019-09-18 14:31:24', 0);
INSERT INTO `dictionary_item` VALUES (1174209371919982594, 1173923443246661633, '测试2', 'test2', 10, NULL, 'admin', '2019-09-18 14:31:45', 'admin', '2019-09-18 14:31:45', 0);
INSERT INTO `dictionary_item` VALUES (1174211413564563458, 1173923443246661633, '测试3', 'test3', 20, NULL, 'admin', '2019-09-18 14:39:51', 'admin', '2019-09-18 14:39:51', 0);
INSERT INTO `dictionary_item` VALUES (1174611695179997186, 1174611433811943425, '运输', 'transport', 1, NULL, 'admin', '2019-09-19 17:10:26', 'admin', '2019-09-19 17:10:26', 0);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型（menu/button）',
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
INSERT INTO `permission` VALUES (1166273333582938114, 'menu', '首页', 'dashboard', NULL, '', NULL, 'admin', '2019-09-06 16:18:34', 'admin', '2019-09-06 16:18:34', 0);
INSERT INTO `permission` VALUES (1166273440365723650, 'menu', '系统设置', 'system', NULL, '', NULL, 'admin', '2019-09-06 16:18:35', 'admin', '2019-09-06 16:18:35', 0);
INSERT INTO `permission` VALUES (1166273575871102977, 'menu', '权限管理', 'permission', 1166273440365723650, '1166273440365723650,', NULL, 'admin', '2019-09-06 16:18:36', 'admin', '2019-09-18 16:40:26', 0);
INSERT INTO `permission` VALUES (1166273628836773889, 'menu', '菜单管理', 'menu', 1166273440365723650, '1166273440365723650,', NULL, 'admin', '2019-09-06 16:18:37', 'admin', '2019-09-06 16:18:34', 0);
INSERT INTO `permission` VALUES (1166274289691312129, 'button', '系统设置', '', NULL, '', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 15:28:37', 0);
INSERT INTO `permission` VALUES (1166274342820560897, 'button', '权限管理', 'permission', 1166274289691312129, '1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:00:24', 0);
INSERT INTO `permission` VALUES (1166274502715817985, 'button', '权限新增', 'permission.add', 1166274342820560897, '1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:45:31', 0);
INSERT INTO `permission` VALUES (1166274565542297601, 'button', '权限编辑', 'permission.edit', 1166274342820560897, '1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:45:35', 0);
INSERT INTO `permission` VALUES (1166274619166474242, 'button', '权限删除', 'permission.del', 1166274342820560897, '1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:45:40', 0);
INSERT INTO `permission` VALUES (1166274761865084929, 'button', '菜单管理', 'menu', 1166274289691312129, '1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:00:59', 0);
INSERT INTO `permission` VALUES (1166274811303346178, 'button', '菜单新增', 'menu.add', 1166274761865084929, '1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:45:56', 0);
INSERT INTO `permission` VALUES (1166274920577548289, 'button', '菜单编辑', 'menu.edit', 1166274761865084929, '1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:46:02', 0);
INSERT INTO `permission` VALUES (1166274968761712641, 'button', '菜单删除', 'menu.del', 1166274761865084929, '1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:46:07', 0);
INSERT INTO `permission` VALUES (1169507224628236289, 'button', '权限查询', 'permission.find', 1166274342820560897, '1166274289691312129,1166274342820560897,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:45:46', 0);
INSERT INTO `permission` VALUES (1169507316957450241, 'button', '菜单查询', 'menu.find', 1166274761865084929, '1166274289691312129,1166274761865084929,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:46:11', 0);
INSERT INTO `permission` VALUES (1169542890862936065, 'button', '部门管理', 'dept', 1166274289691312129, '1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:01:06', 0);
INSERT INTO `permission` VALUES (1169542975571099650, 'button', '部门查询', 'dept.find', 1169542890862936065, '1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 07:28:37', 'admin', '2019-10-31 15:46:23', 0);
INSERT INTO `permission` VALUES (1169545442803347458, 'button', '部门新增', 'dept.add', 1169542890862936065, '1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:46:27', 0);
INSERT INTO `permission` VALUES (1169546311867351042, 'button', '部门编辑', 'dept.edit', 1169542890862936065, '1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:46:32', 0);
INSERT INTO `permission` VALUES (1169549849653735426, 'button', '部门删除', 'dept.del', 1169542890862936065, '1166274289691312129,1169542890862936065,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-10-31 15:46:37', 0);
INSERT INTO `permission` VALUES (1169875030167265282, 'button', '角色管理', 'role', 1166274289691312129, '1166274289691312129,', NULL, 'admin', '2019-09-06 15:28:37', 'admin', '2019-09-06 17:01:14', 0);
INSERT INTO `permission` VALUES (1169876415491350529, 'button', '角色查询', 'role.find', 1169875030167265282, '1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 15:36:03', 'admin', '2019-10-31 16:08:51', 0);
INSERT INTO `permission` VALUES (1169876903532208129, 'button', '角色新增', 'role.add', 1169875030167265282, '1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 15:36:04', 'admin', '2019-10-31 16:08:56', 0);
INSERT INTO `permission` VALUES (1169877188686159873, 'button', '角色编辑', 'role.edit', 1169875030167265282, '1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 15:37:12', 'admin', '2019-10-31 16:09:21', 0);
INSERT INTO `permission` VALUES (1169877688869494786, 'button', '角色删除', 'role.del', 1169875030167265282, '1166274289691312129,1169875030167265282,', NULL, 'admin', '2019-09-06 07:39:11', 'admin', '2019-10-31 16:09:26', 0);
INSERT INTO `permission` VALUES (1169877853609172993, 'button', '用户管理', 'user', 1166274289691312129, '1166274289691312129,', NULL, 'admin', '2019-09-06 15:39:50', 'admin', '2019-09-06 17:01:48', 0);
INSERT INTO `permission` VALUES (1169877924039925762, 'button', '用户查询', 'user.find', 1169877853609172993, '1166274289691312129,1169877853609172993,', NULL, 'admin', '2019-09-06 15:40:07', 'admin', '2019-10-31 16:09:36', 0);
INSERT INTO `permission` VALUES (1169877976909127681, 'button', '用户新增', 'user.add', 1169877853609172993, '1166274289691312129,1169877853609172993,', NULL, 'admin', '2019-09-06 15:40:20', 'admin', '2019-10-31 16:09:40', 0);
INSERT INTO `permission` VALUES (1169878129711816705, 'button', '用户编辑', 'user.edit', 1169877853609172993, '1166274289691312129,1166273333582938114', NULL, 'admin', '2019-09-06 07:40:56', 'admin', '2019-10-31 16:09:44', 0);
INSERT INTO `permission` VALUES (1169878267599560706, 'button', '用户删除', 'user.del', 1169877853609172993, '1166274289691312129,1169877853609172993,', NULL, 'admin', '2019-09-06 15:41:29', 'admin', '2019-10-31 16:09:49', 0);
INSERT INTO `permission` VALUES (1169887615621013506, 'menu', '部门管理', 'dept', 1166273440365723650, '1166273440365723650,', NULL, 'admin', '2019-09-06 16:18:38', 'admin', '2019-09-06 16:18:47', 0);
INSERT INTO `permission` VALUES (1169900212583931906, 'menu', '角色管理', 'role', 1166273440365723650, '1166273440365723650,', NULL, 'admin', '2019-09-06 17:08:41', 'admin', '2019-09-06 17:08:47', 0);
INSERT INTO `permission` VALUES (1169900316246155265, 'menu', '用户管理', 'user', 1166273440365723650, '1166273440365723650,', NULL, 'admin', '2019-09-06 17:09:06', 'admin', '2019-09-06 17:09:06', 0);
INSERT INTO `permission` VALUES (1173859276569501697, 'button', '字典管理', 'dictionary', 1166274289691312129, '1166274289691312129,', NULL, 'admin', '2019-09-17 15:20:35', 'admin', '2019-09-17 15:20:35', 0);
INSERT INTO `permission` VALUES (1173859497940672513, 'button', '字典查询', 'dictionary.find', 1173859276569501697, '1166274289691312129,1173859276569501697,', NULL, 'admin', '2019-09-17 15:21:28', 'admin', '2019-10-31 16:09:58', 0);
INSERT INTO `permission` VALUES (1173859574289588225, 'button', '字典新增', 'dictionary.add', 1173859276569501697, '1166274289691312129,1173859276569501697,', NULL, 'admin', '2019-09-17 15:21:46', 'admin', '2019-10-31 16:10:02', 0);
INSERT INTO `permission` VALUES (1173859654665035777, 'button', '字典编辑', 'dictionary.edit', 1173859276569501697, '1166274289691312129,1173859276569501697,', NULL, 'admin', '2019-09-17 15:22:06', 'admin', '2019-10-31 16:10:07', 0);
INSERT INTO `permission` VALUES (1173859737196355586, 'button', '字典删除', 'dictionary.del', 1173859276569501697, '1166274289691312129,1173859276569501697,', NULL, 'admin', '2019-09-17 15:22:25', 'admin', '2019-10-31 16:10:12', 0);
INSERT INTO `permission` VALUES (1173860060996624385, 'menu', '字典管理', 'dictionary', 1166273440365723650, '1166273440365723650,', NULL, 'admin', '2019-09-17 15:23:42', 'admin', '2019-09-17 15:23:42', 0);

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
INSERT INTO `role` VALUES (1, '超级管理员', 'SuperAdmin', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `role` VALUES (2, '系统管理员', 'Admin', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `role` VALUES (3, '超级管理员', 'OrgAdmin', NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '权限id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型（menu/button）',
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
INSERT INTO `role_permission` VALUES (1174252463540768769, 1166273333582938114, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1174252463540768770, 1166273440365723650, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1174252463540768771, 1166273575871102977, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1174252463549157377, 1166273628836773889, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1174252463549157378, 1169887615621013506, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1174252463549157379, 1169900212583931906, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1174252463549157380, 1169900316246155265, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1174252463549157381, 1173860060996624385, 1, 'menu', NULL, 'admin', '2019-09-18 17:22:59', 'admin', '2019-09-18 17:22:59', 0);
INSERT INTO `role_permission` VALUES (1194085933498564610, 1166274342820560897, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933519536129, 1166274502715817985, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933523730433, 1166274565542297601, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933523730434, 1166274619166474242, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933527924738, 1169507224628236289, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933527924739, 1166274761865084929, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933536313345, 1166274811303346178, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933540507649, 1166274920577548289, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933540507650, 1166274968761712641, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933544701953, 1169507316957450241, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933553090562, 1169542890862936065, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933553090563, 1169542975571099650, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933553090564, 1169545442803347458, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933553090565, 1169546311867351042, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933553090566, 1169549849653735426, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933565673473, 1169875030167265282, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933569867777, 1169876415491350529, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933569867778, 1169876903532208129, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933574062081, 1169877188686159873, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933574062082, 1169877688869494786, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933574062083, 1169877853609172993, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933574062084, 1169877924039925762, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933574062085, 1169877976909127681, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933582450690, 1169878129711816705, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933582450691, 1169878267599560706, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933582450692, 1173859276569501697, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933582450693, 1173859497940672513, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933586644994, 1173859574289588225, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933586644995, 1173859654665035777, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);
INSERT INTO `role_permission` VALUES (1194085933586644996, 1173859737196355586, 1, 'button', NULL, 'admin', '2019-11-12 10:54:06', 'admin', '2019-11-12 10:54:06', 0);

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
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$LZIWw.1hMkKRM3D87blxvOkGc.I14HxHd/LNylxkz3cZLDcjkD8l2', '超级管理员', '13000000000', '123124@qq.com', NULL, 'male', 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png', 'enable', 1, '1,', '根节点', '$\\;\\huA4k#zHCOYS', NULL, NULL, NULL, 'admin', '2019-10-31 15:36:55', 0);
INSERT INTO `user` VALUES (1191559879739359233, 'test', '$2a$10$s7E5gK6hLDKtyWWNMSN2lO6lOcbgb1K5xyTofNxn2/oa3hpOgPByC', 'asf', '15732636972', NULL, NULL, 'male', NULL, 'enable', 1, '1,', '根节点', '0bj8{LP@*`oQnQam', NULL, 'admin', '2019-11-05 11:36:28', 'admin', '2019-11-07 12:07:44', 0);
INSERT INTO `user` VALUES (1191605917275664385, 'test2', '$2a$10$gLJMcZeDvkFPSiiiRPKoyeJShl.SjOyxALH0T4uLXsnpxofnRbQKS', 'qwew', '15732636876', NULL, NULL, 'female', NULL, 'enable', 1, '1,', '根节点', '(6gkz:i8N^/Vz_t%', NULL, 'admin', '2019-11-05 14:39:24', 'admin', '2019-11-07 12:07:37', 0);

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
