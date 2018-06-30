/*
SQLyog Ultimate v8.53 
MySQL - 5.7.17-log : Database - tonghu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tonghu` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `tonghu`;

/*Table structure for table `attachment` */

DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `file_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '文件名称',
  `original_file_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '文件原始名称',
  `file_type` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '文件类型',
  `file_path` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '文件保存的相对路径',
  `file_size` int(11) NOT NULL COMMENT '文件大小',
  `img_measure` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '如果附件是图片，此值为图片的宽和高',
  `profile` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '文件摘要',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文件',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_AFN` (`file_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统附件表';

/*Data for the table `attachment` */

/*Table structure for table `iot_access_token` */

DROP TABLE IF EXISTS `iot_access_token`;

CREATE TABLE `iot_access_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `key_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '授权ID',
  `key_secret` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '授权密钥',
  `access_token` varchar(150) COLLATE utf8_bin NOT NULL COMMENT '调用凭证',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='IOT调用凭证表';

/*Data for the table `iot_access_token` */

insert  into `iot_access_token`(`id`,`key_id`,`key_secret`,`access_token`,`create_time`,`update_time`) values (1,'3207d4b651921e00','6b5fb363c8c8064492bb42b42326dfb3','OTZDOEQwREZBN0Y4RjY0NUFGRkY0NzQwMUU1NjFDNjQxNUM3MEE4QzY4N0E1QjBFQTNBM0U2QzU5OUVGNENGQw==','2018-06-20 22:01:05','0000-00-00 00:00:00');

/*Table structure for table `job_log` */

DROP TABLE IF EXISTS `job_log`;

CREATE TABLE `job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `job_sequence_no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作业序列号',
  `job_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作业名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '作业运行状态，0 失败 1 成功',
  `note` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '操作备注',
  `total_task` int(9) DEFAULT NULL COMMENT '作业处理任务总数量',
  `success_task` int(9) DEFAULT NULL COMMENT '成功处理任务数量',
  `job_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '作业开始执行时间',
  `job_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '作业结束执行时间',
  `cost_time` bigint(20) DEFAULT NULL COMMENT '作业执行耗费的时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '操作发生的时间',
  PRIMARY KEY (`id`),
  KEY `IDX_JOB_NAME` (`job_name`),
  KEY `IDX_JOB_SEQUENCE_NO` (`job_sequence_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='作业日志表';

/*Data for the table `job_log` */

/*Table structure for table `job_log_detail` */

DROP TABLE IF EXISTS `job_log_detail`;

CREATE TABLE `job_log_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `job_sequence_no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作业序列号',
  `job_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作业名称',
  `task_content` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '当前任务处理所处理的内容',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务处理状态，0 失败 1 成功',
  `note` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '操作备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作发生的时间',
  PRIMARY KEY (`id`),
  KEY `IDX_JOB_NAME` (`job_name`),
  KEY `IDX_JOB_SEQUENCE_NO` (`job_sequence_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='作业详细日志表';

/*Data for the table `job_log_detail` */

/*Table structure for table `mock_data` */

DROP TABLE IF EXISTS `mock_data`;

