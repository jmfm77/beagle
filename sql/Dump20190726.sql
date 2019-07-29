CREATE DATABASE  IF NOT EXISTS `machine` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `machine`;
-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: machine
-- ------------------------------------------------------
-- Server version	5.7.25-log

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
-- Dumping data for table `secured_accounts`
--

LOCK TABLES `secured_accounts` WRITE;
/*!40000 ALTER TABLE `secured_accounts` DISABLE KEYS */;
INSERT INTO `secured_accounts` VALUES (57,'werw','erwer','MghfYJbnedlCY/3NMZ0ZKw==',35,'SV5LayiaHz41jFGqIKm1uQ==','werwerwer'),(58,'werwe','rwerw','MghfYJbnedlCY/3NMZ0ZKw==',35,'SV5LayiaHz41jFGqIKm1uQ==','erwerwer'),(59,'wer','wer','MghfYJbnedlCY/3NMZ0ZKw==',35,'SV5LayiaHz41jFGqIKm1uQ==','wer'),(60,'werwer','werwer','MghfYJbnedlCY/3NMZ0ZKw==',35,'SV5LayiaHz41jFGqIKm1uQ==','werwerwer'),(61,'wer','wer','MghfYJbnedlCY/3NMZ0ZKw==',35,'SV5LayiaHz41jFGqIKm1uQ==','wer'),(62,'sqw','sqwsqw','MghfYJbnedlCY/3NMZ0ZKw==',26,'SV5LayiaHz41jFGqIKm1uQ==','sqwsqws'),(63,'sqw','sqwsqw','MghfYJbnedlCY/3NMZ0ZKw==',26,'SV5LayiaHz41jFGqIKm1uQ==','sqwsqws'),(64,'eqweqwe','qweq','MghfYJbnedlCY/3NMZ0ZKw==',35,'SV5LayiaHz41jFGqIKm1uQ==','weqweawqe'),(65,'cuenta 1','descripción1xwxwx','MghfYJbnedlCY/3NMZ0ZKw==',40,'SV5LayiaHz41jFGqIKm1uQ==','daoedueio1'),(72,'sqws','qws','MghfYJbnedlCY/3NMZ0ZKw==',35,'SV5LayiaHz41jFGqIKm1uQ==','qwsqwsqw'),(74,'1','765','qPvGe4ZcYNjaiGbLlP9Y4LiSMWMfaE5BEL9d4e7jIFUkWiAEHieU7ZoCUHJrW0m1uTfreCi9RcDb\r\nskMLfsk6JyNsuo/VWzy93E8dZcx7xr4mODADN6zz7uYlSm/sRLbAtukJBnx7Z6TZIEQswvJBaCQ6\r\nDuzf6E9BBWA92i0wnVIiPyyjYhkYLDAwCU+KGw4QtlVC3puZPBex+X+vCWqLBBYECr9ofVwepEAJ\r\nactoEL/K7Z5+wil9ZvOW6hfgw9yYTBIJQXjUumdRN+ggvsG8z1fwg4bWS0LmMymxffqI/oRGkjSJ\r\ng2PugeSz1ZsRtGx7dYnIasnlCmbCq/MCx7uFOA==',35,'TEnNMMobSIjLW/Ps0V0I0A==','7657');
/*!40000 ALTER TABLE `secured_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (29,'jm@fm10.es','$2a$10$q7wBH3UZ4.Rjba9Ojlo1NeLb8/kirZkLFTyfPRoT1p6HzTfBFo5uW','ROLE_ADMIN',NULL,'0000-00-00 00:00:00',NULL),(33,'admin@admin.es','$2a$10$beOCwLGeWGbzGnFRG4VHhOZgIFrhg2HihHvGJKlfqWJb/q2CKskT2','ROLE_ADMIN',NULL,'0000-00-00 00:00:00',NULL),(34,'jm@fm9.es','$2a$10$EeaW3I9YBPoq7atFy27bhOAcNLrzAv2SW/nbsBDXMfrx6eLnlIFVe','ROLE_ADMIN',NULL,'0000-00-00 00:00:00',NULL),(35,'jm@fm2.es','$2a$10$.m6QJtuysJWuS17rHGgPauv9wC3Gs.qXQvbaA9ROt8X/F2sxiT8iy','ROLE_USER',NULL,'0000-00-00 00:00:00',NULL),(36,'jm@fm3.es','$2a$10$3OQ1KPFLBAIXFIBsNeXf/.VdU/LThUetnnppi94H41Z7jPhaDNnIy','ROLE_USER',NULL,'0000-00-00 00:00:00',NULL),(37,'jm@fm4.es','$2a$10$1gTCyQYlPZDzY3D/A1Ce9.dOMzWAcWoGejq00YC0mjPToX6sUiXEW','ROLE_USER',NULL,'0000-00-00 00:00:00',NULL),(38,'jm@fm12.es','$2a$10$D2ZSRUYsbaeRCyGISMAQSeunT1Uf6zrn64pdQcv9eYMLqZDfaHg9W','ROLE_USER',NULL,'0000-00-00 00:00:00',NULL),(40,'jmfm77@gmail.com','$2a$10$7cNZQ1MWEfJZhoSnHzBkDO2E/s19tv8ir1ftDXoYrT2zpnxeyTR9S','ROLE_USER','37bbd094-8a0c-404b-967f-a99611b66c95','2019-07-29 18:38:33','2019-07-31 21:41:55'),(42,'jm@fm22.es','$2a$10$/V5Dsb9PtzzRjVi8a3X.3u27tfhgG9EktfrwN5kNmgt5JcO2lEpU6','ROLE_USER',NULL,'2019-07-31 21:28:42',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-31 21:56:26
