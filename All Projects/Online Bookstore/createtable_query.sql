-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bookstore
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bookstore
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bookstore` DEFAULT CHARACTER SET utf8 ;
USE `bookstore` ;

-- -----------------------------------------------------
-- Table `bookstore`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`person` (
  `SSN` VARCHAR(45) NOT NULL,
  `Fname` VARCHAR(45) NOT NULL,
  `Lname` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Sex` VARCHAR(45) NULL DEFAULT NULL,
  `Dob` DATE NULL DEFAULT NULL,
  `Age` INT(11) NULL DEFAULT NULL,
  `Address` VARCHAR(150) NULL DEFAULT NULL,
  `Phone_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`SSN`),
  UNIQUE INDEX `Phone_number_UNIQUE` (`Phone_number` ASC),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`author` (
  `author_id` INT(11) NOT NULL,
  `author_ssn` VARCHAR(45) NOT NULL,
  `affiliation` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`author_id`),
  INDEX `author_ssn_idx` (`author_ssn` ASC),
  CONSTRAINT `author_ssn`
    FOREIGN KEY (`author_ssn`)
    REFERENCES `bookstore`.`person` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`items` (
  `item_id` INT(11) NOT NULL,
  `item_name` VARCHAR(45) NULL DEFAULT NULL,
  `subject_type` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `image_link` VARCHAR(45) NULL DEFAULT NULL,
  `category` VARCHAR(45) NOT NULL,
  `price` INT(10) NOT NULL,
  `quantity` INT(10) NOT NULL,
  PRIMARY KEY (`item_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '				';


-- -----------------------------------------------------
-- Table `bookstore`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`publisher` (
  `publisher_id` INT(11) NOT NULL,
  `publisher_ssn` VARCHAR(45) NOT NULL,
  `publication_name` VARCHAR(45) NULL DEFAULT NULL,
  `location` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`publisher_id`),
  INDEX `publisher_ssn_idx` (`publisher_ssn` ASC),
  CONSTRAINT `publisher_ssn`
    FOREIGN KEY (`publisher_ssn`)
    REFERENCES `bookstore`.`person` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`book` (
  `isbn` INT(11) NOT NULL,
  `book_name` VARCHAR(45) NOT NULL,
  `book_type` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `publish_date` DATE NOT NULL,
  `authors` INT(11) NOT NULL,
  `publisher` INT(11) NOT NULL,
  `availability` INT(11) NOT NULL,
  `item_id` INT(11) NOT NULL,
  PRIMARY KEY (`isbn`),
  INDEX `authors_idx` (`authors` ASC),
  INDEX `publisher_idx` (`publisher` ASC),
  INDEX `item_id_idx` (`item_id` ASC),
  CONSTRAINT `authors`
    FOREIGN KEY (`authors`)
    REFERENCES `bookstore`.`author` (`author_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `book_item_id`
    FOREIGN KEY (`item_id`)
    REFERENCES `bookstore`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `publisher`
    FOREIGN KEY (`publisher`)
    REFERENCES `bookstore`.`publisher` (`publisher_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`customer` (
  `customer_id` INT(11) NOT NULL,
  `customer_ssn` VARCHAR(45) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `created_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC),
  INDEX `customer_ssn` (`customer_ssn` ASC),
  CONSTRAINT `customer_ssn`
    FOREIGN KEY (`customer_ssn`)
    REFERENCES `bookstore`.`person` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`payment` (
  `payment_id` INT(11) NOT NULL,
  `total_price` INT(11) NOT NULL,
  `payment_type` VARCHAR(45) NOT NULL,
  `payment_date` DATE NOT NULL,
  PRIMARY KEY (`payment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`order` (
  `order_id` INT(11) NOT NULL,
  `order_date` DATE NOT NULL,
  `quantity` INT(11) NOT NULL,
  `shipping_address` VARCHAR(45) NOT NULL,
  `tracking_no` VARCHAR(45) NOT NULL,
  `item_id` INT(11) NOT NULL,
  `customer_id` INT(11) NOT NULL,
  `payment_id` INT(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `tracking_no_UNIQUE` (`tracking_no` ASC),
  INDEX `item_id_idx` (`item_id` ASC),
  INDEX `customer_id_idx` (`customer_id` ASC),
  INDEX `payment_id_idx` (`payment_id` ASC),
  CONSTRAINT `customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `bookstore`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `order_item_id`
    FOREIGN KEY (`item_id`)
    REFERENCES `bookstore`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `payment_id`
    FOREIGN KEY (`payment_id`)
    REFERENCES `bookstore`.`payment` (`payment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`periodical`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`periodical` (
  `periodical_id` INT(11) NOT NULL,
  `periodical_name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `publisher_id` INT(11) NOT NULL,
  `publish_date` DATE NOT NULL,
  `item_id` INT(11) NOT NULL,
  PRIMARY KEY (`periodical_id`),
  INDEX `publisher_id_idx` (`publisher_id` ASC),
  INDEX `item_id_idx` (`item_id` ASC),
  CONSTRAINT `periodical_item_id`
    FOREIGN KEY (`item_id`)
    REFERENCES `bookstore`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `publisher_id`
    FOREIGN KEY (`publisher_id`)
    REFERENCES `bookstore`.`publisher` (`publisher_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`seller`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`seller` (
  `seller_id` INT(11) NOT NULL,
  `owner_ssn` VARCHAR(45) NOT NULL,
  `seller_name` VARCHAR(45) NOT NULL,
  `seller_address` VARCHAR(45) NULL DEFAULT NULL,
  `seller_contact` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`seller_id`),
  UNIQUE INDEX `seller_contact_UNIQUE` (`seller_contact` ASC),
  INDEX `owner_ssn_idx` (`owner_ssn` ASC),
  CONSTRAINT `owner_ssn`
    FOREIGN KEY (`owner_ssn`)
    REFERENCES `bookstore`.`person` (`SSN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `bookstore`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`staff` (
  `staff_id` INT(11) NOT NULL,
  `staff_ssn` VARCHAR(45) NOT NULL,
  `salary` VARCHAR(45) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `join_date` DATE NOT NULL,
  `job_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`staff_id`),
  UNIQUE INDEX `staff_ssn_UNIQUE` (`staff_ssn` ASC),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
