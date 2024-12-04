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
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `image_urls` varbinary(255) DEFAULT NULL,
  `post_content` longtext,
  `post_title` varchar(255) DEFAULT NULL,
  `post_type` enum('CLUB_EVENT','CLUB_RECRUITMENT') DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `club_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FK83s99f4kx8oiqm3ro0sasmpww` (`member_id`),
  KEY `FKhg8a0mya5n67tywax2khf2yuh` (`club_id`),
  CONSTRAINT `FK83s99f4kx8oiqm3ro0sasmpww` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKhg8a0mya5n67tywax2khf2yuh` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'2024-11-27 08:23:21.000000',_binary 'https://imgur.com/a/GK7smhP,https://imgur.com/a/gS8kgEj,https://imgur.com/a/1LKHojz','?안녕하세요 단국대학교(죽전) 중앙동아리 밴드 ‘MUSE’입니다. 신입부원 모집 안내 및 오디션 공지 드립니다!?\n?올해 마지막 신입부원 모집?\n\n?모집 글 아래에 오디션 공지가 있으니 끝까지 읽어주시기 바랍니다!\n\n?활동계획:\n교내버스킹, 정기 공연, 학교 축제 공연, 홍대 라이브홀 대관 공연등을 진행하고 있습니다. 이번 2학기에는 동아리 알림제, 창립제 공연, 가을 정기공연, 대학연합공연, 홍대공연 등이 예정되어 있습니다!\n\n?모집분야: 보컬, 기타, 베이스, 드럼, 키보드\n특히 ?키보드? 선생님들 격하게 환영합니다?\n\n?1차 모집기간: 종료\n\n?2차 모집기간:\n8월 26일 ~ 9월 5일\n알림제 끝나는 날 6시까지 지원 받겠습니다!\n\n?지원방법:\n알림제 당일 부스에서 지원 가능\n\n?오디션 참고사항:\n<지정곡 안내>\n\n?보컬\n남자: YB-박하사탕 + 외국 밴드곡 1곡\nOR\nMUSE - time is running out + 자유곡 1곡\n\n여자: 체리필터-낭만고양이 + 자유곡 1곡\n\n?기타\nSum 41 - Hell Song(인트로~기타솔로) + 자유곡 하나(필수 아님)\n\n?드럼\n드럼 패드로 4, 8, 16비트, 셋잇단음표 각각 2마디씩 치면서 100-140bpm 템포업 + 자유곡 1절\n\n?베이스\nMuse - time is running out(1절) + 자유곡 하나(필수 x)\n\n?키보드\nToto - Hold the line, 윤하 - 혜성 중 택1 + 자유곡(필수)\n\n? 세션별 기타 사항:\n\n1.\n보컬:mr로 진행 기타&베이스&드럼&키보드:메트로놈 틀고 진행\n\n2.\n오디션 후, 간단한 질문이 있을 예정입니다.\n\n3.\n각 세션별 오디션 날짜는 따로 공지하겠습니다. 궁금한 점은 연락 주시면 안내해드리겠습니다.\n\n2차 오디션은\n9월 6일 ~ 9월 10일 사이에 진행합니다!\n\n장소: 혜당관 604호에서 대기, 606호에서 오디션 진행\n\n?음악을 사랑하시는 분\n\n?악기 연주를 좋아하시는 분\n\n?자신의 끼를 발산하고 싶은 분\n\n?함께 음악얘기를 나눌 수 있는 친구가 필요한 분\n\n?밴드음악을 좋아하시는 분\n\n이라면 환영입니다!\n\n뮤즈와 함께 해요??\n궁금하신 점이 있으시다면 아래 연락처로 연락 주시거나 혜당관 <604호>를 찾아주세요!!\n분위기 정말 좋으니 동방 한 번 방문해주세요!!?\n\n가입문의: 장준익 010-8211-1307\n인스타:dku_muse\n(인스타를 참고하시면 그동안 진행했던 공연영상을 볼 수 있습니다!?)','MUSE 34기 신입부원 모집 (마지막)','CLUB_RECRUITMENT',102,29),(2,'2024-11-27 12:00:01.000000',_binary 'https://imgur.com/a/C8DdJlN,https://imgur.com/a/lZ6BNTo,https://imgur.com/a/IGrE6kA,https://imgur.com/a/QSZz7Tf','[Aegis 신규 회원 모집]\n안녕하세요.\n2024년 2학기 Aegis 회장 정현우입니다.\n2024년도 2학기를 함께할 Aegis 회원님들을 모집합니다.\n\n✔Aegis는 컴퓨터를 공부하기를 원하는 학생들이 모여 연구 및 학습하는 학술동아리입니다.\n- 개발, 보안을 희망하는 학생들이 모여 서로 공부하고 연구 및 공유를 지향합니다.\n\n✔Aegis는 컴퓨터와 관련된 다양한 활동을 진행합니다.\n1. 인근 학교 학생들을 대상으로 코딩 수업을 진행하는 봉사활동\n2. Aegis 졸업자들을 초청하여 진행하는 세미나\n3. 해킹대회와 코딩대회 또는 해커톤\n4. 컴퓨터 기초부터 심화까지 다양한 주제의 스터디\n5. 대외 컨퍼런스 또는 대회 참여\n\n✔컴퓨터를 하나도 몰라도 컴퓨터에 관심과 흥미가 있는 모든 단국대학교 학생들은 언제나 가입이 가능합니다.\n\n✔ 2학기 10,000원의 회비를 받고 있습니다.\n\n✔ 동아리 회칙 - 회칙 꼭 읽어주시면 감사하겠습니다!\nhttps://dk-aegis.org/?page_id=235\n\n✔ 가입 시 별도의 면접이나 제한사항은 절대 없습니다!\n✔ 누구나 가입 가능합니다 :)\n\n✔ 문의\n1. 회장 정현우 010-9845-3537\n2. 동아리방 : 혜당관 530호\n3. 인스타그램 : @dku_aegis\n4. 동아리 홈페이지 : https://dk-aegis.org\n\n가입 링크\nhttps://forms.gle/o2yLyC9iSnrPzfrw6','죽전 중앙동아리 Aegis 2024년 2학기 신규 회원 모집','CLUB_RECRUITMENT',152,20),(3,'2024-11-27 12:04:44.000000',NULL,'안녕하세요! \n이번에 Kitri에서 학교/동아리를 대상으로 화이트햇 스쿨 3기 모집설명회를 신청 받고있습니다. 이와 관련하여 저희 학교에서도 보안 및 해킹을 포함한 다양한 분야에 관심있는 분들을 대상으로 모집설명회를 진행하려고 합니다.\n화이트햇 스쿨 커리큘럼은 홈페이지에 나와있으니 궁금하신 분들은 참고하거나 저에게 개인적으로 편하게 물어보셔도 됩니다!\n본 행사가 기획되면 이지스 주관으로 행사가 진행되지만 동아리 소속이 아니신 분들도 참여 가능합니다!\n12월이 시험기간임을 고려해 날짜 선정한 점 양해부탁드립니다.\n이지스를 통해 신청하신 분들도 정확한 수요를 위해 다시 신청해주시면 감사하겠습니다!\n많은 관심 부탁드립니다! 신청은 금요일(15일)까지 입니다:)\n\n날짜 : 12월 21일 오후 14시\n장소 : 단국대학교 ICT관 (상세 장소는 추후 공지 예정)','화이트햇 스쿨 3기 모집설명회','CLUB_EVENT',152,20),(4,'2024-11-27 12:06:53.000000',_binary 'https://imgur.com/a/MxtgYzU','Aegis에서 개최하는 “졸업생 초청 세미나”에 많은 학생들의 참여 부탁드립니다.\n\n일시: 9/25(수) 18:30 – 20:50\n장소: 미디어센터 507호\n참여자: 세미나 주제에 관심있는 단국대학교 재학생 누구나\n\n발표자 및 내용\n1) 이현재: 대학일기 (대학교 당시 했던 활동들)\n2) 박찬호: 진입장벽 올라타기 전략\n3) 이주선: 문과생이었던 내가 지금은 해커?\n\n자세한 사항은 첨부한 홍보물 참고하시길 바랍니다.\n고맙습니다.','졸업생 초청 세미나','CLUB_EVENT',152,20),(5,'2024-11-27 12:40:23.000000',_binary 'https://imgur.com/a/mDGNRch,https://imgur.com/a/bBuyY87','단국대학교 역사상 최고의 실력과 폼을 유지하는 두 밴드 동아리가 연합 공연을 진행합니다!!\n\n시간: 2024년 11월 23일(토) 오후 5시\n\n장소: 분당 모던 K 실용음악학원 공연장(경기도 성남시 분당구 야탑동 354-4)\n\n<티켓 가격>\n사전 예매: 1인 5,000원\n현장 예매: 1인 7,000원\n\n예매폼: https://form.naver.com/response/7JvPaUAOVXa8vZS5UUdLcg\n\n* 블랙베어즈&뮤즈의 클럽공연 사전 예매 링크입니다. 사전 예매 시 할인 헤택이 있습니다. 현장에서 이름과 전화번호를 확인 후 티켓을 배분할 예정입니다.\n\nPoster: @h.y_graphic @lapiz_.__','?2024 Black Bears X Muse 연합 공연?','CLUB_EVENT',102,29);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
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
