-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : Dim 06 mars 2022 à 22:27
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
(40, 3, 'G6TXOGSRN8PI', 10, 'true', '2022-03-06'),
(41, 3, '7TAQK6DcQIbM', 20, 'true', '2022-03-06'),
(42, 1, 'C8s3JaBxEVeC', 10, 'false', '2022-03-06'),
(43, 1, 'pCVq702pXAr5', 10, 'false', '2022-03-06'),
(44, 1, 'gpi7cojvjwFt', 10, 'false', '2022-03-06'),
(45, 2, 'A2IkiI3jhugq', 20, 'false', '2022-03-06'),
(46, 2, 'UqREQHLYg6a0', 10, 'false', '2022-03-06'),
(47, 1, 'HkWNqusNoSiL', 10, 'false', '2022-03-06');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
