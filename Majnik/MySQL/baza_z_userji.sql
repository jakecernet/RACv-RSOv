-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: 
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
CREATE USER IF NOT EXISTS `root`@`localhost`;
CREATE USER IF NOT EXISTS `root`@`127.0.0.1`;
CREATE USER IF NOT EXISTS `root`@`::1`;
CREATE USER IF NOT EXISTS `pma`@`localhost`;
SELECT COALESCE(CURRENT_ROLE(),'NONE') into @current_role;
CREATE ROLE IF NOT EXISTS mariadb_dump_import_role;
GRANT mariadb_dump_import_role TO CURRENT_USER();
SET ROLE mariadb_dump_import_role;
/*!80001 CREATE ROLE IF NOT EXISTS 'admin' */;
/*M!100005 CREATE ROLE IF NOT EXISTS 'admin' WITH ADMIN mariadb_dump_import_role */;
/*M!100005 GRANT 'admin' TO 'root'@'localhost' WITH ADMIN OPTION */;
/*!80001 CREATE ROLE IF NOT EXISTS 'mehanik' */;
/*M!100005 CREATE ROLE IF NOT EXISTS 'mehanik' WITH ADMIN mariadb_dump_import_role */;
/*M!100005 GRANT 'mehanik' TO 'root'@'localhost' WITH ADMIN OPTION */;
/*!80001 CREATE ROLE IF NOT EXISTS 'pilot' */;
/*M!100005 CREATE ROLE IF NOT EXISTS 'pilot' WITH ADMIN mariadb_dump_import_role */;
/*M!100005 GRANT 'pilot' TO 'root'@'localhost' WITH ADMIN OPTION */;
/*!80001 CREATE ROLE IF NOT EXISTS 'stevardesa' */;
/*M!100005 CREATE ROLE IF NOT EXISTS 'stevardesa' WITH ADMIN mariadb_dump_import_role */;
/*M!100005 GRANT 'stevardesa' TO 'root'@'localhost' WITH ADMIN OPTION */;
GRANT `admin` TO `root`@`localhost` WITH ADMIN OPTION;
GRANT `mehanik` TO `root`@`localhost` WITH ADMIN OPTION;
GRANT `pilot` TO `root`@`localhost` WITH ADMIN OPTION;
GRANT `stevardesa` TO `root`@`localhost` WITH ADMIN OPTION;
GRANT ALL PRIVILEGES ON *.* TO `root`@`localhost` WITH GRANT OPTION;
GRANT PROXY ON ''@'%' TO 'root'@'localhost' WITH GRANT OPTION;
/*M!100005 SET DEFAULT ROLE NONE FOR 'root'@'localhost' */;
/*!80001 ALTER USER 'root'@'localhost' DEFAULT ROLE NONE */;
GRANT ALL PRIVILEGES ON *.* TO `root`@`127.0.0.1` WITH GRANT OPTION;
/*M!100005 SET DEFAULT ROLE NONE FOR 'root'@'127.0.0.1' */;
/*!80001 ALTER USER 'root'@'127.0.0.1' DEFAULT ROLE NONE */;
GRANT ALL PRIVILEGES ON *.* TO `root`@`::1` WITH GRANT OPTION;
/*M!100005 SET DEFAULT ROLE NONE FOR 'root'@'::1' */;
/*!80001 ALTER USER 'root'@'::1' DEFAULT ROLE NONE */;
GRANT USAGE ON *.* TO `pma`@`localhost`;
GRANT SELECT, INSERT, UPDATE, DELETE ON `phpmyadmin`.* TO `pma`@`localhost`;
/*M!100005 SET DEFAULT ROLE NONE FOR 'pma'@'localhost' */;
/*!80001 ALTER USER 'pma'@'localhost' DEFAULT ROLE NONE */;
GRANT USAGE ON *.* TO `admin`;
GRANT SELECT, INSERT, UPDATE, DELETE ON `letala`.* TO `admin`;
GRANT USAGE ON *.* TO `mehanik`;
GRANT SELECT, UPDATE ON `letala`.`letalo` TO `mehanik`;
GRANT SELECT, INSERT, UPDATE ON `letala`.`vzdrzevanje` TO `mehanik`;
GRANT USAGE ON *.* TO `pilot`;
GRANT SELECT, INSERT, UPDATE ON `letala`.`let` TO `pilot`;
GRANT USAGE ON *.* TO `stevardesa`;
GRANT SELECT ON `letala`.* TO `stevardesa`;
GRANT SELECT, INSERT, UPDATE ON `letala`.`rezervacija` TO `stevardesa`;
SET ROLE NONE;
DROP ROLE mariadb_dump_import_role;
/*M!100203 EXECUTE IMMEDIATE CONCAT('SET ROLE ', @current_role) */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-21 13:19:21
