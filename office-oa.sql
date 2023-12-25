# Host: localhost  (Version 5.7.40)
# Date: 2022-12-27 09:13:18
# Generator: MySQL-Front 6.1  (Build 1.26)

CREATE DATABASE `guigu-oa` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
use `guigu-oa`;
#
# Structure for table "oa_process"
#

CREATE TABLE `oa_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `process_code` varchar(50) NOT NULL DEFAULT '' COMMENT '审批code',
  `user_id` bigint(1) NOT NULL DEFAULT '0' COMMENT '用户id',
  `process_template_id` bigint(20) DEFAULT NULL COMMENT '审批模板id',
  `process_type_id` bigint(20) DEFAULT NULL COMMENT '审批类型id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `form_values` text COMMENT '表单值',
  `process_instance_id` varchar(255) DEFAULT NULL COMMENT '流程实例id',
  `current_auditor` varchar(255) DEFAULT NULL COMMENT '当前审批人',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态（0：默认 1：审批中 2：审批通过 -1：驳回）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='审批类型';

#
# Data for table "oa_process"
#


#
# Structure for table "oa_process_record"
#

CREATE TABLE `oa_process_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `process_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '审批流程id',
  `description` varchar(255) DEFAULT NULL COMMENT '审批描述',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态',
  `operate_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作用户id',
  `operate_user` varchar(20) DEFAULT NULL COMMENT '操作用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='审批记录';

#
# Data for table "oa_process_record"
#

INSERT INTO `oa_process_record` VALUES (1,1,'发起申请',1,4,'李四','2022-12-22 11:15:33','2022-12-22 11:15:33',0);

#
# Structure for table "oa_process_template"
#

