package com.app.emsx.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest
 * -----------------------------------------------------
 * ✔ DTO para registro de nuevos usuarios
 * ✔ Compatible con el frontend (React/Next.js)
 * ✔ Usado en /api/auth/register
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    /**
     * Nombre del usuario
     */
    @NotBlank(message = "El nombre no puede estar vacío.")
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios, sin números ni símbolos.")
    private String firstname;

    /**
     * Apellido del usuario
     */
    @jakarta.validation.constraints.NotBlank(message = "El apellido no puede estar vacío.")
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido solo puede contener letras y espacios, sin números ni símbolos.")
    private String lastname;

    /**
     * Correo electrónico único del usuario
     */
    @jakarta.validation.constraints.NotBlank(message = "El email no puede estar vacío.")
    @jakarta.validation.constraints.Email(message = "El email debe ser válido.")
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El email debe tener un formato válido.")
    private String email;

    /**
     * Contraseña del usuario
     */
    @jakarta.validation.constraints.NotBlank(message = "La contraseña no puede estar vacía.")
    private String password;
}
