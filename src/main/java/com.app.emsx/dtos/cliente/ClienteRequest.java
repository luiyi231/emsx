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
    private String name;

    @Email(message = "El email debe ser válido.")
    @NotBlank(message = "El email no puede estar vacío.")
    private String email;
}
