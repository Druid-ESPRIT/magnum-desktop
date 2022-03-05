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
-- Structure de la table `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `plan` int(11) NOT NULL,
  `total` float NOT NULL,
  `orderdate` varchar(30) NOT NULL,
  `status` enum('Pending','Completed','Canceled','Refunded') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `order`
--

INSERT INTO `order` (`id`, `offer_id`, `plan`, `total`, `orderdate`, `status`) VALUES
(47, 18, 2, 80, '2022-02-27 10:48:25.847', 'Completed'),
(48, 18, 2, 80, '2022-02-27 10:48:28.653', 'Canceled'),
(49, 18, 2, 80, '2022-02-27 10:53:33.603', 'Completed'),
(52, 18, 2, 80, '2022-02-27 12:13:33.991', 'Canceled'),
(53, 18, 4, 128, '2022-02-27 12:18:38.812', 'Completed'),
(54, 18, 4, 128, '2022-02-27 12:21:40.178', 'Completed'),
(55, 18, 4, 128, '2022-02-27 12:22:19.116', 'Completed'),
(56, 18, 4, 128, '2022-02-27 12:28:48.559', 'Completed'),
(57, 18, 4, 128, '2022-02-27 12:28:58.844', 'Completed'),
(58, 18, 4, 128, '2022-02-27 12:29:51.748', 'Completed'),
(59, 18, 4, 144, '2022-02-27 12:39:44.132', 'Pending'),
(60, 18, 5, 90, '2022-02-27 14:46:06.948', 'Pending'),
(61, 18, 6, 108, '2022-02-27 14:47:17.358', 'Pending'),
(62, 18, 4, 72, '2022-02-27 14:53:11.079', 'Pending'),
(63, 18, 9, 144, '2022-02-27 14:56:12.084', 'Pending'),
(64, 18, 11, 220, '2022-02-27 14:58:28.715', 'Pending'),
(65, 18, 6, 120, '2022-02-27 19:58:22.064', 'Pending'),
(66, 18, 8, 144, '2022-02-27 19:58:22.064', 'Pending'),
(67, 18, 8, 144, '2022-02-28 10:10:15.903', 'Pending'),
(68, 18, 5, 100, '2022-02-28 23:11:07.566', 'Pending'),
(69, 18, 4, 80, '2022-03-02 00:32:17.067', 'Pending');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_offer_order` (`offer_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_offer_order` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
