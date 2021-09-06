SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS `delivery` ;

CREATE SCHEMA IF NOT EXISTS `delivery` DEFAULT CHARACTER SET utf8 ;
USE `delivery` ;

DROP TABLE IF EXISTS `delivery`.`role` ;

CREATE TABLE IF NOT EXISTS `delivery`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


DROP TABLE IF EXISTS `delivery`.`person` ;

CREATE TABLE IF NOT EXISTS `delivery`.`person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
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
    REFERENCES `delivery`.`role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


DROP TABLE IF EXISTS `delivery`.`locality` ;

CREATE TABLE IF NOT EXISTS `delivery`.`locality` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


DROP TABLE IF EXISTS `delivery`.`shipping_status` ;

CREATE TABLE IF NOT EXISTS `delivery`.`shipping_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


DROP TABLE IF EXISTS `delivery`.`shipping` ;

CREATE TABLE IF NOT EXISTS `delivery`.`shipping` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `person_id` INT NOT NULL,
  `load_locality_id` INT NOT NULL,
  `consignee` VARCHAR(45) NOT NULL COMMENT 'That who recieve shipping',
  `unload_locality_id` INT NOT NULL,
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
    REFERENCES `delivery`.`person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipping_locality1`
    FOREIGN KEY (`load_locality_id`)
    REFERENCES `delivery`.`locality` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipping_locality2`
    FOREIGN KEY (`unload_locality_id`)
    REFERENCES `delivery`.`locality` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_shipping_shipping_status1`
    FOREIGN KEY (`shipping_status_id`)
    REFERENCES `delivery`.`shipping_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

START TRANSACTION;
USE `delivery`;
INSERT INTO `delivery`.`role` (`id`, `name`) VALUES (1, 'manager');
INSERT INTO `delivery`.`role` (`id`, `name`) VALUES (2, 'user');

COMMIT;


START TRANSACTION;
USE `delivery`;
INSERT INTO `delivery`.`person` (`id`, `login`, `password`, `email`, `name`, `surname`, `patronomic`, `title`, `role_id`) VALUES (1, 'vasya', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 'a@a.com', '', NULL, NULL, NULL, 2);
INSERT INTO `delivery`.`person` (`id`, `login`, `password`, `email`, `name`, `surname`, `patronomic`, `title`, `role_id`) VALUES (2, 'petya', 'b1b3773a05c0ed0176787a4f1574ff0075f7521e', 'b@a.com', NULL, NULL, NULL, NULL, 2);
INSERT INTO `delivery`.`person` (`id`, `login`, `password`, `email`, `name`, `surname`, `patronomic`, `title`, `role_id`) VALUES (3, 'sahsa', '6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2', 'c@a.com', '', '', '', '', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `delivery`.`locality`
-- -----------------------------------------------------
START TRANSACTION;
USE `delivery`;
INSERT INTO `delivery`.`locality` (`id`, `name`) VALUES (1, 'Kiev');
INSERT INTO `delivery`.`locality` (`id`, `name`) VALUES (2, 'Dnepr');
INSERT INTO `delivery`.`locality` (`id`, `name`) VALUES (3, 'Lvov');
INSERT INTO `delivery`.`locality` (`id`, `name`) VALUES (4, 'Kropivnickij');
INSERT INTO `delivery`.`locality` (`id`, `name`) VALUES (5, 'Odessa');

COMMIT;

START TRANSACTION;
USE `delivery`;
INSERT INTO `delivery`.`shipping_status` (`id`, `name`) VALUES (1, 'created');
INSERT INTO `delivery`.`shipping_status` (`id`, `name`) VALUES (2, 'approved');
INSERT INTO `delivery`.`shipping_status` (`id`, `name`) VALUES (3, 'delivered');
INSERT INTO `delivery`.`shipping_status` (`id`, `name`) VALUES (4, 'canceled');

COMMIT;


START TRANSACTION;
USE `delivery`;
INSERT INTO `delivery`.`shipping` (`id`, `creation_timestamp`, `person_id`, `load_locality_id`, `consignee`, `unload_locality_id`, `distance`, `weight`, `volume`, `fare`, `shipping_status_id`) VALUES (1, DEFAULT, 1, 1, 'Foxtrot', 2, 540, 100, 90, 257, 1);
INSERT INTO `delivery`.`shipping` (`id`, `creation_timestamp`, `person_id`, `load_locality_id`, `consignee`, `unload_locality_id`, `distance`, `weight`, `volume`, `fare`, `shipping_status_id`) VALUES (2, DEFAULT, 1, 2, 'Eldorado', 1, 540, 200, 150, 450, 2);
INSERT INTO `delivery`.`shipping` (`id`, `creation_timestamp`, `person_id`, `load_locality_id`, `consignee`, `unload_locality_id`, `distance`, `weight`, `volume`, `fare`, `shipping_status_id`) VALUES (3, DEFAULT, 2, 3, 'Silpo', 5, 400, 1500, 1200, 1900, 3);
INSERT INTO `delivery`.`shipping` (`id`, `creation_timestamp`, `person_id`, `load_locality_id`, `consignee`, `unload_locality_id`, `distance`, `weight`, `volume`, `fare`, `shipping_status_id`) VALUES (4, DEFAULT, 2, 2, 'Eldorado', 1, 230, 170, 200, 238, 3);
INSERT INTO `delivery`.`shipping` (`id`, `creation_timestamp`, `person_id`, `load_locality_id`, `consignee`, `unload_locality_id`, `distance`, `weight`, `volume`, `fare`, `shipping_status_id`) VALUES (5, DEFAULT, 3, 1, 'Meest', 1, 0, 200, 200, 200, 4);

COMMIT;

