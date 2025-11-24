package com.app.emsx.dtos.factura;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaRequest {
    @PastOrPresent(message = "La fecha no puede ser del futuro.")
    @NotNull(message = "La fecha es obligatoria.")
    private Date fecha;

    @PositiveOrZero(message = "El total no puede ser negativo.")
    @NotNull(message = "El total es obligatorio.")
    private Double total;

    @NotNull(message = "La factura debe estar asociada a un pedido.")
    private Long pedidoId;
}
