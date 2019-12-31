-- -----------------------------------------------------
-- Schema organizer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `organizer` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `organizer` ;

-- -----------------------------------------------------
-- Table `organizer`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `organizer`.`user` ;

CREATE TABLE IF NOT EXISTS `organizer`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email_address` VARCHAR(128) NOT NULL,
  `username` VARCHAR(64) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_address_uidx` (`email_address` ASC),
  UNIQUE INDEX `username_uidx` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `organizer`.`team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `organizer`.`customer` ;

CREATE TABLE IF NOT EXISTS `organizer`.`customer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `car_plate` VARCHAR(64) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `started_date` DATETIME NOT NULL,
  `ended_date` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `fk_user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_team_user_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `organizer`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;