```sql
CREATE DATABASE supermarket;
USE `supermarket`;

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goodsName` VARCHAR(32) NOT NULL COMMENT '商品名称',
  `goodsDesc` VARCHAR(255) DEFAULT NULL COMMENT '商品描述/备注',
  `productTime` DATE DEFAULT NULL COMMENT '生产日期',
  `shelfLife` INT(11) DEFAULT NULL  COMMENT '保质期',
  `typeId` INT(11) DEFAULT NULL COMMENT '商品类别',
  `expirationTime` INT(11) DEFAULT NULL COMMENT '临近过期时间',
  `state` INT(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

insert  into `goods`(`id`,`goodsName`,`goodsDesc`,`productTime`,`shelfLife`,`typeId`,`expirationTime`,`state`) values (1,'苹果','苹果','2020-01-01',15,1,NULL,1),(2,'椰子','椰子','2020-01-01',20,1,NULL,0),(3,'梨子','梨子','2020-01-01',10,1,NULL,0),(4,'相机','数码','2020-01-01',1000,4,NULL,0),(5,'手机','数码','2020-01-01',2000,4,NULL,0),(6,'薯片','食品','2020-01-01',30,5,NULL,0),(7,'饮水机','家电','2020-01-01',1000,3,NULL,0),(8,'苹果','苹果','2020-01-01',15,1,NULL,0),(9,'苹果','苹果','2020-01-01',15,1,NULL,0),(11,'相机','数码','2020-01-01',1000,4,NULL,0),(12,'相机','数码','2020-01-01',1000,4,NULL,0),(13,'手机','数码','2020-01-01',2000,4,NULL,0),(14,'手机','数码','2020-01-01',2000,4,NULL,0),(15,'苹果','苹果','2020-07-14',15,1,NULL,0),(16,'苹果','苹果','2020-07-19',15,1,NULL,0),(17,'牛肉','牛肉','2020-07-19',15,5,NULL,0),(18,'羊肉','羊肉','2020-07-19',15,5,NULL,0),(19,'牛肉','牛肉','2020-07-01',15,5,NULL,0),(20,'苹果','苹果','2020-01-01',15,1,NULL,0),(25,'纸巾','纸巾','2020-02-03',300,2,NULL,0),(26,'湿纸巾','湿纸巾','2020-07-05',300,2,NULL,0),(27,'臭干子','吃的','2020-03-02',30,5,NULL,0),(28,'苹果','好','2020-01-01',30,1,NULL,0),(29,'牛肉干','牛肉干','2020-02-02',25,5,NULL,0),(30,'苹果','好得一','2020-01-01',30,1,NULL,0),(31,'苹果','ok','2020-01-02',30,1,NULL,0),(32,'苹果','ok','2020-01-02',31,1,NULL,0),(33,'aaa','5295','2010-01-01',111,6,NULL,1),(34,'aaa','11231231','2020-02-02',100,6,NULL,1),(35,'aaa','5295','2020-02-03',111,6,NULL,1),(36,'aaa','11231231','2020-02-02',100,6,NULL,1),(37,'aaa','5295','2020-02-02',111,6,NULL,0),(38,'aaa','afdsf','2020-01-02',1231,2,NULL,0),(39,'ab','asda','2020-02-02',12,6,NULL,0),(40,'苹果','苹果','2020-01-04',300,1,NULL,0),(41,'苹果','苹果','2020-01-04',300,1,NULL,0),(42,'苹果','苹果','2020-01-01',15,1,NULL,0),(43,'苹果','苹果','2020-07-23',15,1,NULL,0),(44,'牛肉','牛肉','2020-09-04',15,5,NULL,0),(45,'苹果','苹果','2020-07-13',15,1,NULL,0),(46,'苹果','苹果','2020-07-13',15,1,NULL,0),(47,'牛肉','牛肉','2020-07-07',15,5,NULL,0),(48,'鼠标','鼠标','2020-07-23',1000,4,NULL,1),(49,'鼠标','鼠标','2020-08-07',1000,4,NULL,1),(50,'电脑','发顺丰','2020-07-15',20000,4,NULL,0),(51,'华为手机','发顺丰','2020-06-30',30,4,NULL,0),(52,'牛肉','牛肉','2020-06-30',15,5,NULL,0),(53,'梨子','梨子','2020-07-19',10,1,NULL,0),(54,'华为手机','手机','2020-06-30',30,4,NULL,1);

DROP TABLE IF EXISTS `goods_type`;

CREATE TABLE `goods_type` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(32)  NOT NULL COMMENT '商品类型',
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

insert  into `goods_type`(`id`,`name`) values (1,'生鲜水果'),(2,'生活用品'),(3,'家电'),(4,'数码'),(5,'食品'),(6,'其他');

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` INT(11) NOT NULL COMMENT '编号',
  `permissionName` VARCHAR(32) DEFAULT NULL COMMENT '权限名',
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

insert  into `permission`(`id`,`code`,`permissionName`) values (1,1,'用户管理'),(2,2,'商品管理'),(3,3,'出库管理'),(4,4,'入库管理');

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rolename` VARCHAR(32) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

insert  into `role`(`id`,`rolename`) values (1,'销售经理'),(2,'销售主管'),(3,'客户经理'),(4,'品牌主管'),(5,'老板'),(6,'保洁阿姨'),(7,'员工')

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `roleId` INT(11) NOT NULL COMMENT '角色id',
  `permissionId` INT(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


INSERT  INTO `role_permission`(`id`,`roleId`,`permissionId`) VALUES 
(1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,1),(6,3,1),(7,4,1),(8,5,1),(9,6,1),(10,7,1)


DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goodsId` INT(11) NOT NULL COMMENT '商品id',
  `goodsAmount` INT(11) DEFAULT NULL COMMENT '商品当前库存量',
  `standardAmount` INT(11) DEFAULT NULL COMMENT '商品标准库存量',
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `stock_change`;

insert  into `stock`(`id`,`goodsId`,`goodsAmount`,`standardAmount`) values (1,1,2700,500),(3,3,500,500),(4,4,90,2000),(5,5,5,2000),(6,6,0,2500),(7,7,0,1500),(8,17,6346,2500),(9,18,0,2500),(10,25,0,1000),(11,26,0,1000),(12,27,0,2500),(13,29,0,2500),(14,33,141,3000),(15,39,0,3000),(16,48,0,2000),(17,50,0,2000),(18,54,200,2000);

CREATE TABLE `stock_change` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goodsId` INT(11) NOT NULL COMMENT '商品ID',
  `amount` INT(11) NOT NULL COMMENT '数量',
  `time` DATE DEFAULT NULL COMMENT '时间',
  `type` INT(11) NOT NULL COMMENT '0:出库1：入库',
  `userId` INT(11) NOT NULL COMMENT '库存操作操作人员id',
  `state` INT(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

insert  into `stock_change`(`id`,`goodsId`,`amount`,`time`,`type`,`userId`,`state`) values (1,8,1000,'2020-01-01',1,1,1),(2,9,70,'2020-01-05',0,1,1),(3,10,1000,'2020-01-02',1,1,1),(4,11,100,'2020-02-01',1,1,1),(5,12,10,'2020-07-15',0,1,1),(6,13,10,'2020-07-21',1,10,1),(7,14,5,'2020-07-21',0,1,0),(8,15,10,'2020-07-15',1,1,1),(9,16,10,'2020-07-20',1,1,0),(10,19,100,'2020-07-19',1,1,0),(11,20,50,'2020-07-21',0,1,0),(12,21,50,'2020-07-21',0,1,1),(13,22,50,'2020-06-21',0,1,1),(14,23,500,'2020-06-21',0,1,1),(15,24,500,'2020-06-21',0,1,1),(16,35,111,'2020-01-01',1,1,0),(17,37,30,'2020-07-20',1,10,1),(18,42,1000,'2020-01-01',1,1,0),(19,43,1000,'2020-01-01',1,1,0),(20,44,6546,'2020-08-09',1,1,1),(21,46,200,'2020-07-22',0,1,0),(22,47,500,'2020-07-20',0,1,0),(23,51,200,'2020-07-22',1,1,0),(24,52,200,'2020-07-14',1,9,0),(25,53,500,'2020-07-23',1,10,0);

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `usercode` VARCHAR(32)  DEFAULT NULL COMMENT '用户账号',
  `username` VARCHAR(32) NOT NULL COMMENT '用户姓名',
  `password` VARCHAR(64) NOT NULL DEFAULT '123456' COMMENT '用户密码',
  `roleId` INT(11) NOT NULL COMMENT '角色id',
  `headPic` VARCHAR(255) DEFAULT NULL COMMENT '头像描述',
  `state` INT(255) DEFAULT '0' COMMENT '是否离职0：未离职1：已离职',
  `tel` VARCHAR(11) DEFAULT NULL COMMENT '电话',
  `email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
  `address` VARCHAR(200) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT  INTO `user`(`id`,`usercode`,`username`,`password`,`roleId`,`headPic`,`state`,`tel`,`email`,`address`) VALUES 
(1,'032002414','李志取','$2a$10$ldDVP5nvNViT09iafdz3p.AA8W/RKIusoeO6noI/y6Gajj0bCS5lC',1,'1',0,'18359597989','2207750450@qq.com',NULL),
(2,'032002417','林卢希','$2a$10$ldDVP5nvNViT09iafdz3p.AA8W/RKIusoeO6noI/y6Gajj0bCS5lC',2,NULL,0,NULL,NULL,NULL),
(3,'032002438','詹峻','$2a$10$ldDVP5nvNViT09iafdz3p.AA8W/RKIusoeO6noI/y6Gajj0bCS5lC',3,NULL,0,NULL,NULL,NULL),
(4,'032002415','连道鑫','$2a$10$ldDVP5nvNViT09iafdz3p.AA8W/RKIusoeO6noI/y6Gajj0bCS5lC',4,NULL,0,NULL,NULL,NULL),
(5,'032002416','林涵忠','$2a$10$ldDVP5nvNViT09iafdz3p.AA8W/RKIusoeO6noI/y6Gajj0bCS5lC',4,NULL,0,NULL,NULL,NULL)

```

