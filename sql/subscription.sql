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
-- Structure de la table `subscription`
--

CREATE TABLE `subscription` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `expire_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `subscription`
--

INSERT INTO `subscription` (`id`, `order_id`, `user_id`, `start_date`, `expire_date`) VALUES
(70, 47, 3, '2022-02-27', '2022-04-28'),
(71, 47, 3, '2022-02-27', '2022-04-28'),
(72, 47, 3, '2022-02-27', '2022-04-28'),
(73, 47, 3, '2022-02-27', '2022-04-28'),
(74, 47, 3, '2022-02-27', '2022-04-28'),
(75, 47, 3, '2022-02-27', '2022-04-28'),
(76, 47, 3, '2022-02-27', '2022-04-28'),
(77, 47, 3, '2022-02-27', '2022-04-28'),
(78, 47, 3, '2022-02-27', '2022-04-28'),
(79, 47, 3, '2022-02-27', '2022-04-28'),
(80, 47, 3, '2022-02-27', '2022-04-28'),
(81, 47, 3, '2022-02-27', '2022-04-28'),
(82, 47, 3, '2022-02-27', '2022-04-28'),
(83, 47, 3, '2022-02-27', '2022-04-28'),
(84, 47, 3, '2022-02-27', '2022-04-28'),
(85, 47, 3, '2022-02-27', '2022-04-28'),
(86, 47, 3, '2022-02-27', '2022-04-28'),
(87, 47, 3, '2022-02-27', '2022-04-28'),
(88, 47, 3, '2022-02-27', '2022-04-28'),
(89, 47, 3, '2022-02-27', '2022-04-28'),
(90, 47, 3, '2022-02-27', '2022-04-28'),
(91, 47, 3, '2022-02-27', '2022-04-28'),
(92, 47, 3, '2022-02-27', '2022-04-28'),
(93, 47, 3, '2022-02-27', '2022-04-28'),
(94, 47, 3, '2022-02-27', '2022-04-28'),
(95, 47, 3, '2022-02-27', '2022-04-28');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `subscription`
--
ALTER TABLE `subscription`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order_subscription` (`order_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `subscription`
--
ALTER TABLE `subscription`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `subscription`
--
ALTER TABLE `subscription`
  ADD CONSTRAINT `fk_order_subscription` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
