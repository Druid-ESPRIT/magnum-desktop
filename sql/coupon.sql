-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 02 mars 2022 à 12:28
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
-- Structure de la table `coupon`
--

CREATE TABLE `coupon` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `code` varchar(24) NOT NULL,
  `reduction` int(11) NOT NULL,
  `used` varchar(10) NOT NULL,
  `created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `coupon`
--

INSERT INTO `coupon` (`id`, `user_id`, `code`, `reduction`, `used`, `created`) VALUES
(3, 1, 'h9NiOBXoXNFu', 10, 'true', '2022-02-27'),
(4, 1, '36JIcGskEE6M', 10, 'true', '2022-02-27'),
(5, 1, 'KsYu6zbnUxRo', 20, 'true', '2022-02-27'),
(19, 1, 'fup2ucBN13r7', 10, 'true', '2022-02-27'),
(20, 1, 'ygNnyBCm5EwO', 10, 'true', '2022-02-27'),
(21, 1, 'xFMGZDxl9yhD', 20, 'true', '2022-02-27'),
(22, 1, 'TTyULH7j2UXP', 10, 'false', '2022-02-27'),
(23, 1, 'B8gPJE4KSXPs', 10, 'false', '2022-02-28'),
(24, 1, 'VwePi1PfcYKd', 10, 'false', '2022-02-28'),
(25, 1, 'XPWU2Yfp1Qew', 10, 'false', '2022-03-02');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `coupon`
--
ALTER TABLE `coupon`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `coupon`
--
ALTER TABLE `coupon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
