use base_veterinaria;

INSERT INTO `especie` VALUES 
(1,_binary '\0','Perro'),
(2,_binary '\0','Gato');

INSERT INTO `categoria` VALUES 
(1,_binary '\0','Alimentos'),
(2,_binary '\0','Juguetes'),
(3,_binary '\0','Vestimentas'),
(4,_binary '\0','Accesorios'),
(5,_binary '\0','Vacunas'),
(6,_binary '\0','Medicamentos');

INSERT INTO `usuario` VALUES 
(1,'recepcionista@gmail.com',_binary '\0','$2a$10$E5vSNtDn5IEuMdL9aCxY/.ctd0iADKNjPkvpZlWhftOdb7vHCLvqe','Jhoselin'),
(2,'veterinaria@gmail.com',_binary '\0','$2a$10$E5vSNtDn5IEuMdL9aCxY/.ctd0iADKNjPkvpZlWhftOdb7vHCLvqe','Jackelin'),
(3,'groomer@gmail.com',_binary '\0','$2a$10$E5vSNtDn5IEuMdL9aCxY/.ctd0iADKNjPkvpZlWhftOdb7vHCLvqe','Fernando'),
(4,'almacenero@gmail.com',_binary '\0','$2a$10$E5vSNtDn5IEuMdL9aCxY/.ctd0iADKNjPkvpZlWhftOdb7vHCLvqe','Jesus'),
(5,'admin@gmail.com',_binary '\0','$2a$10$E5vSNtDn5IEuMdL9aCxY/.ctd0iADKNjPkvpZlWhftOdb7vHCLvqe','Admin'),
(6,'admin.normal@gmail.com',_binary '\0','$2a$10$E5vSNtDn5IEuMdL9aCxY/.ctd0iADKNjPkvpZlWhftOdb7vHCLvqe','AdminNormal');

INSERT INTO `rol` VALUES 
(1,_binary '\0','RECEPCIONISTA'),
(2,_binary '\0','VETERINARIO'),
(3,_binary '\0','ALMACENERO'),
(4,_binary '\0','GROOMER'),
(5,_binary '\0','ADMINISTRADOR');

INSERT INTO `proveedor` VALUES 
(1,923564787,'atalaya@gmail.com',_binary '\0','https://storage.googleapis.com/proyecto-veterinario.appspot.com/proveedores/vet-proveedor-1.webp','Atalaya'),
(2,923365485,'jacinto@gmail.com',_binary '\0','https://storage.googleapis.com/proyecto-veterinario.appspot.com/proveedores/vet-proveedor-2.png','Don Jacinto'),
(3,923994755,'sumi@gmail.com',_binary '\0','','Suministros ALM'),
(4,923564875,'colliques@gmail.com',_binary '\0','','Colliques'),
(5,908573765,'huellitas@gmail.com',_binary '\0','','Huellitas');

INSERT INTO `producto` VALUES 
(1,'1Kg croquetas',_binary '\0','2023-07-02 09:38:27','https://storage.googleapis.com/proyecto-veterinario.appspot.com/productos/vet-producto-1.jpg','RicoCan','RicoCan Cachorros',5,8,22,'3 Kilos',1),
(2,'1Kg croquetas',_binary '\0','2023-07-02 09:38:40','https://storage.googleapis.com/proyecto-veterinario.appspot.com/productos/vet-producto-2.jpg','RicoCat','RicoCat Gatitos',7,11,27,'9 Kilos',1),
(3,'Grande, hecho de rafia',_binary '\0','2023-07-02 09:39:37','https://storage.googleapis.com/proyecto-veterinario.appspot.com/productos/vet-producto-3.jpg','Generico','Rascador Felino',18,25,11,'Ninguna',2),
(4,'Geve pequeño',_binary '\0','2023-07-02 09:40:10','https://storage.googleapis.com/proyecto-veterinario.appspot.com/productos/vet-producto-4.webp','Generico','Hueso',6,10,36,'Ninguna',2),
(5,'Mediano',_binary '\0','2023-07-02 09:40:45','https://storage.googleapis.com/proyecto-veterinario.appspot.com/productos/vet-producto-5.jpg','Generico','Corta Uñas',7,13,16,'Ninguna',4);

INSERT INTO `cliente` VALUES 
(1,'Flores',929073820,'Villa el Salvador',_binary '\0','2023-07-02 09:34:30','https://storage.googleapis.com/proyecto-veterinario.appspot.com/clientes/vet-cliente-1.jpg','Masculino','Erick'),
(2,'Rojas',929073821,'Comas',_binary '\0','2023-07-02 09:34:51','https://storage.googleapis.com/proyecto-veterinario.appspot.com/clientes/vet-cliente-2.jpg','Femenino','Paula'),
(3,'Barrientos',929073822,'Villa Maria del Triunfo',_binary '\0','2023-07-02 09:35:19','https://storage.googleapis.com/proyecto-veterinario.appspot.com/clientes/vet-cliente-3.jpg','Masculino','Andres'),
(4,'Peralta',929073823,'Villa Maria del Triunfo',_binary '\0','2023-07-02 09:35:39','https://storage.googleapis.com/proyecto-veterinario.appspot.com/clientes/vet-cliente-4.jpg','Masculino','Daniel'),
(5,'Cereceda',928308474,NULL,_binary '\0','2023-08-22 18:57:10','https://storage.googleapis.com/proyecto-veterinario.appspot.com/clientes/vet-cliente-5.jpg','Masculino','Juliana');

