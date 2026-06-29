CREATE TABLE editoriales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(15),
    correo_contacto VARCHAR(255) UNIQUE
);

INSERT INTO editoriales (nombre, direccion, telefono, correo_contacto) 
VALUES ('Editorial', 'Santiago', '984637204', 'contacto@gmail.com');