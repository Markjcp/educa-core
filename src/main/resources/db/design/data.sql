INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(1,'Programación','programacion.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(2,'Gastronomía','gastronomia.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(3,'Moda','moda.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(4,'Gestión','gestion.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(5,'Exactas','exactas.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(6,'Idiomas','idiomas.png');

INSERT INTO rol_usuario values (1,'ADMINISTRADOR', 'ROL_ADMIN');
INSERT INTO rol_usuario values (2,'DOCENTE','ROL_DOC');
INSERT INTO rol_usuario values (3,'ALUMNO','ROL_ALM');

/*  Docente  */
INSERT INTO `usuario`(`id_usuario`,`nombre`,`apellido`,`email`,`foto`,`id_rol_usuario`)VALUES(1,'Carlos','Fontela','carlos@mail.com',null, 2);
INSERT INTO `usuario`(`id_usuario`,`nombre`,`apellido`,`email`,`foto`,`id_rol_usuario`)VALUES(2,'Alejandro','Molinari','alejandro@mail.com',null, 2);

INSERT INTO `docente`(`legajo`,`id_usuario`)VALUES(1,1);
INSERT INTO `docente`(`legajo`,`id_usuario`)VALUES(2,2);

-- 10/09
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('FECHA_DEFAUL_PROX_CURSO', '2016-10-30');
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('CANT_MESES_PROX_CURSO', '3');
update `educa`.`curso` set fecha_estimada_prox_sesion = '2016-10-30' where 1=1;