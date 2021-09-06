-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema delivery
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema delivery
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `delivery` DEFAULT CHARACTER SET utf8 ;
USE `delivery` ;

-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role` ;

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `person` ;

CREATE TABLE IF NOT EXISTS `person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `patronomic` VARCHAR(45) NULL,
  `title` VARCHAR(45) NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `fk_person_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_person_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `locality`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `locality` ;

CREATE TABLE IF NOT EXISTS `locality` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shipping_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shipping_status` ;

CREATE TABLE IF NOT EXISTS `shipping_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shipping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shipping` ;

CREATE TABLE IF NOT EXISTS `shipping` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `person_id` INT NOT NULL,
  `download_datetime` DATETIME NOT NULL,
  `load_locality_id` INT NOT NULL,
  `shipper` VARCHAR(45) NOT NULL,
  `download_address` VARCHAR(45) NOT NULL,
  `cosignee` VARCHAR(45) NOT NULL,
  `unload_locality_id` INT NOT NULL,
  `unload_address` VARCHAR(45) NOT NULL,
  `unloading_datetime` DATETIME NULL,
  `distance` DOUBLE NOT NULL DEFAULT 0,
  `weight` DOUBLE NOT NULL DEFAULT 0,
  `volume` DOUBLE NOT NULL DEFAULT 0,
  `fare` DECIMAL(8,2) NOT NULL DEFAULT 0,
  `shipping_status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_shipping_person1_idx` (`person_id` ASC) VISIBLE,
  INDEX `fk_shipping_locality1_idx` (`load_locality_id` ASC) VISIBLE,
  INDEX `fk_shipping_locality2_idx` (`unload_locality_id` ASC) VISIBLE,
  INDEX `fk_shipping_shipping_status1_idx` (`shipping_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_shipping_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipping_locality1`
    FOREIGN KEY (`load_locality_id`)
    REFERENCES `locality` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipping_locality2`
    FOREIGN KEY (`unload_locality_id`)
    REFERENCES `locality` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipping_shipping_status1`
    FOREIGN KEY (`shipping_status_id`)
    REFERENCES `shipping_status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `invoice_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `invoice_status` ;

CREATE TABLE IF NOT EXISTS `invoice_status` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL DEFAULT 'undefined',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `invoice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `invoice` ;

CREATE TABLE IF NOT EXISTS `invoice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `person_id` INT NOT NULL,
  `creation_datetime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `sum` DECIMAL(8,2) NOT NULL DEFAULT 0,
  `invoice_status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_invoice_person1_idx` (`person_id` ASC) VISIBLE,
  INDEX `fk_invoice_invoice_status1_idx` (`invoice_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_invoice_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_invoice_status1`
    FOREIGN KEY (`invoice_status_id`)
    REFERENCES `invoice_status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `invoice_has_shipping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `invoice_has_shipping` ;

CREATE TABLE IF NOT EXISTS `invoice_has_shipping` (
  `invoice_id` INT NOT NULL,
  `shipping_id` INT NOT NULL,
  PRIMARY KEY (`invoice_id`, `shipping_id`),
  INDEX `fk_invoice_shipping_shipping1_idx` (`shipping_id` ASC) VISIBLE,
  CONSTRAINT `fk_invoice_shipping_invoice1`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `invoice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_shipping_shipping1`
    FOREIGN KEY (`shipping_id`)
    REFERENCES `shipping` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `settlment_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `settlment_type` ;

CREATE TABLE IF NOT EXISTS `settlment_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `vector` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `settlements`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `settlements` ;

CREATE TABLE IF NOT EXISTS `settlements` (
  `id` INT NOT NULL,
  `creation_datetime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `person_id` INT NOT NULL,
  `settlment_type_id` INT NOT NULL,
  `amount` DECIMAL(8,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_settlements_person1_idx` (`person_id` ASC) VISIBLE,
  INDEX `fk_settlements_settlment_type1_idx` (`settlment_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_settlements_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `person` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_settlements_settlment_type1`
    FOREIGN KEY (`settlment_type_id`)
    REFERENCES `settlment_type` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_details` ;

CREATE TABLE IF NOT EXISTS `user_details` (
  `person_id` INT NOT NULL,
  `balance` DECIMAL(8,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `fk_user_balance_person1`
    FOREIGN KEY (`person_id`)
    REFERENCES `person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_config`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logistic_config` ;

CREATE TABLE IF NOT EXISTS `logistic_config` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistic_net`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logistic_net` ;

CREATE TABLE IF NOT EXISTS `logistic_net` (
  `logistic_config_id` INT NOT NULL,
  `city_id` INT NOT NULL,
  `neighbor_id` INT NOT NULL,
  `distance` DOUBLE NOT NULL DEFAULT 0,
  PRIMARY KEY (`logistic_config_id`, `city_id`, `neighbor_id`),
  INDEX `fk_logistic_net_locality1_idx` (`city_id` ASC) VISIBLE,
  INDEX `fk_logistic_net_locality2_idx` (`neighbor_id` ASC) VISIBLE,
  CONSTRAINT `fk_logistic_net_logistic_config1`
    FOREIGN KEY (`logistic_config_id`)
    REFERENCES `logistic_config` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_logistic_net_locality1`
    FOREIGN KEY (`city_id`)
    REFERENCES `locality` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_logistic_net_locality2`
    FOREIGN KEY (`neighbor_id`)
    REFERENCES `locality` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `role`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `role` (`id`, `name`) VALUES (1, 'manager');
INSERT INTO `role` (`id`, `name`) VALUES (2, 'user');

COMMIT;


-- -----------------------------------------------------
-- Data for table `person`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `person` (`id`, `login`, `password`, `email`, `name`, `surname`, `patronomic`, `title`, `role_id`) VALUES (1, 'vasya', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', NULL, 'Василий', 'Иванов', NULL, NULL, 2);
INSERT INTO `person` (`id`, `login`, `password`, `email`, `name`, `surname`, `patronomic`, `title`, `role_id`) VALUES (2, 'petya', 'b1b3773a05c0ed0176787a4f1574ff0075f7521e', NULL, 'Петр', 'Григоренко', NULL, NULL, 2);
INSERT INTO `person` (`id`, `login`, `password`, `email`, `name`, `surname`, `patronomic`, `title`, `role_id`) VALUES (3, 'sasha', '6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2', NULL, 'Александр', 'Смирнов', NULL, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `locality`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `locality` (`id`, `name`) VALUES (1, 'Киев');
INSERT INTO `locality` (`id`, `name`) VALUES (2, 'Львов');
INSERT INTO `locality` (`id`, `name`) VALUES (3, 'Луцк');
INSERT INTO `locality` (`id`, `name`) VALUES (4, 'Ужгород');
INSERT INTO `locality` (`id`, `name`) VALUES (5, 'Ровно');
INSERT INTO `locality` (`id`, `name`) VALUES (6, 'Тернополь');
INSERT INTO `locality` (`id`, `name`) VALUES (7, 'Хмельницкий');
INSERT INTO `locality` (`id`, `name`) VALUES (8, 'Житомир');
INSERT INTO `locality` (`id`, `name`) VALUES (9, 'Черкассы');
INSERT INTO `locality` (`id`, `name`) VALUES (10, 'Чернигов');
INSERT INTO `locality` (`id`, `name`) VALUES (11, 'Кропивницкий');
INSERT INTO `locality` (`id`, `name`) VALUES (12, 'Николаев');
INSERT INTO `locality` (`id`, `name`) VALUES (13, 'Херсон');
INSERT INTO `locality` (`id`, `name`) VALUES (14, 'Одесса');
INSERT INTO `locality` (`id`, `name`) VALUES (15, 'Днепр');
INSERT INTO `locality` (`id`, `name`) VALUES (16, 'Запорожье');
INSERT INTO `locality` (`id`, `name`) VALUES (17, 'Сумы');
INSERT INTO `locality` (`id`, `name`) VALUES (18, 'Харьков');
INSERT INTO `locality` (`id`, `name`) VALUES (19, 'Полтава');
INSERT INTO `locality` (`id`, `name`) VALUES (20, 'Винница');
INSERT INTO `locality` (`id`, `name`) VALUES (21, 'Черновцы');
INSERT INTO `locality` (`id`, `name`) VALUES (22, 'Ивано-Франковск');

COMMIT;


-- -----------------------------------------------------
-- Data for table `shipping_status`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `shipping_status` (`id`, `name`) VALUES (1, 'just created');
INSERT INTO `shipping_status` (`id`, `name`) VALUES (2, 'waiting for pay');
INSERT INTO `shipping_status` (`id`, `name`) VALUES (3, 'accepted');
INSERT INTO `shipping_status` (`id`, `name`) VALUES (4, 'delivering');
INSERT INTO `shipping_status` (`id`, `name`) VALUES (5, 'delivered');

COMMIT;


-- -----------------------------------------------------
-- Data for table `invoice_status`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `invoice_status` (`id`, `name`) VALUES (1, 'waiting for pay');
INSERT INTO `invoice_status` (`id`, `name`) VALUES (2, 'paid');

COMMIT;


-- -----------------------------------------------------
-- Data for table `settlment_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `settlment_type` (`id`, `name`, `vector`) VALUES (1, 'payment', 1);
INSERT INTO `settlment_type` (`id`, `name`, `vector`) VALUES (2, 'spending', -1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_details`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `user_details` (`person_id`, `balance`) VALUES (1, 0);
INSERT INTO `user_details` (`person_id`, `balance`) VALUES (2, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `logistic_config`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `logistic_config` (`id`, `name`) VALUES (1, 'один распредсклад');

COMMIT;


-- -----------------------------------------------------
-- Data for table `logistic_net`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 2, 1, 550);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 3, 1, 438);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 4, 1, 809);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 5, 1, 336);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 6, 1, 420);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 7, 1, 323);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 8, 1, 140);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 9, 1, 192);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 10, 1, 148);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 11, 1, 303);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 12, 1, 481);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 13, 1, 547);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 14, 1, 475);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 15, 1, 478);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 16, 1, 556);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 17, 1, 369);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 18, 1, 482);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 19, 1, 344);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 20, 1, 263);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 21, 1, 531);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 22, 1, 605);
INSERT INTO `logistic_net` (`logistic_config_id`, `city_id`, `neighbor_id`, `distance`) VALUES (1, 1, 1, 100);

COMMIT;

