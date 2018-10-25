/*
Navicat MySQL Data Transfer

Source Server         : mysql127.0.0.1
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : logger

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-10-25 14:50:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_data_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_data_dict`;
CREATE TABLE `t_data_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_dict_entry` char(5) DEFAULT NULL,
  `vc_entry_name` varchar(50) DEFAULT NULL,
  `l_dict_type` int(11) DEFAULT NULL,
  `l_sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_unique_type_entry` (`c_dict_entry`,`l_dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Table structure for t_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_type`;
CREATE TABLE `t_dict_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vc_code` varchar(10) NOT NULL COMMENT '类型编码',
  `vc_name` varchar(30) DEFAULT NULL COMMENT '类型名称',
  `vc_description` varchar(100) DEFAULT NULL COMMENT '类型描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_unique_code` (`vc_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='字典类型';

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vc_code` varchar(50) DEFAULT NULL COMMENT '权限编码',
  `vc_name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  `vc_url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `vc_icon` varchar(50) DEFAULT NULL COMMENT '资源图标',
  `parent_id` int(11) DEFAULT '0' COMMENT '父节点ID',
  `c_enable` char(1) DEFAULT '1' COMMENT '是否启用(0：禁用，1：启用，默认是1)',
  `l_sort` int(11) DEFAULT NULL COMMENT '排序',
  `c_resource_type` char(1) DEFAULT NULL COMMENT '1:代表菜单，2：代表按钮',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '父节点ID集合',
  `c_target_blank` char(1) DEFAULT '0' COMMENT '在新窗口打开',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`vc_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vc_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `vc_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `c_enable` char(1) DEFAULT '1' COMMENT '是否启用(0：禁用，1：启用，默认是1)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

-- ----------------------------
-- Table structure for t_scheduled_job
-- ----------------------------
DROP TABLE IF EXISTS `t_scheduled_job`;
CREATE TABLE `t_scheduled_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vc_job_name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `vc_jobgroup_name` varchar(50) DEFAULT NULL COMMENT '任务组名',
  `vc_trigger_name` varchar(50) DEFAULT NULL COMMENT '触发器名',
  `vc_triggergroup_name` varchar(50) DEFAULT NULL COMMENT '触发器组名',
  `vc_jobclass_name` varchar(100) DEFAULT NULL COMMENT '任务执行类',
  `vc_cron_expression` varchar(50) DEFAULT NULL COMMENT 'cron表达式',
  `c_status` char(1) DEFAULT '0' COMMENT '任务状态(0：运行中，1：已停止)',
  `c_exec_on_startup` char(1) DEFAULT '0' COMMENT '程序启动时立即执行(0：否，1：是，默认否)',
  `l_last_start_time` bigint(20) DEFAULT NULL COMMENT '最后一次开始执行时间',
  `l_last_end_time` bigint(20) DEFAULT NULL COMMENT '最后一次执行完成时间',
  `l_last_exec_status` char(1) DEFAULT NULL COMMENT '最后一次执行状态（0：正常，1：异常）',
  `l_event_id` int(11) DEFAULT NULL COMMENT '事件编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_unique_class_cron` (`vc_jobclass_name`,`vc_cron_expression`),
  UNIQUE KEY `AK_unique_jobname_triggername` (`vc_job_name`,`vc_trigger_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `vc_user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `vc_password` varchar(32) DEFAULT NULL COMMENT '登陆密码',
  `vc_real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `vc_mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `vc_email` varchar(50) DEFAULT NULL COMMENT '邮箱地址',
  `c_status` char(1) DEFAULT '0' COMMENT '用户状态(0：正常，1：禁用，默认正常)',
  `c_lock_status` char(1) DEFAULT '0' COMMENT '锁定状态0：非锁定，1：锁定，默认非锁定)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';
