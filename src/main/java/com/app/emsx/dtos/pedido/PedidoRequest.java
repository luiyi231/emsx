package com.app.emsx.dtos.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequest {
    @PastOrPresent(message = "La fecha no puede ser del futuro.")
    @NotNull(message = "La fecha es obligatoria.")
    private Date fecha;

    @PositiveOrZero(message = "El total no puede ser negativo.")
    @NotNull(message = "El total es obligatorio.")
    private Double total;

    @NotNull(message = "El pedido debe tener un cliente asociado.")
    private Long clienteId;

    // Lista de productos con su cantidad
    @NotNull(message = "Debe enviar al menos un producto.")
    private List<PedidoProductoItem> productos;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PedidoProductoItem {
        @NotNull
        private Long productoId;

        @PositiveOrZero(message = "La cantidad no puede ser negativa.")
        @jakarta.validation.constraints.Max(value = 999, message = "No puedes pedir más de 999 unidades de un producto.")
        private Integer cantidad;
    }
}
