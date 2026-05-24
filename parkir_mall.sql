-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 24, 2026 at 09:40 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parkir_mall`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`) VALUES
(1, 'admin', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `kendaraan`
--

CREATE TABLE `kendaraan` (
  `id_kendaraan` int(11) NOT NULL,
  `plat_nomor` varchar(20) NOT NULL,
  `jenis_kendaraan` enum('Mobil','Motor') NOT NULL,
  `pemilik` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kendaraan`
--

INSERT INTO `kendaraan` (`id_kendaraan`, `plat_nomor`, `jenis_kendaraan`, `pemilik`) VALUES
(1, 'AB 1234 CD', 'Mobil', 'Budi'),
(3, 'B9123XYZ', 'Mobil', 'Rina'),
(4, 'AD7788GH', 'Motor', 'Sinta'),
(5, 'L3344MN', 'Mobil', 'Dewa'),
(7, '', 'Motor', NULL),
(10, 'ANU', 'Mobil', NULL),
(12, 'M 081 L', 'Mobil', NULL),
(13, 'F 1233 FRE', 'Motor', NULL),
(14, 'ED 1 T', 'Motor', NULL),
(15, 'ED 1 TS', 'Motor', NULL),
(16, 'SOPO EDI', 'Mobil', NULL),
(17, 'EDI SOPO', 'Motor', NULL),
(18, 'R 457 A', 'Mobil', NULL),
(19, 'SOPO EDI YO', 'Motor', NULL),
(20, 'SOPO EDI SIH', 'Motor', NULL),
(22, 'LUKMAN', 'Mobil', NULL),
(23, 'ADA', 'Motor', NULL),
(24, 'AA', 'Motor', NULL),
(25, 'T 4812 I', 'Motor', NULL),
(27, '93', 'Mobil', NULL),
(30, 'F 9812 AR', 'Motor', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `parkir`
--

CREATE TABLE `parkir` (
  `id_parkir` int(11) NOT NULL,
  `id_kendaraan` int(11) NOT NULL,
  `id_slot` int(11) NOT NULL,
  `waktu_masuk` datetime NOT NULL,
  `waktu_keluar` datetime DEFAULT NULL,
  `durasi_jam` int(11) DEFAULT 0,
  `biaya` double DEFAULT 0,
  `status_parkir` enum('Parkir','Keluar') DEFAULT 'Parkir'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parkir`
--

INSERT INTO `parkir` (`id_parkir`, `id_kendaraan`, `id_slot`, `waktu_masuk`, `waktu_keluar`, `durasi_jam`, `biaya`, `status_parkir`) VALUES
(1, 1, 1, '2026-05-22 23:12:38', NULL, 0, 0, 'Parkir'),
(8, 12, 7, '2026-05-24 13:34:06', NULL, 0, 0, 'Parkir'),
(9, 13, 15, '2026-05-24 13:37:14', NULL, 0, 0, 'Parkir'),
(10, 14, 17, '2026-05-24 15:00:45', '2026-05-24 18:44:31', 4, 5000, 'Keluar'),
(11, 16, 16, '2026-05-24 15:17:41', NULL, 0, 0, 'Parkir'),
(12, 18, 12, '2026-05-24 15:23:46', NULL, 0, 0, 'Parkir'),
(14, 22, 13, '2026-05-24 17:33:39', NULL, 0, 0, 'Parkir'),
(15, 10, 11, '2026-05-24 17:38:04', NULL, 0, 0, 'Parkir'),
(16, 23, 17, '2026-05-24 18:43:37', '2026-05-24 18:43:57', 1, 2000, 'Keluar'),
(17, 24, 16, '2026-05-24 21:56:04', '2026-05-24 21:56:16', 1, 2000, 'Keluar'),
(18, 25, 9, '2026-05-24 22:42:57', NULL, 0, 0, 'Parkir'),
(20, 27, 4, '2026-05-25 00:45:39', '2026-05-25 00:46:06', 1, 3000, 'Keluar'),
(23, 30, 4, '2026-05-25 02:39:21', NULL, 0, 0, 'Parkir');

-- --------------------------------------------------------

--
-- Table structure for table `slot_parkir`
--

CREATE TABLE `slot_parkir` (
  `id_slot` int(11) NOT NULL,
  `nomor_slot` varchar(10) NOT NULL,
  `status_slot` enum('Kosong','Terisi') DEFAULT 'Kosong'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `slot_parkir`
--

INSERT INTO `slot_parkir` (`id_slot`, `nomor_slot`, `status_slot`) VALUES
(1, 'A1', 'Terisi'),
(2, 'A2', 'Kosong'),
(3, 'A3', 'Kosong'),
(4, 'A4', 'Terisi'),
(5, 'A5', 'Kosong'),
(6, 'A6', 'Kosong'),
(7, 'A7', 'Terisi'),
(8, 'A8', 'Kosong'),
(9, 'A9', 'Terisi'),
(10, 'A10', 'Kosong'),
(11, 'B1', 'Kosong'),
(12, 'B2', 'Terisi'),
(13, 'B3', 'Kosong'),
(14, 'B4', 'Kosong'),
(15, 'B5', 'Terisi'),
(16, 'B6', 'Kosong'),
(17, 'B7', 'Kosong'),
(18, 'B8', 'Kosong'),
(19, 'B9', 'Kosong'),
(20, 'B10', 'Kosong');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_parkir` int(11) NOT NULL,
  `total_bayar` double NOT NULL,
  `metode_pembayaran` enum('Cash','QRIS','Debit') DEFAULT NULL,
  `tanggal_transaksi` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_parkir`, `total_bayar`, `metode_pembayaran`, `tanggal_transaksi`) VALUES
(1, 1, 5000, 'Cash', '2026-05-22 23:12:38');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `kendaraan`
--
ALTER TABLE `kendaraan`
  ADD PRIMARY KEY (`id_kendaraan`),
  ADD KEY `plat_nomor` (`plat_nomor`) USING BTREE;

--
-- Indexes for table `parkir`
--
ALTER TABLE `parkir`
  ADD PRIMARY KEY (`id_parkir`),
  ADD KEY `fk_kendaraan` (`id_kendaraan`),
  ADD KEY `fk_slot` (`id_slot`);

--
-- Indexes for table `slot_parkir`
--
ALTER TABLE `slot_parkir`
  ADD PRIMARY KEY (`id_slot`),
  ADD UNIQUE KEY `nomor_slot` (`nomor_slot`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `fk_parkir` (`id_parkir`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `kendaraan`
--
ALTER TABLE `kendaraan`
  MODIFY `id_kendaraan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `parkir`
--
ALTER TABLE `parkir`
  MODIFY `id_parkir` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `slot_parkir`
--
ALTER TABLE `slot_parkir`
  MODIFY `id_slot` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `parkir`
--
ALTER TABLE `parkir`
  ADD CONSTRAINT `fk_kendaraan` FOREIGN KEY (`id_kendaraan`) REFERENCES `kendaraan` (`id_kendaraan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_slot` FOREIGN KEY (`id_slot`) REFERENCES `slot_parkir` (`id_slot`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_parkir` FOREIGN KEY (`id_parkir`) REFERENCES `parkir` (`id_parkir`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
