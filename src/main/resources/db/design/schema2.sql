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
-- Table `educa`.`rol_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`rol_usuario` ;

CREATE TABLE IF NOT EXISTS `educa`.`rol_usuario` (
  `id_rol_usuario` INT NOT NULL,
  `descripcion` VARCHAR(45) NOT NULL,
  `acronimo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_rol_usuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`usuario` ;

CREATE TABLE IF NOT EXISTS `educa`.`usuario` (
  `id_usuario` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NULL,
  `apellido` VARCHAR(120) NULL,
  `email` VARCHAR(150) NULL,
  `foto` VARCHAR(255) NULL,
  `id_rol_usuario` INT NOT NULL,
  `password` VARCHAR(100) NULL,
  `clave_activacion` VARCHAR(100) NULL,
  `id_facebook` VARCHAR(45) NULL,
  `id_google` VARCHAR(45) NULL,
  `activado` TINYINT(1) NULL,
  PRIMARY KEY (`id_usuario`),
  INDEX `fk_usuario_rol_usuario1_idx` (`id_rol_usuario` ASC),
  CONSTRAINT `fk_usuario_rol_usuario1`
    FOREIGN KEY (`id_rol_usuario`)
    REFERENCES `educa`.`rol_usuario` (`id_rol_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`docente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`docente` ;

CREATE TABLE IF NOT EXISTS `educa`.`docente` (
  `legajo` BIGINT NOT NULL AUTO_INCREMENT,
  `id_usuario` BIGINT NULL,
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
  `legajo_docente` BIGINT NULL,
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
  `id_sesion` INT NOT NULL AUTO_INCREMENT,
  `fecha_desde` DATE NULL,
  `fecha_hasta` DATE NULL,
  `cupos` INT NULL,
  `costo` DECIMAL NULL,
  `id_curso` INT NULL,
  `fecha_desde_inscripcion` DATE NULL,
  `fecha_hasta_inscripcion` DATE NULL,
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


-- -----------------------------------------------------
-- Table `educa`.`critica`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`critica` ;

CREATE TABLE IF NOT EXISTS `educa`.`critica` (
  `id_critica` BIGINT NOT NULL AUTO_INCREMENT,
  `id_curso` INT NOT NULL,
  `fecha` DATE NULL,
  `calificacion` SMALLINT NULL,
  `comentario` VARCHAR(500) NULL,
  PRIMARY KEY (`id_critica`),
  INDEX `fk_critica_curso1_idx` (`id_curso` ASC),
  CONSTRAINT `fk_critica_curso1`
    FOREIGN KEY (`id_curso`)
    REFERENCES `educa`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`unidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`unidad` (
  `numero_unidad` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(45) NOT NULL,
  `duracion_estimada` INT NOT NULL,
  PRIMARY KEY (`numero_unidad`, `id_curso`),
  INDEX `fk_unidad_curso1_idx` (`id_curso` ASC),
  CONSTRAINT `fk_unidad_curso1`
    FOREIGN KEY (`id_curso`)
    REFERENCES `educa`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`unidad_sesion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`unidad_sesion` ;

CREATE TABLE IF NOT EXISTS `educa`.`unidad_sesion` (
  `numero_unidad` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `id_sesion` INT NOT NULL,
  `fecha_desde` DATE NULL,
  `fecha_hasta` DATE NULL,
  PRIMARY KEY (`numero_unidad`, `id_curso`, `id_sesion`),
  INDEX `fk_unidad_has_sesion_sesion1_idx` (`id_sesion` ASC),
  INDEX `fk_unidad_has_sesion_unidad1_idx` (`numero_unidad` ASC, `id_curso` ASC),
  CONSTRAINT `fk_unidad_has_sesion_unidad1`
    FOREIGN KEY (`numero_unidad` , `id_curso`)
    REFERENCES `educa`.`unidad` (`numero_unidad` , `id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_unidad_has_sesion_sesion1`
    FOREIGN KEY (`id_sesion`)
    REFERENCES `educa`.`sesion` (`id_sesion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

--24/09
CREATE TABLE IF NOT EXISTS `educa`.`email_template` (
  `clave` VARCHAR(45) NOT NULL,
  `valor` VARCHAR(1500) NOT NULL,
  PRIMARY KEY (`clave`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `educa`.`unidad_sesion` ;

DROP TABLE IF EXISTS `educa`.`sesion` ;

CREATE TABLE IF NOT EXISTS `educa`.`sesion` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `fecha_desde` DATE NULL,
  `fecha_hasta` DATE NULL,
  `cupos` INT NULL,
  `costo` DECIMAL NULL,
  `fecha_desde_inscripcion` DATE NULL,
  `fecha_hasta_inscripcion` DATE NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`),
  INDEX `fk_sesion_curso_idx` (`id_curso` ASC),
  CONSTRAINT `fk_sesion_curso`
    FOREIGN KEY (`id_curso`)
    REFERENCES `educa`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `educa`.`unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`unidad` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(45) NOT NULL,
  `duracion_estimada` INT NOT NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`),
  INDEX `fk_unidad_curso1_idx` (`id_curso` ASC),
  CONSTRAINT `fk_unidad_curso1`
    FOREIGN KEY (`id_curso`)
    REFERENCES `educa`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- 29/09

CREATE TABLE IF NOT EXISTS `educa`.`sesion_usuario` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `id_usuario` BIGINT NOT NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`, `id_usuario`),
  INDEX `fk_sesion_has_usuario_usuario1_idx` (`id_usuario` ASC),
  INDEX `fk_sesion_has_usuario_sesion1_idx` (`numero_componente` ASC, `id_curso` ASC),
  CONSTRAINT `fk_sesion_has_usuario_sesion1`
    FOREIGN KEY (`numero_componente` , `id_curso`)
    REFERENCES `educa`.`sesion` (`numero_componente` , `id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sesion_has_usuario_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `educa`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- 08/10

-- -----------------------------------------------------
-- Table `educa`.`video_unidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`video_unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`video_unidad` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `numero_video` INT NOT NULL,
  `video` BLOB NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`, `numero_video`),
  INDEX `fk_video_unidad_unidad1_idx` (`numero_componente` ASC, `id_curso` ASC),
  CONSTRAINT `fk_video_unidad_unidad1`
    FOREIGN KEY (`numero_componente` , `id_curso`)
    REFERENCES `educa`.`unidad` (`numero_componente` , `id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`material_unidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`material_unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`material_unidad` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `numero_material` INT NOT NULL,
  `material` BLOB NOT NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`, `numero_material`),
  CONSTRAINT `fk_material_unidad_unidad1`
    FOREIGN KEY (`numero_componente` , `id_curso`)
    REFERENCES `educa`.`unidad` (`numero_componente` , `id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`examen_unidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`examen_unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`examen_unidad` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `numero_examen` INT NOT NULL,
  `enunciado` VARCHAR(500) NOT NULL,
  `cant_preguntas_alumno` INT NOT NULL,
  `multiple_choice` TINYINT(1) NOT NULL,
  `respuesta` VARCHAR(200) NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`, `numero_examen`),
  CONSTRAINT `fk_examen_unidad_unidad1`
    FOREIGN KEY (`numero_componente` , `id_curso`)
    REFERENCES `educa`.`unidad` (`numero_componente` , `id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`opcion_examen_unidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`opcion_examen_unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`opcion_examen_unidad` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `numero_examen` INT NOT NULL,
  `orden` INT NOT NULL,
  `texto` VARCHAR(500) NOT NULL,
  `es_correcta` TINYINT(1) NOT NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`, `numero_examen`, `orden`),
  CONSTRAINT `fk_opcion_examen_unidad_examen_unidad1`
    FOREIGN KEY (`numero_componente` , `id_curso` , `numero_examen`)
    REFERENCES `educa`.`examen_unidad` (`numero_componente` , `id_curso` , `numero_examen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


--08/10

-- -----------------------------------------------------
-- Table `educa`.`curso`
-- -----------------------------------------------------
ALTER TABLE `educa`.`curso`
  ADD `estado_curso` VARCHAR(45) NOT NULL DEFAULT 'NO_PUBLICADO';
