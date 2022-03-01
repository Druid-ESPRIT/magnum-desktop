-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 01, 2022 at 09:11 PM
-- Server version: 8.0.28-0ubuntu0.20.04.3
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `magnum`
--

-- --------------------------------------------------------

--
-- Table structure for table `Podcasters`
--

CREATE TABLE `Podcasters` (
  `ID` int NOT NULL,
  `firstName` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'This attribute can be used as the name of the podcast and not necessarily that of the account holder.',
  `lastName` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'This attribute can be used as the name of the podcast and not necessarily that of the account holder.',
  `biography` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'A short and sweet paragraph that tells users a little bit about the podcaster.',
  `avatar` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'The name and extension of the image file that represents the avatar of the user, e.g. "grtcdr.png"'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Podcasters`
--
ALTER TABLE `Podcasters`
  ADD PRIMARY KEY (`ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Podcasters`
--
ALTER TABLE `Podcasters`
  ADD CONSTRAINT `fk_userID_pod` FOREIGN KEY (`ID`) REFERENCES `Users` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
