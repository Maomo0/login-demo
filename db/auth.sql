/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.18 : Database - authc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`authc` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `authc`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(100) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `usertype` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '2' COMMENT '1:admin 2:普通用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`usertype`) values 
('5c27569a-204d-11ed-a2c3-2c4d54be7ddd','admin','123','1'),
('6c229b2a-0b2c-11ed-b17b-2c4d54be7ddd','123','456','2');

/*Table structure for table `user_auth` */

DROP TABLE IF EXISTS `user_auth`;

CREATE TABLE `user_auth` (
  `id` varchar(100) NOT NULL,
  `userid` varchar(200) NOT NULL,
  `auth` varchar(100) DEFAULT NULL COMMENT '权限标志',
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`userid`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_auth` */

insert  into `user_auth`(`id`,`userid`,`auth`) values 
('c0629f80-208d-11ed-a2c3-2c4d54be7ddd','6c229b2a-0b2c-11ed-b17b-2c4d54be7ddd','menuAuth'),
('d61cc722-2054-11ed-a2c3-2c4d54be7ddd','5c27569a-204d-11ed-a2c3-2c4d54be7ddd','menuAuth'),
('d6382d2e-2054-11ed-a2c3-2c4d54be7ddd','5c27569a-204d-11ed-a2c3-2c4d54be7ddd','userAuth');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
