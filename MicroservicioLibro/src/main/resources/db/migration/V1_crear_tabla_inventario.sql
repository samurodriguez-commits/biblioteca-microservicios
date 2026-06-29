-- 1. Crear la tabla de inventarios
CREATE TABLE inventarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    servicio_control_productos VARCHAR(50) NOT NULL,
    servicio_compras_proveedores VARCHAR(50) NOT NULL,
    servicio_ventas_pedidos VARCHAR(50) NOT NULL,
    servicio_stock_almacen VARCHAR(50) NOT NULL
);

INSERT INTO inventarios (servicio_control_productos, servicio_compras_proveedores, servicio_ventas_pedidos, servicio_stock_almacen) 
VALUES ('Activo', 'Activo', 'Activo', 'Activo');