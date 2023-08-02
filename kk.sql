/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.30 : Database - supermarket
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`supermarket` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `supermarket`;

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goodsName` varchar(32) NOT NULL COMMENT '商品名称',
  `goodsDesc` varchar(255) DEFAULT NULL COMMENT '商品描述/备注',
  `productTime` date DEFAULT NULL COMMENT '生产日期',
  `shelfLife` int DEFAULT NULL COMMENT '保质期',
  `typeId` int DEFAULT NULL COMMENT '商品类别',
  `expirationTime` int DEFAULT NULL COMMENT '临近过期时间',
  `state` int DEFAULT '0' COMMENT '状态',
  `price` varchar(32) NOT NULL COMMENT '价钱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb3;

/*Data for the table `goods` */

insert  into `goods`(`id`,`goodsName`,`goodsDesc`,`productTime`,`shelfLife`,`typeId`,`expirationTime`,`state`,`price`) values (1,'苹果','苹果','2022-11-14',15,1,NULL,1,'50'),(2,'椰子','椰子','2022-11-20',20,1,NULL,0,'100'),(4,'相机','数码','2022-11-20',1000,4,NULL,0,'100'),(6,'薯片','食品','2022-11-20',30,5,NULL,0,'100'),(7,'饮水机','家电','2022-11-20',1000,3,NULL,0,'100'),(8,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(12,'相机','数码','2022-11-20',1000,4,NULL,0,'100'),(14,'手机','数码','2022-11-20',2000,4,NULL,0,'100'),(15,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(16,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(17,'牛肉','牛肉','2022-11-20',15,5,NULL,0,'100'),(18,'羊肉','羊肉','2022-11-20',15,5,NULL,0,'100'),(19,'牛肉','牛肉','2022-11-20',15,5,NULL,0,'100'),(20,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(25,'纸巾','纸巾','2022-11-20',300,2,NULL,0,'100'),(26,'湿纸巾','湿纸巾','2022-11-20',300,2,NULL,0,'100'),(27,'臭干子','吃的','2022-11-20',30,5,NULL,0,'100'),(29,'牛肉干','牛肉干','2022-11-20',25,5,NULL,0,'100'),(30,'苹果','好得一','2022-11-20',30,1,NULL,0,'100'),(31,'苹果','ok','2022-11-20',30,1,NULL,0,'100'),(32,'苹果','ok','2022-11-20',31,1,NULL,0,'100'),(33,'aaa','5295','2022-11-20',111,6,NULL,0,'100'),(34,'aaa','11231231','2022-11-20',100,6,NULL,0,'100'),(35,'aaa','5295','2022-11-20',111,6,NULL,0,'100'),(36,'aaa','11231231','2022-11-20',100,6,NULL,0,'100'),(41,'苹果','苹果','2022-11-20',300,1,NULL,0,'100'),(42,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(43,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(44,'牛肉','牛肉','2022-11-20',15,5,NULL,0,'100'),(45,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(46,'苹果','苹果','2022-11-20',15,1,NULL,0,'100'),(47,'牛肉','牛肉','2022-11-20',15,5,NULL,0,'100'),(48,'鼠标','鼠标','2022-11-20',1000,4,NULL,0,'100'),(49,'鼠标','鼠标','2022-11-20',1000,4,NULL,0,'100'),(50,'电脑','发顺丰','2022-11-20',20000,4,NULL,0,'100'),(51,'华为手机','发顺丰','2022-11-20',30,4,NULL,0,'100'),(52,'牛肉','牛肉','2022-11-20',15,5,NULL,0,'100'),(53,'梨子','梨子','2022-11-20',10,1,NULL,1,'50'),(54,'华为手机','手机','2022-11-20',30,4,NULL,0,'100'),(76,'llx','菜逼','2022-11-20',1,6,0,1,'100');

/*Table structure for table `goods_type` */

DROP TABLE IF EXISTS `goods_type`;

CREATE TABLE `goods_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(32) NOT NULL COMMENT '商品类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

