-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : Dim 06 mars 2022 à 22:26
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
  `user_id` int(11) NOT NULL,
  `plan` int(11) NOT NULL,
  `total` float NOT NULL,
  `orderdate` varchar(30) NOT NULL,
  `status` enum('Pending','Completed','Canceled','Refunded') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `order`
--

INSERT INTO `order` (`id`, `offer_id`, `user_id`, `plan`, `total`, `orderdate`, `status`) VALUES
(139, 96, 3, 8, 72, '2022-03-06 05:08:18.671', 'Completed'),
(140, 96, 1, 4, 32, '2022-03-06 05:09:37.375', 'Completed'),
(141, 96, 1, 3, 30, '2022-03-06 05:37:31.928', 'Completed'),
(142, 96, 1, 5, 50, '2022-03-06 05:48:23.265', 'Completed'),
(143, 96, 2, 7, 70, '2022-03-06 05:50:11.737', 'Completed'),
(144, 96, 2, 3, 30, '2022-03-06 05:53:51.355', 'Completed'),
(145, 96, 1, 4, 40, '2022-03-06 15:18:50.51', 'Completed'),
(146, 96, 1, 2, 20, '2022-03-06 15:40:58.106', 'Completed'),
(149, 96, 1, 2, 20, '2022-03-06 17:00:22.138', 'Pending');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_offer_order` (`offer_id`),
  ADD KEY `fk_order_user` (`user_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=150;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_offer_order` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
