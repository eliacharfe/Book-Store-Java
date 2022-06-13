-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: יוני 13, 2022 בזמן 11:51 AM
-- גרסת שרת: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ex4`
--

-- --------------------------------------------------------

--
-- מבנה טבלה עבור טבלה `book`
--

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL,
  `discount` double NOT NULL,
  `image_src` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- הוצאת מידע עבור טבלה `book`
--

INSERT INTO `book` (`id`, `discount`, `image_src`, `name`, `price`, `quantity`) VALUES
(1, 15, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/8532/9781853260858.jpg', 'Les Miserables', 72, 30),
(7, 8, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0999/9780099981909.jpg', 'Crime and punishement', 81, 3),
(8, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0072/9780007270613.jpg', 'The Hobbit', 79.99, 29),
(52, 12, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0620/9780062073501.jpg', 'Murder in the Orient Express', 63, 31),
(53, 10, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/5903/9780590353427.jpg', 'Harry Potter', 70, 42),
(54, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/7967/9781796703405.jpg', 'Angles and Demonds', 64, 27),
(75, 9, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/0887/9781088797242.jpg', 'Introduction to Java Spring Boot', 83, 23),
(93, 10, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/8532/9781853260124.jpg', 'Oliver Twist', 47, 54),
(94, 10, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/1413/9780141332499.jpg', 'The Great Adventures of Sherlock Holmes', 59, 24),
(95, 7, '/default_book_cover_2015.jpg', 'No name', 79.99, 10),
(96, 8, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/8532/9781853261589.jpg', 'The Little Prince', 64, 19),
(99, 7, '/default_book_cover_2015.jpg', 'No name', 79.99, 10),
(100, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9782/0358/9782035834201.jpg', 'Le Malade Imaginaire', 74.99, 10);

-- --------------------------------------------------------

--
-- מבנה טבלה עבור טבלה `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- הוצאת מידע עבור טבלה `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(101),
(1);

-- --------------------------------------------------------

--
-- מבנה טבלה עבור טבלה `purchase`
--

CREATE TABLE `purchase` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `date_time` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- הוצאת מידע עבור טבלה `purchase`
--

INSERT INTO `purchase` (`id`, `amount`, `date_time`) VALUES
(15, 62.1, '2022-05-31 17:25:23.000000'),
(16, 58.96, '2022-05-31 17:25:26.000000'),
(17, 73.5908, '2022-05-31 17:26:02.000000'),
(19, 62.1, '2022-05-31 17:36:56.000000'),
(20, 62.1, '2022-05-31 17:37:00.000000'),
(21, 62.1, '2022-05-31 17:37:14.000000'),
(22, 62.1, '2022-05-31 17:38:30.000000'),
(23, 62.1, '2022-05-31 17:38:32.000000'),
(24, 62.1, '2022-05-31 17:38:34.000000'),
(25, 62.1, '2022-05-31 17:41:36.000000'),
(26, 62.1, '2022-05-31 17:41:37.000000'),
(27, 62.1, '2022-05-31 17:41:39.000000'),
(29, 62.1, '2022-05-31 17:45:45.000000'),
(30, 62.1, '2022-05-31 17:45:47.000000'),
(31, 62.1, '2022-05-31 17:45:49.000000'),
(33, 62.1, '2022-05-31 17:51:17.000000'),
(34, 62.1, '2022-05-31 17:51:20.000000'),
(35, 62.1, '2022-05-31 17:51:22.000000'),
(36, 62.1, '2022-05-31 17:52:38.000000'),
(37, 62.1, '2022-05-31 17:54:19.000000'),
(38, 62.1, '2022-05-31 17:54:21.000000'),
(40, 62.1, '2022-05-31 17:56:46.000000'),
(41, 62.1, '2022-05-31 17:56:48.000000'),
(42, 62.1, '2022-05-31 17:56:50.000000'),
(44, 73.71, '2022-05-31 18:36:24.000000'),
(45, 73.5908, '2022-05-31 18:36:51.000000'),
(46, 73.5908, '2022-05-31 18:39:20.000000'),
(48, 73.5908, '2022-05-31 18:47:33.000000'),
(49, 58.96, '2022-05-31 18:56:28.000000'),
(51, 73.71, '2022-05-31 19:13:46.000000'),
(55, 64, '2022-05-31 19:48:32.000000'),
(56, 72, '2022-05-31 19:50:12.000000'),
(57, 118.71000000000001, '2022-05-31 19:53:33.000000'),
(58, 237.42000000000002, '2022-05-31 19:59:07.000000'),
(59, 237.42000000000002, '2022-05-31 20:04:41.000000'),
(60, 237.42000000000002, '2022-05-31 20:06:02.000000'),
(61, 125.28, '2022-05-31 20:09:39.000000'),
(62, 125.28, '2022-05-31 20:15:45.000000'),
(63, 56.07, '2022-05-31 20:16:05.000000'),
(65, 237.42000000000002, '2022-05-31 20:17:58.000000'),
(66, 125.28, '2022-05-31 20:20:38.000000'),
(67, 237.42000000000002, '2022-05-31 20:22:42.000000'),
(68, 326.53999999999996, '2022-05-31 20:24:24.000000'),
(69, 58.24, '2022-05-31 20:26:27.000000'),
(70, 300.06, '2022-05-31 20:26:57.000000'),
(71, 412.14, '2022-05-31 20:48:29.000000'),
(72, 221.13, '2022-05-31 20:53:02.000000'),
(73, 221.13, '2022-05-31 20:58:21.000000'),
(74, 233.38, '2022-05-31 21:39:16.000000'),
(76, 172.55, '2022-06-01 16:10:21.000000'),
(77, 237.42000000000002, '2022-06-01 16:11:54.000000'),
(78, 63, '2022-06-01 16:12:12.000000'),
(79, 237.42000000000002, '2022-06-01 16:19:34.000000'),
(80, 315, '2022-06-01 16:19:50.000000'),
(82, 168.21, '2022-06-01 19:50:28.000000'),
(84, 179.12, '2022-06-01 19:54:41.000000'),
(85, 176.95, '2022-06-01 19:56:52.000000'),
(89, 56.07, '2022-06-06 09:43:26.000000'),
(90, 176.95000000000002, '2022-06-06 09:43:34.000000'),
(97, 221.13, '2022-06-06 12:31:38.000000'),
(98, 293.4, '2022-06-06 17:28:21.000000');

--
-- Indexes for dumped tables
--

--
-- אינדקסים לטבלה `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);

--
-- אינדקסים לטבלה `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