/*Data for the table `goods_type` */

insert  into `goods_type`(`id`,`name`) values (1,'生鲜水果'),(2,'生活用品'),(3,'家电'),(4,'数码'),(5,'食品'),(6,'其他');

/*Table structure for table `login_record` */

DROP TABLE IF EXISTS `login_record`;

CREATE TABLE `login_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `usercode` varchar(32) DEFAULT NULL COMMENT '用户账号',
  `username` varchar(32) NOT NULL COMMENT '用户姓名',
  `roleName` varchar(32) NOT NULL COMMENT '角色名',
  `loginTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

/*Data for the table `login_record` */

insert  into `login_record`(`id`,`usercode`,`username`,`roleName`,`loginTime`) values (1,'032002414','李志取','老板',NULL),(2,'032002414','李志取','老板','2022-11-27 09:58:43'),(3,'032002417','林卢希','客户经理','2022-11-27 10:28:28'),(4,'032002414','李志取','老板','2022-11-27 10:39:10'),(5,'032002417','林卢希','客户经理','2022-11-28 16:01:54');

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `goodsName` varchar(32) NOT NULL,
  `goodsAmount` int NOT NULL,
  `time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` int NOT NULL COMMENT '编号',
  `permissionName` varchar(32) DEFAULT NULL COMMENT '权限名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

/*Data for the table `permission` */

insert  into `permission`(`id`,`code`,`permissionName`) values (1,1,'用户管理'),(2,2,'商品管理'),(3,3,'出库管理'),(4,4,'入库管理');

/*Table structure for table `persistent_logins` */

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `persistent_logins` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rolename` varchar(32) NOT NULL COMMENT '角色名',
  `salary` int NOT NULL COMMENT '默认薪水',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;

/*Data for the table `role` */

insert  into `role`(`id`,`rolename`,`salary`) values (1,'老板',36000),(2,'客户经理',22000),(3,'销售经理',22000),(4,'品牌主管',16000),(5,'销售主管',16000),(6,'员工',8000),(7,'保洁阿姨',5000);

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `roleId` int NOT NULL COMMENT '角色id',
  `permissionId` int NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3;

/*Data for the table `role_permission` */

insert  into `role_permission`(`id`,`roleId`,`permissionId`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4),(6,3,1),(7,4,1),(8,5,1),(9,6,1),(11,2,1),(31,31,3),(32,31,4),(33,31,1),(36,7,3),(37,32,2);

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goodsId` int NOT NULL COMMENT '商品id',
  `goodsAmount` int DEFAULT NULL COMMENT '商品当前库存量',
  `standardAmount` int DEFAULT NULL COMMENT '商品标准库存量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;

/*Data for the table `stock` */

insert  into `stock`(`id`,`goodsId`,`goodsAmount`,`standardAmount`) values (1,1,-100,500),(4,4,90,2000),(6,6,0,2500),(7,7,0,1500),(8,17,6346,2500),(9,18,0,2500),(10,25,0,1000),(11,26,0,1000),(12,27,0,2500),(13,29,0,2500),(14,33,141,3000),(16,48,0,2000),(17,50,0,2000),(18,54,200,2000),(19,1,0,0),(20,2,0,0),(21,2,0,0),(26,0,200,NULL),(27,0,200,NULL),(28,0,200,NULL),(29,0,200,NULL),(30,0,200,NULL),(31,0,200,NULL),(32,1,NULL,NULL),(33,6,NULL,NULL);

/*Table structure for table `stock_change` */

DROP TABLE IF EXISTS `stock_change`;

