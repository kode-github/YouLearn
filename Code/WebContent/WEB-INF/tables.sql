CREATE DATABASE  IF NOT EXISTS `youlearndb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `youlearndb`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: youlearndb
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account` (
  `nome` varchar(25) NOT NULL,
  `cognome` varchar(25) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `verificato` tinyint(1) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartadicredito`
--

DROP TABLE IF EXISTS `cartadicredito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cartadicredito` (
  `numeroCarta` varchar(16) NOT NULL,
  `meseScadenza` varchar(20) NOT NULL,
  `annoScadenza` varchar(20) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `nomeIntestatario` varchar(50) NOT NULL,
  `accountMail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`numeroCarta`),
  KEY `Account_idx` (`accountMail`),
  CONSTRAINT `Account` FOREIGN KEY (`accountMail`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartadicredito`
--

LOCK TABLES `cartadicredito` WRITE;
/*!40000 ALTER TABLE `cartadicredito` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartadicredito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commento`
--

DROP TABLE IF EXISTS `commento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `commento` (
  `idcommento` int(11) NOT NULL,
  `NumeroLezione` int(11) DEFAULT NULL,
  `IdCorso` int(11) DEFAULT NULL,
  `testo` varchar(1024) NOT NULL,
  `AccountMail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idcommento`),
  KEY `b_idx` (`IdCorso`,`NumeroLezione`),
  KEY `creatore_idx` (`AccountMail`),
  CONSTRAINT `creatore` FOREIGN KEY (`AccountMail`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lezione` FOREIGN KEY (`IdCorso`, `NumeroLezione`) REFERENCES `lezione` (`corsoidcorso`, `numerolezione`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commento`
--

LOCK TABLES `commento` WRITE;
/*!40000 ALTER TABLE `commento` DISABLE KEYS */;
/*!40000 ALTER TABLE `commento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corso`
--

DROP TABLE IF EXISTS `corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `corso` (
  `idCorso` int(11) NOT NULL AUTO_INCREMENT,
  `accountCreatore` varchar(255) NOT NULL,
  `accountSupervisore` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `descrizione` varchar(1048) NOT NULL,
  `dataCreazione` date NOT NULL,
  `dataFine` date NOT NULL,
  `copertina` varchar(255) NOT NULL,
  `prezzo` varchar(10) NOT NULL,
  `stato` varchar(20) NOT NULL,
  `categoria` varchar(30) NOT NULL,
  `nLezioni` int(10) NOT NULL,
  `nIscritti` int(10) NOT NULL,
  PRIMARY KEY (`idCorso`),
  KEY `corso_ibfk_1` (`accountCreatore`),
  KEY `corso_ibfk_2` (`accountSupervisore`),
  CONSTRAINT `corso_ibfk_1` FOREIGN KEY (`accountCreatore`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `corso_ibfk_2` FOREIGN KEY (`accountSupervisore`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corso`
--

LOCK TABLES `corso` WRITE;
/*!40000 ALTER TABLE `corso` DISABLE KEYS */;
/*!40000 ALTER TABLE `corso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iscrizione`
--

DROP TABLE IF EXISTS `iscrizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `iscrizione` (
  `accountMail` varchar(255) NOT NULL,
  `corsoIdCorso` int(11) NOT NULL,
  `dataPagamento` date NOT NULL,
  `importo` double NOT NULL,
  `fattura` int(10) NOT NULL,
  PRIMARY KEY (`accountMail`,`corsoIdCorso`),
  UNIQUE KEY `fattura` (`fattura`),
  KEY `iscrizione_ibfk_2` (`corsoIdCorso`),
  CONSTRAINT `iscrizione_ibfk_1` FOREIGN KEY (`accountMail`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `iscrizione_ibfk_2` FOREIGN KEY (`corsoIdCorso`) REFERENCES `corso` (`idcorso`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iscrizione`
--

LOCK TABLES `iscrizione` WRITE;
/*!40000 ALTER TABLE `iscrizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `iscrizione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lezione`
--

DROP TABLE IF EXISTS `lezione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lezione` (
  `corsoIdCorso` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `visualizzazione` int(32) NOT NULL,
  `numeroLezione` int(10) NOT NULL,
  PRIMARY KEY (`corsoIdCorso`,`numeroLezione`),
  CONSTRAINT `lezione_ibfk_1` FOREIGN KEY (`corsoIdCorso`) REFERENCES `corso` (`idcorso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lezione`
--

LOCK TABLES `lezione` WRITE;
/*!40000 ALTER TABLE `lezione` DISABLE KEYS */;
/*!40000 ALTER TABLE `lezione` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `lezione_AFTER_INSERT` AFTER INSERT ON `lezione` FOR EACH ROW BEGIN
update corso 
    set nlezioni=nlezioni+1
    where corso.idCorso=NEW.corsoIdCorso;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `lezione_AFTER_DELETE` AFTER DELETE ON `lezione` FOR EACH ROW BEGIN
update corso 
    set nlezioni=nlezioni-1
    where corso.idCorso=OLD.corsoIdCorso;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'youlearndb'
--

--
-- Dumping routines for database 'youlearndb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-21 23:39:41
