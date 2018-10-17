-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema world_cup_2018
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema world_cup_2018
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `world_cup_2018` DEFAULT CHARACTER SET utf8 ;
USE `world_cup_2018` ;

-- -----------------------------------------------------
-- Table `world_cup_2018`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `world_cup_2018`.`game` (
  `GameId` VARCHAR(50) NOT NULL,
  `group` VARCHAR(50) NULL DEFAULT NULL,
  `matchDate` VARCHAR(100) NULL DEFAULT NULL,
  `SID` VARCHAR(50) NULL DEFAULT NULL,
  `teamID1` VARCHAR(50) NULL DEFAULT NULL,
  `teamID2` VARCHAR(50) NULL DEFAULT NULL,
  `team1Score` VARCHAR(50) NULL DEFAULT NULL,
  `team2Score` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`GameId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `world_cup_2018`.`goal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `world_cup_2018`.`goal` (
  `GameId` VARCHAR(50) NOT NULL,
  `teamID` VARCHAR(50) NOT NULL,
  `Pno` VARCHAR(50) NOT NULL,
  `Gtime` VARCHAR(20) NOT NULL,
  `Penalty` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`GameId`, `teamID`, `Pno`, `Gtime`, `Penalty`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `world_cup_2018`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `world_cup_2018`.`player` (
  `Team` VARCHAR(105) NULL DEFAULT NULL,
  `TeamID` VARCHAR(50) NOT NULL,
  `PNo` INT(11) NOT NULL,
  `Position` VARCHAR(145) NULL DEFAULT NULL,
  `PName` VARCHAR(145) NULL DEFAULT NULL,
  `Birth Date` VARCHAR(145) NULL DEFAULT NULL,
  `Shirt Name` VARCHAR(145) NULL DEFAULT NULL,
  `club` VARCHAR(145) NULL DEFAULT NULL,
  `height` VARCHAR(100) NULL DEFAULT NULL,
  `weight` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`TeamID`, `PNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `world_cup_2018`.`stadium`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `world_cup_2018`.`stadium` (
  `SID` VARCHAR(50) NOT NULL,
  `SName` VARCHAR(100) NULL DEFAULT NULL,
  `SCity` VARCHAR(50) NULL DEFAULT NULL,
  `Scapacity` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`SID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `world_cup_2018`.`startinglineup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `world_cup_2018`.`startinglineup` (
  `GameId` VARCHAR(50) NOT NULL,
  `teamID` VARCHAR(50) NOT NULL,
  `Pno` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`GameId`, `teamID`, `Pno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `world_cup_2018`.`team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `world_cup_2018`.`team` (
  `teamID` VARCHAR(50) NOT NULL,
  `team` VARCHAR(50) NULL DEFAULT NULL,
  `continent` VARCHAR(50) NULL DEFAULT NULL,
  `league` VARCHAR(50) NULL DEFAULT NULL,
  `population` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`teamID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
