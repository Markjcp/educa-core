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

-- 24/09
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('mail.smtp.auth', 'true');
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('mail.smtp.starttls.enable', 'true');
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('mail.smtp.host', 'smtp.gmail.com');
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('mail.smtp.port', '587');
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('mail.smtp.user', 'educa.notificaciones@gmail.com');
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('mail.smtp.password', 'educaeduca');

INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('MAIL_FROM', 'educa.notificaciones@gmail.com');
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('MAIL_REGISTRACION_ASUNTO', 'Registración Educa');

INSERT INTO `educa`.`email_template` (`clave`, `valor`) VALUES ('EMAIL_ACTIVACION_USUARIO', '<html style=\"margin-top: 20px; padding-top: 10px; padding-left: 10px; margin-left: 40px; border-width: 30px 100px 200px; border-style: solid; border-color: #f1ebf0; -moz-border-top-colors: none; -moz-border-right-colors: none; -moz-border-bottom-colors: none; -moz-border-left-colors: none; border-image: none; margin-right: 20px;\"><head> <link href=\"http://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\"> </head> <body style=\"font-family: Roboto;\"> <h1 style=\"padding-left: 170px;\">Bienvenido a Educa</h1> <p style=\"color: #9090a2;\">Para poder comenzar a utilizar la plataforma, por favor active su cuenta accionando el siguiente bot&oacute;n</p> <div style=\"padding-left: 220px;\"> <a href=\"http://educa-mnforlenza.rhcloud.com/activar-cuenta?clave-activacion=*clave*&id-usuario=*idusuario*\"> <button style=\"background-color: rgb(230, 81, 0) ! important; border: medium none; display: inline-block; height: 36px; line-height: 36px; outline: 0px none; padding: 0px 2rem; text-transform: uppercase; vertical-align: middle; color: white ! important;\">Activar cuenta </button> </a> </div> </body></html> ');

