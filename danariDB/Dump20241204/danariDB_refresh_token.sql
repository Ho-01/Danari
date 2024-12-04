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
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration_time` datetime(6) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=477 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (1,'2024-11-21 09:42:14.652666','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTczMTU3NzMzNCwiZXhwIjoxNzMyMTgyMTM0fQ.vMCXNHTQYHpSgl6Irh_G8RIOuaCrWteM2s-hJCRzzME','username'),(408,'2024-12-04 15:25:25.461064','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJpYXQiOjE3MzI3MjExMjUsImV4cCI6MTczMzMyNTkyNX0.ueGlFNf2vrqkou1ro41Yr4dj7w9Wzd8e7KB7cNxF_vw','string'),(409,'2024-12-04 15:34:35.664838','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJpYXQiOjE3MzI3MjE2NzUsImV4cCI6MTczMzMyNjQ3NX0.8CVdwj8CnODZMrWdgmFkpEn3V0kgx_YeRt9z5wDu3bI','tester'),(446,'2024-12-04 19:18:06.219403','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4a3J0cGdrIiwiaWF0IjoxNzMyNzM1MDg2LCJleHAiOjE3MzMzMzk4ODZ9.fz07x2Riia6PK5MhXjXsP3PsOTtTUTvKcT4j_2NzG4o','xkrtpgk'),(447,'2024-12-04 19:21:04.657174','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkbHRqZGdoIiwiaWF0IjoxNzMyNzM1MjY0LCJleHAiOjE3MzMzNDAwNjR9.wPvvnRtyAGwYI4olRAIUsnnGlCmtSuwRGxbCwt5tF7w','dltjdgh'),(448,'2024-12-04 19:22:02.980861','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJybGF0bWRnaCIsImlhdCI6MTczMjczNTMyMiwiZXhwIjoxNzMzMzQwMTIyfQ.gqC2JTj-HVpnYpTLv7C2TlgtHXXVlsBdEeEF5dhSHpE','rlatmdgh'),(454,'2024-12-05 01:21:05.616229','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1kYW4iLCJpYXQiOjE3MzI3NTY4NjUsImV4cCI6MTczMzM2MTY2NX0.ObY2e-s-io-uGJFpJtgFpxnfx9n0QO79xF9DYpwnLyc','kimdan'),(474,'2024-12-10 12:56:41.935371','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW5rb29rIiwiaWF0IjoxNzMzMjMwNjAxLCJleHAiOjE3MzM4MzU0MDF9.11QeQ7MU61vF_AnEqU1Ot-hIUp7d55AgpcAEtxVSt3c','dankook'),(476,'2024-12-10 13:06:23.045347','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtdW1hbGlmZSIsImlhdCI6MTczMzIzMTE4MywiZXhwIjoxNzMzODM1OTgzfQ.U5gHiDnUiYsRnTA-Cfu7oCPflc9DlD-VoWQ2FHQ8sxs','mumalife');
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
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

-- Dump completed on 2024-12-04 21:47:10
