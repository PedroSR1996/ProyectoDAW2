-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS newrestaurante_db;
-- drop database newrestaurante_db;
USE newrestaurante_db;

-- select * from tb_estado_mesa;
-- select * from tb_mesa;
-- select * from tb_reserva;
-- select * from tb_usuario;
-- select * from tb_mesa;
-- select * from tb_transporte;

create table tb_usuario (
    idUsuario int auto_increment primary key,
    nombre varchar(100) NOT NULL,
    correo varchar(100) UNIQUE NOT NULL,
    contrasena varchar(255) NOT NULL, 
    telefono varchar(20),
	rol varchar(20) NOT NULL DEFAULT 'CLIENTE'
);

create table tb_estado_mesa (
    idEstMesa int auto_increment primary key,
    descripcion varchar(20) NOT NULL
);

create table tb_mesa (
    idMesa int auto_increment primary key,
    numero int NOT NULL,
    idEstMesa int NOT NULL,
    capacidad int NOT NULL,
    ubicacion varchar(100),
    foreign key (idEstMesa) references tb_estado_mesa(idEstMesa)
);

create table tb_reserva (
    idReserva int auto_increment primary key,
    fecha date not null,
    hora TIME not null,
    numeroPersonas int not null,
    idUsuario INT NOT NULL,
    idMesa INT NOT NULL,
    foreign key (idUsuario) references tb_usuario(idUsuario),
    foreign key (idMesa) references tb_mesa(idMesa)
);

create table tb_transporte (
    idTransporte int auto_increment primary key,
    direccion varchar(255),
    numeroPasajeros int,
    idReserva int,
    foreign key  (idReserva) references tb_reserva(idReserva)
);


