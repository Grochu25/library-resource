-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 26 Mar 2025, 19:54
-- Wersja serwera: 10.4.8-MariaDB
-- Wersja PHP: 7.2.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `library_data`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `authors`
--

CREATE TABLE `authors` (
  `id` bigint(20) NOT NULL,
  `name` varchar(35) NOT NULL,
  `birth_year` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `authors`
--

INSERT INTO `authors` (`id`, `name`, `birth_year`, `description`) VALUES
(1, 'Adam Mickiewicz', 1798, 'wieszcz narodowy'),
(2, 'Henryk Sienkiewicz', NULL, NULL),
(3, 'George Orwell', 1903, ''),
(5, 'Juliusz Słowacki', 1809, '');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `books`
--

CREATE TABLE `books` (
  `id` bigint(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `author_id` bigint(20) NOT NULL,
  `publish_year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `books`
--

INSERT INTO `books` (`id`, `title`, `author_id`, `publish_year`) VALUES
(1, 'Pan Tadeusz', 1, 1834),
(2, 'Krzyżacy', 2, 1899),
(3, 'Dziady cz. II', 1, 1823),
(4, 'Rok 1984', 3, 1949),
(5, 'Kordian', 5, 1834);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `borrows`
--

CREATE TABLE `borrows` (
  `id` bigint(20) NOT NULL,
  `copy` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `since` date NOT NULL,
  `until` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `borrows`
--

INSERT INTO `borrows` (`id`, `copy`, `user_id`, `since`, `until`) VALUES
(1, 123123, 1, '2025-02-02', '2025-03-13'),
(2, 321321, 2, '2025-01-14', '2025-03-13'),
(3, 321321, 1, '2025-01-12', '2025-01-29'),
(4, 321329, 1, '2025-03-13', '2025-03-15'),
(5, 321325, 2, '2025-03-13', '2025-03-15'),
(6, 321324, 1, '2025-03-15', '2025-03-15'),
(7, 321327, 6, '2025-03-15', '2025-03-15'),
(8, 222222, 2, '2025-03-15', '2025-03-15'),
(9, 321327, 2, '2025-03-15', '2025-03-15'),
(10, 321329, 2, '2025-03-15', '2025-03-15');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `copies`
--

CREATE TABLE `copies` (
  `id` bigint(20) NOT NULL,
  `book` bigint(20) NOT NULL,
  `destroyed` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `copies`
--

INSERT INTO `copies` (`id`, `book`, `destroyed`) VALUES
(123123, 1, 0),
(123456, 2, 0),
(222222, 3, 0),
(321321, 1, 0),
(321324, 4, 0),
(321325, 4, 0),
(321326, 3, 0),
(321327, 3, 0),
(321328, 4, 0),
(321329, 2, 0),
(333333, 2, 0),
(333334, 5, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(35) NOT NULL,
  `surname` varchar(35) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(12) NOT NULL,
  `address_street` varchar(50) NOT NULL,
  `address_city` varchar(50) NOT NULL,
  `address_state` varchar(4) NOT NULL,
  `address_zip` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `email`, `phone_number`, `address_street`, `address_city`, `address_state`, `address_zip`) VALUES
(1, 'Stefan', 'Jarosz', 'stefan@gmail.com', '123456789', 'Wiejska 17', 'Warszawa', 'MAZO', '10-100'),
(2, 'Agnieszka', 'Kowal', 'agnieszka@gmail.com', '123456789', 'Wiejska 17', 'Warszawa', 'MAZO', '10-100'),
(6, 'Adam', 'Marek', 'adam@gmail.com', '', 'Kolonialna 7', 'Warszawa', 'Mazo', '00-123');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `authors`
--
ALTER TABLE `authors`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_BookAuth` (`author_id`);

--
-- Indeksy dla tabeli `borrows`
--
ALTER TABLE `borrows`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_CustomerBor` (`user_id`),
  ADD KEY `FK_CopyBor` (`copy`) USING BTREE;

--
-- Indeksy dla tabeli `copies`
--
ALTER TABLE `copies`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_BookCopy` (`book`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `authors`
--
ALTER TABLE `authors`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `books`
--
ALTER TABLE `books`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT dla tabeli `borrows`
--
ALTER TABLE `borrows`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `copies`
--
ALTER TABLE `copies`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=333335;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `FK_BookAuth` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`);

--
-- Ograniczenia dla tabeli `borrows`
--
ALTER TABLE `borrows`
  ADD CONSTRAINT `FK_CopyBor` FOREIGN KEY (`copy`) REFERENCES `copies` (`id`),
  ADD CONSTRAINT `FK_CustomerBor` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Ograniczenia dla tabeli `copies`
--
ALTER TABLE `copies`
  ADD CONSTRAINT `FK_BookCopy` FOREIGN KEY (`book`) REFERENCES `books` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
