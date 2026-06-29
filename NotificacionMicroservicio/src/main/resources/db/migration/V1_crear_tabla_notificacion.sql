CREATE TABLE notificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioid INT NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    fecha_envio DATE NOT NULL,
    leida BOOLEAN NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    FOREIGN KEY (usuarioid) REFERENCES usuarios(id)
);

INSERT INTO notificaciones (usuarioid, mensaje, fecha_envio, leida, tipo) 
VALUES (1, 'Bienvenido a la biblioteca', '2026-06-21', false, 'INFO');