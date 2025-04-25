-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-04-2025 a las 04:32:32
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
-- Base de datos: `bd_gestorbiblioteca`
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
(1, 'Gabriel García Márquez', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Gabriel_García_Márquez.jpg'),
(2, 'Miguel de Cervantes', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Miguel_de_Cervantes.jpg'),
(3, 'Carlos Ruiz Zafón', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Carlos_Ruiz_Zafón.jpg'),
(4, 'Julio Cortázar', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Julio_Cortázar.jpg'),
(5, 'Jorge Luis Borges', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Jorge_Luis_Borges.jpg'),
(6, 'Isabel Allende', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Isabel_Allende.jpg'),
(7, 'George Orwell', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/George_Orwell.jpg'),
(8, 'Roberto Bolaño', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Roberto_Bolaño.jpg'),
(9, 'Octavio Paz', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Octavio_Paz.jpg'),
(10, 'Juan Rulfo', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Juan_Rulfo.jpg'),
(11, 'Mario Vargas Llosa', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Mario_Vargas_Llosa.jpg'),
(12, 'Ernesto Sabato', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Ernesto_Sabato.jpg'),
(13, 'Antoine de Saint Exupéry', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Antoine_de_Saint_Exupéry.jpg'),
(14, 'Laura Esquivel', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Laura_Esquivel.jpg'),
(15, 'Elena Garro', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Elena_Garro.jpg'),
(16, 'Umberto Eco', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Umberto_Eco.jpg'),
(17, 'Carlos Fuentes', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Carlos_Fuentes.jpg'),
(18, 'Manuel Puig', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Manuel_Puig.jpg'),
(19, 'Mario Benedetti', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Mario_Benedetti.jpg'),
(20, 'Tomás Eloy Martínez', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Tomás_Eloy_Martínez.jpg'),
(21, 'Adolfo Bioy Casares', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Adolfo_Bioy_Casares.jpg'),
(22, 'Camilo José Cela', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Camilo_José_Cela.jpg'),
(23, 'Alejo Carpentier', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Alejo_Carpentier.jpg'),
(24, 'Héctor Abad Faciolince', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Héctor_Abad_Faciolince.jpg'),
(25, 'Elena Poniatowska', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Elena_Poniatowska.jpg'),
(26, 'José Donoso', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/José_Donoso.jpg'),
(27, 'Fernando del Paso', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Fernando_del_Paso.jpg'),
(28, 'Ángeles Mastretta', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Ángeles_Mastretta.jpg'),
(29, 'Jack Kerouac', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Jack_Kerouac.jpg'),
(30, 'Juan Gabriel Vásquez', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Juan_Gabriel_Vásquez.jpg'),
(31, 'Fernando Vallejo', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Fernando_Vallejo.jpg'),
(32, 'Laura Restrepo', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Laura_Restrepo.jpg'),
(33, 'Fernanda Melchor', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Fernanda_Melchor.jpg'),
(34, 'Valeria Luiselli', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Valeria_Luiselli.jpg'),
(35, 'Carmen Laforet', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Carmen_Laforet.jpg'),
(36, 'Rosa Montero', 'Sobre el autor', 'C:/xampp/htdocs/Imagenes/Rosa_Montero.jpg');

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
(1, 'Sudamericana', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Sudamericana.jpg'),
(2, 'Francisco de Robles', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/FranciscodeRobles.jpg'),
(3, 'Planeta', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Planeta.jpg'),
(4, 'Emecé', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Emecé.jpg'),
(5, 'Plaza & Janés', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Plaza&Janés.jpg'),
(6, 'DeBolsillo', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/DeBolsillo.jpg'),
(7, 'Oveja Negra', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/OvejaNegra.jpg'),
(8, 'Anagrama', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Anagrama.jpg'),
(9, 'FCE', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/FCE.jpg'),
(10, 'Seix Barral', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/SeixBarral.jpg'),
(11, 'La Oveja Negra', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/LaOvejaNegra.jpg'),
(12, 'Alfaguara', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Alfaguara.jpg'),
(13, 'Salamandra', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Salamandra.jpg'),
(14, 'Joaquín Mortiz', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/JoaquínMortiz.jpg'),
(15, 'Lumen', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Lumen.jpg'),
(16, 'Destino', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Destino.jpg'),
(17, 'Era', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Era.jpg'),
(18, 'Océano', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/Océano.jpg'),
(19, 'Literatura Random House', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/LiteraturaRandomHouse.jpg'),
(20, 'Sexto Piso', 'Sobre la editorial', 'C:/xampp/htdocs/Imagenes/SextoPiso.jpg');

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
(1, 'Realismo mágico'),
(2, 'Clásico'),
(3, 'Misterio'),
(4, 'Experimental'),
(5, 'Cuentos'),
(6, 'Distopía'),
(7, 'Romance'),
(8, 'Novela'),
(9, 'Ensayo'),
(10, 'Psicológica'),
(11, 'Histórica'),
(12, 'Fábula'),
(13, 'Drama'),
(14, 'Ciencia ficción'),
(15, 'Memorias'),
(16, 'Crónica'),
(17, 'Novela beatnik');

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
(1, '978-0307474728', 'Cien años de soledad', 1, 'C:/xampp/htdocs/Imagenes/Cienañosdesoledad.jpg', 1967, 1, 1, 432, 'En el mítico pueblo de Macondo, la estirpe de los Buendía vive un siglo de glorias y fracasos marcado por amores prohibidos, guerras civiles y prodigios mágicos. José Arcadio Buendía funda la aldea, pero una maldición familiar perseguirá a sus descendientes hasta el último Aureliano, quien descifrará su destino en manuscritos antiguos mientras vientos de destrucción arrasan Macondo.'),
(2, '978-8420412146', 'Don Quijote de la Mancha', 2, 'C:/xampp/htdocs/Imagenes/DonQuijotedelaMancha.jpg', 1605, 2, 2, 1056, 'Alonso Quijano, un hidalgo enloquecido por leer libros de caballerías, se autonombra Don Quijote y sale a \"desfacer entuertos\" con su fiel escudero Sancho Panza. Confunde molinos con gigantes y posadas con castillos, mientras su idealismo choca contra la realidad. Una sátira sobre los sueños y la locura que se convirtió en la primera novela moderna.'),
(3, '978-8408043640', 'La sombra del viento', 3, 'C:/xampp/htdocs/Imagenes/Lasombradelviento.jpg', 2001, 3, 3, 544, 'En el Barcelona de posguerra, Daniel Sempere encuentra un libro maldito de Julián Carax en el Cementerio de los Libros Olvidados. Al investigar al autor, descubre que alguien quema todas sus obras y que su vida se entrelaza con la de Carax. Un misterio lleno de amores trágicos, venganzas y secretos familiares en una ciudad oscura y gótica.'),
(4, '978-8420424385', 'Rayuela', 4, 'C:/xampp/htdocs/Imagenes/Rayuela.jpg', 1963, 1, 4, 736, 'Horacio Oliveira, un intelectual argentino en París, busca el sentido de la vida entre discusiones filosóficas y su amor por la Maga. La novela puede leerse en orden tradicional o siguiendo el \"tablero de dirección\" del autor, saltando entre capítulos. Una obra experimental que juega con el caos, el jazz y la imposibilidad de encontrar respuestas absolutas.'),
(5, '978-8420638768', 'Ficciones', 5, 'C:/xampp/htdocs/Imagenes/Ficciones.jpg', 1944, 4, 5, 192, 'Esta colección de cuentos incluye obras maestras como \"La biblioteca de Babel\" (un universo-librería con todos los libros posibles) y \"El jardín de senderos que se bifurcan\" (un laberinto temporal). Borges explora ideas sobre el infinito, los espejos y realidades alternas con una prosa precisa que desafía la percepción convencional.'),
(6, '978-8401337201', 'La casa de los espíritus', 6, 'C:/xampp/htdocs/Imagenes/Lacasadelosespíritus.jpg', 1982, 5, 1, 496, 'Clara del Valle, con sus poderes telepáticos, y su esposo Esteban Trueba, un terrateniente violento, son los pilares de esta saga familiar en un país latinoamericano sin nombre. A través de generaciones, sus destinos se entrelazan con golpes de estado, amores prohibidos y revoluciones, mientras lo sobrenatural convive con lo cotidiano en una mansión llena de voces y fantasmas.'),
(7, '978-8499890944', '1984', 7, 'C:/xampp/htdocs/Imagenes/1984.jpg', 1949, 6, 6, 352, 'En un Londres distópico controlado por el Partido y el Gran Hermano, Winston Smith trabaja reescribiendo la historia para ajustarla a la propaganda oficial. Cuando comienza una relación prohibida con Julia y cuestiona el sistema, la Policía del Pensamiento lo vigila. Una reflexión aterradora sobre el totalitarismo, la manipulación del lenguaje y la pérdida de la individualidad.'),
(8, '978-0307472212', 'El amor en los tiempos del cólera', 1, 'C:/xampp/htdocs/Imagenes/Elamorenlostiemposdelcólera.jpg', 1985, 7, 7, 464, 'Florentino Ariza, un poeta romántico, es rechazado en su juventud por Fermina Daza, quien se casa con el doctor Juvenal Urbino. Tras 51 años de espera (y 622 amantes), Florentino declara su amor nuevamente a Fermina cuando ella queda viuda. Un barco fluvial será testigo de su pasión tardía, mientras una epidemia de cólera azota la ciudad.'),
(9, '978-8433968763', 'Los detectives salvajes', 8, 'C:/xampp/htdocs/Imagenes/Losdetectivessalvajes.jpg', 1998, 8, 8, 608, 'Los poetas Arturo Belano y Ulises Lima recorren México, Europa y África en busca de Cesárea Tinajero, fundadora del movimiento \"real visceralista\". A través de voces fragmentadas, el libro reconstruye su viaje entre drogas, sexo y poesía, mientras exploran los límites del arte y la amistad. Una novela caótica y lírica sobre la juventud y la búsqueda literaria.'),
(10, '978-9681682082', 'El laberinto de la soledad', 9, 'C:/xampp/htdocs/Imagenes/Ellaberintodelasoledad.jpg', 1950, 9, 9, 208, 'Este ensayo seminal analiza la identidad mexicana a través de símbolos como la fiesta, la máscara y la Chingada. Paz explora cómo la Conquista, la Revolución y el mestizaje forjaron un carácter nacional oscilante entre el hermetismo y la explosión. Una reflexión sobre la soledad del mexicano, atrapado entre tradiciones ancestrales y la modernidad.'),
(11, '978-9681682082', 'Pedro Páramo', 10, 'C:/xampp/htdocs/Imagenes/PedroPáramo.jpg', 1955, 9, 1, 128, 'Juan Preciado llega a Comala para cumplir la promesa hecha a su madre moribunda: encontrar a su padre, Pedro Páramo. Pero el pueblo está deshabitado, lleno de murmullos y fantasmas que narran cómo Pedro, un cacique cruel, destruyó todo por amor a Susana San Juan. Una obra maestra del realismo mágico donde los muertos hablan y el tiempo es circular.'),
(12, '978-8432217090', 'La ciudad y los perros', 11, 'C:/xampp/htdocs/Imagenes/Laciudadylosperros.jpg', 1963, 10, 8, 400, 'En el Colegio Militar Leoncio Prado de Lima, los cadetes apodados \"El Jaguar\", \"El Poeta\" y \"El Esclavo\" enfrentan un sistema violento y jerárquico. Cuando un crimen sacude la institución, las lealtades se rompen y cada uno revela su verdadera naturaleza. Una crítica feroz a la corrupción y el machismo en la sociedad peruana de los años 50.'),
(13, '978-9500728241', 'El túnel', 12, 'C:/xampp/htdocs/Imagenes/Eltúnel.jpg', 1948, 1, 10, 160, 'Juan Pablo Castel, un pintor obsesivo, mata a María Iribarne, la única persona que pareció entender su obra. Desde la cárcel, narra cómo su paranoia y celos lo llevaron al crimen. Una exploración psicológica de la soledad y la incomprensión, donde el amor se convierte en posesión y el arte en un refugio enfermizo.'),
(14, '978-0307472137', 'Crónica de una muerte anunciada', 1, 'C:/xampp/htdocs/Imagenes/Crónicadeunamuerteanunciada.jpg', 1981, 11, 8, 128, 'Santiago Nasar será asesinado por los hermanos Vicario para vengar el honor de su hermana Ángela, quien lo acusó de desvirgarla. Aunque todo el pueblo sabe del plan, nadie lo detiene. La novela reconstruye los hechos como un rompecabezas, mostrando cómo el destino, la pasión y la cobardía colectiva condujeron a un crimine inevitable.'),
(15, '978-8420638768', 'El Aleph', 5, 'C:/xampp/htdocs/Imagenes/ElAleph.jpg', 1949, 4, 5, 192, 'En esta colección de cuentos, Borges presenta \"El Aleph\", un punto en un sótano que contiene todos los lugares del universo vistos simultáneamente. Otros relatos exploran venganzas eternas (\"El inmortal\"), bibliotecas infinitas y hombres que olvidan su identidad. Juegos literarios que fusionan filosofía, matemáticas y fantasía con una prosa impecable.'),
(16, '978-8420442143', 'La fiesta del chivo', 11, 'C:/xampp/htdocs/Imagenes/Lafiestadelchivo.jpg', 2000, 12, 11, 512, 'En República Dominicana, Urania Cabral regresa para confrontar a su padre, un exministro de Trujillo. Mientras, se narra el último día del dictador (apodado \"El Chivo\") en 1961, cuando es emboscado por conspiradores. La novela alterna entre la crueldad del régimen, los traumas de Urania y la caída del tirano, mostrando el horror y sus secuelas.'),
(17, '978-8498381498', 'El principito', 13, 'C:/xampp/htdocs/Imagenes/Elprincipito.jpg', 1943, 13, 12, 96, 'Un piloto perdido en el Sahara conoce a un niño proveniente del asteroide B-612, quien le cuenta su viaje por planetas habitados por adultos absurdos (un rey sin súbditos, un farolero que enciende lámparas inútiles). Con su rosa caprichosa y el zorro que le enseña sobre el amor, el Principito revela verdades simples pero profundas sobre la amistad y la pérdida.'),
(18, '978-8401337201', 'Como agua para chocolate', 14, 'C:/xampp/htdocs/Imagenes/Comoaguaparachocolate.jpg', 1989, 3, 1, 256, 'Tita, la hija menor, está condenada a cuidar a su madre en vez de casarse. Cuando su sobrina Pedro se une a su hermana, Tita canaliza sus emociones en la cocina: sus lágrimas en la sopa provocan nostalgia, su pasión en el mole desata ardores. Cada capítulo, con una receta, muestra cómo la tradición y el deseo chocan en el México revolucionario.'),
(19, '978-9682701051', 'Los recuerdos del porvenir', 15, 'C:/xampp/htdocs/Imagenes/Losrecuerdosdelporvenir.jpg', 1963, 14, 8, 240, 'El pueblo de Ixtepec narra su propia historia durante la Guerra Cristera: el general Francisco Rosas impone su ley mientras Julia Andrade y Nicolás Moncada viven un amor imposible. La tierra misma parece recordar las injusticias, mezclando lo real con lo sobrenatural. Una crítica poética a la violencia institucional y la memoria colectiva en el México posrevolucionario.'),
(20, '978-8426413271', 'El nombre de la rosa', 16, 'C:/xampp/htdocs/Imagenes/Elnombredelarosa.jpg', 1980, 15, 11, 592, 'En 1327, el fraile franciscano Guillermo de Baskerville investiga muertes sospechosas en una abadía benedictina. Con ayuda del novicio Adso, descubre que las víctimas estudiaban un libro perdido de Aristóteles sobre la comedia, oculto en una biblioteca laberíntica. Una novela erudita que mezcla teología, semiótica y misterio medieval.'),
(21, '978-9681682082', 'La región más transparente', 17, 'C:/xampp/htdocs/Imagenes/Laregiónmástransparente.jpg', 1958, 9, 8, 448, 'En el México de los años 50, Ixca Cienfuegos teje las historias de empresarios, artistas y revolucionarios que intentan sobrevivir en una ciudad contradictoria. Desde el banquero Federico Robles hasta la prostituta Gladys, sus destinos chocan con la corrupción y el fracaso del sueño posrevolucionario. Una radiografía ácida de la sociedad mexicana moderna.'),
(22, '978-8432217090', 'El beso de la mujer araña', 18, 'C:/xampp/htdocs/Imagenes/Elbesodelamujeraraña.jpg', 1976, 10, 13, 288, 'En una cárcel argentina, Valentín, un revolucionario, y Molina, un homosexual condenado por \"corrupción de menores\", comparten celda. A través de películas que Molina narra y confesiones íntimas, nace una amistad que desafía sus diferencias. Una conmovedora reflexión sobre política, identidad y el poder transformador del afecto en tiempos oscuros.'),
(23, '978-9500728241', 'La tregua', 19, 'C:/xampp/htdocs/Imagenes/Latregua.jpg', 1960, 1, 8, 160, 'Martín Santomé, un viudo cercano a la jubilación, encuentra un inesperado amor con Laura Avellaneda, una joven compañera de trabajo. Su diario registra esta \"tregua\" de felicidad antes de que la tragedia golpee. Una novela íntima sobre el amor tardío, la rutina burocrática y la fugacidad de la dicha, escrita con la sencillez poética de Benedetti.'),
(24, '978-9681682082', 'La muerte de Artemio Cruz', 17, 'C:/xampp/htdocs/Imagenes/LamuertedeArtemioCruz.jpg', 1962, 9, 8, 320, 'En su lecho de muerte, el poderoso empresario Artemio Cruz revive momentos clave de su vida: desde su participación en la Revolución Mexicana hasta sus traiciones que lo llevaron al éxito. Tres voces (yo, tú, él) narran su ascenso y caída moral, mostrando el costo del poder en un país marcado por la desigualdad.'),
(25, '978-0307472137', 'El coronel no tiene quien le escriba', 1, 'C:/xampp/htdocs/Imagenes/Elcoronelnotienequienleescriba.jpg', 1961, 1, 8, 96, 'Un veterano de la Guerra de los Mil Días espera, durante quince años, una pensión que nunca llega. Con su esposa enferma y un gallo de pelea como única esperanza, resiste con dignidad en un pueblo donde la pobreza y la burocracia ahogan los sueños. Una obra maestra de economía narrativa sobre la espera y la resistencia silenciosa.'),
(26, '978-8408043640', 'Santa Evita', 20, 'C:/xampp/htdocs/Imagenes/SantaEvita.jpg', 1995, 3, 11, 384, 'Tras su muerte, el cadáver embalsamado de Eva Perón inicia un viaje surrealista: es secuestrado, escondido y hasta \"casado\" con un dictador. La novela mezcla investigación periodística y ficción para explorar el mito de Evita, su poder en vida y la locura política que desató su cuerpo sin vida en la Argentina de los años 50.'),
(27, '978-8420638768', 'La invención de Morel', 21, 'C:/xampp/htdocs/Imagenes/LainvencióndeMorel.jpg', 1940, 4, 14, 128, 'Un fugitivo llega a una isla abandonada donde descubre extrañas proyecciones de personas que repiten acciones eternamente. Al enamorarse de Faustine, una mujer en esas imágenes, intenta descifrar el invento del científico Morel: una máquina que captura la realidad en loops infinitos. Una fascinante fábula sobre inmortalidad y obsesión.'),
(28, '978-8420412146', 'La familia de Pascual Duarte', 22, 'C:/xampp/htdocs/Imagenes/LafamiliadePascualDuarte.jpg', 1942, 16, 8, 192, 'Pascual Duarte, un campesino extremeño, narra desde prisión su vida de violencia: asesinatos, abusos y una fatalidad que parece perseguirlo. Escrita como una falsa autobiografía, esta novela fundó el \"tremendismo\" literario al mostrar la crudeza rural española sin romanticismos. Un retrato descarnado de la miseria y el destino trágico.'),
(29, '978-8408043640', 'El juego del ángel', 3, 'C:/xampp/htdocs/Imagenes/Eljuegodelángel.jpg', 2008, 3, 3, 544, 'En el Barcelona de los años 20, el joven escritor David Martín acepta un encargo misterioso: escribir un libro que \"cambiará el mundo\" para un editor siniestro. Atrapado entre amores peligrosos y secretos ocultos en la Torre del Ángel, descubre que su vida copia una tragedia ocurrida décadas atrás. Una historia gótica sobre creación y obsesión.'),
(30, '978-9681682082', 'Los pasos perdidos', 23, 'C:/xampp/htdocs/Imagenes/Lospasosperdidos.jpg', 1953, 9, 8, 320, 'Un musicólogo viaja a la selva sudamericana en busca de instrumentos primitivos, pero el viaje se convierte en un regreso a los orígenes: abandona su vida urbana vacía por una comunidad atemporal. Cuando decide volver a \"civilización\", descubre que el tiempo en la selva sigue otro ritmo. Una reflexión sobre el progreso y el paraíso perdido.'),
(31, '978-8432217090', 'La casa verde', 11, 'C:/xampp/htdocs/Imagenes/Lacasaverde.jpg', 1966, 10, 8, 448, 'En la selva peruana, Bonifacia (la \"selvática\") es separada de su mundo y termina en un burdel llamado La Casa Verde. La novela entrelaza su historia con la de Fushía, un contrabandista japonés, y Lituma, un sargento obsesionado con ella. Vargas Llosa retrata la explotación y el mestizaje en una narrativa audaz y polifónica.'),
(32, '978-8408043640', 'El olvido que seremos', 24, 'C:/xampp/htdocs/Imagenes/Elolvidoqueseremos.jpg', 2006, 3, 15, 288, 'Héctor Abad Gómez, un médico y activista colombiano, es asesinado por defender derechos humanos. Su hijo, el escritor Héctor Abad Faciolince, reconstruye su vida con ternura y rabia: su lucha contra la desigualdad, su amor por la poesía y cómo su muerte marcó a la familia. Un homenaje literario que es también una denuncia política.'),
(33, '978-9684114259', 'La noche de Tlatelolco', 25, 'C:/xampp/htdocs/Imagenes/LanochedeTlatelolco.jpg', 1971, 17, 16, 320, 'Elena Poniatowska compila voces de estudiantes, madres, soldados y testigos de la masacre del 2 de octubre de 1968 en la Plaza de las Tres Culturas. Con técnica periodística y sensibilidad literaria, expone cómo el gobierno mexicano silenció protestas estudiantiles con violencia. Un documento esencial sobre memoria histórica y resistencia.'),
(34, '978-8432217090', 'El obsceno pájaro de la noche', 26, 'C:/xampp/htdocs/Imagenes/Elobscenopájarodelanoche.jpg', 1970, 10, 8, 448, 'En un conventillo chileno llamado \"La Rinconada\", Mudito (un hombre que pierde identidad) sirve a una familia decadente mientras narra pesadillas sobre monstruos y nacimientos deformes. La novela, escrita en un flujo de conciencia alucinante, explora la marginalidad, el cuerpo y el miedo a la nada. Una obra maestra del grotesco y lo surreal.'),
(35, '978-8420442143', 'La guerra del fin del mundo', 11, 'C:/xampp/htdocs/Imagenes/Laguerradelfindelmundo.jpg', 1981, 12, 11, 512, 'En el Brasil del siglo XIX, el fanático religioso Antonio Conselheiro lidera una rebelión en Canudos, donde miles de pobres creen estar construyendo el reino de Dios. El gobierno envía al ejército para exterminarlos. Basada en hechos reales, esta épica muestra el choque entre fe y progreso, con una narrativa tan vasta como el sertón.'),
(36, '978-8420638768', 'El libro de arena', 5, 'C:/xampp/htdocs/Imagenes/Ellibrodearena.jpg', 1975, 4, 5, 192, 'En esta colección de Borges, un hombre adquiere un libro sagrado cuyas páginas nunca están en el mismo orden (\"El libro de arena\"). Otros cuentos presentan un espejo que duplica la realidad, un rey que construye un mapa a escala 1:1 y un poeta que olvida su identidad. Juegos metafísicos sobre lo infinito y lo efímero.'),
(37, '978-8432217090', 'La resistencia', 12, 'C:/xampp/htdocs/Imagenes/Laresistencia.jpg', 2000, 10, 9, 160, 'En cinco ensayos epistolares, Ernesto Sabato alerta sobre la deshumanización en la era tecnológica: la pérdida de valores, la alienación y la necesidad de defender la belleza, el arte y los lazos humanos. Un llamado urgente, escrito con tono apasionado, a resistir contra el vacío de la posmodernidad.'),
(38, '978-9681682082', 'Noticias del imperio', 27, 'C:/xampp/htdocs/Imagenes/Noticiasdelimperio.jpg', 1987, 9, 11, 672, 'Desde su encierro en un castillo belga, la emperatriz Carlota (enloquecida) narra cómo Napoleón III impuso a su esposo Maximiliano como emperador de México, y su posterior fusilamiento. La novela alterna su monólogo delirante con crónicas históricas, creando un fresco sobre el Segundo Imperio Mexicano y sus contradicciones trágicas.'),
(39, '978-9706511345', 'Arráncame la vida', 28, 'C:/xampp/htdocs/Imagenes/Arráncamelavida.jpg', 1985, 18, 8, 304, 'Catarina Kauffmann, una mujer de clase alta, se casa con Andrés Ascencio, un político que escala al poder mediante la corrupción y la violencia. Desde su perspectiva ingenua al principio y luego desencantada, vivimos el México posrevolucionario donde el machismo y el autoritarismo dictan las reglas. Una novela fresca y mordaz sobre el poder.'),
(40, '978-8433966479', 'Los subterráneos', 29, 'C:/xampp/htdocs/Imagenes/Lossubterráneos.jpg', 1958, 8, 17, 192, 'En el San Francisco de los años 50, Leo Percepied (alter ego de Kerouac) vive un apasionado y caótico romance con Mardou Fox, una mujer afroamericana, mientras el jazz, las drogas y la filosofía existencialista envuelven su mundo. Una novela autobiográfica que captura el espíritu rebelde de la Generación Beat, escrita en el estilo fluido y frenético característico de Kerouac.'),
(41, '978-9500728241', 'Sobre héroes y tumbas', 12, 'C:/xampp/htdocs/Imagenes/Sobrehéroesytumbas.jpg', 1961, 1, 8, 480, 'En el Buenos Aires de los años 50, Martín conoce a Alejandra, descendiente de un general traidor, y se enamora perdidamente. La novela entrelaza su historia con el \"Informe sobre ciegos\", un manuscrito paranoico escrito por el padre de ella. Una obra total sobre el amor, la locura y los fantasmas históricos de Argentina.'),
(42, '978-8408043640', 'La piel del cielo', 25, 'C:/xampp/htdocs/Imagenes/Lapieldelcielo.jpg', 2001, 3, 8, 384, 'Lorenzo de Tena, un astrónomo mexicano, lucha por hacer ciencia en un país donde el sistema premia la mediocridad. Desde su infancia en el campo hasta sus viajes internacionales, su pasión por las estrellas choca con la realidad política. Poniatowska retrata el México del siglo XX a través de una vida dedicada al conocimiento.'),
(43, '978-8420412146', 'El ruido de las cosas al caer', 30, 'C:/xampp/htdocs/Imagenes/Elruidodelascosasalcaer.jpg', 2011, 12, 8, 288, 'Antonio Yammara, un profesor bogotano, investiga la vida de Ricardo Laverde, un piloto asesinado por narcotraficantes. La novela explora cómo el narcoterrorismo de los años 90 en Colombia destruyó vidas y dejó heridas que aún sangran. Una historia sobre el miedo, la memoria y los lazos que nos salvan.'),
(44, '978-8420442143', 'La virgen de los sicarios', 31, 'C:/xampp/htdocs/Imagenes/Lavirgendelossicarios.jpg', 1994, 12, 8, 128, 'Un escritor regresa a Medellín después de años en Europa y contrata a Alexis, un joven sicario, como su amante-protector. La ciudad, devastada por el narcotráfico, es un infierno donde la muerte es trivial y la belleza sobrevive en rincones inesperados. Una novela cruda y lírica sobre la violencia colombiana.'),
(45, '978-8408043640', 'Delirio', 32, 'C:/xampp/htdocs/Imagenes/Delirio.jpg', 2004, 3, 8, 352, 'Agustina, una mujer bogotana, sufre un colapso mental el mismo día que su esposo desaparece. Mientras él busca respuestas, descubrimos su historia familiar marcada por secretos incestuosos y el trastorno bipolar. Una novela sobre el amor que resiste a la locura y las grietas ocultas de la sociedad colombiana.'),
(46, '978-8420412146', 'La forma de las ruinas', 30, 'C:/xampp/htdocs/Imagenes/Laformadelasruinas.jpg', 2015, 12, 8, 512, 'Un escritor obsesionado con conspiraciones investiga los asesinatos de líderes colombianos (Gaitán, Galán). La novela mezcla hechos reales con ficción para explorar cómo la paranoia y la violencia moldean la historia. Vásquez reflexiona sobre la memoria nacional y el papel del narrador frente a la verdad.'),
(47, '978-8420439778', 'Temporada de huracanes', 33, 'C:/xampp/htdocs/Imagenes/Temporadadehuracanes.jpg', 2017, 19, 8, 224, 'En La Matosa, un pueblo mexicano, la Bruja es asesinada y su cuerpo encontrado en un canal. La novela sigue a quienes la rodearon: prostitutas, narcos y marginados. Fernanda Melchor escribe con un torrente verbal hipnótico sobre pobreza, misoginia y violencia, donde hasta la naturaleza parece conspirar contra los personajes.'),
(48, '978-8415601864', 'Los ingrávidos', 34, 'C:/xampp/htdocs/Imagenes/Losingrávidos.jpg', 2011, 20, 8, 144, 'Una escritora viaja a México para reconstruir la vida de Gilberto Owen, poeta olvidado del grupo Contemporáneos. A través de cartas y recuerdos, descubre paralelos entre su vida y la de él. Una novela lírica sobre fantasmas literarios, el exilio y cómo el pasado resuena en el presente.'),
(49, '978-8420412146', 'Nada', 35, 'C:/xampp/htdocs/Imagenes/Nada.jpg', 1945, 16, 8, 256, 'Andrea llega a Barcelona para estudiar y se aloja con su familia materna en un piso sombrío. Entre tías amargadas, un tío artista fracasado y una atmósfera opresiva, descubre el vacío de la posguerra española. Una novela existencialista que captura la angustia de una generación marcada por la Guerra Civil.'),
(50, '978-8432217090', 'La ridícula idea de no volver a verte', 36, 'C:/xampp/htdocs/Imagenes/Laridículaideadenovolveraverte.jpg', 2013, 10, 15, 224, 'Rosa Montero mezcla memorias personales de su duelo por la muerte de su esposo con la vida de Marie Curie, quien también perdió a su amor tempranamente. A través de diarios, fotos y reflexiones, explora cómo el dolor transforma y qué significa seguir viviendo. Un híbrido literario íntimo y universal.');

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
(1, 14, '2025-04-21'),
(2, 14, '2025-04-21'),
(3, 14, '2025-04-21'),
(4, 14, '2025-04-21'),
(5, 14, '2025-04-21'),
(6, 14, '2025-04-21'),
(7, 14, '2025-04-21'),
(8, 14, '2025-04-21'),
(9, 14, '2025-04-21'),
(10, 14, '2025-04-21'),
(11, 14, '2025-04-21'),
(12, 14, '2025-04-21'),
(13, 14, '2025-04-21'),
(14, 14, '2025-04-21'),
(15, 14, '2025-04-21'),
(16, 15, '2025-04-21'),
(17, 15, '2025-04-22'),
(18, 15, '2025-04-22'),
(19, 15, '2025-04-22'),
(20, 15, '2025-04-22'),
(21, 15, '2025-04-22'),
(22, 15, '2025-04-22'),
(23, 15, '2025-04-22'),
(24, 15, '2025-04-22'),
(25, 15, '2025-04-22'),
(26, 15, '2025-04-22'),
(27, 15, '2025-04-22'),
(28, 15, '2025-04-22'),
(29, 15, '2025-04-22'),
(30, 15, '2025-04-22'),
(31, 16, '2025-04-22'),
(32, 16, '2025-04-22'),
(33, 16, '2025-04-22'),
(34, 16, '2025-04-22'),
(35, 16, '2025-04-22'),
(36, 16, '2025-04-22'),
(37, 16, '2025-04-22'),
(38, 16, '2025-04-22'),
(39, 16, '2025-04-22'),
(40, 16, '2025-04-22'),
(41, 17, '2025-04-22'),
(42, 17, '2025-04-22'),
(43, 17, '2025-04-22'),
(44, 17, '2025-04-22'),
(45, 17, '2025-04-22'),
(46, 17, '2025-04-22'),
(47, 17, '2025-04-22'),
(48, 17, '2025-04-22'),
(49, 17, '2025-04-22'),
(50, 17, '2025-04-22');

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
(2, 2, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(3, 3, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(4, 4, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(5, 5, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(14, 14, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(15, 15, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(16, 16, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(17, 14, 'Agregaste el libro \'Cien años de soledad\' a la base de datos.', 'Informativa'),
(18, 14, 'Agregaste el libro \'Don Quijote de la Mancha\' a la base de datos.', 'Informativa'),
(19, 14, 'Agregaste el libro \'La sombra del viento\' a la base de datos.', 'Informativa'),
(20, 14, 'Agregaste el libro \'Rayuela\' a la base de datos.', 'Informativa'),
(21, 14, 'Agregaste el libro \'Ficciones\' a la base de datos.', 'Informativa'),
(22, 14, 'Agregaste el libro \'La casa de los espíritus\' a la base de datos.', 'Informativa'),
(23, 14, 'Agregaste el libro \'1984\' a la base de datos.', 'Informativa'),
(24, 14, 'Agregaste el libro \'El amor en los tiempos del cólera\' a la base de datos.', 'Informativa'),
(25, 14, 'Agregaste el libro \'Los detectives salvajes\' a la base de datos.', 'Informativa'),
(26, 14, 'Agregaste el libro \'El laberinto de la soledad\' a la base de datos.', 'Informativa'),
(27, 14, 'Agregaste el libro \'Pedro Páramo\' a la base de datos.', 'Informativa'),
(28, 15, 'Agregaste el libro \'La ciudad y los perros\' a la base de datos.', 'Informativa'),
(29, 15, 'Agregaste el libro \'El túnel\' a la base de datos.', 'Informativa'),
(30, 15, 'Agregaste el libro \'Crónica de una muerte anunciada\' a la base de datos.', 'Informativa'),
(31, 15, 'Agregaste el libro \'El Aleph\' a la base de datos.', 'Informativa'),
(32, 15, 'Agregaste el libro \'La fiesta del chivo\' a la base de datos.', 'Informativa'),
(33, 15, 'Agregaste el libro \'El principito\' a la base de datos.', 'Informativa'),
(34, 15, 'Agregaste el libro \'Como agua para chocolate\' a la base de datos.', 'Informativa'),
(35, 15, 'Agregaste el libro \'Los recuerdos del porvenir\' a la base de datos.', 'Informativa'),
(36, 15, 'Agregaste el libro \'El nombre de la rosa\' a la base de datos.', 'Informativa'),
(37, 15, 'Agregaste el libro \'La región más transparente\' a la base de datos.', 'Informativa'),
(38, 15, 'Agregaste el libro \'El beso de la mujer araña\' a la base de datos.', 'Informativa'),
(39, 15, 'Agregaste el libro \'La tregua\' a la base de datos.', 'Informativa'),
(40, 15, 'Agregaste el libro \'La muerte de Artemio Cruz\' a la base de datos.', 'Informativa'),
(41, 15, 'Agregaste el libro \'El coronel no tiene quien le escriba\' a la base de datos.', 'Informativa'),
(42, 15, 'Agregaste el libro \'Santa Evita\' a la base de datos.', 'Informativa'),
(43, 15, 'Agregaste el libro \'La invención de Morel\' a la base de datos.', 'Informativa'),
(44, 15, 'Agregaste el libro \'La familia de Pascual Duarte\' a la base de datos.', 'Informativa'),
(45, 15, 'Agregaste el libro \'El juego del ángel\' a la base de datos.', 'Informativa'),
(46, 15, 'Agregaste el libro \'Los pasos perdidos\' a la base de datos.', 'Informativa'),
(47, 16, 'Agregaste el libro \'La casa verde\' a la base de datos.', 'Informativa'),
(48, 16, 'Agregaste el libro \'El olvido que seremos\' a la base de datos.', 'Informativa'),
(49, 16, 'Agregaste el libro \'La noche de Tlatelolco\' a la base de datos.', 'Informativa'),
(50, 16, 'Agregaste el libro \'El obsceno pájaro de la noche\' a la base de datos.', 'Informativa'),
(51, 16, 'Agregaste el libro \'La guerra del fin del mundo\' a la base de datos.', 'Informativa'),
(52, 16, 'Agregaste el libro \'El libro de arena\' a la base de datos.', 'Informativa'),
(53, 16, 'Agregaste el libro \'La resistencia\' a la base de datos.', 'Informativa'),
(54, 16, 'Agregaste el libro \'Noticias del imperio\' a la base de datos.', 'Informativa'),
(55, 16, 'Agregaste el libro \'Arráncame la vida\' a la base de datos.', 'Informativa'),
(56, 16, 'Agregaste el libro \'Los subterráneos\' a la base de datos.', 'Informativa'),
(57, 17, 'Termine de configurar su cuenta agregando una foto.', 'Informativa'),
(58, 17, 'Agregaste el libro \'Sobre héroes y tumbas\' a la base de datos.', 'Informativa'),
(59, 17, 'Agregaste el libro \'La piel del cielo\' a la base de datos.', 'Informativa'),
(60, 17, 'Agregaste el libro \'El ruido de las cosas al caer\' a la base de datos.', 'Informativa'),
(61, 17, 'Agregaste el libro \'La virgen de los sicarios\' a la base de datos.', 'Informativa'),
(62, 17, 'Agregaste el libro \'Delirio\' a la base de datos.', 'Informativa'),
(63, 17, 'Agregaste el libro \'La forma de las ruinas\' a la base de datos.', 'Informativa'),
(64, 17, 'Agregaste el libro \'Temporada de huracanes\' a la base de datos.', 'Informativa'),
(65, 17, 'Agregaste el libro \'Los ingrávidos\' a la base de datos.', 'Informativa'),
(66, 17, 'Agregaste el libro \'Nada\' a la base de datos.', 'Informativa'),
(67, 17, 'Agregaste el libro \'La ridícula idea de no volver a verte\' a la base de datos.', 'Informativa');

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
(1, 'Abel Vargas', 'Abel', '1234', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Abel.jpg', 'socio'),
(2, 'Hector Edgardo', 'Hector', '1234', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Hector.jpg', 'socio'),
(3, 'Cesar Emilio', 'Cesar', '1234', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Cesar.jpg', 'socio'),
(4, 'Andre Sebastian', 'Andre', '1234', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Andre.jpg', 'socio'),
(5, 'Irma Yarith', 'Irma', '1234', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Irma.jpg', 'socio'),
(14, 'Administrador', 'Admin1', '1234', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Admin1.jpg', 'administrador'),
(15, 'Administrador', 'Admin2', '1234', '2025-04-20', 'C:/xampp/htdocs/Imagenes/Admin2.jpg', 'administrador'),
(16, 'administrador', 'Admin3', '1234', '2025-04-21', 'C:/xampp/htdocs/Imagenes/Admin3.jpg', 'administrador'),
(17, 'administrador', 'Admin4', '1234', '2025-04-22', 'C:/xampp/htdocs/Imagenes/Admin4.jpg', 'administrador');

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
  MODIFY `IdAutor` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `editoriales`
--
ALTER TABLE `editoriales`
  MODIFY `IdEditorial` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `generos`
--
ALTER TABLE `generos`
  MODIFY `IdGenero` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `IdLibro` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de la tabla `multas`
--
ALTER TABLE `multas`
  MODIFY `IdMulta` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  MODIFY `IdNotificacion` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  MODIFY `IdPrestamo` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `IdUsuario` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
