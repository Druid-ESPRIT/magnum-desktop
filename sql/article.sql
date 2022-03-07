-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 06 mars 2022 à 23:17
-- Version du serveur : 10.4.22-MariaDB
-- Version de PHP : 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `magnum`
--

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

CREATE TABLE `article` (
  `id` int(10) NOT NULL,
  `authorID` int(9) NOT NULL,
  `title` varchar(50) NOT NULL,
  `url` longtext NOT NULL,
  `content` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id`, `authorID`, `title`, `url`, `content`) VALUES
(34, 3, 'art1', 'https://th.bing.com/th/id/OIP.5QUS7s6PIA-H7-u-PfHeMQHaK2?w=203&h=298&c=7&r=0&o=5&pid=1.7', 'http://www.africau.edu/images/default/sample.pdf'),
(62, 3, 'technologie', 'https://th.bing.com/th/id/OIP.5QUS7s6PIA-H7-u-PfHeMQHaK2?w=203&h=298&c=7&r=0&o=5&pid=1.7', 'http://www.africau.edu/images/default/sample.pdf'),
(63, 3, 'technologie', 'https://th.bing.com/th/id/OIP.5QUS7s6PIA-H7-u-PfHeMQHaK2?w=203&h=298&c=7&r=0&o=5&pid=1.7', 'http://www.africau.edu/images/default/sample.pdf'),
(68, 4, 'science', 'https://th.bing.com/th/id/OIP.5QUS7s6PIA-H7-u-PfHeMQHaK2?w=203&h=298&c=7&r=0&o=5&pid=1.7', 'http://www.africau.edu/images/default/sample.pdf');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD KEY `authorID` (`authorID`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `article`
--
ALTER TABLE `article`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
