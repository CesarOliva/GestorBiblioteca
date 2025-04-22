-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-04-2025 a las 04:34:36
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestorbiblioteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `autores`
--

CREATE TABLE `autores` (
  `IdAutor` int(10) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Biografia` varchar(1000) NOT NULL,
  `Foto` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `autores`
--

INSERT INTO `autores` (`IdAutor`, `Nombre`, `Biografia`, `Foto`) VALUES
(1, 'Dan Brown', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Usuario.jpg'),
(2, 'Franz Kafka', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Usuario.jpg'),
(3, 'George Orwell', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Usuario.jpg'),
(4, 'Sun Tzu', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Usuario.jpg'),
(6, 'Aldous Huxley', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Usuario.jpg'),
(7, 'j', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Usuario.jpg'),
(8, 'julio', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/julio.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `editoriales`
--

CREATE TABLE `editoriales` (
  `IdEditorial` int(10) NOT NULL,
  `Editorial` varchar(100) NOT NULL,
  `Descripcion` varchar(500) NOT NULL,
  `Foto` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `editoriales`
--

INSERT INTO `editoriales` (`IdEditorial`, `Editorial`, `Descripcion`, `Foto`) VALUES
(1, 'Editorial Booket', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Editorial.jpg'),
(3, 'Editorial EMU', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Editorial.jpg'),
(5, 'j', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Editorial.jpg'),
(6, 'hola', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/hola.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `generos`
--

CREATE TABLE `generos` (
  `IdGenero` int(10) NOT NULL,
  `Genero` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `generos`
--

INSERT INTO `generos` (`IdGenero`, `Genero`) VALUES
(1, 'Thriller'),
(2, 'Ficción'),
(3, 'Sátira política'),
(4, 'Tratado Político'),
(6, 'Ciencia ficción'),
(7, 'j'),
(8, 'adald'),
(9, 'jjj');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE `libros` (
  `IdLibro` int(10) NOT NULL,
  `ISBN` varchar(20) NOT NULL,
  `Titulo` varchar(200) NOT NULL,
  `IdAutor` int(10) NOT NULL,
  `Portada` varchar(200) NOT NULL,
  `Año` int(7) NOT NULL,
  `IdEditorial` int(10) NOT NULL,
  `IdGenero` int(10) NOT NULL,
  `Paginas` int(6) NOT NULL,
  `Descripcion` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`IdLibro`, `ISBN`, `Titulo`, `IdAutor`, `Portada`, `Año`, `IdEditorial`, `IdGenero`, `Paginas`, `Descripcion`) VALUES
(1, '9786070706646', 'El código Da Vinci', 1, 'C:/xampp/htdocs/Imagenes/ElCodigoDaVinci.jpg', 2003, 1, 1, 652, 'El código Da Vinci de Dan Brown es un thriller que sigue al profesor Robert Langdon en la investigación de un asesinato en el Museo del Louvre. A medida que descifra pistas ocultas en obras de arte y símbolos, descubre una conspiración que podría cambiar la historia del cristianismo. La novela mezcla misterio, religión y arte en una trama llena de suspenso.'),
(2, '978607411709', 'La metámorfosis', 2, 'C:/xampp/htdocs/Imagenes/Lametámorfosis.jpg', 1915, 3, 2, 62, 'La metamorfosis de Franz Kafka narra la historia de Gregor Samsa, un hombre que un día despierta transformado en un insecto gigante. A medida que pierde su humanidad y es rechazado por su familia, la obra explora temas como la alienación, la incomunicación y la pérdida de identidad en la sociedad moderna.'),
(3, '9786070777240', 'La rebelión en la granja', 3, 'C:/xampp/htdocs/Imagenes/Larebeliónenlagranja.jpg', 1945, 1, 3, 150, 'Rebelión en la granja de George Orwell es una fábula satírica sobre una granja donde los animales se rebelan contra los humanos para crear una sociedad justa. Sin embargo, el poder corrompe a los líderes animales, especialmente a los cerdos, y la granja termina siendo tan opresiva como antes. Representa una crítica al totalitarismo y la traición de los ideales revolucionarios.'),
(4, '9786071411228', '1984', 3, 'C:/xampp/htdocs/Imagenes/1984.jpg', 1949, 3, 3, 264, '1984 de George Orwell es una novela distópica que retrata una sociedad totalitaria controlada por el Partido y su líder, el Gran Hermano. A través del protagonista, Winston Smith, se muestra la opresión, la vigilancia constante y la manipulación de la verdad. La obra es una crítica al autoritarismo y a la pérdida de la libertad individual.'),
(5, '9786071435620', 'El arte de la guerra', 4, 'C:/xampp/htdocs/Imagenes/Elartedelaguerra.jpg', -400, 3, 4, 91, 'El arte de la guerra de Sun Tzu es un tratado militar chino que ofrece estrategias y enseñanzas sobre la guerra, el liderazgo y la táctica. Sus principios, como conocer al enemigo y a uno mismo, vencer sin combatir y adaptarse al cambio, han sido aplicados no solo en el ámbito militar, sino también en la política, los negocios y la vida personal.'),
(8, '9786071411075', 'Un mundo feliz', 6, 'C:/xampp/htdocs/Imagenes/Unmundofeliz.jpg', 1930, 3, 6, 221, 'Un mundo feliz de Aldous Huxley es una novela distópica que presenta una sociedad futurista donde la felicidad se logra mediante el control total de la tecnología, la genética y las emociones. No hay guerra ni dolor, pero tampoco libertad ni individualidad. La obra critica la deshumanización y la pérdida de valores en nombre del progreso y la estabilidad.'),
(9, 'j', 'j', 7, 'C:/xampp/htdocs/Imagenes/j.jpg', 9, 5, 7, 9, 'j'),
(10, 'j', 'j', 7, 'C:/xampp/htdocs/Imagenes/j.jpg', 2, 5, 7, 8, 'j'),
(11, '8asda', 'j', 8, 'C:/xampp/htdocs/Imagenes/j.jpg', 195, 5, 8, 5, 'jaklsjdkad'),
(12, '888', 'jjjj', 8, 'C:/xampp/htdocs/Imagenes/jjjj.jpg', 195, 6, 9, 8, 'aklsjdklj');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `librosadministradores`
--

CREATE TABLE `librosadministradores` (
  `IdLibro` int(10) NOT NULL,
  `IdAdmin` int(10) NOT NULL,
  `Fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `librosadministradores`
--

INSERT INTO `librosadministradores` (`IdLibro`, `IdAdmin`, `Fecha`) VALUES
(1, 1, '2025-04-15'),
(2, 1, '2025-04-16'),
(3, 1, '2025-04-16'),
(4, 1, '2025-04-17'),
(5, 1, '2025-04-17'),
(6, 1, '2025-04-17'),
(7, 1, '2025-04-17'),
(8, 1, '2025-04-17'),
(9, 1, '2025-04-18'),
(10, 1, '2025-04-18'),
(11, 1, '2025-04-20'),
(12, 1, '2025-04-20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `multas`
--

CREATE TABLE `multas` (
  `IdMulta` int(10) NOT NULL,
  `IdPrestamo` int(10) NOT NULL,
  `IdSocio` int(10) NOT NULL,
  `Monto` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

CREATE TABLE `notificaciones` (
  `IdNotificacion` int(10) NOT NULL,
  `IdUsuario` int(10) NOT NULL,
  `Mensaje` varchar(200) NOT NULL,
  `Tipo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `notificaciones`
--

INSERT INTO `notificaciones` (`IdNotificacion`, `IdUsuario`, `Mensaje`, `Tipo`) VALUES
(1, 1, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(2, 1, 'Agregaste el libro \'j\' a la base de datos.', 'Informativa'),
(3, 6, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(4, 7, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(5, 1, 'Agregaste el libro \'j\' a la base de datos.', 'Informativa'),
(6, 1, 'Agregaste el libro \'jjjj\' a la base de datos.', 'Informativa'),
(7, 8, 'Termine de configurar su cuenta agregando una foto.', 'Informativa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos`
--

CREATE TABLE `prestamos` (
  `IdPrestamo` int(10) NOT NULL,
  `IdSocio` int(10) NOT NULL,
  `IdLibro` int(10) NOT NULL,
  `FechaPrestamo` date NOT NULL,
  `FechaVuelta` date NOT NULL,
  `Devuelto` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `IdUsuario` int(10) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Usuario` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Contraseña` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `FechaCreacion` date NOT NULL,
  `Foto` varchar(100) NOT NULL,
  `TipoUsuario` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`IdUsuario`, `Nombre`, `Usuario`, `Contraseña`, `FechaCreacion`, `Foto`, `TipoUsuario`) VALUES
(1, 'Cesar Oliva', 'csrlv', '652124', '2025-04-15', 'C:/xampp/htdocs/Imagenes/Usuario.jpg', 'administrador'),
(2, 'André Anzures', 'andre', '123', '2025-04-15', 'C:/xampp/htdocs/Imagenes/Usuario.jpg', 'socio'),
(3, 'Yarith Alvarado', 'yari', 'yari123', '2025-04-18', 'C:/xampp/htdocs/Imagenes/Usuario.jpg', 'socio'),
(4, 'Abel Vargas', 'Abel123', 'abelin123', '2025-04-18', 'C:/xampp/htdocs/Imagenes/Usuario.jpg', 'socio'),
(5, 'Hector Edgardo', 'hectored', 'hectored', '2025-04-18', 'C:/xampp/htdocs/Imagenes/Usuario.jpg', 'socio'),
(6, 'Angel Vazquez', 'vazquesex', '656565', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Usuario.jpg', 'socio'),
(7, 'Hola', 'hola123', 'hola', '2025-04-20', 'C:/xampp/htdocs/Imagenes/hola123.jpg', 'socio'),
(8, 'ABELLLL', 'abellllll', 'abellllll', '2025-04-20', 'C:/xampp/htdocs/Imagenes/abellllll.jpg', 'socio');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `autores`
--
ALTER TABLE `autores`
  ADD PRIMARY KEY (`IdAutor`);

--
-- Indices de la tabla `editoriales`
--
ALTER TABLE `editoriales`
  ADD PRIMARY KEY (`IdEditorial`);

--
-- Indices de la tabla `generos`
--
ALTER TABLE `generos`
  ADD PRIMARY KEY (`IdGenero`);

--
-- Indices de la tabla `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`IdLibro`),
  ADD KEY `IdAutor` (`IdAutor`),
  ADD KEY `IdEditorial` (`IdEditorial`),
  ADD KEY `IdGenero` (`IdGenero`);

--
-- Indices de la tabla `librosadministradores`
--
ALTER TABLE `librosadministradores`
  ADD KEY `IdlLibro` (`IdLibro`),
  ADD KEY `librosadministradores_ibfk_2` (`IdAdmin`);

--
-- Indices de la tabla `multas`
--
ALTER TABLE `multas`
  ADD PRIMARY KEY (`IdMulta`),
  ADD KEY `IdPrestamo` (`IdPrestamo`),
  ADD KEY `multas_ibfk_2` (`IdSocio`);

--
-- Indices de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD PRIMARY KEY (`IdNotificacion`),
  ADD KEY `IdUsuario` (`IdUsuario`);

--
-- Indices de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD PRIMARY KEY (`IdPrestamo`),
  ADD KEY `IdLibro` (`IdLibro`),
  ADD KEY `prestamos_ibfk_2` (`IdSocio`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`IdUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `autores`
--
ALTER TABLE `autores`
  MODIFY `IdAutor` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `editoriales`
--
ALTER TABLE `editoriales`
  MODIFY `IdEditorial` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `generos`
--
ALTER TABLE `generos`
  MODIFY `IdGenero` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `IdLibro` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `multas`
--
ALTER TABLE `multas`
  MODIFY `IdMulta` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  MODIFY `IdNotificacion` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  MODIFY `IdPrestamo` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `IdUsuario` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `libros`
--
ALTER TABLE `libros`
  ADD CONSTRAINT `libros_ibfk_1` FOREIGN KEY (`IdAutor`) REFERENCES `autores` (`IdAutor`),
  ADD CONSTRAINT `libros_ibfk_2` FOREIGN KEY (`IdEditorial`) REFERENCES `editoriales` (`IdEditorial`),
  ADD CONSTRAINT `libros_ibfk_3` FOREIGN KEY (`IdGenero`) REFERENCES `generos` (`IdGenero`);

--
-- Filtros para la tabla `librosadministradores`
--
ALTER TABLE `librosadministradores`
  ADD CONSTRAINT `librosadministradores_ibfk_1` FOREIGN KEY (`IdLibro`) REFERENCES `libros` (`IdLibro`),
  ADD CONSTRAINT `librosadministradores_ibfk_2` FOREIGN KEY (`IdAdmin`) REFERENCES `usuarios` (`IdUsuario`);

--
-- Filtros para la tabla `multas`
--
ALTER TABLE `multas`
  ADD CONSTRAINT `multas_ibfk_1` FOREIGN KEY (`IdPrestamo`) REFERENCES `prestamos` (`IdPrestamo`),
  ADD CONSTRAINT `multas_ibfk_2` FOREIGN KEY (`IdSocio`) REFERENCES `usuarios` (`IdUsuario`);

--
-- Filtros para la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD CONSTRAINT `notificaciones_ibfk_1` FOREIGN KEY (`IdUsuario`) REFERENCES `usuarios` (`IdUsuario`);

--
-- Filtros para la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD CONSTRAINT `prestamos_ibfk_1` FOREIGN KEY (`IdLibro`) REFERENCES `libros` (`IdLibro`),
  ADD CONSTRAINT `prestamos_ibfk_2` FOREIGN KEY (`IdSocio`) REFERENCES `usuarios` (`IdUsuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