CREATE TABLE `oa_process_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '模板名称',
  `icon_url` varchar(100) DEFAULT NULL COMMENT '图标路径',
  `process_type_id` varchar(255) DEFAULT NULL,
  `form_props` text COMMENT '表单属性',
  `form_options` text COMMENT '表单选项',
  `process_definition_key` varchar(20) DEFAULT NULL COMMENT '流程定义key',
  `process_definition_path` varchar(255) DEFAULT NULL COMMENT '流程定义上传路径',
  `process_model_id` varchar(255) DEFAULT NULL COMMENT '流程定义模型id',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(3) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='审批模板';

#
# Data for table "oa_process_template"
#

INSERT INTO `oa_process_template` VALUES (1,'加班','https://gw.alicdn.com/tfs/TB1bHOWCSzqK1RjSZFjXXblCFXa-112-112.png','1','[{\"type\":\"datePicker\",\"field\":\"sdate\",\"title\":\"开始时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"datePicker\",\"field\":\"edate\",\"title\":\"结束时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"day\",\"title\":\"加班天数\",\"info\":\"\",\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"加班原因\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"select\",\"field\":\"dept\",\"title\":\"所属部门\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"技术部\",\"label\":\"技术部\"},{\"value\":\"产品部\",\"label\":\"产品部\"},{\"label\":\"市场部\",\"value\":\"市场部\"},{\"label\":\"人事部\",\"value\":\"人事部\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true}]','{\n    \"form\": {\n        \"labelPosition\": \"right\",\n        \"size\": \"medium\",\n        \"labelWidth\": \"80px\",\n        \"hideRequiredAsterisk\": false,\n        \"showMessage\": true,\n        \"inlineMessage\": false\n    },\n    \"submitBtn\": {\n                         \"innerText\":\"提交审批 \",\n                        \"round\":true,\n                        \"width\": \"280px\",\n                        \"type\":\"primary\"\n                     },\n    \"resetBtn\": false\n}\n','jiaban','','','加班',1,'2022-12-07 14:33:51','2022-12-27 09:12:58',0),(2,'请假','https://gw.alicdn.com/imgextra/i3/O1CN01LLn0YV1LhBXs7T2iO_!!6000000001330-2-tps-120-120.png','2','[{\"type\":\"datePicker\",\"field\":\"sdate\",\"title\":\"开始时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"datePicker\",\"field\":\"edate\",\"title\":\"结束时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"day\",\"title\":\"请假天数\",\"info\":\"\",\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"请假原因\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"select\",\"field\":\"dept\",\"title\":\"所属部门\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"技术部\",\"label\":\"技术部\"},{\"value\":\"产品部\",\"label\":\"产品部\"},{\"label\":\"市场部\",\"value\":\"市场部\"},{\"label\":\"人事部\",\"value\":\"人事部\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true}]','{\n    \"form\": {\n        \"labelPosition\": \"right\",\n        \"size\": \"medium\",\n        \"labelWidth\": \"80px\",\n        \"hideRequiredAsterisk\": false,\n        \"showMessage\": true,\n        \"inlineMessage\": false\n    },\n    \"submitBtn\": {\n                         \"innerText\":\"提交审批 \",\n                        \"round\":true,\n                        \"width\": \"280px\",\n                        \"type\":\"primary\"\n                     },\n    \"resetBtn\": false\n}\n','qingjia','','','请假',0,'2022-12-07 15:04:02','2022-12-27 09:13:00',0),(3,'申请费用','https://gw.alicdn.com/tfs/TB1e76lCOLaK1RjSZFxXXamPFXa-112-112.png','3','[{\"type\":\"datePicker\",\"field\":\"sdate\",\"title\":\"使用时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}],\"props\":{\"format\":\"\"}},{\"type\":\"input\",\"field\":\"amount\",\"title\":\"申请金额\",\"info\":\"\",\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"select\",\"field\":\"F0ma1n7tec860p\",\"title\":\"费用类别\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"房租费\",\"label\":\"房租费\"},{\"value\":\"水费\",\"label\":\"水费\"},{\"label\":\"电费\",\"value\":\"电费\"},{\"label\":\"网络费\",\"value\":\"网络费\"},{\"label\":\"火车票\",\"value\":\"火车票\"},{\"label\":\"飞机票\",\"value\":\"飞机票\"},{\"label\":\"部门团建费\",\"value\":\"部门团建费\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"其他补充\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true},{\"type\":\"select\",\"field\":\"dept\",\"title\":\"所属部门\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"技术部\",\"label\":\"技术部\"},{\"value\":\"产品部\",\"label\":\"产品部\"},{\"label\":\"市场部\",\"value\":\"市场部\"},{\"label\":\"人事部\",\"value\":\"人事部\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true},{\"type\":\"input\",\"field\":\"content\",\"title\":\"申请事由\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"switch\",\"field\":\"F9hn1n7twlkcfk\",\"title\":\"消息提示\",\"info\":\"\",\"_fc_drag_tag\":\"switch\",\"hidden\":false,\"display\":true},{\"type\":\"span\",\"title\":\"注意\",\"native\":false,\"children\":[\"有疑问找云尚小秘哦！\"],\"_fc_drag_tag\":\"span\",\"hidden\":false,\"display\":true}]',' ','feiyong','','','申请费用',0,'2022-12-08 10:35:33','2022-12-26 21:00:01',0);

#
# Structure for table "oa_process_type"
#

CREATE TABLE `oa_process_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '类型名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='审批类型';

#
# Data for table "oa_process_type"
#

INSERT INTO `oa_process_type` VALUES (1,'出勤','出勤','2022-12-06 09:35:12','2022-12-06 09:36:13',0),(2,'人事','人事','2022-12-06 09:35:19','2022-12-06 09:36:16',0),(3,'财务','财务','2022-12-06 09:35:29','2022-12-06 09:36:17',0);


