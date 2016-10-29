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
  `estado_curso` VARCHAR(45) NOT NULL DEFAULT 'NO_PUBLICADO',
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
-- Table `educa`.`foro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`foro` ;

CREATE TABLE IF NOT EXISTS `educa`.`foro` (
  `id_foro` BIGINT NOT NULL AUTO_INCREMENT,
  `estado_foro` VARCHAR(45) NOT NULL DEFAULT 'HABILITADO',
  `temas_por_aprobar` INT NOT NULL DEFAULT 0,
  `temas_aprobados` INT NOT NULL DEFAULT 0,
  `comentarios_por_aprobar` INT NOT NULL DEFAULT 0,
  `comentarios_aprobados` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_foro`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`sesion`
-- -----------------------------------------------------
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
  `id_foro` BIGINT NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`),
  INDEX `fk_sesion_curso_idx` (`id_curso` ASC),
  INDEX `fk_sesion_foro1_idx` (`id_foro` ASC),
  CONSTRAINT `fk_sesion_curso`
    FOREIGN KEY (`id_curso`)
    REFERENCES `educa`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sesion_foro1`
    FOREIGN KEY (`id_foro`)
    REFERENCES `educa`.`foro` (`id_foro`)
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
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `duracion_estimada` INT NOT NULL,
  `estado_unidad` VARCHAR(45) NOT NULL DEFAULT 'NO_PUBLICADO',
  PRIMARY KEY (`numero_componente`, `id_curso`),
  INDEX `fk_unidad_curso1_idx` (`id_curso` ASC),
  CONSTRAINT `fk_unidad_curso1`
    FOREIGN KEY (`id_curso`)
    REFERENCES `educa`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`email_template`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`email_template` ;

CREATE TABLE IF NOT EXISTS `educa`.`email_template` (
  `clave` VARCHAR(45) NOT NULL,
  `valor` VARCHAR(1500) NOT NULL,
  PRIMARY KEY (`clave`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`sesion_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`sesion_usuario` ;

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


-- -----------------------------------------------------
-- Table `educa`.`video_unidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`video_unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`video_unidad` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `numero_video` INT NOT NULL,
  `video` LONGBLOB NOT NULL,
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
  `cant_preguntas_alumno` INT NOT NULL,
  `completo` TINYINT(1) NOT NULL DEFAULT false,
  PRIMARY KEY (`numero_componente`, `id_curso`, `numero_examen`),
  CONSTRAINT `fk_examen_unidad_unidad1`
    FOREIGN KEY (`numero_componente` , `id_curso`)
    REFERENCES `educa`.`unidad` (`numero_componente` , `id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`pregunta_examen_unidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`pregunta_examen_unidad` ;

CREATE TABLE IF NOT EXISTS `educa`.`pregunta_examen_unidad` (
  `numero_componente` INT NOT NULL,
  `id_curso` INT NOT NULL,
  `numero_examen` INT NOT NULL,
  `numero_pregunta` INT NOT NULL,
  `enunciado` VARCHAR(500) NOT NULL,
  `multiple_choice` TINYINT(1) NOT NULL,
  `respuesta` VARCHAR(45) NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`, `numero_examen`, `numero_pregunta`),
  CONSTRAINT `fk_pregunta_examen_unidad_examen_unidad1`
    FOREIGN KEY (`numero_componente` , `id_curso` , `numero_examen`)
    REFERENCES `educa`.`examen_unidad` (`numero_componente` , `id_curso` , `numero_examen`)
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
  `numero_pregunta` INT NOT NULL,
  `numero_opcion` INT NOT NULL,
  `texto` VARCHAR(500) NOT NULL,
  `es_correcta` TINYINT(1) NOT NULL,
  PRIMARY KEY (`numero_componente`, `id_curso`, `numero_examen`, `numero_pregunta`, `numero_opcion`),
  CONSTRAINT `fk_opcion_examen_unidad_pregunta_examen_unidad1`
    FOREIGN KEY (`numero_componente` , `id_curso` , `numero_examen` , `numero_pregunta`)
    REFERENCES `educa`.`pregunta_examen_unidad` (`numero_componente` , `id_curso` , `numero_examen` , `numero_pregunta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`tema`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`tema` ;

CREATE TABLE IF NOT EXISTS `educa`.`tema` (
  `id_tema` BIGINT NOT NULL AUTO_INCREMENT,
  `fecha_creacion` DATE NOT NULL,
  `id_usuario` BIGINT NOT NULL,
  `titulo` VARCHAR(45) NULL,
  `descripcion` VARCHAR(200) NOT NULL,
  `estado_tema` VARCHAR(45) NOT NULL DEFAULT 'INDEFINIDO',
  `id_foro` BIGINT NOT NULL,
  PRIMARY KEY (`id_tema`),
  INDEX `fk_tema_usuario1_idx` (`id_usuario` ASC),
  INDEX `fk_tema_foro1_idx` (`id_foro` ASC),
  CONSTRAINT `fk_tema_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `educa`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tema_foro1`
    FOREIGN KEY (`id_foro`)
    REFERENCES `educa`.`foro` (`id_foro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `educa`.`comentario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `educa`.`comentario` ;

CREATE TABLE IF NOT EXISTS `educa`.`comentario` (
  `id_comentario` BIGINT NOT NULL AUTO_INCREMENT,
  `fecha_creacion` DATE NOT NULL,
  `id_usuario` BIGINT NOT NULL,
  `descripcion` VARCHAR(500) NOT NULL,
  `estado_comentario` VARCHAR(45) NOT NULL DEFAULT 'INDEFINIDO',
  `id_tema` BIGINT NOT NULL,
  PRIMARY KEY (`id_comentario`),
  INDEX `fk_comentario_usuario1_idx` (`id_usuario` ASC),
  INDEX `fk_comentario_tema1_idx` (`id_tema` ASC),
  CONSTRAINT `fk_comentario_usuario1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `educa`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comentario_tema1`
    FOREIGN KEY (`id_tema`)
    REFERENCES `educa`.`tema` (`id_tema`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
