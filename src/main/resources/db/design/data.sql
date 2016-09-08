INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(1,'Programación','programacion.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(2,'Gastronomía','gastronomia.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(3,'Moda','moda.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(4,'Gestión','gestion.png');
INSERT INTO `categoria`(`id_categoria`,`descripcion`,`imagen`)VALUES(5,'Exactas','exactas.png');

/*  Docente  */
INSERT INTO `usuario`(`id_usuario`,`nombre`,`apellido`,`email`,`foto`)VALUES(1,'Carlos','Fontela','carlos@mail.com',null);
INSERT INTO `usuario`(`id_usuario`,`nombre`,`apellido`,`email`,`foto`)VALUES(2,'Alejandro','Molinari','alejandro@mail.com',null);

INSERT INTO `docente`(`legajo`,`id_usuario`)VALUES(1,1);
INSERT INTO `docente`(`legajo`,`id_usuario`)VALUES(2,2);
