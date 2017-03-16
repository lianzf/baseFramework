# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 192.168.20.15 (MySQL 5.5.40-36.1-log)
# Database: flow_management
# Generation Time: 2017-03-14 12:11:18 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_sys_resources
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sys_resources`;

CREATE TABLE `t_sys_resources` (
  `RESOURCE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `RESOURCE_NAME` varchar(30) NOT NULL COMMENT '资源名称',
  `RESOURCE_DESC` varchar(200) DEFAULT NULL COMMENT '描述',
  `RESOURCE_TYPE` varchar(200) DEFAULT NULL COMMENT 'url、method、code',
  `RESOURCE_STRING` varchar(200) DEFAULT NULL COMMENT '资源串',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '即排序?暂不用，保留',
  `MODULE` varchar(4) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_sys_resources` WRITE;
/*!40000 ALTER TABLE `t_sys_resources` DISABLE KEYS */;

INSERT INTO `t_sys_resources` (`RESOURCE_ID`, `RESOURCE_NAME`, `RESOURCE_DESC`, `RESOURCE_TYPE`, `RESOURCE_STRING`, `PRIORITY`, `MODULE`, `PARENT_ID`)
VALUES
	(1,'权限管理','权限管理','1','AUTHMANAGE',NULL,NULL,0),
	(2,'数据字典','数据字典','1','/dictionary/getPage',NULL,NULL,1),
	(3,'用户管理','用户管理','1','/sysUser/getPage',NULL,NULL,1),
	(4,'角色管理','角色管理','1','/sysRoles/getPage',NULL,NULL,1),
	(5,'资源管理','资源管理','1','/sysRes/getPage',NULL,NULL,1),
	(6,'aaaa','aaaaa','1','/dictionary/getPage',NULL,NULL,1);

/*!40000 ALTER TABLE `t_sys_resources` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_sys_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sys_roles`;

CREATE TABLE `t_sys_roles` (
  `ROLES_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(30) NOT NULL COMMENT '名称',
  `ROLE_DESC` varchar(200) DEFAULT NULL COMMENT '描述',
  `MODULE` varchar(4) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ROLES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_sys_roles` WRITE;
/*!40000 ALTER TABLE `t_sys_roles` DISABLE KEYS */;

INSERT INTO `t_sys_roles` (`ROLES_ID`, `ROLE_NAME`, `ROLE_DESC`, `MODULE`, `PARENT_ID`)
VALUES
	(9,'ROLE_DEVELOP','开发人员',NULL,NULL),
	(10,'ROLE_PROD','产品人员',NULL,NULL),
	(11,'ROLE_COMPANY','合作方',NULL,NULL),
	(12,'ROLE_ADMIN','系统管理员',NULL,NULL);

/*!40000 ALTER TABLE `t_sys_roles` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_sys_roles_res
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sys_roles_res`;

CREATE TABLE `t_sys_roles_res` (
  `ROLES_RES_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色资源ID',
  `resources_ID` int(30) NOT NULL COMMENT '资源ID',
  `ROLES_ID` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ROLES_RES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_sys_roles_res` WRITE;
/*!40000 ALTER TABLE `t_sys_roles_res` DISABLE KEYS */;

INSERT INTO `t_sys_roles_res` (`ROLES_RES_ID`, `resources_ID`, `ROLES_ID`)
VALUES
	(2,1,11),
	(3,2,11),
	(4,3,11),
	(5,4,12),
	(6,5,11);

/*!40000 ALTER TABLE `t_sys_roles_res` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_sys_users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sys_users`;

CREATE TABLE `t_sys_users` (
  `USERS_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USER_ACCOUNT` varchar(30) NOT NULL COMMENT '用户账号',
  `USER_NAME` varchar(40) NOT NULL COMMENT '用户名称',
  `USER_PASSWORD` varchar(40) NOT NULL COMMENT '密码',
  `USER_DESC` varchar(200) DEFAULT NULL COMMENT '描述',
  `ENABLED` int(2) DEFAULT NULL COMMENT '0禁用1正常',
  `is_delete` int(11) DEFAULT '0',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `LOGIN_TIME` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  PRIMARY KEY (`USERS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_sys_users` WRITE;
/*!40000 ALTER TABLE `t_sys_users` DISABLE KEYS */;

INSERT INTO `t_sys_users` (`USERS_ID`, `USER_ACCOUNT`, `USER_NAME`, `USER_PASSWORD`, `USER_DESC`, `ENABLED`, `is_delete`, `CREATE_TIME`, `LOGIN_TIME`)
VALUES
	(1,'admin','admin','21232f297a57a5a743894a0e4a801fc3','系统管理员',1,0,NULL,'2017-03-14 20:05:42'),
	(2,'test','test','21232f297a57a5a743894a0e4a801fc3','测试',1,0,NULL,'2017-03-14 19:53:50');

/*!40000 ALTER TABLE `t_sys_users` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_sys_users_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sys_users_roles`;

CREATE TABLE `t_sys_users_roles` (
  `USERS_ROLES_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `USERS_ID` int(30) NOT NULL COMMENT '用户ID',
  `ROLES_ID` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`USERS_ROLES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_sys_users_roles` WRITE;
/*!40000 ALTER TABLE `t_sys_users_roles` DISABLE KEYS */;

INSERT INTO `t_sys_users_roles` (`USERS_ROLES_ID`, `USERS_ID`, `ROLES_ID`)
VALUES
	(1,1,12),
	(2,2,11);

/*!40000 ALTER TABLE `t_sys_users_roles` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
