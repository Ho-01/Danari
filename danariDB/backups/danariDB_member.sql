-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: danari-2.cfuwoqymgasv.ap-northeast-2.rds.amazonaws.com    Database: danariDB
-- ------------------------------------------------------
-- Server version	8.0.35

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `student_id` int NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'InitMember','$2a$10$dF0J1G2AA84u.qiAiPhtyuaX3Qj/vd7LUFlnzTKJcvVjMIpNDMbke',32190000,'username'),(3,'string','$2a$10$w6wrXvVgQWwLjeZ7ei142eW9cjL0uT5m3bRCNyb.wJdFKLc9Pdvp6',0,'string'),(52,'InitMember','$2a$10$Bvvo6nldpOFdP/FxXYnCPOxTb43hvsxrv8wKyYLdBxxd0Uh3QYKpK',32190000,'username'),(102,'이성호','$2a$10$asgMAUh9gVAowk6VDPcXp.2iPBVoPXODNPSlysmwRhU/PU.u/xR82',32183186,'uploader'),(103,'탁세하','$2a$10$s8rkv09t89kErGrHGB9Ke.gFvxXlzOnAORat4tj9I6HWjEFC0Phb.',32214744,'tester'),(152,'김단국','$2a$10$PCGLBcmrxo.5PUkWtbQYwu8UvET1nPFbxBKKHob6i.rlpzxWJ1ERK',32214744,'kimdan'),(153,'김뮤즈','$2a$10$2ZrPL0bVzHwYBSzhrvNbjuRH06OjlF0OIqGDUpyWBmr/6K1DiV2ku',32214744,'mumalife'),(154,'김승호','$2a$10$J2PrTF8jlRpklTVcRcQNwuK9KaaSTgDwTthaL64USd5FelGDqDG3O',32214744,'rlatmdgh'),(155,'탁세하','$2a$10$2LHv0KqSEChF1Np.8zfcCO./gBO/HCQfFcQcnEieoRxSVJy1pqeWm',32214744,'xkrtpgk'),(156,'이성호','$2a$10$iRiDTjFWqnvnSKLjJi5UQ..xJkaMClEOM9RF4WrMlAm2hPMBy8TyG',32214744,'dltjdgh'),(157,'김단국','$2a$10$YCelEp5W89HmHNHxpELzd.u7kcY.ozHazo.D93M7hrEbrMtxVEqVi',32214744,'dankook');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-04 21:47:07
