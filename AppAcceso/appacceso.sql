-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-05-2019 a las 03:27:02
-- Versión del servidor: 10.1.30-MariaDB
-- Versión de PHP: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `appacceso`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `guardias`
--

CREATE TABLE `guardias` (
  `idGuardia` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Apellidos` varchar(45) NOT NULL,
  `Usuario` varchar(45) NOT NULL,
  `pass` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `guardias`
--

INSERT INTO `guardias` (`idGuardia`, `Nombre`, `Apellidos`, `Usuario`, `pass`) VALUES
(2, 'Brenda', 'Mezquitic Gallardo', 'admin', '21232f297a57a5a743894a0e4a801fc3'),
(3, 'Jose Angel', 'Cantu Ramirez', 'angelcantu21', '21232f297a57a5a743894a0e4a801fc3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `invitados`
--

CREATE TABLE `invitados` (
  `idInvitado` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Fecha` date NOT NULL,
  `Caducidad` date NOT NULL,
  `FotoID` varchar(200) NOT NULL,
  `Selfie` varchar(200) NOT NULL,
  `Vehiculo` varchar(45) DEFAULT 'No aplica',
  `Placas` varchar(45) DEFAULT 'No aplica',
  `Empresa` varchar(45) DEFAULT 'No aplica',
  `GeneradorQR` varchar(200) NOT NULL,
  `FkResidente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `invitados`
--

INSERT INTO `invitados` (`idInvitado`, `Nombre`, `Fecha`, `Caducidad`, `FotoID`, `Selfie`, `Vehiculo`, `Placas`, `Empresa`, `GeneradorQR`, `FkResidente`) VALUES
(1, 'Brenda Mezquitic Gallardo', '2018-12-20', '2018-12-24', 'foto.jpg', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', '!h4ffa77d32xd63284sfsdf62sf45325483dsf5684sdf5', 1),
(13, 'Jose Angel Cantu Ramirez', '2018-12-22', '2018-11-24', 'id.png', 'selfie.png', 'Chevy', 'SRD-4727', 'Uber', '9373isjsue', 1),
(14, 'Diego Gerardo Laureano Martinez', '2018-12-22', '2018-11-24', 'id.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', '9373isjsue', 1),
(16, 'Erick Lucio Cantu', '2019-01-03', '2019-00-07', 'id.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', '9373isjsue', 1),
(18, 'Fernanda Garcia', '2019-01-03', '2019-00-14', 'id.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', '9373isjsue', 1),
(19, 'Latin Coder', '2019-05-15', '2019-04-22', 'id.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', '9373isjsue', 2),
(20, 'BRENGEL CANTU', '2019-05-15', '0000-00-00', 'http://localhost/appacceso/uploads/id.png.png', 'selfie.png', 'Chevy Monza', 'SFHX-07-D8', 'Agua Fiel', 'BRENGEL CANTU1', 1),
(21, 'Papure papa', '2019-05-15', '2019-04-22', 'http://localhost/appacceso/uploads/id.png.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', 'Papure papa2', 2),
(22, 'chubaca', '2019-05-15', '2019-04-20', 'http://localhost/appacceso/uploads/id.png.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', 'chubaca2', 2),
(23, 'Franco Escamilla', '2019-05-15', '2019-04-22', 'http://localhost/appacceso/uploads/id.png.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', 'Franco Escamilla1', 1),
(24, 'La mole chida', '2019-05-15', '2019-04-26', 'http://localhost/appacceso/uploads/id.png.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', 'La mole chida2', 2),
(25, 'Legal', '2019-05-15', '2019-04-16', 'http://localhost/appacceso/uploads/id.png.png', 'selfie.png', 'No aplica', 'No aplica', 'No aplica', 'Legal1', 1),
(26, 'Bit Farm', '2019-05-15', '2019-04-18', 'http://localhost/appacceso/uploads/Bit Farm.png', 'Bit Farm.png', 'Chevy 2008', 'CHSJ-73-X3', 'Rappi', 'Bit Farm1', 1),
(27, 'Lelelele', '2019-05-15', '2019-04-19', 'http://localhost/appacceso/uploads/Lelelele.png', 'Lelelele.png', 'No aplica', 'No aplica', 'No aplica', 'Lelelele1', 1),
(28, 'qr mexico', '2019-05-15', '2019-04-16', 'http://localhost/appacceso/uploads/qr mexico.png', 'qr mexico.png', 'No aplica', 'No aplica', 'No aplica', 'qr mexico1', 1),
(29, 'ravo morales', '2019-05-15', '2019-04-13', 'http://localhost/appacceso/uploads/ravo morales.png', 'ravo morales.png', 'No aplica', 'No aplica', 'No aplica', 'ravo morales1', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

CREATE TABLE `mensajes` (
  `idMensaje` int(11) NOT NULL,
  `Asunto` varchar(45) DEFAULT NULL,
  `Texto` varchar(200) DEFAULT NULL,
  `FkResidente` int(11) NOT NULL,
  `FkGuardia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mensajes`
--

INSERT INTO `mensajes` (`idMensaje`, `Asunto`, `Texto`, `FkResidente`, `FkGuardia`) VALUES
(1, 'Ruido', 'Quiero repotar a mis vecinos que tienen el ruido muy alto.', 1, 2),
(2, 'Olor a gas', 'Hay un olor a gas muy fuerte dentro de los departamentos.', 1, 3),
(3, 'Prueba', 'Este es un mensaje de prueba para saber si el id es nulo.', 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `residentes`
--

CREATE TABLE `residentes` (
  `idResidente` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Apellidos` varchar(45) NOT NULL,
  `Departamento` varchar(45) NOT NULL,
  `Codigo_Acceso` varchar(100) NOT NULL,
  `Disponible` tinyint(1) DEFAULT '1',
  `Ausente` tinyint(1) DEFAULT '0',
  `No_molestar` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `residentes`
--

INSERT INTO `residentes` (`idResidente`, `Nombre`, `Apellidos`, `Departamento`, `Codigo_Acceso`, `Disponible`, `Ausente`, `No_molestar`) VALUES
(1, 'Jose Angel', 'Cantu Ramirez', '#782', '83125430', 1, 0, 0),
(2, 'Brenda', 'Mezquitic Gallardo', '#71', '821821821', 0, 1, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `guardias`
--
ALTER TABLE `guardias`
  ADD PRIMARY KEY (`idGuardia`);

--
-- Indices de la tabla `invitados`
--
ALTER TABLE `invitados`
  ADD PRIMARY KEY (`idInvitado`),
  ADD KEY `fk_Invitados_Residentes1_idx` (`FkResidente`);

--
-- Indices de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD PRIMARY KEY (`idMensaje`),
  ADD KEY `fk_Mensajes_Guardias1_idx` (`FkGuardia`),
  ADD KEY `fk_Mensajes_Residentes1_idx` (`FkResidente`);

--
-- Indices de la tabla `residentes`
--
ALTER TABLE `residentes`
  ADD PRIMARY KEY (`idResidente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `guardias`
--
ALTER TABLE `guardias`
  MODIFY `idGuardia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `invitados`
--
ALTER TABLE `invitados`
  MODIFY `idInvitado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `idMensaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `residentes`
--
ALTER TABLE `residentes`
  MODIFY `idResidente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `invitados`
--
ALTER TABLE `invitados`
  ADD CONSTRAINT `fk_Invitados_Residentes1` FOREIGN KEY (`FkResidente`) REFERENCES `residentes` (`idResidente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD CONSTRAINT `fk_Mensajes_Guardias1` FOREIGN KEY (`FkGuardia`) REFERENCES `guardias` (`idGuardia`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Mensajes_Residentes1` FOREIGN KEY (`FkResidente`) REFERENCES `residentes` (`idResidente`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
