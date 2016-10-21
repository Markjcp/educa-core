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
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('MAIL_REGISTRACION_ASUNTO', 'Registro Educa');
INSERT INTO `educa`.`email_template` (`clave`, `valor`) VALUES ('EMAIL_ACTIVACION_USUARIO', '<html ><head> <link href="http://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css"> </head> <body style="font-family: Roboto;"> <h1 style="text-align: center">Bienvenido a Educa</h1> <p style="color: #9090a2; text-align: center">Para poder comenzar a utilizar la plataforma, por favor active su cuenta accionando el siguiente bot&oacute;n</p> <div style="text-align: center"> <a href="http://educa-mnforlenza.rhcloud.com/activar-cuenta?clave-activacion=*clave*&amp;id-usuario=*idusuario*"> <button style="background-color: rgb(230, 81, 0) ! important; border: medium none; display: inline-block; height: 36px; line-height: 36px; outline: 0px none; padding: 0px 2rem; text-transform: uppercase; vertical-align: middle; color: white ! important;">Activar cuenta </button> </a> </div> </body></html>');

INSERT INTO `educa`.`usuario` (`nombre`, `apellido`, `email`, `foto`, `id_rol_usuario`, `password`, `activado`) VALUES ('Marcos', 'Forlenza', 'admin@mail.com', '', '1', 'VIyIwyC5YYs0fVrl/Wsl4XdUgqpFgNVG', 1);
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('RECAPTCHA_SECRET', '6LcnlgcUAAAAAKg9iDvUH1jyFhM-9i0s0wpnc5IQ');

-- 20/10
INSERT INTO `educa`.`parametro` (`key`, `value`) VALUES ('MULTIPLICADOR_PREGUNTAS', '2');