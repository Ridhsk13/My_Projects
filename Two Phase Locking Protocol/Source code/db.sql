-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema 2pl
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema 2pl
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `2pl` DEFAULT CHARACTER SET utf8 ;
USE `2pl` ;

-- -----------------------------------------------------
-- Table `2pl`.`locktable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2pl`.`locktable` (
  `dataItem` VARCHAR(10) NOT NULL,
  `State` VARCHAR(45) NOT NULL,
  `tranNumHolding` VARCHAR(100) NOT NULL,
  `tranNumWaiting` VARCHAR(100) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `2pl`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2pl`.`transaction` (
  `tranNum` VARCHAR(10) NOT NULL,
  `timestamp` INT(10) NOT NULL,
  `state` VARCHAR(45) NULL DEFAULT NULL,
  `listOfItemsLocked` VARCHAR(100) NULL DEFAULT NULL,
  `operationsInWaiting` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`tranNum`),
  UNIQUE INDEX `timestamp_UNIQUE` (`timestamp` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
