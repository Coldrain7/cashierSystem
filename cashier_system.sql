-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cashier_system
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `classification`
--

DROP TABLE IF EXISTS `classification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sup_id` int DEFAULT NULL,
  `cla_id` int DEFAULT NULL COMMENT '存储子分类的id',
  `classification` varchar(128) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_CLASSIFICATION_REFERENCE` (`cla_id`),
  KEY `FK_supermarket_classification` (`sup_id`),
  CONSTRAINT `FK_CLASSIFICATION_REFERENCE` FOREIGN KEY (`cla_id`) REFERENCES `classification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_supermarket_classification` FOREIGN KEY (`sup_id`) REFERENCES `supermarket` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='存储分类的结构，树形结构';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classification`
--

LOCK TABLES `classification` WRITE;
/*!40000 ALTER TABLE `classification` DISABLE KEYS */;
INSERT INTO `classification` VALUES (1,100000000,NULL,'饮料类',0,'2024-04-04 17:59:24','2024-04-04 17:59:24'),(2,100000000,NULL,'小零食',0,'2024-04-04 17:59:24','2024-04-04 17:59:24');
/*!40000 ALTER TABLE `classification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commodity`
--

DROP TABLE IF EXISTS `commodity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commodity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sup_id` int DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `barcode` varchar(128) DEFAULT NULL,
  `cla_id` int DEFAULT NULL,
  `inventory` int DEFAULT NULL,
  `unit_id` int DEFAULT NULL,
  `purchase_price` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `is_discount` tinyint(1) DEFAULT NULL,
  `produce_date` datetime DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  `expiration_time` int DEFAULT NULL,
  `specification` varchar(128) DEFAULT NULL COMMENT '规格',
  `parent` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) DEFAULT '0',
  `wholesale_price` decimal(10,2) DEFAULT NULL,
  `is_multibarcode` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_commodity_classification` (`cla_id`),
  KEY `FK_commodity_supplier` (`supplier_id`),
  KEY `FK_commodity_unit` (`unit_id`),
  KEY `FK_sell` (`sup_id`),
  KEY `FK_combine_and_seperate` (`parent`),
  CONSTRAINT `FK_combine_and_seperate` FOREIGN KEY (`parent`) REFERENCES `commodity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_commodity_classification` FOREIGN KEY (`cla_id`) REFERENCES `classification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_commodity_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_commodity_unit` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_sell` FOREIGN KEY (`sup_id`) REFERENCES `supermarket` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb3 COMMENT='商品相关属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commodity`
--

LOCK TABLES `commodity` WRITE;
/*!40000 ALTER TABLE `commodity` DISABLE KEYS */;
INSERT INTO `commodity` VALUES (2,100000000,'船夫山椒竹笋','6976290985522',2,11,2,1,1,1,NULL,1,0,NULL,NULL,'2023-12-27 14:11:28','2024-04-04 18:00:14',0,0.00,NULL),(3,100000000,'比巴卜怪兽','6976030212031',2,6,3,12,16,1,NULL,NULL,0,NULL,NULL,'2023-12-08 15:46:12','2024-04-04 18:15:13',0,16.00,NULL),(4,100000000,'星花路放','6976030211614',2,3,3,7,10,1,NULL,NULL,0,NULL,NULL,'2023-12-08 15:49:30','2024-04-04 18:15:13',0,10.00,NULL),(5,100000000,'哆趣手表','6976030211393',2,15,4,10,14,1,NULL,NULL,0,NULL,NULL,'2023-12-08 15:48:00','2024-04-04 18:15:13',0,14.00,NULL),(6,100000000,'去骨鸡爪','6975508910073',2,22,2,2,2,1,NULL,1,0,NULL,NULL,'2022-12-15 15:25:16','2024-04-04 18:15:13',0,2.00,NULL),(7,100000000,'张奇龙拉丝爽','6975480150252',2,0,2,1,1,1,NULL,NULL,0,NULL,NULL,'2022-10-12 11:26:20','2024-04-04 18:15:13',0,1.00,NULL),(8,100000000,'张奇龙拉丝爽','6975480150221',2,1,2,1,1,1,NULL,3,0,NULL,NULL,'2022-11-09 12:03:23','2024-04-04 18:15:13',0,1.00,NULL),(9,100000000,'美好火锅味系列','6975262830723',2,31,2,1,1,1,NULL,NULL,0,NULL,NULL,'2023-03-29 16:23:54','2024-04-04 18:15:13',0,1.00,NULL),(10,100000000,'美好火锅豆干','6975262830624',2,20,2,1,1,1,NULL,2,0,NULL,NULL,'2023-01-02 18:53:03','2024-04-04 18:15:13',0,1.00,NULL),(11,100000000,'双汇小肉肠','6975262830556',2,0,4,1,1,1,NULL,NULL,0,NULL,NULL,'2023-04-08 18:02:59','2024-04-04 18:15:13',0,0.00,NULL),(12,100000000,'美好脆骨小肉肠','6975262830518',2,-19,2,1,1,1,NULL,2,0,NULL,NULL,'2023-01-02 18:54:02','2024-04-04 18:15:13',0,1.00,NULL),(13,100000000,'脆骨小肉肠','6975262830389',2,65,2,1,1,1,NULL,NULL,0,NULL,NULL,'2023-02-24 16:47:24','2024-04-04 18:15:13',0,1.00,NULL),(14,100000000,'美好火锅素毛肚','6975262830235',2,11,5,1,1,1,NULL,NULL,0,NULL,NULL,'2022-10-28 16:07:04','2024-04-04 18:15:13',0,1.00,NULL),(15,100000000,'鸡爪35克','6974466960014',2,13,4,2,2,1,NULL,1,0,NULL,NULL,'2022-04-18 12:36:15','2024-04-04 18:15:13',0,2.00,NULL),(16,100000000,'酱媳妇鸡爪','6974338340012',2,0,6,1,1,1,NULL,3,0,NULL,NULL,'2022-11-09 12:02:01','2024-04-04 18:15:13',0,1.00,NULL),(17,100000000,'芝麻肉干','6973942377117',2,0,2,2,2,1,NULL,NULL,0,NULL,NULL,'2023-02-14 16:16:13','2024-04-04 18:15:13',0,2.00,NULL),(18,100000000,'夜光果味饮料','6973053860065',2,0,3,4,5,1,NULL,1,0,NULL,NULL,'2023-08-25 15:08:21','2024-04-04 18:15:13',0,5.00,NULL),(19,100000000,'美家园别墅小屋','6972235177465',2,0,4,10,12,1,NULL,1,0,NULL,NULL,'2023-08-16 15:01:30','2024-04-04 18:15:13',0,12.00,NULL),(20,100000000,'拼装玩具枪','6972235177410',2,3,3,7,10,1,NULL,1,0,NULL,NULL,'2023-08-25 15:05:41','2024-04-04 18:15:13',0,10.00,NULL),(21,100000000,'聪明星悦星口琴','6972206010517',2,3,4,2,3,1,NULL,1,0,NULL,NULL,'2023-10-13 16:30:28','2024-04-04 18:15:13',0,3.00,NULL),(22,100000000,'香辣去骨鸡爪','6971961270983',2,3,2,1,1,1,NULL,1,0,NULL,NULL,'2023-09-13 13:16:06','2024-04-04 18:15:13',0,1.00,NULL),(23,100000000,'韩仔红绿灯果冻','6971824482027',2,8,4,2,2,1,NULL,1,0,NULL,NULL,'2023-08-25 15:04:19','2024-04-04 18:15:13',0,2.00,NULL),(24,100000000,'20克黑鸭烤脖','6971814540034',2,1,4,1,1,1,NULL,1,0,NULL,NULL,'2022-07-02 13:11:05','2024-04-04 18:15:13',0,1.00,NULL),(25,100000000,'凯迪动漫刮画便签','6970288774709',2,3,4,7,10,1,NULL,1,0,NULL,NULL,'2023-10-13 16:28:38','2024-04-04 18:15:13',0,10.00,NULL),(26,100000000,'酷变机甲','6970274205903',2,1,3,16,20,1,NULL,1,0,NULL,NULL,'2023-08-16 15:06:29','2024-04-04 18:15:13',0,20.00,NULL),(27,100000000,'28g有才虎皮凤爪','6959508600134',2,25,4,2,3,1,NULL,1,0,NULL,NULL,'2024-01-06 15:32:23','2024-04-04 18:15:13',0,3.00,NULL),(28,100000000,'味滋鹌鹑蛋','6956060535034',2,1,5,2,2,1,NULL,4,0,NULL,NULL,'2023-08-15 19:18:48','2024-04-04 18:15:13',0,2.00,NULL),(29,100000000,'铁板鱿鱼烧烤味','6956060519669',2,4,2,1,1,1,NULL,4,0,NULL,NULL,'2023-08-15 19:19:41','2024-04-04 18:15:13',0,1.00,NULL),(30,100000000,'广东正乐32克抹茶瓜子巧克力袋','6955170301096',2,18,2,1,1,1,NULL,1,0,NULL,NULL,'2023-12-27 14:09:54','2024-04-04 18:15:13',0,0.00,NULL),(31,100000000,'盛歌流心熊','6952767613006',2,4,4,8,10,1,NULL,1,0,NULL,NULL,'2024-01-06 15:42:20','2024-04-04 18:15:13',0,10.00,NULL),(32,100000000,'手撕鱿鱼条','6948576407820',2,5,3,14,18,1,NULL,1,0,NULL,NULL,'2024-01-06 15:40:53','2024-04-04 18:15:13',0,18.00,NULL),(33,100000000,'干瞪眼去骨鸭脚筋泡椒味15g','6941424702801',2,20,2,1,1,1,NULL,3,0,NULL,NULL,'2024-01-17 13:14:43','2024-04-04 18:15:13',0,1.00,NULL),(34,100000000,'欢辣王贡菜','6939936600456',2,19,2,1,1,1,NULL,1,0,NULL,NULL,'2023-09-13 13:18:50','2024-04-04 18:15:13',0,1.00,NULL),(35,100000000,'味芝元洞庭野生鱼尾26g','6936869215085',2,8,2,2,2,1,NULL,1,0,NULL,NULL,'2023-10-04 17:16:12','2024-04-04 18:15:13',0,2.00,NULL),(36,100000000,'香之派多味鸡爪','6935563286841',2,14,4,1,2,1,NULL,3,0,NULL,NULL,'2023-09-13 11:34:40','2024-04-04 18:15:13',0,1.50,NULL),(37,100000000,'卫龙风吃海带','6935284400281',2,17,2,1,1,1,NULL,NULL,0,NULL,NULL,'2023-09-02 14:54:16','2024-04-04 18:15:13',0,1.00,NULL),(38,100000000,'卫龙魔芋爽','6935284400250',2,29,2,1,1,1,NULL,5,0,NULL,NULL,'2022-10-07 12:34:14','2024-04-04 18:15:13',0,1.00,NULL),(39,100000000,'德食氮气烤脖','6933328405117',2,12,4,2,3,1,NULL,1,0,NULL,NULL,'2023-10-23 14:31:12','2024-04-04 18:15:13',0,0.00,NULL),(40,100000000,'德食西冷牛扒','6933328404776',2,15,2,2,2,1,NULL,NULL,0,NULL,NULL,'2023-12-27 14:01:32','2024-04-04 18:15:13',0,0.00,NULL),(41,100000000,'德食西冷肉扒','6933328404769',2,0,4,2,2,1,NULL,1,0,NULL,NULL,'2023-09-04 15:26:12','2024-04-04 18:15:13',0,2.00,NULL),(42,100000000,'德食牛蹄筋','6933328404332',2,18,2,1,1,1,NULL,1,0,NULL,NULL,'2023-12-27 14:08:45','2024-04-04 18:15:13',0,0.00,NULL),(43,100000000,'德食牛蹄筋','6933328404325',2,17,2,1,1,1,NULL,1,0,NULL,NULL,'2023-09-13 13:12:48','2024-04-04 18:15:13',0,1.00,NULL),(44,100000000,'鲜客鹌鹑蛋','6931768502090',2,16,2,1,1,1,NULL,NULL,0,NULL,NULL,'2023-12-27 14:06:41','2024-04-04 18:15:13',0,0.00,NULL),(45,100000000,'鲜客鹌鹑蛋','6931768502083',2,29,2,1,1,1,NULL,1,0,NULL,NULL,'2023-12-27 14:05:50','2024-04-04 18:15:13',0,0.00,NULL),(46,100000000,'好味屋魔芋王子','6931754808588',2,12,2,1,1,1,NULL,1,0,NULL,NULL,'2023-08-19 09:03:24','2024-04-04 18:15:13',0,1.00,NULL),(47,100000000,'26g鱼豆腐香辣味','6931754807536',2,19,2,1,1,1,NULL,1,0,NULL,NULL,'2023-08-16 14:53:00','2024-04-04 18:15:13',0,1.00,NULL),(48,100000000,'26g手撕素肉排黑鸭味','6931754805655',2,43,2,1,1,1,NULL,NULL,0,NULL,NULL,'2023-08-16 14:54:33','2024-04-04 18:15:13',0,1.00,NULL),(49,100000000,'一根脆骨奥尔良味','6924955406639',2,0,6,2,2,1,NULL,NULL,0,NULL,NULL,'2023-04-14 13:57:21','2024-04-04 18:15:13',0,2.00,NULL),(50,100000000,'迪士尼草莓棒棒糖','6924762329251',2,20,4,2,3,1,NULL,NULL,0,NULL,NULL,'2023-11-15 15:28:06','2024-04-04 18:15:13',0,3.00,NULL),(51,100000000,'双色棒棒糖','6924762329169',2,31,4,1,1,1,NULL,NULL,0,NULL,NULL,'2023-11-15 15:24:01','2024-04-04 18:15:13',0,1.00,NULL),(52,100000000,'炭烤土豆（烧烤味）','6923236121452',2,122,2,1,1,1,NULL,NULL,0,NULL,NULL,'2022-08-03 10:58:36','2024-04-04 18:15:13',0,1.00,NULL),(53,100000000,'帝诺可可味黑巧克力','6922368506090',2,0,7,2,2,1,NULL,NULL,0,NULL,NULL,'2023-11-15 15:29:37','2024-04-04 18:15:13',0,2.00,NULL),(54,100000000,'巧克力工具型42g帝诺 儿童食品玩具 超市','6922368502627',2,0,3,2,4,1,NULL,NULL,0,NULL,NULL,'2023-11-15 15:30:56','2024-04-04 18:15:13',0,4.00,NULL),(55,100000000,'香卤带鱼','6920732133705',2,9,2,2,2,1,NULL,1,0,NULL,NULL,'2023-11-01 14:40:31','2024-04-04 18:15:13',0,2.00,NULL),(56,100000000,'蜜汁排骨','6920127120556',2,0,2,1,1,1,NULL,1,0,NULL,NULL,'2022-07-02 13:22:37','2024-04-04 18:15:13',0,1.00,NULL),(57,100000000,'味非凡麻辣小腿王40克','6920127120129',2,15,2,2,2,1,NULL,1,0,NULL,NULL,'2022-04-17 15:12:39','2024-04-04 18:15:13',0,2.00,NULL),(58,100000000,'百事可乐可乐型汽水2L/8 碳酸饮料','940055',1,6,9,46,49,1,NULL,6,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,49.00,NULL),(59,100000000,'980元气森林电解质水','6975177140924',1,0,8,8,10,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,10.00,NULL),(60,100000000,'元气森林可乐味','6975176781777',1,0,8,4,5,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(61,100000000,'倾芬气垫霜','6974164582563',1,2,3,38,88,1,NULL,7,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,88.00,NULL),(62,100000000,'康师傅2升乳酸菌水乐','6973870135797',1,5,8,6,8,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,8.00,NULL),(63,100000000,'康师傅茉莉花纯粹零糖','6973870133021',1,14,8,2,3,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,3.00,NULL),(64,100000000,'康师傅乳酸菌水乐','6973870132710',1,31,8,3,4,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(65,100000000,'康师傅乳酸菌葡萄味','6973870132666',1,10,8,2,3,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,3.00,NULL),(66,100000000,'康师傅冰糖西柚','6973870132369',1,13,8,3,4,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(67,100000000,'500ml康师傅冰糖红西柚','6973870132314',1,9,8,2,3,1,NULL,4,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(68,100000000,'金桔柠檬康师傅','6973870131690',1,8,8,3,4,1,NULL,6,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(69,100000000,'金桔柠檬','6973870131676',1,1,8,2,3,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,3.00,NULL),(70,100000000,'1L康师傅小酪多多','6973870130341',1,11,8,3,4,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(71,100000000,'贝纳颂摩卡拿铁','6973870130099',1,13,8,5,7,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(72,100000000,'贝纳颂丝滑拿铁300ml','6973870130075',1,15,8,5,7,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(73,100000000,'康师傅500柠檬味','6973870130006',1,14,8,2,3,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,3.00,NULL),(74,100000000,'华洋凤梨味果汁汽水','6973782440149',1,9,8,3,5,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,5.00,NULL),(75,100000000,'王老吉刺柠吉饮料','6973539970240',1,10,8,4,5,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(76,100000000,'多多柠檬茶','6973497201752',1,0,8,5,6,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,6.00,NULL),(77,100000000,'方好牌黄桃罐头710g','6972509070300',1,0,8,12,16,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,16.00,NULL),(78,100000000,'方好什锦罐头','6972509070072',1,0,8,7,12,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,12.00,NULL),(79,100000000,'方好430克黄桃罐头','6972509070003',1,0,8,7,10,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,12.00,NULL),(80,100000000,'捷虎能量','6972306252008',1,-2,8,5,6,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,6.00,NULL),(81,100000000,'小轻甘甘菊金银花露','6972306250509',1,18,8,4,5,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,5.00,NULL),(82,100000000,'椰容头道鲜榨椰汁1L','6971777220059',1,29,8,11,15,1,NULL,8,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,15.00,NULL),(83,100000000,'苹果醋风味饮料','6971329920697',1,6,8,3,5,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,5.00,NULL),(84,100000000,'米禄发酵糯米饮品清润银耳味','6970804370132',1,27,8,4,5,1,NULL,6,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,5.00,NULL),(85,100000000,'元气森林 石榴红树莓味苏打气泡水（汽水）','6970399927179',1,0,8,3,5,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(86,100000000,'元气森林 夏黑葡萄味苏打气泡水（汽水）','6970399923096',1,7,8,4,5,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(87,100000000,'外星人电解质水电解质饮料','6970399922365',1,1,8,4,6,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,0.00,NULL),(88,100000000,'元気森林白桃味苏打气泡水','6970399920415',1,4,8,4,5,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,5.00,NULL),(89,100000000,'椰树椰子汁','6957735788861',1,-3,8,4,5,1,NULL,10,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,5.00,NULL),(90,100000000,'美汁源果粒橙小','6956416205147',1,25,8,3,4,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(91,100000000,'美汁源水蜜桃味果粒奶优饮品','6956416203389',1,6,8,3,4,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(92,100000000,'美汁源果粒橙橙汁饮料','6956416200067',1,6,8,3,4,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(93,100000000,'果粒橙橙汁饮料','6956416200036',1,43,8,8,10,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,10.00,NULL),(94,100000000,'王老吉凉茶植物饮料1.5升','6956367338697',1,52,8,8,10,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,12.00,NULL),(95,100000000,'王老吉凉茶植物饮料500毫升','6956367338666',1,3,8,3,4,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(96,100000000,'王老吉凉茶植物饮料1.5升','6956367338635',1,0,9,53,58,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,65.00,NULL),(97,100000000,'王老吉罐装凉茶','6956367338604',1,15,9,54,58,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,62.00,NULL),(98,100000000,'310vg王老吉','6956367311485',1,13,9,47,50,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,55.00,NULL),(99,100000000,'芬达拉罐','6954767446523',1,2,11,2,3,1,NULL,2,NULL,'1x6',102,'2024-04-04 23:05:47','2024-04-05 00:04:43',0,2.50,NULL),(100,100000000,'芬达 500mL PET8装饮料','6954767445175',1,17,8,2,3,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,3.00,NULL),(101,100000000,'500芬达苹果味','6954767444376',1,5,8,2,3,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,3.00,NULL),(102,100000000,'芬达拉罐','6954767441481',1,20,8,12,16,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-05 00:04:05',0,16.00,NULL),(103,100000000,'芬达300ML','6954767441276',1,0,8,2,2,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,2.00,NULL),(104,100000000,'雪碧/含糖雪碧 888mL','6954767434971',1,18,8,3,4,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,4.00,NULL),(105,100000000,'2L雪碧','6954767433073',1,2,8,5,6,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,6.00,NULL),(106,100000000,'雪碧500ML','6954767432076',1,22,8,2,3,1,NULL,2,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,3.00,NULL),(107,100000000,'雪碧易拉罐装','6954767430522',1,6,11,11,14,1,NULL,9,NULL,NULL,NULL,'2024-04-04 23:05:47','2024-04-04 23:05:47',0,14.00,NULL);
/*!40000 ALTER TABLE `commodity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sup_id` int DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `point` int DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_Member_of_Supermarket` (`sup_id`),
  CONSTRAINT `FK_Member_of_Supermarket` FOREIGN KEY (`sup_id`) REFERENCES `supermarket` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10050 DEFAULT CHARSET=utf8mb3 COMMENT='会员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (10000,100000000,'李',78945613204,50,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10001,100000000,'张君琼',23569874105,391,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10002,100000000,'俞庭香',96325847016,260,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10004,100000000,'兰祖英',14728956307,291,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10005,100000000,'传秋',52896317408,2032,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10006,100000000,'李明芳',87451296309,2560,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10007,100000000,'韩飞',49613587210,1,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10008,100000000,'朱五春',21036985411,185,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10009,100000000,'朱武松',75392648112,71,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10010,100000000,'李和芳',98741023613,135,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10011,100000000,'古丽',63579284714,782,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10012,100000000,'曾',10987654325,350,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10013,100000000,'小敖',56239871426,31,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10014,100000000,'葛修南',89476523127,1436,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10015,100000000,'黄燕',32659847028,376,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10016,100000000,'韩学琴',74913658229,999,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10017,100000000,'何',96532187330,722,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10018,100000000,'张德祥',48765213931,1874,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10019,100000000,'周仕容',25894761032,914,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10020,100000000,'李燕',63179854133,5238,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10021,100000000,'胡美',97428635234,3807,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10022,100000000,'黎洪艳',51639748335,1020,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10023,100000000,'曾红',28965412036,1116,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10024,100000000,'钟桂芬',70246893137,1587,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10025,100000000,'曾品英',43569872338,833,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10026,100000000,'陈向惠',92784561439,156,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10027,100000000,'葛方英',65932178540,1475,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10028,100000000,'许梅',38465927641,1955,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10029,100000000,'熊正容',10798635442,1655,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10030,100000000,'肖慈芬',53269874543,4281,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10031,100000000,'吴道友',86491325644,64,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10032,100000000,'陈文琳',29654783745,504,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10033,100000000,'黄文莲',71386594846,277,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10034,100000000,'胡启全',45629876047,1295,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10035,100000000,'林丽',98735214148,1298,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10036,100000000,'游书琼',62954783249,531,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10037,100000000,'张晴',35789642350,71,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10038,100000000,'肖家先',89652314451,3143,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10039,100000000,'张大嬢',54378965552,2538,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10040,100000000,'舒钟英',27491356653,1304,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10041,100000000,'宋世平',60854723754,729,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10042,100000000,'岳昌荣',93568942855,1158,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10043,100000000,'刘丽',46729853956,804,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10044,100000000,'林永全',18945672057,319,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10045,100000000,'卓',59263847158,116,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10046,100000000,'郭世全',87395426259,0,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10047,100000000,'罗勇妈',34628975360,536,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10048,100000000,'南宇嬢',72954638461,1049,0,'2024-04-04 18:20:09','2024-04-05 00:14:08'),(10049,100000000,'童花芬',41369857562,345,0,'2024-04-04 18:20:09','2024-04-05 00:14:08');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multibarcode`
--

DROP TABLE IF EXISTS `multibarcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multibarcode` (
  `id` int NOT NULL AUTO_INCREMENT,
  `com_id` bigint DEFAULT NULL,
  `barcode` varchar(128) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_data` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_multi_barcode` (`com_id`),
  CONSTRAINT `FK_multi_barcode` FOREIGN KEY (`com_id`) REFERENCES `commodity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='一品多码关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multibarcode`
--

LOCK TABLES `multibarcode` WRITE;
/*!40000 ALTER TABLE `multibarcode` DISABLE KEYS */;
/*!40000 ALTER TABLE `multibarcode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `id` bigint NOT NULL,
  `worker_id` int DEFAULT NULL,
  `mem_id` int DEFAULT NULL,
  `com_id` bigint NOT NULL,
  `number` int DEFAULT NULL,
  `payment` double DEFAULT NULL,
  `method` int DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`com_id`),
  KEY `FK_create_record` (`worker_id`),
  KEY `FK_commodity_record` (`com_id`),
  KEY `FK_purchase` (`mem_id`),
  CONSTRAINT `FK_commodity_record` FOREIGN KEY (`com_id`) REFERENCES `commodity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_create_record` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_purchase` FOREIGN KEY (`mem_id`) REFERENCES `member` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='销售记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supermarket`
--

DROP TABLE IF EXISTS `supermarket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supermarket` (
  `id` int NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supermarket`
--

LOCK TABLES `supermarket` WRITE;
/*!40000 ALTER TABLE `supermarket` DISABLE KEYS */;
INSERT INTO `supermarket` VALUES (100000000,'喜乐多生活超市',0,'2024-04-04 00:56:09','2024-04-04 00:56:09');
/*!40000 ALTER TABLE `supermarket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sup_id` int DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `number` int DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `phone` bigint DEFAULT NULL,
  `linkman` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_supermarket_supplier` (`sup_id`),
  CONSTRAINT `FK_supermarket_supplier` FOREIGN KEY (`sup_id`) REFERENCES `supermarket` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COMMENT='供应商表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,100000000,'富顺开元休闲食品',1,0,'2024-04-04 15:43:23','2024-04-05 21:58:10',NULL,NULL),(2,100000000,'富顺宏光商贸',2,0,'2024-04-04 15:43:23','2024-04-05 21:58:10',NULL,NULL),(3,100000000,'三友',3,0,'2024-04-04 15:43:23','2024-04-05 21:58:10',NULL,NULL),(4,100000000,'富顺顺达商贸',4,0,'2024-04-04 15:43:23','2024-04-05 21:58:10',NULL,NULL),(5,100000000,'先惠',5,0,'2024-04-04 15:47:53','2024-04-05 21:58:10',NULL,NULL),(6,100000000,'富顺刘大福食品',6,0,'2024-04-04 23:00:43','2024-04-05 21:58:10',NULL,NULL),(7,100000000,'琦琦美业',7,0,'2024-04-04 23:00:43','2024-04-05 21:58:10',NULL,NULL),(8,100000000,'九越商贸',8,0,'2024-04-04 23:00:43','2024-04-05 21:58:10',NULL,NULL),(9,100000000,'无',9,0,'2024-04-04 23:00:43','2024-04-05 21:58:10',NULL,NULL),(10,100000000,'鑫鑫酒业',10,0,'2024-04-04 23:00:43','2024-04-05 21:58:10',NULL,NULL),(11,100000000,'富顺宏光商贸',11,0,'2024-04-04 23:01:14','2024-04-05 21:58:10',NULL,NULL),(13,100000000,'自采',12,0,'2024-04-06 00:24:26','2024-04-06 00:24:26',NULL,NULL),(14,100000000,'测试供应商',13,0,'2024-04-06 00:29:36','2024-04-06 00:29:36',NULL,NULL),(15,100000000,'测试供应商',14,0,'2024-04-06 00:34:50','2024-04-06 00:34:50',NULL,NULL),(16,100000000,'测试供应商',15,0,'2024-04-06 00:37:55','2024-04-06 00:37:55',NULL,NULL),(17,100000000,'测试供应商',16,0,'2024-04-06 00:39:26','2024-04-06 00:39:26',NULL,NULL),(18,100000000,'测试供应商29',89,0,'2024-04-06 00:42:57','2024-04-06 00:48:50',12312312312,'');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sup_id` int DEFAULT NULL,
  `unit` varchar(32) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_unit_supermarket` (`sup_id`),
  CONSTRAINT `FK_unit_supermarket` FOREIGN KEY (`sup_id`) REFERENCES `supermarket` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COMMENT='单位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES (2,100000000,'包',0,'2024-04-04 15:56:53','2024-04-04 15:56:53'),(3,100000000,'盒',0,'2024-04-04 15:56:53','2024-04-04 15:56:53'),(4,100000000,'个',0,'2024-04-04 15:56:53','2024-04-04 15:56:53'),(5,100000000,'无',0,'2024-04-04 15:56:53','2024-04-04 15:56:53'),(6,100000000,'支',0,'2024-04-04 15:56:53','2024-04-04 15:56:53'),(7,100000000,'块',0,'2024-04-04 15:56:53','2024-04-04 15:56:53'),(8,100000000,'瓶',0,'2024-04-04 22:46:48',NULL),(9,100000000,'件',0,'2024-04-04 22:47:55',NULL),(10,100000000,'题',1,'2024-04-04 22:49:56',NULL),(11,100000000,'提',0,'2024-04-04 22:50:04',NULL);
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sup_id` int DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `mail` varchar(64) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_manage` (`sup_id`),
  CONSTRAINT `FK_manage` FOREIGN KEY (`sup_id`) REFERENCES `supermarket` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=100000001 DEFAULT CHARSET=utf8mb3 COMMENT='员工，包含收银员，管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES (100000000,100000000,'harry','2057787038@qq.com','123456',1,0,'2024-04-04 18:21:46','2024-04-04 22:31:47');
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-06  0:57:34