CREATE TABLE `mock_data` (  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',  `data_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '数据项名称',  `data_type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '数据值类型',  `data_value` mediumtext COLLATE utf8_bin NOT NULL COMMENT '数据值信息 ',  `note` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '数据值简介',  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',  PRIMARY KEY (`id`),  UNIQUE KEY `IDX_DATA_NAME` (`data_name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='mock数据表';

/*Data for the table `mock_data` */

/*Table structure for table `monitor_receiver_log` */

DROP TABLE IF EXISTS `monitor_receiver_log`;

CREATE TABLE `monitor_receiver_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `inner_serial_id` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '内部流水号，由调用服务的时间生成',
  `service_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '调用服务的名称',
  `user_id` bigint(20) NOT NULL COMMENT '访问用户ID',
  `user_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '访问用户的登录名',
  `role_ids` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户角色ID集合',
  `result_code` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '访问的结果编码',
  `result_msg` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '访问的结果信息',
  `time_cost` int(11) NOT NULL COMMENT '调用耗时，单位毫秒',
  `full_url` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '访问服务的全路径',
  `remote_ip` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '访问者ip地址',
  `server_ip` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '服务器ip地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `invoke_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '访问服务开始时间',
  `response_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '请求响应时间',
  PRIMARY KEY (`id`),
  KEY `IDX_RLOG_SERVICE_NAME` (`service_name`),
  KEY `IDX_RLOG_USER_ID` (`user_id`),
  KEY `IDX_RLOG_USER_NAME` (`user_name`),
  KEY `IDX_RLOG_INVOKE_TIME` (`invoke_time`),
  KEY `IDX_RLOG_SERVICE_RESULT_CODE` (`service_name`,`result_code`),
  KEY `IDX_RLOG_SERIAL_ID` (`inner_serial_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='访问服务监控日志表';

/*Data for the table `monitor_receiver_log` */

/*Table structure for table `monitor_sql_log` */

DROP TABLE IF EXISTS `monitor_sql_log`;

CREATE TABLE `monitor_sql_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `inner_serial_id` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '内部流水号，由调用服务的时间生成',
  `sql_command_type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'SQL操作类型',
  `sql_str` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT 'SQL操作语句',
  `parameters` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT 'SQL参数',
  `mybatis_sql_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'SQL来源类方法',
  `file_resource` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'SQL来源文件',
  `data_source` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '数据库信息',
  `time_cost` int(11) NOT NULL COMMENT '执行SQL耗时，单位毫秒',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `sql_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'SQL开始时间',
  `sql_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'SQL结束时间',
  PRIMARY KEY (`id`),
  KEY `IDX_SLOG_SQL_ID` (`mybatis_sql_id`),
  KEY `IDX_SLOG_SQL_START_TIME` (`sql_start_time`),
  KEY `IDX_SLOG_SERIAL_ID` (`inner_serial_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL监测日志表';

/*Data for the table `monitor_sql_log` */

/*Table structure for table `position_test` */

DROP TABLE IF EXISTS `position_test`;

CREATE TABLE `position_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `x` decimal(38,8) NOT NULL COMMENT 'X轴值',
  `y` decimal(38,8) NOT NULL COMMENT 'Y轴值',
  `z` decimal(38,8) NOT NULL COMMENT 'Z轴值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地理位置测试表';

/*Data for the table `position_test` */

insert  into `position_test`(`id`,`x`,`y`,`z`,`create_time`,`update_time`) values (1,'0.10000000','0.20000000','0.30000000','2018-06-20 00:18:36','0000-00-00 00:00:00');

/*Table structure for table `resource_module` */

DROP TABLE IF EXISTS `resource_module`;

CREATE TABLE `resource_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `module_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '资源模块名称',
  `sort_num` tinyint(4) NOT NULL DEFAULT '0' COMMENT '展示顺序，数值越小，展示越靠前',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前资源所属父资源模块id，默认值为0：无父模块',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_MODULE_NAME` (`module_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统资源模块表';

/*Data for the table `resource_module` */

insert  into `resource_module`(`id`,`module_name`,`sort_num`,`pid`,`create_time`,`create_userid`,`update_time`,`update_userid`,`note`) values (1,'首页',1,0,'2018-06-15 20:31:52',1,'0000-00-00 00:00:00',NULL,'warning'),(2,'设备管理',2,0,'2018-06-15 20:32:51',1,'0000-00-00 00:00:00',NULL,'projectManage'),(3,'大屏对接',3,0,'2018-06-15 20:34:27',1,'0000-00-00 00:00:00',NULL,'dictdataManage'),(4,'事件管理',4,0,'2018-06-15 20:34:27',1,'0000-00-00 00:00:00',NULL,'anaDataManage'),(5,'数据管理',5,0,'2018-06-15 20:36:28',1,'0000-00-00 00:00:00',NULL,'dataManage'),(6,'用户管理',6,0,'2018-06-15 20:36:07',1,'0000-00-00 00:00:00',NULL,'userManage'),(7,'系统管理',7,0,'2018-06-15 20:36:28',1,'0000-00-00 00:00:00',NULL,'sysManage');

/*Table structure for table `resources` */

DROP TABLE IF EXISTS `resources`;

CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `resource_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
  `resource_url` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '访问资源的路径',
  `module_id` bigint(20) NOT NULL COMMENT '资源所属模块id，与系统资源模块表存在主外键关系',
  `sort_num` tinyint(4) NOT NULL COMMENT '展示顺序，若字段值为0，表示该资源菜单可以不展示出来，可由模块菜单代为展示',
  `is_restricted` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户访问是否需判断用户所属角色拥有访问此资源的权限 0 不需要，1需要。  默认：1需要',
  `is_menu` tinyint(4) NOT NULL DEFAULT '1' COMMENT '该资源是否是页面左侧的菜单项. 0 不是，1 是。默认：1是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_RESOURCE_NAME` (`resource_name`),
  UNIQUE KEY `IDX_RESOURCE_URL` (`resource_url`),
  UNIQUE KEY `IDX_RESOURCES_MODULE_SORT_NUM` (`module_id`,`sort_num`),
  CONSTRAINT `FK_REFERENCE_RESOURCEMODULE_RESOURCE` FOREIGN KEY (`module_id`) REFERENCES `resource_module` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统资源表';

/*Data for the table `resources` */

insert  into `resources`(`id`,`resource_name`,`resource_url`,`module_id`,`sort_num`,`is_restricted`,`is_menu`,`create_time`,`create_userid`,`update_time`,`update_userid`,`note`) values (1,'首页','/warning.do',1,1,0,1,'2018-06-15 20:37:00',1,'0000-00-00 00:00:00',NULL,NULL),(2,'摄像头','/projectList.do',2,1,1,1,'2018-06-15 21:07:40',1,'0000-00-00 00:00:00',NULL,NULL),(3,'门禁','/projectCreate.do',2,2,1,1,'2018-06-15 21:10:06',1,'0000-00-00 00:00:00',NULL,NULL),(4,'无人机','/projectStatInfo.do',2,3,1,1,'2018-06-15 21:12:41',1,'0000-00-00 00:00:00',NULL,NULL),(5,'单兵系统','/projectShowPass.do',2,4,1,1,'2018-06-15 21:13:48',1,'0000-00-00 00:00:00',NULL,NULL),(6,'Mock数据','/mockDataManage.do',5,1,1,1,'2018-06-15 21:21:56',1,'0000-00-00 00:00:00',NULL,NULL),(7,'资源数据','/resourceDataManage.do',5,2,1,0,'2018-06-15 21:21:56',1,'0000-00-00 00:00:00',NULL,NULL),(8,'用户管理','/userManage.do',6,1,1,1,'2018-06-15 21:21:56',1,'0000-00-00 00:00:00',NULL,NULL),(9,'查看当前用户信息','/currentUserInfo.do',6,2,0,0,'2018-06-15 21:27:25',1,'0000-00-00 00:00:00',NULL,NULL),(10,'设置当前用户信息','/setCurrentUserInfo.do ',6,3,0,0,'2018-06-15 21:28:25',1,'0000-00-00 00:00:00',NULL,NULL),(11,'修改当前用户密码','/setCurrentUserPassInfo.do',6,4,0,0,'2018-06-15 21:29:24',1,'0000-00-00 00:00:00',NULL,NULL),(12,'当前在线用户','/onlineUserList.do',6,5,1,0,'2018-06-15 21:30:11',1,'0000-00-00 00:00:00',NULL,NULL),(13,'踢掉在线用户','/shotOffOnlineUser.do ',6,6,1,0,'2018-06-15 21:35:16',1,'0000-00-00 00:00:00',NULL,NULL),(14,'日志管理','/sysLog.do',7,1,1,1,'2018-06-15 21:23:24',1,'0000-00-00 00:00:00',NULL,NULL),(15,'系统数据','/sysConfig.do',7,2,1,1,'2018-06-15 21:23:56',1,'0000-00-00 00:00:00',NULL,NULL);

/*Table structure for table `resources_sub` */

DROP TABLE IF EXISTS `resources_sub`;

CREATE TABLE `resources_sub` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `sub_resource_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '子资源名称',
  `sub_resource_url` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '访问子资源的路径',
  `resource_id` bigint(20) NOT NULL COMMENT '子资源所属资源id，与系统资源表存在主外键关系',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_RESOURCE_SUB_URL` (`sub_resource_url`,`resource_id`),
  KEY `FK_REFERENCE_SUBRESOURCE_RESOURCE` (`resource_id`),
  CONSTRAINT `FK_REFERENCE_SUBRESOURCE_RESOURCE` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统资源子表';

/*Data for the table `resources_sub` */

/*Table structure for table `role_resource` */

DROP TABLE IF EXISTS `role_resource`;

CREATE TABLE `role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_ROLE_RESOURCE` (`role_id`,`resource_id`),
  KEY `FK_REFERENCE_ROLERESOURCE_RESOURCE` (`resource_id`),
  CONSTRAINT `FK_REFERENCE_ROLERESOURCE_RESOURCE` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_REFERENCE_ROLERESOURCE_ROLE` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统角色资源关联表';

/*Data for the table `role_resource` */

insert  into `role_resource`(`id`,`role_id`,`resource_id`,`create_time`,`create_userid`,`update_time`,`update_userid`,`note`) values (1,1,2,'2018-06-15 21:43:51',1,'0000-00-00 00:00:00',NULL,NULL),(2,1,3,'2018-06-15 21:43:55',1,'0000-00-00 00:00:00',NULL,NULL),(3,1,4,'2018-06-15 21:45:14',1,'0000-00-00 00:00:00',NULL,NULL),(4,1,5,'2018-06-15 21:45:20',1,'0000-00-00 00:00:00',NULL,NULL),(5,1,6,'2018-06-15 21:45:23',1,'0000-00-00 00:00:00',NULL,NULL),(6,1,7,'2018-06-15 21:45:30',1,'0000-00-00 00:00:00',NULL,NULL),(7,1,8,'2018-06-15 21:45:30',1,'0000-00-00 00:00:00',NULL,NULL),(8,1,12,'2018-06-15 21:45:38',1,'0000-00-00 00:00:00',NULL,NULL),(9,1,14,'2018-06-15 21:45:44',1,'0000-00-00 00:00:00',NULL,NULL),(10,1,15,'2018-06-15 21:45:44',1,'0000-00-00 00:00:00',NULL,NULL),(11,2,2,'2018-06-15 21:45:49',1,'0000-00-00 00:00:00',NULL,NULL),(12,2,3,'2018-06-15 21:45:55',1,'0000-00-00 00:00:00',NULL,NULL),(13,2,4,'2018-06-15 21:47:45',1,'0000-00-00 00:00:00',NULL,NULL),(14,2,5,'2018-06-15 21:47:51',1,'0000-00-00 00:00:00',NULL,NULL),(15,2,6,'2018-06-15 21:47:56',1,'0000-00-00 00:00:00',NULL,NULL),(16,2,7,'2018-06-15 21:47:56',1,'0000-00-00 00:00:00',NULL,NULL),(17,2,14,'2018-06-15 21:47:56',1,'0000-00-00 00:00:00',NULL,NULL),(18,2,15,'2018-06-15 21:47:56',1,'0000-00-00 00:00:00',NULL,NULL);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `role_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `editable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可编辑，系统初始化的角色不可修改、删除：0 不可编辑；1 可编辑，默认值为1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_ROLE_NAME` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统角色表';

/*Data for the table `roles` */

insert  into `roles`(`id`,`role_name`,`editable`,`create_time`,`create_userid`,`update_time`,`update_userid`,`note`) values (1,'超级管理员',0,'2018-06-15 20:10:33',1,'0000-00-00 00:00:00',NULL,NULL),(2,'管理员',1,'2018-06-15 20:10:52',1,'0000-00-00 00:00:00',NULL,NULL);

/*Table structure for table `sys_config` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `config_item` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '配置项标识',
  `config_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '配置项名称',
  `config_value` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '系统配置值',
  `value_type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '配置值类型',
  `config_note` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '配置项简介',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  PRIMARY KEY (`id`),
  KEY `IDX_CONFIG_ITEM` (`config_item`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统全局配置信息表';

/*Data for the table `sys_config` */

insert  into `sys_config`(`id`,`config_item`,`config_name`,`config_value`,`value_type`,`config_note`,`create_userid`,`create_time`,`update_userid`,`update_time`) values (1,'image_file_max_size','图片大小限制（M）','10','integer','图片大小属性的最大值，单位 M',1,'2018-11-20 21:23:57',NULL,'0000-00-00 00:00:00'),(2,'front_config_file','前端大屏所需的配置文件','2','file','前端大屏所需的配置文件',1,'2018-11-20 21:23:57',NULL,'0000-00-00 00:00:00'),(3,'tonghu_api_url_prefix','潼湖项目API应用接口URL前缀','http://123.57.132.183:8080/tonghuapi','string','潼湖项目API应用接口URL前缀',1,'2018-06-29 12:26:26',NULL,'0000-00-00 00:00:00');

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `inner_serial_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '内部流水号，由调用服务的时间生成',
  `user_id` bigint(20) DEFAULT NULL COMMENT '当前操作用户的id',
  `user_ip` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '当前操作用户的ip地址',
  `operator_type` tinyint(4) NOT NULL COMMENT '操作类型：1新增；2修改；3删除；4查询；5查看详细信息；6登陆系统；7退出系统',
  `content` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '当前操作的内容',
  `is_success` tinyint(4) NOT NULL COMMENT '当前操作是否成功：1操作成功；0操作失败',
  `cause` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '若操作失败，记录操作失败原因',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作发生的时间',
  PRIMARY KEY (`id`),
  KEY `IDX_SLOG_UT` (`user_id`,`create_time`),
  KEY `IDX_SLOG_TIME` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统日志表';

/*Data for the table `sys_log` */

/*Table structure for table `test_interface` */

DROP TABLE IF EXISTS `test_interface`;

CREATE TABLE `test_interface` (
  `id` int(255) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='接口测试表';

/*Data for the table `test_interface` */

insert  into `test_interface`(`id`,`name`) values (1,'a'),(2,'b');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `user_id` bigint(20) NOT NULL COMMENT '用户id，与用户表存在主外键关系',
  `role_id` bigint(20) NOT NULL COMMENT '角色id，与角色表存在主外键关系',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_USER_ROLE` (`user_id`,`role_id`),
  KEY `FK_REFERENCE_USERROLE_ROLE` (`role_id`),
  CONSTRAINT `FK_REFERENCE_USERROLE_ROLE` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_REFERENCE_USERROLE_USER` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色关联表';

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`,`create_time`,`create_userid`,`update_time`,`update_userid`,`note`) values (1,1,1,'2018-06-15 20:11:11',1,'0000-00-00 00:00:00',NULL,NULL),(2,2,1,'2018-06-15 20:14:48',1,'0000-00-00 00:00:00',NULL,NULL),(3,3,2,'2018-06-15 20:14:48',1,'0000-00-00 00:00:00',NULL,NULL);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id(自增主键)',
  `user_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户登录名称',
  `password` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户登录密码',
  `user_email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户电子邮箱地址',
  `true_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '用户真实名称',
  `user_mobile` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '用户手机号码',
  `user_phone` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '用户座机号码',
  `is_lock` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否锁定，被锁定的用户将不能登录到系统中：0 正常； 1被锁定，默认值为0',
  `editable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可编辑，系统初始化的用户不可修改、删除：0 不可编辑；1 可编辑，默认值为1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_userid` bigint(20) NOT NULL COMMENT '记录创建userid',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录最后修改时间',
  `update_userid` bigint(20) DEFAULT NULL COMMENT '记录修改userid',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_USER_NAME` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统用户表';

/*Data for the table `users` */

insert  into `users`(`id`,`user_name`,`password`,`user_email`,`true_name`,`user_mobile`,`user_phone`,`is_lock`,`editable`,`create_time`,`create_userid`,`update_time`,`update_userid`,`note`) values (1,'mrajian','46f94c8de14fb36680850768ff1b7f2a','mrajian@126.com','超级管理员','13436972811','13436972811',0,0,'2018-06-15 20:09:40',1,'0000-00-00 00:00:00',1,NULL),(2,'admin','46f94c8de14fb36680850768ff1b7f2a','','超级管理员','','',0,1,'2018-06-15 20:13:47',1,'0000-00-00 00:00:00',NULL,NULL),(3,'zhangheng','46f94c8de14fb36680850768ff1b7f2a','','管理员','','',0,1,'2018-06-15 20:13:47',1,'0000-00-00 00:00:00',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
