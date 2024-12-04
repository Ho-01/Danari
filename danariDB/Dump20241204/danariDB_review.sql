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
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `review_content` varchar(255) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `club_id` bigint NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `FKk0ccx5i4ci2wd70vegug074w1` (`member_id`),
  KEY `FKn4ceqc9jqta3syecui557cc1u` (`club_id`),
  CONSTRAINT `FKk0ccx5i4ci2wd70vegug074w1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKn4ceqc9jqta3syecui557cc1u` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'2024-11-27 08:31:19.000000','MUSE 동아리 활동을 통해 많은 경험과 추억을 쌓았습니다! 동아리 분위기도 너무 좋고, 여러 공연에서 의미 있는 시간을 보냈습니다.',102,29),(53,'2024-11-27 16:17:17.200719','소감 한 마디 씩 부탁드립니다!!',152,20),(54,'2024-11-27 16:18:39.835570','재밌어보여요!!!',152,21),(74,'2024-11-27 19:13:31.665963','활동을 많이 해서 좋아요!!',155,20),(75,'2024-11-27 19:14:40.697596','이지스 최고 ^_^',155,20),(76,'2024-11-27 19:21:36.398282','막학기 알차게 보내고 갑니다~',156,20),(77,'2024-11-27 19:22:38.414683','뮤직 이즈 마이라이프.',154,29),(78,'2024-11-28 01:22:17.728972','muse 화이팅~',153,29),(79,'2024-11-28 01:30:28.790439','좋아요~',153,29),(80,'2024-11-28 01:54:50.831822','송승준 좋아요~',153,29),(81,'2024-11-28 02:32:23.847306','이지스 좋아요',153,20),(82,'2024-11-28 02:41:53.655244','굿~',153,29),(83,'2024-11-28 04:47:56.442695','괜찮은거같아요',153,29),(84,'2024-11-28 05:02:44.937152','좋아요',153,29),(85,'2024-11-28 05:02:46.202463','',153,29),(86,'2024-11-28 05:19:45.083247','굿',153,20),(87,'2024-11-28 05:26:06.920202','이서진 굿',153,29),(88,'2024-11-28 06:07:51.830480','홍승조 굿~',153,29),(89,'2024-11-28 06:17:49.434188','좋아요',153,29),(90,'2024-11-28 06:21:38.948471','굿',153,29),(91,'2024-11-28 06:26:27.475169','..',153,29),(92,'2024-11-28 06:56:23.307343','...',153,20),(93,'2024-12-03 13:08:43.846654','화이팅~~~',153,29);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
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

-- Dump completed on 2024-12-04 21:47:09
