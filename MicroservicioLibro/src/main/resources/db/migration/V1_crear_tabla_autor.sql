CREATE TABLE autores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) UNIQUE,
    correo VARCHAR(255) UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    numero VARCHAR(15),
    ubicacion VARCHAR(200),
    nacionalidad VARCHAR(100)
);

INSERT INTO autores (rut, correo, nombre, numero, ubicacion, nacionalidad) 
VALUES ('12.345.678-9', 'werinton@gmail.com', 'Werinton Viagney', '947395738', 'Santiago', 'Chilena');