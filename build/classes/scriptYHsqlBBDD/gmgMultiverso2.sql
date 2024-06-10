CREATE DATABASE IF NOT EXISTS gmgmultiverso;
USE gmgmultiverso;
/*Primero borramos las tablas*/
DROP TABLE IF EXISTS detalle_pedido;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS proveedor;
DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS cliente;

/*Creamos las tablas*/
CREATE TABLE cliente
(
id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
apellido VARCHAR(50) NOT NULL,
contrasenia VARCHAR(50) NOT NULL,
direccion VARCHAR(50) NOT NULL,
telefono INT UNIQUE, 
email VARCHAR(50) UNIQUE
);

CREATE TABLE empleado
(
id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
apellido VARCHAR(50) NOT NULL,
contrasenia VARCHAR(50)  NOT NULL,
telefono INT UNIQUE NOT NULL, 
email VARCHAR(50) UNIQUE
);

CREATE TABLE pedido
(
id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
id_cliente INT NOT NULL,
fecha_pedido DATETIME,
id_empleado INT,
estado INT,
ultima_actualizacion DATETIME NOT NULL,
FOREIGN KEY (id_cliente) REFERENCES cliente(id),
FOREIGN KEY (id_empleado) REFERENCES empleado(id)
);

--el email si es UNIQUE es NOT NULL
CREATE TABLE proveedor
(
id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
nombre_empresa VARCHAR(50) NOT NULL,
telefono INT UNIQUE NOT NULL, 
email VARCHAR(50) UNIQUE
);

CREATE TABLE producto
(
id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
precio DECIMAL(10, 2) NOT NULL, 
unidad_existente INT NOT NULL,
id_proveedor INT NOT NULL,
FOREIGN KEY (id_proveedor) REFERENCES proveedor(id)
);

CREATE TABLE detalle_pedido
(
id_pedido INT NOT NULL,
id_producto INT NOT NULL,
cantidad INT NOT NULL,
PRIMARY KEY (id_pedido, id_producto),
FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE,
FOREIGN KEY (id_producto) REFERENCES producto(id) ON DELETE RESTRICT
);



/*Insertamos valores a las tablas*/
-- Datos cliente
INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES ('Carmen','García', '1234','Calle Méndez',612478563,'carm.gar@gmail.com');
INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES ('Jose Luis','Sanz', '5678','Calle Sol',645889154,'jl.sanz@gmail.com');
INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES ('Luisa','Ramírez', '9012','Calle Delicias',635467102,'luisa.ramirez@gmail.com');
INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES ('Alberto','Col', '3456','Calle Palas',677467102,'alberto.col@gmail.com');
INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES ('Peter','Pan', '7890','Calle Nunca Jamás',678963102,'peter.pan@gmail.com');
INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES ('Blanca','Paloma', '1252','Calle Gran Vía',674159852,'blanca.paloma@gmail.com');

-- Datos empleado
INSERT INTO empleado (nombre, apellido, contrasenia, telefono, email) VALUES ('Alan','Rodríguez','alan1234',626029117,'alan.rodriguez@gmail.com');
INSERT INTO empleado (nombre, apellido, contrasenia, telefono, email) VALUES ('Gustavo','López','gustavo5678',677737021,'gustavo.lopez@gmail.com');
INSERT INTO empleado (nombre, apellido, contrasenia, telefono, email) VALUES ('Leandro','Martínez','leandro9012',688649468,'leandro.martinez@gmail.com');
INSERT INTO empleado (nombre, apellido, contrasenia, telefono, email) VALUES ('Paula','Ruiz','paula3456',624662616,'oaula.ruiz@gmail.com');
INSERT INTO empleado (nombre, apellido, contrasenia, telefono, email) VALUES ('Romina','Ramos','romina7890',660518943,'romina.ramos@gmail.com');
INSERT INTO empleado (nombre, apellido, contrasenia, telefono, email) VALUES ('Zulema','Ortiz','zulema1234',613938195,'zulema.ortiz@gmail.com');

INSERT INTO empleado (nombre, apellido, contrasenia, telefono, email) VALUES ('Admin','Admin','admin',695319038,'admin@gmgmultiverso.es');


-- Datos pedido
INSERT INTO pedido (id_cliente, fecha_pedido, id_empleado, estado, ultima_actualizacion) VALUES (1, '2024-03-15', 1, 1, '2023-03-15');
INSERT INTO pedido (id_cliente, fecha_pedido, id_empleado, estado, ultima_actualizacion) VALUES (2, '2024-02-05', 2, 3, '2024-02-05');
INSERT INTO pedido (id_cliente, fecha_pedido, id_empleado, estado, ultima_actualizacion) VALUES (3, '2024-04-11', 3, 2, '2024-04-11');
INSERT INTO pedido (id_cliente, fecha_pedido, id_empleado, estado, ultima_actualizacion) VALUES (4, '2024-01-19', 4, 2, '2024-01-19');
INSERT INTO pedido (id_cliente, fecha_pedido, id_empleado, estado, ultima_actualizacion) VALUES (5, '2024-03-22', 5, 1, '2024-03-22');
INSERT INTO pedido (id_cliente, fecha_pedido, id_empleado, estado, ultima_actualizacion) VALUES (4, '2024-04-01', 1, 3, '2024-04-01');

-- Datos proveedor
INSERT INTO proveedor (nombre_empresa, telefono, email) VALUES ('Delicias',627713519,'susana.gen@delicias.es');
INSERT INTO proveedor (nombre_empresa, telefono, email) VALUES ('Kongo',699467577,'julia.perez@kongo.es');
INSERT INTO proveedor (nombre_empresa, telefono, email) VALUES ('Antojitos',693410234,'carlos.costa@antojitos.es');
INSERT INTO proveedor (nombre_empresa, telefono, email) VALUES ('Tom Hortons',674374831,'plex.falan@tomhortons.es');
INSERT INTO proveedor (nombre_empresa, telefono, email) VALUES ('Paraiso',644189382,'mariana.gol@paraiso.es');
INSERT INTO proveedor (nombre_empresa, telefono, email) VALUES ('Rustica',603656304,'sebastian.mar@rustica.es');

-- Datos producto
INSERT INTO producto (nombre, precio, unidad_existente, id_proveedor) VALUES ('Arroz',8.50,100,4);
INSERT INTO producto (nombre, precio, unidad_existente, id_proveedor) VALUES ('Hamburguesa carne',9.50,700,3);
INSERT INTO producto (nombre, precio, unidad_existente, id_proveedor) VALUES ('Hamburguesa vegan',9.50,700,5);
INSERT INTO producto (nombre, precio, unidad_existente, id_proveedor) VALUES ('Macarrones',5.50,700,1);
INSERT INTO producto (nombre, precio, unidad_existente, id_proveedor) VALUES ('Tostadas',4.50,700,2);
INSERT INTO producto (nombre, precio, unidad_existente, id_proveedor) VALUES ('Pizza',8.50,700,3);

-- Datos detalle_pedido
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (1, 1, 2);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (2, 2, 4);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (3, 3, 3);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (4, 4, 2);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (2, 1, 1);
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (2, 2, 2);










