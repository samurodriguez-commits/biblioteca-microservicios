package com.example.MicroservicioLibro.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Integer id;
    private String servicioControlProductos;
    private String servicioComprasProveedores;
    private String servicioVentasPedidos;
    private String servicioStockAlmacen;
    private String nombreProducto;
    private Integer cantidad;
}
