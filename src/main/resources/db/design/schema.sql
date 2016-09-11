SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `educa` ;
CREATE SCHEMA IF NOT EXISTS `educa` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `educa` ;

-- -----------------------------------------------------
-- Table `educa`.`categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`categoria` ;

CREATE TABLE IF NOT EXISTS `educa`.`categoria` (
  `id_categoria` INT NOT NULL,
  `descripcion` VARCHAR(100) NULL,
  `imagen` VARCHAR(255) NULL,
  PRIMARY KEY (`id_categoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`usuario` ;

CREATE TABLE IF NOT EXISTS `educa`.`usuario` (
  `id_usuario` INT NOT NULL,
  `nombre` VARCHAR(50) NULL,
  `apellido` VARCHAR(120) NULL,
  `email` VARCHAR(150) NULL,
  `foto` VARCHAR(255) NULL,
  PRIMARY KEY (`id_usuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`docente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`docente` ;

CREATE TABLE IF NOT EXISTS `educa`.`docente` (
  `legajo` INT NOT NULL,
  `id_usuario` INT NULL,
  PRIMARY KEY (`legajo`),
  INDEX `fk_docente_usuario_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_docente_usuario`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `educa`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`curso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`curso` ;

CREATE TABLE IF NOT EXISTS `educa`.`curso` (
  `id_curso` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `descripcion` VARCHAR(255) NULL,
  `legajo_docente` INT NULL,
  `id_categoria` INT NULL,
  `imagen` BLOB NULL,
  `link_imagen` VARCHAR(45) NULL,
  `valoracion_promedio` INT NULL,
  `cantidad_valoraciones` BIGINT NULL,
  `fecha_estimada_prox_sesion` DATE NULL,
  PRIMARY KEY (`id_curso`),
  INDEX `fk_curso_1_idx` (`id_categoria` ASC),
  INDEX `fk_curso_docente_idx` (`legajo_docente` ASC),
  CONSTRAINT `fk_curso_categoria`
    FOREIGN KEY (`id_categoria`)
    REFERENCES `educa`.`categoria` (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_curso_docente`
    FOREIGN KEY (`legajo_docente`)
    REFERENCES `educa`.`docente` (`legajo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`sesion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`sesion` ;

CREATE TABLE IF NOT EXISTS `educa`.`sesion` (
  `id_sesion` INT NOT NULL,
  `fecha_desde` DATE NULL,
  `fecha_hasta` DATE NULL,
  `cupos` INT NULL,
  `costo` DECIMAL NULL,
  `id_curso` INT NULL,
  PRIMARY KEY (`id_sesion`),
  INDEX `fk_sesion_curso_idx` (`id_curso` ASC),
  CONSTRAINT `fk_sesion_curso`
    FOREIGN KEY (`id_curso`)
    REFERENCES `educa`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`parametro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`parametro` ;

CREATE TABLE IF NOT EXISTS `educa`.`parametro` (
  `key` VARCHAR(45) NOT NULL,
  `value` VARCHAR(100) NULL,
  PRIMARY KEY (`key`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
