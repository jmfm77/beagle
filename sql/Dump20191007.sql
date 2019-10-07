CREATE DATABASE  IF NOT EXISTS `ml_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `ml_1`;
-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: ml_1
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
-- Dumping data for table `auth_group`
--

LOCK TABLES `auth_group` WRITE;
/*!40000 ALTER TABLE `auth_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auth_group_permissions`
--

LOCK TABLES `auth_group_permissions` WRITE;
/*!40000 ALTER TABLE `auth_group_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_group_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auth_permission`
--

LOCK TABLES `auth_permission` WRITE;
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` VALUES (1,'Can add log entry',1,'add_logentry'),(2,'Can change log entry',1,'change_logentry'),(3,'Can delete log entry',1,'delete_logentry'),(4,'Can view log entry',1,'view_logentry'),(5,'Can add permission',2,'add_permission'),(6,'Can change permission',2,'change_permission'),(7,'Can delete permission',2,'delete_permission'),(8,'Can view permission',2,'view_permission'),(9,'Can add group',3,'add_group'),(10,'Can change group',3,'change_group'),(11,'Can delete group',3,'delete_group'),(12,'Can view group',3,'view_group'),(13,'Can add user',4,'add_user'),(14,'Can change user',4,'change_user'),(15,'Can delete user',4,'delete_user'),(16,'Can view user',4,'view_user'),(17,'Can add content type',5,'add_contenttype'),(18,'Can change content type',5,'change_contenttype'),(19,'Can delete content type',5,'delete_contenttype'),(20,'Can view content type',5,'view_contenttype'),(21,'Can add session',6,'add_session'),(22,'Can change session',6,'change_session'),(23,'Can delete session',6,'delete_session'),(24,'Can view session',6,'view_session');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'pbkdf2_sha256$150000$WIvtJM3vLWIJ$L9dtXhRKYlOLmyhGVUHlz5Hnj27xV7ucgyFeZzFLxE0=','2019-09-17 20:31:51.368982',1,'elk','','','',1,1,'2019-09-17 20:31:26.541648');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auth_user_groups`
--

LOCK TABLES `auth_user_groups` WRITE;
/*!40000 ALTER TABLE `auth_user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auth_user_user_permissions`
--

LOCK TABLES `auth_user_user_permissions` WRITE;
/*!40000 ALTER TABLE `auth_user_user_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_user_user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `django_admin_log`
--

