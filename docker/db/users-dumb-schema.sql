SHOW DATABASES;
DROP DATABASE IF EXISTS served_users;
CREATE DATABASE served_users;
USE served_users;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `MAILS`
--

DROP TABLE IF EXISTS `MAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MAILS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_type` varchar(45) DEFAULT NULL,
  `service_from` varchar(60) DEFAULT NULL,
  `receiver` varchar(60) DEFAULT NULL,
  `email_subject` varchar(255) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `content` mediumtext,
  `lastTryAt` timestamp NULL DEFAULT NULL,
  `tries` int(11) DEFAULT NULL,
  `sent` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MAILS`
--

LOCK TABLES `MAILS` WRITE;
/*!40000 ALTER TABLE `MAILS` DISABLE KEYS */;
INSERT INTO `MAILS` VALUES (18,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-13 04:25:33','This is the bill for your order at Holy Smokes on 13/04/2016.<br/><br/>5x Roasted Sunchoke & Celeriac: £53.00<br/>1x My new dish: £15.00<br/>1x new DIsh: £12.55<br/><br/>Subtotal: £80.55<br/>Service Charge: £8.86<br/><strong>TOTAL: £89.41</strong>','2016-04-13 04:30:54',2,1),(19,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-13 16:35:32','This is the bill for your order at Holy Smokes on 13/04/2016.<br/><br/>2x Pumpkin-Pear Soup: £16.00<br/>3x Roasted Sunchoke & Celeriac: £31.80<br/><br/>Subtotal: £47.80<br/>Service Charge: £5.26<br/><strong>TOTAL: £53.06</strong>','2016-04-13 16:40:55',2,1),(20,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Holy Smokes','2016-04-13 22:50:32','This is the bill for your order at Holy Smokes on 13/04/2016.<br/><br/>1x Pumpkin-Pear Soup: £8.00<br/>1x Roasted Sunchoke & Celeriac: £10.60<br/>4x New Dish: £10.00<br/><br/>Subtotal: £28.60<br/>Service Charge: £3.15<br/><strong>TOTAL: £36.04</strong>','2016-04-13 22:55:56',2,1),(21,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Holy Smokes','2016-04-19 18:39:52','This is the bill for your order at Holy Smokes on 19/04/2016.<br/><br/>1x Dish of the Day: £8.25<br/>1x New Dish: £2.50<br/><br/>Subtotal: £10.75<br/>Service Charge: £1.18<br/><strong>TOTAL: £11.93</strong>','2016-04-19 18:46:01',2,1),(22,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-20 00:31:29','This is the bill for your order at Holy Smokes on 20/04/2016.<br/><br/>1x Pumpkin-Pear Soup: £8.00<br/>1x Roasted Sunchoke & Celeriac: £10.60<br/>2x New Dish: £5.00<br/><br/>Subtotal: £23.60<br/>Service Charge: £2.60<br/><strong>TOTAL: £26.20</strong>','2016-04-20 00:36:02',2,1),(23,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-20 00:36:29','This is the bill for your order at Holy Smokes on 20/04/2016.<br/><br/>1x Pumpkin-Pear Soup: £8.00<br/>1x Roasted Sunchoke & Celeriac: £10.60<br/><br/>Subtotal: £18.60<br/>Service Charge: £2.05<br/><strong>TOTAL: £20.65</strong>','2016-04-20 00:41:03',2,1),(24,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-20 04:12:58','This is the bill for your order at Holy Smokes on 20/04/2016.<br/><br/>1x Roasted Sunchoke & Celeriac: £10.60<br/>1x Pumpkin-Pear Soup: £8.00<br/>1x New Dish: £2.50<br/><br/>Subtotal: £21.10<br/>Service Charge: £2.32<br/><strong>TOTAL: £24.48</strong>','2016-04-20 04:19:59',2,1),(25,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Restaurant Gordon Ramsay','2016-04-20 04:12:58','This is the bill for your order at Restaurant Gordon Ramsay on 20/04/2016.<br/><br/>2x Hickory Bourbon Chicken: £19.98<br/>1x Smoky Mountain Chicken: £10.00<br/>1x Chicken Bella: £12.99<br/><br/>Subtotal: £42.97<br/>Service Charge: £5.59<br/><strong>TOTAL: £52.85</strong>','2016-04-20 04:20:00',2,1),(26,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Holy Smokes','2016-04-21 20:57:55','This is the bill for your order at Holy Smokes on 21/04/2016.<br/><br/>4x Dish of the Day: £33.00<br/>5x New Dish: £12.50<br/><br/>Subtotal: £45.50<br/>Service Charge: £5.01<br/><strong>TOTAL: £57.33</strong>','2016-04-21 21:05:02',2,1),(27,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-25 06:08:07','This is the bill for your order at Holy Smokes on 25/04/2016.<br/><br/>2x Dish of the Day: £16.50<br/>2x New Dish: £5.00<br/><br/>Subtotal: £21.50<br/>Service Charge: £2.37<br/><strong>TOTAL: £23.87</strong>','2016-04-25 06:15:06',2,1),(28,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-25 06:13:07','This is the bill for your order at Holy Smokes on 25/04/2016.<br/><br/>1x Dish of the Day: £8.25<br/>2x New Dish: £5.00<br/><br/>Subtotal: £13.25<br/>Service Charge: £1.46<br/><strong>TOTAL: £14.71</strong>','2016-04-25 06:20:06',2,1),(29,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Holy Smokes','2016-04-25 06:18:07','This is the bill for your order at Holy Smokes on 25/04/2016.<br/><br/>1x Dish of the Day: £8.25<br/>2x New Dish: £5.00<br/><br/>Subtotal: £13.25<br/>Service Charge: £1.46<br/><strong>TOTAL: £14.71</strong>','2016-04-25 06:25:07',2,1),(30,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Holy Smokes','2016-04-25 09:58:06','This is the bill for your order at Holy Smokes on 25/04/2016.<br/><br/>2x Dish of the Day: £16.50<br/>1x New Dish: £2.50<br/><br/>Subtotal: £19.00<br/>Service Charge: £2.09<br/><strong>TOTAL: £23.94</strong>','2016-04-25 10:05:07',2,1),(31,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Holy Smokes','2016-04-25 10:03:06','This is the bill for your order at Holy Smokes on 25/04/2016.<br/><br/>1x Dish of the Day: £8.25<br/>4x New Dish: £10.00<br/><br/>Subtotal: £18.25<br/>Service Charge: £2.01<br/><strong>TOTAL: £20.26</strong>','2016-04-25 10:10:08',2,1),(32,'Order Bill','Order Service','edgarjdq@gmail.com','Bill for your order at Holy Smokes','2016-04-26 04:43:05','This is the bill for your order at Holy Smokes on 26/04/2016.<br/><br/>1x Dish of the Day: £8.25<br/>1x Pumpkin-Pear Soup: £8.00<br/><br/>Subtotal: £16.25<br/>Service Charge: £1.79<br/><strong>TOTAL: £18.04</strong>','2016-04-26 04:50:09',2,1),(33,'Order Bill','Order Service','edgarjdq@gmail.com','Bill for your order at Holy Smokes','2016-04-26 05:23:05','This is the bill for your order at Holy Smokes on 26/04/2016.<br/><br/>2x Dish of the Day: £16.50<br/><br/>Subtotal: £16.50<br/>Service Charge: £1.82<br/><strong>TOTAL: £18.32</strong>','2016-04-26 05:30:09',2,1),(34,'Order Bill','Order Service','edgarjdq@gmail.com','Bill for your order at Holy Smokes','2016-04-26 05:43:03','This is the bill for your order at Holy Smokes on 26/04/2016.<br/><br/>2x Dish of the Day: £16.50<br/><br/>Subtotal: £16.50<br/>Service Charge: £1.82<br/><strong>TOTAL: £18.32</strong>','2016-04-26 05:50:10',2,1),(35,'Order Bill','Order Service','edgarjdq@gmail.com','Bill for your order at Holy Smokes','2016-04-26 05:47:19','This is the bill for your order at Holy Smokes on 26/04/2016.<br/><br/>2x Dish of the Day: £16.50<br/><br/>Subtotal: £16.50<br/>Service Charge: £1.82<br/><strong>TOTAL: £18.32</strong>','2016-04-26 05:55:10',2,1),(36,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Niche Food And Drink','2016-04-27 19:52:24','This is the bill for your order at Niche Food And Drink on 27/04/2016.<br/><br/>1x Today’s homemade soup: £5.25<br/>1x Parmesan and Cheddar doughnuts: £6.50<br/>2x Crayfish and avocado cocktail: £14.50<br/><br/>Subtotal: £26.25<br/>Service Charge: £3.41<br/><strong>TOTAL: £29.66</strong>','2016-04-27 20:00:12',2,1),(37,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Holy Smokes','2016-04-27 19:57:24','This is the bill for your order at Holy Smokes on 27/04/2016.<br/><br/>6x Dish of the Day: £49.50<br/><br/>Subtotal: £49.50<br/>Service Charge: £5.45<br/><strong>TOTAL: £66.95</strong>','2016-04-27 20:05:13',2,1),(38,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Restaurant Gordon Ramsay','2016-04-27 19:57:24','This is the bill for your order at Restaurant Gordon Ramsay on 27/04/2016.<br/><br/>6x Local Cheese Board & Charcuterie: £42.00<br/><br/>Subtotal: £42.00<br/>Service Charge: £5.46<br/><strong>TOTAL: £53.76</strong>','2016-04-27 20:05:13',2,1),(39,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Restaurant Gordon Ramsay','2016-04-27 20:17:24','This is the bill for your order at Restaurant Gordon Ramsay on 27/04/2016.<br/><br/>1x Prince Edward Island Mussels: £14.00<br/>1x Prince Edward Island Mussels: £14.00<br/><br/>Subtotal: £28.00<br/>Service Charge: £3.64<br/><strong>TOTAL: £31.64</strong>','2016-04-27 20:25:14',2,1),(40,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Niche Food And Drink','2016-04-27 20:27:24','This is the bill for your order at Niche Food And Drink on 27/04/2016.<br/><br/>4x Today’s homemade soup: £21.00<br/><br/>Subtotal: £21.00<br/>Service Charge: £2.73<br/><strong>TOTAL: £23.73</strong>','2016-04-27 20:35:14',2,1),(41,'Order Bill','Order Service','juanloverab@gmail.com','Bill for your order at Niche Food And Drink','2016-04-28 05:52:24','This is the bill for your order at Niche Food And Drink on 28/04/2016.<br/><br/>1x Beef & Chorizo: £14.25<br/>1x Warm chocolate Brownie: £5.25<br/>1x Today’s homemade soup: £5.25<br/>1x Parmesan and Cheddar doughnuts: £6.50<br/>1x Coconut and raspberry blancmange: £5.25<br/><br/>Subtotal: £36.50<br/>Service Charge: £4.75<br/><strong>TOTAL: £41.25</strong>','2016-04-28 06:00:15',2,1),(42,'Order Bill','Order Service','edgarjdq@gmail.com','Bill for your order at Holy Smokes','2016-04-30 00:57:20','This is the bill for your order at Holy Smokes on 30/04/2016.<br/><br/>1x Dish of the Day: £8.25<br/>2x New Dish: £5.00<br/><br/>Subtotal: £13.25<br/>Service Charge: £1.46<br/><strong>TOTAL: £14.71</strong>','2016-04-30 01:05:17',2,1),(43,'Order Bill','Order Service','edgarjdq@gmail.com','Bill for your order at Holy Smokes','2016-04-30 06:07:19','This is the bill for your order at Holy Smokes on 30/04/2016.<br/><br/>2x Pumpkin-Pear Soup: £16.00<br/>1x Ordered at restaurant: £10.00<br/><br/>Subtotal: £26.00<br/>Service Charge: £2.86<br/><strong>TOTAL: £30.16</strong>','2016-04-30 06:15:18',2,1),(44,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Niche Food And Drink','2016-05-01 12:22:16','This is the bill for your order at Niche Food And Drink on 01/05/2016.<br/><br/>4x Today’s homemade soup: £21.00<br/><br/>Subtotal: £21.00<br/>Service Charge: £2.73<br/><strong>TOTAL: £23.73</strong>','2016-05-01 12:30:19',2,1),(45,'Order Bill','Order Service','aperezdieppa@gmail.com','Bill for your order at Niche Food And Drink','2016-05-01 12:27:16','This is the bill for your order at Niche Food And Drink on 01/05/2016.<br/><br/>3x Cobb salad: £29.25<br/>1x Steak & Stilton salad: £14.50<br/>1x 30-day aged sirloin (8oz): £22.75<br/>3x 30-day aged rib-eye (10oz): £79.50<br/>1x Beetroot gravlax Nicoise salad: £8.75<br/><br/>Subtotal: £154.75<br/>Service Charge: £19.34<br/><strong>TOTAL: £176.09</strong>','2016-05-01 12:35:20',2,1);
/*!40000 ALTER TABLE `MAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `digits_id` varchar(255) DEFAULT NULL,
  `last_device_id` varchar(255) DEFAULT NULL,
  `last_device_type` enum('ios','android') NOT NULL,
  `promo_code` varchar(45) NOT NULL,
  `test_user` int(1) DEFAULT '0',
  `customer_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `phone_unique` (`phone_number`),
  KEY `index6` (`email`),
  KEY `index7` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` VALUES (0,'Juan José ','Lovera','+584241760995','juanloverab@gmail.com','4168491623','0','ios','promo_+584241760995',1,'cus_80qgJvPKM1pPPx'),(3,'Edgar','Davila','+584163623389','edgarjdq@gmail.com','4088160094','APA91bGjqBoIi_54UcDlggZ0QQlILTzcJeJC4-wHSgKbWpaL66pHX--csJwDUDdL0qNr7xISSHQb5s5H7H7PDc1TxnNsLUNuv_-2lqi5SRQZojEgqOf7PsiJVlYoV_D7llMtvgXLaZdm','android','promo_+584163623389',1,'cus_testtest'),(4,'Antonio','Perez Dieppa','+447575800009','aperezdieppa@gmail.com','4753198043','7e64d4400aba5f529e690797e67c9736bd6718db814a3ba25329f0f441ea5265','ios','promo_+447575800009',1,'cus_8GTHcEjauiYCq3'),(6,'Steph','Aldous','+447929213944','steph.aldous@googlemail.com','4526559995','cf6a8b8f857aae1308a6c10367f71390de03f04214eccfcdc4ee6d09febf5b57','ios','promo_+447929213944',1,NULL),(7,'antonio','bzdzj','+447535467370',NULL,'4174761641','APA91bHRrhZQ5J7AmIBbfsM1jon4LXuRpg655NIz8U8NWyP6yc9CbEiYUEjbhJJQeGwCqCvwignDWtvtDn2lDDyxD2nTUoj6UDnmy9QoqJCFYxcVa7YA2Bsvw74uFx42-AXqpPaoMGSd','android','promo_+447535467370',0,NULL),(10,'','','+14088060705','','3121101212','0','ios','promo_+14088060705',0,NULL),(11,NULL,NULL,'+447752674088',NULL,'710537122059567104','ef2cfd027b0645ca9aa57bd085f5eb65a592dba006307516c91f0ef6c06c4864','ios','promo_+447752674088',0,NULL),(12,NULL,NULL,'+447717171557',NULL,'710825598957051904','6194d7104c3f49ab9c65ddefb1fd890c00080d83b35b2cbded77c3fecf4bbf23','ios','promo_+447717171557',0,NULL),(13,NULL,NULL,'+447540797676',NULL,'710857067469340672','0','ios','promo_+447540797676',0,NULL),(14,NULL,NULL,'+447595182296',NULL,'710868021561180160','7765e44f6530e0915df81d85427cf4fd2cab242544a129150e9c0a3304b4a00d','ios','promo_+447595182296',0,NULL),(15,NULL,NULL,'+447833683486',NULL,'711152527857885184','55564a9c42a34ab1c0b9e2387cc3694dafb57ac794eaf41b933ae26c8ee440a5','ios','promo_+447833683486',0,NULL),(16,NULL,NULL,'+447879437846',NULL,'711316766014754817','613ddde630c4e86af5c2eb0ced608515eb6407e403ae1a52a3dc31c0dc906de1','ios','promo_+447879437846',0,NULL),(17,NULL,NULL,'+61451990490',NULL,'3150621961','b8b72fdfe439aac7f078946803ab6f79435826ff5daeff5897b4b93abdd54c07','ios','promo_+61451990490',0,NULL),(18,NULL,NULL,'+97336844440',NULL,'712043774336053248','34da9bff8c6c560a4a7bf207f1239ed75a8e590bfb0b57347f82c3618e9e0f4e','ios','promo_+97336844440',0,NULL),(19,NULL,NULL,'+19172443867',NULL,'712260665214410752','0','ios','promo_+19172443867',0,NULL),(20,NULL,NULL,'+447857087727',NULL,'712723387467055110','e232873007a0918096281b863826e131117f0726a8d1d33690ea1f502718189d','ios','promo_+447857087727',0,NULL),(21,NULL,NULL,'+14084442464',NULL,'4182508213','b5d7e6cdb7dd100a71f0e1b592a8fcaccf8c3076f831944791f4ff57540e7bcc','ios','promo_+14084442464',0,NULL),(22,NULL,NULL,'+447931764747',NULL,'716593555695869952','b504007eff499bd2fc6d35e9c124bf7162a50f82a3c6e63d5a7e88d5619dc452','ios','promo_+447931764747',0,NULL),(23,NULL,NULL,'+9613032227',NULL,'720836336643477504','0','ios','promo_+9613032227',0,NULL),(24,'Jalna','Soulage','+447581371755','jalna_soulage@hotmail.com','724667012182581248','d7387d33828435d4ed1e0d679f26683e7973273b5feceeedc8cf70748b694e89','ios','promo_+447581371755',0,NULL),(25,NULL,NULL,'+447832796311',NULL,'725367939902353408','0','ios','promo_+447832796311',0,NULL),(26,NULL,NULL,'+447769773368',NULL,'726425249349292032','e4c7a5953f18c1fe4af2251d570a60102474bd8b2858d2edbe40f9ca4c3a9c58','ios','promo_+447769773368',0,NULL),(27,NULL,NULL,'+584141342096',NULL,'726632539419475969','0','ios','promo_+584141342096',0,NULL),(28,NULL,NULL,'+96170333323',NULL,'727971875364712448','b66d67e72a66b5c2080b2989239ede3e9cd5e5f3dcb9a22793d408eff567af1a','ios','promo_+96170333323',0,NULL),(29,NULL,NULL,'+96179112098',NULL,'729026504496193536','22a8591ae4a9bb966fbe59c0e5da1c96b4b7c6d9e525a042e76d622809e794f4','ios','promo_+96179112098',0,NULL),(30,'','','+18183148446','','729574586749091840','0','ios','promo_+18183148446',0,NULL),(31,NULL,NULL,'+447973266751',NULL,'730117677507850240','5007fda96ffd2701fbce247c750e52e40156a62570deb7603a309f170773359e','ios','promo_+447973266751',0,NULL),(32,NULL,NULL,'+380667200901',NULL,'714500558632169472','380f11e9b76c90bb456a41dd2c0fc4036c88ad56e6ba2ed324ff4ccd1ff6c8d2','ios','promo_+380667200901',0,NULL),(33,NULL,NULL,'+380675763338',NULL,'731538214448697344','bde8f751324346e675b055cdb08b26329c632ba5cfa62d0fb6cc01c1c8a1a9f8','ios','promo_+380675763338',0,NULL),(34,NULL,NULL,'+18583498121',NULL,'732270851022802944','df845f33a54f19095c4ab147672a349653104fa6b27d40f9ae21fb6d99d3a41f','ios','promo_+18583498121',0,NULL);
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_PAYMENT_CARDS`
--

DROP TABLE IF EXISTS `USER_PAYMENT_CARDS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_PAYMENT_CARDS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `card_token` varchar(255) NOT NULL,
  `card_type` varchar(45) NOT NULL,
  `card_last_digits` varchar(45) NOT NULL,
  `stripe_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `unique_userid_cardtoken` (`user_id`,`card_token`),
  KEY `user_id_index` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_PAYMENT_CARDS`
