/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.7.14 : Database - jsp_bbs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jsp_bbs` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jsp_bbs`;

/*Table structure for table `bbs_permission` */

DROP TABLE IF EXISTS `bbs_permission`;

CREATE TABLE `bbs_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '权限名',
  `code` varchar(45) NOT NULL COMMENT '权限编码',
  `url` varchar(128) NOT NULL COMMENT '权限路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `url_UNIQUE` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `bbs_permission` */

insert  into `bbs_permission`(`id`,`name`,`code`,`url`) values (1,'首页','BBS_INDEX','admin'),(2,'版块管理','BBS_SECTION','admin/section'),(3,'用户管理','BBS_USER','admin/user'),(4,'帖子管理','BBS_TOPIC','admin/topic'),(5,'角色管理','BBS_ROLE','admin/role'),(6,'权限管理','BBS_PERMISSION','admin/permission');

/*Table structure for table `bbs_reply` */

DROP TABLE IF EXISTS `bbs_reply`;

CREATE TABLE `bbs_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL COMMENT '回帖内容',
  `topicID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `pid` int(11) DEFAULT NULL,
  `createTime` datetime NOT NULL COMMENT '回帖时间',
  `level` int(11) NOT NULL COMMENT '所在楼层',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_bbs_reply_bbs_topic1_idx` (`topicID`),
  KEY `fk_bbs_reply_bbs_user1_idx` (`userID`),
  KEY `fk_bbs_reply_bbs_reply1_idx` (`pid`),
  CONSTRAINT `fk_bbs_reply_bbs_reply1` FOREIGN KEY (`pid`) REFERENCES `bbs_reply` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_bbs_reply_bbs_topic1` FOREIGN KEY (`topicID`) REFERENCES `bbs_topic` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_bbs_reply_bbs_user1` FOREIGN KEY (`userID`) REFERENCES `bbs_user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `bbs_reply` */

insert  into `bbs_reply`(`id`,`content`,`topicID`,`userID`,`pid`,`createTime`,`level`) values (1,'灌灌灌灌',5,1,0,'2017-11-12 21:43:33',1),(2,'的撒旦实打实',4,1,0,'2017-11-12 21:47:33',1),(5,'回复 1 楼：现场协助协助协助操作',4,2,2,'2017-11-12 22:32:14',2),(8,'的撒旦撒发撒飞洒是',4,1,0,'2017-11-20 10:36:01',3),(9,'回复 3 楼：出席这次大赛的',4,1,8,'2017-11-20 10:36:12',4);

/*Table structure for table `bbs_role` */

DROP TABLE IF EXISTS `bbs_role`;

CREATE TABLE `bbs_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '角色名',
  `code` varchar(45) NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `bbs_role` */

insert  into `bbs_role`(`id`,`name`,`code`) values (1,'超级管理员','SUPER_ADMIN'),(3,'管理员','ADMIN');

/*Table structure for table `bbs_role_permission` */

DROP TABLE IF EXISTS `bbs_role_permission`;

CREATE TABLE `bbs_role_permission` (
  `roleID` int(11) NOT NULL,
  `permissionID` int(11) NOT NULL,
  PRIMARY KEY (`roleID`,`permissionID`),
  KEY `fk_bbs_role_has_bbs_permission_bbs_permission1_idx` (`permissionID`),
  KEY `fk_bbs_role_has_bbs_permission_bbs_role1_idx` (`roleID`),
  CONSTRAINT `fk_bbs_role_has_bbs_permission_bbs_permission1` FOREIGN KEY (`permissionID`) REFERENCES `bbs_permission` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_bbs_role_has_bbs_permission_bbs_role1` FOREIGN KEY (`roleID`) REFERENCES `bbs_role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bbs_role_permission` */

insert  into `bbs_role_permission`(`roleID`,`permissionID`) values (1,1),(3,1),(1,2),(1,3),(1,4),(3,4),(1,5),(1,6);

/*Table structure for table `bbs_section` */

DROP TABLE IF EXISTS `bbs_section`;

CREATE TABLE `bbs_section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '版块名',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `masterID` int(11) DEFAULT NULL COMMENT '版主ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_bbs_setion_bbs_user1_idx` (`masterID`),
  CONSTRAINT `fk_bbs_setion_bbs_user1` FOREIGN KEY (`masterID`) REFERENCES `bbs_user` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `bbs_section` */

insert  into `bbs_section`(`id`,`name`,`createTime`,`masterID`) values (3,'经验交流','2017-11-08 07:56:25',2),(4,'闲聊灌水','2017-11-09 21:20:14',1),(6,'资源共享','2017-11-19 00:00:00',2),(7,'悬赏问答','2017-11-19 00:00:00',2);

/*Table structure for table `bbs_topic` */

DROP TABLE IF EXISTS `bbs_topic`;

CREATE TABLE `bbs_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL COMMENT '帖子标题',
  `content` longtext NOT NULL COMMENT '帖子内容',
  `viewCount` int(11) NOT NULL DEFAULT '0' COMMENT '查看次数',
  `createTime` datetime NOT NULL COMMENT '发帖时间',
  `authorID` int(11) NOT NULL COMMENT '作者（发帖用户）',
  `sectionID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bbs_topic_bbs_user_idx` (`authorID`),
  KEY `fk_bbs_topic_bbs_section1_idx` (`sectionID`),
  CONSTRAINT `fk_bbs_topic_bbs_section1` FOREIGN KEY (`sectionID`) REFERENCES `bbs_section` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_bbs_topic_bbs_user` FOREIGN KEY (`authorID`) REFERENCES `bbs_user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `bbs_topic` */

insert  into `bbs_topic`(`id`,`title`,`content`,`viewCount`,`createTime`,`authorID`,`sectionID`) values (1,'测试','大苏打撒大苏打撒',7,'2017-11-08 08:00:45',1,3),(3,'经验','经验经验',4,'2017-11-11 00:41:27',2,3),(4,'哈哈哈','哈哈哈',11,'2017-11-11 00:47:14',1,4),(5,'帆帆帆帆帆帆帆帆','帆帆帆帆帆帆帆帆',1,'2017-11-11 00:49:11',1,3),(6,'v都是发给第三方的收购大股东是公司的','不错v保存在此次v',2,'2017-11-20 10:42:53',2,7);

/*Table structure for table `bbs_user` */

DROP TABLE IF EXISTS `bbs_user`;

CREATE TABLE `bbs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `regTime` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `bbs_user` */

insert  into `bbs_user`(`id`,`username`,`password`,`regTime`) values (1,'admin','123','2017-11-08 07:56:02'),(2,'123','123','2017-11-10 12:13:23'),(3,'456','123','2017-11-20 10:42:15');

/*Table structure for table `bbs_user_role` */

DROP TABLE IF EXISTS `bbs_user_role`;

CREATE TABLE `bbs_user_role` (
  `userID` int(11) NOT NULL,
  `roleID` int(11) NOT NULL,
  PRIMARY KEY (`userID`,`roleID`),
  KEY `fk_bbs_user_has_bbs_role_bbs_role1_idx` (`roleID`),
  KEY `fk_bbs_user_has_bbs_role_bbs_user1_idx` (`userID`),
  CONSTRAINT `fk_bbs_user_has_bbs_role_bbs_role1` FOREIGN KEY (`roleID`) REFERENCES `bbs_role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_bbs_user_has_bbs_role_bbs_user1` FOREIGN KEY (`userID`) REFERENCES `bbs_user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bbs_user_role` */

insert  into `bbs_user_role`(`userID`,`roleID`) values (1,1),(2,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
