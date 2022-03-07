-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 02 mars 2022 à 12:27
-- Version du serveur :  10.4.17-MariaDB
-- Version de PHP : 7.4.15

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
-- Structure de la table `offer`
--

CREATE TABLE `offer` (
  `id` int(11) NOT NULL,
  `podcaster_id` int(11) NOT NULL,
  `description` varchar(250) NOT NULL,
  `price` float NOT NULL,
  `image` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `offer`
--

INSERT INTO `offer` (`id`, `podcaster_id`, `description`, `price`, `image`) VALUES
(18, 10, 'zafezae', 30, 'test.png'),
(24, 125215, 'faeefz', 25, 'test.png'),
(25, 10, 'khaled', 30, 'test2.png'),
(30, 10, 'gaezezagezage', 20, 'test.png'),
(31, 10, 'zfeqzefezq', 20, 'test.png'),
(32, 10, 'efeqzefqze', 10, 'test1.png'),
(33, 10, 'gsergrs', 8, 'test.png'),
(34, 10, 'vdvqdqd1', 19, 'test2.png'),
(35, 10, 'vezqe', 17, 'test.png'),
(36, 10, 'THIS IS A COOL OFFER', 69, 'test1.png');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `offer`
--
ALTER TABLE `offer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
