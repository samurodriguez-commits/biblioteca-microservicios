CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL UNIQUE,
    numero INT NOT NULL
);

INSERT INTO usuarios (nombre, correo, numero) VALUES ('Pedrito Viagney', 'Pedrito@gmail.com', 934256235);