CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    fecha_publicacion VARCHAR(30) NOT NULL,
    idioma_id INT,
    autor_id INT,
    editorial_id INT,
    categoria_id INT
);

INSERT INTO libros (nombre, fecha_publicacion, idioma_id, autor_id, editorial_id, categoria_id) 
VALUES ('Cien años de soledad', '1967', 1, 1, 1, 1);