#
# Structure for table "sys_menu"
#

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型(0:目录,1:菜单,2:按钮)',
  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort_value` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

#
# Data for table "sys_menu"
#

INSERT INTO `sys_menu` VALUES (2,0,'系统管理',0,'system','Layout',NULL,'el-icon-s-tools',1,1,'2021-05-31 18:05:37','2022-06-09 09:23:24',0),(3,2,'用户管理',1,'sysUser','system/sysUser/list','','el-icon-s-custom',1,1,'2021-05-31 18:05:37','2022-06-09 09:22:47',0),(4,2,'角色管理',1,'sysRole','system/sysRole/list','','el-icon-user-solid',2,1,'2021-05-31 18:05:37','2022-06-09 09:37:18',0),(5,2,'菜单管理',1,'sysMenu','system/sysMenu/list','','el-icon-s-unfold',3,1,'2021-05-31 18:05:37','2022-06-09 09:37:21',0),(6,3,'查看',2,NULL,NULL,'bnt.sysUser.list',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(7,3,'添加',2,NULL,NULL,'bnt.sysUser.add',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(8,3,'修改',2,NULL,NULL,'bnt.sysUser.update',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(9,3,'删除',2,NULL,NULL,'bnt.sysUser.remove',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(10,4,'查看',2,NULL,NULL,'bnt.sysRole.list',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(11,4,'添加',2,NULL,NULL,'bnt.sysRole.add',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(12,4,'修改',2,NULL,NULL,'bnt.sysRole.update',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(13,4,'删除',2,NULL,NULL,'bnt.sysRole.remove',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(14,5,'查看',2,NULL,NULL,'bnt.sysMenu.list',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(15,5,'添加',2,NULL,NULL,'bnt.sysMenu.add',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(16,5,'修改',2,NULL,NULL,'bnt.sysMenu.update',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(17,5,'删除',2,NULL,NULL,'bnt.sysMenu.remove',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(18,3,'分配角色',2,NULL,NULL,'bnt.sysUser.assignRole',NULL,1,1,'2022-05-23 17:14:32','2022-06-09 09:22:38',0),(19,4,'分配权限',2,'assignAuth','system/sysRole/assignAuth','bnt.sysRole.assignAuth',NULL,1,1,'2022-05-23 17:18:14','2022-06-09 09:22:38',0),(20,2,'部门管理',1,'sysDept','system/sysDept/list','','el-icon-s-operation',4,1,'2022-05-24 10:07:05','2022-06-09 09:38:12',0),(21,20,'查看',2,NULL,NULL,'bnt.sysDept.list',NULL,1,1,'2022-05-24 10:07:44','2022-06-09 09:22:38',0),(22,2,'岗位管理',1,'sysPost','system/sysPost/list','','el-icon-more-outline',5,1,'2022-05-24 10:25:30','2022-06-09 09:38:13',0),(23,22,'查看',2,NULL,NULL,'bnt.sysPost.list',NULL,1,1,'2022-05-24 10:25:45','2022-06-09 09:22:38',0),(24,20,'添加',2,NULL,NULL,'bnt.sysDept.add',NULL,1,1,'2022-05-25 15:31:27','2022-06-09 09:22:38',0),(25,20,'修改',2,NULL,NULL,'bnt.sysDept.update',NULL,1,1,'2022-05-25 15:31:41','2022-06-09 09:22:38',0),(26,20,'删除',2,NULL,NULL,'bnt.sysDept.remove',NULL,1,1,'2022-05-25 15:31:59','2022-06-09 09:22:38',0),(27,22,'添加',2,NULL,NULL,'bnt.sysPost.add',NULL,1,1,'2022-05-25 15:32:44','2022-06-09 09:22:38',0),(28,22,'修改',2,NULL,NULL,'bnt.sysPost.update',NULL,1,1,'2022-05-25 15:32:58','2022-06-09 09:22:38',0),(29,22,'删除',2,NULL,NULL,'bnt.sysPost.remove',NULL,1,1,'2022-05-25 15:33:11','2022-06-09 09:22:38',0),(30,34,'操作日志',1,'sysOperLog','system/sysOperLog/list','','el-icon-document-remove',7,1,'2022-05-26 16:09:59','2022-06-09 09:39:23',0),(31,30,'查看',2,NULL,NULL,'bnt.sysOperLog.list',NULL,1,1,'2022-05-26 16:10:17','2022-06-09 09:22:38',0),(32,34,'登录日志',1,'sysLoginLog','system/sysLoginLog/list','','el-icon-s-goods',8,1,'2022-05-26 16:36:13','2022-06-09 09:39:24',0),(33,32,'查看',2,NULL,NULL,'bnt.sysLoginLog.list',NULL,1,1,'2022-05-26 16:36:31','2022-06-09 09:36:36',0),(34,2,'日志管理',0,'log','ParentView','','el-icon-tickets',6,1,'2022-05-31 13:23:07','2022-06-09 09:39:22',0),(35,0,'审批设置',0,'processSet','Layout','','el-icon-setting',1,1,'2022-12-01 09:32:46','2022-12-01 09:32:46',0),(36,35,'审批模板',1,'processTemplate','processSet/processTemplate/list','','el-icon-s-help',2,1,'2022-12-01 09:37:08','2022-12-19 14:10:48',0),(37,36,'查看',2,'','','bnt.processTemplate.list','',1,1,'2022-12-01 09:37:49','2022-12-01 09:37:49',0),(38,36,'审批模板设置',2,'templateSet','processSet/processTemplate/templateSet','bnt.processTemplate.templateSet','',1,1,'2022-12-01 14:52:08','2022-12-13 18:11:56',0),(39,35,'审批类型',1,'processType','processSet/processType/list','','el-icon-s-unfold',1,1,'2022-12-02 14:46:18','2022-12-13 18:12:24',0),(40,39,'查看',2,'','','bnt.processType.list','',1,1,'2022-12-02 14:46:41','2022-12-02 14:46:41',0),(41,0,'审批管理',0,'processMgr','Layout','','el-icon-more-outline',1,1,'2022-12-02 14:48:11','2022-12-20 09:29:30',0),(42,41,'审批列表',1,'process','processMgr/process/list','','el-icon-document-remove',1,1,'2022-12-02 14:49:06','2022-12-02 14:59:17',0),(43,42,'查看',2,'','','bnt.process.list','',1,1,'2022-12-02 14:49:24','2022-12-02 14:49:24',0),(44,36,'在线流程设置',2,'onlineProcessSet','processSet/processTemplate/onlineProcessSet','bnt.processTemplate.onlineProcessSet','',1,1,'2022-12-08 10:13:15','2022-12-19 18:57:35',0),(45,39,'添加',2,'','','bnt.processType.add','',1,1,'2022-12-09 09:14:53','2022-12-09 09:14:53',0),(46,39,'修改',2,'','','bnt.processType.update','',1,1,'2022-12-09 09:15:10','2022-12-09 09:15:10',0),(47,39,'删除',2,'','','bnt.processType.remove','',1,1,'2022-12-09 09:15:25','2022-12-09 09:15:25',0),(48,36,'删除',2,'','','bnt.processTemplate.remove','',1,1,'2022-12-09 09:22:29','2022-12-09 09:22:29',0),(49,36,'发布',2,'','','bnt.processTemplate.publish','',1,1,'2022-12-09 09:24:47','2022-12-09 09:24:47',0),(50,0,'公众号菜单',0,'wechat','Layout','','el-icon-s-operation',1,1,'2022-12-13 09:06:58','2022-12-21 11:20:55',0),(51,50,'菜单列表',1,'menu','wechat/menu/list','','el-icon-s-help',1,1,'2022-12-13 09:07:52','2022-12-13 09:09:49',0),(52,51,'查看',2,'','','bnt.menu.list','',1,1,'2022-12-13 09:08:48','2022-12-13 17:58:23',0),(53,51,'添加',2,'','','bnt.menu.add','',1,1,'2022-12-13 16:29:25','2022-12-13 17:58:34',0),(54,51,'修改',2,'','','bnt.menu.update','',1,1,'2022-12-13 16:29:41','2022-12-13 17:58:42',0),(55,51,'删除',2,'','','bnt.menu.remove','',1,1,'2022-12-13 16:29:59','2022-12-13 17:58:47',0),(56,51,'删除微信菜单',2,'','','bnt.menu.removeMenu','',1,1,'2022-12-13 16:30:36','2022-12-13 17:58:54',0),(57,51,'同步微信菜单',2,'','','bnt.menu.syncMenu','',1,1,'2022-12-13 16:31:00','2022-12-13 17:59:01',0);




#
# Structure for table "sys_role"
#

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色';

#
# Data for table "sys_role"
#

INSERT INTO `sys_role` VALUES (1,'系统管理员','SYSTEM','系统管理员','2021-05-31 18:09:18','2022-06-08 09:21:10',0),(2,'普通管理员','COMMON','普通管理员','2021-06-01 08:38:40','2022-02-24 10:42:46',0),(8,'用户管理员','yhgly',NULL,'2022-06-08 17:39:04','2022-06-08 17:39:04',0);

#
# Structure for table "sys_role_menu"
#

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  `menu_id` bigint(11) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='角色菜单';

#
# Data for table "sys_role_menu"
#

INSERT INTO `sys_role_menu` VALUES (1,2,2,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(2,2,3,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(3,2,6,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(4,2,7,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(5,2,8,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(6,2,9,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(7,2,18,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(8,2,4,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(9,2,10,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(10,2,11,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(11,2,12,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(12,2,13,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(13,2,19,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(14,2,5,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(15,2,14,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(16,2,15,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(17,2,16,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(18,2,17,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(19,2,2,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(20,2,3,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(21,2,6,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(22,2,7,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(23,2,8,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(24,2,2,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(25,2,3,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(26,2,6,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(27,2,7,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(28,2,8,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(29,2,5,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(30,2,14,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(31,2,20,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(32,2,21,'2022-06-09 09:26:34','2022-06-09 09:26:34',0);

#
# Structure for table "sys_user"
#

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `head_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `post_id` bigint(20) DEFAULT NULL COMMENT '岗位id',
  `open_id` varchar(255) DEFAULT NULL COMMENT '微信openId',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态（1：正常 0：停用）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