--

LOCK TABLES `USER_PAYMENT_CARDS` WRITE;
/*!40000 ALTER TABLE `USER_PAYMENT_CARDS` DISABLE KEYS */;
INSERT INTO `USER_PAYMENT_CARDS` VALUES (19,0,'tok_17aI35DA4XS7TAHTUIfHEY4p','MasterCard','0860',NULL),(20,7,'tok_17aX8cDA4XS7TAHTo6sAKo8X','Visa','0029',NULL),(21,7,'tok_17aXqgDA4XS7TAHTyVgQCi53','Visa','0029',NULL),(22,7,'tok_17aXr7DA4XS7TAHTe1C6Bspb','Visa','0029',NULL),(23,0,'tok_17d92VDA4XS7TAHTXCAIEDaC','Visa','4242','cus_7suszz06Yd4ZVJ'),(24,0,'tok_17d962DA4XS7TAHTaRlr0Ro9','Visa','4242','cus_7suvfesmAqzbTE'),(25,0,'tok_17d9DGDA4XS7TAHTH7EwFOvi','Visa','4242','cus_7sv3b1jdRGDcN1'),(26,0,'tok_17d9EBDA4XS7TAHTKf8gRzwH','Visa','4242','cus_7sv4CHopEPXByl'),(27,0,'tok_17dAMeDA4XS7TAHTPp3w53FG','Visa','4242','cus_7swFHWfcDnzFUT'),(28,0,'tok_17dAQxDA4XS7TAHTOa4OVxhA','Visa','4242','cus_7swJHPBANHaZky'),(29,4,'tok_17e7MODA4XS7TAHT4ERCiHu2','Visa','4242','cus_7tvCxBTywNxHmL'),(30,0,'tok_17eFWNDA4XS7TAHTjFeQSR4V','Visa','4242','cus_7u3d8ryaCGzudm'),(31,0,'tok_17k5JpDA4XS7TAHT5JWR60C7','Visa','4242','cus_805UREyC5nJIAP'),(32,0,'tok_17kozWDA4XS7TAHTzog6zZbQ','Visa','4242','cus_80qgJvPKM1pPPx'),(33,4,'tok_17rdwuDA4XS7TAHTnti5Rnem','Visa','0014','cus_87tkN51UY5EsH2'),(34,4,'tok_17zwKhDA4XS7TAHTSh7JV9jK','Visa','0029','cus_8GTHcEjauiYCq3'),(35,4,'tok_182orFDA4XS7TAHTrLJCGugK','Visa','0029',NULL),(36,3,'tok_testtest','Visa','4242','cus_testtest'),(37,6,'tok_186SX0DA4XS7TAHThm2lZ1KY','Visa','2997',NULL);
/*!40000 ALTER TABLE `USER_PAYMENT_CARDS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_TOKENS`
--

DROP TABLE IF EXISTS `USER_TOKENS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_TOKENS` (
  `access_token` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ttl` bigint(20) NOT NULL DEFAULT '3600000',
  PRIMARY KEY (`access_token`),
  UNIQUE KEY `unique_access_token` (`access_token`),
  UNIQUE KEY `unique_user_id` (`user_id`),
  KEY `user_id_index` (`user_id`),
  KEY `access_token_index` (`access_token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_TOKENS`
--

LOCK TABLES `USER_TOKENS` WRITE;
/*!40000 ALTER TABLE `USER_TOKENS` DISABLE KEYS */;
INSERT INTO `USER_TOKENS` VALUES ('009e2263-dd5f-4e50-a715-50c8a6791d4e',30,'2016-05-09 07:37:10',0),('01639eb2-f0e4-4a3f-a0f1-bc4e8fe6d1fa',23,'2016-04-15 04:53:31',0),('0d5c2ae9-c67c-49d7-891d-2de16b80e1bc',28,'2016-05-04 21:28:23',0),('1f0d459b-0996-4626-b720-ba8433aeb5e6',12,'2016-03-18 13:53:13',0),('204c1854-cd95-4698-befd-74265b51fc35',18,'2016-03-21 22:33:57',0),('2a46c9d2-7060-489b-baca-d900873d6b5e',22,'2016-04-03 11:53:40',0),('302e6d3a-7c8e-4f19-ae0b-021f1b65e628',3,'2016-04-26 04:42:22',0),('361c307a-4f8e-4d14-9dc3-210480b2f611',31,'2016-05-17 12:12:38',0),('3a9524b5-baf0-4a5e-9050-9a033673dc06',17,'2016-03-21 04:42:32',0),('45709e92-0f4c-44ad-998a-c6f296fab25e',33,'2016-05-14 17:40:10',0),('4c8601b5-2327-4ca2-a767-c8c0cd91acf9',24,'2016-05-07 21:36:58',0),('4e5018da-cd6d-4419-92f0-f94fe2f0ccff',19,'2016-05-08 18:15:34',0),('5f290c15-52a3-4fac-a39c-522b8c5a4df4',11,'2016-05-11 07:06:22',0),('65ca8bb0-453a-4bd0-8e0b-4e3eeb902409',21,'2016-03-28 19:38:40',0),('748bc271-8ad5-41fc-8fc1-af1a9b6941d3',1,'2016-01-10 19:54:14',0),('86e5ed05-433d-42c9-b11a-cb26bb66138a',15,'2016-03-19 11:36:06',0),('8e579f56-4281-4f41-bfd1-89eedd34d8e8',20,'2016-03-25 22:42:42',0),('a10d4ff8-2498-44ca-a29d-5637d4dc1a26',2,'2016-01-10 19:51:42',0),('a1826655-bf29-492e-9b53-75d074cfe083',16,'2016-03-19 22:25:00',0),('a41eb111-9439-466f-9f91-da0fbf3529c6',32,'2016-05-13 13:59:19',0),('b6a7dbb0-19b9-4643-a916-091d79f13aa7',14,'2016-03-18 16:41:48',0),('c3fe9570-ead1-492d-b556-0b10f71341e1',5,'2016-01-10 19:49:43',0),('ccf518a0-4023-4447-b5ad-42204764fcee',34,'2016-05-16 18:11:30',0),('d2f3deac-b8c1-485b-87d0-104ed9856c89',29,'2016-05-07 19:19:13',0),('d9ee8099-0a17-4f87-8fd4-4ea166c8e533',25,'2016-05-10 17:31:41',0),('e2eb3cbb-a055-40e0-bb82-d2e571517a22',4,'2016-05-15 23:17:47',0),('ebac9710-0a23-4bc5-b67a-223c0a2c5c82',27,'2016-05-01 04:46:10',0),('ecb6fcb5-9035-4f4c-a844-979fa700831e',0,'2016-05-16 00:29:00',0),('ef35a7cb-af66-4dda-80bf-6e60066aeb76',7,'2016-02-03 21:04:04',0),('f078808c-7955-4d10-8ffe-59b14bf12bdf',10,'2016-03-14 20:26:25',0),('f7fff858-b03e-43fb-ba22-988d74147422',26,'2016-04-30 22:04:19',0),('fbeb5fd3-69f7-46d3-9ff9-3d6ad488090a',6,'2016-05-01 22:32:03',0),('fbfd6874-77e7-40c8-bd29-b28497688e7e',13,'2016-03-18 15:58:16',0);
/*!40000 ALTER TABLE `USER_TOKENS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WEB_USERS`
--

DROP TABLE IF EXISTS `WEB_USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WEB_USERS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WEB_USERS`
--

LOCK TABLES `WEB_USERS` WRITE;
/*!40000 ALTER TABLE `WEB_USERS` DISABLE KEYS */;
INSERT INTO `WEB_USERS` VALUES (1,'Antonio','Perez Dieppa','aperezdieppa@gmail.com'),(2,'asdf','asdf','asdf@asdasd.com'),(3,'asdf','asdf','asda@asdasd.com'),(4,'s','s','sdg@asdasd.com'),(5,'Antonio','Perez','aperezdieppa@gmail.com'),(6,'pedro','juan','asdasd@asdasd.com'),(7,'adf','asd','ASD@ASD.COM'),(8,'John','Cena','john@cena.com'),(9,'df','df','gklu@hotmail.com'),(10,'Pedro','Jimenez','papa@gmail.com'),(11,'Steph','Aldous','steph.aldous@googlemail.com'),(12,'Steph','Aldous','stephanie.aldous@cancer.org.uk'),(13,'R','Thorp','ron.thorp@gmail.com'),(14,'James','Teodorini','jamesteodorini@gmail.com'),(15,'Mica','Castillo','micadaniellecastillo@gmail.com'),(16,'Aisling','Finneran','aisling.finneran@gmail.com'),(17,'Hayley','Sorrell','hayley.sorrell@gmail.com'),(18,'Kathryn','Weir','kathryn.weir@cancer.org.uk'),(19,'Josie','Downey','downey.josie@gmail.com'),(20,'Oliver','Welch','oliver.welch@cancer.org.uk'),(21,'Joanne','Lila','lila');
/*!40000 ALTER TABLE `WEB_USERS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
