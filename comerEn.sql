-- MariaDB dump 10.19  Distrib 10.8.3-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: comeren
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

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
-- Temporary table structure for view `Restaurante_Valoracion`
--

DROP TABLE IF EXISTS `Restaurante_Valoracion`;
/*!50001 DROP VIEW IF EXISTS `Restaurante_Valoracion`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `Restaurante_Valoracion` (
  `idRestaurante` tinyint NOT NULL,
  `nombre` tinyint NOT NULL,
  `descripcion` tinyint NOT NULL,
  `horario_abre` tinyint NOT NULL,
  `horario_cierra` tinyint NOT NULL,
  `icono` tinyint NOT NULL,
  `idDueño` tinyint NOT NULL,
  `idAdmin` tinyint NOT NULL,
  `oculto` tinyint NOT NULL,
  `localidad` tinyint NOT NULL,
  `valoracion` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `comentario`
--

DROP TABLE IF EXISTS `comentario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comentario` (
  `idComentario` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) NOT NULL,
  `idRestaurante` int(11) NOT NULL,
  `comentario` varchar(140) NOT NULL,
  `valoracion` int(11) NOT NULL DEFAULT 4,
  PRIMARY KEY (`idComentario`),
  KEY `FK_idUsuario_comentario` (`idUsuario`),
  KEY `FK_idRestaurante_comentario` (`idRestaurante`),
  CONSTRAINT `FK_idRestaurante_comentario` FOREIGN KEY (`idRestaurante`) REFERENCES `restaurante` (`idRestaurante`),
  CONSTRAINT `FK_idUsuario_comentario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentario`
--

LOCK TABLES `comentario` WRITE;
/*!40000 ALTER TABLE `comentario` DISABLE KEYS */;
INSERT INTO `comentario` VALUES
(1,7,1,'Lorem ipsum dolor sit amet consectetur, adipiscing elit etiam mi ante suscipit, libero potenti mattis sociis.',4),
(4,7,3,'',5),
(5,20,3,'',5),
(6,4,4,'a',4),
(7,3,7,'a',4),
(8,1,7,'',4),
(9,7,7,'',4),
(10,21,5,'',4);
/*!40000 ALTER TABLE `comentario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etiqueta`
--

DROP TABLE IF EXISTS `etiqueta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etiqueta` (
  `idEtiqueta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`idEtiqueta`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etiqueta`
--

LOCK TABLES `etiqueta` WRITE;
/*!40000 ALTER TABLE `etiqueta` DISABLE KEYS */;
INSERT INTO `etiqueta` VALUES
(1,'Comida Rapida'),
(2,'Hamburguesas'),
(3,'Servicio 24h'),
(4,'Comida Italiana');
/*!40000 ALTER TABLE `etiqueta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fotografia`
--

DROP TABLE IF EXISTS `fotografia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fotografia` (
  `idFotografia` int(11) NOT NULL AUTO_INCREMENT,
  `idRestaurante` int(11) NOT NULL,
  `ubicacion` varchar(40) NOT NULL,
  PRIMARY KEY (`idFotografia`),
  KEY `FK_idRestaurante_fotografia` (`idRestaurante`),
  CONSTRAINT `FK_idRestaurante_fotografia` FOREIGN KEY (`idRestaurante`) REFERENCES `restaurante` (`idRestaurante`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotografia`
--

LOCK TABLES `fotografia` WRITE;
/*!40000 ALTER TABLE `fotografia` DISABLE KEYS */;
INSERT INTO `fotografia` VALUES
(1,1,'foto_temporal.jpg'),
(2,1,'foto_temporal.jpg'),
(3,1,'foto_temporal.jpg');
/*!40000 ALTER TABLE `fotografia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante`
--

DROP TABLE IF EXISTS `restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurante` (
  `idRestaurante` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(300) NOT NULL DEFAULT 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  `horario_abre` time DEFAULT '09:00:00',
  `horario_cierra` time DEFAULT '23:00:00',
  `icono` varchar(40) DEFAULT 'foto_temporal.jpg',
  `idDueño` int(11) NOT NULL,
  `idAdmin` int(11) NOT NULL,
  `oculto` tinyint(1) NOT NULL DEFAULT 0,
  `localidad` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`idRestaurante`),
  KEY `FK_idDueño_restaurante` (`idDueño`),
  KEY `FK_idAdmin_restaurante` (`idAdmin`),
  CONSTRAINT `FK_idAdmin_restaurante` FOREIGN KEY (`idAdmin`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `FK_idDueño_restaurante` FOREIGN KEY (`idDueño`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante`
--

LOCK TABLES `restaurante` WRITE;
/*!40000 ALTER TABLE `restaurante` DISABLE KEYS */;
INSERT INTO `restaurante` VALUES
(1,'McDonalds','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','09:00:00','23:00:00','foto_temporal.jpg',5,1,0,'Castro Urdiales'),
(3,'Restaurante Prueba','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','09:00:00','23:00:00','foto_temporal.jpg',19,6,0,'Santander'),
(4,'McdcDonalds','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','09:00:00','23:00:00','foto_temporal.jpg',5,1,0,'Castro Urdiales'),
(5,'asddd','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','09:00:00','23:00:00','foto_temporal.jpg',20,6,0,NULL),
(6,'asdasd','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','09:00:00','23:00:00','foto_temporal.jpg',21,6,0,NULL),
(7,'McDonalds','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','09:00:00','23:00:00','foto_temporal.jpg',5,8,0,'Castro Urdiales');
/*!40000 ALTER TABLE `restaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante_etiqueta`
--

DROP TABLE IF EXISTS `restaurante_etiqueta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurante_etiqueta` (
  `idRestaurante` int(11) NOT NULL,
  `idEtiqueta` int(11) NOT NULL,
  PRIMARY KEY (`idRestaurante`,`idEtiqueta`),
  KEY `FK_idEtiqueta_restaurante_etiqueta` (`idEtiqueta`),
  CONSTRAINT `FK_idEtiqueta_restaurante_etiqueta` FOREIGN KEY (`idEtiqueta`) REFERENCES `etiqueta` (`idEtiqueta`),
  CONSTRAINT `FK_idRestaurante_restaurante_etiqueta` FOREIGN KEY (`idRestaurante`) REFERENCES `restaurante` (`idRestaurante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante_etiqueta`
--

LOCK TABLES `restaurante_etiqueta` WRITE;
/*!40000 ALTER TABLE `restaurante_etiqueta` DISABLE KEYS */;
INSERT INTO `restaurante_etiqueta` VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(3,1),
(3,2),
(3,3),
(3,4),
(4,1),
(4,2),
(4,4),
(5,1),
(6,1),
(6,2),
(6,3),
(7,1),
(7,2),
(7,3);
/*!40000 ALTER TABLE `restaurante_etiqueta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES
(1,'Admin'),
(2,'Dueño');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suscripcion`
--

DROP TABLE IF EXISTS `suscripcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suscripcion` (
  `idSuscripcion` int(11) NOT NULL AUTO_INCREMENT,
  `precio` float NOT NULL DEFAULT 5,
  `descripcion` varchar(120) NOT NULL,
  `duracion` int(12) NOT NULL,
  `categoria` enum('publicidad','premios') NOT NULL,
  PRIMARY KEY (`idSuscripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suscripcion`
--

LOCK TABLES `suscripcion` WRITE;
/*!40000 ALTER TABLE `suscripcion` DISABLE KEYS */;
INSERT INTO `suscripcion` VALUES
(1,9.9,'Elimina todos los anuncios de la aplicación web!',12,'publicidad'),
(2,9.9,'Consigue premios únicos de tus restaurantes favoritos!',21,'premios');
/*!40000 ALTER TABLE `suscripcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ubicacion`
--

DROP TABLE IF EXISTS `ubicacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ubicacion` (
  `idUbicacion` int(11) NOT NULL AUTO_INCREMENT,
  `Lng` float NOT NULL,
  `Lat` float NOT NULL,
  `idRestaurante` int(11) NOT NULL,
  PRIMARY KEY (`idUbicacion`),
  KEY `FK_idRestaurante_ubicacion` (`idRestaurante`),
  CONSTRAINT `FK_idRestaurante_ubicacion` FOREIGN KEY (`idRestaurante`) REFERENCES `restaurante` (`idRestaurante`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ubicacion`
--

LOCK TABLES `ubicacion` WRITE;
/*!40000 ALTER TABLE `ubicacion` DISABLE KEYS */;
INSERT INTO `ubicacion` VALUES
(1,-3.21583,43.3684,1),
(2,-3.81104,43.4736,3),
(3,-2.92993,43.2598,4);
/*!40000 ALTER TABLE `ubicacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(40) NOT NULL,
  `correo` varchar(60) NOT NULL,
  `icono` varchar(40) NOT NULL DEFAULT 'iconoLogin.svg',
  `contrasena` char(64) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES
(1,'duenio','duenio','duenio@duenio.com','iconoLogin.svg','admin'),
(3,'duenio','duenio','duenio@duenio.com','iconoLogin.svg','ebe55a7b7c34aa9eec135591f5573560311179dc3a4b0cfdeef5f1d8bd64ed74'),
(4,'duenio','duenio','duenio@duenio.com','iconoLogin.svg','a0ccddd9e5ddd2617e88f6515a2998f0119b6e99fd2bfef049550ad983af9fa0'),
(5,'duenio1','duenio','duenio@duenio.com','iconoLogin.svg','ce493bb975e763dcbe185d6671846b1576e99c905b9f929dd80b9d1014297e4f'),
(6,'admin','admin','admin2@admin2.com','iconoLogin.svg','686dd4419d5da11febc928e2f4df2daa16dd8e3d6b8201b80abf99a32f4d4b83'),
(7,'Usuario','usuario3','usuario3@usuario.com','iconoLogin.svg','a0ccddd9e5ddd2617e88f6515a2998f0119b6e99fd2bfef049550ad983af9fa0'),
(8,'duenio2','duenio2','duenio2@duenio2.com','iconoLogin.svg','a0ccddd9e5ddd2617e88f6515a2998f0119b6e99fd2bfef049550ad983af9fa0'),
(19,'Dueno Prueba','comeren.correo@gmail.com','comeren.correo@gmail.com','iconoLogin.svg','5N9znwev'),
(20,'asddddd','comeren.correo@gmail.com','comeren.correo@gmail.com','iconoLogin.svg','733b83923eb02567f247e7910d5d9042dfc4f0ef21b3df8920cf3048927b9d10'),
(21,'asdaddas','comeren.correo@gmail.com','comeren.correo@gmail.com','iconoLogin.svg','f3ef2bbf26d6044728eecf2f5694dd75e77823dfcc6c8c06211a858631876de0');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_rol`
--

DROP TABLE IF EXISTS `usuario_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_rol` (
  `idUsuario` int(11) NOT NULL,
  `idRol` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idRol`),
  KEY `FK_idRol_usuario_rol` (`idRol`),
  CONSTRAINT `FK_idRol_usuario_rol` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`),
  CONSTRAINT `FK_idUsuario_usuario_rol` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_rol`
--

LOCK TABLES `usuario_rol` WRITE;
/*!40000 ALTER TABLE `usuario_rol` DISABLE KEYS */;
INSERT INTO `usuario_rol` VALUES
(1,1),
(5,2),
(6,1),
(8,2),
(19,2),
(20,2),
(21,2);
/*!40000 ALTER TABLE `usuario_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_suscripcion`
--

DROP TABLE IF EXISTS `usuario_suscripcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_suscripcion` (
  `idUsuario` int(11) NOT NULL,
  `idSuscripcion` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idSuscripcion`),
  KEY `FK_idSuscripcion_usuario_suscripcion` (`idSuscripcion`),
  CONSTRAINT `FK_idSuscripcion_usuario_suscripcion` FOREIGN KEY (`idSuscripcion`) REFERENCES `suscripcion` (`idSuscripcion`),
  CONSTRAINT `FK_idUsuario_usuario_suscripcion` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_suscripcion`
--

LOCK TABLES `usuario_suscripcion` WRITE;
/*!40000 ALTER TABLE `usuario_suscripcion` DISABLE KEYS */;
INSERT INTO `usuario_suscripcion` VALUES
(7,1);
/*!40000 ALTER TABLE `usuario_suscripcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `Restaurante_Valoracion`
--

/*!50001 DROP TABLE IF EXISTS `Restaurante_Valoracion`*/;
/*!50001 DROP VIEW IF EXISTS `Restaurante_Valoracion`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `Restaurante_Valoracion` AS select `restaurante`.`idRestaurante` AS `idRestaurante`,`restaurante`.`nombre` AS `nombre`,`restaurante`.`descripcion` AS `descripcion`,`restaurante`.`horario_abre` AS `horario_abre`,`restaurante`.`horario_cierra` AS `horario_cierra`,`restaurante`.`icono` AS `icono`,`restaurante`.`idDueño` AS `idDueño`,`restaurante`.`idAdmin` AS `idAdmin`,`restaurante`.`oculto` AS `oculto`,`restaurante`.`localidad` AS `localidad`,avg(`comentario`.`valoracion`) AS `valoracion` from (`restaurante` join `comentario` on(`restaurante`.`idRestaurante` = `comentario`.`idRestaurante`)) where `restaurante`.`oculto` = 0 group by `restaurante`.`idRestaurante` order by avg(`comentario`.`valoracion`) desc limit 5 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-04  8:12:48