CREATE TABLE `stock_change` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goodsId` int NOT NULL COMMENT '商品ID',
  `amount` int NOT NULL COMMENT '数量',
  `time` date DEFAULT NULL COMMENT '时间',
  `type` int NOT NULL COMMENT '0:出库1：入库',
  `userId` int NOT NULL COMMENT '库存操作操作人员id',
  `state` int DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;

/*Data for the table `stock_change` */

insert  into `stock_change`(`id`,`goodsId`,`amount`,`time`,`type`,`userId`,`state`) values (1,8,1000,'2020-01-01',1,1,1),(2,9,70,'2020-01-05',0,1,1),(3,10,1000,'2020-01-02',1,1,1),(4,11,100,'2020-02-01',1,1,1),(5,12,10,'2020-07-15',0,1,1),(6,13,10,'2020-07-21',1,10,1),(7,14,5,'2020-07-21',0,1,0),(8,15,10,'2020-07-15',1,1,1),(9,16,10,'2020-07-20',1,1,0),(10,19,100,'2022-10-22',1,1,0),(11,20,50,'2022-10-22',0,1,0),(12,21,50,'2022-10-22',0,1,1),(13,22,50,'2022-10-22',0,1,1),(14,23,500,'2022-10-22',0,1,1),(15,24,500,'2022-11-05',0,1,1),(16,35,111,'2022-11-05',1,1,0),(17,37,30,'2022-11-05',1,10,1),(18,42,1000,'2022-11-08',1,1,0),(19,43,1000,'2022-11-05',1,1,0),(20,44,6546,'2022-11-08',1,1,1),(21,46,200,'2022-11-08',0,1,0),(22,47,500,'2022-11-08',0,1,0),(23,51,200,'2022-10-22',1,1,0),(24,52,200,'2022-11-05',1,9,0),(25,53,500,'2022-11-08',1,10,0),(31,1,200,'2022-11-08',1,1,1),(32,74,200,'2022-11-08',1,1,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `usercode` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户账号',
  `username` varchar(32) NOT NULL COMMENT '用户姓名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '123456' COMMENT '用户密码',
  `roleId` int NOT NULL COMMENT '角色id',
  `salary` int NOT NULL COMMENT '薪水',
  `headPic` varchar(255) DEFAULT NULL COMMENT '头像描述',
  `state` int DEFAULT '0' COMMENT '是否离职0：未离职1：已离职',
  `tel` varchar(11) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user`(`id`,`usercode`,`username`,`password`,`roleId`,`salary`,`headPic`,`state`,`tel`,`email`,`address`) values (1,'032002414','李志取','$2a$10$nRiCpZPbO8M1MFY7ZCeEp.cCwm7TTOO7DEIBCvj3Sebw1xXmn9W2K',1,1000,'1',0,'18359597989','2207750450@qq.com',NULL),(2,'032002417','林卢希','$2a$10$nRiCpZPbO8M1MFY7ZCeEp.cCwm7TTOO7DEIBCvj3Sebw1xXmn9W2K',2,1000,NULL,0,NULL,'2207750450@qq.com',NULL),(3,'032002438','詹峻','$2a$10$nRiCpZPbO8M1MFY7ZCeEp.cCwm7TTOO7DEIBCvj3Sebw1xXmn9W2K',3,1000,NULL,0,NULL,'2207750450@qq.com',NULL),(14,'123','123123','$2a$10$nRiCpZPbO8M1MFY7ZCeEp.cCwm7TTOO7DEIBCvj3Sebw1xXmn9W2K',7,12322,NULL,0,NULL,'2207750450@qq.com',NULL),(15,'11','11','$2a$10$nRiCpZPbO8M1MFY7ZCeEp.cCwm7TTOO7DEIBCvj3Sebw1xXmn9W2K',7,11,NULL,0,NULL,'2207750450@qq.com',NULL),(16,'1','1','$2a$10$nRiCpZPbO8M1MFY7ZCeEp.cCwm7TTOO7DEIBCvj3Sebw1xXmn9W2K',7,0,NULL,0,NULL,'2207750450@qq.com',NULL),(17,'111','余华生','$2a$10$nRiCpZPbO8M1MFY7ZCeEp.cCwm7TTOO7DEIBCvj3Sebw1xXmn9W2K',1,225891,NULL,0,NULL,'2207750450@qq.com',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