LOCK TABLES `django_admin_log` WRITE;
/*!40000 ALTER TABLE `django_admin_log` DISABLE KEYS */;
INSERT INTO `django_admin_log` VALUES (1,'2019-09-18 18:42:29.618886','74','1',2,'[{\"changed\": {\"fields\": [\"description\", \"password\"]}}]',7,1),(2,'2019-09-20 14:47:23.956237','74','1',2,'[{\"changed\": {\"fields\": [\"description\"]}}]',7,1),(3,'2019-09-20 14:52:57.634480','74','1',2,'[{\"changed\": {\"fields\": [\"description\"]}}]',7,1),(4,'2019-09-20 14:53:07.366043','74','1',2,'[]',7,1),(5,'2019-09-20 14:53:31.618808','74','1',2,'[{\"changed\": {\"fields\": [\"description\"]}}]',7,1),(6,'2019-09-20 16:50:27.661894','74','1',2,'[{\"changed\": {\"fields\": [\"description\"]}}]',7,1),(7,'2019-09-21 13:21:09.950616','72','2',2,'[{\"changed\": {\"fields\": [\"name\", \"description\"]}}]',7,1),(8,'2019-09-21 14:44:37.319613','65','3',2,'[{\"changed\": {\"fields\": [\"name\", \"description\"]}}]',7,1),(9,'2019-09-21 14:46:10.844722','64','4',2,'[{\"changed\": {\"fields\": [\"name\", \"description\"]}}]',7,1),(10,'2019-09-21 17:09:57.054270','63','5',2,'[{\"changed\": {\"fields\": [\"name\", \"description\"]}}]',7,1);
/*!40000 ALTER TABLE `django_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `django_content_type`
--

LOCK TABLES `django_content_type` WRITE;
/*!40000 ALTER TABLE `django_content_type` DISABLE KEYS */;
INSERT INTO `django_content_type` VALUES (1,'admin','logentry'),(3,'auth','group'),(2,'auth','permission'),(4,'auth','user'),(7,'beagle','securedaccounts'),(8,'beagle','users'),(5,'contenttypes','contenttype'),(6,'sessions','session');
/*!40000 ALTER TABLE `django_content_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `django_migrations`
--

LOCK TABLES `django_migrations` WRITE;
/*!40000 ALTER TABLE `django_migrations` DISABLE KEYS */;
INSERT INTO `django_migrations` VALUES (1,'contenttypes','0001_initial','2019-09-17 20:27:29.968773'),(2,'auth','0001_initial','2019-09-17 20:27:30.354169'),(3,'admin','0001_initial','2019-09-17 20:27:31.646949'),(4,'admin','0002_logentry_remove_auto_add','2019-09-17 20:27:32.029561'),(5,'admin','0003_logentry_add_action_flag_choices','2019-09-17 20:27:32.070972'),(6,'contenttypes','0002_remove_content_type_name','2019-09-17 20:27:32.336251'),(7,'auth','0002_alter_permission_name_max_length','2019-09-17 20:27:32.477850'),(8,'auth','0003_alter_user_email_max_length','2019-09-17 20:27:32.556769'),(9,'auth','0004_alter_user_username_opts','2019-09-17 20:27:32.587930'),(10,'auth','0005_alter_user_last_login_null','2019-09-17 20:27:32.733813'),(11,'auth','0006_require_contenttypes_0002','2019-09-17 20:27:32.748361'),(12,'auth','0007_alter_validators_add_error_messages','2019-09-17 20:27:32.781259'),(13,'auth','0008_alter_user_username_max_length','2019-09-17 20:27:32.925493'),(14,'auth','0009_alter_user_last_name_max_length','2019-09-17 20:27:33.048573'),(15,'auth','0010_alter_group_name_max_length','2019-09-17 20:27:33.114087'),(16,'auth','0011_update_proxy_permissions','2019-09-17 20:27:33.160389'),(17,'sessions','0001_initial','2019-09-17 20:27:33.278745');
/*!40000 ALTER TABLE `django_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `django_session`
--

LOCK TABLES `django_session` WRITE;
/*!40000 ALTER TABLE `django_session` DISABLE KEYS */;
INSERT INTO `django_session` VALUES ('wfgl5du1o5urprfj7jmi3wherv1xqg90','YzYyN2I4ZmRiNDYwYjQ1M2IxNWY1OGQ2N2NlZmUyNDA4MjM5Mzk3Yjp7Il9hdXRoX3VzZXJfaWQiOiIxIiwiX2F1dGhfdXNlcl9iYWNrZW5kIjoiZGphbmdvLmNvbnRyaWIuYXV0aC5iYWNrZW5kcy5Nb2RlbEJhY2tlbmQiLCJfYXV0aF91c2VyX2hhc2giOiI1NDZhNWUyZWE4MGIxNzJmNWNlMWU3NTA5ZjJmZWIxMGYyMTA3MTNiIn0=','2019-10-01 20:31:51.377473');
/*!40000 ALTER TABLE `django_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `secured_accounts`
--

LOCK TABLES `secured_accounts` WRITE;
/*!40000 ALTER TABLE `secured_accounts` DISABLE KEYS */;
INSERT INTO `secured_accounts` VALUES (77,'Busqueda 2','Rosalía','',43,'','dd4815b3-bcf6-4558-8d5a-a0f1c282d3f3','1'),(78,'busqueda_3','Elecciones','',43,'','feb3088c-d3f5-442c-ac1e-b31c827897f5','1');
/*!40000 ALTER TABLE `secured_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (43,'seguridad2018.001@gmail.com','$2a$10$Q6n2EkSj8XnS9WPY/Z6x0OTmD6aENB84ZY1KrQZ.JcBxizHw6QqUm','ROLE_USER',NULL,'2019-10-05 16:45:16',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ml_1'
--

--
-- Dumping routines for database 'ml_1'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-07 17:59:35
