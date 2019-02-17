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
INSERT INTO `account` VALUES ('Mario','Sessa','PentiumD','china@gmail.com','Utente',0),('Luigi','Crisci','PentiumD','luigicrisci1997@gmail.com','Utente',0),('Mario','XXXXXX','PentiumD','mario@mail.it','Utente',0),('Pasquale','Ambrosio','PentiumD','pasqualeAmbrosio@gmail.com','Supervisore',0),('Luigi','Tester','PentiumD','supervisore@gmail.com','Supervisore',0);
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
INSERT INTO `cartadicredito` VALUES ('4023600655123698','02','2020','PayPal','Luigi','luigicrisci1997@gmail.com'),('4023600666666666','02','2089','Visa','Mario','china@gmail.com'),('8888888888888888','02','2025','Visa','Mario','mario@mail.it');
/*!40000 ALTER TABLE `cartadicredito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commento`
--

DROP TABLE IF EXISTS `commento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `commento` (
  `idcommento` int(11) NOT NULL AUTO_INCREMENT,
  `idLezione` int(11) DEFAULT NULL,
  `testo` varchar(1024) NOT NULL,
  `AccountMail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idcommento`),
  KEY `creatore_idx` (`AccountMail`),
  CONSTRAINT `creatore` FOREIGN KEY (`AccountMail`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=449 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commento`
--

LOCK TABLES `commento` WRITE;
/*!40000 ALTER TABLE `commento` DISABLE KEYS */;
INSERT INTO `commento` VALUES (1,26,'Un commento','luigicrisci1997@gmail.com'),(2,26,'Un secondo commento','luigicrisci1997@gmail.com'),(3,26,'Un commento utente','pasqualeAmbrosio@gmail.com'),(8,20,'Un commento utente','pasqualeAmbrosio@gmail.com'),(9,20,'Un commento docente','luigicrisci1997@gmail.com'),(10,20,'Un secondo commento docente','luigicrisci1997@gmail.com'),(33,222,'EEEEEEEEEEEEE','luigicrisci1997@gmail.com'),(34,222,'Questo corso è tropp bell','china@gmail.com');
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
  `accountSupervisore` varchar(255) DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `descrizione` varchar(1048) NOT NULL,
  `dataCreazione` date NOT NULL,
  `dataFine` date NOT NULL,
  `copertina` varchar(255) NOT NULL,
  `prezzo` varchar(10) NOT NULL,
  `stato` varchar(20) NOT NULL,
  `categoria` varchar(30) NOT NULL,
  `nLezioni` int(10) NOT NULL DEFAULT '0',
  `nIscritti` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idCorso`),
  KEY `corso_ibfk_1` (`accountCreatore`),
  KEY `corso_ibfk_2` (`accountSupervisore`),
  CONSTRAINT `corso_ibfk_1` FOREIGN KEY (`accountCreatore`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `corso_ibfk_2` FOREIGN KEY (`accountSupervisore`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10015 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corso`
--