INSERT INTO `raza` VALUES 
(1,_binary '\0','Doberman',1),
(2,_binary '\0','Bull dog',1),
(3,_binary '\0','Snaucher',1),
(4,_binary '\0','Pequines',1),
(5,_binary '\0','Salchicha',1),
(6,_binary '\0','Siames',2),
(7,_binary '\0','Angora',2),
(8,_binary '\0','Sfinge',2);

INSERT INTO `mascota` VALUES 
(1,3,'gringo con blanco',_binary '\0','2023-07-02 09:56:02','https://storage.googleapis.com/proyecto-veterinario.appspot.com/mascotas/vet-mascota-1.jpg',6,'Roñoso',3.5,'Macho',1,8),
(2,3,'gato de color naranja super cariñoso',_binary '\0','2023-07-02 10:01:00','https://storage.googleapis.com/proyecto-veterinario.appspot.com/mascotas/vet-mascota-2.jpg',6,'Gata Gato',4.5,'Macho',1,8),
(3,3,'negro con blanco',_binary '\0','2023-07-02 10:01:01','https://storage.googleapis.com/proyecto-veterinario.appspot.com/mascotas/vet-mascota-3.jpg',6,'Aidoneo',4,'Macho',2,8),
(4,1,'negro con bigotes blancos',_binary '\0','2023-07-02 10:04:03','https://storage.googleapis.com/proyecto-veterinario.appspot.com/mascotas/vet-mascota-4.webp',6,'Botitas',4,'Macho',2,8),
(5,1,'acaramelada con manchas blancas',_binary '\0','2023-07-02 10:04:42','https://storage.googleapis.com/proyecto-veterinario.appspot.com/mascotas/vet-mascota-5.jpg',2,'Leya',20.5,'Hembra',3,2);

INSERT INTO `cita` VALUES 
(1,_binary '\0','pendiente',NULL,'2023-08-21 01:12:00',NULL,'Consulta',5),
(2,_binary '\0','atendida','2023-08-22 19:16:02','2023-08-21 02:13:00',NULL,'Consulta',4),
(3,_binary '\0','atendida','2023-08-22 19:16:47','2023-08-21 03:13:00',NULL,'Tratamiento',3),
(4,_binary '\0','atendida','2023-08-22 19:18:35','2023-08-21 04:14:00',NULL,'Baño',2),
(5,_binary '\0','atendida','2023-08-22 19:24:38','2023-08-21 05:14:00',NULL,'Corte',1);

INSERT INTO `banio` VALUES 
(1,'Baño con agua caliente',_binary '\0','2023-08-22 19:18:35','','',30,'Baño Solo',4,2),
(2,'Corte rapado cabeza de leon',_binary '\0','2023-08-22 19:24:38','','',45,'Baño + Corte',5,1),
(3,'',_binary '\0','2023-08-22 19:25:32','','',20,'Baño Solo',NULL,4),
(4,'con espuma',_binary '\0','2023-08-22 19:26:00','','',25,'Baño Medicado',NULL,3);

INSERT INTO `tratamiento` VALUES 
(1,'Triple felina',_binary '\0','2023-08-22 19:16:02',30,'Vacunacion',2,4),
(2,'Tenia colicos, 5 ml de dexa',_binary '\0','2023-08-22 19:16:47',10,'Desparacitacion',3,3),
(3,'',_binary '\0','2023-08-22 19:27:07',40,'Castracion',NULL,2),
(4,'Tenia tos',_binary '\0','2023-08-22 19:27:39',10,'Consulta',NULL,1);

INSERT INTO `boleta` VALUES 
(1,'emitida','2023-08-22 19:06:28',10.62,NULL,69.62,59,'efectivo',0,1,5),
(2,'emitida','2023-08-22 19:06:50',12.06,NULL,79.06,67,'efectivo',0,5,5),
(3,'anulada','2023-08-22 19:07:04',4.14,'No le alcanzo el efectivo',27.14,23,'efectivo',0,4,5),
(4,'emitida','2023-08-22 19:07:23',4.86,NULL,31.86,27,'efectivo',0,3,5),
(5,'emitida','2023-08-22 19:07:42',4.14,NULL,27.14,23,'efectivo',0,2,5);

INSERT INTO `pedido` VALUES 
(1,'recibida','2023-08-22 19:08:47','2023-08-22 19:09:27',NULL,'efectivo',395,1,5),
(2,'recibida','2023-08-22 19:09:23','2023-08-22 19:09:30',NULL,'efectivo',130,2,5);

INSERT INTO `producto_proveedor` VALUES 
(1,1),
(2,1),
(3,1),
(4,2),
(5,2);

INSERT INTO `boleta_producto` VALUES 
(1,2,1,11,11),
(1,3,1,25,25),
(1,4,1,10,10),
(1,5,1,13,13),
(2,1,1,8,8),
(2,2,1,11,11),
(2,3,1,25,25),
(2,4,1,10,10),
(2,5,1,13,13),
(3,4,1,10,10),
(3,5,1,13,13),
(4,1,2,8,16),
(4,2,1,11,11),
(5,4,1,10,10),
(5,5,1,13,13);

INSERT INTO `pedido_producto` VALUES 
(1,1,15,75,5),
(1,2,20,140,7),
(1,3,10,180,18),
(2,4,10,60,6),
(2,5,10,70,7);

INSERT INTO `usuario_rol` VALUES 
(1,1),
(5,1),
(2,2),
(5,2),
(3,3),
(5,3),
(4,4),
(5,4),
(5,5),
(6,5);



