SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `educa` ;
CREATE SCHEMA IF NOT EXISTS `educa` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `educa` ;

-- -----------------------------------------------------
-- Table `educa`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`category` ;

CREATE TABLE IF NOT EXISTS `educa`.`category` (
  `category_id` INT NOT NULL,
  `category_description` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