LOCK TABLES `corso` WRITE;
/*!40000 ALTER TABLE `corso` DISABLE KEYS */;
INSERT INTO `corso` VALUES (5,'luigicrisci1997@gmail.com','pasqualeAmbrosio@gmail.com','NonBellissimo',' Stupendo oiii             ','2018-02-02','2030-02-02','141668b4-4eee-4ae4-972b-d6266166c9e2.jpg','90','Attivo','Musica',2,1),(12,'china@gmail.com','pasqualeAmbrosio@gmail.com','SuperComunista','PER IL PROLETARIATO','1917-02-02','2089-02-02','azz.jpg','1','Attivo','Informatica',1,2),(24,'luigicrisci1997@gmail.com','pasqualeAmbrosio@gmail.com','PasqualeBellissimo','Pasquale è stupendo','2019-02-01','2025-09-06','bafa13cf-0077-4ac8-928a-ae5dfc309a49.jpg','100','Attivo','Informatica',0,0),(26,'luigicrisci1997@gmail.com','pasqualeAmbrosio@gmail.com','Corso di Giusy','Come diventare Giusy e vivere una vita felice','2019-02-04','2025-09-06','135ebdea-e2ff-426a-959d-c2cb6b87eae7.jpg','5','Attivo','Informatica',0,0),(30,'luigicrisci1997@gmail.com','pasqualeAmbrosio@gmail.com','LuigiEEEEE','  Il mio corso è il più bello anche dopo la modifica','2019-02-07','2029-09-06','f7b2cc8f-959a-46b0-9791-6573d1d91a2c.jpg','150','Attivo','Danza',0,0),(41,'pasqualeAmbrosio@gmail.com','pasqualeAmbrosio@gmail.com','TestIscrizione','aaaaaaaaaaaaaaaaaaaaaa','2019-02-11','2025-09-06','x','56','Attivo','Informatica',0,1),(56,'luigicrisci1997@gmail.com','pasqualeAmbrosio@gmail.com','Quanto e bello','aaaaaaaaaaaaaaaaaaaa','2019-02-15','2025-09-06','f9166b1f-af45-458e-a425-d56d97d7f24b.png','25','Attivo','Informatica',0,0),(10013,'luigicrisci1997@gmail.com','supervisore@gmail.com','Fotografia- che passione','La fotograia è bella proprio','2019-02-16','2025-09-06','15e1534c-a201-4d5e-ad03-868539f14fb7.jpg','35','Attivo','Informatica',2,0),(10014,'luigicrisci1997@gmail.com',NULL,'aaaaaaaaaaaaa','aaaaaaaaaaaaaaaa','2019-02-16','2025-09-06','be3e7362-0072-4273-87c4-e5c67d9b0210.png','25','Completamento','Informatica',2,0);
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
INSERT INTO `iscrizione` VALUES ('luigicrisci1997@gmail.com',12,'2019-01-10',89,555),('luigicrisci1997@gmail.com',41,'2019-02-12',56,1111111111),('mario@mail.it',5,'2019-02-15',90,1111111111),('mario@mail.it',12,'2019-02-15',1,1111111111);
/*!40000 ALTER TABLE `iscrizione` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `iscrizione_AFTER_INSERT` AFTER INSERT ON `iscrizione` FOR EACH ROW BEGIN
update corso 
    set nIscritti=nIscritti+1
    where idcorso=new.corsoIdCorso;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `iscrizione_BEFORE_DELETE` BEFORE DELETE ON `iscrizione` FOR EACH ROW BEGIN
update corso 
    set nIscritti=nIscritti-1
    where idcorso=old.corsoIdCorso;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `lezione`
--

DROP TABLE IF EXISTS `lezione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lezione` (
  `nome` varchar(255) NOT NULL,
  `visualizzazione` int(32) NOT NULL DEFAULT '0',
  `numeroLezione` int(10) DEFAULT '0',
  `IdLezione` int(11) NOT NULL AUTO_INCREMENT,
  `filePath` varchar(2048) NOT NULL,
  `corsoIdCorso` int(11) NOT NULL,
  PRIMARY KEY (`IdLezione`),
  KEY `corso_idx` (`corsoIdCorso`),
  CONSTRAINT `corso` FOREIGN KEY (`corsoIdCorso`) REFERENCES `corso` (`idcorso`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10062 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lezione`
--

LOCK TABLES `lezione` WRITE;
/*!40000 ALTER TABLE `lezione` DISABLE KEYS */;
INSERT INTO `lezione` VALUES ('Lezione 1',28,1,23,'cd171a96-bf26-4458-974c-19ea51cda60e.mp4',5),('Lezione 2',1,1,24,'b27997f6-7521-4339-86ff-8bc996bbdb84.mp4',5),('LezioneSeguita',39,1,222,'sss',12),('Le basi',5,1,10056,'9a33cc64-d54a-4e6a-a1cf-5e633beeb93f.mp4',10013),('I vari tipi di obiettivi',1,2,10057,'42cb9714-dd66-4a19-8a09-058bbb27a23f.mp4',10013),('E che bella lezione!',0,2,10060,'fd8a9398-2af8-4ebe-ae5d-62ebbf2ea669.mp4',10014),('E jamm bell',0,1,10061,'f6f8234d-ed80-43c7-845f-6a2736e7fcae.mp4',10014);
/*!40000 ALTER TABLE `lezione` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `lezione_BEFORE_INSERT` BEFORE INSERT ON `lezione` FOR EACH ROW BEGIN
	
    IF((Select MAX(numeroLezione) 
							from lezione where corsoIdCorso=new.corsoIdCorso) IS NULL)
	then set new.numeroLezione=1;
    else set new.numeroLezione=(Select MAX(numeroLezione) 
							from lezione where corsoIdCorso=new.corsoIdCorso)+1;
	end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `lezione_AFTER_INSERT` AFTER INSERT ON `lezione` FOR EACH ROW begin 
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
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
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
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `scandezaCorso` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `scandezaCorso` ON SCHEDULE EVERY 1 DAY STARTS '2019-02-16 00:03:33' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	
    update corso
    set stato="disattivato"
    where dataFine=now();
    
END */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'youlearndb'
--
/*!50003 DROP PROCEDURE IF EXISTS `adjustLezioni` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `adjustLezioni`(nLezione int,idCorso int)
BEGIN
	update lezione
	set numeroLezione=numeroLezione-1
    where numeroLezione>nLezione AND corsoIdCorso=idCorso ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `increaseVisualizzazioni` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `increaseVisualizzazioni`(id int)
BEGIN
	update Lezione
    set visualizzazione=visualizzazione+1
    where idLezione=id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-17 16:20:22
