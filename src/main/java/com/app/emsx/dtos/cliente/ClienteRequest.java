package com.app.emsx.dtos.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {
    @NotBlank(message = "El nombre del cliente no puede estar vacío.")
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios, sin números ni símbolos.")
    private String name;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe ser válido.")
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El email debe tener un formato válido. No puede terminar con símbolos antes del @.")
    private String email;
}
