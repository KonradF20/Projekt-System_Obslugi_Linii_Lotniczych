-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Cze 04, 2025 at 01:16 AM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projekt`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `airports`
--

CREATE TABLE `airports` (
  `airport_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `iata_code` char(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `airports`
--

INSERT INTO `airports` (`airport_id`, `name`, `city`, `country`, `iata_code`) VALUES
(1, 'Lotnisko Chopina w Warszawie', 'Warszawa', 'Polska', 'WAW'),
(2, 'Lotnisko im. Jana Pawła II Kraków-Balice', 'Kraków', 'Polska', 'KRK'),
(3, 'Lotnisko im. Lecha Wałęsy w Gdańsku', 'Gdańsk', 'Polska', 'GDN'),
(4, 'Port Lotniczy Wrocław-Strachowice', 'Wrocław', 'Polska', 'WRO'),
(5, 'Port Lotniczy Poznań-Ławica', 'Poznań', 'Polska', 'POZ'),
(6, 'London Heathrow Airport', 'Londyn', 'Wielka Brytania', 'LHR'),
(7, 'John F. Kennedy International Airport', 'Nowy Jork', 'USA', 'JFK'),
(8, 'Charles de Gaulle Airport', 'Paryż', 'Francja', 'CDG'),
(9, 'Frankfurt am Main Airport', 'Frankfurt', 'Niemcy', 'FRA'),
(10, 'Dubai International Airport', 'Dubaj', 'Zjednoczone Emiraty Arabskie', 'DXB'),
(11, 'Amsterdam Schiphol Airport', 'Amsterdam', 'Holandia', 'AMS'),
(12, 'Adolfo Suárez Madrid–Barajas Airport', 'Madryt', 'Hiszpania', 'MAD'),
(13, 'Los Angeles International Airport', 'Los Angeles', 'USA', 'LAX'),
(14, 'Hong Kong International Airport', 'Hong Kong', 'Chiny', 'HKG'),
(15, 'Singapore Changi Airport', 'Singapur', 'Singapur', 'SIN'),
(16, 'Sydney Kingsford Smith Airport', 'Sydney', 'Australia', 'SYD'),
(17, 'Incheon International Airport', 'Seul', 'Korea Południowa', 'ICN'),
(18, 'Suvarnabhumi Airport', 'Bangkok', 'Tajlandia', 'BKK'),
(19, 'Istanbul Airport', 'Istanbul', 'Turcja', 'IST'),
(20, 'Vancouver International Airport', 'Vancouver', 'Kanada', 'YVR');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `baggage`
--

CREATE TABLE `baggage` (
  `baggage_id` int(11) NOT NULL,
  `reservation_id` int(11) NOT NULL,
  `weight` decimal(5,2) NOT NULL,
  `dimensions` varchar(50) DEFAULT NULL,
  `type` enum('checked','carry-on') NOT NULL DEFAULT 'checked'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `flights`
--

CREATE TABLE `flights` (
  `flight_id` int(11) NOT NULL,
  `flight_number` varchar(10) NOT NULL,
  `departure_airport_id` int(11) NOT NULL,
  `arrival_airport_id` int(11) NOT NULL,
  `departure_time` datetime NOT NULL,
  `arrival_time` datetime NOT NULL,
  `plane_id` int(11) NOT NULL,
  `available_seats` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `travel_class` enum('economy','business','first') NOT NULL DEFAULT 'economy',
  `terminal` varchar(10) DEFAULT NULL,
  `gate` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `flights`
--

INSERT INTO `flights` (`flight_id`, `flight_number`, `departure_airport_id`, `arrival_airport_id`, `departure_time`, `arrival_time`, `plane_id`, `available_seats`, `price`, `travel_class`, `terminal`, `gate`) VALUES
(1, 'FLWAW01', 1, 6, '2025-06-10 08:00:00', '2025-06-10 10:00:00', 1, 189, 320.00, 'economy', 'A', '12'),
(2, 'FLWAW02', 1, 7, '2025-06-11 09:30:00', '2025-06-11 18:30:00', 2, 180, 2800.00, 'business', 'A', '14'),
(3, 'FLWAW03', 1, 8, '2025-06-12 11:00:00', '2025-06-12 13:00:00', 3, 396, 300.00, 'economy', 'B', '7'),
(4, 'FLWAW04', 1, 9, '2025-06-13 12:15:00', '2025-06-13 13:45:00', 4, 260, 720.00, 'business', 'B', '9'),
(5, 'FLWAW05', 1, 10, '2025-06-14 14:00:00', '2025-06-14 20:30:00', 5, 120, 1000.00, 'economy', 'C', '3'),
(6, 'FLKRK01', 2, 11, '2025-06-10 08:30:00', '2025-06-10 10:30:00', 6, 296, 700.00, 'business', '1', 'A2'),
(7, 'FLKRK02', 2, 12, '2025-06-11 10:00:00', '2025-06-11 13:00:00', 7, 315, 1750.00, 'first', '1', 'A5'),
(8, 'FLKRK03', 2, 13, '2025-06-12 11:30:00', '2025-06-12 22:30:00', 8, 416, 2200.00, 'economy', '2', 'B1'),
(9, 'FLKRK04', 2, 14, '2025-06-13 13:00:00', '2025-06-13 22:00:00', 9, 220, 3500.00, 'business', '2', 'B4'),
(10, 'FLKRK05', 2, 15, '2025-06-14 14:30:00', '2025-06-15 01:30:00', 10, 90, 4200.00, 'business', '3', 'C3'),
(11, 'FLGDN01', 3, 16, '2025-06-10 07:45:00', '2025-06-11 03:45:00', 1, 189, 400.00, 'economy', 'A', '16'),
(12, 'FLGDN02', 3, 17, '2025-06-11 09:15:00', '2025-06-11 19:15:00', 2, 180, 900.00, 'business', 'A', '18'),
(13, 'FLGDN03', 3, 18, '2025-06-12 10:45:00', '2025-06-12 20:45:00', 3, 396, 450.00, 'economy', 'B', '11'),
(14, 'FLGDN04', 3, 19, '2025-06-13 12:00:00', '2025-06-13 14:00:00', 4, 260, 1300.00, 'first', 'B', '13'),
(15, 'FLGDN05', 3, 20, '2025-06-14 13:30:00', '2025-06-15 00:00:00', 5, 120, 2600.00, 'business', 'C', '5'),
(16, 'FLWRO01', 4, 6, '2025-06-11 08:15:00', '2025-06-11 10:15:00', 6, 296, 280.00, 'economy', '1', 'A7'),
(17, 'FLWRO02', 4, 9, '2025-06-12 09:45:00', '2025-06-12 11:15:00', 7, 315, 1100.00, 'business', '1', 'A9'),
(18, 'FLWRO03', 4, 11, '2025-06-13 11:00:00', '2025-06-13 13:00:00', 8, 416, 3200.00, 'first', '2', 'B6'),
(19, 'FLWRO04', 4, 12, '2025-06-14 12:30:00', '2025-06-14 15:30:00', 9, 220, 800.00, 'economy', '2', 'B8'),
(20, 'FLWRO05', 4, 19, '2025-06-15 14:00:00', '2025-06-15 16:00:00', 10, 90, 1500.00, 'business', '3', 'C7'),
(21, 'FLPOZ01', 5, 7, '2025-06-10 08:30:00', '2025-06-10 17:30:00', 1, 189, 2200.00, 'economy', 'A', '10'),
(22, 'FLPOZ02', 5, 13, '2025-06-11 10:00:00', '2025-06-11 21:00:00', 2, 180, 4800.00, 'business', 'A', '12'),
(23, 'FLPOZ03', 5, 15, '2025-06-12 11:30:00', '2025-06-12 22:30:00', 3, 396, 7000.00, 'first', 'B', '4'),
(24, 'FLPOZ04', 5, 16, '2025-06-13 13:00:00', '2025-06-14 09:00:00', 4, 260, 500.00, 'economy', 'B', '6'),
(25, 'FLPOZ05', 5, 20, '2025-06-14 14:30:00', '2025-06-15 01:00:00', 5, 120, 1200.00, 'business', 'C', '2'),
(26, 'FLRET01', 6, 1, '2025-06-17 12:00:00', '2025-06-17 14:00:00', 1, 189, 400.00, 'economy', '1', 'A3'),
(27, 'FLRET02', 7, 1, '2025-06-18 13:30:00', '2025-06-18 22:30:00', 2, 180, 1000.00, 'business', '1', 'A4'),
(28, 'FLRET03', 8, 1, '2025-06-19 15:00:00', '2025-06-19 17:00:00', 3, 396, 2800.00, 'business', '2', 'B3'),
(29, 'FLRET04', 9, 1, '2025-06-20 16:15:00', '2025-06-20 17:45:00', 4, 260, 2100.00, 'economy', '2', 'B5'),
(30, 'FLRET05', 10, 1, '2025-06-21 18:00:00', '2025-06-22 00:30:00', 5, 120, 3700.00, 'business', '3', 'C4'),
(31, 'FLRET06', 11, 2, '2025-06-17 11:30:00', '2025-06-17 13:30:00', 6, 296, 280.00, 'economy', 'A', '8'),
(32, 'FLRET07', 12, 2, '2025-06-18 13:00:00', '2025-06-18 16:00:00', 7, 315, 800.00, 'business', 'A', '9'),
(33, 'FLRET08', 13, 2, '2025-06-19 14:30:00', '2025-06-20 01:30:00', 8, 416, 1300.00, 'first', 'B', '1'),
(34, 'FLRET09', 14, 2, '2025-06-20 16:00:00', '2025-06-21 01:00:00', 9, 220, 3500.00, 'business', 'B', '2'),
(35, 'FLRET10', 15, 2, '2025-06-21 17:30:00', '2025-06-22 04:30:00', 10, 90, 1500.00, 'economy', 'C', '1'),
(36, 'FLRET11', 16, 3, '2025-06-17 10:45:00', '2025-06-18 04:45:00', 1, 189, 500.00, 'economy', '1', 'A1'),
(37, 'FLRET12', 17, 3, '2025-06-18 12:15:00', '2025-06-18 22:15:00', 2, 180, 1600.00, 'business', '1', 'A6'),
(38, 'FLRET13', 18, 3, '2025-06-19 13:45:00', '2025-06-20 23:45:00', 3, 396, 3200.00, 'first', '2', 'B7'),
(39, 'FLRET14', 19, 3, '2025-06-20 15:00:00', '2025-06-20 17:00:00', 4, 260, 2200.00, 'economy', '2', 'B9'),
(40, 'FLRET15', 20, 3, '2025-06-21 16:30:00', '2025-06-22 03:00:00', 5, 120, 6000.00, 'first', '3', 'C8'),
(41, 'FLRET16', 6, 4, '2025-06-18 12:15:00', '2025-06-18 14:15:00', 6, 296, 300.00, 'economy', 'A', '15'),
(42, 'FLRET17', 9, 4, '2025-06-19 13:45:00', '2025-06-19 15:15:00', 7, 315, 900.00, 'business', 'A', '17'),
(43, 'FLRET18', 11, 4, '2025-06-20 15:00:00', '2025-06-20 17:00:00', 8, 416, 2500.00, 'business', 'B', '10'),
(44, 'FLRET19', 12, 4, '2025-06-21 16:30:00', '2025-06-21 19:30:00', 9, 220, 1800.00, 'economy', 'B', '12'),
(45, 'FLRET20', 19, 4, '2025-06-22 18:00:00', '2025-06-22 20:00:00', 10, 90, 1300.00, 'economy', 'C', '6'),
(46, 'FLRET21', 7, 5, '2025-06-17 12:30:00', '2025-06-17 21:30:00', 1, 189, 2000.00, 'economy', '1', 'A8'),
(47, 'FLRET22', 13, 5, '2025-06-18 14:00:00', '2025-06-19 01:00:00', 2, 180, 5000.00, 'business', '1', 'A10'),
(48, 'FLRET23', 15, 5, '2025-06-19 15:30:00', '2025-06-20 02:30:00', 3, 396, 8500.00, 'first', '2', 'B11'),
(49, 'FLRET24', 16, 5, '2025-06-20 17:00:00', '2025-06-21 13:00:00', 4, 260, 750.00, 'economy', '2', 'B12'),
(50, 'FLRET25', 20, 5, '2025-06-21 18:30:00', '2025-06-22 05:00:00', 5, 120, 1400.00, 'business', '3', 'C9'),
(51, 'PA15', 5, 11, '2025-10-15 20:00:00', '2025-10-15 22:30:00', 1, 189, 1000.00, 'business', 'A', '12'),
(52, 'FLAWAW55', 3, 6, '2025-12-12 14:00:00', '2025-12-12 16:30:00', 1, 189, 300.00, 'business', 'A', '14');

--
-- Wyzwalacze `flights`
--
DELIMITER $$
CREATE TRIGGER `before_insert_flight` BEFORE INSERT ON `flights` FOR EACH ROW BEGIN
  DECLARE plane_capacity INT;
  SELECT capacity INTO plane_capacity FROM Planes WHERE plane_id = NEW.plane_id;
  SET NEW.available_seats = plane_capacity;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `passengers`
--

CREATE TABLE `passengers` (
  `id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `street` varchar(100) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `postal_code` varchar(10) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `document_type` enum('ID_CARD','PASSPORT') NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `planes`
--

CREATE TABLE `planes` (
  `plane_id` int(11) NOT NULL,
  `model` varchar(50) NOT NULL,
  `capacity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `planes`
--

INSERT INTO `planes` (`plane_id`, `model`, `capacity`) VALUES
(1, 'Boeing 737-800', 189),
(2, 'Airbus A320', 180),
(3, 'Boeing 777-300ER', 396),
(4, 'Airbus A330-200', 260),
(5, 'Embraer E195', 120),
(6, 'Boeing 787-9 Dreamliner', 296),
(7, 'Airbus A350-900', 315),
(8, 'Boeing 747-400', 416),
(9, 'Airbus A321', 220),
(10, 'Bombardier CRJ900', 90);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reservations`
--

CREATE TABLE `reservations` (
  `reservation_id` int(11) NOT NULL,
  `passenger_id` int(11) NOT NULL,
  `flight_id` int(11) NOT NULL,
  `reservation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `status` enum('confirmed','cancelled') NOT NULL DEFAULT 'confirmed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('USER','ADMIN') NOT NULL DEFAULT 'USER',
  `balance` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email`, `password`, `role`, `balance`) VALUES
(1, 'admin@example.com', 'admin123', 'ADMIN', 0.00),
(2, 'user@example.com', 'user123', 'USER', 1500.00),
(3, 'konrad@example.com', 'konrad123', 'USER', 250.00);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `airports`
--
ALTER TABLE `airports`
  ADD PRIMARY KEY (`airport_id`),
  ADD UNIQUE KEY `iata_code` (`iata_code`);

--
-- Indeksy dla tabeli `baggage`
--
ALTER TABLE `baggage`
  ADD PRIMARY KEY (`baggage_id`),
  ADD KEY `reservation_id` (`reservation_id`);

--
-- Indeksy dla tabeli `flights`
--
ALTER TABLE `flights`
  ADD PRIMARY KEY (`flight_id`),
  ADD UNIQUE KEY `flight_number` (`flight_number`),
  ADD KEY `departure_airport_id` (`departure_airport_id`),
  ADD KEY `arrival_airport_id` (`arrival_airport_id`),
  ADD KEY `plane_id` (`plane_id`);

--
-- Indeksy dla tabeli `passengers`
--
ALTER TABLE `passengers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- Indeksy dla tabeli `planes`
--
ALTER TABLE `planes`
  ADD PRIMARY KEY (`plane_id`);

--
-- Indeksy dla tabeli `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservation_id`),
  ADD KEY `passenger_id` (`passenger_id`),
  ADD KEY `flight_id` (`flight_id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `airports`
--
ALTER TABLE `airports`
  MODIFY `airport_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `baggage`
--
ALTER TABLE `baggage`
  MODIFY `baggage_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `flights`
--
ALTER TABLE `flights`
  MODIFY `flight_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `passengers`
--
ALTER TABLE `passengers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `planes`
--
ALTER TABLE `planes`
  MODIFY `plane_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `reservation_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `baggage`
--
ALTER TABLE `baggage`
  ADD CONSTRAINT `baggage_ibfk_1` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `flights`
--
ALTER TABLE `flights`
  ADD CONSTRAINT `flights_ibfk_1` FOREIGN KEY (`departure_airport_id`) REFERENCES `airports` (`airport_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `flights_ibfk_2` FOREIGN KEY (`arrival_airport_id`) REFERENCES `airports` (`airport_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `flights_ibfk_3` FOREIGN KEY (`plane_id`) REFERENCES `planes` (`plane_id`) ON UPDATE CASCADE;

--
-- Constraints for table `passengers`
--
ALTER TABLE `passengers`
  ADD CONSTRAINT `fk_passenger_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`passenger_id`) REFERENCES `passengers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`flight_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
