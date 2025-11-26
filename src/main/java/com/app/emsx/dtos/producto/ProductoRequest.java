package com.app.emsx.dtos.producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequest {
    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres.")
    private String name;

    @NotNull(message = "El precio es obligatorio.")
    @jakarta.validation.constraints.Positive(message = "El precio debe ser mayor a 0.")
    @jakarta.validation.constraints.Max(value = 99999, message = "El precio no puede superar los 99999.")
    private Double price;
}
