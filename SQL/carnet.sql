-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2016-06-12 16:31:46
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `carnet`
--

-- --------------------------------------------------------

--
-- 表的结构 `carinfo`
--

CREATE TABLE IF NOT EXISTS `carinfo` (
  `carid` varchar(15) NOT NULL,
  `userid` varchar(11) NOT NULL,
  `carbrand` varchar(10) DEFAULT NULL,
  `carlogo` varchar(10) DEFAULT NULL,
  `carlevel` varchar(5) DEFAULT NULL,
  `cartype` varchar(10) DEFAULT NULL,
  `motortype` varchar(10) DEFAULT NULL,
  `mileage` varchar(11) DEFAULT NULL,
  `fueleconomy` varchar(11) DEFAULT NULL,
  `motorperform` varchar(1) DEFAULT NULL,
  `transperform` varchar(1) DEFAULT NULL,
  `heanligthperform` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`carid`),
  KEY `FK_Reference_1` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `oilinfo`
--

CREATE TABLE IF NOT EXISTS `oilinfo` (
  `oilinfoid` int(11) NOT NULL AUTO_INCREMENT,
  `carid` varchar(15) DEFAULT NULL,
  `oiltime` datetime NOT NULL,
  `oiltype` varchar(5) NOT NULL,
  `oilcount` int(11) NOT NULL,
  `oilstation` varchar(20) NOT NULL,
  PRIMARY KEY (`oilinfoid`),
  KEY `FK_Reference_2` (`carid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=61 ;

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `userid` varchar(11) NOT NULL,
  `usertel` varchar(11) NOT NULL,
  `userpwd` varchar(20) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `userfeedback`
--

CREATE TABLE IF NOT EXISTS `userfeedback` (
  `feedbackid` int(10) NOT NULL AUTO_INCREMENT,
  `userid` varchar(11) DEFAULT NULL,
  `feedbacktheme` varchar(30) DEFAULT NULL,
  `feedbackinfo` varchar(100) DEFAULT NULL,
  `feedbacktel` varchar(11) DEFAULT NULL,
  `isreply` varchar(1) DEFAULT '0',
  PRIMARY KEY (`feedbackid`),
  KEY `FK_Reference_4` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- 表的结构 `userstyleinfo`
--

CREATE TABLE IF NOT EXISTS `userstyleinfo` (
  `userid` varchar(11) NOT NULL,
  `usersex` varchar(1) DEFAULT NULL,
  `usernick` varchar(10) DEFAULT NULL,
  `usercity` varchar(10) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `userpay` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 限制导出的表
--

--
-- 限制表 `carinfo`
--
ALTER TABLE `carinfo`
  ADD CONSTRAINT `FK_Reference_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);

--
-- 限制表 `oilinfo`
--
ALTER TABLE `oilinfo`
  ADD CONSTRAINT `FK_Reference_2` FOREIGN KEY (`carid`) REFERENCES `carinfo` (`carid`);

--
-- 限制表 `userfeedback`
--
ALTER TABLE `userfeedback`
  ADD CONSTRAINT `FK_Reference_4` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);

--
-- 限制表 `userstyleinfo`
--
ALTER TABLE `userstyleinfo`
  ADD CONSTRAINT `FK_Reference_3` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
