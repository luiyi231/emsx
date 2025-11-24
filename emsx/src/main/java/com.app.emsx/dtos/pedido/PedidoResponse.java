package com.app.emsx.dtos.pedido;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {
    private Long id;
    private Date fecha;
    private Double total;

    private Long clienteId;

    private List<ProductoItemResponse> productos;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductoItemResponse {
        private Long productoId;
        private Integer cantidad;
        private Double subtotal;
    }
}
