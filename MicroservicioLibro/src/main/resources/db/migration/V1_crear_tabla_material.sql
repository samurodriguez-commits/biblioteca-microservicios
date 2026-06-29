CREATE TABLE materiales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_material VARCHAR(50) NOT NULL,
    tipo_material VARCHAR(50),
    estado VARCHAR(50),
    libro_id INT
);

INSERT INTO materiales (nombre_material, tipo_material, estado, libro_id) 
VALUES ('cuero especial', 'cubierta resistente', 'Disponible', 1);