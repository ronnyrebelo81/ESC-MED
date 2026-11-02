-- MySQL dump 10.13  Distrib 26.10.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: escala_plantao_medico
-- ------------------------------------------------------
-- Server version	26.10.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'a4b4de71-ce8c-11f1-8aeb-d09466c7b27b:1-52';

--
-- Table structure for table `escalas`
--

DROP TABLE IF EXISTS `escalas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `escalas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `unidade` varchar(100) NOT NULL,
  `ativa` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `escalas`
--

LOCK TABLES `escalas` WRITE;
/*!40000 ALTER TABLE `escalas` DISABLE KEYS */;
INSERT INTO `escalas` VALUES (1,'Escala de Teste','Unidade de Teste',1),(2,'Escala de Teste Java','Unidade de Teste Java',1),(3,'Escala Teste pelo Sistema','Unidade de Teste pelo Sistema',1);
/*!40000 ALTER TABLE `escalas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicos`
--

DROP TABLE IF EXISTS `medicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `crm` varchar(20) NOT NULL,
  `especialidade` varchar(80) DEFAULT NULL,
  `usuario` varchar(50) NOT NULL,
  `senha_hash` varchar(255) NOT NULL,
  `perfil` enum('ADMIN','MEDICO') NOT NULL DEFAULT 'MEDICO',
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `crm` (`crm`),
  UNIQUE KEY `usuario` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicos`
--

LOCK TABLES `medicos` WRITE;
/*!40000 ALTER TABLE `medicos` DISABLE KEYS */;
INSERT INTO `medicos` VALUES (1,'Medico Teste','CRM-TESTE-20261030','Clinica Geral','teste_medico_20261030','HASH_TEMPORARIO_TESTE','MEDICO',1),(2,'Administrador do Sistema','CRM-ADMIN-ESC001','Administracao','admin','210000:3YvZAto3HPv9d2v9JN005A==:3aJOo2/BQIvCWY8h4DsG26y6c+DOdLnbpD5H0wvHj5o=','ADMIN',1),(3,'Médico Teste Sistema','CRM-TESTE-002','Clínica Geral','medico_teste02','210000:JBeN4DmpP+3cOjSOCNwIrA==:W3twKiKhI/nJJUin0xo83eAQbNoLqFbcrkNXVD45No8=','MEDICO',1),(4,'Médico Teste Dois','CRM-TESTE-003','Clínica Geral','medico_teste03','210000:j8h8iD1lzoYLJeiWPM5yqQ==:wGK5YsRxS30E0Z6poSP9NTWhdEVm9sgY982nZk56zVU=','MEDICO',1),(6,'Médico Teste Três','CRM-TESTE-004','Clínica Geral','medico_teste04','210000:vR4HPyIZhiNBTPiO21/QCQ==:dWcl6Y0UHmOftVtayb3dXpvSEVb+fZtsQSmJ1Gtgt0s=','MEDICO',1);
/*!40000 ALTER TABLE `medicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plantoes`
--

DROP TABLE IF EXISTS `plantoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plantoes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `escala_id` int NOT NULL,
  `medico_id` int NOT NULL,
  `data_plantao` date NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fim` time NOT NULL,
  `status` enum('PROGRAMADO','REALIZADO','CANCELADO') NOT NULL DEFAULT 'PROGRAMADO',
  PRIMARY KEY (`id`),
  KEY `escala_id` (`escala_id`),
  KEY `medico_id` (`medico_id`),
  CONSTRAINT `plantoes_ibfk_1` FOREIGN KEY (`escala_id`) REFERENCES `escalas` (`id`),
  CONSTRAINT `plantoes_ibfk_2` FOREIGN KEY (`medico_id`) REFERENCES `medicos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plantoes`
--

LOCK TABLES `plantoes` WRITE;
/*!40000 ALTER TABLE `plantoes` DISABLE KEYS */;
INSERT INTO `plantoes` VALUES (1,1,1,'2026-11-01','07:00:00','19:00:00','PROGRAMADO'),(2,1,1,'2026-11-02','07:00:00','19:00:00','PROGRAMADO'),(3,1,3,'2026-11-01','07:00:00','19:00:00','PROGRAMADO'),(4,1,1,'2026-11-05','07:00:00','19:00:00','PROGRAMADO'),(5,1,1,'2026-11-02','07:00:00','19:00:00','PROGRAMADO'),(6,2,4,'2026-11-02','07:00:00','19:00:00','PROGRAMADO'),(7,1,4,'2026-11-02','07:00:00','19:00:00','PROGRAMADO'),(8,1,3,'2026-11-03','07:00:00','19:00:00','PROGRAMADO'),(9,1,4,'2026-11-04','13:00:00','19:00:00','PROGRAMADO'),(10,1,6,'2026-04-03','07:00:00','13:00:00','PROGRAMADO'),(11,1,6,'2026-11-03','13:00:00','19:00:00','PROGRAMADO'),(12,1,3,'2026-11-04','07:00:00','19:00:00','PROGRAMADO');
/*!40000 ALTER TABLE `plantoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trocas`
--

DROP TABLE IF EXISTS `trocas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trocas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `plantao_id` int NOT NULL,
  `medico_solicitante_id` int NOT NULL,
  `medico_substituto_id` int DEFAULT NULL,
  `nome_externo` varchar(100) DEFAULT NULL,
  `contato_externo` varchar(100) DEFAULT NULL,
  `status` enum('PENDENTE','APROVADA','RECUSADA') NOT NULL DEFAULT 'PENDENTE',
  `data_solicitacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `plantao_id` (`plantao_id`),
  KEY `medico_solicitante_id` (`medico_solicitante_id`),
  KEY `medico_substituto_id` (`medico_substituto_id`),
  CONSTRAINT `trocas_ibfk_1` FOREIGN KEY (`plantao_id`) REFERENCES `plantoes` (`id`),
  CONSTRAINT `trocas_ibfk_2` FOREIGN KEY (`medico_solicitante_id`) REFERENCES `medicos` (`id`),
  CONSTRAINT `trocas_ibfk_3` FOREIGN KEY (`medico_substituto_id`) REFERENCES `medicos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trocas`
--

LOCK TABLES `trocas` WRITE;
/*!40000 ALTER TABLE `trocas` DISABLE KEYS */;
INSERT INTO `trocas` VALUES (1,1,1,NULL,'Medico Externo Teste','(63) 99999-0000','RECUSADA','2026-10-30 02:11:54'),(2,4,3,NULL,'Ronny Teste','63 94441-1680','APROVADA','2026-11-01 19:21:23'),(3,4,3,1,NULL,NULL,'APROVADA','2026-11-01 19:46:05'),(4,7,3,4,NULL,NULL,'APROVADA','2026-11-01 20:05:47'),(5,8,3,4,NULL,NULL,'RECUSADA','2026-11-01 20:38:11'),(6,9,3,4,NULL,NULL,'APROVADA','2026-11-01 20:38:23'),(7,10,3,6,NULL,NULL,'APROVADA','2026-11-02 00:52:00'),(8,12,3,6,NULL,NULL,'RECUSADA','2026-11-02 00:52:32');
/*!40000 ALTER TABLE `trocas` ENABLE KEYS */;
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

-- Dump completed on 2026-11-01 23:48:25
