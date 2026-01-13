-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: letala
-- ------------------------------------------------------
-- Server version	10.4.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `let`
--

DROP TABLE IF EXISTS `let`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `let` (
  `IDLeta` varchar(20) NOT NULL,
  `Letalska_druzba` varchar(30) NOT NULL,
  `LetaloRegistracija` varchar(15) NOT NULL,
  `Datum` date NOT NULL,
  `Cas_odhoda` timestamp NOT NULL DEFAULT current_timestamp(),
  `Cas_prihoda` timestamp NOT NULL DEFAULT current_timestamp(),
  `StartLetalisceID` int(10) NOT NULL,
  `KonecLetalisceID` int(10) NOT NULL,
  PRIMARY KEY (`IDLeta`),
  KEY `fk_letalska_druzba_let` (`Letalska_druzba`),
  KEY `fk_letalo_let` (`LetaloRegistracija`),
  KEY `fk_start_letalisce_let` (`StartLetalisceID`),
  KEY `fk_konec_letalisce_let` (`KonecLetalisceID`),
  CONSTRAINT `fk_konec_letalisce_let` FOREIGN KEY (`KonecLetalisceID`) REFERENCES `letalisce` (`LetalisceID`),
  CONSTRAINT `fk_letalo_let` FOREIGN KEY (`LetaloRegistracija`) REFERENCES `letalo` (`Registracija`),
  CONSTRAINT `fk_letalska_druzba_let` FOREIGN KEY (`Letalska_druzba`) REFERENCES `letalska_druzba` (`Ime`),
  CONSTRAINT `fk_start_letalisce_let` FOREIGN KEY (`StartLetalisceID`) REFERENCES `letalisce` (`LetalisceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `let`
--

LOCK TABLES `let` WRITE;
/*!40000 ALTER TABLE `let` DISABLE KEYS */;
INSERT INTO `let` VALUES ('LH456','Ryanair','D-AIAB','2024-07-02','2024-07-02 12:00:00','2024-07-02 14:30:00',456,123),('RA123','Adria Airways','S5-AAA','2024-07-01','2024-07-01 08:00:00','2024-07-01 10:00:00',123,456);
/*!40000 ALTER TABLE `let` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `letalisce`
--

DROP TABLE IF EXISTS `letalisce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `letalisce` (
  `LetalisceID` int(10) NOT NULL AUTO_INCREMENT,
  `Ime` varchar(30) NOT NULL,
  `drzava` varchar(56) NOT NULL,
  `longtitude` decimal(10,6) NOT NULL,
  `latitude` decimal(10,6) NOT NULL,
  PRIMARY KEY (`LetalisceID`),
  CONSTRAINT `long_mask` CHECK (`longtitude` between -180 and 180),
  CONSTRAINT `lat_mask` CHECK (`latitude` between -90 and 90)
) ENGINE=InnoDB AUTO_INCREMENT=790 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `letalisce`
--

LOCK TABLES `letalisce` WRITE;
/*!40000 ALTER TABLE `letalisce` DISABLE KEYS */;
INSERT INTO `letalisce` VALUES (123,'Letališče Jožeta Pučnika Ljubl','Slovenija',14.457600,46.223000),(456,'Frankfurt Airport','Nemčija',8.570600,50.037900),(789,'Letališče Edvarda Rusjana Mari','Slovenija',15.645000,46.485000);
/*!40000 ALTER TABLE `letalisce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `letalo`
--

DROP TABLE IF EXISTS `letalo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `letalo` (
  `Registracija` varchar(15) NOT NULL,
  `Ime` varchar(50) NOT NULL,
  `Proizvajalec` varchar(30) NOT NULL,
  `Datum_izdelave` date NOT NULL,
  `Sedezi` smallint(5) NOT NULL,
  `Nosilnost` int(10) NOT NULL,
  `Tip` varchar(20) NOT NULL,
  `Letalska_druzba` varchar(30) NOT NULL,
  PRIMARY KEY (`Registracija`),
  KEY `fk_letalska_druzba_letalo` (`Letalska_druzba`),
  CONSTRAINT `fk_letalska_druzba_letalo` FOREIGN KEY (`Letalska_druzba`) REFERENCES `letalska_druzba` (`Ime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `letalo`
--

LOCK TABLES `letalo` WRITE;
/*!40000 ALTER TABLE `letalo` DISABLE KEYS */;
INSERT INTO `letalo` VALUES ('D-ABCD','Mig-25','Mikoyan-Gurevich','1985-11-05',1,10000,'Vojaško','Ryanair'),('D-AIAB','Boeing 737','Boeing','2018-09-20',160,18000,'Potniško','Adria Airways'),('S5-AAA','Airbus A320','Airbus','2015-06-15',180,20000,'Potniško','Adria Airways'),('S5-BBB','Pilatus PC-12','Pilatus Aircraft','2020-03-10',9,2000,'Poslovno','Adria Airways');
/*!40000 ALTER TABLE `letalo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `letalska_druzba`
--

DROP TABLE IF EXISTS `letalska_druzba`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `letalska_druzba` (
  `Ime` varchar(30) NOT NULL,
  `Naslov` varchar(40) NOT NULL,
  `Lastnik` varchar(20) NOT NULL,
  `Spletna_stran` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Ime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `letalska_druzba`
--

LOCK TABLES `letalska_druzba` WRITE;
/*!40000 ALTER TABLE `letalska_druzba` DISABLE KEYS */;
INSERT INTO `letalska_druzba` VALUES ('Adria Airways','Letališka cesta 30, Ljubljana','Jože Potrebuješ','www.adriaairways.si'),('Ryanair','Irska','Michael OLeary','www.ryanair.com');
/*!40000 ALTER TABLE `letalska_druzba` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posadka`
--

DROP TABLE IF EXISTS `posadka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posadka` (
  `letIDLeta` varchar(20) NOT NULL,
  `UsluzbencEMSO` bigint(13) NOT NULL,
  PRIMARY KEY (`letIDLeta`,`UsluzbencEMSO`),
  KEY `fk_usluzbenc_posadka` (`UsluzbencEMSO`),
  CONSTRAINT `fk_let_posadka` FOREIGN KEY (`letIDLeta`) REFERENCES `let` (`IDLeta`),
  CONSTRAINT `fk_usluzbenc_posadka` FOREIGN KEY (`UsluzbencEMSO`) REFERENCES `usluzbenc` (`EMSO`),
  CONSTRAINT `emso_mask_posadka` CHECK (`UsluzbencEMSO` regexp '^[0-9]{7}50[0-9]{4}$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posadka`
--

LOCK TABLES `posadka` WRITE;
/*!40000 ALTER TABLE `posadka` DISABLE KEYS */;
INSERT INTO `posadka` VALUES ('LH456',3456787503456),('RA123',1234567501234),('RA123',2345677502345);
/*!40000 ALTER TABLE `posadka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `potnik`
--

DROP TABLE IF EXISTS `potnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `potnik` (
  `potnikID` int(10) NOT NULL AUTO_INCREMENT,
  `Ime` varchar(20) NOT NULL,
  `Priimek` varchar(30) NOT NULL,
  `Datum_rojstva` date NOT NULL,
  PRIMARY KEY (`potnikID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `potnik`
--

LOCK TABLES `potnik` WRITE;
/*!40000 ALTER TABLE `potnik` DISABLE KEYS */;
INSERT INTO `potnik` VALUES (1,'Štefan','Hadalin','1990-05-15'),(2,'Mici','Brinar','1985-08-22'),(3,'Ivan','Cankar','1978-11-30'),(4,'Damjan','Murko','1995-02-10');
/*!40000 ALTER TABLE `potnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezervacija`
--

DROP TABLE IF EXISTS `rezervacija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezervacija` (
  `potnikID` int(10) NOT NULL,
  `IDLeta` varchar(20) NOT NULL,
  `Sedez` char(4) NOT NULL,
  `Datum_rezervacije` date NOT NULL,
  `Meni` varchar(20) NOT NULL,
  PRIMARY KEY (`potnikID`,`IDLeta`),
  KEY `IDLeta` (`IDLeta`),
  CONSTRAINT `rezervacija_ibfk_1` FOREIGN KEY (`potnikID`) REFERENCES `potnik` (`potnikID`),
  CONSTRAINT `rezervacija_ibfk_2` FOREIGN KEY (`IDLeta`) REFERENCES `let` (`IDLeta`),
  CONSTRAINT `sedez_mask` CHECK (`Sedez` regexp '^[0-9]+[A-Z]$'),
  CONSTRAINT `meni_mask` CHECK (`Meni` in ('Vegetarijanski','Mesni','Veganski','Otroški'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezervacija`
--

LOCK TABLES `rezervacija` WRITE;
/*!40000 ALTER TABLE `rezervacija` DISABLE KEYS */;
INSERT INTO `rezervacija` VALUES (1,'RA123','12A','2024-06-01','Mesni'),(2,'RA123','14B','2024-06-02','Vegetarijanski'),(3,'LH456','10C','2024-06-03','Veganski'),(4,'LH456','11D','2024-06-04','Otroški');
/*!40000 ALTER TABLE `rezervacija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tovorni_nalog`
--

DROP TABLE IF EXISTS `tovorni_nalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tovorni_nalog` (
  `IDNaloga` int(10) NOT NULL AUTO_INCREMENT,
  `IDLeta` varchar(20) NOT NULL,
  `Masa` smallint(5) NOT NULL,
  `Opis` varchar(200) NOT NULL,
  `Narocnik` varchar(30) NOT NULL,
  PRIMARY KEY (`IDNaloga`),
  KEY `IDLeta` (`IDLeta`),
  CONSTRAINT `tovorni_nalog_ibfk_1` FOREIGN KEY (`IDLeta`) REFERENCES `let` (`IDLeta`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tovorni_nalog`
--

LOCK TABLES `tovorni_nalog` WRITE;
/*!40000 ALTER TABLE `tovorni_nalog` DISABLE KEYS */;
INSERT INTO `tovorni_nalog` VALUES (1,'RA123',500,'Elektronika','TechCorp'),(2,'LH456',300,'Oblačila','FashionInc');
/*!40000 ALTER TABLE `tovorni_nalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usluzbenc`
--

DROP TABLE IF EXISTS `usluzbenc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usluzbenc` (
  `EMSO` bigint(13) NOT NULL,
  `Letalska_druzba` varchar(30) NOT NULL,
  `ime` varchar(20) NOT NULL,
  `priimek` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `vloga` varchar(20) NOT NULL,
  PRIMARY KEY (`EMSO`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_letalska_druzba_usluzbenec` (`Letalska_druzba`),
  CONSTRAINT `fk_letalska_druzba_usluzbenec` FOREIGN KEY (`Letalska_druzba`) REFERENCES `letalska_druzba` (`Ime`),
  CONSTRAINT `emso_mask` CHECK (`EMSO` regexp '^[0-9]{7}50[0-9]{4}$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usluzbenc`
--

LOCK TABLES `usluzbenc` WRITE;
/*!40000 ALTER TABLE `usluzbenc` DISABLE KEYS */;
INSERT INTO `usluzbenc` VALUES (1234567501234,'Adria Airways','Jože','Štefan','joze.stefan@gmail.com','Pilot'),(2345677502345,'Ryanair','Štefka','Rupnik','stefka.rupnik@ryanair.com','Stevardesa'),(3456787503456,'Ryanair','Barak','Obama','barak.obama@gov.com','Mehanik');
/*!40000 ALTER TABLE `usluzbenc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usluzbenc_vzdrzevanje`
--

DROP TABLE IF EXISTS `usluzbenc_vzdrzevanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usluzbenc_vzdrzevanje` (
  `UsluzbencEMSO` bigint(13) NOT NULL,
  `VzdrzevanjeIDVzdrzevanja` int(10) NOT NULL,
  PRIMARY KEY (`UsluzbencEMSO`,`VzdrzevanjeIDVzdrzevanja`),
  KEY `fk_usluzbenc_vzdrzevanje_vzdrzevanje` (`VzdrzevanjeIDVzdrzevanja`),
  CONSTRAINT `fk_usluzbenc_vzdrzevanje_usluzbenc` FOREIGN KEY (`UsluzbencEMSO`) REFERENCES `usluzbenc` (`EMSO`),
  CONSTRAINT `fk_usluzbenc_vzdrzevanje_vzdrzevanje` FOREIGN KEY (`VzdrzevanjeIDVzdrzevanja`) REFERENCES `vzdrzevanje` (`IDVzdrzevanja`),
  CONSTRAINT `emso_mask_vzdrzevanje` CHECK (`UsluzbencEMSO` regexp '^[0-9]{7}50[0-9]{4}$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usluzbenc_vzdrzevanje`
--

LOCK TABLES `usluzbenc_vzdrzevanje` WRITE;
/*!40000 ALTER TABLE `usluzbenc_vzdrzevanje` DISABLE KEYS */;
INSERT INTO `usluzbenc_vzdrzevanje` VALUES (2345677502345,2),(3456787503456,1);
/*!40000 ALTER TABLE `usluzbenc_vzdrzevanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vzdrzevanje`
--

DROP TABLE IF EXISTS `vzdrzevanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vzdrzevanje` (
  `IDVzdrzevanja` int(10) NOT NULL AUTO_INCREMENT,
  `LetaloRegistracija` varchar(15) NOT NULL,
  `Datum` date NOT NULL,
  `Opis` varchar(200) NOT NULL,
  `Vrsta` varchar(20) NOT NULL,
  PRIMARY KEY (`IDVzdrzevanja`),
  KEY `fk_letalo_vzdrzevanje` (`LetaloRegistracija`),
  CONSTRAINT `fk_letalo_vzdrzevanje` FOREIGN KEY (`LetaloRegistracija`) REFERENCES `letalo` (`Registracija`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vzdrzevanje`
--

LOCK TABLES `vzdrzevanje` WRITE;
/*!40000 ALTER TABLE `vzdrzevanje` DISABLE KEYS */;
INSERT INTO `vzdrzevanje` VALUES (1,'S5-AAA','2024-05-15','Redni servis motorjev','Redno'),(2,'D-AIAB','2024-06-10','Popravilo pristajalne naprave','Izredno');
/*!40000 ALTER TABLE `vzdrzevanje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-13  8:29:46