#
# Data for table "sys_user"
#

INSERT INTO `sys_user` VALUES (1,'admin','96e79218965eb72c92a549dd5a330112','admin','15000000000','http://r61cnlsfq.hn-bkt.clouddn.com/7daa4595-dfde-45da-8513-c5c2b81d20cc',1022,NULL,'',NULL,1,'2021-05-31 18:08:43','2022-12-13 14:52:31',0),(2,'wjl','96e79218965eb72c92a549dd5a330112','王经理','15000000002','http://r61cnlsfq.hn-bkt.clouddn.com/b09b3467-3d99-437a-bd2e-dd8c9be92bb8',1022,6,'',NULL,1,'2022-02-08 10:35:38','2022-12-22 10:05:03',0),(3,'lrsjl','96e79218965eb72c92a549dd5a330112','李人事经理','15000000004',NULL,2018,5,'',NULL,1,'2022-05-24 11:05:40','2022-12-22 10:05:21',0),(4,'lisi','96e79218965eb72c92a549dd5a330112','李四','15000000001',NULL,1021,10,'omwf25izKON9dktgoy0dogqvnGhk',NULL,1,'2022-12-06 09:32:31','2022-12-21 09:25:06',0),(5,'zzjl','96e79218965eb72c92a549dd5a330112','张总经理','15000000003',NULL,10,8,'',NULL,1,'2022-12-07 16:47:00','2022-12-22 10:05:07',0),(6,'xkfzr','96e79218965eb72c92a549dd5a330112','张学科','15000000005',NULL,1010,11,NULL,NULL,1,'2022-12-14 09:18:12','2022-12-14 09:18:12',0),(7,'zhangsan','96e79218965eb72c92a549dd5a330112','张三','15000000006',NULL,1021,10,NULL,NULL,1,'2022-12-26 11:26:45','2022-12-27 09:11:37',0),(11,'zhangsan01','96e79218965eb72c92a549dd5a330112','张三01','15000000007',NULL,1021,10,NULL,NULL,1,'2022-12-26 11:27:33','2022-12-27 09:11:38',0),(12,'zhangsan02','96e79218965eb72c92a549dd5a330112','张三02','15000000008',NULL,1021,10,NULL,NULL,1,'2022-12-26 11:27:39','2022-12-27 09:11:39',0);

#
# Structure for table "sys_user_role"
#

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_admin_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户角色';

#
# Data for table "sys_user_role"
#

INSERT INTO `sys_user_role` VALUES (2,2,2,'2022-01-20 20:49:37','2022-02-24 10:43:07',0),(3,1,1,'2022-05-19 10:37:27','2022-05-24 16:55:53',1),(4,2,1,'2022-05-19 10:37:27','2022-05-24 16:55:53',1),(5,1,1,'2022-05-24 16:55:53','2022-05-24 16:55:53',0),(6,2,3,'2022-05-25 16:09:31','2022-05-25 16:09:31',0),(7,2,4,'2022-06-02 11:08:14','2022-06-02 11:15:36',1),(8,2,4,'2022-06-02 11:15:36','2022-06-02 16:10:53',1),(9,1,4,'2022-06-02 11:15:36','2022-06-02 16:10:53',1),(10,1,4,'2022-06-02 16:10:53','2022-06-02 16:10:53',0);

